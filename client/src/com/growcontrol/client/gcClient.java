package com.growcontrol.client;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.growcontrol.client.plugins.gcClientPlugin;
import com.poixson.app.xApp;
import com.poixson.app.plugin.xPluginLoader_Dir;
import com.poixson.app.plugin.xPluginManager;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
import com.poixson.utils.Failure;
import com.poixson.utils.StringUtils;
import com.poixson.utils.xLogger.xLog;
import com.poixson.utils.xLogger.xLogPrintStream;


/*
 * Startup sequence
 *   55  load configs
 *  100  load gui
 *  250  load plugins
 *  275  start plugins
 *  300  sockets
 *  400  show login window
 * Shutdown sequence
 *  400  close gui windows
 *  300  stop listen sockets
 *  275  stop plugins
 */
public abstract class gcClient extends xApp {



	/**
	 * Get the client class instance.
	 * @return gcClient instance object.
	 */
	public static gcClient get() {
		return (gcClient) xApp.get();
	}



	public gcClient() {
		super();
		gcClientVars.init();
//		if(xVars.debug())
//			this.displayColors();
	}




	/**
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
*/



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



/*
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
*/



	// ascii header
	@Override
	protected void displayLogo() {
		// colors
		final String COLOR_BG = "black";
		final String COLOR_PXN_P    = "bold,green";
		final String COLOR_PXN_OI   = "bold,blue";
		final String COLOR_PXN_X    = "bold,green";
		final String COLOR_PXN_SON  = "bold,blue";
		final String COLOR_GROW     = "bold,green";
		final String COLOR_CONTROL  = "bold,white";
		final String COLOR_SOFTWARE = "cyan";
		final String COLOR_VERSION  = "cyan";
		final String COLOR_GRASS    = "green";
		final String COLOR_FLOWER_STEM     = "green";
		final String COLOR_FLOWER_A_PEDALS = "blue";
		final String COLOR_FLOWER_B_PEDALS = "bold,red";
		final String COLOR_FLOWER_C_PEDALS = "magenta";
		final String COLOR_FLOWER_D_PEDALS = "red";
		final String COLOR_FLOWER_E_PEDALS = "yellow";
		final String COLOR_FLOWER_F_PEDALS = "bold,white";
		final String COLOR_FLOWER_G_PEDALS = "yellow";
		final String COLOR_FLOWER_H_PEDALS = "bold,white";
		// line 1
		final Map<Integer, String> colors1 = new LinkedHashMap<Integer, String>();
		colors1.put(new Integer(7),  COLOR_PXN_P  );
		colors1.put(new Integer(8),  COLOR_PXN_OI );
		colors1.put(new Integer(10), COLOR_PXN_X  );
		colors1.put(new Integer(11), COLOR_PXN_SON);
		colors1.put(new Integer(18), COLOR_VERSION);
		// line 2
		final Map<Integer, String> colors2 = new LinkedHashMap<Integer, String>();
		colors2.put(new Integer(5),  COLOR_GROW           );
		colors2.put(new Integer(9),  COLOR_CONTROL        );
		colors2.put(new Integer(19), COLOR_FLOWER_C_PEDALS); // Flower C
		colors2.put(new Integer(30), COLOR_FLOWER_E_PEDALS); // Flower E
		colors2.put(new Integer(57), COLOR_FLOWER_H_PEDALS); // Flower H
		// line 3
		final Map<Integer, String> colors3 = new LinkedHashMap<Integer, String>();
		colors3.put(new Integer(4),  COLOR_FLOWER_A_PEDALS); // Flower A
		colors3.put(new Integer(7),  COLOR_SOFTWARE       );
		colors3.put(new Integer(18), COLOR_FLOWER_C_PEDALS); // Flower C
		colors3.put(new Integer(29), COLOR_FLOWER_E_PEDALS); // Flower E
		colors3.put(new Integer(55), COLOR_FLOWER_H_PEDALS); // Flower H
		// line 4
		final Map<Integer, String> colors4 = new LinkedHashMap<Integer, String>();
		colors4.put(new Integer(2),  COLOR_FLOWER_A_PEDALS); // Flower A
		colors4.put(new Integer(17), COLOR_FLOWER_C_PEDALS); // Flower C
		colors4.put(new Integer(29), COLOR_FLOWER_E_PEDALS); // Flower E
		colors4.put(new Integer(40), COLOR_FLOWER_F_PEDALS); // Flower F
		colors4.put(new Integer(55), COLOR_FLOWER_H_PEDALS); // Flower H
		// line 5
		final Map<Integer, String> colors5 = new LinkedHashMap<Integer, String>();
		colors5.put(new Integer(1),  COLOR_FLOWER_A_PEDALS); // Flower A
		colors5.put(new Integer(18), COLOR_FLOWER_C_PEDALS); // Flower C
		colors5.put(new Integer(31), COLOR_FLOWER_E_PEDALS); // Flower E
		colors5.put(new Integer(32), COLOR_FLOWER_STEM    );
		colors5.put(new Integer(34), COLOR_FLOWER_E_PEDALS);
		colors5.put(new Integer(40), COLOR_FLOWER_F_PEDALS); // Flower F
		colors5.put(new Integer(56), COLOR_FLOWER_H_PEDALS); // Flower H
		// line 6
		final Map<Integer, String> colors6 = new LinkedHashMap<Integer, String>();
		colors6.put(new Integer(3),  COLOR_FLOWER_A_PEDALS); // Flower A
		colors6.put(new Integer(7),  COLOR_FLOWER_STEM    );
		colors6.put(new Integer(40), COLOR_FLOWER_F_PEDALS); // Flower F
		colors6.put(new Integer(57), COLOR_FLOWER_H_PEDALS); // Flower H
		colors6.put(new Integer(59), COLOR_FLOWER_STEM    );
		colors6.put(new Integer(60), COLOR_FLOWER_H_PEDALS);
		// line 7
		final Map<Integer, String> colors7 = new LinkedHashMap<Integer, String>();
		colors7.put(new Integer(5),  COLOR_FLOWER_STEM    ); // Flower A
		colors7.put(new Integer(11), COLOR_FLOWER_B_PEDALS); // Flower B
		colors7.put(new Integer(18), COLOR_FLOWER_STEM    ); // Flower C
		colors7.put(new Integer(25), COLOR_FLOWER_D_PEDALS); // Flower D
		colors7.put(new Integer(30), COLOR_FLOWER_STEM    ); // Flower E
		colors7.put(new Integer(41), COLOR_FLOWER_F_PEDALS); // Flower F
		colors7.put(new Integer(42), COLOR_FLOWER_STEM    );
		colors7.put(new Integer(43), COLOR_FLOWER_F_PEDALS);
		colors7.put(new Integer(48), COLOR_FLOWER_G_PEDALS); // Flower G
		colors7.put(new Integer(54), COLOR_FLOWER_STEM    ); // Flower H
		// line 8
		final Map<Integer, String> colors8 = new LinkedHashMap<Integer, String>();
		colors8.put(new Integer(5), COLOR_FLOWER_STEM);
		// line 9
		final Map<Integer, String> colors9 = new LinkedHashMap<Integer, String>();
		colors9.put(new Integer(6), COLOR_FLOWER_STEM);
		// line 10
		final Map<Integer, String> colors10 = new LinkedHashMap<Integer, String>();
		colors10.put(new Integer(1), COLOR_GRASS);
		// line 11
		final Map<Integer, String> colors11 = new LinkedHashMap<Integer, String>();
		colors11.put(new Integer(1), COLOR_GRASS);

		// build lines
		final String version = StringUtils.padCenter(15, this.getVersion(), ' ');
		final PrintStream out =
			new xLogPrintStream(
				xLog.getRoot(),
				null
			);
		out.println();
		DisplayLineColors(out, COLOR_BG, colors1, "      PoiXson    "+version+"                                 "              );
		DisplayLineColors(out, COLOR_BG, colors2, "    GROWCONTROL   _ _        ,`--',                     \" ' \"    "        );
		DisplayLineColors(out, COLOR_BG, colors3, "   _  Client     (_\\_)      . _\\/_ .                  \" \\ | / \"  "     );
		DisplayLineColors(out, COLOR_BG, colors4, " _(_)_          (__<_{)     `. /\\ .'   .\\|/.         ' --(:)-- ' "        );
		DisplayLineColors(out, COLOR_BG, colors5, "(_)@(_)          {_/_}        \"||\"     -(:)-          \" / | \\ \"  "     );
		DisplayLineColors(out, COLOR_BG, colors6, "  (_)\\.         |\\ |           || /\\   \"/|\\\"           \" '|' \"   "  );
		DisplayLineColors(out, COLOR_BG, colors7, "    . |/| .vVv.  \\\\| /| \\V/  /\\||//\\)   '|'    `@'   |\\   |      "    );
		DisplayLineColors(out, COLOR_BG, colors8, "    |\\|/   \\#/    \\|//  `|/ (/\\||/    .\\ | ,   \\|/   /_ \\ |  /`| "   );
		DisplayLineColors(out, COLOR_BG, colors9, "     \\|   \\\\|//    |/  \\\\|//   ||     /-\\|/_\\ \\\\|//,   /-\\|/_//  ");
		DisplayLineColors(out, COLOR_BG, colors10,"^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^"          );
		DisplayLineColors(out, COLOR_BG, colors11,"/////////////////////////////////////////////////////////////////"          );
		out.println();
		out.println("    Copyright (C)2007-2017 PoiXson, Mattsoft  ");
		out.println("   - Brainchild of the one known as lorenzo - ");
		out.println(" This program comes with absolutely no warranty. This is free");
		out.println(" software and you are welcome to modify it or redistribute it");
		out.println(" under certain conditions. Type 'show license' at the command");
		out.println(" prompt for license details, or go to www.growcontrol.com for");
		out.println(" more information.");
		out.println();
		out.flush();
	}

//         A       B      C     D     E         F      G         H
// 1 |       PoiXson                                                     |
// 2 |     GROWCONTROL   _ _        ,`--',                     " ' "     |
// 3 |    _  Client     (_\_)      . _\/_ .                  " \ | / "   |
// 4 |  _(_)_          (__<_{)     `. /\ .'   .\|/.         ' --(:)-- '  |
// 5 | (_)@(_)          {_/_}        "||"     -(:)-          " / | \ "   |
// 6 |   (_)\.         |\ |           || /\   "/|\"           " '|' "    |
// 7 |     . |/| .vVv.  \\| /| \V/  /\||//\)   '|'    `@'   |\   |       |
// 8 |     |\|/   \#/    \|//  `|/ (/\||/    .\ | ,   \|/   /_ \ |  /`|  |
// 9 |      \|   \\|//    |/  \\|//   ||     /-\|/_\ \\|//,   /-\|/_//   |
//10 | ^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^ |
//11 | ///////////////////////////////////////////////////////////////// |
//   0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8
//   0         1         2         3         4         5         6



}
