#!/usr/bin/env bash
set -e # fail if there's any error
set -u

myFile=$1
filename=$(basename -- "$myFile")
extension="${filename##*.}"
filename="${filename%.*}"
pathToFile=$(dirname "$myFile")
ffprobeResult=$(ffprobe "$myFile" -show_format 2>/dev/null)
formatName=$(echo "$ffprobeResult" | awk -F"=" '$1 == "format_name" {print $2}')
echo "File format:" $(echo "$ffprobeResult" | awk -F"=" '$1 == "format_name" {print $2}')", file name:" $(echo "$ffprobeResult" | awk -F"=" '$1 == "filename" {print $2}')
ffmpeg -y -v 0 -fflags +genpts -i "$myFile" -c:v copy -c:a copy "$pathToFile"/"$filename"."mkv"
# only gets here if the conversion didn't fail
# rm "$myFile"
