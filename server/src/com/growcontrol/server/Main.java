package com.growcontrol.gcServer;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import com.growcontrol.gcCommon.pxnApp;
import com.growcontrol.gcCommon.pxnMain;
import com.growcontrol.gcCommon.pxnLogger.pxnLevel;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnThreadQueue.pxnThreadQueue;
import com.growcontrol.gcServer.serverPlugin.gcServerPluginManager;


public class Main extends pxnMain {


	public static void main(String[] args) {
		Init(new Main(), args);
	}
	@Override
	protected pxnApp getAppInstance() {
		return new gcServer();
	}


	// app startup
	@Override
	protected void StartMain() {
//		if(gcServer.server != null) throw new UnsupportedOperationException("Cannot redefine singleton gcServer; already running");
		// init gc server
		getAppInstance();
		// welcome message
		displayLogoHeader();
		displayStartupVars();
		// parse command args
		ProcessArgs();
		// queue server startup
		gcServer.doStart();
		// start/hand-off thread to main queue
		pxnThreadQueue.getMain().run();
		// main thread ended
		pxnLog.get().warning("Main process ended (this shouldn't happen)");
		System.out.println();
		System.out.println();
		System.exit(0);
	}
	private void ProcessArgs() {
		synchronized(args) {
			// process args
			args.reset();
			while(args.hasNext()) {
				String arg = args.getNext();
				if(arg.startsWith("--"))
					arg = arg.toLowerCase();
				switch(arg) {
				// version
				case "--version":
				case "-V":
					System.out.println("GrowControl "+gcServer.version+" Server");
					System.exit(0);
				// no console
				case "--no-console":
				case "-n":
					gcServer.get().setConsoleEnabled(false);
					addArgsMsg("no-console");
					break;
				// debug mode
				case "--debug":
				case "-d":
					gcServer.get().setForceDebug(true);
					addArgsMsg("debug");
					break;
				// log level
				case "--loglevel":
				case "--log":
				case "--level":
				case "-l":
					if(!args.next()) {
						System.out.println("Incomplete! --configs-path argument");
						break;
					}
					gcServer.get().setLogLevel(pxnLevel.Parse(args.getPart()));
//					pxnLog.get().setLevel(pxnLevel.Parse(args.getPart()));
					addArgsMsg("level");
					break;
				// configs path
				case "--configs-path":
					if(!args.next()) {
						System.out.println("Incomplete! --configs-path argument");
						break;
					}
					gcServer.get().setConfigsPath(args.getPart());
					System.out.println("Set configs path to: "+args.getPart());
					addArgsMsg("configs-path");
					break;
				// plugins path
				case "--plugins-path":
				case "-p":
					if(!args.next()) {
						System.out.println("Incomplete! --configs-path argument");
						break;
					}
					gcServerPluginManager.get(args.getPart());
					System.out.println("Set plugins path to: "+args.getPart());
					addArgsMsg("plugins-path");
				// unknown
				default:
					System.out.println("Unknown argument: "+arg);
					break;
				}
			}
		}
	}


	// ascii header
	public static void displayStartupVars() {
		AnsiConsole.out.println(" Grow Control Server "+gcServer.version);
		AnsiConsole.out.println(" Running as: "+System.getProperty("user.name"));
		AnsiConsole.out.println(" Current dir: "+System.getProperty("user.dir"));
		AnsiConsole.out.println(" java home: "+System.getProperty("java.home"));
		if(gcServer.get().forceDebug())
			AnsiConsole.out.println(" Force Debug: true");
		String argsMsg = getArgsMsg();
		if(argsMsg != null && !argsMsg.isEmpty())
			AnsiConsole.out.println(" args: [ "+argsMsg+" ]");
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

		AnsiConsole.out.println(" Copyright (C) 2007-2013 PoiXson, Mattsoft");
		AnsiConsole.out.println(" This program comes with absolutely no warranty. This is free software,");
		AnsiConsole.out.println(" and you are welcome to redistribute it under certain conditions.");
//		AnsiConsole.out.println(" For details type 'show w' for warranty, or 'show c' for conditions.");
		AnsiConsole.out.println(" Type 'show license' for license details.");
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
