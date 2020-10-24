#!/usr/bin/env bash
set -e # fail if there's any error
set -u

my_file=$1
my_new="$(dirname "$my_file")/$(basename "$my_file").mp3"
echo "$my_file"
ffmpeg -y -v 0 -i "$my_file" -ab 320k -map_metadata 0 -id3v2_version 3 "$my_new"
# only gets here if the conversion didn't fail
rm "$my_file"
