package com.growcontrol.client;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.poixson.app.xApp;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
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
		gcClientVars.getConfig();
	}



	// load gui
	@xAppStep(type=StepType.STARTUP, title="GUI", priority=100)
	public void __STARTUP_gui() {
		guiManager.get();
	}



//TODO: move this to gcServerManager
//	// load plugins
//	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=250)
//	public void __STARTUP_load_plugins() {
		final xPluginManager manager = xPluginManager.get();
		manager.setClassField("Client Main");
		manager.loadAll();
		manager.initAll();
	}
//	}




	// sockets
	@xAppStep(type=StepType.STARTUP, title="Sockets", priority=300)
	public void __STARTUP_sockets() {
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



//TODO: move this to gui manager?
//	// scripts
//	@xAppStep(type=StepType.STARTUP, title="Scripts", priority=350)
//	public void __STARTUP_scripts() {
//		final gcScriptManager manager = gcScriptManager.get();
//		manager.loadAll();
//		manager.StartAll();
//	}



	// show login window
	@xAppStep(type=StepType.STARTUP, title="LoginWindow", priority=400)
	public void __STARTUP_login_window() {
		guiManager.get()
			.Show(GUI_MODE.LOGIN);
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
		final guiManager manager = guiManager.peak();
		if(manager != null)
			manager.shutdown();
//TODO:
//		guiManager.Shutdown();
	}



//TODO: move this to gui manager?
//	// scripts
//	@xAppStep(type=StepType.SHUTDOWN, title="Scripts", priority=350)
//	public void __SHUTDOWN_scripts() {
//		gcScriptManager.get()
//			.StopAll();
//	}



	// close sockets
	@xAppStep(type=StepType.SHUTDOWN, title="Sockets", priority=300)
	public void __SHUTDOWN_sockets() {
//TODO:
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="StopPlugins", priority=275)
	public void __SHUTDOWN_stop_plugins() {
		xPluginManager.get()
			.disableAll();
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
*/



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



	// ascii header
	@Override
	protected void displayLogo() {
		final PrintStream out = AnsiConsole.out;
		final Ansi.Color bgcolor = Ansi.Color.BLACK;
		out.println();
		// line 1
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).bold().a("      ")
			.fg(Ansi.Color.GREEN).a("P")
			.fg(Ansi.Color.WHITE).a("oi")
			.fg(Ansi.Color.GREEN).a("X")
			.fg(Ansi.Color.WHITE).a("son")
			.a("                          ")
			.a("                          ")
			.reset() );
		// line 2
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).bold().a("   ")
			.fg(Ansi.Color.BLACK).a("©")
			.fg(Ansi.Color.GREEN).a("GROW")
			.fg(Ansi.Color.WHITE).a("CONTROL")
			.boldOff().a("   ")
			/* C */.fg(Ansi.Color.MAGENTA).a("_ _        ")
			/* E */.fg(Ansi.Color.YELLOW).a(",`--',")
					.a("                     ")
			/* H */.fg(Ansi.Color.WHITE).a("\" ' \"    ")
			.reset() );
		// line 3
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a("   ")
			/* A */.fg(Ansi.Color.BLUE).a("_  ")
					.fg(Ansi.Color.CYAN).a("Client     ")
			/* C */.fg(Ansi.Color.MAGENTA).a("(_\\_)      ")
			/* E */.fg(Ansi.Color.YELLOW).a(". ")
					.bold().a("_\\/_ ").boldOff().a(".")
					.a("                  ")
			/* H */.fg(Ansi.Color.WHITE).a("\" \\ | / \"  ")
			.reset() );
		// line 4
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a(" ")
			/* A */.fg(Ansi.Color.BLUE).a("_(_)_          ")
			/* C */.fg(Ansi.Color.MAGENTA).a("(__")
					.a("<").a("_{)     ")
			/* E */.fg(Ansi.Color.YELLOW).a("`. ")
					.bold().a("/\\ ").boldOff().a(".'   ")
			/* F */.fg(Ansi.Color.WHITE).a(".\\|/.         ")
			/* H */.fg(Ansi.Color.WHITE).a("' --")
					.bold().a("(:)").boldOff().a("-- ' ")
			.reset() );
		// line 5
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor)
			/* A */.fg(Ansi.Color.BLUE).a("(_)")
					.bold().a("@").boldOff().a("(_)          ")
			/* C */.fg(Ansi.Color.MAGENTA).a("{_/_}        ")
			/* E */.fg(Ansi.Color.YELLOW).a("\"")
					.fg(Ansi.Color.GREEN).a("||")
					.fg(Ansi.Color.YELLOW).a("\"     ")
			/* F */.fg(Ansi.Color.WHITE).a("-")
					.bold().a("(:)").boldOff().a("-          ")
			/* H */.fg(Ansi.Color.WHITE).a("\" / | \\ \"  ")
			.reset() );
		// line 6
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a("  ")
			/* A */.fg(Ansi.Color.BLUE).a("(_)")
					.fg(Ansi.Color.GREEN).a("\\.         ")
			/* C */.fg(Ansi.Color.GREEN).a("|\\ |           ")
			/* E */.fg(Ansi.Color.GREEN).a("|| /\\   ")
			/* F */.fg(Ansi.Color.WHITE).a("\"/")
					.fg(Ansi.Color.GREEN).a("|")
					.fg(Ansi.Color.WHITE).a("\\\"           ")
			/* H */.fg(Ansi.Color.WHITE).a("\" '")
					.fg(Ansi.Color.GREEN).a("|")
					.fg(Ansi.Color.WHITE).a("' \"   ")
			.reset() );
		// line 7
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a("    ")
			/* A */.fg(Ansi.Color.GREEN).a(". |/| ")
			/* B */.fg(Ansi.Color.RED).a(".vVv.  ")
			/* C */.fg(Ansi.Color.GREEN).a("\\\\| /| ")
			/* D */.fg(Ansi.Color.RED).a("\\V/  ")
			/* E */.fg(Ansi.Color.GREEN).a("/\\||//\\)   ")
			/* F */.fg(Ansi.Color.GREEN).a("'|'    ")
			/* G */.fg(Ansi.Color.GREEN).a("`")
					.fg(Ansi.Color.YELLOW).bold().a("@")
					.fg(Ansi.Color.GREEN).boldOff().a("'   ")
			/* H */.fg(Ansi.Color.GREEN).a("|\\   |      ")
			.reset() );
		// line 8
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a("    ")
			/* A */.fg(Ansi.Color.GREEN).a("|\\|/   ")
			/* B */.fg(Ansi.Color.GREEN).a("\\")
					.fg(Ansi.Color.RED).a("#")
					.fg(Ansi.Color.GREEN).a("/    ")
			/* C */.fg(Ansi.Color.GREEN).a("\\|//  ")
			/* D */.fg(Ansi.Color.GREEN).a("`")
					.bold().a("|").boldOff().a("/ ")
			/* E */.fg(Ansi.Color.GREEN).a("(/\\||/    ")
			/* F */.fg(Ansi.Color.GREEN).a(".\\ | ,   ")
			/* G */.fg(Ansi.Color.GREEN).a("\\")
					.bold().a("|").boldOff().a("/   ")
			/* H */.fg(Ansi.Color.GREEN).a("/_ \\ |  /`| ")
			.reset() );
		// line 9
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor).a("     ")
			.fg(Ansi.Color.GREEN)
			/* A */.a("\\|   ")
			/* B */.a("\\\\")
					.bold().a("|")
					.boldOff().a("//    ")
			/* C */.a("|/  ")
			/* D */.a("\\\\")
					.bold().a("|").boldOff().a("//   ")
			/* E */.a("||     ")
			/* F */.a("/-\\|/_\\ ")
			/* G */.a("\\\\")
					.bold().a("|").boldOff().a("//,   ")
			/* H */.a("/-\\|/_//  ")
			.reset() );
		// line 10
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor)
			.fg(Ansi.Color.GREEN)
			.a("^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/")
			.a("^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^")
			.reset() );
		// line 11
		out.println(Ansi.ansi()
			.a(" ").bg(bgcolor)
			.fg(Ansi.Color.GREEN)
			.a("////////////////////////////////")
			.a("/////////////////////////////////")
			.reset() );
		out.println();
		out.println(" Copyright (C) 2007-2014 PoiXson, Mattsoft");
		out.println(" - Brainchild of the one known as lorenzo -");
		out.println(" This program comes with absolutely no warranty. This is free software");
		out.println(" and you are welcome to modify it or redistribute it under certain");
		out.println(" conditions. Type 'show license' for license details.");
		out.println();
		out.flush();
	}

//         A       B      C     D     E         F      G         H
// 1 |       PoiXson                                                     |
// 2 |    ©GROWCONTROL   _ _        ,`--',                     " ' "     |
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
