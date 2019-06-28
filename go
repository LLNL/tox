#!/bin/bash
SECONDS=0
# --------------------------------
if [ -z "$WHEREAMI" ]; then
    pushd .. > /dev/null
    export WHEREAMI=$PWD
    popd > /dev/null
fi
# --------------------------------
export JAVA_HOME=$(/usr/libexec/java_home -v1.8)
export ANT_HOME=$WHEREAMI/ant
export PATH=$PATH:$ANT_HOME/bin
export TOMCAT_HOME=$WHEREAMI/tomcat
export WEBAPPS=$TOMCAT_HOME/webapps
export JAVA_OPTS=
# export CATALINA_OPTS="-Doracle.jdbc.autoCommitSpecCompliant=false"
export CATALINA_OPTS="-Doracle.jdbc.autoCommitSpecCompliant=false -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
echo -----------------------------
say "go"
case "$1" in
	'-?'|'-help'|'--help')
		echo "usage: go"
		echo "       compile and deploy tox"
		echo -----------------------------
		;;
	'bounce')
		echo -----------------------------
		"$TOMCAT_HOME/bin/shutdown.sh"
		"$TOMCAT_HOME/bin/startup.sh"
		echo -----------------------------
		say "bounced"
		;;
	*)
		ant
		if [ $? = 0 ] ; then
			echo -----------------------------
			ant clean
			echo -----------------------------
			"$TOMCAT_HOME/bin/shutdown.sh"
			rm -fr "$WEBAPPS/tox*"
			rm -f "$TOMCAT_HOME/logs/tox*"
			echo -----------------------------
			cp -v tox.war "$WEBAPPS"
			"$TOMCAT_HOME/bin/startup.sh"
			echo -----------------------------
			say "success"
		else
			say "fail"
		fi
		;;
esac
duration=$SECONDS
echo "$(($duration / 60)) minutes and $(($duration % 60)) seconds elapsed."