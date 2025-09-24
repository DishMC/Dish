/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const crypto = require('crypto');

/**
 * @param {String} pathToFile The file path
 * @returns a sha1 hex hash of the file
 */
function calculateFileHash(pathToFile) {
  return crypto.createHash('sha1').update(fs.readFileSync(pathToFile).toString().replace(/\n/g, '')).digest("hex");
}

module.exports = calculateFileHash;