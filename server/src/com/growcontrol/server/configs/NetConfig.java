package com.growcontrol.server.configs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.xLogger.xLog;


public class NetConfig {

	public final boolean enabled;
	public final boolean ssl;
	public final String  host;
	public final int     port;



	public static Map<String, NetConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, NetConfig> configs = new HashMap<String, NetConfig>();
		for(final Object o : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								o
						);
				final NetConfig cfg = get(datamap);
				configs.put(cfg.toString(), cfg);
			} catch (Exception e) {
xLog.getRoot("config").trace(e);
			}
		}
		return configs;
	}
	public static NetConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		return new NetConfig(
				config.getBool(gcServerDefines.CONFIG_SOCKET_ENABLE, false),
				config.getBool(gcServerDefines.CONFIG_SOCKET_SSL, false),
				config.getString(gcServerDefines.CONFIG_SOCKET_HOST),
				config.getInt   (gcServerDefines.CONFIG_SOCKET_PORT, gcServerDefines.DEFAULT_SOCKET_PORT)
		);
	}



	public NetConfig(final boolean enabled, final boolean ssl,
			final String host, final int port) {
		if(port < 1 || port > 65535) throw new IllegalArgumentException("Invalid port number: "+Integer.toString(port));
		this.enabled = enabled;
		this.ssl     = ssl;
		this.host    = utils.isEmpty(host) ? null : host;
		this.port    = port;
	}



	public String getHost() {
		final String host = this.host;
		if(utils.isEmpty(host) || "*".equals(host) || "any".equalsIgnoreCase(host))
			return null;
		return host;
	}
	public InetAddress getInetAddress() throws UnknownHostException {
		final String host = this.getHost();
		if(host == null)
			return null;
		if("127.0.0.1".equals(host) || "localhost".equalsIgnoreCase(host))
			return InetAddress.getLoopbackAddress();
		return InetAddress.getByName(host);
	}



	@Override
	public String toString() {
		final String host = this.getHost();
		return (host == null ? "*" : this.host)+
				":"+Integer.toString(this.port)+
				(this.ssl ? "(ssl)" : "(raw)");
	}
	public boolean matches(final NetConfig config) {
		if(config == null)
			return false;
		if(this.enabled != config.enabled)
			return false;
		return this.toString().equals(config.toString());
	}



}
