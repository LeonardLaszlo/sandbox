#!/bin/bash
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"

# File declaration
file=/var/www/html/data.json
content=$(cat "$file")
echo $content 

key="CPUTemperatureStatistics"
re="\"($key)\":\s*\[\s*([^]\[]*?)\s*\]"

if [[ $content =~ $re ]]; then
	value="${BASH_REMATCH[2]}"
fi

while IFS= read
do
    echo $REPLY
	re="\"X\":\s\"([0-9]*)\",\s\"Y\":\s\"([0-9];*)\""

	if [[ $REPLY =~ $re ]]; then
		x="${BASH_REMATCH[1]}"
		y="${BASH_REMATCH[2]}"
		echo -e "\\nx: $x"
		echo -e "\\ny: $y"
	fi
done <<< "$value"


