/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');

const { error, log, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, warn } = require('../../config');
const calculateFileHash = require('../../utils/checkFileHash');
const path = require('path');

const args = process.argv.slice(2);
const baseDir = __dirname;

const DECOMPILE_VERSION = args[0];

function readDir(dir) {
  const MC_VER = DECOMPILE_VERSION.split('/')[1];
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      const PATCH_DIR = `${baseDir}/${dir}`.replace(`/workspaces/${MC_VER}/src/main/java/`, `/${DECOMPILE_VERSION}/`).replace('/net/', '/').replace('/com/', '/');
      const WORKSPACE_DIR = `${baseDir}/${dir}`.replace('\\patches\\decompile-errors/', `/`);
      if (fs.existsSync(WORKSPACE_DIR + '/' + f)) {
        const CACHE_DIR = `cache/to_apply/${dir.replace(`workspaces/${MC_VER}/src/main/java/`, '')}`.replace('/net/', '/').replace('/com/', '/');
        const OLD_HASH = calculateFileHash(`${CACHE_DIR}/${f}`); // the original file hash
        const NEW_HASH = calculateFileHash(`${WORKSPACE_DIR}/${f}`); // the hash of the workspace file
        if (OLD_HASH !== NEW_HASH) {
          if (!fs.existsSync(PATCH_DIR)) fs.mkdirSync(PATCH_DIR, { recursive: true });
          try {
            execSync(`git diff --no-index -u --minimal --output="${baseDir}/${f.replace('.java', '.patch')}" ${CACHE_DIR}/${f} ${dir}/${f}`, { stdio: [] });
          } catch (e) {
            // warn(e); // debug purposes.
          }
          if (fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
            const AREGEX = new RegExp(`a/${CACHE_DIR}/${f}`, 'g');
            const BREGEX = new RegExp(`b/${dir}/${f}`, 'g');
            let patchFile = fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString();
            const replaceDir = dir.replace(`workspaces/${MC_VER}/src/main/java/`, '').replace('net/', '').replace('com/', '');
            patchFile = patchFile.replace(AREGEX, `a/${replaceDir}/${f}`);
            patchFile = patchFile.replace(BREGEX, `b/${replaceDir}/${f}`);
            if (!fs.existsSync(PATCH_DIR)) fs.mkdirSync(PATCH_DIR, { recursive: true });
            fs.writeFileSync(`${PATCH_DIR}/${f.replace('.java', '.patch')}`, patchFile);
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

/**
 * This will read the source code from workspaces/version/src/main/java/net/minecraft and ~/com/mojang -
 * then, it will compare them to the decompiled directory with the files and generate the patches into -
 * the provided patches directory.
 * 
 * This is not performant at all, but nothing is performant when creating it for the first time.
 */

(async function () {
  // check if git repo is initialized, if not, it will not apply patches. 
  if (!fs.existsSync('decompiled/.git')) return error('Git repo is not initialized. Run git init inside decompiled');
  // args[0] should be {releaseType}/{version}
  // releaseType is release or snapshot
  if (!DECOMPILE_VERSION || !DECOMPILE_VERSION.includes('/')) return error('Missing releaseType and version!');
  if (!checkMinecraftVersion(DECOMPILE_VERSION)) return error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
  readDir(`workspaces/${DECOMPILE_VERSION.split('/')[1]}/src/main/java/net/minecraft`);
  readDir(`workspaces/${DECOMPILE_VERSION.split('/')[1]}/src/main/java/com/mojang`);
})();