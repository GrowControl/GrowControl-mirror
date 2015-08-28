package com.growcontrol.client;

import com.growcontrol.client.configs.ProfilesConfig;
import com.growcontrol.client.configs.gcClientConfig;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.xLogger.xLog;


public class gcClientVars {

	private static volatile boolean inited = false;

	// configs
	private static final Object configLock = new Object();
	private static gcClientConfig config         = null;
	private static ProfilesConfig profilesConfig = null;

	// handlers
	private static final xHandler system  = new xHandler();
	private static final xHandler plugins = new xHandler();



	public static void init() {
		if(!inited)
			Keeper.add(new gcClientVars());
	}
	private gcClientVars() {
	}



	// client config
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
							true
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



	// system event handler
	public static xHandler system() {
		return system;
	}



	// plugin event handler
	public static xHandler plugins() {
		return plugins;
	}



}
