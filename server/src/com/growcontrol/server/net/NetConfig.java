/*
package com.growcontrol.server.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;


public class NetConfig {

	public final boolean enabled;
	public final boolean ssl;
	public final String  host;
	public final int     port;



	public static NetConfig get(final Map<String, Object> data) {
		if(utils.isEmpty(data))
			return null;
		return (new gcSocketConfig(data)).get();
	}



	protected static class gcSocketConfig extends xConfig {
		public gcSocketConfig(final Map<String, Object> data) {
			super(data);
		}
		public NetConfig get() {
			final boolean enabled;
			if(this.exists(gcServerDefines.CONFIG_SOCKET_ENABLE))
				enabled = this.getBool(gcServerDefines.CONFIG_SOCKET_ENABLE, false);
			else
				enabled = true;
			final boolean ssl    = this.getBool  (gcServerDefines.CONFIG_SOCKET_SSL, 	false);
			final String  host   = this.getString(gcServerDefines.CONFIG_SOCKET_HOST);
			final int     port   = this.getInt   (gcServerDefines.CONFIG_SOCKET_PORT, ssl ? 1143 : 1142);
			return new NetConfig(
					enabled,
					ssl,
					host,
					port
			);
		}
	}



	public NetConfig(final boolean enabled, final boolean ssl,
			final String host, final int port) {
		if(port < 1 || port > 65535) throw new IllegalArgumentException("Invalid port number: "+Integer.toString(port));
		this.enabled = enabled;
		this.ssl    = ssl;
		this.host   = utils.isEmpty(host) ? null : host;
		this.port   = port;
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



}
*/