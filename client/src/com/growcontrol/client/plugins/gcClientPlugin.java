package com.growcontrol.client.plugins;

import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.EventListener.xListener;


public abstract class gcClientPlugin extends xJavaPlugin {



	@Override
	public void register(final xListener listener) {
		if(listener == null) throw new NullPointerException("Cannot register null listener");
		// command listener
//		if(listener instanceof xCommandListener) {
//			gcClientVars.commands()
//				.register(listener);
		// unknown
//		} else {
			throw new RuntimeException("Cannot register unknown listener type: "
					+listener.getClass().getName());
//		}
	}



}
