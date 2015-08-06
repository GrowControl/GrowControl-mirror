package com.growcontrol.server.configs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.xLogger.xLog;


public class NetServerConfig {

	public final boolean enabled;
	public final boolean ssl;
	public final String  host;
	public final int     port;

	public final String key;



	public static Map<String, NetServerConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, NetServerConfig> configs = new HashMap<String, NetServerConfig>();
		for(final Object o : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								o
						);
				final NetServerConfig cfg = get(datamap);
				configs.put(cfg.toString(), cfg);
			} catch (Exception e) {
xLog.getRoot("config").trace(e);
			}
		}
		return configs;
	}
	public static NetServerConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		// default to enable if key doesn't exist
		final boolean enabled =
				config.exists(gcServerDefines.CONFIG_SOCKET_ENABLE)
				? config.getBool(gcServerDefines.CONFIG_SOCKET_ENABLE, false)
				: true;
		final boolean ssl = config.getBool  (gcServerDefines.CONFIG_SOCKET_SSL, false);
		final String host = config.getString(gcServerDefines.CONFIG_SOCKET_HOST);
		final int    port = config.getInt   (
				gcServerDefines.CONFIG_SOCKET_PORT,
				ssl
				? gcServerDefines.DEFAULT_SOCKET_PORT_SSL
				: gcServerDefines.DEFAULT_SOCKET_PORT
		);
		return new NetServerConfig(
				enabled,
				ssl,
				host,
				port
		);
	}



	public NetServerConfig(final boolean enabled, final boolean ssl,
			final String host, final int port) {
		if(port < 1 || port > utilsNumbers.MAX_PORT)
			throw new IllegalArgumentException("Invalid port number: "+Integer.toString(port));
		this.enabled = enabled;
		this.ssl     = ssl;
		this.host    = (utils.isEmpty(host) ? null : host);
		this.port    = port;
		this.key = this.getKey();
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
		return this.key;
	}
	private String getKey() {
		final StringBuilder str = new StringBuilder();
		final String host = this.getHost();
		str.append(host == null ? "*" : host);
		str.append(":").append(this.port);
		if(this.ssl) str.append("<ssl>");
		return str.toString();
	}
	public boolean matches(final NetServerConfig config) {
		if(config == null)
			return false;
		if(this.enabled != config.enabled)
			return false;
		return this.toString().equalsIgnoreCase(config.toString());
	}



}
