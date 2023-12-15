package com.growcontrol.server.plugins;

import com.growcontrol.server.gcServer;
import com.growcontrol.server.configs.gcServerConfig;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;
import com.poixson.tools.config.xConfig;


public abstract class gcServerPlugin extends xJavaPlugin {

	public static final String KEY_CLASS_MAIN = "Server Main";

	protected final gcServer server;



	public gcServerPlugin(final gcServer server,
			final xPluginManager<gcServerPlugin> manager, final xPluginYML yml) {
		super(manager, yml);
		this.server = server;
	}



	public gcServer getServer() {
		return this.server;
	}
	public xConfig getPluginConfig() {
		final gcServer server = this.getServer();
		final gcServerConfig config = server.getConfig();
		return config.getConfig(this.getPluginNameSafe());
	}



//TODO
/*
	// register meta listener
	public void register(final MetaAddress address, final MetaListener listener) {
		if (address  == null) throw new RequiredArgumentException("address");
		if (listener == null) throw new RequiredArgumentException("listener");
		final MetaRouter router = MetaRouter.get();
		router.register(
			address,
			listener
		);
	}
*/



}
