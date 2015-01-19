


SOURCE_ROOT=`pwd`
SPEC_FILE="growcontrol.spec"



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



# build rpm
rpmbuild -bb \
	--define="_topdir ${BUILD_ROOT}" \
	--define="_tmppath ${BUILD_ROOT}/tmp" \
	--define="SOURCE_ROOT_SERVER ${SOURCE_ROOT}" \
	--define="SOURCE_ROOT_CLIENT ${SOURCE_ROOT}" \
	--define="_rpmdir ${OUTPUT_DIR}" \
	--define="BUILD_NUMBER ${BUILD_NUMBER}" \
	"${BUILD_ROOT}/SPECS/${SPEC_FILE}" \
		|| exit 1

