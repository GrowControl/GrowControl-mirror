package com.growcontrol.client.configs;

import java.util.Map;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLevel;


public final class gcClientConfig extends xConfig {



	public gcClientConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// version
	public String getVersion() {
		final String value = getString(gcClientDefines.CONFIG_VERSION);
		if(utils.isEmpty(value))
			return null;
		return value;
	}



	// log level
	public xLevel getLogLevel() {
		final String value = getString(gcClientDefines.CONFIG_LOG_LEVEL);
		if(utils.isEmpty(value))
			return null;
		return xLevel.parse(value);
	}



	// debug
	public Boolean getDebug() {
		return getBoolean(gcClientDefines.CONFIG_DEBUG);
	}



}
