package com.growcontrol.server;

import java.util.Collection;
import java.util.Map;

import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.xThreadPool;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.Utils.xTimeU;
import com.poixson.commonjava.xLogger.xLevel;


public final class gcServerConfig extends xConfig {

	public static final String CONFIG_FILE = "config.yml";

	// key names
	public static final String VERSION       = "Version";
	public static final String LOG_LEVEL     = "Log Level";
	public static final String DEBUG         = "Debug";
	public static final String TICK_INTERVAL = "Tick Interval";
	public static final String LISTEN_PORT   = "Listen Port";
	public static final String LOGIC_THREADS = "Logic Threads";
	public static final String ZONES         = "Zones";

	// defaults
	public static final xTime default_TICK_INTERVAL = xTime.get("1s");
	public static final int default_LISTEN_PORT     = 1142;
	public static final int default_LOGIC_THREADS   = 0;



	public gcServerConfig(Map<String, Object> data) {
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



	// tick interval
	public xTime getTickInterval() {
		if(!exists(TICK_INTERVAL))
			return default_TICK_INTERVAL;
		{
			final Long value = getLong(TICK_INTERVAL);
			if(value != null)
				return xTime.get(value, xTimeU.MS);
		}
		{
			final String value = getString(TICK_INTERVAL);
			if(utils.notEmpty(value))
				return xTime.parse(value);
		}
		return default_TICK_INTERVAL;
	}



	// listen port
	public int getListenPort() {
		final Integer value = getInteger(LISTEN_PORT);
		if(value != null)
			return utilsNumbers.MinMax(value.intValue(), 1, 65536);
		return default_LISTEN_PORT;
	}



	// logic threads (0 uses main thread)
	public int getLogicThreads() {
		if(!exists(LOGIC_THREADS))
			return default_LOGIC_THREADS;
		final Integer value = getInteger(LOGIC_THREADS);
		return utilsNumbers.MinMax(value.intValue(), 0, xThreadPool.HARD_LIMIT);
	}



	// zones (rooms)
	public void populateZones(final Collection<String> zones) {
		if(zones == null) throw new NullPointerException();
		if(exists(ZONES))
			zones.addAll(getStringList(ZONES));
	}



}
