#!/bin/bash

while [ true ]
do
	result=$(curl -sk https://sem.sch.bme.hu/van-e_valaki/check.php;)
	if [[ $result == *\"vannake\":true* ]]; then
		say "There is someone in schem";
	fi
	date
	sleep 60;
done
