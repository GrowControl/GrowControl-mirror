package com.growcontrol.client.studio;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.client.plugins.gcClientPlugin;
import com.growcontrol.client.plugins.gcClientPluginFactory;
import com.growcontrol.client.plugins.gcClientPluginManager;
import com.growcontrol.client.studio.app.steps.gcStudio_Logo;
import com.growcontrol.client.studio.configs.gcStudioConfig;
import com.growcontrol.client.studio.gui.xWindow_Studio_Main;
import com.growcontrol.server.gcServer;
import com.poixson.app.xApp;
import com.poixson.app.xAppMoreSteps;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepDAO;
import com.poixson.app.xAppStepMultiFinishedException;
import com.poixson.app.xAppStepType;
import com.poixson.app.steps.xAppSteps_LockFile;
import com.poixson.app.steps.xAppSteps_UserNotRoot;
import com.poixson.plugins.loaders.xPluginLoader_Dir;
import com.poixson.tools.xWindow;
import com.poixson.tools.config.xConfigLoader;


/*
 * Startup sequence
 *    5 | prevent root     | xAppSteps_UserNotRoot
 *   10 | startup time     | xApp
 *   15 | lock file        | xAppSteps_LockFile
 *   18 | start server
 *   20 | display logo     | gcClient_Logo
 *   50 | load configs
 *  350 | main window
 *  400 | load plugins
 *  405 | init plugins
 *  410 | start plugins
 *  450 | start scripts
 *
 * Shutdown sequence
 *  450 | stop scripts
 *  410 | stop plugins
 *  400 | unload plugins
 *   60 | display uptime    | xApp
 *   50 | stop thread pools | xApp
 *   15 | release lock file | xAppSteps_LockFile
 *   10 | garbage collect   | xApp
 *    1 | exit              | xApp
 */
public class gcStudio extends xApp {
	public static final String DEFAULT_LOCK_FILE = "gcStudio.lock";

	protected final AtomicReference<gcStudioConfig>        config  = new AtomicReference<gcStudioConfig>(null);
	protected final AtomicReference<gcClientPluginManager> plugins = new AtomicReference<gcClientPluginManager>(null);
//TODO
//	protected final AtomicReference<gcScriptManager>       scripts = new AtomicReference<gcScriptManager>(null);

	protected final AtomicReference<xWindow> windowMain = new AtomicReference<xWindow>(null);

	public static final AtomicReference<gcServer> instance_server = new AtomicReference<gcServer>(null);



	public gcStudio(final String[] args) {
		super(args);
	}



	@xAppMoreSteps
	public Object[] steps(final xAppStepType type) {
//TODO
		final String lock_file = DEFAULT_LOCK_FILE;
		return new Object[] {
			new xAppSteps_UserNotRoot(),       //  5
			new xAppSteps_LockFile(lock_file), // 15
			new gcStudio_Logo(this),           // 20
		};
	}



	// -------------------------------------------------------------------------------
	// gui



	public xWindow getWindowMain() {
		// existing instance
		{
			final xWindow window = this.windowMain.get();
			if (window != null)
				return window;
		}
		// new instance
		try {
			final xWindow_Studio_Main window = new xWindow_Studio_Main(this);
			if (this.windowMain.compareAndSet(null, window))
				return window;
		} catch (Exception e) {
			this.fail(e);
		}
		return this.windowMain.get();
	}



	// -------------------------------------------------------------------------------
	// configs



	public gcStudioConfig getConfig() {
		// existing instance
		{
			final gcStudioConfig config = this.config.get();
			if (config != null)
				return config;
		}
		// new config instance
		{
			final gcStudioConfig config = xConfigLoader.FromFile("gcStudio.yml", gcStudioConfig.class);
			if (this.config.compareAndSet(null, config))
				return config;
		}
		return this.config.get();
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// start server
	@xAppStep(type=xAppStepType.STARTUP, title="StartServer", step=18)
	public void __STARTUP__server() {
		final gcServer server = new gcServer(this.getArgs());
		instance_server.set(server);
	}



	// load configs
	@xAppStep(type=xAppStepType.STARTUP, title="Configs", step=50)
	public void __STARTUP__load_configs() {
		this.getConfig();
	}



	// main window
	@xAppStep(type=xAppStepType.STARTUP, title="WindowStudioMain", step=350)
	public void __START__window_main() {
		final xWindow window = this.getWindowMain();
		window.setVisible();
	}



	// plugins
	@xAppStep(type=xAppStepType.STARTUP, title="LoadPlugins", step=400)
	public void __STARTUP__plugins_load() {
		final gcClientPluginManager manager = this.getPluginManager();
		// plugin loader
		{
//TODO: plugin dir
			final xPluginLoader_Dir<gcClientPlugin> loader =
				new xPluginLoader_Dir<gcClientPlugin>(
					manager,
					new gcClientPluginFactory<gcClientPlugin>(this),
					gcClientPlugin.KEY_CLASS_MAIN,
					null
				);
			manager.removeAllLoaders();
			manager.addLoader(loader);
		}
		manager.load();
	}

	@xAppStep(type=xAppStepType.STARTUP, title="InitPlugins", step=405, multi=true)
	public void __STARTUP__plugins_init(final xAppStepDAO step) {
		final gcClientPluginManager manager = this.plugins.get();
		if (step.isFirstLoop())
			manager.initAll();
		if (manager.run())
			throw new xAppStepMultiFinishedException();
	}

	@xAppStep(type=xAppStepType.STARTUP, title="StartPlugins", step=410, multi=true)
	public void __STARTUP__plugins_start(final xAppStepDAO step) {
		final gcClientPluginManager manager = this.plugins.get();
		if (step.isFirstLoop())
			manager.startAll();
		if (manager.run())
			throw new xAppStepMultiFinishedException();
	}



	// scripts
	@xAppStep(type=xAppStepType.STARTUP, title="Scripts", step=450)
	public void __START__scripts() {
//TODO
//		try {
//			final gcScriptManager manager = new gcScriptManager(this);
//			final gcScriptManager previous = this.scripts.getAndSet(manager);
//			if (previous != null)
//				previous.stop();
//TODO: load scripts
//			manager.start();
//		} catch (Exception e) {
//			this.fail(e);
//		}
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	// scripts
	@xAppStep(type=xAppStepType.SHUTDOWN, title="Scripts", step=450)
	public void __STOP__scripts() {
//TODO
//		try {
//			final gcScriptManager manager = this.scripts.getAndSet(null);
//			if (manager != null)
//				manager.stop();
//TODO: wait for scripts to finish
//		} catch (Exception e) {
//			this.fail(e);
//		}
	}



	@xAppStep(type=xAppStepType.SHUTDOWN, title="Stop-Plugins", step=410, multi=true)
	public void __STOP__plugins_stop(final xAppStepDAO step) {
		final gcClientPluginManager manager = this.plugins.get();
		if (manager != null) {
			if (step.isFirstLoop())
				manager.stopAll();
			if (manager.run())
				throw new xAppStepMultiFinishedException();
		}
	}

	@xAppStep(type=xAppStepType.SHUTDOWN, title="UnloadPlugins", step=400, multi=true)
	public void __STOP__plugins_unload(final xAppStepDAO step) {
		final gcClientPluginManager manager = this.plugins.get();
		if (manager != null) {
			if (step.isFirstLoop())
				manager.termAll();
			if (manager.run()) {
				this.plugins.set(null);
				throw new xAppStepMultiFinishedException();
			}
		}
	}



	// -------------------------------------------------------------------------------



	public gcClientPluginManager getPluginManager() {
		// existing
		{
			final gcClientPluginManager manager = this.plugins.get();
			if (manager != null)
				return manager;
		}
		// new instance
		{
			final gcClientPluginManager manager = new gcClientPluginManager();
			if (this.plugins.compareAndSet(null, manager))
				return manager;
		}
		return this.plugins.get();
	}



}
