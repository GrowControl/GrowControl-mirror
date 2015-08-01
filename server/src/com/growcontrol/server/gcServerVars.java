package com.growcontrol.server;

import com.growcontrol.server.configs.gcServerConfig;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.Utils.Keeper;


public class gcServerVars {



	private static volatile boolean inited = false;
	public static void init() {
		if(!inited)
			Keeper.add(new gcServerVars());
	}
	private gcServerVars() {
	}



	// server config
	private static gcServerConfig config = null;
	private static final Object configLock = new Object();
	public static gcServerConfig getConfig() {
		if(config == null) {
			synchronized(configLock) {
				if(config == null) {
					config = (gcServerConfig) xConfigLoader.Load(
							gcServerDefines.CONFIG_FILE,
							gcServerConfig.class,
							true
					);
					if(config == null) {
						Failure.fail("Failed to load "+gcServerDefines.CONFIG_FILE);
						return null;
					}
					if(config.isFromResource())
						gcServer.log().warning("Created default "+gcServerDefines.CONFIG_FILE);
				}
			}
		}
		return config;
	}



//	// system event handler
//	private static final xHandler system = new xHandler();
//	public static xHandler system() {
//		return system;
//	}



	// command event handler
	private static final xHandler commands = new xHandler();
	public static xHandler commands() {
		return commands;
	}



	// plugin event handler
	private static final xHandler plugins = new xHandler();
	public static xHandler plugins() {
		return plugins;
	}



//	// meta router handler
//	private static final MetaRouter router = MetaRouter.get();
//	public static MetaRouter router() {
//		return router;
//	}



}
