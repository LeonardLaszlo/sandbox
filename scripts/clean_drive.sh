#!/bin/bash
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"

declare -a locationArray=(
# /media/seagate
/media/Seagate1TB
)

declare -a filePatternArray=(
"._*"
".DS_Store"
"desktop.ini"
"Thumbs.db"
".Spotlight*"
"__MACOSX"
".TemporaryItems"
".Trash-*"
".Trashes"
"Folder.jpg"
"AlbumArt*"
)

for i in "${locationArray[@]}"
do
	log=$i/.clean.log
	
	if [ ! -f $log ]; then
		touch $log
		echo "Log file $log successfully created!"
	else
		# Add new line at the end of file
		echo -e "\n\n" &>> $log
	fi
	
	echo "[" `date` "]"
	echo "Location: $i"
	echo "[" `date` "]" &>> $log
	echo "Location: $i" &>> $log
	
	for j in "${filePatternArray[@]}"
	do
		echo "Pattern: $j"
		echo "Pattern: $j" &>> $log
		find $i -name "$j" -print0 | xargs -0 rm -rfv &>> $log
	done
done
