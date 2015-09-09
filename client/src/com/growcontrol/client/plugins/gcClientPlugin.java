package com.growcontrol.client.plugins;

import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.xEvents.xEventListener;


public abstract class gcClientPlugin extends xJavaPlugin {



	@Override
	public void register(final xEventListener listener) {
		if(listener == null) throw new NullPointerException("Cannot register null listener!");
//		// command listener
//		if(listener instanceof xCommandListener) {
//			gcClientVars.commands()
//				.register(listener);
		// unknown
//		} else {
			throw new RuntimeException("Cannot register unknown listener type: "
					+listener.getClass().getName());
//		}
	}



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
