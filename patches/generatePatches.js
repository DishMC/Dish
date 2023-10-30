/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

const { error, log, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, warn } = require('../config');
const calculateFileHash = require('../utils/checkFileHash');

const args = process.argv.slice(2);
const baseDir = __dirname;
const stdio = [process.stdin, process.stdout, process.stderr];

const DECOMPILE_VERSION = args[0];

function readDir(dir) {
  const PATCH_DIR = `${baseDir}/${dir}`.replace('/dish/workspace/src/main/java/', `/${DECOMPILE_VERSION}/`);

  fs.readdirSync(dir).forEach(function (f) {
    if (fs.statSync(dir + '/' + f).isDirectory()) return readDir(dir + '/' + f);
    const TMP_DIR = dir.replace('workspace/src/main', 'workspace/tmp/src/main');
    const OLD_HASH = fs.existsSync(`${TMP_DIR}/${f}.hash`) ? fs.readFileSync(`${TMP_DIR}/${f}.hash`).toString() : '0';
    const NEW_HASH = calculateFileHash(dir + '/' + f);

    if (NEW_HASH !== OLD_HASH) {
      try {
        // compare between vanilla file and updated file.
        // Before I did it by commit, but I was running into issues where if I commited the change, it would just overwrite the patch. (What it's meant to do)
        // So, compare between files, not commits.
        execSync(`git diff --no-index -u --minimal --output=${baseDir}/${f.replace('.java', '.patch')} ${path.join(__dirname, '../' + TMP_DIR)}\\${f} ${dir}/${f}`, { stdio: [] });
      } catch (e) {
        // warn(e); // used for debug purposes. git diff --no-index will always return an error for some reason
      }
      if (fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
        const AREGEX = new RegExp(`"a/${path.join(__dirname, '../' + TMP_DIR)}\\${f}"`.replace(/\\/g, '\\\\\\\\'), 'g');
        const BREGEX = new RegExp(`b/${dir}/${f}`, 'g');
        let patchFile = fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString();
        patchFile = patchFile.replace(AREGEX, `a/${dir.replace('dish/workspace/', '')}/${f}`);
        patchFile = patchFile.replace(BREGEX, `b/${dir.replace('dish/workspace/', '')}/${f}`);
        if (!fs.existsSync(PATCH_DIR)) fs.mkdirSync(PATCH_DIR, { recursive: true });
        fs.writeFileSync(`${PATCH_DIR}/${f.replace('.java', '.patch')}`, patchFile);
        log(`Generated patch for '~/${f}'`);
      }
      fs.rmSync(`${baseDir}/${f.replace('.java', '.patch')}`, { recursive: true });
    }
  });
}

function readDishDir(dir) {
  const PATCH_DIR = `${baseDir}/${dir}`.replace('/dish/workspace/src/main/java/', `/${DECOMPILE_VERSION}/`);

  fs.readdirSync(dir).forEach(function (f) {
    if (fs.statSync(dir + '/' + f).isDirectory()) return readDishDir(dir + '/' + f);
    if (!fs.existsSync(`${PATCH_DIR}/${f}`)) fs.mkdirSync(PATCH_DIR, { recursive: true });
    fs.cpSync(`${dir}/${f}`, `${PATCH_DIR}/${f}`);
    log(`Moved ${f}`);
  });
}

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync('dish/workspace/.git')) return error('Git repo is not initialized. Run git init inside dish/workspace');
  // args[0] should be {releaseType}/{version}
  // releaseType is release or snapshot
  if (!DECOMPILE_VERSION || !DECOMPILE_VERSION.includes('/')) return error('Missing releaseType and version!');
  if (!checkMinecraftVersion(DECOMPILE_VERSION)) return error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
  readDir('dish/workspace/src/main/java/net/minecraft');
  readDishDir('dish/workspace/src/main/java/net/ouja');
})();