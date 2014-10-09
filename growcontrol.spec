Name            : GrowControl
Summary         : Automation software for home and garden, or hobby projects
Version         : 3.4.%{BUILD_NUMBER}
Release         : 1
BuildArch       : noarch
Prefix          : %{_javadir}/gc
Requires        : java >= 7
%define  _rpmfilename  %%{NAME}-%%{VERSION}-%%{RELEASE}.%%{ARCH}.rpm

Group		: Development/Electronic Lab
License		: (c) PoiXson
URL             : http://growcontrol.com/

%description
GrowControl is a computer automation system for your home and garden, or hobby projects. It's expandable with plugins, fully multi-threaded makes it fast, and your ideas make it powerful.



%define serverjar "%{SOURCE_ROOT_SERVER}/gcServer-%{version}-SNAPSHOT.jar"
%define clientjar "%{SOURCE_ROOT_CLIENT}/gcClient-%{version}-SNAPSHOT.jar"



# avoid jar repack
%define __jar_repack %{nil}
# avoid centos 5/6 extras processes on contents (especially brp-java-repack-jars)
%define __os_install_post %{nil}

# disable debug info
# % define debug_package %{nil}



### Packages ###
%package gcServer
Summary         : Automation server software for home and garden, or hobby projects
Provides        : gcServer = %{version}
Group           : Development/Electronic Lab
%description gcServer
GrowControl is a computer automation system for your home and garden, or hobby projects. It's expandable with plugins, fully multi-threaded makes it fast, and your ideas make it powerful.

%package gcClient
Summary         : Client GUI tool to access the GrowControl server
Provides        : gcClient = %{version}
Group           : Development/Electronic Lab
%description gcClient
GrowControl is a computer automation system for your home and garden, or hobby projects. It's expandable with plugins, fully multi-threaded makes it fast, and your ideas make it powerful.



### Prep ###
%prep
echo
echo "Prep.."
# check for existing workspace
if [ -d "%{SOURCE_ROOT}" ]; then
	echo "Found source workspace: %{SOURCE_ROOT}"
else
	echo "Source workspace not found: %{SOURCE_ROOT}"
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
%{__install} -m 0777 \
	"%{serverjar}" \
	"${RPM_BUILD_ROOT}%{prefix}/gcServer-%{version}_%{release}.jar" \
		|| exit 1
%{__install} -m 0777 \
	"%{clientjar}" \
	"${RPM_BUILD_ROOT}%{prefix}/gcClient-%{version}_%{release}.jar" \
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

%files gcServer
%defattr(644,-,-,755)
%{prefix}/gcServer-%{version}_%{release}.jar

%files gcClient
%defattr(644,-,-,755)
%{prefix}/gcClient-%{version}_%{release}.jar



%config(noreplace) %{_sysconfdir}/gc/config.yml

%ghost
/var/log/gc/server.log
/var/log/gc/client.log



### Install ###
# %pre gcServer
# echo "Pre-install server.."
# %pre gcClient
# echo "Pre-install client.."



### Uninstall ###
%preun gcServer
echo "Pre-uninstall.."
service gcserver stop


