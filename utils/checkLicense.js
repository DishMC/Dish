/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { error, log } = require('../config');

const LICENSE_HEADER = fs.readFileSync('LICENSE-header.txt').toString();
const MISSING_LICENSE = [];
const IGNORED_PATHS = ['node_modules', 'Dish-API', 'Dish-Bundler', 'cache', 'decompiled', 'compiled', 'workspaces', 'dish/workspace', '.git'];

function readDir(dir) {
  if (IGNORED_PATHS.includes(dir.replace('./', ''))) return;
  fs.readdirSync(dir).forEach(f => {
    if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(dir + '/' + f);
    if (!f.endsWith('.js')) return;
    const file = fs.readFileSync(`${dir}/${f}`).toString();
    if (!file.startsWith(LICENSE_HEADER)) MISSING_LICENSE.push(dir + '/' + f);
    log(`Checked ${dir}/${f}`);
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