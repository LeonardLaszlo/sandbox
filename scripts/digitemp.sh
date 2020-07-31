#!/bin/bash
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games"

digitempXml=/home/odroid/.cache/digitemp.xml
digiStats=/home/odroid/.cache/.digitemp_stats.txt
cpuStats=/home/odroid/.cache/.cpu_stats.txt

cpuFrequency=$(</sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq)
cpuTemperature=$(</sys/class/thermal/thermal_zone0/temp)
cpuTemperature=$((cpuTemperature/1000))
cpuFrequency=$((cpuFrequency/1000))

CMD1="$(dmesg | grep s5p_cec | wc -l)"
CMD2="$(dmesg | grep smsc95xx | wc -l)"
CMD3="$(dmesg | grep 'disabled by hub' | wc -l)"
CMD4="$(dmesg | grep s5p-ehci | wc -l)"

# May 11 13:30:45 Sensor 0 C: 25.62 F: 78.12
command="$(cd /home/odroid;digitemp_DS9097 -q -t 0 -c .digitemprc)"
result=${command}
regexp='(([a-zA-Z]+) ([0-9]+) (([0-9]+):([0-9]+):([0-9]+)) Sensor [0-9]+ C: ([0-9]+\.[0-9]+) F: ([0-9]+\.[0-9]+))'
[[ $result =~ $regexp ]]
month=${BASH_REMATCH[2]}
day=${BASH_REMATCH[3]}
time=${BASH_REMATCH[4]}
hour=${BASH_REMATCH[5]}
minute=${BASH_REMATCH[6]}
second=${BASH_REMATCH[7]}
celsius=${BASH_REMATCH[8]}
fahrenheit=${BASH_REMATCH[9]}

updateStats() {
   if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]
   then
     return 0
   else
     	if [ -f "$1" ]
	then
		# read statistics from file
		i=1
		while read -r line
		do
			if [ $i -lt $2 ]
			then
				STATS[$i]=$line
				i=$[$i+1]
			else
				# k in {1..$[$2-2]}
				for (( k=1; k<=$[$2-2]; k++ ))
				do
					STATS[$k]=${STATS[$[$k+1]]}
				done
				STATS[$[$2-1]]=$line
			fi
		done < $1
		
		# add new statistic
		STATS[$i]="{ X: \""$3"\", Y: $4 },"
		
		# write new statistics to file
		j=1
		while [ $j -lt $[$i+1] ]
		do
			if [ $j -eq 1 ]
			then
				echo ${STATS[$j]} > "$1"
			else
				echo ${STATS[$j]} >> "$1"
			fi
			j=$[$j+1]
		done
	else
	    echo "{ X: \""$3"\", Y: $4 }," > "$1"
	fi
   fi
}

if [ "$minute" == "00" ]
then
	updateStats $digiStats 24 $hour $celsius
fi

updateStats $cpuStats 30 $minute $cpuTemperature

data="<?xml version='1.0' standalone='yes'?>
<data>
	<cpu>
		<frequency>${cpuFrequency}</frequency>
		<temperature>${cpuTemperature}</temperature>
	</cpu>
	<environment>
		<temperature>$celsius</temperature>
		<time>$time</time>
		<date>$month $day</date>
	</environment>
	<s5p_cec>${CMD1}</s5p_cec>
	<smsc95xx>${CMD2}</smsc95xx>
	<disabledbyhub>${CMD3}</disabledbyhub>
	<s5p_ehci>${CMD4}</s5p_ehci>
</data>"

echo "$data" > "$digitempXml"
