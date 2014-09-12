package com.growcontrol.client.configs;

import java.util.ArrayList;
import java.util.Map;

import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.xLogger.xLevel;


public final class ClientConfig extends xConfig {

	public static final String CONFIG_FILE = "config.yml";

	// key names
	public static final String VERSION       = "Version";
	public static final String LOG_LEVEL     = "Log Level";
	public static final String DEBUG         = "Debug";
	public static final String TICK_INTERVAL = "Tick Interval";
	public static final String LISTEN_PORT   = "Listen Port";
	public static final String LOGIC_THREADS = "Logic Threads";
	public static final String ZONES         = "Zones";

	// default values
	public static final xTime DEFAULT_TICK_INTERVAL = xTime.get("1s");
	public static final int   DEFAULT_LISTEN_PORT   = 1142;
	public static final int   DEFAULT_LOGIC_THREADS = 0;



	public ClientConfig(final Map<String, Object> data) {
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
		final String str = this.getString(TICK_INTERVAL);
		if(utils.isEmpty(str))
			return DEFAULT_TICK_INTERVAL;
		return xTime.get(str);
	}



	// listen port
	public int getListenPort() {
		final Integer i = this.getInteger(LISTEN_PORT);
		if(i == null)
			return DEFAULT_LISTEN_PORT;
		return i.intValue();
	}



	// logic threads
	public int getLogicThreads() {
		final Integer i = this.getInteger(LOGIC_THREADS);
		if(i == null)
			return DEFAULT_LOGIC_THREADS;
		return i.intValue();
	}



	// zones
	@SuppressWarnings("unchecked")
	public String[] getZones() {
		return ((ArrayList<String>) this.data.get(ZONES)).toArray(new String[0]);
	}



}
