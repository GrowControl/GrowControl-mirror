package com.growcontrol.server;

import com.growcontrol.common.meta.MetaRouter;
import com.growcontrol.server.configs.gcServerConfig;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.commands.xCommandsHandler;


public class gcServerVars {

	private static volatile boolean inited = false;

	// internal mode
	private static volatile boolean internal = false;

	// configs
	private static gcServerConfig config = null;
	private static final Object configLock = new Object();

	// handlers
//	private static final xSystemHandler system = new xHandlerSystem();
	private static final xCommandsHandler      commandsHandler = xCommandsHandler.get();
//	private static final xPluginsEventHandler  pluginsHandler  = xPluginsEventHandler.get();
	private static final MetaRouter router = MetaRouter.get();



	public static void init() {
		if(!inited)
			Keeper.add(new gcServerVars());
	}
	private gcServerVars() {
	}



	public static boolean isInternal() {
		return internal;
	}
	public static void setInternal(final boolean value) {
		internal = value;
	}



	// server config
	public static gcServerConfig getConfig() {
		if(config == null) {
			synchronized(configLock) {
				if(config == null) {
					config = (gcServerConfig) xConfigLoader.Load(
							xLog.getRoot(),
							null,
							gcServerDefines.CONFIG_FILE,
							gcServerConfig.class,
							gcServer.class
					);
					if(config == null) {
						Failure.fail("Failed to load "+gcServerDefines.CONFIG_FILE);
						return null;
					}
					if(config.isFromResource())
						xLog.getRoot(gcServerConfig.LOG_NAME).warning("Created default "+gcServerDefines.CONFIG_FILE);
				}
			}
		}
		return config;
	}



//	// system event handler
//	public static xHandler system() {
//		return system;
//	}



	// command event handler
	public static xCommandsHandler commands() {
		return commandsHandler;
	}



//	// plugin event handler
//	public static xPluginsEventHandler plugins() {
//		return pluginsHandler;
//	}



	// meta event handler
	public static MetaRouter router() {
		return router;
	}



}
