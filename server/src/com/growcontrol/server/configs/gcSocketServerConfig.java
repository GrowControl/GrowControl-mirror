package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;


public class gcSocketServerConfig extends xConfig {

//	public static final String default_HOST     = "any";
//	public static final int    default_PORT     = 1142;
//	public static final int    default_PORT_SSL = 1143;



	public static gcSocketServerConfig get(final xConfig config, final boolean ssl) {
		if(config == null) throw new NullPointerException();
		final Map<String, Object> map = config.getStringObjectMap(
				ssl ? gcServerDefines.CONFIG_SOCKETSSL : gcServerDefines.CONFIG_SOCKET
		);
		if(map == null)
			return null;
		return new gcSocketServerConfig(map);
	}
	protected gcSocketServerConfig(final Map<String, Object> data) {
		super(data);
	}



	// enabled
	public boolean getEnabled() {
		final Boolean bool = this.getBoolean(gcServerDefines.CONFIG_SOCKET_ENABLE);
		if(bool == null)
			return false;
		return bool.booleanValue();
	}



	// host
	public String getHost() {
		final String host = this.getString(gcServerDefines.CONFIG_SOCKET_HOST);
//		if(utils.isEmpty(host))
//			return default_HOST;
		return host;
	}



	// port
	public int getPort() {
		final Integer port = this.getInteger(gcServerDefines.CONFIG_SOCKET_PORT);
		if(port == null)
			return 1142;
//			return default_PORT;
		final int p = port.intValue();
		if(p < 1 || p > 65536) throw new IllegalArgumentException("Invalid port value");
		return p;
	}



}
