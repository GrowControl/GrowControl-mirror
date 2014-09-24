package com.growcontrol.client.configs;

import java.util.Map;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.xLogger.xLevel;


public final class gcClientConfig extends xConfig {

	// client config
	public static final String CONFIG_FILE = gcClientDefines.CONFIG_FILE;

	// default values
	public static final xTime DEFAULT_TICK_INTERVAL = xTime.get("1s");
	public static final int   DEFAULT_LISTEN_PORT   = 1142;
	public static final int   DEFAULT_LOGIC_THREADS = 0;



	public gcClientConfig(final Map<String, Object> data) {
		super(data);
	}



	// version
	public String getVersion() {
		final String value = getString(gcClientDefines.CONFIG_VERSION);
		if(utils.notEmpty(value))
			return value;
		return null;
	}



	// log level
	public xLevel getLogLevel() {
		final String value = getString(gcClientDefines.CONFIG_LOG_LEVEL);
		if(utils.notEmpty(value))
			return xLevel.parse(value);
		return null;
	}



	// debug
	public Boolean getDebug() {
		return getBoolean(gcClientDefines.CONFIG_DEBUG);
	}



}
