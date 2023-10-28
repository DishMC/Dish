/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const args = process.argv.slice(2);
const fs = require('fs');
const { error, DEFAULT_MINECRAFT_VERSION, log, warn } = require('../config');
const { execSync } = require('child_process');
const calculateFileHash = require('../utils/checkFileHash');

const stdio = [process.stdin, process.stdout, process.stderr];
const DECOMPILE_VERSION = args[0] ?? DEFAULT_MINECRAFT_VERSION;

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    if (fs.statSync(dir + '/' + f).isDirectory()) return readDir(dir + '/' + f);
    if (!fs.existsSync('dish/workspace/tmp/' + dir)) fs.mkdirSync('dish/workspace/tmp/' + dir, { recursive: true });
    fs.writeFileSync(`dish/workspace/tmp/${dir}/${f}.hash`, calculateFileHash(dir + '/' + f));
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
  log(`Creating workspace for version '${DECOMPILE_VERSION}'`);
  if (!fs.existsSync(`workspaces/${DECOMPILE_VERSION.split('/')[1]}`)) {
    // If workspace does not exist, try to create it.
    // If it fails, error out
    try {
      execSync(`node init.js ${DECOMPILE_VERSION}`, { stdio });
    } catch (e) {
      error(e);
      return process.exit(1);
    }
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
  fs.writeFileSync('dish/workspace/.gitignore', 'build\n*gradle\nsrc/main/resources\n');
  fs.rmSync('dish/workspace/build.gradle');
  const versionJson = JSON.parse(fs.readFileSync(`dish/libraries/${DECOMPILE_VERSION.split('/')[1]}.json`).toString());
  const DEPENDENCIES = parseLibraries(fs.readFileSync(`cache/${DECOMPILE_VERSION.split('/')[1]}/META-INF/libraries.list`).toString()).join('\n\t');
  const GRADLE = (fs.readFileSync('libs/static/build.gradle.dish.txt').toString()).replace('{VERSION}', versionJson.version).replace('{DEPENDENCIES}', DEPENDENCIES);
  fs.writeFileSync(`dish/workspace/build.gradle`, GRADLE);
  log('Copied workspace');
  warn('Initilizing git repo, this may take a while...');
  execSync('cd dish/workspace && git init');
  execSync('cd dish/workspace && git add .', { stdio });
  execSync('cd dish/workspace && git commit -m "Initial Commit"', { stdio });
  log(`Created workspace for the version '${DECOMPILE_VERSION}'`);
  warn('Creating hashes, this may take a while...');
  readDir('dish/workspace/src/main/java');
  warn('Attempting to apply patches');
  execSync(`node patches/applyPatches ${DECOMPILE_VERSION}${args[1] ? ' ' + args[1] : ''}`, { stdio });
  log('Getting version.json');
  execSync(`cd cache/${DECOMPILE_VERSION.split('/')[1]} && jar xf server-${DECOMPILE_VERSION.split('/')[1]}.jar version.json`, { stdio });
  const VERSION = JSON.parse(fs.readFileSync(`cache/${DECOMPILE_VERSION.split('/')[1]}/version.json`).toString());
  VERSION.build_time = new Date();
  fs.writeFileSync('dish/workspace/src/main/resources/version.json', JSON.stringify(VERSION, null, 2.5));
  process.exit(0);
})();