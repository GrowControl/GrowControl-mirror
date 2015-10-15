package com.growcontrol.common;


public final class gcCommonDefines {
	private gcCommonDefines() {}


	public static final String LOG_NAME_SERVER = "SERVER";
	public static final String LOG_NAME_CLIENT = "CLIENT";


	public static final int ADDRESS_MAX_LENGTH = 8;


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
