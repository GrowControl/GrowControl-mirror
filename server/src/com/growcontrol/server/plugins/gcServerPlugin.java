/*
package com.growcontrol.api.serverapi.plugins;

import com.growcontrol.common.meta.MetaAddress;
import com.growcontrol.common.meta.MetaListener;
import com.growcontrol.common.meta.MetaRouter;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.xEvents.xEventListener;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.commands.xCommandListener;


public abstract class apiServerPlugin extends xJavaPlugin {



	// register meta listener
	public void register(final MetaAddress address, final MetaListener listener) {
		if(address  == null) throw new IllegalArgumentException("Cannot register null meta address!");
		if(listener == null) throw new IllegalArgumentException("Cannot register null meta listener!");
		final MetaRouter router = MetaRouter.get();
		router.register(
			address,
			listener
		);
	}
	// register command handler
	public void register(final xCommandListener listener) {
		if(listener == null) throw new IllegalArgumentException("Cannot register null command listener!");
		xApp.get().getCommandsHandler()
			.register(listener);
	}
	// unregister listener type
	@Override
	public void unregister(final Class<? extends xEventListener> listenerClass) {
		if(listenerClass == null) throw new IllegalArgumentException("Cannot unregister null listener!");
		// command listener
		if(xCommandListener.class.isInstance(listenerClass)) {
			xApp.get().getCommandsHandler()
				.unregisterType(listenerClass);
		// unknown
		} else {
			xLog.getRoot().warning("Unknown listener type, cannot unregister!");
//			throw new RuntimeException("Cannot register unknown listener type: "
//					+listenerClass.getName());
		}
	}



}
*/
