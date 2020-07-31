#!/bin/bash

ODROIDHOME=/home/odroid
BACKUP=/media/odroid/Kingstone/bak

# file locations
declare -a FILEARRAY=($BACKUP/rc.local
$BACKUP/knockd.conf
$BACKUP/knockd
$BACKUP/xrdp.ini
$BACKUP/default-ssl.conf
$BACKUP/client.conf
$BACKUP/daemon.conf
$BACKUP/pulseaudio.conf
$BACKUP/smb.conf
$BACKUP/50-default.conf
$BACKUP/motion.conf
$BACKUP/clean_seagate.sh
$BACKUP/fstab
$BACKUP/x11vnc.pass
$BACKUP/x11vnc.conf)

# file destination locations
declare -a FILEDESTINATIONARRAY=(/etc/rc.local
/etc/knockd.conf
/etc/default/knockd
/etc/xrdp/xrdp.ini
/etc/apache2/sites-available/default-ssl.conf
/etc/pulse/client.conf
/etc/pulse/daemon.conf
/etc/init/pulseaudio.conf
/etc/samba/smb.conf
/etc/rsyslog.d/50-default.conf
/home/odroid/.motion/motion.conf
/etc/cron.weekly/clean_seagate.sh
/etc/fstab
/etc/x11vnc.pass
/etc/init/x11vnc.conf)

# diretory locations
declare -a DIRECTORYARRAY=( $BACKUP/transmission/
$BACKUP/html/
$BACKUP/.ssh/)

# diretory destination locations
declare -a DIRECTORYDESTINATIONARRAY=(/home/odroid/.config/transmission/
/var/www/html/
/home/odroid/.ssh/)

# SVN
SVNHOMEPATH=/home/svn
SVNPROJECT=/home/svn/java_projects

# Check for superuser privileges
if [[ $UID != 0 ]]; then
	echo "Please run this script with sudo:"
	echo "sudo $0 $*"
	exit 1
fi

# disable root login
read -p "Do you want disable root login?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	passwd -l root
fi

# update, upgrade apt-get
read -p "Update package indexes and upgrade installed packages?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	dpkg --configure -a
	apt-get update
	apt-get upgrade
	dpkg --configure -a
fi

# install common packages
read -p "Do you want to install common packages?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	apt-get install sysstat ntfs-3g exfat-fuse exfat-utils
fi

# install openjdk 7
read -p "Install openjdk 7? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	apt-get install openjdk-7-jdk
fi

# install xrdp
read -p "Install and configure xrdp on lxde? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	echo "lxsession -s LXDE -e LXDE" > /home/odroid/.xsession
	apt-get install lxde xrdp
fi

# install iptables and knock daemon plus configuring port knocking
read -p "Install iptables and knock daemon plus configuring port knocking? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
        apt-get install iptables iptables-persistent knockd
	iptables -P INPUT DROP
	iptables -P FORWARD DROP
	iptables -I INPUT 1 -i lo -j ACCEPT
	iptables -A INPUT -s 192.168.1.0/24 -j ACCEPT
	iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT
	/etc/init.d/iptables-persistent save
fi

# install and configure apache2, php5, and svn
# ssl tutorial: https://www.digitalocean.com/community/tutorials/how-to-create-a-ssl-certificate-on-apache-for-ubuntu-14-04
# subversion tutorial: https://help.ubuntu.com/community/Subversion
read -p "Install and configure apache2, php5, and svn?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	apt-get install apache2 php5 subversion
	a2enmod ssl
	service apache2 restart
	mkdir /etc/apache2/ssl
	openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/apache2/ssl/apache.key -out /etc/apache2/ssl/apache.crt
	# SSLCONF=/etc/apache2/sites-available/default-ssl.conf
	# cp $SSLCONF $SSLCONF.bak
	# cat $BACKUP/default-ssl.conf > $SSLCONF
	a2ensite default-ssl.conf
	service apache2 reload
	# APACHEPATH=/var/www/html
	# INDEX=/var/www/html/index.html
	# mv $INDEX $INDEX.bak
	# cp $BACKUP/.htaccess $APACHEPATH
	# cp $BACKUP/index.php $APACHEPATH
	# cp $BACKUP/volume.php $APACHEPATH
	# cp $BACKUP/jquery-1.6.1.min.js $APACHEPATH
	# chmod 644 $APACHEPATH/.htaccess
	# chmod 644 $APACHEPATH/index.php
	# chmod 644 $APACHEPATH/volume.php
	# chmod 644 $APACHEPATH/jquery-1.6.1.min.js
	# service apache2 reload
	addgroup subversion
	adduser odroid subversion
	adduser www-data subversion
	mkdir $SVNHOMEPATH
	mkdir $SVNPROJECT
	svnadmin create $SVNPROJECT
        chown -R www-data:subversion $SVNPROJECT
        chmod -R g+rws $SVNPROJECT
fi

# load svn dump
# http://www.if-not-true-then-false.com/2012/svn-subversion-backup-and-restore/
read -p "Load SVN dump?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	svnadmin load $SVNPROJECT < $BACKUP/java_projects.dump
fi

# install and initialize digitemp
read -p "Install and initialize digitemp?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	apt-get install digitemp
	cd $ODROIDHOME;digitemp_DS9097 -i -s /dev/ttyUSB0
	digitemp_DS9097 -q -t 0 -c $ODROIDHOME/.digitemprc
fi

# create a network share via Samba using the CLI (Command-line interface/Linux Terminal)
# https://help.ubuntu.com/community/How%20to%20Create%20a%20Network%20Share%20Via%20Samba%20Via%20CLI%20(Command-line%20interface/Linux%20Terminal)%20-%20Uncomplicated,%20Simple%20and%20Brief%20Way!

read -p "Create a network share via Samba using the CLI (Command-line interface/Linux Terminal)?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	apt-get install samba
	smbpasswd -a odroid
	# SMBCONF=/etc/samba/smb.conf
	# cp $SMBCONF $SMBCONF.bak
	# cat $BACKUP/smb.conf > $SMBCONF
	# service smbd restart
	testparm
	echo 'To access your network share use your username (<user_name>) and password through the path "smb://<HOST_IP_OR_NAME>/<folder_name>/" (Linux users) or "\\<HOST_IP_OR_NAME>\<folder_name>\" (Windows users). Note that "<folder_name>" value is passed in "[<folder_name>]", in other words, the share name you entered in "/etc/samba/smb.conf".'
fi

# copy transmission config
# read -p "Do you want to copy transmission config?" -n 1 -r
# echo
# if [[ $REPLY =~ ^[Yy]$ ]]
# then
#         cp -ar $BACKUP/transmission /home/odroid/.config
# 	chmod -R 755 /home/odroid/.config/transmission
# fi

# copy system log configuration
# read -p "Do you want to copy system log configuration?" -n 1 -r
# echo
# if [[ $REPLY =~ ^[Yy]$ ]]
# then
#         mv /etc/rsyslog.d/50-default.conf /etc/rsyslog.d/50-default.conf.bak
#         cat $BACKUP/50-default.conf > /etc/rsyslog.d/50-default.conf
# fi


# start pulseaudio in system mode
# http://askubuntu.com/questions/192522/how-do-i-configure-sound-with-pulseaudio-and-multiseat
read -p "Do you want to start pulseaudio in system mode?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	# PULSECLIENTCONF=/etc/pulse/client.conf
	# PULSEDAEMONCONF=/etc/pulse/daemon.conf
	# PULSEAUDIOCONF=/etc/init/pulseaudio.conf
        # cp $PULSECLIENTCONF $PULSECLIENTCONF.bak
	# cp $PULSEDAEMONCONF $PULSEDAEMONCONF.bak
	# cp $PULSEAUDIOCONF $PULSEAUDIOCONF.bak
	# cat $BACKUP/client.conf > $PULSECLIENTCONF
	# cat $BACKUP/daemon.conf > $PULSEDAEMONCONF
	# cat $BACKUP/pulseaudio.conf > $PULSEAUDIOCONF
	deluser odroid audio
	usermod -a -G pulse-access odroid
	usermod -a -G pulse-access www-data
	fgrep -ie 'audio' /etc/group
	fgrep -ie 'pulse-access' /etc/group
	cp /etc/pulse/default.pa /etc/pulse/default.pa.bak
fi


# install motion
read -p "Do you want to install motion?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	# MOTIONCONF=/home/odroid/.motion/motion.conf
	apt-get install motion
	mkdir ~/.motion
	# cat $BACKUP/motion.conf > $MOTIONCONF
fi

# create swap partition
read -p "Do you want to create swap partition" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	swapoff -a
	
	command="$(blkid)"
	result=${command}
	echo $result

	regexp='(([0-9a-z/]*): UUID="([0-9a-zA-Z-]*)" TYPE="swap")'
	[[ $result =~ $regexp ]]
	line=${BASH_REMATCH[1]}
	devpath=${BASH_REMATCH[2]}
	uuid=${BASH_REMATCH[3]}
	echo "I find out that swap partition path is: $devpath and uuid is: $uuid. Is this correct?"
	read -p "Confirm partition" -n 1 -r
	echo
	if [[ $REPLY =~ ^[Yy]$ ]]
	then
		mkswap $devpath
		swapon -U $uuid
		echo "UUID=$uuid   none    swap    sw    0   0" >> /etc/fstab
		sysctl vm.swappiness=20
		sysctl vm.vfs_cache_pressure=50
		echo "vm.swappiness = 20" >> /etc/sysctl.conf
		echo "vm.vfs_cache_pressure = 50" >> /etc/sysctl.conf
		swapon -s
	fi
fi

# restore config
read -p "Do you want to restore config?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
	# get length of file array
	fileArrayLength=${#FILEARRAY[@]}
 
	# use for loop read all files
	for (( i=0; i<${fileArrayLength}; i++ ));
	do
		cp -v ${FILEDESTINATIONARRAY[$i]} ${FILEDESTINATIONARRAY[$i]}.bakup
		cat ${FILEARRAY[$i]} > ${FILEDESTINATIONARRAY[$i]}
	done
	
	# get length of directory array
	directoryArrayLength=${#FILEARRAY[@]}
 
	# use for loop read all files
	for (( i=0; i<${fileArrayLength}; i++ ));
	do
		echo ${DIRECTORYDESTINATIONARRAY[$i]}/
		cp -R ${DIRECTORYARRAY[$i]}* ${DIRECTORYDESTINATIONARRAY[$i]}
	done
	
	chmod +x /etc/cron.weekly/clean_seagate.sh
	chmod 644 -R /var/www/html
fi

echo 'TODO: run "crontab -e" and add line "* * * * * /home/odroid/Scripts/digitemp.sh" ';
echo 'TODO: run "passwd" and "sudo su; passwd" to chage passwords';
echo "Check ssh config file: /etc/ssh/sshd_config"
