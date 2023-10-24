/**
 * Copyright (c) Ouja and contributers
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { error } = require('../config');

const LICENSE_HEADER = fs.readFileSync('LICENSE-header.txt').toString();

let MISSING_LICENSE = null;

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(dir + '/' + f);
    if (!f.endsWith('.js')) return;
    const file = fs.readFileSync(`${dir}/${f}`).toString();
    if (!file.startsWith(LICENSE_HEADER)) MISSING_LICENSE = dir + '/' + f;
  });
}

(async function () {
  readDir('.');
  if (MISSING_LICENSE) {
    error(`Missing license header inside ${MISSING_LICENSE}`);
    return process.exit(1);
  }
  process.exit(0);
})();