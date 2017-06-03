package com.growcontrol.server;

import com.growcontrol.server.plugins.gcServerPlugin;
import com.poixson.app.plugin.xPluginManager;
import com.poixson.utils.Keeper;


public class gcServerVars {
	private gcServerVars() {}
	{ Keeper.add(new gcServerVars()); }



//	// internal mode
//	private static volatile boolean internal = false;

	// configs
//	private static gcServerConfig config = null;
//	private static final Object configLock = new Object();

	// handlers
//	private static final xSystemHandler system = new xHandlerSystem();
//	private static final xCommandsHandler commandsHandler = xCommandsHandler.get();
//	private static final xPluginsEventHandler pluginsHandler = xPluginsEventHandler.get();
//	private static final MetaRouter router = MetaRouter.get();



	// ------------------------------------------------------------------------------- //
	// app mode



	public static enum APP_MODE {
		SERVER_ONLY  ( (byte)1 ),
		CLIENT_ONLY  ( (byte)2 ),
		SERVER_CLIENT( (byte)3 ),
		INTERNAL     ( (byte)4 );
		private final byte mode;
		APP_MODE(final byte mode) {
			this.mode = mode;
		}
		public byte getValue() {
			return this.mode;
		}
		public boolean equals(final APP_MODE compare) {
			if (compare == null)
				return false;
			return (
				this.getValue() == compare.getValue()
			);
		}
	}
	private static volatile APP_MODE appMode = null;

	public static APP_MODE getAppMode() {
		return appMode;
	}
	public static APP_MODE peekAppMode() {
		return appMode;
	}
	public static void setAppMode(final APP_MODE mode) {
		appMode = mode;
	}



	// ------------------------------------------------------------------------------- //
	// plugin manager



	private static volatile xPluginManager<gcServerPlugin> pluginManager = null;

	public static xPluginManager<gcServerPlugin> getPluginManager() {
		return pluginManager;
	}
	public static void setPluginManager(final xPluginManager<gcServerPlugin> manager) {
		pluginManager = manager;
	}



/*
	// server config
	public static gcServerConfig getConfig() {
		if (config == null) {
			synchronized(configLock) {
				if (config == null) {
					try {
						config = (gcServerConfig) xConfig.Load(
								null,
								null,
								gcServerDefines.CONFIG_FILE,
								gcServerConfig.class,
								gcServer.class
						);
					} catch (xConfigException e) {
						xLog.getRoot().trace(e);
						config = null;
					}
					if (config == null) {
						Failure.fail("Failed to load "+gcServerDefines.CONFIG_FILE);
						return null;
					}
					if (config.isFromResource())
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



//	// command event handler
//	public static xCommandsHandler commands() {
//		return commandsHandler;
//	}



//	// plugin event handler
//	public static xPluginsEventHandler plugins() {
//		return pluginsHandler;
//	}



//	// meta event handler
//	public static MetaRouter router() {
//		return router;
//	}
*/



}
