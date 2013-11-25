package com.growcontrol.gcServer.serverPlugin;

import java.util.ArrayList;
import java.util.List;

import com.growcontrol.gcCommon.pxnPlugin.pxnPluginManager;


public class gcServerPluginManager extends pxnPluginManager {

	// main class field names for plugin.yml
	public final String mainClass_Server = "Server Main";
	public final String mainClass_Client = "Client Main";


	public static gcServerPluginManager get() {
		return get(null);
	}
	public static gcServerPluginManager get(String pluginsPath) {
		if(manager == null) {
			synchronized(lock) {
				if(manager == null)
					if(pluginsPath != null && !pluginsPath.isEmpty())
						manager = new gcServerPluginManager(pluginsPath);
			}
		}
		return (gcServerPluginManager) manager;
	}
	protected gcServerPluginManager(String pluginsPath) {
		super(pluginsPath);
	}


	// load jars from dir
	@Override
	public void LoadPluginsDir() {
		LoadPluginsDir(new String[] {
			mainClass_Server,
			mainClass_Client
		});
	}
	// init server plugin instances
	@Override
	public void InitPlugins() {
		InitPlugins(mainClass_Server);
	}


	// get client plugins
	// 0 | plugin name
	// 1 | version
	// 2 | filename
	public List<String[]> getClientPlugins() {
		List<String[]> clientPlugins = new ArrayList<String[]>();
		synchronized(plugins) {
			for(PluginHolder holder : plugins.values()) {
				String mainClass = holder.mainClasses.get(mainClass_Client);
				if(mainClass == null || mainClass.isEmpty())
					continue;
				// add to client plugin list
				clientPlugins.add(new String[] {
					holder.pluginName,
					holder.version,
					holder.file.toString()
				});
			}
		}
		if(clientPlugins.size() == 0)
			return null;
		return clientPlugins;
	}


}
