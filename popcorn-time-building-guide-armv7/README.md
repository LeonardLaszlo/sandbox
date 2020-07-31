# Popcorn time building guide for ARMv7

This is an experimental building guide for Odroid-U3 and Odroid-XU4!

THIS GUIDE WILL NOT WORK BECAUSE THE POPCORN TIME PROJECT IS NOT AVAILABLE ANYMORE AT `https://git.popcorntime.io/popcorntime/desktop.git`

### Known issues
- It doesn't work on Chromebook!
- ~~It does not load .torrent files.~~
- Some videos/audios can't be decoded yet due to lack of codecs on nw.js.

### Why is Popcorn Time not available for ARMv7 linux?
The main reason why Popcorn Time is not supported officially is because it's framework called nw.js is not released officially for ARMv7 architecture. Anyway some unofficial builds are available on forums over the internet. The following tutorial explains how you can build Popcorn Time for linux ARMv7 architecture.

### Building guide

#### Clone git repository and open a terminal in the new directory! After run:
- `sudo apt-get update`
- `sudo apt-get install nodejs-legacy npm`
- `mkdir workspace`
- copy `make_popcorn.sh` to workspace directory
- `cd workspace`
- `chmod +x make_popcorn.sh`
- execute `./make_popcorn.sh '-b v0.3.8-5 https://git.popcorntime.io/popcorntime/desktop.git'` and accept default settings
- `cd popcorn-app`

#### Change building script and node-webkit-builder
- override files `~/workspace/popcorn-app/Gruntfile.js` and `~/workspace/popcorn-app/node_modules/grunt-node-webkit-builder/tasks/node_webkit_builder.js`

#### Build and run application
Execute in terminal:
- in popcorn-app directory run: `grunt build` to build Popcorn-Time
- run `grunt start` to start the application
- optionally run `grunt package` to create a Package in your build directory.

##### Instalation guide
https://github.com/LeonardLaszlo/popcorn-time-installation-guide-armv7

##### Link to article in Odroid Magazine:
http://magazine.odroid.com/assets/201507/pdf/ODROID-Magazine-201507.pdf

##### Kodi as external player:
https://github.com/LeonardLaszlo/popcorn-time-building-guide-armv7/blob/master/KODI.md

##### VPN just for pocorntime:
https://github.com/LeonardLaszlo/popcorn-time-building-guide-armv7/blob/master/VPN.md

