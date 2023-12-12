/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { log, warn, error } = require('./config');

const args = process.argv.slice(2);
const deleteWorkspaces = args[0] && args[0] == 'true';

function rm(path, options) {
  fs.rmSync(path, options);
  log(`Deleted ${path}`);
}

(async function () {
  if (fs.existsSync('cache')) {
    log('Deleting cache directory');
    rm('cache', { recursive: true });
  }
  if (fs.existsSync('rejected_patches.rej')) rm('rejected_patches.rej');
  if (fs.existsSync('rejected_dish_patches.rej')) rm('rejected_dish_patches.rej');
  if (!deleteWorkspaces) return process.exit(0);
  warn('Deleting workspaces in 5 seconds, this can delete any progress you have worked on!');
  warn('You can stop this by pressing CTRL + C on your keyboard!');
  setTimeout(() => {
    log('Deleting workspaces');
    if (fs.existsSync('workspaces')) rm('workspaces', { recursive: true });
    fs.readdirSync('dish').forEach(d => {
      if (d.startsWith('workspace')) rm(`dish/${d}`, { recursive: true });
    });
    process.exit(0);
  }, 1000 * 5);
})();
