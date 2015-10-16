package com.growcontrol.server;

import com.growcontrol.common.gcCommonDefines;
import com.poixson.commonjava.Utils.xTime;


public final class gcServerDefines {
	private gcServerDefines() {}



	// logger name
	public static final String LOG_NAME = gcCommonDefines.LOG_NAME_SERVER;



	// defaults
	public static final xTime   DEFAULT_TICK_INTERVAL = xTime.get("60s");
	public static final int     DEFAULT_META_THREADS  = 0;
	public static final boolean DEFAULT_PROMPT_TICKER = false;

	// socket defaults
	public static final int DEFAULT_SOCKET_BACKLOG  = 10;



	// server config
	public static final String CONFIG_FILE = "server.yml";

	// config keys
	public static final String CONFIG_TICK_INTERVAL = "Tick Interval";
	public static final String CONFIG_META_THREADS  = "Meta Threads";
	public static final String CONFIG_PROMPT_TICKER = "Prompt Ticker";



	// socket config keys
	public static final String CONFIG_SOCKET_BACKLOG = "Socket Backlog";
	public static final String CONFIG_SOCKETS        = "Sockets";
		public static final String CONFIG_SOCKET_ENABLE = "Enable";
		public static final String CONFIG_SOCKET_SSL    = "SSL";
		public static final String CONFIG_SOCKET_HOST   = "Host";
		public static final String CONFIG_SOCKET_PORT   = "Port";



}
