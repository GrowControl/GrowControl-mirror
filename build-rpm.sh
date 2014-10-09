clear



PWD=`pwd`
SOURCE_ROOT="${PWD}"
BUILD_ROOT="${PWD}/rpmbuild-root"
OUTPUT_DIR="${PWD}"
SPEC_FILE="growcontrol.spec"



BUILD_NUMBER="x"
if [ "$1" == "--build-number" ]; then
	BUILD_NUMBER=${2}
fi



# ensure rpmbuild tool is available
which rpmbuild >/dev/null || { echo "rpmbuild not installed - yum install rpmdevtools"; exit 1; }
# ensure .spec file exists
[[ -f "${SPEC_FILE}" ]] || { echo "Spec file ${SPEC_FILE} not found!"; exit 1; }



# build space
for dir in BUILD RPMS SOURCE SPECS SRPMS tmp ; do
	if [ -d "${BUILD_ROOT}/${dir}" ]; then
		rm -rf --preserve-root "${BUILD_ROOT}/${dir}" \
			|| exit 1
	fi
	mkdir -p "${BUILD_ROOT}/${dir}" \
		|| exit 1
done

# copy .spec file
cp "${SPEC_FILE}" "${BUILD_ROOT}/SPECS/" \
	|| exit 1



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

