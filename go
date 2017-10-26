#!/bin/bash
# --------------------------------
if [ -z "$WHEREAMI" ]; then
    pushd .. > /dev/null
    export WHEREAMI=$PWD
    popd > /dev/null
fi
# --------------------------------
export JAVA_HOME=$(/usr/libexec/java_home -v1.8)
export TOMCAT_HOME=$WHEREAMI/tomcat
export WEBAPPS=$TOMCAT_HOME/webapps
export JAVA_OPTS=
export CATALINA_OPTS="-Doracle.jdbc.autoCommitSpecCompliant=false"
echo -----------------------------
say "go"
case "$1" in
	'-?'|'-help'|'--help')
		echo "usage: go"
		echo "       compile and deploy tox"
		echo -----------------------------
		;;
	*)
		ant
		if [ $? = 0 ] ; then
			echo -----------------------------
			ant clean
			echo -----------------------------
			$TOMCAT_HOME/bin/shutdown.sh
			rm -fr $WEBAPPS/tox*
			echo -----------------------------
			cp -v tox.war $WEBAPPS
			$TOMCAT_HOME/bin/startup.sh
			sleep 2
			echo -----------------------------
			unitTest
			echo -----------------------------
			say "success"
		else
			say "fail"
		fi
		;;
esac
