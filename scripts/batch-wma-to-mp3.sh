#!/usr/bin/env bash
my_bin="$(dirname "$0")/wma-to-mp3.sh"
find "$1" -type f -name '*.wma' -exec "$my_bin" {} \;
