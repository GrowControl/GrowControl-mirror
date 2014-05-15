package com.growcontrol.server;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import com.growcontrol.gccommon.listeners.CommandEvent;
import com.growcontrol.gccommon.listeners.MetaEvent;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.CommandHandler;


public class gcServer extends xApp {
	public static final String APPNAME = "gcServer";
	public static final String VERSION = "3.1.0";

	// server socket pool
//	private volatile pxnSocketServer socket = null;

	// zones
//	private final List<String> zones = new ArrayList<String>();


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
		displayStartupVars();
		displayLogoHeader();
		initMain(args, new gcServer());
	}
	protected gcServer() {
		super();
	}


	// init config
	@Override
	protected void initConfig() {
//		ServerConfig.get();
//		if(!ServerConfig.isLoaded()) {
//		log.fatal("Failed to load config.yml, exiting..");
//		System.exit(1);
//		return;
//		// set log level
//		UpdateLogLevel();
	}


	@Override
	protected void processArgs(final String[] args) {
	}
//	// process command
//	@Override
//	public void ProcessCommand(String line) {
//		if(line == null) throw new NullPointerException("line cannot be null");
//		line = line.trim();
//		if(line.isEmpty()) return;
//		// trigger event
//		if(!ServerListeners.get().triggerCommand(line)) {
//			// command not found
//			pxnLog.get().Publish("Unknown command: "+line);
//		}
//	}


	/**
	 * Server startup sequence.
	 *   2. Listeners
	 *   3. Command prompt
	 *   4.
	 *   5.
	 *   6. Load plugins and sockets
	 *   7. Start plugins and sockets
	 * @return true if success, false if problem.
	 */
	@Override
	protected boolean startup(final int step) {
		switch(step) {
		// listeners
		case 2:
			// init listeners
			final gcServerVars vars = gcServerVars.get();
			// server command listener
			vars.commands().register(
				new ServerCommands()
			);
			// io event listener
			//getLogicQueue();
			return true;
		// command prompt
		case 3:
			// command processor
			xLog.setCommandHandler(new CommandHandler() {
				@Override
				public void processCommand(String line) {
					final CommandEvent event = new CommandEvent(line);
					gcServerVars.get()
						.commands().trigger(
							event
						);
					if(!event.isHandled())
						log().warning("Unknown command: "+event.arg(0));
				}
			});
			// start console input thread
			initConsole();
			return true;
		case 4:

// load zones
// load scheduler
// load plugins
// start devices
// start socket listener
// server ready

//			// load scheduler
//			log.info("Starting schedulers..");
//			pxnScheduler.get().Start();
//			pxnTicker.get().setInterval(ServerConfig.TickInterval());
//			pxnTicker.get().Start();

//			// load devices
//			deviceLoader.LoadDevices(Arrays.asList(new String[] {"Lamp"}));

			return true;
		case 5:

			// load zones
//			synchronized(zones) {
//				config.PopulateZones(zones);
//				log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//			}
			// start logic thread queue
//			getLogicQueue();

			return true;
		// load plugins and sockets
		case 6:

//			try {
//				gcServerPluginManager pluginManager = gcServerPluginManager.get("plugins/");
//				pluginManager.LoadPluginsDir();
//				pluginManager.InitPlugins();
//				pluginManager.EnablePlugins();
//			} catch (Exception e) {
//				log.exception(e);
//				Shutdown();
//				System.exit(1);
//			}

//			// start socket listener
//			if(socket == null)
//				socket = new pxnSocketServer();
//			socket.setHost();
//			socket.setPort(ServerConfig.ListenPort());
//			// create processor
//			socket.setFactory(new pxnSocketProcessorFactory() {
//				@Override
//				public gcPacketReader newProcessor() {
//					return new gcPacketReader();
//				}
//			});
//			socket.Start();

			return true;
		// start plugins and sockets
		case 7:
			return true;
		}
		return false;
	}
	/**
	 * Server shutdown sequence.
	 *   7. Stop plugins and sockets
	 *   6. Unload plugins and sockets
	 *   5.
	 *   4.
	 *   3.
	 *   2.
	 * @return true if success, false if problem.
	 */
	@Override
	protected boolean shutdown(final int step) {
		switch(step) {
		// stop plugins and sockets
		case 7:

//			// close socket listener
//			if(socket != null)
//				socket.Close();
//			// pause scheduler
//			pxnScheduler.PauseAll();

//			{
//				gcServerPluginManager manager = gcServerPluginManager.get();
//				if(manager != null)
//					manager.DisablePlugins();
//			}
//			// end schedulers
//			pxnScheduler.ShutdownAll();

			return true;
		// unload plugins and sockets
		case 6:

//			{
//				gcServerPluginManager manager = gcServerPluginManager.get();
//				if(manager != null)
//					manager.UnloadPlugins();
//			}

//			// close sockets
//			if(socket != null)
//				socket.ForceClose();

			return true;
		case 5:
			return true;
		case 4:
			return true;
		case 3:
			return true;
		case 2:
			return true;
		}
		return false;
	}


	@Override
	public String getAppName() {
		return APPNAME;
	}
	@Override
	public String getVersion() {
		return VERSION;
	}







//System.out.println("Trying to trigger an event");
//listeners.commands().trigger(
//	new CommandEvent("stop")
//);



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



//	// log level
//	@Override
//	public void setLogLevel(pxnLevel level) {
//		if(level == null) return;
//		this.level = level;
//		UpdateLogLevel();
//	}
//	@Override
//	protected void UpdateLogLevel() {
//		if(forceDebug) {
//			pxnLog.get().setLevel(pxnLevel.DEBUG);
//			return;
//		}
//		if(level == null)
//			if(ServerConfig.isLoaded())
//				level = ServerConfig.LogLevel();
//		if(level != null)
//			pxnLog.get().setLevel(level);
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
	public static void displayStartupVars() {
		AnsiConsole.out.println(" Grow Control Server "+gcServer.VERSION);
		AnsiConsole.out.println(" Running as:  "+System.getProperty("user.name"));
		AnsiConsole.out.println(" Current dir: "+System.getProperty("user.dir"));
		AnsiConsole.out.println(" java home:   "+System.getProperty("java.home"));
//		if(gcServer.get().forceDebug())
//			AnsiConsole.out.println(" Force Debug: true");
//		String argsMsg = getArgsMsg();
//		if(argsMsg != null && !argsMsg.isEmpty())
//			AnsiConsole.out.println(" args: [ "+argsMsg+" ]");
		AnsiConsole.out.println();
		AnsiConsole.out.flush();
	}
	protected static void displayLogoHeader() {
		AnsiConsole.out.println();
		// line 1
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.bold().a("      ")
			.fg(Ansi.Color.GREEN).a("P")
			.fg(Ansi.Color.WHITE).a("oi")
			.fg(Ansi.Color.GREEN).a("X")
			.fg(Ansi.Color.WHITE).a("son")
			.a("                                                    ")
			.reset() );
		// line 2
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.bold().a("    ")
			.fg(Ansi.Color.GREEN).a("GROW")
			.fg(Ansi.Color.WHITE).a("CONTROL")
			.fg(Ansi.Color.YELLOW).a("     _")
			.a("                                            ")
			.reset() );
		// line 3
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.a("                  ")
			.fg(Ansi.Color.YELLOW).bold().a("_(_)_                          ").boldOff()
			.fg(Ansi.Color.MAGENTA).a("wWWWw   ")
			.fg(Ansi.Color.YELLOW).bold().a("_")
			.a("       ")
			.reset() );
		// line 4
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.a("      ")
			.fg(Ansi.Color.RED).a("@@@@").a("       ")
			.fg(Ansi.Color.YELLOW).bold().a("(_)@(_)   ").boldOff()
			.fg(Ansi.Color.MAGENTA).a("vVVVv     ")
			.fg(Ansi.Color.YELLOW).bold().a("_     ").boldOff()
			.fg(Ansi.Color.BLUE).a("@@@@  ")
			.fg(Ansi.Color.MAGENTA).a("(___) ")
			.fg(Ansi.Color.YELLOW).bold().a("_(_)_")
			.a("     ")
			.reset() );
		// line 5
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.a("     ")
			.fg(Ansi.Color.RED).a("@@()@@ ")
			.fg(Ansi.Color.MAGENTA).bold().a("wWWWw  ")
			.fg(Ansi.Color.YELLOW).a("(_)").boldOff()
			.fg(Ansi.Color.GREEN).a("\\    ")
			.fg(Ansi.Color.MAGENTA).a("(___)   ")
			.fg(Ansi.Color.YELLOW).bold().a("_(_)_  ").boldOff()
			.fg(Ansi.Color.BLUE).a("@@()@@   ")
			.fg(Ansi.Color.MAGENTA).a("Y  ")
			.fg(Ansi.Color.YELLOW).bold().a("(_)@(_)")
			.a("    ")
			.reset() );
		// line 6
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.a("      ")
			.fg(Ansi.Color.RED).a("@@@@  ")
			.fg(Ansi.Color.MAGENTA).bold().a("(___)     ").boldOff()
			.fg(Ansi.Color.GREEN).a("`|/    ")
			.fg(Ansi.Color.MAGENTA).a("Y    ")
			.fg(Ansi.Color.YELLOW).bold().a("(_)@(_)  ").boldOff()
			.fg(Ansi.Color.BLUE).a("@@@@   ")
			.fg(Ansi.Color.GREEN).a("\\|/   ")
			.fg(Ansi.Color.YELLOW).bold().a("(_)").boldOff()
			.fg(Ansi.Color.GREEN).a("\\")
			.a("     ")
			.reset() );
		// line 7
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.fg(Ansi.Color.GREEN).a("       /      ")
			.fg(Ansi.Color.MAGENTA).a("Y       ")
			.fg(Ansi.Color.GREEN).a("\\|    \\|/    /")
			.fg(Ansi.Color.YELLOW).bold().a("(_)    ").boldOff()
			.fg(Ansi.Color.GREEN).a("\\|      |/      |     ")
			.reset() );
		// line 8
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.fg(Ansi.Color.GREEN).a("    \\ |     \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/    ")
			.reset() );
		// line 9
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.fg(Ansi.Color.GREEN).a("    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//   ")
			.reset() );
		// line 10
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.fg(Ansi.Color.GREEN).a("^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^")
			.reset() );
		// line 11
		AnsiConsole.out.println(Ansi.ansi()
			.a(" ").bg(Ansi.Color.BLACK)
			.fg(Ansi.Color.GREEN).a("/////////////////////////////////////////////////////////////////")
			.reset() );
		AnsiConsole.out.println();

		AnsiConsole.out.println(" Copyright (C) 2007-2014 PoiXson, Mattsoft");
		AnsiConsole.out.println(" This program comes with absolutely no warranty. This is free software");
		AnsiConsole.out.println(" and you are welcome to modify it or redistribute it under certain");
		AnsiConsole.out.println(" conditions. Type 'show license' for license details.");
		AnsiConsole.out.println();
		AnsiConsole.out.flush();
	}
// 1 |      PoiXson
// 2 |    ©GROWCONTROL    _
// 3 |                  _(_)_                          wWWWw   _
// 4 |      @@@@       (_)@(_)   vVVVv     _     @@@@  (___) _(_)_
// 5 |     @@()@@ wWWWw  (_)\    (___)   _(_)_  @@()@@   Y  (_)@(_)
// 6 |      @@@@  (___)     `|/    Y    (_)@(_)  @@@@   \|/   (_)\
// 7 |       /      Y       \|    \|/    /(_)    \|      |/      |
// 8 |    \ |     \ |/       | / \ | /  \|/       |/    \|      \|/
// 9 |    \\|//   \\|///  \\\|//\\\|/// \|///  \\\|//  \\|//  \\\|//
//10 |^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

//System.out.println("      "+Ansi.Color.MAGENTA+"PoiXson");
//System.out.println("    ©GROWCONTROL    _");
//System.out.println("                  _(_)_                          wWWWw   _");
//System.out.println("      @@@@       (_)@(_)   vVVVv     _     @@@@  (___) _(_)_");
//System.out.println("     @@()@@ wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)");
//System.out.println("      @@@@  (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\");
//System.out.println("       /      Y       \\|    \\|/    /(_)    \\|      |/      |");
//System.out.println("    \\ |     \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/");
//System.out.println("    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//");
//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

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
