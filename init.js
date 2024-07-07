/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const { execSync } = require('child_process');
const { error, checkMinecraftVersion, DEFAULT_MINECRAFT_VERSION, stdio } = require('./config');
const parseArg = require('./utils/parseArguments');

const DECOMPILE_VERSION = parseArg('MC', DEFAULT_MINECRAFT_VERSION);
const OLD_VERSION = parseArg('OLD_MC', null);

let MINECRAFT_VERSION = 'UNKNOWN';

(async function () {
  try {
    if (!checkMinecraftVersion(DECOMPILE_VERSION)) {
      error(`Using invalid Minecraft version '${DECOMPILE_VERSION}'`);
      return process.exit(1);
    }

    if (OLD_VERSION && !checkMinecraftVersion(OLD_VERSION)) {
      error(`Using invalid Minecraft version '${OLD_VERSION}'`);
      return process.exit(1);
    }

    if (!fs.existsSync('mache/gradlew')) {
      error('Run git submodule update --init --recursive');
      return process.exit(1);
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
    execSync(`node create-workspace.js ${DECOMPILE_VERSION}`, { stdio }); // automatically create a workspace
    // delete decompiled source as it's taking space
    fs.rmSync('decompiled', { recursive: true });
  } catch (e) {
    console.error(e);
    return process.exit(1);
  }

  process.exit(0);
})();
