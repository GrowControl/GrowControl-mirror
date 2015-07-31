package com.growcontrol.server;

import com.poixson.commonjava.Utils.xTime;


public class gcServerDefines {


	// defaults
	public static final boolean DEFAULT_PROMPT_TICKER = false;
	public static final xTime DEFAULT_TICK_INTERVAL = xTime.get("5s");
	public static final int DEFAULT_LOGIC_THREADS = 0;


	// server config
	public static final String CONFIG_FILE = "server.yml";
	// config keys
	public static final String CONFIG_VERSION       = "Version";
	public static final String CONFIG_LOG_LEVEL     = "Log Level";
	public static final String CONFIG_DEBUG         = "Debug";
	public static final String CONFIG_PROMPT_TICKER = "Prompt Ticker";
	public static final String CONFIG_TICK_INTERVAL = "Tick Interval";
	public static final String CONFIG_LOGIC_THREADS = "Logic Threads";
//	public static final String CONFIG_ZONES         = "Zones";
	// sockets
	public static final String CONFIG_SOCKETS       = "Sockets";
	public static final String CONFIG_SOCKET_ENABLE = "Enable";
	public static final String CONFIG_SOCKET_SSL    = "SSL";
	public static final String CONFIG_SOCKET_HOST   = "Host";
	public static final String CONFIG_SOCKET_PORT   = "Port";


}
