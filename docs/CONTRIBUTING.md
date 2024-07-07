# Contributing to Dish

This is a person project of mine. This does not mean I will be unhappy about people who want to contribute to this project. It is very welcomed. Below is the process that should be taken to make this project look clean and smooth.

## Requirements

What you will need to contribute:

- [Java](https://adoptium.net/), version 17 or higher.
- [Git](https://git-scm.com/downloads).
- [Node.js](https://nodejs.org/en) and npm.

Now that you have the required stuff installed, what you will need to do is clone Dish and run the following command `npm install`. Once the npm packages are installed, run the command `git submodule update --init --recursive`.

Once everything is ready, run the command `node init.js`. Then, hopefully, no patches get rejected, then you can run the command `node dish`. After everything is set up, you can go to the directory `~/dish/workspace` and modify the Minecraft or Dish code.

### Patches

DishMC relies on patching into Minecraft's source code. They are located in seperate directories based on the build type and version. For full releases, they are found in `~/patches/release/` and snapshots are in the snapshot folder. Applying patches can be run in it's own command, but usually, it is ran automatically when initilizing the project. You can run the command `node patches/applyPatches.js <build_type>/<version>`.

When modifying a Minecraft file, always mark it with `// dish -- <reason, if needed>`. Try to reduce the amount of patch work by trying to not modify things that shouldn't need to be modified. Like, changing a variable name unless if its needed.

#### Generating Patches

When you are finished making the changes, run the command in the home directory `node patches/generatePatches.js <build_type>/<version>`.

#### Updating to a new version

If you wish to update Dish to a new version that has been recently released, run the command `node dish/index.js <new_build_type>/<new_version> <old_build_type>/<old_version> <(true | false) -- If true, it will ignore the cache>`. This will very likely cause an error, which means you will need to fix the rejected patches or new files that have new decompile errors. This can be done by going to the workspaces directory and the new version's sub directory. Opening it in the IDE of your choice, then, fix the errors and run `node patches/generatePatches <build_type>/<version>`.

##### Publishing

You can either create a `.env` file and add these keys: `MAVEN_URL=<URL>`, `MAVEN_USER=<USER>`, and `MAVEN_PASSWORD=<PASSWORD>`, then run the command `node dish/upload-api-to-maven.js` OR just run the command `node dish/upload-api-to-maven.js <URL> <USER> <PASSWORD>`
