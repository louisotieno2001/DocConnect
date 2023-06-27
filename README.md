# DocConnect
This is a simple app that enables users to contact a doctor when in need by either texting them when the issue is not too emergent and directly contacting the doctor by displaying the routes to where the patent is incase of emmergencies

# Build system setup
This project uses a custom build script powered by apache ant. The build script build.xml utilizes ant plugin system and android sdk build tools for compilation.

## Requirements
- Apache Ant 1.9.16+
- Apache Ivy 2.5.0+
- Android sdk: *build-tools **33.0.2+** , sdk **33**, cmdline-tools*

## Requirement Installation
### Apache ant
Ant can be downloaded from apache [ant binary distribution age](https://ant.apache.org/bindownload.cgi)
Extract the archive downloaded. Add the bin folder to environment PATH variable to allow ant to be used in the commandline. More information on ant setup can be obtained from the official [ant installation Page](https://ant.apache.org/manual/install.html#getBinary) 
### Apache Ivy
Ivy can be downloaded from [apache ivy download page](https://ant.apache.org/ivy/download.cgi)
Extract the archive and copy the resulting jar files to `ANT_HOME/lib` directory. 

### Android sdk
Download the android commandline tools for your corresponding os:
- [Linux](https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip)
- [Mac](https://dl.google.com/android/repository/commandlinetools-mac-9477386_latest.zip)
- [windows](https://dl.google.com/android/repository/commandlinetools-win-9477386_latest.zip)

Extract the dowmloaded archive to a central location easily accessible for later eg `~/android/`.
Export the location of this path to an environmental variable called `ANDROID_HOME` eg in linux
```
  export ANDROID_HOME=/path/to/android/sdk

```
Your android sdk directory should now look like:
```
   android
     |___cmdline_tools
          |__ tools

```
use `sdkmanager` in the `ANDROID_HOME/cmdline_tools/tools/bin/` to install all the required sdk, emulators and buildtools. 
Use 
```
sdkmanager --list
``` 
to list availablle sdk, build-tools and emulators installable in your system.

Install android 33 using 
```
./sdkmanager --install "platforms;android-33"
```
Install android build-tools 
```
./sdkmanager --install "build-tools;33.0.2"
```

After complete successful installation your `ANDROID_HOME` directory should look like.
```
   android
     |___cmdline_tools
     |    |__ tools
     |
     |___build_tools
     |    |__ 33.0.2
     |
     |___platforms
          |__ android-33
     
```

## Compilation
The build script performs incremental compilation on all build steps to avoid recompiling unmodified files.

First setup the plugin. This is a one time initialization process.
```
ant plugin-setup
```

The build steps are as follows.
- Asset and Resource compilation 
  This generates `R.java` files for reference in java. The resources and assets from libraries are precompiled for later incremental builds.
  After compilation `aapt2` links all the resources to produce a `res.apk` file. This is not installable since it does not have `classes.dex`
  ```
       ant aapt2
  ```
- Java classes compilation
  This compiles all .java files to .class files.
  The resulting classes are combined to a jar file.
  ```
       ant compile
  ```
- Dex file compilation.
  This compiles all referenced libraries and app jar files to a `classes.dex` file. Multiple `classes.dex` files may be generated when the main dex file reaches the 64k method limit.
  ```
       ant d8
  ```
- App Bundling
  The `classes.dex` and `res.apk` are combined together to form an unaligned apk archive which is launchable.
  ```
      ant bundle
  ```
- Zip Align 
  The resulting app archive from app bundling is aligned to optimize the zip format for android devices. This happens during bundling phase.

- Apk Signing
  This signs the aligned apk with a given key. If key is not provided default debug key is used which produces a `signed-debug.apk`
  ```
     ant apksign
  ```
- Apk Installing
  This installs the apk through adb. Adb server must be running and the target device connected to enable installation.
  ```
     ant install 
  ```