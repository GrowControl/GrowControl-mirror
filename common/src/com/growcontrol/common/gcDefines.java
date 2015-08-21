package com.growcontrol.common;


public final class gcDefines {
	private gcDefines() {}


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
