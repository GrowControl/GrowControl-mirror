
clear



# load workspace_utils.sh script
if [ -e workspace_utils.sh ]; then
	source ./workspace_utils.sh
elif [ -e /usr/local/bin/pxn/workspace_utils.sh ]; then
	source /usr/local/bin/pxn/workspace_utils.sh
else
	wget http://dl.poixson.com/scripts/pxn/workspace_utils.sh
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


