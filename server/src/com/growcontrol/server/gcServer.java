package com.growcontrol.server;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.growcontrol.server.commands.gcCommandsCommon;
import com.growcontrol.server.commands.gcCommandsServer;
import com.poixson.app.xApp;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
import com.poixson.utils.DirsFiles;
import com.poixson.utils.Failure;
import com.poixson.utils.StringUtils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.jLineConsole;
import com.poixson.utils.xLogger.xCommandHandler;
import com.poixson.utils.xLogger.xConsole;
import com.poixson.utils.xLogger.xLog;
import com.poixson.utils.xLogger.xLogFormatter_Color;
import com.poixson.utils.xLogger.xLogHandlerConsole;
import com.poixson.utils.xLogger.xLogPrintStream;


/*
 * Startup sequence
 *   55  load configs
 *   85  command prompt
 *  160  tick scheduler
 *  200  event handler
 *  250  load plugins
 *  275  start plugins
 *  300  sockets
 *  350  scripts
 * Shutdown sequence
 *  350  stop scripts
 *  300  stop listen sockets
 *  275  stop plugins
 *  200  stop event handler
 *    1  exit if no client running
 */
public class gcServer extends xApp {



	public static gcServer get() {
		return (gcServer) xApp.get();
	}



	public gcServer() {
		super();
		gcServerVars.init();
//		if (xVars.debug()) {
//			this.displayColors();
//		}
//		case "--internal":
//		gcServerVars.setInternal(true);
//		it.remove();
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	// load configs
	@xAppStep(type=StepType.STARTUP, title="Configs", priority=55)
	public void __STARTUP_load_configs() {
		try {
			xVars.setJLineHistorySize(200);
			xVars.setJLineHistoryFile(
				DirsFiles.buildFilePath(
					DirsFiles.cwd(),
					"history",
					"txt"
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
			final xLog log = xLog.getRoot();
			log.setHandler(
				new xLogHandlerConsole()
			);
			// set color format
			log.setFormatter(
				new xLogFormatter_Color()
			);
			// load commands
			final xCommandHandler handler = xLog.getCommandHandler();
			handler.register(
				new gcCommandsCommon()
			);
			handler.register(
				new gcCommandsServer()
			);
			// start console input
			console.Start();
		} catch (Exception e) {
			Failure.fail(e);
		}
//TODO:
//		final gcServerConfig config = gcServerVars.getConfig();
//		// prompt ticker
//		if (config.getPromptTickerEnabled()) {
//			new xTickPrompt();
//		}
	}



	// tick scheduler
	@xAppStep(type=StepType.STARTUP, title="Ticker", priority=160)
	public void __STARTUP_ticker() {
//TODO:
//		final gcServerConfig config = gcServerVars.getConfig();
//		final xTickHandler ticker = xTickHandler.get();
//		ticker.setInterval(
//				config.getTickInterval()
//		);
//		ticker.Start();
	}



	// io event listener
	@xAppStep(type=StepType.STARTUP, title="EventHandler", priority=200)
	public void __STARTUP_event_handler() {
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
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		final xPluginManager manager = xPluginManager.get();
//		manager.setClassField("Server Main");
//		manager.loadAll();
//		manager.initAll();
	}



	// enable plugins
	@xAppStep(type=StepType.STARTUP, title="StartPlugins", priority=275)
	public void __STARTUP_start_plugins() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		xPluginManager.get()
//			.enableAll();
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



	// stop listening sockets
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
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//TODO:
//		xPluginManager.get()
//			.disableAll();
//TODO: wait for plugins to stop
//		xPluginManager.get()
//			.unloadAll();
	}



	@xAppStep(type=StepType.SHUTDOWN, title="EventHandler", priority=200)
	public void __SHUTDOWN_event_handler() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
	}



	// stop ticker
	@xAppStep(type=StepType.SHUTDOWN, title="Ticker", priority=160)
	public void __SHUTDOWN_ticker() {
//TODO:
//		try {
//		} catch (Exception e) {
//			Failure.fail(e);
//		}
//		xTickHandler.get()
//			.Stop();
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



	// ------------------------------------------------------------------------------- //



	// ascii header
	@Override
	protected void displayLogo() {
		// colors
		final String COLOR_PXN_P    = "bold,green";
		final String COLOR_PXN_OI   = "bold,blue";
		final String COLOR_PXN_X    = "bold,green";
		final String COLOR_PXN_SON  = "bold,blue";
		final String COLOR_COPY     = "bold,black";
		final String COLOR_GROW     = "bold,green";
		final String COLOR_CONTROL  = "bold,white";
		final String COLOR_SOFTWARE = "cyan";
		final String COLOR_VERSION  = "cyan";
		final String COLOR_GRASS    = "green";
		final String COLOR_FLOWER_STEM     = "green";
		final String COLOR_FLOWER_A_PEDALS = "red";
		final String COLOR_FLOWER_A_CENTER = "red";
		final String COLOR_FLOWER_B_PEDALS = "magenta";
		final String COLOR_FLOWER_C_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_C_CENTER = "yellow";
		final String COLOR_FLOWER_D_PEDALS = "bold,magenta";
		final String COLOR_FLOWER_E_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_E_CENTER = "yellow";
		final String COLOR_FLOWER_F_PEDALS = "blue";
		final String COLOR_FLOWER_F_CENTER = "bold,blue";
		final String COLOR_FLOWER_G_PEDALS = "magenta";
		final String COLOR_FLOWER_H_PEDALS = "bold,yellow";
		final String COLOR_FLOWER_H_CENTER = "yellow";
		// line 1
		final Map<Integer, String> colors1 = new LinkedHashMap<Integer, String>();
		colors1.put(new Integer(7),  COLOR_PXN_P  );
		colors1.put(new Integer(8),  COLOR_PXN_OI );
		colors1.put(new Integer(10), COLOR_PXN_X  );
		colors1.put(new Integer(11), COLOR_PXN_SON);
		// line 2
		final Map<Integer, String> colors2 = new LinkedHashMap<Integer, String>();
		colors2.put(new Integer(4),  COLOR_COPY);
		colors2.put(new Integer(5),  COLOR_GROW);
		colors2.put(new Integer(9),  COLOR_CONTROL);
		colors2.put(new Integer(21), COLOR_FLOWER_C_PEDALS);
		// line 3
		final Map<Integer, String> colors3 = new LinkedHashMap<Integer, String>();
		colors3.put(new Integer(7),  COLOR_SOFTWARE);
		colors3.put(new Integer(19), COLOR_FLOWER_C_PEDALS);
		colors3.put(new Integer(50), COLOR_FLOWER_G_PEDALS);
		colors3.put(new Integer(58), COLOR_FLOWER_H_PEDALS);
		// line 4
		final Map<Integer, String> colors4 = new LinkedHashMap<Integer, String>();
		colors4.put(new Integer(4),  COLOR_VERSION);
		colors4.put(new Integer(18), COLOR_FLOWER_C_PEDALS);
		colors4.put(new Integer(21), COLOR_FLOWER_C_CENTER);
		colors4.put(new Integer(22), COLOR_FLOWER_C_PEDALS);
		colors4.put(new Integer(28), COLOR_FLOWER_D_PEDALS);
		colors4.put(new Integer(38), COLOR_FLOWER_E_PEDALS);
		colors4.put(new Integer(44), COLOR_FLOWER_F_PEDALS);
		colors4.put(new Integer(50), COLOR_FLOWER_G_PEDALS);
		colors4.put(new Integer(56), COLOR_FLOWER_H_PEDALS);
		// line 5
		final Map<Integer, String> colors5 = new LinkedHashMap<Integer, String>();
		colors5.put(new Integer(13), COLOR_FLOWER_B_PEDALS);
		colors5.put(new Integer(20), COLOR_FLOWER_C_PEDALS);
		colors5.put(new Integer(23), COLOR_FLOWER_STEM);
		colors5.put(new Integer(28), COLOR_FLOWER_D_PEDALS);
		colors5.put(new Integer(36), COLOR_FLOWER_E_PEDALS);
		colors5.put(new Integer(43), COLOR_FLOWER_F_PEDALS);
		colors5.put(new Integer(45), COLOR_FLOWER_F_CENTER);
		colors5.put(new Integer(47), COLOR_FLOWER_F_PEDALS);
		colors5.put(new Integer(52), COLOR_FLOWER_G_PEDALS);
		colors5.put(new Integer(55), COLOR_FLOWER_H_PEDALS);
		colors5.put(new Integer(58), COLOR_FLOWER_H_CENTER);
		colors5.put(new Integer(59), COLOR_FLOWER_H_PEDALS);
		// line 6
		final Map<Integer, String> colors6 = new LinkedHashMap<Integer, String>();
		colors6.put(new Integer(6),  COLOR_FLOWER_A_PEDALS);
		colors6.put(new Integer(8),  COLOR_FLOWER_A_CENTER);
		colors6.put(new Integer(9),  COLOR_FLOWER_A_PEDALS);
		colors6.put(new Integer(13), COLOR_FLOWER_B_PEDALS);
		colors6.put(new Integer(23), COLOR_FLOWER_STEM);
		colors6.put(new Integer(30), COLOR_FLOWER_D_PEDALS);
		colors6.put(new Integer(35), COLOR_FLOWER_E_PEDALS);
		colors6.put(new Integer(38), COLOR_FLOWER_E_CENTER);
		colors6.put(new Integer(39), COLOR_FLOWER_E_PEDALS);
		colors6.put(new Integer(44), COLOR_FLOWER_F_PEDALS);
		colors6.put(new Integer(51), COLOR_FLOWER_STEM);
		colors6.put(new Integer(57), COLOR_FLOWER_H_PEDALS);
		colors6.put(new Integer(60), COLOR_FLOWER_STEM);
		// line 7
		final Map<Integer, String> colors7 = new LinkedHashMap<Integer, String>();
		colors7.put(new Integer(5),  COLOR_FLOWER_A_PEDALS);
		colors7.put(new Integer(7),  COLOR_FLOWER_A_CENTER);
		colors7.put(new Integer(9),  COLOR_FLOWER_A_PEDALS);
		colors7.put(new Integer(15), COLOR_FLOWER_B_PEDALS);
		colors7.put(new Integer(23), COLOR_FLOWER_STEM);
		colors7.put(new Integer(37), COLOR_FLOWER_E_PEDALS);
		colors7.put(new Integer(44), COLOR_FLOWER_STEM);
		// line 8
		final Map<Integer, String> colors8 = new LinkedHashMap<Integer, String>();
		colors8.put(new Integer(5),  COLOR_FLOWER_STEM);
		colors8.put(new Integer(6),  COLOR_FLOWER_A_PEDALS);
		colors8.put(new Integer(13), COLOR_FLOWER_STEM);
		// line 9
		final Map<Integer, String> colors9 = new LinkedHashMap<Integer, String>();
		colors9.put(new Integer(5), COLOR_FLOWER_STEM);
		// line 10
		final Map<Integer, String> colors10 = new LinkedHashMap<Integer, String>();
		colors10.put(new Integer(1), COLOR_GRASS);
		// line 11
		final Map<Integer, String> colors11 = new LinkedHashMap<Integer, String>();
		colors11.put(new Integer(1), COLOR_GRASS);

		// build lines
		final String version = StringUtils.padCenter(15, this.getVersion(), ' ');
		final PrintStream out = new xLogPrintStream(
				xLog.getRoot(),
				null
		);
		out.println();
		DisplayLineColors(out, colors1, "      PoiXson                                                    "   );
		DisplayLineColors(out, colors2, "    GROWCONTROL     _                                            "   );
		DisplayLineColors(out, colors3, "      Server      _(_)_                          wWWWw   _       "   );
		DisplayLineColors(out, colors4, " "+version+" (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     "       );
		DisplayLineColors(out, colors5, "            wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)    "  );
		DisplayLineColors(out, colors6, "     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\     " );
		DisplayLineColors(out, colors7, "    @@()@@    Y       \\|    \\|/    /(_)    \\|      |/      |/    ");
		DisplayLineColors(out, colors8, "    \\@@@@   \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/    ");
		DisplayLineColors(out, colors9, "    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//   ");
		DisplayLineColors(out, colors10,"^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^"    );
		DisplayLineColors(out, colors11,"/////////////////////////////////////////////////////////////////"    );
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
//  |       A      B      C       D      E       F      G      H      |
//1 |      PoiXson                                                    |
//2 |   ©GROWCONTROL     _                                            |
//3 |      Server      _(_)_                          wWWWw   _       |
//4 | <---version---> (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     |
//5 |            wWWWw  (_)\    (___)   _(_)_  @@()@@   Y  (_)@(_)    |
//6 |     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \|/   (_)\     |
//7 |    @@()@@    Y       \|    \|/    /(_)    \|      |/      |/    |
//8 |    \@@@@   \ |/       | / \ | /  \|/       |/    \|      \|/    |
//9 |    \\|//   \\|///  \\\|//\\\|/// \|///  \\\|//  \\|//  \\\|//   |
//10 |^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^|
//11 |/////////////////////////////////////////////////////////////////|
//  0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 |
//  0         1         2         3         4         5         6     |



}
