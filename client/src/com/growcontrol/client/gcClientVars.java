package com.growcontrol.client;

import com.growcontrol.client.plugins.gcClientPlugin;
import com.poixson.app.plugin.xPluginManager;
import com.poixson.utils.Keeper;


public class gcClientVars {
	private gcClientVars() {}
	{ Keeper.add(new gcClientVars()); }



	// ------------------------------------------------------------------------------- //
	// plugin manager



	private static volatile xPluginManager<gcClientPlugin> pluginManager = null;

	public static xPluginManager<gcClientPlugin> getPluginManager() {
		return pluginManager;
	}
	public static void setPluginManager(final xPluginManager<gcClientPlugin> manager) {
		pluginManager = manager;
	}



/*
	// configs
	private static final Object configLock = new Object();
	private static gcClientConfig config         = null;
	private static ProfilesConfig profilesConfig = null;

	// handlers
	//private static final xHandlerSystem  system  = new xHandlerSystem();
//	private static final xPluginsEventHandler pluginsHandler = xPluginsEventHandler.get();



	// client config
	public static gcClientConfig getConfig() {
		if(config == null) {
			synchronized(configLock) {
				if(config == null) {
					config = (gcClientConfig) xConfigLoader.Load(
							gcClientDefines.CONFIG_FILE,
							gcClientConfig.class,
							gcClient.class
					);
					if(config == null) {
						Failure.fail("Failed to load "+gcClientDefines.CONFIG_FILE);
						return null;
					}
					if(config.isFromResource())
						xLog.getRoot(gcClientConfig.LOG_NAME).warning("Created default "+gcClientDefines.CONFIG_FILE);
				}
			}
		}
		return config;
	}



	// client config
	public static ProfilesConfig getProfilesConfig() {
		if(profilesConfig == null) {
			synchronized(configLock) {
				if(profilesConfig == null) {
					profilesConfig = (ProfilesConfig) xConfigLoader.Load(
							gcClientDefines.PROFILES_FILE,
							ProfilesConfig.class,
							gcClient.class
					);
					if(profilesConfig == null) {
						Failure.fail("Failed to load "+gcClientDefines.PROFILES_FILE);
						return null;
					}
					if(profilesConfig.isFromResource())
						xLog.getRoot("CLIENT").warning("Created default "+gcClientDefines.PROFILES_FILE);
				}
			}
		}
		return profilesConfig;
	}



//	// system event handler
//	public static xHandlerSystem system() {
//		return system;
//	}



//	// plugin event handler
//	public static xPluginsEventHandler plugins() {
//		return pluginsHandler;
//	}
*/



}
