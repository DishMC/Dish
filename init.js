/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const args = process.argv.slice(2);
const fs = require('fs');
const { execSync } = require('child_process');
const crypto = require('crypto');
const path = require('path');
const calculateFileHash = require('./utils/checkFileHash');
const { warn, deleteDir, copyDir, log, deleteFile, cacheExpired, error, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, stdio } = require('./config');
const parseArg = require('./utils/parseArguments');

const DECOMPILE_VERSION = parseArg('MC', DEFAULT_MINECRAFT_VERSION);
const OLD_VERSION = parseArg('OLD_MC', null);
const IGNORE_CACHE = parseArg('IGNORE-CACHE', null);

let MINECRAFT_VERSION = "UNKNOWN";

(async function () {
  try {
    if (!checkMinecraftVersion(DECOMPILE_VERSION)) return error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
    if (OLD_VERSION && !checkMinecraftVersion(OLD_VERSION)) return error(`Using invalid Minecraft version '${OLD_VERSION}'`);
    console.time('Download server');
    const meta = await downloadServer();
    console.timeEnd('Download server');
    console.time('Remap server');
    await remapServer(meta.id);
    console.timeEnd('Remap server');
    console.time('Decompile');
    if (fs.existsSync('compiled')) deleteDir('compiled');
    if (fs.existsSync('decompiled')) deleteDir('decompiled');
    fs.mkdirSync('compiled');
    fs.mkdirSync('decompiled');
    warn('Extracting server.jar, this may take a while...');
    execSync(`jar xf server.jar net com assets data`, { stdio });
    warn('Moving assets and data folders to cache');
    fs.cpSync('assets', `cache/${MINECRAFT_VERSION}/assets`, { recursive: true });
    fs.cpSync('data', `cache/${MINECRAFT_VERSION}/data`, { recursive: true });
    deleteDir('assets');
    deleteDir('data');
    warn('Copying net and com directories, this may take a while...');
    // fs.mkdirSync('compiled/net', { recursive: true });
    // fs.mkdirSync('compiled/com', { recursive: true });
    fs.mkdirSync('compiled', { recursive: true });
    copyDir('net', 'compiled');
    copyDir('com', 'compiled');
    deleteDir('net');
    deleteDir('com');
    execSync(`java -jar ./libs/fernflower.jar -dgs=1 -hdc=0 -asc=1 -udv=0 -rsy=1 -aoa=1 ${path.join(__dirname, 'compiled')} ${path.join(__dirname, 'decompiled')}`, { stdio });
    console.timeEnd('Decompile');
    log('Cleaning up compiled sources');
    deleteDir('compiled');
    deleteFile('server.jar');
    if (fs.existsSync('cache/to_apply')) {
      warn('deleting old to_apply dir');
      fs.rmSync('cache/to_apply', { recursive: true });
    }
    warn('Copying decompiled files');
    fs.cpSync('decompiled', 'cache/to_apply', { recursive: true });
    warn('Initializing git repo, this may take a while...');
    execSync('cd decompiled && git init'); // Need to initialize a git repository so that applying patches will work.
    warn('Starting to apply patches');
    execSync(`node patches/decompile-errors/applyPatches.js ${DECOMPILE_VERSION}${OLD_VERSION ? ' ' + OLD_VERSION : ''}`, { stdio });
    warn('Adding files to git repo, this may take a while...');
    execSync('cd decompiled && git add .', { stdio });
    execSync('cd decompiled && git commit -m "Initial Commit"', { stdio });
    execSync(`node create-workspace.js ${DECOMPILE_VERSION}`, { stdio }); // automatically create a workspace
    // delete decompiled source as it's taking space
    fs.rmSync('decompiled', { recursive: true });
  } catch (e) {
    console.error(e);
    return process.exit(1);
  }
  process.exit(0);
})();

async function downloadServer() {
  let manifest;
  if (!fs.existsSync('cache')) fs.mkdirSync('cache');
  if (fs.existsSync('cache/manifest.json') && !cacheExpired('cache/manifest.json') && !IGNORE_CACHE) {
    manifest = JSON.parse(fs.readFileSync('cache/manifest.json').toString());
    warn('Using cache/manifest.json');
  } else {
    manifest = await (await fetch('https://launchermeta.mojang.com/mc/game/version_manifest_v2.json')).json();
    fs.writeFileSync('cache/manifest.json', JSON.stringify(manifest));
  }

  let index = manifest.versions.findIndex(a => a.id == DECOMPILE_VERSION.split('/')[1]);
  if (index == -1) index = 0;
  const version = manifest.versions[index].id;
  MINECRAFT_VERSION = version;
  warn(`Found Minecraft version '${version}' with the release type '${manifest.versions[index].type}'`);
  let meta;
  if (fs.existsSync(`cache/${version}.json`) && !cacheExpired(`cache/${version}.json`) && !IGNORE_CACHE) {
    meta = JSON.parse(fs.readFileSync(`cache/${version}.json`).toString());
    warn(`using cache/${version}.json`);
  } else {
    meta = await (await fetch(manifest.versions[index].url)).json();
    fs.writeFileSync(`cache/${manifest.versions[index].id}.json`, JSON.stringify(meta));
  }

  if (!fs.existsSync(`cache/${version}`)) fs.mkdirSync(`cache/${version}`);

  if (fs.existsSync(`cache/${version}/server.jar`) && !IGNORE_CACHE) {
    const hash = calculateFileHash(`cache/${version}/server.jar`);
    if (hash !== meta.downloads.server.sha1) {
      warn(`Server jar sha1 hash does not match!\nFile Hash: ${hash}\nMeta: ${meta.downloads.server.sha1}`);
      warn('Deleting cache and re-installing...');
      deleteDir(`cache/${version}`);
      return await downloadServer();
    }
    warn(`using cache/${version}/server.jar`);
  } else {
    execSync(`curl -o cache/${version}/server.jar ${meta.downloads.server.url}`, { stdio });
  }

  if (fs.existsSync(`cache/${version}/mappings.txt`) && !IGNORE_CACHE) {
    const hash = calculateFileHash(`cache/${version}/mappings.txt`);
    if (hash !== meta.downloads.server_mappings.sha1) {
      warn(`Server mappings sha1 hash does not match!\nFile Hash: ${hash}\nMeta: ${meta.downloads.server_mappings.sha1}`);
      warn('Deleting cache and re-installing...');
      deleteDir(`cache/${version}`);
      return await downloadServer();
    }
    warn(`using cache/${version}/mappings.txt`);
  } else {
    execSync(`curl -o cache/${version}/mappings.txt ${meta.downloads.server_mappings.url}`, { stdio });
  }

  if (!fs.existsSync(`cache/${version}/server-${version}.jar`)) {
    execSync(`cd cache/${version} && jar xf server.jar META-INF`, { stdio });
    fs.copyFileSync(`cache/${version}/META-INF/versions/${version}/server-${version}.jar`, `cache/${version}/server-${version}.jar`);
  }

  return meta;
}

async function remapServer(version) {
  execSync(`java -jar ./libs/specialsource.jar -i ./cache/${version}/server-${version}.jar -m ./cache/${version}/mappings.txt -o server.jar`, { stdio });
}
