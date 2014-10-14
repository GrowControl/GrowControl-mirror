
PWD=`pwd`



if [ "${1}" == "--build-number" ]; then
	BUILD_NUMBER="${2}"
fi
if [ -z ${BUILD_NUMBER} ]; then
	BUILD_NUMBER="x"
fi



# replace version in pom files
sedresult=0
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" "${PWD}/pom.xml"        || sedresult=1
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" "${PWD}/server/pom.xml" || sedresult=1
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" "${PWD}/client/pom.xml" || sedresult=1



if [ $sedresult != 0 ]; then
	echo "Failed to sed the pom files!"
else
	# build
	mvn clean install
	buildresult=$?
fi



# return the pom files
mvresult=0
mv -fv "${PWD}/pom.xml.original"        "${PWD}/pom.xml"        || mvresult=1
mv -fv "${PWD}/server/pom.xml.original" "${PWD}/server/pom.xml" || mvresult=1
mv -fv "${PWD}/client/pom.xml.original" "${PWD}/client/pom.xml" || mvresult=1



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

