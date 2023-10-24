/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');
const { error, log } = require('../../config');

const baseDir = __dirname;

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      if (!fs.existsSync(`${baseDir}/${dir}`)) fs.mkdirSync(`${baseDir}/${dir}`, { recursive: true });
      execSync(`cd ./${dir} && git diff -u --minimal --output="${baseDir}/${dir}/${f.replace('.java', '.patch')}" ${f}`, { stdio: [] });
      if (fs.readFileSync(`${baseDir}/${dir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
        log(`Generated patch for '~/${f}'`);
      } else {
        fs.rmSync(`${baseDir}/${dir}/${f.replace('.java', '.patch')}`, { recursive: true });
        if (fs.readdirSync(`${baseDir}/${dir}`).length == 0) fs.rmSync(`${baseDir}/${dir}`, { recursive: true });
      }
    } catch (e) {
      error(`There was an error trying to generate a patch for '~/${f}'`);
      error(e);
    }
  });
}

(async function () {
  readDir('decompiled/minecraft');
  readDir('decompiled/mojang');
})();