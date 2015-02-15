package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;


public class gcSocketServerConfig extends xConfig {

//	public static final String default_HOST     = "any";
//	public static final int    default_PORT     = 1142;
//	public static final int    default_PORT_SSL = 1143;

	protected final boolean ssl;



	public static gcSocketDAO get(final xConfig config, final boolean ssl) {
		if(config == null) throw new NullPointerException();
		final Map<String, Object> map = config.getStringObjectMap(
				ssl ? gcServerDefines.CONFIG_SOCKETSSL : gcServerDefines.CONFIG_SOCKET
		);
		if(map == null)
			return null;
		final gcSocketServerConfig cfg = new gcSocketServerConfig(map, ssl);
		return cfg.getDAO();
	}
	protected gcSocketServerConfig(final Map<String, Object> data, final boolean ssl) {
		super(data);
		this.ssl = ssl;
	}
	protected gcSocketServerConfig(final Map<String, Object> data) {
		super(data);
		throw new UnsupportedOperationException();
	}



	public static class gcSocketDAO {

		public final boolean enabled;
		public final boolean ssl;
		public final String  host;
		public final int     port;

		public gcSocketDAO(final boolean enabled, final boolean ssl,
				final String host, final int port) {
			this.enabled = enabled;
			this.ssl     = ssl;
			this.host    = host;
			this.port    = port;
		}

	}



	// get config holder
	public gcSocketDAO getDAO() {
		return new gcSocketDAO(
				this.getEnabled(),
				this.ssl,
				this.getHost(),
				this.getPort()
		);
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
