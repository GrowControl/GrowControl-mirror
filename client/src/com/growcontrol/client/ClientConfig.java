package com.growcontrol.gcClient;

import com.growcontrol.gcCommon.pxnConfig.pxnConfig;
import com.growcontrol.gcCommon.pxnConfig.pxnConfigLoader;


public final class ClientConfig {
	private ClientConfig() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static final String CONFIG_FILE = "config.yml";
	private static volatile String configPath = null;

	// config dao
	private static volatile pxnConfig config = null;
	private static final Object lock = new Object();


	public static pxnConfig get() {
		if(config == null) {
			synchronized(lock) {
				if(config == null)
					config = pxnConfigLoader.Load(configPath, CONFIG_FILE);
			}
		}
		return config;
	}
	public static boolean isLoaded() {
		return (config != null);
	}


	// configs path
	public static String getPath() {
		if(configPath == null || configPath.isEmpty())
			return "./";
		return configPath;
	}
	public static void setPath(String path) {
		configPath = path;
	}


	// version
	public static String Version() {
		pxnConfig config = get();
		if(config == null) return null;
		return config.getString("Version");
	}
	// log level
	public static String LogLevel() {
		pxnConfig config = get();
		if(config == null) return null;
		return config.getString("Log Level");
	}


}
