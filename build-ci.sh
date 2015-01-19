# sh GrowControl.git/build-ci.sh  --dl-path=/home/pxn/www/dl/GrowControl  --yum-path=/home/pxn/www/yum/extras-testing/noarch


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


NAME="GrowControl"
NAMEs="gcServer"
NAMEc="gcClient"
[ -z "${WORKSPACE}" ] && WORKSPACE=`pwd`
rm -vf "${WORKSPACE}/${NAME}.git/${NAMEs}"-*.jar
rm -vf "${WORKSPACE}/${NAME}.git/${NAMEc}"-*.jar
rm -vf "${WORKSPACE}/${NAME}.git/${NAMEs}"-*.noarch.rpm
rm -vf "${WORKSPACE}/${NAME}.git/${NAMEc}"-*.noarch.rpm
rm -vf "${WORKSPACE}/${NAME}.git/${NAME}"-*.zip


title "Checkout.."
( cp -fv "${WORKSPACE}/${NAME}.git/setup_workspace.sh" "${WORKSPACE}/setup_workspace.sh" ) || exit 1
( cd "${WORKSPACE}" && sh setup_workspace.sh --https --user ) || exit 1


title "Build.."
( cd "${WORKSPACE}/${NAME}.git/" && sh build-mvn.sh --build-number ${BUILD_NUMBER} ) || exit 1
( cd "${WORKSPACE}/${NAME}.git/" && sh build-rpm.sh --build-number ${BUILD_NUMBER} ) || exit 1


title "Deploy.."
cp -fv "${WORKSPACE}/${NAME}.git/${NAMEs}"-*.jar        "${DL_PATH}/" || exit 1
cp -fv "${WORKSPACE}/${NAME}.git/${NAMEc}"-*.jar        "${DL_PATH}/" || exit 1
cp -fv "${WORKSPACE}/${NAME}.git/${NAMEs}"-*.noarch.rpm "${DL_PATH}/" || exit 1
cp -fv "${WORKSPACE}/${NAME}.git/${NAMEc}"-*.noarch.rpm "${DL_PATH}/" || exit 1
cp -fv "${WORKSPACE}/${NAME}.git/${NAME}"-*.zip         "${DL_PATH}/" || exit 1
# server
latest_version "${DL_PATH}/${NAMEs}-*.noarch.rpm"         || exit 1
echo "Latest version: "${LATEST_FILE}
ln -fs "${LATEST_FILE}" "${YUM_PATH}/${NAMEs}.noarch.rpm" || exit 1
# client
latest_version "${DL_PATH}/${NAMEc}-*.noarch.rpm"         || exit 1
echo "Latest version: "${LATEST_FILE}
ln -fs "${LATEST_FILE}" "${YUM_PATH}/${NAMEc}.noarch.rpm" || exit 1

