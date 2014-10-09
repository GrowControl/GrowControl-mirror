
PWD=`pwd`
if [[ $PWD == */GrowControl.git ]]; then
	echo
	echo "Cannot run this script within the repo!"
	echo "Move up to the parent directory and run from there."
	echo
	exit 1
fi
echo



if [ ${1} == "--build-number" ]; then
	BUILD_NUMBER="${2}"
	echo "Starting build # ${BUILD_NUMBER}.."
else
	echo "Unknown build number!"
	BUILD_NUMBER="x"
fi
echo
echo



echo "Copy the scripts.."
cp -fv "${PWD}/GrowControl.git/setup_workspace.sh" "${PWD}/" || exit 1
cp -fv "${PWD}/GrowControl.git/build-rpm.sh"       "${PWD}/" || exit 1
cp -fv "${PWD}/GrowControl.git/growcontrol.spec"   "${PWD}/" || exit 1
echo
echo



echo "Updating workspace.."
sh "${PWD}/setup_workspace.sh" --https --user \
	|| exit 1



echo "Building jars.."
(
	cd "${PWD}/GrowControl.git/"                    || exit 1
	sh build-jars.sh --build-number ${BUILD_NUMBER} || exit 1
)



echo "Copy Artifacts.."
rm -fv "${PWD}/gc"{Server,Client}-*.{jar,rpm}
rm -fv "${PWD}/GrowControl-"*.zip
cp -fv "${PWD}/GrowControl.git/server/target/gcServer-"*.jar    "${PWD}/" || exit 1
cp -fv "${PWD}/GrowControl.git/client/target/gcClient-"*.jar    "${PWD}/" || exit 1
cp -fv "${PWD}/GrowControl.git/target/GrowControl-"*.zip        "${PWD}/" || exit 1
cp -fv "${PWD}/GrowControl.git/"{build-rpm.sh,growcontrol.spec} "${PWD}/" || exit 1



echo "Building RPMs.."
sh "${PWD}/build-rpm.sh" --build-number ${BUILD_NUMBER} \
	|| exit 1



echo "Finished Full Build!"

