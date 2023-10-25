/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const { execSync } = require('child_process');
const fs = require('fs');

const { error, log, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION } = require('../../config');

const args = process.argv.slice(2);
const baseDir = __dirname;

const DECOMPILE_VERSION = args[0] ?? DEFAULT_MINECRAFT_VERSION;

function readDir(dir) {
  fs.readdirSync(dir).forEach(f => {
    try {
      if (fs.statSync(`${dir}/${f}`).isDirectory()) return readDir(`${dir}/${f}`);
      const PATCH_DIR = `${baseDir}/${dir}`.replace('/decompiled/', `/${DECOMPILE_VERSION}/`);
      if (!fs.existsSync(PATCH_DIR)) fs.mkdirSync(PATCH_DIR, { recursive: true });
      execSync(`cd ./${dir} && git diff -u --minimal --output="${baseDir}/${f.replace('.java', '.patch')}" ${f}`, { stdio: [] });
      if (fs.readFileSync(`${baseDir}/${f.replace('.java', '.patch')}`).toString().length > 2) {
        log(`Generated patch for '~/${f}'`);
        fs.cpSync(`${baseDir}/${f.replace('.java', '.patch')}`, `${PATCH_DIR}/${f.replace('.java', '.patch')}`);
      } else {
        log(`'~/${f}' is the same. Ignoring`);
      }
      fs.rmSync(`${baseDir}/${f.replace('.java', '.patch')}`, { recursive: true });
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
  readDir('decompiled/minecraft');
  readDir('decompiled/mojang');
})();