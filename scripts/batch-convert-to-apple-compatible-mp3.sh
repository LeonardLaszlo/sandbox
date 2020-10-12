#!/usr/bin/env bash
my_bin="$(dirname $0)/convert-to-apple-compatible-mp3.sh"
find "$1" -type f -name '*.mp3' -exec "$my_bin" {} \;
