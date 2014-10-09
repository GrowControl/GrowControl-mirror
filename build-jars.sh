clear



if [ "${1}" == "--build-number" ]; then
	BUILD_NUMBER="${2}"
fi
if [ -z ${BUILD_NUMBER} ]; then
	BUILD_NUMBER="x"
fi



# replace version in pom files
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" pom.xml        || exit 1
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" server/pom.xml || exit 1
sed -i.original "s/x-SNAPSHOT/${BUILD_NUMBER}-SNAPSHOT/" client/pom.xml || exit 1



# build
mvn clean install
buildresult=$?



# return the pom files
mvresult=0
mv -fv pom.xml.original        pom.xml        || mvresult=1
mv -fv server/pom.xml.original server/pom.xml || mvresult=1
mv -fv client/pom.xml.original client/pom.xml || mvresult=1



# results
if [ $buildresult != 0 ]; then
	echo "Build has failed!"
	exit 1
fi
if [ $mvresult != 0 ]; then
	echo "Failed to return an original pom.xml file!"
	exit 1
fi

