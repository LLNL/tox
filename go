#!/bin/bash
# $Id: go 7 2010-01-27 22:44:17Z dacracot $
export WEBAPPS=/Volumes/webdav/WEBAPPS
echo -----------------------------
case "$1" in
	'-?'|'-help'|'--help')
		echo "usage: go"
		echo "       compile and deploy tox only"
		echo "usage: go -t <host> <port>"
		echo "       deploy the simple test application only"
		echo "usage: go -b <host> <port>"
		echo "       deploy both tox and the simple test application"
		echo -----------------------------
		;;
	'-t')
		ant -Dtest.host=$2 -Dtest.port=$3 load.test.war
		if [ $? = 0 ] ; then
			echo -----------------------------
			ant clean
			echo -----------------------------
			rm -frv $WEBAPPS/test*
			echo -----------------------------
			cp -v test.war $WEBAPPS
			echo -----------------------------
			say "The build of the test application was successful."
		else
			say "The build failed."
		fi
		;;
	'-b')
		ant -Dtest.host=$2 -Dtest.port=$3
		if [ $? = 0 ] ; then
			echo -----------------------------
			cp -v tmp/tox.jar .
			echo -----------------------------
			ant clean
			echo -----------------------------
			rm -frv $WEBAPPS/tox* $WEBAPPS/test*
			echo -----------------------------
			cp -v tox.war test.war $WEBAPPS
			echo -----------------------------
			say "The build of both tox and the test application was successful."
		else
			say "The build failed."
		fi
		;;
	*)
		ant load.war
		if [ $? = 0 ] ; then
			echo -----------------------------
			cp -v tmp/tox.jar .
			echo -----------------------------
			ant clean
			echo -----------------------------
			rm -frv $WEBAPPS/tox*
			echo -----------------------------
			cp -v tox.war $WEBAPPS
			echo -----------------------------
			say "The build of tox was successful."
		else
			say "The build failed."
		fi
		;;
esac
