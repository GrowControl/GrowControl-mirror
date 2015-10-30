package com.growcontrol.common;


public final class gcCommonDefines {
	private gcCommonDefines() {}



	// logger name
	public static final String LOG_NAME_SERVER = "SERVER";
	public static final String LOG_NAME_CLIENT = "CLIENT";



	// defaults
	public static final int ADDRESS_MAX_LENGTH = 16;

	// socket defaults
	public static final int DEFAULT_SOCKET_PORT_RAW = 1142;
	public static final int DEFAULT_SOCKET_PORT_SSL = 1143;
	public static int DEFAULT_SOCKET_PORT(final boolean ssl) {
		return (
			ssl
			? DEFAULT_SOCKET_PORT_RAW
			: DEFAULT_SOCKET_PORT_SSL
		);
	}



	// config keys
	public static final String CONFIG_VERSION       = "Version";
	public static final String CONFIG_LOG_LEVEL     = "Log Level";
	public static final String CONFIG_DEBUG         = "Debug";

	// socket config keys
	public static final String CONFIG_SOCKET_BACKLOG = "Socket Backlog";
	public static final String CONFIG_SOCKETS        = "Sockets";
		public static final String CONFIG_SOCKET_ENABLED = "Enabled";
		public static final String CONFIG_SOCKET_SSL     = "SSL";
		public static final String CONFIG_SOCKET_HOST    = "Host";
		public static final String CONFIG_SOCKET_PORT    = "Port";



}
