#!/bin/bash

echo in > /sys/class/gpio/gpio199/direction
echo in > /sys/class/gpio/gpio200/direction
echo in > /sys/class/gpio/gpio204/direction

while true; do

GPIO_VALUE1=`cat /sys/class/gpio/gpio199/value`
GPIO_VALUE2=`cat /sys/class/gpio/gpio200/value`
GPIO_VALUE3=`cat /sys/class/gpio/gpio204/value`

echo $GPIO_VALUE1 $GPIO_VALUE2 $GPIO_VALUE3

sleep 1

done

