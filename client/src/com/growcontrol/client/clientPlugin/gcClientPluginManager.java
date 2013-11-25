package com.growcontrol.gcClient.clientPlugin;

import com.growcontrol.gcCommon.pxnPlugin.pxnPluginManager;


public class gcClientPluginManager extends pxnPluginManager {

	// main class field names for plugin.yml
	public final String mainClass_Server = "Server Main";
	public final String mainClass_Client = "Client Main";


	public static gcClientPluginManager get() {
		return get(null);
	}
	public static gcClientPluginManager get(String pluginsPath) {
		if(manager == null) {
			synchronized(lock) {
				if(manager == null)
					if(pluginsPath != null && !pluginsPath.isEmpty())
						manager = new gcClientPluginManager(pluginsPath);
			}
		}
		return (gcClientPluginManager) manager;
	}
	protected gcClientPluginManager(String pluginsPath) {
		super(pluginsPath);
	}


	// load jars from dir
	@Override
	public void LoadPluginsDir() {
		LoadPluginsDir(new String[] {
			mainClass_Client
		});
	}
	// init client plugin instances
	@Override
	public void InitPlugins() {
		InitPlugins(mainClass_Client);
	}


//	// client gui frames for plugins
//	public gcPluginFrame newFrame(String title) {
//		gcPluginFrame frame = new gcPluginFrame(title);
//		addFrame(frame);
//		return frame;
//	}
//	public void addFrame(gcPluginFrame frame) {
//		frames.add(frame);
//	}
//	// add frame to dashboard
//	public void addFrame(gcPluginFrame frame) {
//		Main.getClient().getConnectState().getFrame("dashboard");
//	}


}
