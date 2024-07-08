/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const args = process.argv.slice(2);
const fs = require('fs');
const calculateFileHash = require('../utils/checkFileHash');
const https = require('https');
const { error, DEFAULT_MINECRAFT_VERSION, log, warn } = require('../config');
const { execSync } = require('child_process');

const stdio = [process.stdin, process.stdout, process.stderr];
const DECOMPILE_VERSION = args[0] ?? DEFAULT_MINECRAFT_VERSION;

function generateHashes(dir) {
  fs.readdirSync(dir).forEach(f => {
    if (fs.statSync(dir + '/' + f).isDirectory()) return generateHashes(dir + '/' + f);
    if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });
    fs.cpSync(`${dir}/${f}`, `${dir.replace('workspace/src/main', 'workspace/tmp/src/main')}/${f}`);
    fs.writeFileSync(`${dir.replace('workspace/src/main', 'workspace/tmp/src/main')}/${f}.hash`, calculateFileHash(dir + '/' + f));
  });
}

function parseLibraries(libraries) {
  libraries = libraries.split('\n');
  libraries = libraries.map(l => l.split('	')[1]);
  libraries = libraries.filter(l => l !== undefined);
  libraries = libraries.map(l => `implementation '${l}'`);
  return libraries;
}

(async function () {
  if (!fs.existsSync('init.js')) return error('Run this file in the parent directory');
  if (!fs.existsSync(`cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list`)) {
    warn(`Could not find file: cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list, installing...`);
    await cacheServer();
    // final check
    if (!fs.existsSync(`cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list`)) {
      error(`Could not find file: cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list`);
      return process.exit(1);
    }
  }

  log(`Creating workspace for version '${DECOMPILE_VERSION}'`);
  if (!fs.existsSync(`workspaces/${DECOMPILE_VERSION.split('/')[1]}`)) {
    // If workspace does not exist, try to create it.
    // If it fails, error out
    warn(`Workspace for version '${DECOMPILE_VERSION.split('/')[1]}' wasn't found. Running init.js`);
    await generateWorkspace();
  }

  if (!fs.existsSync(`dish/libraries/${DECOMPILE_VERSION.split('/')[1]}.json`)) {
    error(`Could not find file: dish/libraries/${DECOMPILE_VERSION.split('/')[1]}.json`);
    return process.exit(1);
  }

  if (fs.existsSync('dish/workspace')) {
    console.time('Moved directories');
    warn('Dish workspace already exists. Moving it, this may take a while...');
    fs.cpSync('dish/workspace', `dish/workspace-old-${Date.now()}`, { recursive: true });
    warn('Deleting old directory');
    fs.rmSync('dish/workspace', { recursive: true });
    console.timeEnd('Moved directories');
  }
  warn('Copying workspace, this may take a while...');
  fs.cpSync(`workspaces/${DECOMPILE_VERSION.split('/')[1]}`, 'dish/workspace', { recursive: true });
  fs.writeFileSync('dish/workspace/.gitignore', 'build\n*gradle\nsrc/main/resources\n.idea\ntmp\n.idea\nrunserver\n');
  fs.rmSync('dish/workspace/build.gradle');
  const versionJson = JSON.parse(fs.readFileSync(`dish/libraries/${DECOMPILE_VERSION.split('/')[1]}.json`).toString());
  const DEPENDENCIES = parseLibraries(fs.readFileSync(`cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list`).toString()).join('\n\t');
  const GRADLE = (fs.readFileSync('libs/static/build.gradle.dish.txt').toString()).replace('{VERSION}', versionJson.version).replace('{API_VERSION}', versionJson.api_version).replace('{DEPENDENCIES}', DEPENDENCIES);
  fs.writeFileSync(`dish/workspace/build.gradle`, GRADLE);
  fs.cpSync('libs/static/flightrecorder-config.jfc', 'dish/workspace/src/main/resources/flightrecorder-config.jfc');
  log('Copied workspace');
  warn('Initilizing git repo, this may take a while...');
  execSync('cd dish/workspace && git init');
  execSync('cd dish/workspace && git add .');
  execSync('cd dish/workspace && git commit -m "Initial Commit"');
  log(`Created workspace for the version '${DECOMPILE_VERSION}'`);
  warn('Creating hashes, this may take a while...');
  generateHashes('dish/workspace/src/main/java');
  warn('Attempting to apply patches');
  execSync(`node patches/applyPatches ${DECOMPILE_VERSION}${args[1] ? ' ' + args[1] : ''}`, { stdio });
  execSync('cd dish/workspace && git add .', { stdio });
  execSync('cd dish/workspace && git commit -m "applied patches"', { stdio });
  process.exit(0);
})();

async function generateWorkspace() {
  return new Promise((res, rej) => {
    execSync(`node init.js --MC="${DECOMPILE_VERSION}" --OLD_MC="${args[1] || ''}" --IGNORE-CACHE="${args[2] ? 'true' : 'false'}"`, { stdio });
    execSync(`node dish/index.js ${DECOMPILE_VERSION}${args[1] ? ' ' + args[1] : ''}`, { stdio });
    res();
  });
}

async function cacheServer(version = DECOMPILE_VERSION.split('/')[1]) {
  const manifestReq = await fetch('https://launchermeta.mojang.com/mc/game/version_manifest_v2.json');
  if (manifestReq.status !== 200) {
    error('https://launchermeta.mojang.com/mc/game/version_manifest_v2.json returned ' + manifestReq.status);
    return process.exit(1);
  }

  const manifest = await manifestReq.json();
  const versionJson = manifest.versions.filter(v => v.id === version)[0];
  if (!versionJson) {
    error(`Could not find version '${version}'`);
    return process.exit(1);
  }

  const packageReq = await fetch(versionJson.url);
  if (manifestReq.status !== 200) {
    error(versionJson.url + ' returned ' + packageReq.status);
    return process.exit(1);
  }

  const package = await packageReq.json();
  await fs.promises.mkdir(`cache/${version}`, { recursive: true });
  await fs.promises.writeFile(`cache/${version}/${version}.json`, JSON.stringify(package));

  log('Downloading server jar...');
  const file = fs.createWriteStream(`cache/${version}/server.jar`);
  await downloadServerJar(package.downloads.server.url, file);
  log('Server jar finished downloading');
  execSync(`cd cache/${version} && jar xf server.jar META-INF`, { stdio });
}

async function downloadServerJar(url, file) {
  return new Promise((res, rej) => {
    const request = https.get(url, (res) => res.pipe(file));
    request.on('error', (e) => rej(e));
    request.on('close', () => res());
  });
}
