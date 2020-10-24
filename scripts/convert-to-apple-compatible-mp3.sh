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
if [ "mov,mp4,m4a,3gp,3g2,mj2" == "$formatName" ]; then
  echo "$ffprobeResult" | awk -F"=" '$1 == "filename" {print $2}'
  ffmpeg -y -v 0 -i "$myFile" -acodec libmp3lame -ab 192k "$pathToFile"/temp."$extension"
  # only gets here if the conversion didn't fail
  mv "$pathToFile"/temp."$extension" "$pathToFile"/"$filename"."$extension"
fi
