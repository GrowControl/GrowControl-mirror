package com.growcontrol.server.plugins;

import com.growcontrol.common.meta.MetaAddress;
import com.growcontrol.common.meta.MetaListener;
import com.growcontrol.common.meta.MetaRouter;
import com.growcontrol.server.gcServerVars;
import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public abstract class gcServerPlugin extends xJavaPlugin {



	@Override
	public void register(final xListener listener) {
		if(listener == null) throw new NullPointerException("Cannot register null listener");
		final gcServerVars vars = gcServerVars.get();
		// command listener
		if(listener instanceof xCommandListener) {
			vars.commands().register(listener);
		// unknown
		} else {
			throw new RuntimeException("Cannot register unknown listener type: "
					+listener.getClass().getName());
		}
	}
	public void register(final MetaAddress address, final MetaListener listener) {
		MetaRouter.get().register(
			address,
			listener
		);
	}



}
