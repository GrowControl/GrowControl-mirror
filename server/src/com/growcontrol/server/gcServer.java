package com.growcontrol.server;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.server.app.steps.gcServerLogo;
import com.growcontrol.server.plugins.gcServerPluginManager;
import com.poixson.app.xAppStep.StepType;
import com.poixson.app.steps.xAppStandard;
import com.poixson.app.steps.xAppSteps_Console;


/*
 * Startup sequence
 *   10  prevent root        - xAppSteps_Tool
 *   50  load configs        - xAppSteps_Config
 *   70  lock file           - xAppSteps_LockFile
 *   80  display logo        - xAppSteps_Logo
 *   85  sync clock          - xAppStandard
 *  100  prepare commands    - xCommandHandler
 *  105  start console input - xAppSteps_Console
 *  200  startup time        - xAppStandard
 *  300  start event router
 *  405  load plugins        - gcServerPluginManager
 *  410  start plugins       - gcServerPluginManager
 *  450  start scripts
 *  500  start sockets
 *
 * Shutdown sequence
 *  500  stop listen sockets
 *  450  stop scripts
 *  410  stop plugins        - gcServerPluginManager
 *  405  unload plugins      - gcServerPluginManager
 *  300  stop event router
 *  150  stop schedulers     - xAppSteps_Scheduler
 *  105  stop console input  - xAppSteps_Console
 *  100  stop thread pools   - xAppStandard
 *   60  display uptime      - xAppStandard
 *   10  garbage collect     - xApp
 *    1  exit
 */
public class gcServer extends xAppStandard {

	private static final AtomicReference<gcServer> instance =
			new AtomicReference<gcServer>(null);



	/**
	 * Get the server class instance.
	 * @return gcServer instance object.
	 */
	public static gcServer get() {
		return instance.get();
	}



	public gcServer() {
		super();
		if ( ! instance.compareAndSet(null, this) )
			throw new RuntimeException("gcServer instance already exists! Cannot create a second instance.");
	}
//TODO: is this useful?
//	if (xVars.debug()) {
//		this.displayColors();
//	}
//	case "--internal":
//	gcServerVars.setInternal(true);
//	it.remove();



	@Override
	protected Object[] getStepObjects(final StepType type) {
		return new Object[] {
			new gcServerLogo(),
			xAppSteps_Console.get(),
			gcServerPluginManager.get()
		};
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



/*
	// standard commands
	@xAppStep( Type=StepType.STARTUP, Title="Commands", StepValue=91 )
	public void __STARTUP_commands(final xApp app) {
		ShellUtils.RegisterCommands(
			new Commands_Standard()
		);
	}



/ *
	// load configs
	@xAppStep(type=StepType.STARTUP, title="Configs", priority=55)
	public void __STARTUP_load_configs() {
		try {
			xVars.setJLineHistorySize(200);
			xVars.setJLineHistoryFile(
				FileUtils.MergePaths(
					FileUtils.cwd(),
					"history.txt"
				)
			);
		} catch (Exception e) {
			Failure.fail(e);
		}
//TODO:
//		final gcServerConfig config = gcServerVars.getConfig();
//		{
//			// debug mode
//			final Boolean debug = config.getDebug();
//			if (debug != null && debug.booleanValue()) {
//				xVars.debug(debug.booleanValue());
//			}
//		}
//		{
//			// log level
//			if (!xVars.debug()) {
//				final xLevel lvl = config.getLogLevel();
//				if (lvl != null) {
//					xLog.getRoot()
//						.setLevel(lvl);
//				}
//			}
//		}
//		// load zones
//		synchronized(zones) {
//			config.PopulateZones(zones);
//			log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//		}
	}



	// command prompt
	@xAppStep(type=StepType.STARTUP, title="CommandPrompt", priority=85)
	public void __STARTUP_command_prompt() {
		try {
			// load console
			final xConsole console = new jLineConsole();
			xLog.setConsole(console);
			// load commands
			final xCommandHandler handler = xLog.getCommandHandler();
			handler.register(
				new gcCommandsCommon()
			);
			handler.register(
				new gcCommandsServer()
			);
			// start console input
			console.start();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// prompt ticker
	@xAppStep(type=StepType.STARTUP, title="PromptTicker", priority=165)
	public void __STARTUP_prompt_ticker() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		final gcServerConfig config = gcServerVars.getConfig();
//		// prompt ticker
//		if (config.getPromptTickerEnabled()) {
//			new xTickPrompt();
//		}
//TODO:
//		final gcServerConfig config = gcServerVars.getConfig();
//		final xTickHandler ticker = xTickHandler.get();
//		ticker.setInterval(
//				config.getTickInterval()
//		);
//		ticker.Start();
	}



	// meta io event listener
	@xAppStep(type=StepType.STARTUP, title="MetaEventHandler", priority=200)
	public void __STARTUP_meta_event_handler() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		getLogicQueue();
	}



	// load plugins
	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=250)
	public void __STARTUP_load_plugins() {
		try {
			final xPluginManager<gcServerPlugin> manager =
				new xPluginManager<gcServerPlugin>();
			final xPluginLoader_Dir<gcServerPlugin> loader =
				new xPluginLoader_Dir<gcServerPlugin>(manager);
			loader.setPluginMainClassKey(
				gcServerDefines.CONFIG_PLUGIN_CLASS_KEY_SERVER
			);
			// store plugin manager
			gcServerVars.setPluginManager(manager);
			loader.LoadFromDir();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// start plugins
	@xAppStep(type=StepType.STARTUP, title="StartPlugins", priority=275)
	public void __STARTUP_start_plugins() {
		try {
			final xPluginManager<gcServerPlugin> manager =
				gcServerVars.getPluginManager();
			if (manager == null) {
				Failure.fail("Plugin manager failed to load!");
			}
			manager.enableAll();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// sockets
	@xAppStep(type=StepType.STARTUP, title="Sockets", priority=300)
	public void __STARTUP_sockets() {
//TODO:
//		// load socket configs
//		final Map<String, NetServerConfig> netConfigs;
//		try {
//			netConfigs = gcServerVars.getConfig().getNetConfigs();
//		} catch (xConfigException e) {
//			this.log().trace(e);
//			Failure.fail(e.getMessage());
//			return;
//		}
//		if (utils.isEmpty(netConfigs)) {
//			log().warning("No socket server configs found to load");
//			return;
//		}
//		// start socket servers
//		final NetServerManager manager = NetServerManager.get();
//		for (final NetServerConfig config : netConfigs.values()) {
//			if (!config.isEnabled()) continue;
//			try {
//				manager.getServer(config);
//			} catch (UnknownHostException e) {
//				log().trace(e);
//			} catch (InterruptedException e) {
//				log().trace(e);
//				break;
//			}
//		}
//		// log configs
//		for (final NetConfig dao : configs) {
//			if (dao.enabled) {
//				this.log().getWeak("sockets").finer(
//					dao.host+":"+Integer.toString(dao.port)+
//					(dao.ssl ? " (ssl)" : " (raw)")
//				);
//			}
//		}

//		// start socket listener
//		if (socket == null) {
//			socket = new pxnSocketServer();
//		}
//		socket.setHost();
//		socket.setPort(ServerConfig.ListenPort());
//		// create processor
//		socket.setFactory(new pxnSocketProcessorFactory() {
//			@Override
//			public gcPacketReader newProcessor() {
//				return new gcPacketReader();
//			}
//		});
//		socket.Start();
	}



	// scripts
	@xAppStep(type=StepType.STARTUP, title="Scripts", priority=350)
	public void __STARTUP_scripts() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		final gcScriptManager manager = gcScriptManager.get();
//		manager.loadAll();
//		manager.StartAll();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// scripts
	@xAppStep(type=StepType.SHUTDOWN, title="Scripts", priority=350)
	public void __SHUTDOWN_scripts() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		gcScriptManager.get()
//			.StopAll();
//TODO: wait for scripts to finish
	}



	// close sockets
	@xAppStep(type=StepType.SHUTDOWN, title="Sockets", priority=300)
	public void __SHUTDOWN_sockets() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		final NetServerManager manager = NetServerManager.get();
//		manager.CloseAll();
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="StopPlugins", priority=275)
	public void __SHUTDOWN_stop_plugins() {
		try {
			final xPluginManager<gcServerPlugin> manager =
				gcServerVars.getPluginManager();
			manager.disableAll();
			//TODO: wait for plugins to stop
			manager.unloadAll();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// meta io event listener
	@xAppStep(type=StepType.SHUTDOWN, title="MetaEventHandler", priority=200)
	public void __SHUTDOWN_meta_event_handler() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



	// prompt ticker
	@xAppStep(type=StepType.SHUTDOWN, title="PromptTicker", priority=165)
	public void __SHUTDOWN_prompt_ticker() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



	// exit if no client running
	@xAppStep(type=StepType.SHUTDOWN, title="exit", priority=2)
	public void __SHUTDOWN_exit() {
		try {
			Main.StopServer();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}
*/



}
