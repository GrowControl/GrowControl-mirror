Name            : GrowControl
Summary         : Automation software for home and garden, or hobby projects
Version         : 3.5.%{BUILD_NUMBER}
Release         : 1
BuildArch       : noarch
Prefix          : %{_javadir}/gc
Requires        : java >= 1.8
Provides        : gcServer = %{version}
Provides        : gcClient = %{version}
%define  _rpmfilename  %%{NAME}-%%{VERSION}-%%{RELEASE}.%%{ARCH}.rpm
%define  serverjar     "%{SOURCE_ROOT}/server/target/gcServer-%{version}.jar"
%define  clientjar     "%{SOURCE_ROOT}/client/target/gcClient-%{version}.jar"

Group           : Development/Electronic Lab
License         : (c) PoiXson
Packager        : PoiXson <support@poixson.com>
URL             : http://growcontrol.com/



%description
GrowControl is a computer automation system for your home and garden, or hobby projects. It's expandable with plugins, fully multi-threaded makes it fast, and your ideas make it powerful.



# avoid jar repack
%define __jar_repack %{nil}
# avoid centos 5/6 extras processes on contents (especially brp-java-repack-jars)
%define __os_install_post %{nil}

# disable debug info
# % define debug_package %{nil}



### Prep ###
%prep
echo
echo "Prep.."
# check for existing workspace
if [ -d "%{SOURCE_ROOT}/server/target/" ]; then
	echo "Found source workspace: %{SOURCE_ROOT}/server/target/"
else
	echo "Source workspace not found: %{SOURCE_ROOT}/server/target/"
	exit 1
fi
if [ -d "%{SOURCE_ROOT}/client/target/" ]; then
	echo "Found source workspace: %{SOURCE_ROOT}/client/target/"
else
	echo "Source workspace not found: %{SOURCE_ROOT}/client/target/"
	exit 1
fi
# check for pre-built jar files
if [ ! -f "%{serverjar}" ]; then
	echo "%{serverjar} file is missing"
	exit 1
fi
if [ ! -f "%{clientjar}" ]; then
	echo "%{clientjar} file is missing"
	exit 1
fi
echo
echo



### Build ###
%build



### Install ###
%install
echo
echo "Install.."
# delete existing rpm
if [[ -f "%{_rpmdir}/%{name}-%{version}-%{release}.noarch.rpm" ]]; then
	%{__rm} -f "%{_rpmdir}/%{name}-%{version}-%{release}.noarch.rpm" \
		|| exit 1
fi
# create directories
%{__install} -d -m 0755 \
	"${RPM_BUILD_ROOT}%{prefix}" \
	"${RPM_BUILD_ROOT}%{_sysconfdir}/gc" \
	"${RPM_BUILD_ROOT}/var/log/gc" \
		|| exit 1
# copy jar files
%{__install} -m 0555 \
	"%{serverjar}" \
	"${RPM_BUILD_ROOT}%{prefix}/gcServer-%{version}_%{release}.jar" \
		|| exit 1
%{__install} -m 0555 \
	"%{clientjar}" \
	"${RPM_BUILD_ROOT}%{prefix}/gcClient-%{version}_%{release}.jar" \
		|| exit 1
%{__install} -d -m 0755 \
	"${RPM_BUILD_ROOT}/usr/share/applications/" \
		|| exit 1
%{__install} -m 0644 \
	"%{SOURCE_ROOT}/growcontrol.desktop" \
	"${RPM_BUILD_ROOT}/usr/share/applications/growcontrol.desktop" \
		|| exit 1



# default config file
touch "${RPM_BUILD_ROOT}%{_sysconfdir}/gc/config.yml"
# create empty log files
touch "${RPM_BUILD_ROOT}/var/log/gc/server.log"
touch "${RPM_BUILD_ROOT}/var/log/gc/client.log"



%check



%clean
# %{__rm} -rf ${RPM_BUILD_ROOT} || exit 1



### Files ###

%files
%defattr(-,root,root,-)
%{prefix}/gcServer-%{version}_%{release}.jar
%{prefix}/gcClient-%{version}_%{release}.jar
/usr/share/applications/growcontrol.desktop



%config(noreplace) %{_sysconfdir}/gc/config.yml

%ghost
/var/log/gc/server.log
/var/log/gc/client.log



### Install ###
# %pre -n gcServer
# echo "Pre-install server.."
# %pre -n gcClient
# echo "Pre-install client.."



### Uninstall ###
# %preun -n gcServer
# echo "Pre-uninstall.."
# service -n gcserver stop

