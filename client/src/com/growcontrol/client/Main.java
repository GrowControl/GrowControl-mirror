package com.growcontrol.gcClient;

import org.fusesource.jansi.AnsiConsole;

import com.growcontrol.gcClient.clientPlugin.gcClientPluginManager;
import com.growcontrol.gcCommon.pxnApp;
import com.growcontrol.gcCommon.pxnMain;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnThreadQueue.pxnThreadQueue;


public class Main extends pxnMain {


	public static void main(String[] args) {
		Init(new Main(), args);
	}
	@Override
	protected pxnApp getAppInstance() {
		return new gcClient();
	}


	// app startup
	@Override
	protected void StartMain() {
//		if(gcClient.client != null) throw new UnsupportedOperationException("Cannot redefine singleton gcClient; already running");
		// init gc client
		getAppInstance();
		// welcome message
		displayLogoHeader();
		displayStartupVars();
		// parse command args
		ProcessArgs();
		// queue client startup
		gcClient.doStart();
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
					System.out.println("GrowControl "+gcClient.version+" Server");
					System.exit(0);
				// no console
				case "--no-console":
				case "-n":
					gcClient.get().setConsoleEnabled(false);
					addArgsMsg("no-console");
					break;
				// debug mode
				case "--debug":
				case "-d":
					gcClient.get().setForceDebug(true);
					addArgsMsg("debug");
					break;
				// configs path
				case "--configs-path":
					if(!args.next()) {
						System.out.println("Incomplete! --configs-path argument");
						break;
					}
					gcClient.get().setConfigsPath(args.getPart());
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
					gcClientPluginManager.get(args.getPart());
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
		AnsiConsole.out.println(" Grow Control Client "+gcClient.version);
		AnsiConsole.out.println(" Running as: "+System.getProperty("user.name"));
		AnsiConsole.out.println(" Current dir: "+System.getProperty("user.dir"));
		AnsiConsole.out.println(" java home: "+System.getProperty("java.home"));
		if(gcClient.get().forceDebug())
			AnsiConsole.out.println(" Force Debug: true");
		String argsMsg = getArgsMsg();
		if(argsMsg != null && !argsMsg.isEmpty())
			AnsiConsole.out.println(" args: [ "+argsMsg+" ]");
		AnsiConsole.out.println();
		AnsiConsole.out.flush();
	}
	protected static void displayLogoHeader() {
	}


//	// shutdown client
//	public static void Shutdown() {
//		if(gcClient.client == null) {
//			// stop gui manager only
//			guiManager.Shutdown();
//			System.exit(0);
//		} else {
//			// stop client instance
//			gcClient.get().Shutdown();
//		}
//	}
//	public static void HideGUI() {
//		client.HideGUI();
//	}


//	// get client
//	public static gcClient getClient() {
//		return client;
//	}


//	// get main logger
//	public static gcLogger getLogger() {
//		return log;
//	}


}
