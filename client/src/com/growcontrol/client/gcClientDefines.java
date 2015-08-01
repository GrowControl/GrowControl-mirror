package com.growcontrol.client;


public class gcClientDefines {


	// defaults
	public static final int DEFAULT_SOCKET_PORT = 1142;

//	public static final xTime DEFAULT_TICK_INTERVAL = xTime.get("1s");
//	public static final int   DEFAULT_LISTEN_PORT   = 1142;
//	public static final int   DEFAULT_LOGIC_THREADS = 0;


	// client config
	public static final String CONFIG_FILE = "client.yml";
	// config keys
	public static final String CONFIG_VERSION       = "Version";
	public static final String CONFIG_LOG_LEVEL     = "Log Level";
	public static final String CONFIG_DEBUG         = "Debug";


	// window states
	public static final String CONFIG_WINDOWS       = "Windows";
		public static final String CONFIG_WINDOW_NAME   = "Name";
		public static final String CONFIG_WINDOW_X      = "x";
		public static final String CONFIG_WINDOW_Y      = "y";
		public static final String CONFIG_WINDOW_W      = "w";
		public static final String CONFIG_WINDOW_H      = "h";


	// saved profiles
	public static final String PROFILES_FILE        = "profiles.yml";
	// profile keys
	public static final String PROFILE_LAST_USED    = "Last Used Profile";
	public static final String PROFILE_AUTO_CONNECT = "Auto-Connect";
	public static final String PROFILES             = "Profiles";
		public static final String PROFILE_NAME = "Name";
		public static final String PROFILE_HOST = "Host";
		public static final String PROFILE_PORT = "Port";
		public static final String PROFILE_USER = "Username";
		public static final String PROFILE_PASS = "Password";


}
