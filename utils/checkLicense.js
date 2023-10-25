/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { error } = require('../config');

const LICENSE_HEADER = fs.readFileSync('LICENSE-header.txt').toString();

const MISSING_LICENSE = [];

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(dir + '/' + f);
    if (!f.endsWith('.js')) return;
    const file = fs.readFileSync(`${dir}/${f}`).toString();
    if (!file.startsWith(LICENSE_HEADER)) MISSING_LICENSE.push(dir + '/' + f);
  });
}

(async function () {
  readDir('.');
  if (MISSING_LICENSE.length > 0) {
    error(`Missing license header inside files: ${MISSING_LICENSE.join(', ')}`);
    return process.exit(1);
  }
  process.exit(0);
})();