# (cd ${WORKSPACE}/GrowControl.git/; sh build-ci.sh)



echo "Checkout.."
( cp -fv "${WORKSPACE}/GrowControl.git/setup_workspace.sh" "${WORKSPACE}/setup_workspace.sh" ) || exit 1
( cd "${WORKSPACE}" && sh setup_workspace.sh --https --user ) || exit 1



echo "Pre-build Cleanup.."
rm -fv "${WORKSPACE}/GrowControl-"*.zip
rm -fv "${WORKSPACE}/GrowControl.git/GrowControl-"*.zip
rm -fv "${WORKSPACE}/gc"{Server,Client}-*.{jar,noarch.rpm}
rm -fv "${WORKSPACE}/GrowControl.git/gc"{Server,Client}-*.{jar,noarch.rpm}



echo "Build.."
( cd "${WORKSPACE}/GrowControl.git/" && sh build-mvn.sh --build-number ${BUILD_NUMBER} ) || exit 1
( cd "${WORKSPACE}/GrowControl.git/" && sh build-rpm.sh --build-number ${BUILD_NUMBER} ) || exit 1
( cp -fv "${WORKSPACE}/GrowControl.git/gc"{Server,Client}-*.{jar,noarch.rpm} "${WORKSPACE}" ) || exit 1
( cp -fv "${WORKSPACE}/GrowControl.git/GrowControl-"*.zip                    "${WORKSPACE}" ) || exit 1



echo "Deploy.."
rm -fv "/home/pxn/www/yum/extras-testing/noarch/gc"{Server,Client}-*.noarch.rpm
cp -fv "${WORKSPACE}/gc"{Server,Client}-*.noarch.rpm "/home/pxn/www/yum/extras-testing/noarch/" || exit 1


