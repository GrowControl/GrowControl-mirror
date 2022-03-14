/*
package com.growcontrol.client;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.client.app.steps.gcClientLogo;
import com.growcontrol.client.plugins.gcClientPluginManager;
import com.poixson.app.xAppStep.StepType;
import com.poixson.app.steps.xAppStandard;


/ *
 * Startup sequence
 *   10  prevent root        - xAppSteps_Tool
 *   50  load configs        - xAppSteps_Config
 *   70  lock file           - xAppSteps_LockFile
 *   80  display logo        - xAppSteps_Logo
 *   85  sync clock          - xAppStandard
 *  105  load gui
 *  200  startup time        - xAppStandard
 *  405  load plugins        - gcClientPluginManager
 *  410  start plugins       - gcClientPluginManager
 *  450  start scripts
 *  500  start sockets
 *
 * Shutdown sequence
 *  500  stop listen sockets
 *  450  stop scripts
 *  410  stop plugins        - gcClientPluginManager
 *  405  unload plugins      - gcClientPluginManager
 *  150  stop schedulers     - xAppSteps_Scheduler
 *  105  close gui windows
 *  100  stop thread pools   - xAppStandard
 *   60  display uptime      - xAppStandard
 *   10  garbage collect     - xApp
 *    1  exit
 * /
public abstract class gcClient extends xAppStandard {

	private static final AtomicReference<gcClient> instance =
			new AtomicReference<gcClient>(null);



	/ **
	 * Get the client class instance.
	 * @return gcClient instance object.
	 * /
	public static gcClient get() {
		return instance.get();
	}



	public gcClient() {
		super();
		if ( ! instance.compareAndSet(null, this) )
			throw new RuntimeException("gcClient instance already exists! Cannot create a second instance.");
	}



	@Override
	protected Object[] getStepObjects(final StepType type) {
		return new Object[] {
			new gcClientLogo(),
			gcClientPluginManager.get()
		};
	}



/ *
	/ **
	 * Handle command-line arguments.
	 * /
	@Override
	protected void processArgs(final String[] args) {
		if(utils.isEmpty(args)) return;
		for(final String arg : args) {
			switch(arg) {
			case "--debug":
				xVars.debug(true);
				break;
			default:
				System.out.println("Unknown argument: "+arg);
				System.exit(1);
				break;
			}
		}
	}
* /



	// ------------------------------------------------------------------------------- //
	// startup steps



	// load configs
	@xAppStep(type=StepType.STARTUP, title="Configs", priority=55)
	public void __STARTUP_load_configs() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		gcClientVars.getConfig();
	}



	// load gui
	@xAppStep(type=StepType.STARTUP, title="GUI", priority=100)
	public void __STARTUP_gui() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		guiManager.get();
	}



//TODO: move this to gcServerManager
	// load plugins
	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=250)
	public void __STARTUP_load_plugins() {
		try {
			final xPluginManager<gcClientPlugin> manager =
				new xPluginManager<gcClientPlugin>();
			final xPluginLoader_Dir<gcClientPlugin> loader =
				new xPluginLoader_Dir<gcClientPlugin>(manager);
			loader.setPluginMainClassKey(
				gcClientDefines.CONFIG_PLUGIN_CLASS_KEY_CLIENT
			);
			// store plugin manager
			gcClientVars.setPluginManager(manager);
			loader.LoadFromDir();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// start plugins
	@xAppStep(type=StepType.STARTUP, title="StartPlugins", priority=275)
	public void __STARTUP_start_plugins() {
		try {
			final xPluginManager<gcClientPlugin> manager =
				gcClientVars.getPluginManager();
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
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



//TODO: move this to gui manager?
	// scripts
	@xAppStep(type=StepType.STARTUP, title="Scripts", priority=350)
	public void __STARTUP_scripts() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//		final gcScriptManager manager = gcScriptManager.get();
//		manager.loadAll();
//		manager.StartAll();
	}



	// show login window
	@xAppStep(type=StepType.STARTUP, title="LoginWindow", priority=400)
	public void __STARTUP_login_window() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		guiManager.get()
//			.Show(GUI_MODE.LOGIN);
	}



//TODO: search for server in client class

//			// connect to server
//			conn = new connection("192.168.3.3", 1142);
//			conn.sendPacket(clientPacket.sendHELLO(version, "lorenzo", "pass"));



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// close gui windows
	@xAppStep(type=StepType.SHUTDOWN, title="CloseWindows", priority=400)
	public void __SHUTDOWN_close_gui_windows() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		final guiManager manager = guiManager.peek();
//		if(manager != null)
//			manager.shutdown();
//TODO:
//		guiManager.Shutdown();
	}



//TODO: move this to gui manager?
	// scripts
	@xAppStep(type=StepType.SHUTDOWN, title="Scripts", priority=350)
	public void __SHUTDOWN_scripts() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//		gcScriptManager.get()
//			.StopAll();
	}



	// close sockets
	@xAppStep(type=StepType.SHUTDOWN, title="Sockets", priority=300)
	public void __SHUTDOWN_sockets() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="StopPlugins", priority=275)
	public void __SHUTDOWN_stop_plugins() {
		try {
			final xPluginManager<gcClientPlugin> manager =
				gcClientVars.getPluginManager();
			manager.disableAll();
			//TODO: wait for plugins to stop
			manager.unloadAll();
		} catch (Exception e) {
			Failure.fail(e);
		}
	}



	// ------------------------------------------------------------------------------- //



/ *
	protected void updateConfig() {
		// config version
		{
			boolean configVersionDifferent = false;
			final String configVersion = this.config.getVersion();
			final String clientVersion = this.getVersion();
			if(utils.notEmpty(configVersion) && utils.notEmpty(clientVersion)) {
				if(configVersion.endsWith("x") || configVersion.endsWith("*")) {
					final String vers = utilsString.trims(configVersion, "x", "*");
					if(!clientVersion.startsWith(vers))
						configVersionDifferent = true;
				} else {
					if(!configVersion.equals(clientVersion))
						configVersionDifferent = true;
				}
			}
			if(configVersionDifferent)
				log().warning(gcClientDefines.CONFIG_FILE+" for this client may need updates");
		}
		// log level
		{
			final Boolean debug = this.config.getDebug();
			if(debug != null && debug.booleanValue())
				xVars.debug(debug.booleanValue());
			if(!xVars.debug()) {
				// set log level
				final xLevel level = this.config.getLogLevel();
				if(level != null)
					xLog.getRoot()
						.setLevel(level);
			}
		}
	}



//	// connect to server
//	public void Connect(String host, int port, String user, String pass) {
//		pxnThreadQueue.addToMain("SocketConnect",
//			new doConnect(host, port, user, pass));
//	}


//	private class doConnect implements Runnable {
//
//		public pxnSocketClient socket = null;
//		private final String host;
//		private final int    port;
//@SuppressWarnings("unused")
//		private final String user;
//@SuppressWarnings("unused")
//		private final String pass;
//
//
//		public doConnect(String host, int port, String user, String pass) {
//			if(host == null || host.isEmpty())
//				host = "127.0.0.1";
//			if(port < 1) port = 1142;
//			if(user == null || user.isEmpty()) user = null;
//			if(pass == null || pass.isEmpty()) pass = null;
//			this.host = host;
//			this.port = pxnUtilsMath.MinMax(port, 1, 65535);
//			this.user = user;
//			this.pass = pass;
//		}
//
//
//		// connect to server
//		@Override
//		public synchronized void run() {
//pxnLog.get().info("Connecting..");
//			// create socket
//			if(socket == null)
//				socket = new pxnSocketClient();
//			socket.setHost(this.host);
//			socket.setPort(this.port);
//			// create processor
//			socket.setFactory(new pxnSocketProcessorFactory() {
//				@Override
//				public gcPacketReader newProcessor() {
//					return new gcPacketReader();
//				}
//			});
//			socket.Start();
//			if(!pxnSocketState.CONNECTED.equals(socket.getState())) {
//				pxnLog.get().warning("Failed to connect!");
//				return;
//			}
//			// send HELLO packet
//			gcPacketSender.sendHELLO(
//				socket.getWorker(),
//				gcClient.version);
////				connectInfo.username,
////				connectInfo.password);
//pxnLog.get().severe("CONNECTED!!!!!!!!!!!!!!!!!!!");
//			guiManager.get().Update(guiManager.GUI.DASH);
//		}
//
//
//	}



//	// get zones
//	public List<String> getZones() {
//		synchronized(zones) {
//			return new ArrayList<String>(zones);
//		}
//	}
//	public String[] getZonesArray() {
//		synchronized(zones) {
//			return (String[]) zones.toArray();
//		}
//	}
* /



}
*/
