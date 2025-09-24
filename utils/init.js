/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { execSync } = require('child_process');
const { error, warn, copyDir, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, stdio } = require('../config');

async function init(DECOMPILE_VERSION = DEFAULT_MINECRAFT_VERSION, OLD_VERSION = null) {
  let MINECRAFT_VERSION = 'UNKNOWN';

  try {
    if (!checkMinecraftVersion(DECOMPILE_VERSION)) {
      error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
      return false;
    }

    if (OLD_VERSION && !checkMinecraftVersion(OLD_VERSION)) {
      error(`Using invalid Minecraft version '${OLD_VERSION}'`);
      return false;
    }

    if (!fs.existsSync('mache/gradlew')) {
      error('Run git submodule update --init --recursive');
      return false;
    }

    MINECRAFT_VERSION = DECOMPILE_VERSION.split('/').pop();

    if (process.platform !== 'win32') {
      execSync(`cd mache && chmod +x gradlew`, { stdio }); // for linux, you will need to make gradlew executable
      execSync(`cd mache && ./gradlew :versions:v${MINECRAFT_VERSION.replace(/\./g, '_')}:setup`, { stdio });
    } else {
      execSync(`cd mache && gradlew :versions:v${MINECRAFT_VERSION.replace(/\./g, '_')}:setup`, { stdio });
    }

    fs.cpSync(`mache/versions/${MINECRAFT_VERSION}/src/main/resources/assets`, `cache/${MINECRAFT_VERSION}/assets`, { recursive: true });
    fs.cpSync(`mache/versions/${MINECRAFT_VERSION}/src/main/resources/data`, `cache/${MINECRAFT_VERSION}/data`, { recursive: true });
    fs.cpSync(`mache/versions/${MINECRAFT_VERSION}/src`, 'decompiled/src', { recursive: true, force: true });
    await createWorkspace(DECOMPILE_VERSION); // automatically create a workspace
    // delete decompiled source as it's taking space
    fs.rmSync('decompiled', { recursive: true });
    return true;
  } catch (e) {
    console.error(e);
    return false;
  }
}

// create-workspace
function parseLibraries(libraries) {
  libraries = libraries.split('\n');
  libraries = libraries.map(l => l.split('	')[1]);
  libraries = libraries.filter(l => l !== undefined);
  libraries = libraries.map(l => `implementation '${l}'`);
  return libraries;
}

async function createWorkspace(DECOMPILE_VERSION = DEFAULT_MINECRAFT_VERSION) {
  if (!DECOMPILE_VERSION || !DECOMPILE_VERSION.includes('/')) return error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
  const MC_VER = DECOMPILE_VERSION.split('/')[1];
  if (!fs.existsSync(`cache/${MC_VER}`)) return error(`Could not find cache version '${MC_VER}'!`);
  // todo: Make this auto archive the workspace, then delete it and create a new one.
  if (fs.existsSync(`workspaces/${MC_VER}`)) return error('Workspace is already found');
  fs.mkdirSync(`workspaces/${MC_VER}`, { recursive: true });
  fs.cpSync('libs/static/gradle', `workspaces/${MC_VER}/gradle`, { recursive: true });
  fs.cpSync('libs/static/gradlew', `workspaces/${MC_VER}/gradlew`, { recursive: true });
  fs.cpSync('libs/static/gradlew.bat', `workspaces/${MC_VER}/gradlew.bat`, { recursive: true });
  fs.cpSync('libs/static/settings.gradle', `workspaces/${MC_VER}/settings.gradle`, { recursive: true });
  const DEPENDENCIES = parseLibraries(fs.readFileSync(`cache/${MC_VER}/META-INF/libraries.list`).toString()).join('\n\t');
  const GRADLE = (fs.readFileSync('libs/static/build.gradle.txt').toString()).replace('{VERSION}', MC_VER + '-R0-SNAPSHOT').replace('{DEPENDENCIES}', DEPENDENCIES);
  fs.writeFileSync(`workspaces/${MC_VER}/build.gradle`, GRADLE);
  log('Created a workspace');
  warn('Moving decompiled source to workspace, this may take a while...');
  fs.mkdirSync(`workspaces/${MC_VER}/src/main/java/net/minecraft`, { recursive: true });
  fs.mkdirSync(`workspaces/${MC_VER}/src/main/java/com/mojang`, { recursive: true });
  // copy source folders
  copyDir('decompiled/src/main/java/net/minecraft', `workspaces/${MC_VER}/src/main/java/net/minecraft`);
  copyDir('decompiled/src/main/java/com/mojang', `workspaces/${MC_VER}/src/main/java/com/mojang`);
  // copy assets and data folders
  fs.mkdirSync(`workspaces/${MC_VER}/src/main/resources/assets`, { recursive: true });
  fs.mkdirSync(`workspaces/${MC_VER}/src/main/resources/data`, { recursive: true });
  copyDir(`cache/${MC_VER}/assets`, `workspaces/${MC_VER}/src/main/resources/assets`);
  copyDir(`cache/${MC_VER}/data`, `workspaces/${MC_VER}/src/main/resources/data`);
  // Linux wants the gradlew file to be executable, and to run it with ./ in the beginning. Windows does not do this.
  if (process.platform !== 'win32') {
    execSync(`cd workspaces/${MC_VER} && chmod +x gradlew`, { stdio }); // for linux, you will need to make gradlew executable
    execSync(`cd workspaces/${MC_VER} && ./gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  } else {
    execSync(`cd workspaces/${MC_VER} && gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  }
}

module.exports = init;
