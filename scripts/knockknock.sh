#!/bin/bash

knockknock() {
    if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ] || [ -z "$5" ]
    then
        return 0
    else
        url=$1
        porto=$2
        seq1=$3
        seq2=$4
        seq3=$5
        
        read -p "Knock port $porto?" -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]
        then
            knock $url $porto $seq1 $seq2 $seq3
        fi
    fi
}

knockknock laslaul.ddns.net 22 9 5 1990
knockknock laslaul.ddns.net 13389 9 5 1990
knockknock laslaul.ddns.net 5900 9 5 1990
knockknock laslaul.ddns.net 80 9 5 1990
knockknock laslaul.ddns.net 8080 9 5 1990
knockknock laslaul.ddns.net 443 9 5 1990
