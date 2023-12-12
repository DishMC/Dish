# DishMC

DishMC adds plugin support with it's own API to the Minecraft: Java Edition's servers. It is maintained by [iris](https://github.com/KaiAF).

How the update process works. Update to the recent snapshot, do the changes and once the full release is out, update to the full release, then backport the changes to the last major supported version.

**DISCLAIMER:** This is no where near being ready for actual use, and will likely be refactored many times.

## Bug Reports & Suggestions

If you had found a bug and wish to report it, please do so by opening an issue [in the issues tab](https://github.com/DishMC/Dish/issues). Please provide all the information you can when opening an issue.

If you wish to suggest a change, or a feature, you can do so by opening an issue [in the issues tab](https://github.com/DishMC/Dish/issues).

### Plugin Developers

Our API is open source and can be viewed [here](https://github.com/DishMC/Dish-API)

Gradle

```java
repositories {
  mavenCentral()
  maven { url = 'https://maven.ouja.net' }
}

dependencies {
  compileOnly("net.ouja:api:1.20.4-R0.0-SNAPSHOT")
  implementation 'it.unimi.dsi:fastutil:8.5.12'
}
```

Maven

```xml
<repository>
  <id>ouja</id>
  <url>https://maven.ouja.net</url>
</repository>

<dependency>
  <groupId>net.ouja</groupId>
  <artifactId>api</artifactId>
  <version>1.20.4-R0.0-SNAPSHOT</version>
  <scope>provided</scope>
</dependency>
<dependency>
  <groupId>it.unimi.dsi</groupId>
  <artifactId>fastutil</artifactId>
  <version>8.5.12</version>
  <scope>provided</scope>
</dependency>
```

#### Contributing

See [contributing](CONTRIBUTING.md)

#### Compiling

To compile the server, you will need to run the command `node dish/compile.js`.
