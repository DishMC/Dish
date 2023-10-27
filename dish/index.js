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

(async function () {
  if (!fs.existsSync('init.js')) return error('Run this file in the parent directory');
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

  if (fs.existsSync('dish/workspace')) return error('Dish workspace already exists.');
  warn('Copying workspace, this may take a while...');
  fs.cpSync(`workspaces/${DECOMPILE_VERSION.split('/')[1]}`, 'dish/workspace', { recursive: true });
  fs.writeFileSync('dish/workspace/.gitignore', 'build\n*gradle\nsrc/main/resources\n');
  log('Copied workspace');
  warn('Initilizing git repo, this may take a while...');
  execSync('cd dish/workspace && git init');
  execSync('cd dish/workspace && git add .', { stdio });
  execSync('cd dish/workspace && git commit -m "Initial Commit"', { stdio });
  log(`Created workspace for the version '${DECOMPILE_VERSION}'`);
  warn('Creating hashes, this may take a while...');
  readDir('dish/workspace/src/main/java');
  warn('Attempting to apply patches');
  execSync('node patches/applyPatches ' + DECOMPILE_VERSION, { stdio });
  process.exit(0);
})();