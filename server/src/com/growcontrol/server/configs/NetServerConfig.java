/*
package com.growcontrol.server.configs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.growcontrol.common.gcCommonDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.xHashable;


public class NetServerConfig extends xConfig implements xHashable {

	private final String key;

	private final boolean enabled;
	private final boolean ssl;
	private final String  host;
	private final int     port;



	// new config instance
	public NetServerConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		// enabled - default to enable if key doesn't exist
		this.enabled =
			this.exists(gcCommonDefines.CONFIG_SOCKET_ENABLED)
			? this.getBool(gcCommonDefines.CONFIG_SOCKET_ENABLED, false)
			: true;
		// ssl
		{
			Boolean value = this.getBool(gcCommonDefines.CONFIG_SOCKET_SSL, false);
			if (value == null) {
				final int tmpPort = this.getInt(gcCommonDefines.CONFIG_SOCKET_PORT, -1);
				if (tmpPort > 0) {
					if (tmpPort == gcCommonDefines.DEFAULT_SOCKET_PORT_SSL) {
						value = Boolean.TRUE;
					}
				}
			}
			this.ssl =
				value == null
				? gcCommonDefines.DEFAULT_SOCKET_SSL
				: value.booleanValue();
		}
		// host
		this.host = this.getString(gcCommonDefines.CONFIG_SOCKET_HOST);
		if (utils.isEmpty(this.host)) throw new xConfigException("Host is missing from config!");
		// port
		this.port = this.getInt(
			gcCommonDefines.CONFIG_SOCKET_PORT,
			gcCommonDefines.DEFAULT_SOCKET_PORT(this.ssl)
		);
		if (this.port < 1 || this.port > utilsNumbers.MAX_PORT)
			throw new IllegalArgumentException("Invalid port number: "+Integer.toString(this.port));
		// key
		this.key = this.genKey();
	}
//	public NetServerConfig(
//			final boolean enabled, final boolean ssl,
//			final String host, final int port) {
//		this.enabled = enabled;
//		this.ssl     = ssl;
//		this.host    = (utils.isEmpty(host) ? null : host);
//		this.port    = port;
//		this.key = this.genKey();
//	}



	public boolean isEnabled() {
		return this.enabled;
	}
	public boolean useSSL() {
		return this.ssl;
	}
	public String getHost() {
		final String host = this.host;
		if (utils.isEmpty(host) || "*".equals(host) || "any".equalsIgnoreCase(host)) {
			return null;
		}
		return host;
	}
	public int getPort() {
		return this.port;
	}
	public InetAddress getInetAddress() throws UnknownHostException {
		final String host = this.getHost();
		if (host == null) {
			return null;
		}
		if ("127.0.0.1".equals(host) || "localhost".equalsIgnoreCase(host)) {
			return InetAddress.getLoopbackAddress();
		}
		return InetAddress.getByName(host);
	}



	@Override
	public String toString() {
		return this.key;
	}
	@Override
	public String getKey() {
		return this.key;
	}
	private String genKey() {
		final StringBuilder str = new StringBuilder();
		final String host = this.getHost();
		str.append(host == null ? "*" : host.toLowerCase());
		str.append(":").append(this.port);
		if (this.ssl) {
			str.append("<ssl>");
		}
		return str.toString();
	}
	@Override
	public boolean matches(final xHashable hashable) {
		if (hashable == null || !(hashable instanceof NetServerConfig) ) {
			return false;
		}
		final NetServerConfig config = (NetServerConfig) hashable;
		if (this.enabled != config.enabled) {
			return false;
		}
		return this.getKey().equalsIgnoreCase(config.getKey());
	}



}
*/
