/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

/**
 * Parse `process.argv` and return what you are looking for.
 * @param {String} key The name of the argument you are looking for. EX: --MC_VER
 * @param {String} defaultValue The default return value
 * @returns {String} The value of the found argument
 * @since v1.1.3
 */
function parseArg(key, defaultValue) {
  const args = process.argv.slice(2);
  const foundArg = args.filter(a => a.startsWith(`--${key}=`))[0];
  return foundArg ? foundArg.split(`--${key}=`)[1] : defaultValue;
}

module.exports = parseArg;