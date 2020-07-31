### Kodi as external player 

(Thanks to gripped! NB.: Since v0.3.8-5 I added kodi as external player in the instalation package, so you don't have to recompile it!)

(Obviously you need a working kodi!)

To get it to work you first need to go into the popcorn time settings and set it to always stream on the same port. I chose 51234

Then create a file somewhere named stream.strm with the following in it.

`http://127.0.0.1:51234/`

For the next step there are two ways.

##### One (harder but better)
Recompile popcorntime with this 
```
        'kodi': {
            type: 'kodi',
            switches: '/LOCATION/OF/stream.strm',
            subswitch: '',
            fs: '',
        },
```
added to the players section of desktop.git/src/app/lib/device/ext-player.js

If you copy external-kodi-icon.png to desktop.git/src/app/images/icons/ you'll also have the correct icon.
Rebuild using this guide: https://github.com/LeonardLaszlo/popcorn-time-building-guide-armv7

Then kodi appears in the list of external players.

##### Two (easier)
Choose one of the supported external players that you DON'T have on your system eg mpv, mplayer, vlc, smplayer
create a file with that name in /usr/local/bin, make it executable and put this in it

```
#!/bin/bash
/usr/lib/kodi/kodi.bin /LOCATION/OF/stream.strm
```

Obviously the wrong program name shows up in the list the second way.

I find the streams much more watchable in Kodi

