#!/usr/bin/env bash
video_converter_script="$(dirname $0)/convert-video-to-mkv.sh"
find "$1" -type f -name '*.avi' -exec "$video_converter_script" {} \;
find "$1" -type f -name '*.mpg' -exec "$video_converter_script" {} \;
find "$1" -type f -name '*.mp4' -exec "$video_converter_script" {} \;
find "$1" -type f -name '*.webm' -exec "$video_converter_script" {} \;
find "$1" -type f -name '*.flv' -exec "$video_converter_script" {} \;
find "$1" -type f -name '*.mkv' -exec "$video_converter_script" {} \;
