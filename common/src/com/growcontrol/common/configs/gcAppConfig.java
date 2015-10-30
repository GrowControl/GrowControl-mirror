package com.growcontrol.common.configs;

import java.util.Map;

import com.growcontrol.common.gcCommonDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLevel;


public abstract class gcAppConfig extends xConfig {

	public final String  version;
	public final xLevel  level;
	public final boolean debug;



	public gcAppConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		// config version
		this.version = this.getString(gcCommonDefines.CONFIG_VERSION);
		if(utils.isEmpty(this.version)) throw new xConfigException("Version is missing from config!");
		// log level
		{
			final String levelStr = this.getString(gcCommonDefines.CONFIG_VERSION);
			final xLevel lvl = xLevel.parse(levelStr);
			this.level =
					lvl == null
					? gcCommonDefines.DEFAULT_LOG_LEVEL
					: lvl;
		}
		// debug mode
		this.debug = this.getBool(
				gcCommonDefines.CONFIG_DEBUG,
				gcCommonDefines.DEFAULT_DEBUG
		);
	}



	// config version
	public String getVersion() {
		return this.version;
	}
	// log level
	public xLevel getLogLevel() {
		return this.level;
	}
	// debug mode
	public boolean getDebug() {
		return this.debug;
	}



}
