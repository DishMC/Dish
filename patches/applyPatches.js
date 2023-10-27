/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

const { error, log, warn, checkMinecraftVersion, copyDir } = require('../config');

const args = process.argv.slice(2);
const baseDir = __dirname;
const PATH = 'dish/workspace';

const rejected = [];

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      fs.cpSync(`${dir}/${f}`, `${PATH}/${f}`);
      execSync(`cd ${PATH} && git apply ${f}`, { stdio: [process.stdin, process.stdout, process.stderr] });
      log(`Applied ${f.replace('.patch', '')}`);
    } catch (e) {
      rejected.push(`${dir}/${f}`);
      error(`There was an error trying to apply a patch for '~/${f}'`);
      warn(e);
    }
    fs.rmSync(`${PATH}/${f}`);
  });
}

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync(`${PATH}/.git`)) return error('Git repo is not initialized. Run git init inside ' + PATH);
  // // args[0] should be {releaseType}/{version}
  // // releaseType is release or snapshot
  if (!args[0] || !args[0].includes('/')) return error('Missing releaseType and version!');
  if (!checkMinecraftVersion(args[0])) return error(`Using invalid Minecraft version '${args[0]}'`);
  if (!fs.existsSync(`patches/${args[0]}`)) {
    if ((!args[1] || !args[1].includes('/')) || !fs.existsSync(`patches/${args[1]}`)) return error(`Missing patches for version '${args[0]}'`);
    // create new directory
    fs.mkdirSync(`patches/${args[0]}`, { recursive: true });
    // copy old version to new version
    copyDir(`patches/${args[1]}`, `patches/${args[0]}`);
  }
  readDir(`patches/${args[0]}`);
  if (rejected.length > 0) fs.writeFileSync('rejected_dish_patches.rej', rejected.join('\n'));
})();