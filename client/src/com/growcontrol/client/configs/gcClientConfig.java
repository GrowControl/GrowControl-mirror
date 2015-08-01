package com.growcontrol.client.configs;

import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLevel;


public final class gcClientConfig extends xConfig {

	private volatile Map<String, WindowConfig> windowConfigs = null;



	public gcClientConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// version
	public String getVersion() {
		final String value = this.getString(gcClientDefines.CONFIG_VERSION);
		if(utils.isEmpty(value))
			return null;
		return value;
	}



	// log level
	public xLevel getLogLevel() {
		final String value = this.getString(gcClientDefines.CONFIG_LOG_LEVEL);
		if(utils.isEmpty(value))
			return null;
		return xLevel.parse(value);
	}



	// debug
	public Boolean getDebug() {
		return this.getBoolean(gcClientDefines.CONFIG_DEBUG);
	}



	// windows configs
	public Map<String, WindowConfig> getWindowConfigs() {
		if(this.windowConfigs == null) {
			final Set<Object> dataset = this.getSet(
					Object.class,
					gcClientDefines.CONFIG_WINDOWS
			);
			this.windowConfigs = WindowConfig.get(dataset);
		}
		return this.windowConfigs;
	}
	public WindowConfig getWindowConfig(final String name) {
		if(this.windowConfigs == null)
			this.getWindowConfigs();
		return this.windowConfigs.get(name);
	}



}
