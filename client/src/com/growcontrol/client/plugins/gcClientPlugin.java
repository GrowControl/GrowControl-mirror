package com.growcontrol.client.plugins;

import com.growcontrol.client.gcClientVars;
import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public abstract class gcClientPlugin extends xJavaPlugin {



	@Override
	public void register(final xListener listener) {
		if(listener == null) throw new NullPointerException("Cannot register null listener");
		final gcClientVars vars = gcClientVars.get();
		// command listener
		if(listener instanceof xCommandListener) {
			vars.commands().register(listener);
		// unknown
		} else {
			throw new RuntimeException("Cannot register unknown listener type: "
					+listener.getClass().getName());
		}
	}



}
