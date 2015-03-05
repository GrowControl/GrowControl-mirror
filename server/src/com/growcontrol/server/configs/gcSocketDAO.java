package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;


public class gcSocketDAO {

	public final boolean enabled;
	public final boolean ssl;
	public final String  host;
	public final int     port;



	public static gcSocketDAO get(final Map<String, Object> data) {
		if(utils.isEmpty(data))
			return null;
		return (new gcSocketConfig(data)).get();
	}



	protected static class gcSocketConfig extends xConfig {
		public gcSocketConfig(final Map<String, Object> data) {
			super(data);
		}
		public gcSocketDAO get() {
			final boolean enabled;
			if(this.exists(gcServerDefines.CONFIG_SOCKET_ENABLE))
				enabled = this.getBool(gcServerDefines.CONFIG_SOCKET_ENABLE, false);
			else
				enabled = true;
			final boolean ssl    = this.getBool  (gcServerDefines.CONFIG_SOCKET_SSL, 	false);
			final String  host   = this.getString(gcServerDefines.CONFIG_SOCKET_HOST);
			final int     port   = this.getInt   (gcServerDefines.CONFIG_SOCKET_PORT, ssl ? 1143 : 1142);
			return new gcSocketDAO(
					enabled,
					ssl,
					host,
					port
			);
		}
	}



	public gcSocketDAO(final boolean enabled, final boolean ssl,
			final String host, final int port) {
		if(port < 1 || port > 65535) throw new IllegalArgumentException("Invalid port number: "+Integer.toString(port));
		this.enabled = enabled;
		this.ssl    = ssl;
		this.host   = utils.isEmpty(host) ? null : host;
		this.port   = port;
	}



}
