/**
 * Copyright (c) Ouja and contributors
 * LGPL-3.0-or-later
 */

const fs = require('fs');
const crypto = require('crypto');
const { execSync } = require('child_process');

const { error, log, warn, deleteDir, deleteFile } = require('../config');

const stdio = [process.stdin, process.stdout, process.stderr];

(async function () {
  if (!fs.existsSync('dish/workspace')) {
    error('Missing workspace');
    return process.exit(1);
  }

  const version = fs.readFileSync('dish/workspace/src/main/java/net/minecraft/DetectedVersion.java').toString().split('this.name = "')[1].split('"')[0];
  log(`Found version '${version}'`);
  if (!fs.existsSync(`cache/${version}`)) {
    error(`Missing cache for version '${version}'`);
    return process.exit(1);
  }
  if (!fs.existsSync('Dish-Bundler')) {
    error('Dish-Bundler not found');
    return process.exit(1);
  }
  if (fs.existsSync('Dish-Bundler/build')) {
    warn('Deleting build directory. This may take a few seconds...');
    deleteDir('Dish-Bundler/build');
    log('Deleted build directory');
  }
  if (fs.existsSync('Dish-Bundler/libs')) {
    warn('Deleting libraries inside Dish-Bundler');
    deleteDir('Dish-Bundler/libs');
    log('Deleted libraries');
  }
  if (fs.existsSync('dish/workspace/build')) {
    warn('Deleting build directory');
    deleteDir('dish/workspace/build');
    log('Deleted build directory');
  }
  console.time('Building Jar');
  // Linux wants the gradlew file to be executable, and to run it with ./ in the beginning. Windows does not do this.
  if (process.platform !== 'win32') {
    execSync(`cd dish/workspace && chmod +x gradlew`, { stdio }); // for linux, you will need to make gradlew executable
    execSync(`cd dish/workspace && ./gradlew tasks generateVer`, { stdio });
    execSync(`cd dish/workspace && ./gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  } else {
    execSync(`cd dish/workspace && gradlew tasks generateVer`, { stdio });
    execSync(`cd dish/workspace && gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  }
  console.timeEnd('Building Jar');
  console.time('Remapping server');
  remapServer(version);
  console.timeEnd('Remapping server');
  fs.mkdirSync(`Dish-Bundler/libs/versions/${version}`, { recursive: true });
  fs.mkdirSync('Dish-Bundler/libs/libraries', { recursive: true });
  fs.mkdirSync('Dish-Bundler/libs/META-INF', { recursive: true });
  fs.copyFileSync(`server-${version}-dirty.jar`, `Dish-Bundler/libs/versions/${version}/server-${version}.jar`);
  deleteFile(`server-${version}-dirty.jar`);
  readLibraries(version);
  readVersions(version);
  createClassPath(version);
  fs.writeFileSync('Dish-Bundler/libs/META-INF/main-class', 'net.minecraft.server.Main');
  // Linux wants the gradlew file to be executable, and to run it with ./ in the beginning. Windows does not do this.
  if (process.platform !== 'win32') {
    execSync(`cd Dish-Bundler && chmod +x gradlew`, { stdio }); // for linux, you will need to make gradlew executable
    execSync(`cd Dish-Bundler && ./gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  } else {
    execSync(`cd Dish-Bundler && gradlew build`, { stdio }); // build to make sure it works. Will error if there are decompile errors
  }
  fs.renameSync('Dish-Bundler/build/libs/bundler-0.1-SNAPSHOT.jar', 'Dish-Bundler/build/libs/server.jar');
  process.exit(0);
})();

function remapServer(version) {
  let mappings = fs.readFileSync(`cache/${version}/mappings.txt`).toString();
  mappings = mappings.replace('net.minecraft.Util$10 -> ac$2:', 'net.minecraft.Util$2 -> ac$2:');
  mappings = mappings.replace('net.minecraft.Util$11 -> ac$3:', 'net.minecraft.Util$3 -> ac$3:');
  mappings = mappings.replace('net.minecraft.Util$2 -> ac$4:', 'net.minecraft.Util$4 -> ac$4:');
  fs.writeFileSync(`cache/${version}/mappings.txt`, mappings);
  log('Remapped mappings');
  const jarName = fs.readdirSync('dish/workspace/build/libs').filter(f => f.startsWith('DishMC-' + version) && f.endsWith('.jar'))[0];
  if (!jarName) return error(`Could not find jar '${jarName}'`);
  execSync(`java -jar libs/specialsource.jar -i dish/workspace/build/libs/${jarName} -o server-${version}-dirty.jar -m cache/${version}/mappings.txt --reverse`, { stdio });
}

function readLibraries(version) {
  log('Reading libraries');
  let libraries_list = '';
  if (!fs.existsSync(`cache/${version}.json`)) return error('Could not find dependencies json file!');
  const version_meta = JSON.parse(fs.readFileSync(`cache/${version}.json`).toString());
  const dish_meta = JSON.parse(fs.readFileSync(`dish/libraries/${version}.json`).toString());
  /**
   * Minecraft Libraries
   */
  version_meta.libraries.forEach(function (l) {
    const artifact = l.downloads.artifact;
    if (!fs.existsSync(`cache/${version}/META-INF/libraries/${artifact.path}`)) return;
    libraries_list = libraries_list + (libraries_list.length > 0 ? '\n' : '') + generateHash(`cache/${version}/META-INF/libraries/${artifact.path}`) + '	' + l.name + '	' + artifact.path;
    const pathNoFile = artifact.path.split('/').slice(0, artifact.path.split('/').length - 1).join('/');
    fs.mkdirSync(`Dish-Bundler/libs/libraries/${pathNoFile}`, { recursive: true });
    fs.cpSync(`cache/${version}/META-INF/libraries/${pathNoFile}`, `Dish-Bundler/libs/libraries/${pathNoFile}`, { recursive: true });
  });
  /**
   * Dish libraries
   */
  dish_meta.libraries.forEach(function (l) {
    const artifact = l.downloads;
    const pathNoFile = artifact.path.split('/').slice(0, artifact.path.split('/').length - 1).join('/');
    if (!fs.existsSync(`cache/${version}/META-INF/libraries/${artifact.path}`)) {
      warn(`${l.name} isn't installed. Downloading...`);
      fs.mkdirSync(`cache/${version}/META-INF/libraries/${pathNoFile}`, { recursive: true });
      execSync(`curl -o cache/${version}/META-INF/libraries/${artifact.path} ${artifact.url}`, { stdio });
      log(`Downloaded ${l.name}`);
    }
    libraries_list = libraries_list + (libraries_list.length > 0 ? '\n' : '') + generateHash(`cache/${version}/META-INF/libraries/${artifact.path}`) + '	' + l.name + '	' + artifact.path;
    fs.mkdirSync(`Dish-Bundler/libs/libraries/${pathNoFile}`, { recursive: true });
    fs.copyFileSync(`cache/${version}/META-INF/libraries/${artifact.path}`, `Dish-Bundler/libs/libraries/${artifact.path}`);
  });
  fs.writeFileSync('Dish-Bundler/libs/META-INF/libraries.list', libraries_list);
  log('Created libraries.list');
}

function readVersions(version) {
  log('Reading version: ' + version);
  fs.writeFileSync('Dish-Bundler/libs/META-INF/versions.list', generateHash(`Dish-Bundler/libs/versions/${version}/server-${version}.jar`) + '	' + version + '	' + `${version}/server-${version}.jar`);
  log('Created versions.list');
}

function createClassPath(version) {
  log('Creating classpath-joined');
  const version_meta = JSON.parse(fs.readFileSync(`cache/${version}.json`).toString());
  const libraries = version_meta.libraries.map(v => 'libraries/' + v.downloads.artifact.path);
  libraries.push(`versions/${version}/server-${version}.jar`);
  fs.writeFileSync('Dish-Bundler/libs/META-INF/classpath-joined', libraries.join(';'));
  log('Created classpath-joined');
}

/**
 * Don't use calculateFileHash has this requires sha256
 */
function generateHash(file) {
  return crypto.createHash('sha256').update(fs.readFileSync(file)).digest('hex');
}
