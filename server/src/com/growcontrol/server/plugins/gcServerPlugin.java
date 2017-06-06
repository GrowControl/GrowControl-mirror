package com.growcontrol.server.plugins;

import com.poixson.app.plugin.xJavaPlugin;


public abstract class gcServerPlugin extends xJavaPlugin {



	public gcServerPlugin() {}



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
*/



}
