package com.growcontrol.api.clientapi.plugins;

import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.xEvents.xEventListener;


public abstract class apiClientPlugin extends xJavaPlugin {



	@Override
	public void unregister(final Class<? extends xEventListener> listenerClass) {
		if(listenerClass == null) throw new NullPointerException("Cannot unregister null listener!");
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



}
