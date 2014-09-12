package com.growcontrol.client;

import java.util.Map;

import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLevel;


public final class ClientConfig extends xConfig {

	public static final String CONFIG_FILE = "config.yml";

	// key names
	public static final String VERSION       = "Version";
	public static final String LOG_LEVEL     = "Log Level";
	public static final String DEBUG         = "Debug";

	// defaults


	public ClientConfig(Map<String, Object> data) {
		super(data);
	}


	// version
	public String getVersion() {
		final String value = getString(VERSION);
		if(utils.notEmpty(value))
			return value;
		return null;
	}


	// log level
	public xLevel getLogLevel() {
		final String value = getString(LOG_LEVEL);
		if(utils.notEmpty(value))
			return xLevel.parse(value);
		return null;
	}


	// debug
	public Boolean getDebug() {
		return getBoolean(DEBUG);
	}


}
