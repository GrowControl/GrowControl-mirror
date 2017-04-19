package com.growcontrol.server;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

import com.poixson.app.xApp;
import com.poixson.app.steps.xAppStep;
import com.poixson.app.steps.xAppStep.StepType;
import com.poixson.utils.StringUtils;


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
 */
public class gcServer extends xApp {



	public static gcServer get() {
		return (gcServer) xApp.get();
	}



	public gcServer() {
		super();
//		gcServerVars.init();
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
System.out.println("GC CONFIGS");
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
//					xLog.getRoot().setLevel(lvl);
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
System.out.println("PROMPT");
//TODO:
//		final xCommandsHandler handler = gcServerVars.commands();
//		handler.register(
//			new gcCommonCommands()
//		);
//		handler.register(
//			new gcServerCommands()
//		);
//		xLog.setCommandHandler(
//			handler
//		);
//		// console input
//		final gcServerConfig config = gcServerVars.getConfig();
//		xLog.getConsole()
//			.Start();
//		// prompt ticker
//		if (config.getPromptTickerEnabled()) {
//			new xTickPrompt();
//		}
	}



	// tick scheduler
	@xAppStep(type=StepType.STARTUP, title="Ticker", priority=160)
	public void __STARTUP_ticker() {
System.out.println("TICKER");
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
System.out.println("EVENT HANDLER");
//TODO:
//		getLogicQueue();
	}



	// load plugins
	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=250)
	public void __STARTUP_load_plugins() {
System.out.println("LOAD PLUGINS");
//TODO:
//		final xPluginManager manager = xPluginManager.get();
//		manager.setClassField("Server Main");
//		manager.loadAll();
//		manager.initAll();
	}



	// enable plugins
	@xAppStep(type=StepType.STARTUP, title="StartPlugins", priority=275)
	public void __STARTUP_start_plugins() {
System.out.println("START PLUGINS");
//TODO:
//		xPluginManager.get()
//			.enableAll();
	}



	// sockets
	@xAppStep(type=StepType.STARTUP, title="Sockets", priority=300)
	public void __STARTUP_sockets() {
System.out.println("SOCKETS");
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
System.out.println("SCRIPTS");
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
System.out.println("SCRIPTS");
//TODO:
//		gcScriptManager.get()
//			.StopAll();
//TODO: wait for scripts to finish
	}



	// stop listening sockets
	@xAppStep(type=StepType.SHUTDOWN, title="Sockets", priority=300)
	public void __SHUTDOWN_sockets() {
System.out.println("SOCKETS");
//TODO:
//		final NetServerManager manager = NetServerManager.get();
//		manager.CloseAll();
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="StopPlugins", priority=275)
	public void __SHUTDOWN_stop_plugins() {
System.out.println("PLUGINS");
//TODO:
//		xPluginManager.get()
//			.disableAll();
//TODO: wait for plugins to stop
//		xPluginManager.get()
//			.unloadAll();
	}



	@xAppStep(type=StepType.SHUTDOWN, title="EventHandler", priority=200)
	public void __SHUTDOWN_event_handler() {
System.out.println("EVENT HANDLER");
//TODO:
	}



	// stop ticker
	@xAppStep(type=StepType.SHUTDOWN, title="Ticker", priority=160)
	public void __SHUTDOWN_ticker() {
System.out.println("TICKER");
//TODO:
//		xTickHandler.get()
//			.Stop();
	}



	// ------------------------------------------------------------------------------- //



	// ascii header
	@Override
	protected void displayLogo() {
		// colors
		final String COLOR_POIXSON_P   = "bold,green";
		final String COLOR_POIXSON_OI  = "bold,blue";
		final String COLOR_POIXSON_X   = "bold,green";
		final String COLOR_POIXSON_SON = "bold,blue";
		final String COLOR_COPY        = "bold,black";
		final String COLOR_GROW        = "bold,green";
		final String COLOR_CONTROL     = "bold,white";
		final String COLOR_SOFTWARE    = "cyan";
		final String COLOR_VERSION     = "cyan";
		final String COLOR_GRASS       = "green";
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
		colors1.put(new Integer(7),  COLOR_POIXSON_P);
		colors1.put(new Integer(8),  COLOR_POIXSON_OI);
		colors1.put(new Integer(10), COLOR_POIXSON_X);
		colors1.put(new Integer(11), COLOR_POIXSON_SON);
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
		// build art
		final String version = utilsString.padCenter(15, this.mvnprops.version, ' ');
		final PrintStream out = AnsiConsole.out;
		out.println();
		this.displayLogoLine(out, colors1, "      PoiXson                                                    "   );
		this.displayLogoLine(out, colors2, "   ©GROWCONTROL     _                                            "   );
		this.displayLogoLine(out, colors3, "      Server      _(_)_                          wWWWw   _       "   );
		this.displayLogoLine(out, colors4, " "+version+" (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     "       );
		this.displayLogoLine(out, colors5, "            wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)    "  );
		this.displayLogoLine(out, colors6, "     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\     " );
		this.displayLogoLine(out, colors7, "    @@()@@    Y       \\|    \\|/    /(_)    \\|      |/      |/    ");
		this.displayLogoLine(out, colors8, "    \\@@@@   \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/    ");
		this.displayLogoLine(out, colors9, "    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//   ");
		this.displayLogoLine(out, colors10,"^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^"    );
		this.displayLogoLine(out, colors11,"/////////////////////////////////////////////////////////////////"    );
		out.println();
		out.println(" Copyright (C) 2007-2015 PoiXson, Mattsoft");
		out.println(" - Brainchild of the one known as lorenzo -");
		out.println(" This program comes with absolutely no warranty. This is free software");
		out.println(" and you are welcome to modify it or redistribute it under certain");
		out.println(" conditions. Type 'show license' for license details.");
		out.println();
		out.flush();
	}

//   |       A      B      C       D      E       F      G      H      |
// 1 |      PoiXson                                                    |
// 2 |   ©GROWCONTROL     _                                            |
// 3 |      Server      _(_)_                          wWWWw   _       |
// 4 | <---version---> (_)@(_)   vVVVv     _     @@@@  (___) _(_)_     |
// 5 |            wWWWw  (_)\    (___)   _(_)_  @@()@@   Y  (_)@(_)    |
// 6 |     @@@@   (___)     `|/    Y    (_)@(_)  @@@@   \|/   (_)\     |
// 7 |    @@()@@    Y       \|    \|/    /(_)    \|      |/      |/    |
// 8 |    \@@@@   \ |/       | / \ | /  \|/       |/    \|      \|/    |
// 9 |    \\|//   \\|///  \\\|//\\\|/// \|///  \\\|//  \\|//  \\\|//   |
//10 |^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^|
//11 |/////////////////////////////////////////////////////////////////|
//   0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 |
//   0         1         2         3         4         5         6     |

//System.out.println("                     .==IIIIIIIIIIII=:.");
//System.out.println("               .7IIII777777II7I7I77777III.");
//System.out.println(" .+?IIII7IIIIII7777I++III+I+++?+I77IIII777II");
//System.out.println(" .II=+?7777777II?+==+====?+=++?7++++?I?I7777II");
//System.out.println("  ~II=III+?=+=======+==III=?+==+I??+?+7???II77I.");
//System.out.println("   +I7?==+===?=III+?I=?===+=?=?I??=?+++??III?I77=");
//System.out.println("     I77II+=+=7=?======?=?~+=I====?7+=???+++II?7I7        .II7I7=.");
//System.out.println("      II7I7+===I=?I+++++~=~+7~~=??I=I=+=++?7++??777...7I7I7??7++7I77IIIIIIIII");
//System.out.println("        II7+I77I+=~?7=I~?7I=?7I~~+=++7=I===+II++77I7777I??77I++=?7I===+?7?+=7I");
//System.out.println("          II7++~+I7I77777777777777I?=~77+=I+?=++I7777??I?+++=?I?===77I+=+7+7:");
//System.out.println("            ~IIIII+==+~~~~+:?~=:~~+II77777??=I+?+777I??+=7?=?=II==I==I+?77=");
//System.out.println("                IIIIII7777??+=::~?:~~~=I=I777=?I??7?+I7?7+~7777=+77777?+I");
//System.out.println("                    :IIIIIIII7777777I:?~~~+~I77I=?I=I+77?=?::=~??+?777I");
//System.out.println("                             IIIIIII7777+~~?~=+777?I77?~~77777II,");
//System.out.println("                                  ~7III777?:~~?~777I~:?77II");
//System.out.println("                                      ~III77~~~++7~I=77");
//System.out.println("                                         :II7I:::I7:7I.");
//System.out.println("                                           ~I77:~~:I7II");
//System.out.println("                                            +I77:::I7II");
//System.out.println("                                             II7,,::77II");
//System.out.println("                            PoiXson           I7?~~,=7I");
//System.out.println("                          ©GROWCONTROL         I7?,:III");
//System.out.println("                                                ~III+.");



}
