/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

require('dotenv').config();
const fs = require('fs');
const { execSync } = require('child_process');
const { log, warn, error, stdio } = require('../config');

const args = process.argv.slice(2);

const MAVEN_URL = args[0] ?? process.env.MAVEN_URL;
const MAVEN_USER = args[1] ?? process.env.MAVEN_USER;
const MAVEN_PASSWORD = args[2] ?? process.env.MAVEN_PASSWORD;

function checkURL() {
  try {
    const checkedURL = new URL(MAVEN_URL);
    if (checkedURL.protocol == 'http:') {
      error('Using insecure protocols is not allowed. Use HTTPS!');
      return process.exit(1);
    }
  } catch (e) {
    if (e.code == 'ERR_INVALID_URL') {
      error(`Invalid URL found '${MAVEN_URL}'`);
    } else {
      error('An unknown error occured!');
      console.error(e);
    }

    return process.exit(1);
  }
}

(async function () {
  if (!fs.existsSync('Dish-API') || !fs.existsSync('Dish-API/.gitignore')) return error('Could not find Dish-API. Make sure to run \'git submodule update --init --recursive\'!');
  if (!MAVEN_URL || !MAVEN_USER || !MAVEN_PASSWORD) return error(`Missing maven${!MAVEN_URL ? ' url' : ''}${!MAVEN_USER ? ' username' : ''}${!MAVEN_PASSWORD ? ' password' : ''}`);
  checkURL();
  try {
    if (process.platform !== 'win32') {
      execSync(`cd Dish-API && chmod +x gradlew`, { stdio }); // for linux, you will need to make gradlew executable
      execSync(`cd Dish-API && ./gradlew publish -PMAVEN_URL="${MAVEN_URL}" -PMAVEN_USER="${MAVEN_USER}" -PMAVEN_PASSWORD="${MAVEN_PASSWORD}"`, { stdio });
    } else {
      execSync(`cd Dish-API && gradlew publish -PMAVEN_URL="${MAVEN_URL}" -PMAVEN_USER="${MAVEN_USER}" -PMAVEN_PASSWORD="${MAVEN_PASSWORD}"`, { stdio });
    }
    // really hacky way of getting the version info
    const API_VERSION = (fs.readFileSync('Dish-API/build.gradle').toString()).split(`version = '`)[1].split('\'')[0];
    const LAST_UPDATED = /\<timestamp\>.*\<\/timestamp\>/g;
    const BUILD_NUMBER = /\<buildNumber\>.*\<\/buildNumber\>/g;
    const metadata = await (await fetch(`${MAVEN_URL}/net/ouja/api/${API_VERSION}/maven-metadata.xml`)).text();
    // regex the metadata generated when uploading Dish-API
    const exec = LAST_UPDATED.exec(metadata);
    if (!exec) return warn('Could not automatically grab version!');
    const bExec = BUILD_NUMBER.exec(metadata);
    const VERSION = 'api-' + API_VERSION.replace('-SNAPSHOT', '') + '-' + exec[0].split('>')[1].split('<')[0] + '-' + (bExec ? bExec[0].split('>')[1].split('<')[0] : '1');
    const MC_VERSION = API_VERSION.split('-R')[0];
    const jsonFormat = {
      "version": MC_VERSION,
      "api_version": API_VERSION.split(MC_VERSION + '-')[1],
      "libraries": [
        // TODO: Find a way to set the static libraries (that shouldn't change hardly at all)
        //       I want to be able to edit *one* place
        {
          "downloads": {
            "url": "https://repo1.maven.org/maven2/org/yaml/snakeyaml/2.0/snakeyaml-2.0.jar",
            "path": "org/yaml/snakeyaml/2.0/snakeyaml-2.0.jar"
          },
          "name": "org.yaml:snakeyaml:2.0"
        },
        {
          "downloads": {
            "url": `https://maven.ouja.net/net/ouja/api/${API_VERSION}/${VERSION}.jar`,
            "path": `net/ouja/api/${MC_VERSION}/${API_VERSION}.jar`
          },
          "name": `net.ouja:api:${API_VERSION}`
        }
      ]
    };
    fs.writeFileSync(`dish/libraries/${MC_VERSION}.json`, JSON.stringify(jsonFormat, null, 2.5));
    // README hack
    const README = fs.readFileSync('README.md').toString();
    const gradleRegex = /compileOnly\(\".*\"\)/g;
    const gradleExec = gradleRegex.exec(README)[0];
    const gradleVersion = gradleExec ? gradleExec.split('compileOnly("net.ouja:api:')[1].split('")')[0] : null;
    fs.writeFileSync('README.md', README.replace(new RegExp(gradleVersion, 'g'), API_VERSION));
    process.exit(0);
  } catch (e) {
    return process.exit(1);
  }
})();