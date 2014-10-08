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



clear



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



# ask use https/ssh with git
AskHttpsSsh ${*}



# CHECKOUT
title "Checking-out Repos"
CheckoutRepo CommonJava.git     "${REPO_PREFIX}PoiXson/CommonJava.git"
CheckoutRepo xSocket.git        "${REPO_PREFIX}PoiXson/xSocket.git"
CheckoutRepo GrowControl.git    "${REPO_PREFIX}PoiXson/GrowControl.git"
CheckoutRepo gcPlugins.git      "${REPO_PREFIX}PoiXson/gcPlugins.git"
newline
newline



# SYMLINKS
echo "Creating Symbolic Links"
mkdir -p -v GrowControl.git/server/src/com/poixson
mkdir -p -v GrowControl.git/client/src/com/poixson

# server in client
mkRelLink GrowControl.git/server/src/com/growcontrol/server  GrowControl.git/client/src/com/growcontrol  server

# common java
mkRelLink  CommonJava.git/src/com/poixson/commonjava  GrowControl.git/server/src/com/poixson      commonjava
mkRelLink  CommonJava.git/src/com/poixson/commonjava  GrowControl.git/client/src/com/poixson      commonjava

# common app
mkRelLink  CommonJava.git/src/com/poixson/commonapp   GrowControl.git/server/src/com/poixson      commonapp
mkRelLink  CommonJava.git/src/com/poixson/commonapp   GrowControl.git/client/src/com/poixson      commonapp

# gc common
mkRelLink  GrowControl.git/gccommon                   GrowControl.git/server/src/com/growcontrol  gccommon
mkRelLink  GrowControl.git/gccommon                   GrowControl.git/client/src/com/growcontrol  gccommon

# sockets library
mkRelLink  xSocket.git/src/com/poixson/xsocket        GrowControl.git/server/src/com/poixson      xsocket
mkRelLink  xSocket.git/src/com/poixson/xsocket        GrowControl.git/client/src/com/poixson      xsocket

newline
newline



Cleanup
newline
echo "Finished building workspace!"
newline


