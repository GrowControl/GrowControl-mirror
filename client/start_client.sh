#!/bin/bash


# JAVAPACKAGES_DEBUG=1
PATTERN='gcClient*.jar'
MAIN_CLASS=com.growcontrol.client.gcClient

. /usr/share/java-utils/java-functions
# find highest version jar
JAR=`pwd`/`ls -A1U $PATTERN | sort --version-sort -r | head -n1`

# build classpath
CLASSPATH=$JAR${CLASSPATH:+:}$CLASSPATH
for lib in `pwd`/lib/*.jar; do
	CLASSPATH=$CLASSPATH:$lib
done

# execute
if [ -n "${JAVAPACKAGES_DEBUG}" ]; then
	echo "RUN: JAVA ${FLAGS} -classpath ${CLASSPATH}" \
		"${OPTIONS} ${MAIN_CLASS} ${@}"
fi
run "$@"

