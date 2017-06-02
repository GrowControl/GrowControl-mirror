package com.growcontrol.client.plugins;

import com.poixson.app.plugin.xJavaPlugin;
import com.poixson.app.plugin.xPluginManager;
import com.poixson.app.plugin.xPluginYML;


public abstract class gcClientPlugin extends xJavaPlugin {



	public gcClientPlugin(final xPluginManager<?> manager, final xPluginYML yml) {
		super(manager, yml);
	}



	@Override
	protected void onInit() {
		super.onInit();
	}
	@Override
	protected void onUnload() {
		super.onUnload();
	}



	@Override
	protected abstract void onEnable();
	@Override
	protected abstract void onDisable();



//TODO:
/*
	@Override
	public void unregister(final Class<? extends xEventListener> listenerClass) {
		if(listenerClass == null) throw new IllegalArgumentException("Cannot unregister null listener!");
//		// command listener
//		if(xCommandListener.class.isInstance(listenerClass)) {
//			gcClientVars.commands()
//				.unregister(listenerClass);
//		// unknown
//		} else {
			throw new RuntimeException("Cannot register unknown listener type: "
					+listenerClass.getName());
//		}
	}
*/



}
