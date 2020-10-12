#!/usr/bin/env bash
set -e # fail if there's any error
set -u

my_file=$1
my_new="$(echo $(dirname "$my_file")/$(basename "$my_file").m4a)"
echo "$my_file"
ffmpeg -y -v 0 -i "$my_file" -acodec alac "$my_new"
# only gets here if the conversion didn't fail
# rm "$my_file"
