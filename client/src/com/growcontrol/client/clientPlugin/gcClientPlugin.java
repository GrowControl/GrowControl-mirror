package com.growcontrol.gcClient.clientPlugin;

import com.growcontrol.gcCommon.pxnPlugin.pxnPlugin;


public abstract class gcClientPlugin  extends pxnPlugin {

//	// plugin owned frames
//	protected List<gcPluginFrame> frames = new ArrayList<gcPluginFrame>();


//	// get plugin manager
//	@Override
//	public gcClientPluginManager getPluginManager() {
//		return (gcClientPluginManager) super.getPluginManager();
//	}


//	// add frame to dashboard
//	public void addFrame(gcPluginFrame frame) {
//		// owned by plugin
//		frames.add(frame);
//		// add frame to dashboard
//		getPluginManager().addFrame(frame);
//	}


//	// client gui frames for plugins
//	public gcPluginFrame newFrame(String title) {
//		gcClientPluginManager pluginManager = getPluginManager();
//		if(pluginManager == null) throw new NullPointerException("pluginManager hasn't been set!");
//		return pluginManager.newFrame(title);
//	}
//	public void addFrame(gcPluginFrame frame) {
//		gcClientPluginManager pluginManager = getPluginManager();
//		if(pluginManager == null) throw new NullPointerException("pluginManager hasn't been set!");
//		pluginManager.addFrame(frame);
//	}


}
