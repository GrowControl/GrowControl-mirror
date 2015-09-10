package com.growcontrol.server.plugins;

import com.growcontrol.common.meta.MetaAddress;
import com.growcontrol.common.meta.MetaListener;
import com.growcontrol.common.meta.MetaRouter;
import com.growcontrol.server.gcServerVars;
import com.poixson.commonapp.plugin.xJavaPlugin;
import com.poixson.commonjava.xEvents.xEventListener;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.commands.xCommandListener;


public abstract class gcServerPlugin extends xJavaPlugin {



	@Override
	public void register(final xEventListener listener) {
		if(listener == null) throw new NullPointerException("Cannot register null listener!");
		// command listener
		if(listener instanceof xCommandListener) {
			gcServerVars.commands()
				.register(listener);
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



	@Override
	public void unregister(final Class<? extends xEventListener> listenerClass) {
		if(listenerClass == null) throw new NullPointerException("Cannot unregister null listener!");
		// command listener
		if(xCommandListener.class.isInstance(listenerClass)) {
			gcServerVars.commands()
				.unregisterType(listenerClass);
		// unknown
		} else {
xLog.getRoot().warning("Unknown listener type, cannot unregister.");
//			throw new RuntimeException("Cannot register unknown listener type: "
//					+listenerClass.getName());
		}
	}



}
