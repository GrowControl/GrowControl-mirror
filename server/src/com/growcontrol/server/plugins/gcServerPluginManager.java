/*
package com.growcontrol.server.plugins;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.server.gcServerDefines;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.loaders.xPluginLoader_Dir;
import com.poixson.tools.Keeper;


public class gcServerPluginManager extends xPluginManager<gcServerPlugin> {
	private static final String LOG_NAME = "gcServerPlugin";

	private static final AtomicReference<gcServerPluginManager> instance =
			new AtomicReference<gcServerPluginManager>(null);



	public static gcServerPluginManager get() {
		// existing instance
		{
			final gcServerPluginManager manager = instance.get();
			if (manager != null)
				return manager;
		}
		// new instance
		{
			final gcServerPluginManager manager = new gcServerPluginManager();
			if (instance.compareAndSet(null, manager))
				return manager;
			return instance.get();
		}
	}
	protected gcServerPluginManager() {
		super(LOG_NAME);
		// plugin loaders
		{
			final xPluginLoader_Dir<gcServerPlugin> loader =
				new xPluginLoader_Dir<gcServerPlugin>(
					this,
					gcServerDefines.CONFIG_PLUGIN_CLASS_KEY_SERVER
				);
			this.addLoader(loader);
		}
		Keeper.add(this);
	}



	// ------------------------------------------------------------------------------- //
	// load/unload plugins



	@xAppStep( Type=StepType.START, Title="Plugins-Load", StepValue=400 )
	public void START_load_plugins() {
		super.start();
	}



	@xAppStep( Type=StepType.STOP, Title="Plugins-Unload", StepValue=400 )
	public void STOP_unload_plugins() {
		super.stop();
	}



}
*/
