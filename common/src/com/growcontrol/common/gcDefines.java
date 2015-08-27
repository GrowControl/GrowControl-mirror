package com.growcontrol.common;


public final class gcDefines {
	private gcDefines() {}


	public static final String LOG_NAME_SERVER = "SERVER";
	public static final String LOG_NAME_CLIENT = "CLIENT";


	// default ports
	public static final int DEFAULT_SOCKET_PORT_RAW = 1142;
	public static final int DEFAULT_SOCKET_PORT_SSL = 1143;
	public static int DEFAULT_SOCKET_PORT(final boolean ssl) {
		return (
			ssl
			? DEFAULT_SOCKET_PORT_RAW
			: DEFAULT_SOCKET_PORT_SSL
		);
	}


}
