# Popcorn time instalation guide for ARMv7

This is an experimental build for Odroid-U3 and Odroid-XU4.

### Known issues
- It doesn't work on Chromebook!
- Some videos/audios can't be decoded yet due to lack of codecs on nw.js.
- When playing with Kodi as external player on Odroid-U3 the OS crashes after a while. Avoid using Kodi as external player on U3!
- ~~It does not load .torrent files.~~

### Instalation guide
- Clone the repository and open a terminal after change into the directory directory:
- Run `chmod +x pt_installer.sh`
- Run `sudo ./pt_installer.sh`

### Uninstalation guide
- Main application:
	- Delete `~/.Popcorn-Time`
	- Delete `~/.local/share/applications/Popcorn-Time.desktop`

- Configuration files and databases:
	- Delete `~/.config/Popcorn-Time`
- Temporary data:
    - Delete `/tmp/Popcorn-Time/`

##### Link to article in Odroid Magazine:
http://magazine.odroid.com/assets/201507/pdf/ODROID-Magazine-201507.pdf

##### Popcorn time building guide for ARMv7:
https://github.com/LeonardLaszlo/popcorn-time-building-guide-armv7