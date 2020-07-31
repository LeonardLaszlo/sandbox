#!/bin/sh
for i in `ps ax | grep kodi.bin | grep -v grep | sed 's/ *//' | sed 's/[^0-9].*//'`
do
  kill -9 $i
done
