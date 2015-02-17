#!/bin/bash



PWD=`pwd`
# load build_utils.sh script
if [ -e "${PWD}/build_utils.sh" ]; then
	source "${PWD}/build_utils.sh"
elif [ -e "/usr/local/bin/pxn/build_utils.sh" ]; then
	source "/usr/local/bin/pxn/build_utils.sh"
else
	wget "https://raw.githubusercontent.com/PoiXson/shellscripts/master/pxn/build_utils.sh" \
		|| exit 1
	source "${PWD}/build_utils.sh"
fi



# replace version in pom files
sedresult=0
sedVersion "${PWD}/pom.xml"        || sedresult=1
sedVersion "${PWD}/server/pom.xml" || sedresult=1
sedVersion "${PWD}/client/pom.xml" || sedresult=1



if [ $sedresult != 0 ]; then
	echo "Failed to sed the pom files!"
else
	# build
	mvn clean install source:jar
	buildresult=$?
fi



# return the pom files
mvresult=0
restoreSed "${PWD}/pom.xml"        || mvresult=1
restoreSed "${PWD}/server/pom.xml" || mvresult=1
restoreSed "${PWD}/client/pom.xml" || mvresult=1



# results
if [ $sedresult != 0 ]; then
	exit 1
fi
if [ $buildresult != 0 ]; then
	echo "Build has failed!"
	exit 1
fi
if [ $mvresult != 0 ]; then
	echo "Failed to return an original pom.xml file!"
	exit 1
fi



cp -fv "${PWD}/target/GrowControl-"*.zip     "${PWD}"
cp -fv "${PWD}/server/target/gcServer-"*.jar "${PWD}"
cp -fv "${PWD}/client/target/gcClient-"*.jar "${PWD}"



newline
ls -lh "${PWD}/GrowControl-"*.zip
ls -lh "${PWD}/gcServer-"*.jar
ls -lh "${PWD}/gcClient-"*.jar
newline

