#!/bin/bash


BACKUPDIR=/media/odroid/seagate/Odroid/bak
SVNPROJECT=/home/svn/java_projects

# file locations
declare -a FILEARRAY=(
/etc/knockd.conf
/etc/default/knockd
/etc/rc.local
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
/etc/init/x11vnc.conf
)

# diretory locations
declare -a DIRECTORYARRAY=(
/home/odroid/.config/transmission
/var/www/html
/home/odroid/.ssh
)

# Check for superuser privileges
if [[ $UID != 0 ]]; then
        echo "Please run this script with sudo:"
        echo "sudo $0 $*"
        exit 1
fi

rm -r $BACKUPDIR
mkdir $BACKUPDIR

## now copy files
for i in "${FILEARRAY[@]}"
do
   cp -v "$i" $BACKUPDIR
done

## now copy directories
for i in "${DIRECTORYARRAY[@]}"
do
   cp -r "$i" $BACKUPDIR
done

# create svn dump
# http://www.if-not-true-then-false.com/2012/svn-subversion-backup-and-restore/
read -p "Create SVN dump?" -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
        svnadmin dump $SVNPROJECT > $BACKUPDIR/java_projects.dump
        #gzip -9 $PATHOFDUMP
fi
