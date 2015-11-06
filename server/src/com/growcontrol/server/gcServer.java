package com.growcontrol.server;

import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

import com.growcontrol.common.commands.gcCommonCommands;
import com.growcontrol.common.scripting.gcScriptManager;
import com.growcontrol.server.commands.gcServerCommands;
import com.growcontrol.server.configs.NetServerConfig;
import com.growcontrol.server.configs.gcServerConfig;
import com.growcontrol.server.net.NetServerManager;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonapp.app.annotations.xAppStep;
import com.poixson.commonapp.app.annotations.xAppStep.StepType;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonapp.plugin.xPluginManager;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.xVars;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsString;
import com.poixson.commonjava.scheduler.ticker.xTickHandler;
import com.poixson.commonjava.scheduler.ticker.xTickPrompt;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.commands.xCommandsHandler;


public class gcServer extends xApp {



	/**
	 * Get the server class instance.
	 * @return
	 */
	public static gcServer get() {
		return (gcServer) xApp.get();
	}



	/**
	 * Application start entry point.
	 * @param args Command line arguments.
	 */
	public static void main(final String[] args) {
		initMain(args, gcServer.class);
	}
	public gcServer() {
		super();
		gcServerVars.init();
		if(xVars.debug())
			this.displayColors();
		this.displayLogo();
	}



	/**
	 * Handle command-line arguments.
	 */
	@Override
	protected void processArgs(final String[] args) {
		if(utils.isEmpty(args)) return;
		for(final String arg : args) {
			switch(arg) {
			case "--debug":
				xVars.debug(true);
				break;
			case "--internal":
				gcServerVars.setInternal(true);
				break;
			default:
				System.out.println("Unknown argument: "+arg);
				System.exit(1);
				break;
			}
		}
	}



	@Override
	public xCommandsHandler getCommandsHandler() {
		return gcServerVars.commands();
	}



	// ------------------------------------------------------------------------------- //
	// startup



	// load config
	@xAppStep(type=StepType.STARTUP, title="Config", priority=20)
	public void __STARTUP_config() {
		gcServerVars.getConfig();
	}



	// command prompt
	@xAppStep(type=StepType.STARTUP, title="Commands", priority=30)
	public void __STARTUP_commands() {
		final xCommandsHandler handler = gcServerVars.commands();
		handler.register(
				new gcCommonCommands()
		);
		handler.register(
				new gcServerCommands()
		);
		xLog.setCommandHandler(
				handler
		);
	}



	// console input
	@xAppStep(type=StepType.STARTUP, title="Console", priority=32)
	public void __STARTUP_console() {
		final gcServerConfig config = gcServerVars.getConfig();
		xLog.getConsole()
			.Start();
		// prompt ticker
		if(config.getPromptTickerEnabled())
			new xTickPrompt();
	}

//	// io event listener
//	@xAppStep(type=StepType.STARTUP, title="ioEvents", priority=40)
//	public void _startup_ioevents_() {
//		getLogicQueue();
//	}



	// load plugins
	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=50)
	public void __STARTUP_load_plugins() {
		final xPluginManager manager = xPluginManager.get();
		manager.setClassField("Server Main");
		manager.loadAll();
		manager.initAll();
	}



	// enable plugins
	@xAppStep(type=StepType.STARTUP, title="EnablePlugins", priority=55)
	public void __STARTUP_enable_plugins() {
		xPluginManager.get()
			.enableAll();
	}



	// tick scheduler
	@xAppStep(type=StepType.STARTUP, title="Ticker", priority=80)
	public void __STARTUP_ticker() {
		final gcServerConfig config = gcServerVars.getConfig();
		final xTickHandler ticker = xTickHandler.get();
		ticker.setInterval(
				config.getTickInterval()
		);
		ticker.Start();
	}



	// sockets
	@xAppStep(type=StepType.STARTUP, title="Sockets", priority=90)
	public void __STARTUP_sockets() {
		// load socket configs
		final Map<String, NetServerConfig> netConfigs;
		try {
			netConfigs = gcServerVars.getConfig().getNetConfigs();
		} catch (xConfigException e) {
			this.log().trace(e);
			Failure.fail(e.getMessage());
			return;
		}
		if(utils.isEmpty(netConfigs)) {
			log().warning("No socket server configs found to load");
			return;
		}
		// start socket servers
		final NetServerManager manager = NetServerManager.get();
		for(final NetServerConfig config : netConfigs.values()) {
			if(!config.isEnabled()) continue;
			try {
				manager.getServer(config);
			} catch (UnknownHostException e) {
				log().trace(e);
			} catch (InterruptedException e) {
				log().trace(e);
				break;
			}
		}
//		// log configs
//		for(final NetConfig dao : configs) {
//			if(dao.enabled) {
//				this.log().getWeak("sockets").finer(
//						dao.host+":"+Integer.toString(dao.port)+
//						(dao.ssl ? " (ssl)" : " (raw)")
//				);
//			}
//		}
	}



	// scripts
	@xAppStep(type=StepType.STARTUP, title="Scripts", priority=95)
	public void __STARTUP_scripts() {
		final gcScriptManager manager = gcScriptManager.get();
		manager.loadAll();
		manager.StartAll();
	}



//		// load zones
//		synchronized(zones) {
//			config.PopulateZones(zones);
//			log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//		}
//		// start logic thread queue
//		getLogicQueue();
//
//		// start socket listener
//		if(socket == null)
//			socket = new pxnSocketServer();
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



	// ------------------------------------------------------------------------------- //
	// shutdown



	// scripts
	@xAppStep(type=StepType.SHUTDOWN, title="Scripts", priority=95)
	public void __SHUTDOWN_scripts() {
		gcScriptManager.get()
			.StopAll();
	}



	// sockets
	@xAppStep(type=StepType.SHUTDOWN, title="Sockets", priority=90)
	public void __SHUTDOWN_sockets() {
		final NetServerManager manager = NetServerManager.get();
		manager.CloseAll();
	}



	// stop ticker
	@xAppStep(type=StepType.SHUTDOWN, title="Ticker", priority=80)
	public void __SHUTDOWN_ticker() {
		xTickHandler.get()
			.Stop();
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="DisablePlugins", priority=55)
	public void __SHUTDOWN_disable_plugins() {
		xPluginManager.get()
			.disableAll();
	}



	// unload plugins
	@xAppStep(type=StepType.SHUTDOWN, title="UnloadPlugins", priority=50)
	public void __SHUTDOWN_unload_plugins() {
		xPluginManager.get()
			.unloadAll();
	}



	// ------------------------------------------------------------------------------- //



/*
	private void updateConfig() {
	// config version
	{
		boolean configVersionDifferent = false;
		final String configVersion = this.config.getVersion();
		final String serverVersion = this.getVersion();
		if(utils.notEmpty(configVersion) && utils.notEmpty(serverVersion)) {
			if(configVersion.endsWith("x") || configVersion.endsWith("*")) {
				final String vers = utilsString.trims(configVersion, "x", "*");
				if(!serverVersion.startsWith(vers))
					configVersionDifferent = true;
			} else {
				if(!configVersion.equals(serverVersion))
					configVersionDifferent = true;
			}
		}
		if(configVersionDifferent)
			log().warning(gcServerDefines.CONFIG_FILE+" for this server may need updates");
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
				xLog.getRoot().setLevel(level);
		}
	}
	// tick interval
	{
		final xTime tick = this.config.getTickInterval();
		xTicker.get()
			.setInterval(tick);
	}
//	// logic threads (0 uses main thread)
//	{
//		@SuppressWarnings("unused")
//		final int logic = this.config.getLogicThreads();
//		//TODO: apply this to logic thread pool
//	}
//	// zones
//	{
//		this.config.populateZones(this.zones);
//	}
	// sockets
	{
		final NetServerManager manager = NetServerManager.get();
		manager.setConfigs(this.config.getSocketConfigs());
	}
}
*/



//		pxnLogger log = pxnLog.get();
//if(!consoleEnabled) {
//System.out.println("Console input is disabled due to noconsole command argument.");
////TODO: currently no way to stop the server with no console input
//System.exit(0);
//}


//TODO: remove this
//log.severe("Listing Com Ports:");
//for(Map.Entry<String, String> entry : Serial.listPorts().entrySet())
//log.severe(entry.getKey()+" - "+entry.getValue());


//TODO: remove temp scheduled task
// new task (multi-threaded / repeat)
//pxnSchedulerTask task = new pxnSchedulerTask(true, true) {
//	@Override
//	public void run() {
//		pxnLog.get().Publish("333333333 tick");
//	}
//	@Override
//	public String getTaskName() {
//		return "333tickname";
//	}
//};
//task.addTrigger(new triggerInterval("3s"));
//pxnScheduler.get("gcServer").newTask(task);

//System.out.println("next run: "+task.UntilNextTrigger().get(TimeU.MS));


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
