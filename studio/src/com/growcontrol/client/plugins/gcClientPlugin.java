package com.growcontrol.client.plugins;

import com.growcontrol.client.studio.gcStudio;
import com.growcontrol.client.studio.configs.gcStudioConfig;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;
import com.poixson.tools.config.xConfig;


public abstract class gcClientPlugin extends xJavaPlugin {

	public static final String KEY_CLASS_MAIN = "Client Main";

	protected final gcStudio client;



	public gcClientPlugin(final gcStudio client,
			final xPluginManager<gcClientPlugin> manager, final xPluginYML yml) {
		super(manager, yml);
		this.client = client;
	}



	public gcStudio getClient() {
		return this.client;
	}
	public xConfig getPluginConfig() {
		final gcStudio client = this.getClient();
		final gcStudioConfig config = client.getConfig();
		return config.getConfig(this.getPluginNameSafe());
	}



}
