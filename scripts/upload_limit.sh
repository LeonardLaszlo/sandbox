#!/bin/bash
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"

upload=`wget -qO- https://netwatcher.sch.bme.hu | sed -rn 's/\s*([0-9]*)\.[0-9] GB(.*?)/\1/p'`

if [ -z $upload ]; then
	upload=`wget -qO- https://netwatcher.sch.bme.hu | sed -rn 's/\s*([0-9]*)\.[0-9] MB(.*?)/\1/p'`
	if [ -n $upload ]; then
		upload=`wget -qO- https://netwatcher.sch.bme.hu | sed -rn 's/\s*([0-9]*)\.[0-9] MB(.*?)/\1/p'`
		echo "[" `date` "] upload = " $upload "MB!" &>> /home/odroid/Log/log.log
	else
		echo "[" `date` "] null az upload értéke!" &>> /home/odroid/Log/log.log
	fi
else
	if [ $upload -gt 20 ]; then
		echo "[" `date` "] upload = " $upload "GB!" &>> /home/odroid/Log/log.log
		killall transmission-gtk &>> /home/odroid/Log/log.log
		wget -qO- https://netwatcher.sch.bme.hu &>> /home/odroid/Log/log.log
	else
		echo "[" `date` "] upload = " $upload "GB!" &>> /home/odroid/Log/log.log
	fi
fi

