# ==================================================
# GrowControl - Workspace setup script
#
# This script downloads and prepares the GrowControl
# source code. The project uses multiple repositories
# with symlinks between them. Once this script has
# completed, the project will be ready to compile.
# The script can be used safely on an existing
# workspace without damage, but will rather update
# the existing files. This is useful for recompiling.
#
# http://GrowControl.com
# https://github.com/PoiXson/GrowControl
# ==================================================
# setup_workspace.sh

# may be useful when writing a bat version
# http://tldp.org/LDP/abs/html/dosbatch.html



if [[ `pwd` == */GrowControl.git ]]; then
	echo
	echo "Cannot run this script within the repo!"
	echo "Move up to the parent directory and run from there."
	echo
	exit 1
fi



# load workspace_utils.sh script
if [ -e workspace_utils.sh ]; then
	source ./workspace_utils.sh
elif [ -e /usr/local/bin/pxn/workspace_utils.sh ]; then
	source /usr/local/bin/pxn/workspace_utils.sh
else
	wget https://raw.githubusercontent.com/PoiXson/shellscripts/master/pxn/workspace_utils.sh \
		|| exit 1
	source ./workspace_utils.sh
fi



# CHECKOUT
title "Cloning Repos"
CheckoutRepo  CommonJava.git   "${REPO_PREFIX}/CommonJava.git"   || exit 1
CheckoutRepo  xSocket.git      "${REPO_PREFIX}/xSocket.git"      || exit 1
CheckoutRepo  GrowControl.git  "${REPO_PREFIX}/GrowControl.git"  || exit 1
CheckoutRepo  gcPlugins.git    "${REPO_PREFIX}/gcPlugins.git"    || exit 1
newline
newline



# SYMLINKS
title "Creating Symbolic Links"

mkdir -pv GrowControl.git/server/src/com/poixson || exit 1
mkdir -pv GrowControl.git/client/src/com/poixson || exit 1
newline

# server in client
mklinkrel.sh  GrowControl.git/server/src/com/growcontrol/server  GrowControl.git/client/src/com/growcontrol  server      || exit 1
# plugins
mklinkrel.sh  gcPlugins.git                                      GrowControl.git                             plugins     || exit 1

# common java
mklinkrel.sh  CommonJava.git/src/com/poixson/commonjava          GrowControl.git/server/src/com/poixson      commonjava  || exit 1
mklinkrel.sh  CommonJava.git/src/com/poixson/commonjava          GrowControl.git/client/src/com/poixson      commonjava  || exit 1

# common app
mklinkrel.sh  CommonJava.git/src/com/poixson/commonapp           GrowControl.git/server/src/com/poixson      commonapp   || exit 1
mklinkrel.sh  CommonJava.git/src/com/poixson/commonapp           GrowControl.git/client/src/com/poixson      commonapp   || exit 1

# gc common
mklinkrel.sh  GrowControl.git/gccommon                           GrowControl.git/server/src/com/growcontrol  gccommon    || exit 1
mklinkrel.sh  GrowControl.git/gccommon                           GrowControl.git/client/src/com/growcontrol  gccommon    || exit 1

# sockets library
mklinkrel.sh  xSocket.git/src/com/poixson/xsocket                GrowControl.git/server/src/com/poixson      xsocket     || exit 1
mklinkrel.sh  xSocket.git/src/com/poixson/xsocket                GrowControl.git/client/src/com/poixson      xsocket     || exit 1

newline
newline



newline
echo "Finished workspace setup!"
newline
newline
ls -lh
newline
newline

