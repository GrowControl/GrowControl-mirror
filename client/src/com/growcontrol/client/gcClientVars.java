package com.growcontrol.client;

import com.growcontrol.client.configs.gcClientConfig;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.Utils.Keeper;


public class gcClientVars {



	private static volatile boolean inited = false;
	public static void init() {
		if(!inited)
			Keeper.add(new gcClientVars());
	}
	private gcClientVars() {
	}



	// client config
	private static gcClientConfig config = null;
	private static final Object configLock = new Object();
	public static gcClientConfig getConfig() {
		if(config == null) {
			synchronized(configLock) {
				if(config == null) {
					config = (gcClientConfig) xConfigLoader.Load(
							gcClientDefines.CONFIG_FILE,
							gcClientConfig.class,
							true
					);
					if(config == null) {
						Failure.fail("Failed to load "+gcClientDefines.CONFIG_FILE);
						return null;
					}
					if(config.isFromResource())
						gcClient.log().warning("Created default "+gcClientDefines.CONFIG_FILE);
				}
			}
		}
		return config;
	}



	// system event handler
	private static final xHandler system = new xHandler();
	public static xHandler system() {
		return system;
	}



//	// command event handler
//	private static final xHandler commands = new xHandler();
//	public static xHandler commands() {
//		return commands;
//	}



	// plugin event handler
	private static final xHandler plugins = new xHandler();
	public static xHandler plugins() {
		return plugins;
	}



//	// meta router handler
//	private static final xHandler router;
//	public static xHandler router() {
//		return router;
//	}



}
