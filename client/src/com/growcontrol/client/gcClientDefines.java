package com.growcontrol.client;

import com.poixson.commonjava.Utils.xTime;


public class gcClientDefines {


	// defaults
	public static final xTime DEFAULT_TICK_INTERVAL = xTime.get("1s");
	public static final int   DEFAULT_LISTEN_PORT   = 1142;
	public static final int   DEFAULT_LOGIC_THREADS = 0;


	// client config
	public static final String CONFIG_FILE = "client.yml";
	// config keys
	public static final String CONFIG_VERSION       = "Version";
	public static final String CONFIG_LOG_LEVEL     = "Log Level";
	public static final String CONFIG_DEBUG         = "Debug";


}
