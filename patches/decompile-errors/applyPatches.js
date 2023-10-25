/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

const { error, log, warn, checkMinecraftVersion, copyDir } = require('../../config');

const args = process.argv.slice(2);
const baseDir = __dirname;

const rejected = [];

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      fs.cpSync(`${dir}/${f}`, `${path.join(baseDir, '../../decompiled')}/${f}`);
      execSync(`cd ${path.join(baseDir, '../../decompiled')} && git apply ${f}`, { stdio: [process.stdin, process.stdout, process.stderr] });
      log(`Applied ${f.replace('.patch', '')}`);
    } catch (e) {
      rejected.push(`${dir}/${f}`);
      error(`There was an error trying to apply a patch for '~/${f}'`);
      warn(e);
    }
    fs.rmSync(`${path.join(baseDir, '../../decompiled')}/${f}`);
  });
}

/**
 * This will read the patch directory for the provided Minecraft version, then copy the patch file into the decompiled directory, apply it, then delete it.
 * It is slow. Need to find a better way.
 */

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync('decompiled/.git')) return error('Git repo is not initialized. Run git init inside decompiled');
  // args[0] should be {releaseType}/{version}
  // releaseType is release or snapshot
  if (!args[0] || !args[0].includes('/')) return error('Missing releaseType and version!');
  if (!checkMinecraftVersion(args[0])) return error(`Using invalid Minecraft version '${args[0]}'`);
  if (!fs.existsSync(`patches/decompile-errors/${args[0]}`)) {
    if ((!args[1] || !args[1].includes('/')) || !fs.existsSync(`patches/decompile-errors/${args[1]}`)) return error(`Missing patches for version '${args[0]}'`);
    // create new directory
    fs.mkdirSync(`patches/decompile-errors/${args[0]}`, { recursive: true });
    // copy old version to new version
    copyDir(`patches/decompile-errors/${args[1]}`, `patches/decompile-errors/${args[0]}`);
  }
  readDir(`patches/decompile-errors/${args[0]}/minecraft`);
  if (fs.existsSync(`patches/decompile-errors/${args[0]}/mojang`)) readDir(`patches/decompile-errors/${args[0]}/mojang`);
  if (rejected.length > 0) fs.writeFileSync('rejected_patches.rej', rejected.join('\n'));
})();