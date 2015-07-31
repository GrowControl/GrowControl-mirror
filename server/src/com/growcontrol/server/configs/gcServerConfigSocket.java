package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.common.netty.NetConfig;
import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;


public class gcServerConfigSocket extends xConfig {



	public gcServerConfigSocket(final Map<String, Object> datamap) {
		super(datamap);
	}



	public NetConfig get() {
		final boolean enable =
				this.exists(gcServerDefines.CONFIG_SOCKET_ENABLE)
				? this.getBool(gcServerDefines.CONFIG_SOCKET_ENABLE, false)
				: true;
		final boolean ssl = this.getBool(gcServerDefines.CONFIG_SOCKET_SSL, false);
		final String host = this.getString(gcServerDefines.CONFIG_SOCKET_HOST);
		final int port = this.getInt(
				gcServerDefines.CONFIG_SOCKET_PORT,
				ssl ? 1143 : 1142
		);
		return new NetConfig(
				enable,
				ssl,
				host,
				port
		);
	}



}
