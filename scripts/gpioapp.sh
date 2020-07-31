#!/bin/bash

echo out > /sys/class/gpio/gpio199/direction
echo out > /sys/class/gpio/gpio200/direction
echo out > /sys/class/gpio/gpio204/direction

aludj () {
if [ -z "$1" ]
then
	return 0
else
	res=$(echo "-0.05*$1" | bc -l)
	res=$(echo "$res+4.5" | bc -l)
	sleep $res >> /dev/null
fi
}

while true; do
cpuTemperature=$(</sys/class/thermal/thermal_zone0/temp)
cpuTemperature=$((cpuTemperature/1000))

echo 0 > /sys/class/gpio/gpio204/value
echo 1 > /sys/class/gpio/gpio199/value
aludj $cpuTemperature

echo 1 > /sys/class/gpio/gpio204/value
echo 0 > /sys/class/gpio/gpio199/value
aludj $cpuTemperature

done

