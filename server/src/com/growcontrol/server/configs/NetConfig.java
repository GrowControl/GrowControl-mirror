package com.growcontrol.common.netty;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.poixson.commonjava.Utils.utils;


public class NetConfig {

	public final boolean enabled;
	public final boolean ssl;
	public final String  host;
	public final int     port;



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
