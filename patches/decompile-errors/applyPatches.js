/**
 * Copyright (c) Ouja and contributers
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

const { error, log, warn } = require('../../config');

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
      rejected.push(f);
      error(`There was an error trying to apply a patch for '~/${f}'`);
      warn(e);
    }
    fs.rmSync(`${path.join(baseDir, '../../decompiled')}/${f}`);
  });
}

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync('decompiled/.git')) return error('Git repo is not initialized. Run git init inside decompiled');
  readDir('patches/decompile-errors/decompiled/minecraft');
  readDir('patches/decompile-errors/decompiled/mojang');
  if (rejected.length > 0) fs.writeFileSync('rejected_patches.rej', rejected.join('\n'));
})();