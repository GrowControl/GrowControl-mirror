GrowControl is a computer automation system for your home and garden, or hobby projects. It's expandable with plugins, fully multi-threaded makes it fast, and your ideas make it powerful.

The basic system comes in 4 parts, all of which are optional and can be customized.

* The server is the core of the system, coordinating all the events and actions. Normally, this will run silently in the background on some small device. The client can be run in stand-alone mode, which makes the server optional.
* The client is an application which you can run on your desktop or laptop. The client is the graphical interface to communicate with the server. This program can show you the state of your entire system, or make changes. The server can continue running as normal without the client being in use, unless of course you're running in stand-alone mode.
* The plugins are what give the system its functional power. Without plugins, the client and server do very little. Officially supported plugins, as well as 3rd party contributed plugins are available on growcontrol.com
* The hardware is the last piece you'll need to make it all work. It too is optional, as there are many plugins which can be used without extra hardware. The Arduino and its variations are commonly used, and can easily read sensors, control 120v outlets using relays, or any number of other devices.



Requirements
------------

To use the GrowControl server/client software, you'll first need to install the latest Java Runtime. On linux, either the OpenJDK or the official JRE/JDK will work. Most libraries which are needed are already included in the jar package. The GrowControl Server has minimal system requirements, and will run happily on almost any system, new or old.

In order to flash the Arduino devices with the sketches provided by GrowControl, you'll need the Arduino IDE which can be found here http://www.arduino.cc/ Most sketches will run on the ATMega328 based Arduino's, such as the Arduino Uno. Sketches which require more resources are noted at the top of the sketch file, and will run on any SAM3X8E based Arduino, such as the Arduino Due, or the Udoo.



Installing
----------

Installing on a Redhat derived distro such as Fedora or CentOS is simple. RPM's can be found on the http://growcontrol.com/ website, or you may receive automatic updates using the yum repo which can be installed using http://yum.poixson.com/latest.rpm . This yum repo may be discontinued once the project is submitted to a mainstream repo, in which case only the testing repo will be provided at this location.

Download Links:
http://dl.poixson.com/  - All build versions can be found here, in both rpm and jar formats.
http://yum.poixson.com/ - Only the most recent rpm builds are provided here.



Development
-----------

Compiling GrowControl from source is relatively easy. There are a few tools you'll need, namely git and maven.

Fedora/Redhat:
sudo yum install git maven java-1.8.0-openjdk-devel

Ubuntu:
sudo apt-get update
sudo apt-get install git maven openjdk-7-jdk

From the locaton you'd like to set up your workspace, the following commands will clone the github repo's.

git clone https://github.com/PoiXson/GrowControl.git
git clone https://github.com/PoiXson/pxnCommon.git

First, compile the pxnCommon package:
(cd pxnCommon; mvn clean install)

Then finally compile GrowControl:
(cd GrowControl; mvn clean install)

You'll then find the compiled jar's in the following locations:
GrowControl/target/
GrowControl/server/target/
GrowControl/client/target/

Note: the files beginning with scant- contain only project specific classes. Required libraries aren't included in these jar's.

In order to create the project files to import into Eclipse:
(cd pxnCommon; mvn eclipse:eclipse dependency:sources)
(cd GrowControl; mvn eclipse:eclipse dependency:sources)



Startup Steps
-------------

Priority | Location | Description
3  | PXN | lock file
5  | PXN | ensure not root
11 | PXN | load clock
20 | S/C | load config
30 | S   | load commands
32 | S   | start console input
40 |   C | start gui
50 | S/C | load plugins
55 | S/C | enable plugins
75 | PXN | load scheduler
80 | PXN | start ticker
90 | S   | start sockets
95 | S/C | start scripts
98 |   C | display login window

PXN = pxnCommon
S   = gcServer
  C = gcClient
S/C = gcServer and gcClient



Shutdown Steps
--------------

Priority | Location | Description
100| PXN | display stats
98 |   C | close windows
95 | S/C | stop scripts
90 | S/C | close sockets
80 | PXN | stop ticker
75 | PXN | stop scheduler
70 | PXN | stop thread pools
55 | S/C | disable plugins
50 | S/C | unload plugins
32 | PXN | stop console input

PXN = pxnCommon
S   = gcServer
  C = gcClient
S/C = gcServer and gcClient



Visit us on irc:

irc.esper.net #growcontrol

