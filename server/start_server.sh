#!/bin/bash

#JAVAPACKAGES_DEBUG=1



function DisplayHelp() {
	echo -e "${COLOR_BROWN}Usage:${COLOR_RESET}"
	echo    "  xbuild [options] <group>"
	echo
	echo -e "${COLOR_BROWN}Options:${COLOR_RESET}"
	echo -e "  ${COLOR_GREEN}-q, --quiet${COLOR_RESET}               Hide extra logs"
	echo -e "  ${COLOR_GREEN}--colors${COLOR_RESET}                  Enable console colors"
	echo -e "  ${COLOR_GREEN}--no-colors${COLOR_RESET}               Disable console colors"
	echo -e "  ${COLOR_GREEN}-V, --version${COLOR_RESET}             Display the version"
	echo -e "  ${COLOR_GREEN}-h, --help${COLOR_RESET}                Display this help message and exit"
	exit 1
}

function DisplayVersion() {
	echo -e "${COLOR_BROWN}xBuild${COLOR_RESET} ${COLOR_GREEN}$XBUILD_VERSION${COLOR_RESET}"
	echo
}



# ----------------------------------------



#TODO: is this useful?
## JAVAPACKAGES_DEBUG=1
#PATTERN='gcServer*.jar'
#MAIN_CLASS=com.growcontrol.server.gcServer
#. /usr/share/java-utils/java-functions
## find highest version jar
#JAR=`pwd`/`ls -A1U $PATTERN | sort --version-sort -r | head -n1`
## build classpath
#CLASSPATH=$JAR${CLASSPATH:+:}$CLASSPATH
#for lib in `pwd`/lib/*.jar; do
#	CLASSPATH=$CLASSPATH:$lib
#done

PATTERN=""
MAIN_CLASS=""

ACTIONS=""
ACTIONS_FOUND=""
VERBOSE=$NO
QUIET=$NO
NO_COLORS=$NO

BIN_DIR=""



# ----------------------------------------
# parse args



if [[ $# -eq 0 ]]; then
	DisplayHelp
	exit 1
fi
SELF="$0"
while [ $# -gt 0 ]; do
	case "$1" in

	--bin-dir)
		if [[ -z $2 ]] || [[ "$2" == "-"* ]]; then
			failure "--bin-dir flag requires a value"
			failure ; DisplayHelp ; exit 1
		fi
		\shift
		BIN_DIR="$1"
	;;
	--bin-dir=*)
		BIN_DIR="${1#*=}"
		if [[ -z $BIN_DIR ]]; then
			failure "--bin-dir flag requires a value"
			failure ; DisplayHelp ; exit 1
		fi
	;;

	-v|--verbose) VERBOSE=$YES ;;
	-q|--quiet)   QUIET=$YES   ;;
	--color|--colors)       NO_COLORS=$NO  ; enable_colors  ;;
	--no-color|--no-colors) NO_COLORS=$YES ; disable_colors ;;
	-V|--version) DisplayVersion   ; exit 1 ;;
	-h|--help)    DisplayHelp ; exit 1 ;;

	-*)
		failure "Unknown flag: $1"
		failure ; DisplayHelp
		exit 1
	;;
	*)
		failure "Unknown argument: $1"
		failure ; DisplayHelp ; exit 1
	;;

	esac
	\shift
done



case "$ACTIONS" in
server)
	PATTERN="gcServer*.jar"
	MAIN_CLASS="com.growcontrol.server.gcServer"
	. /usr/share/java-utils/java-functions
	# find highest version jar
	JAR=`pwd`/`ls -A1U $PATTERN | sort --version-sort -r | head -n1`
	# build classpath
	CLASSPATH=$JAR${CLASSPATH:+:}$CLASSPATH
	for lib in `pwd`/lib/*.jar; do
		CLASSPATH=$CLASSPATH:$lib
	done
	# execute
	if [ -n "${JAVAPACKAGES_DEBUG}" ]; then
		echo "RUN: JAVA ${FLAGS} -classpath ${CLASSPATH}" \
			"${OPTIONS} ${MAIN_CLASS} ${@}"
	fi
#TODO: remove this?
#	run "$@"
;;
*)
failure "Unknown action: $"
;;
esac
