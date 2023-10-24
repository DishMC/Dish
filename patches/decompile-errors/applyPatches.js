const { execSync } = require('child_process');
const fs = require('fs');
const { error, log, warn } = require('../../config');

const baseDir = __dirname;

const rejected = [];

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      fs.cpSync(`${dir}/${f}`, `decompiled/${f}`);
      execSync(`cd decompiled && git apply ${f}`, { stdio: [] });
      log(`Applied ${f.replace('.patch', '')}`);
    } catch (e) {
      rejected.push(f);
      error(`There was an error trying to apply a patch for '~/${f}'`);
      warn(e);
    }
    fs.rmSync(`decompiled/${f}`);
  });
}

(async function () {
  readDir('patches/decompile-errors/decompiled/minecraft');
  readDir('patches/decompile-errors/decompiled/mojang');
  if (rejected.length > 0) fs.writeFileSync('rejected_patches.rej', rejected.join('\n\n'));
})();