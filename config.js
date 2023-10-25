/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');

const colors = require('./libs/colors');

const DEFAULT_MINECRAFT_VERSION = "release/1.20.2";

function log(msg) {
  console.log(`[Info]${colors.reset} ${msg}`);
}

function debug(msg) {
  console.debug(`${colors.fgMagenta}[Debug]${colors.reset} ${msg}`);
}

function warn(msg) {
  console.warn(`${colors.fgYellow}[Warning]${colors.reset} ${msg}`);
}

function error(msg) {
  console.error(`${colors.fgRed}[Error]${colors.reset} ${msg}`);
}

/**
 * This is a wrapper to delete a directory as linux and windows use different commands!
 * @param {String} path 
 * @param {Object} options 
 * @returns {execSync} execSync
 */
function deleteDir(path, options) {
  const isWindows = process.platform === 'win32';
  if (isWindows) return execSync(`rmdir ${path.replace(/\//g, '\\')} /q /s`, options);
  return execSync(`rm -rf ${path}`, options);
}

/**
 * This is a wrapper to delete a file
 * @param {String} path 
 * @param {Object} options 
 * @returns {execSync} execSync
 */
function deleteFile(path, options) {
  const isWindows = process.platform === 'win32';
  if (isWindows) return execSync(`del ${path.replace(/\//g, '\\')}`, options);
  return execSync(`rm -rf ${path}`, options);
}

/**
 * This is a wrapper to copy a directory
 * @param {String} dir1 
 * @param {String} dir2 
 * @param {Object} options 
 * @returns {execSync} execSync
 */
function copyDir(dir1, dir2, options) {
  const isWindows = process.platform === 'win32';
  if (isWindows) return execSync(`xcopy ${dir1.replace(/\//g, '\\')} ${dir2.replace(/\//g, '\\')} /s /e /h`, options);
  dir2 = dir2.split('/');
  dir2 = dir2.slice(0, dir2.length - 1);
  dir2 = dir2.join('/');
  return execSync(`cp -r ${dir1} ${dir2}`, options);
}

/**
 * Checks if the cache is expired by reading the time of when the file was created and the current date.
 * @param {String} pathToFile The file / folder path
 * @param {Number} MAX_CACHE_TIME_DAYS the time to expire in days
 * @returns {Boolean} if true, the cache has expired.
 */
function cacheExpired(pathToFile, MAX_CACHE_TIME_DAYS = 1) {
  if (!pathToFile || !fs.existsSync(pathToFile)) return true;
  const stat = fs.statSync(pathToFile);
  if (!stat) return true;
  return stat.birthtime.setDate(stat.birthtime.getDate() + 1) < Date.now();
}

/**
 * @param {String} version The minecraft version. release/{version}, snapshot/{version} or pre/{version}-pre{number}
 * @returns {Boolean} true if matches
 */
function checkMinecraftVersion(version) {
  return /(^release\/([0-9]\.[1-3][0-9]\.[0-9]))$|(^snapshot\/([1-3][0-9]w[0-9][0-9][a-z])$)|(^pre\/([0-9]\.[1-3][0-9]\.[0-9]\-pre[0-9])$)/g.test(version);
}

module.exports = {
  log, debug, warn, error,
  deleteDir, deleteFile, copyDir,
  cacheExpired, checkMinecraftVersion,
  DEFAULT_MINECRAFT_VERSION
};
