package com.growcontrol.client.plugins;

import com.poixson.plugins.xJavaPlugin;


public abstract class gcClientPlugin extends xJavaPlugin {



	public gcClientPlugin() {
		super();
	}



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
