/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');

const { error, log, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, warn } = require('../config');
const calculateFileHash = require('../utils/checkFileHash');

const args = process.argv.slice(2);
const baseDir = __dirname;

const DECOMPILE_VERSION = args[0];

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      const PATCH_DIR = `${baseDir}/${dir}`.replace('/decompiled/', `/${DECOMPILE_VERSION}/`);
      const WORKSPACE_DIR = `${baseDir}/${dir}`.replace('\\patches/decompiled/', `\\dish\\workspace\\src\\main\\java\\${dir.includes('minecraft') ? 'net\\' : 'com\\'}`);
      if (fs.existsSync(WORKSPACE_DIR + '/' + f)) {
        const WORKSPACE_HASH = calculateFileHash(`${WORKSPACE_DIR}/${f}`); // The file hash of the file inside the workspaces directory
        const DECOMPILED_HASH = calculateFileHash(`${dir}/${f}`); // The file hash of the file inside of the decompiled directory
        if (WORKSPACE_HASH !== DECOMPILED_HASH) {
          if (!fs.existsSync(PATCH_DIR)) fs.mkdirSync(PATCH_DIR, { recursive: true });
          return;
          fs.cpSync(`${WORKSPACE_DIR}/${f}`, `${dir}/${f}`, { recursive: true });
          execSync(`cd ./${dir} && git diff -u --minimal --output="${baseDir}/${f.replace('.java', '.patch')}" ${f}`, { stdio: [] });
          if (fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
            fs.cpSync(`${baseDir}/${f.replace('.java', '.patch')}`, `${PATCH_DIR}/${f.replace('.java', '.patch')}`);
            log(`Generated patch for '~/${f}'`);
          }
          fs.rmSync(`${baseDir}/${f.replace('.java', '.patch')}`, { recursive: true });
        }
      } else {
        error(`Can't find ${WORKSPACE_DIR}/${f}`);
      }
    } catch (e) {
      error(`There was an error trying to generate a patch for '~/${f}'`);
      error(e);
    }
  });
}

function readDishDir(dir) {
  const PATCH_DIR = `${baseDir}/${dir}`.replace('/dish/workspace/src/main/java/', `/${DECOMPILE_VERSION}/`);

  fs.readdirSync(dir).forEach(function (f) {
    if (fs.statSync(dir + '/' + f).isDirectory()) return readDishDir(dir + '/' + f);
    const OLD_HASH = fs.existsSync(`dish/workspace/tmp/${dir}/${f}.hash`) ? fs.readFileSync(`dish/workspace/tmp/${dir}/${f}.hash`).toString() : '0';
    const NEW_HASH = calculateFileHash(dir + '/' + f);

    if (OLD_HASH == '0') {
      // this file did not exist
      // so I need to add it to git
      execSync(`cd ${dir} && git add ${f}`);
    }

    if (NEW_HASH !== OLD_HASH) {
      execSync(`cd ${dir} && git diff${OLD_HASH == '0' ? ' --cached' : ''} -u --minimal --output=${baseDir}/${f.replace('.java', '.patch')} ${f}`);
      if (fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
        fs.cpSync(`${baseDir}/${f.replace('.java', '.patch')}`, `${PATCH_DIR}/${f.replace('.java', '.patch')}`);
        log(`Generated patch for '~/${f}'`);
      }
      fs.rmSync(`${baseDir}/${f.replace('.java', '.patch')}`, { recursive: true });
    }
  });
}

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync('dish/workspace/.git')) return error('Git repo is not initialized. Run git init inside dish/workspace');
  // args[0] should be {releaseType}/{version}
  // releaseType is release or snapshot
  if (!DECOMPILE_VERSION || !DECOMPILE_VERSION.includes('/')) return error('Missing releaseType and version!');
  if (!checkMinecraftVersion(DECOMPILE_VERSION)) return error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
  readDishDir('dish/workspace/src/main/java');
})();