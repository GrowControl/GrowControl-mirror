package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.xThreadPool;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.Utils.xTimeU;
import com.poixson.commonjava.xLogger.xLevel;


public final class gcServerConfig extends xConfig {

	// server config
	public static final String CONFIG_FILE = gcServerDefines.CONFIG_FILE;

	// defaults
	public static final xTime default_TICK_INTERVAL = xTime.get("5s");
	public static final int default_LISTEN_PORT     = 1142;
	public static final int default_LOGIC_THREADS   = 0;



	public gcServerConfig(Map<String, Object> data) {
		super(data);
	}



	// version
	public String getVersion() {
		final String value = getString(gcServerDefines.CONFIG_VERSION);
		if(utils.isEmpty(value))
			return null;
		return value;
	}



	// log level
	public xLevel getLogLevel() {
		final String value = getString(gcServerDefines.CONFIG_LOG_LEVEL);
		if(utils.isEmpty(value))
			return null;
		return xLevel.parse(value);
	}



	// debug
	public Boolean getDebug() {
		return getBoolean(gcServerDefines.CONFIG_DEBUG);
	}



	// tick interval
	public xTime getTickInterval() {
		if(!exists(gcServerDefines.CONFIG_TICK_INTERVAL))
			return default_TICK_INTERVAL;
		{
			final Long value = getLong(gcServerDefines.CONFIG_TICK_INTERVAL);
			if(value != null)
				return xTime.get(value, xTimeU.MS);
		}
		{
			final String value = getString(gcServerDefines.CONFIG_TICK_INTERVAL);
			if(utils.notEmpty(value))
				return xTime.parse(value);
		}
		return default_TICK_INTERVAL;
	}



	// listen port
	public int getListenPort() {
		final Integer value = getInteger(gcServerDefines.CONFIG_LISTEN_PORT);
		if(value == null)
			return default_LISTEN_PORT;
		return utilsNumbers.MinMax(value.intValue(), 1, 65536);
	}



	// logic threads (0 uses main thread)
	public int getLogicThreads() {
		if(!exists(gcServerDefines.CONFIG_LOGIC_THREADS))
			return default_LOGIC_THREADS;
		final Integer value = getInteger(gcServerDefines.CONFIG_LOGIC_THREADS);
		return utilsNumbers.MinMax(value.intValue(), 0, xThreadPool.HARD_LIMIT);
	}



//	// zones (rooms)
//	public void populateZones(final Collection<String> zones) {
//		if(zones == null) throw new NullPointerException();
//		if(exists(gcServerDefines.CONFIG_ZONES))
//			zones.addAll(getStringList(gcServerDefines.CONFIG_ZONES));
//	}



	// socket
	public gcSocketServerConfig getSocketConfig(final boolean ssl) {
		return gcSocketServerConfig.get(this, ssl);
	}



}
