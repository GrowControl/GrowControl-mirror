/*
package com.growcontrol.client.plugins;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.client.gcClientDefines;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.loaders.xPluginLoader_Dir;
import com.poixson.tools.Keeper;


public class gcClientPluginManager extends xPluginManager<gcClientPlugin> {
	private static final String LOG_NAME = "gcClientPlugin";

	private static final AtomicReference<gcClientPluginManager> instance =
			new AtomicReference<gcClientPluginManager>(null);



	public static gcClientPluginManager get() {
		// existing instance
		{
			final gcClientPluginManager manager = instance.get();
			if (manager != null)
				return manager;
		}
		// new instance
		{
			final gcClientPluginManager manager = new gcClientPluginManager();
			if (instance.compareAndSet(null, manager))
				return manager;
			return instance.get();
		}
	}
	protected gcClientPluginManager() {
		super(LOG_NAME);
		// plugin loaders
		{
			final xPluginLoader_Dir<gcClientPlugin> loader =
				new xPluginLoader_Dir<gcClientPlugin>(
					this,
					gcClientDefines.CONFIG_PLUGIN_CLASS_KEY_CLIENT
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



//TODO:
/ *
	// client gui frames for plugins
	public gcPluginFrame newFrame(String title) {
		gcPluginFrame frame = new gcPluginFrame(title);
		addFrame(frame);
		return frame;
	}
	public void addFrame(gcPluginFrame frame) {
		frames.add(frame);
	}
	// add frame to dashboard
	public void addFrame(gcPluginFrame frame) {
		Main.getClient().getConnectState().getFrame("dashboard");
	}
* /



}
*/
