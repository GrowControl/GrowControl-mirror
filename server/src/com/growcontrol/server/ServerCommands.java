package com.growcontrol.gcServer;

import java.io.IOException;

import jline.console.ConsoleReader;

import com.growcontrol.gcCommon.meta.metaValue;
import com.growcontrol.gcCommon.pxnCommand.pxnCommandEvent;
import com.growcontrol.gcCommon.pxnCommand.pxnCommandsHolder;
import com.growcontrol.gcCommon.pxnLogger.pxnConsole;
import com.growcontrol.gcCommon.pxnLogger.pxnLevel;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;


public final class ServerCommands extends pxnCommandsHolder {
	private ServerCommands() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static ServerCommands instance = null;
	private static final Object lock = new Object();


	public static ServerCommands get() {
		if(instance == null) {
			synchronized(lock) {
				if(instance == null)
					instance = new ServerCommands();
			}
		}
		return instance;
	}
	@Override
	public void initCommands() {
		// server control
		addCommand("stop")
			.addAlias("exit")
			.addAlias("quit")
			.addAlias("shutdown")
//TODO: remove this
.addAlias("s")
			.setUsage("Stops and closes the server.");
		addCommand("kill")
//TODO: remove this
.addAlias("k")
			.setUsage("Emergency shutdown, no saves or power-downs. Don't use this unless you need to.");
		addCommand("start")
			.addAlias("resume")
			.setUsage("Starts or resumes server tasks and schedulers.");
		addCommand("pause")
			.setUsage("Pauses or resumes the scheduler and some plugin tasks. Optional argument: [on/off/true/false/1/0]");
		addCommand("clear")
			.addAlias("cls")
			.setUsage("Clears the console screen.");
		addCommand("help")
			.addAlias("?");
		addCommand("log")
			.addAlias("level")
			.setUsage("log level info - Sets or displays the log level.");
		addCommand("show")
			.setUsage("Displays additional information.");
		addCommand("version")
			.setUsage("Displays the current running version, and the latest available (if enabled)");
		addCommand("say")
			.addAlias("broadcast")
			.addAlias("wall")
			.setUsage("");
//		addCommand("list")
//		// input / output
//		addCommand("set")
//		addCommand("get")
//		addCommand("watch")
//		// tools
//		addCommand("ping")
//			.setUsage("");
		addCommand("threads")
			.setUsage("Displays number of loaded threads, and optional details.");
//		setAllPriority(EventPriority.LOWEST);
		addCommand("route")
			.addAlias("send")
			.setUsage("Sends an event to the meta data router, which passes to plugins.\nUsage: route <ReceiverName> [MetaType] <Value>");
	}


	@Override
	public boolean onCommand(pxnCommandEvent commandEvent) {
		if(commandEvent.isHandled()) return false;
		switch(commandEvent.getCommandStr()) {
		// server control
		case "stop":
			return ServerCommands._stop();
		case "kill":
			return _kill();
		case "start":
			return _start();
		case "pause":
			return _pause();
		case "clear":
			return _clear();
		case "help":
			return _help(commandEvent.getArgs());
		case "log":
			return _log(commandEvent.getArgs());
		case "show":
			return _show(commandEvent.getArgs());
		case "version":
			return _version();
		case "say":
			String msg = commandEvent.raw;
			if(msg.contains(" ")) msg = msg.substring(msg.indexOf(" ") + 1);
			return _say(msg);
//		// tools
//		case "ping":
//			return _ping( (String[]) event.args.toArray());
		case "threads":
			return _threads();
		case "route":
			return _route(commandEvent);
		default:
			break;
		}
		return false;
	}


	// stop command
	private static boolean _stop() {
		gcServer.get().Shutdown();
		return true;
	}


	// kill command
	private static boolean _kill() {
		try {
			pxnLog.get().warning("Killing server! (Triggered by console command)");
		} catch(Exception ignore) {}
		System.out.println();
		System.out.println();
		System.exit(0);
		return true;
	}


	// start command
	private static boolean _start() {
		return true;
	}
	// pause command
	private static boolean _pause() {
		return true;
	}


	// clear command
	private static boolean _clear() {
		try {
			ConsoleReader reader = pxnConsole.getReader();
			if(reader == null) return false;
			reader.clearScreen();
			reader.flush();
		} catch (IOException e) {
			pxnLog.get().exception(e);
		}
		return true;
	}


	// help command
	private static boolean _help(String[] args) {
		return true;
	}


	// log command
	private static boolean _log(String[] args) {
		pxnLevel level = null;
		// 0 args
		if(args.length == 0) {
			_log();
			return true;
		} else
		// 1 arg
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("level")) {
				_log();
				return true;
			}
			level = pxnLevel.Parse(args[0]);
		} else
		// 2 args
		if(args.length == 2 && args[0].equalsIgnoreCase("level")) {
			level = pxnLevel.Parse(args[1]);
		}
		// invalid
		if(level == null)
			return false;
		gcServer.get().setLogLevel(level);
		return true;
	}
	private static void _log() {
		pxnLog.get().Publish("Current log level: "+pxnLog.get().getLevel().toString());
	}


	// show command
	private static boolean _show(String[] args) {
		return true;
	}


	// version command
	private static boolean _version() {
		pxnLog.get().info("Version: "+gcServer.version);
		return true;
	}


	// say command
	private static boolean _say(String msg) {
		msg = "(console) "+msg;
		pxnLog.get().Publish(msg);
		return true;
	}


//	// ping command
//	private static boolean _ping(String[] args) {
//		return true;
//	}


	// threads command
	private static boolean _threads() {
		return true;
	}


	// help command
	private static boolean _route(pxnCommandEvent commandEvent) {
		String[] args = commandEvent.getArgs();
		if(args.length != 3) {
			pxnLog.get().Publish("Invalid arguments!\n"+commandEvent.command.getUsageStr());
			return true;
		}
		String toName = args[0];
		metaValue meta = metaValue.newValue(args[1], args[2]);
		if(meta == null) {
			pxnLog.get().Publish("Invalid meta type!");
//TODO: list meta types
			return true;
		}
		// send to router
		meta.Send(toName);
		return true;
	}


//		// help
//		if(command.equals("help")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
//			gcServer.log.info("[ Basic Commands ]");
//			gcServer.log.info("version - Displays the current running version.");
//			gcServer.log.info("start - Starts the scheduler.");
//			gcServer.log.info("stop  - Stops the scheduler.");
//			gcServer.log.info("pause - Pauses the scheduler.");
//			gcServer.log.info("clear - Clears the screen.");
//			gcServer.log.info("say - Broadcasts a message.");
//			gcServer.log.info("list plugins - Lists the loaded plugins.");
//			gcServer.log.info("list devices - Lists the loaded devices.");
//			gcServer.log.info("list outputs - Lists the available outputs.");
//			gcServer.log.info("list inputs  - Lists the available inputs.");
//			gcServer.log.info("[ Tools ]");
//			gcServer.log.info("ping - ");
//			gcServer.log.info("threads - ");
// input / output
//set
//get
//watch
//stop
//exit
//quit
//shutdown
//kill
//			return true;
//		}
//		// version
//		if(command.equals("version")) {
//			//TODO:
//			gcServer.log.info("GrowControl "+gcServer.version);
//			return true;
//		}
//		// say
//		if(command.equals("say")) {
//			String msg = "";
//			for(String line : args) msg += " "+line;
//			gcServer.log.info("Server says:"+msg);
//			return true;
//		}

		// list plugins/devices/inputs/outputs
//		if(command.equals("list")) {
//			if(args.length >= 1) {
//				if(args[0].equalsIgnoreCase("plugins")) {
////					gcServerPluginLoader.listPlugins();
//					return true;
//				} else if(args[0].equalsIgnoreCase("devices")) {
//					listDevices();
//					return true;
//				} else if(args[0].equalsIgnoreCase("outputs")) {
//					listOutputs();
//					return true;
//				} else if(args[0].equalsIgnoreCase("inputs")) {
//					listInputs();
//					return true;
//				}
//			}
//			gcServer.log.info("Usage: "+command.getUsage());
//			return true;
//		}
//		if(command.equals("plugins")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
////			gcPluginLoader.listPlugins();
//			return true;
//		}
//		// list devices
//		if(command.equals("devices")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
////			gcServer.deviceLoader.listDevices();
//			return true;
//		}
//
//		// set input / output
//		if(command.equals("set")) {
////			if(gcServerPluginLoader.doOutput(args)) {
////				String msg = ""; for(String arg : args) msg += arg+" ";
////				gcServer.log.debug("set> "+msg);
////				return true;
////			}
////			String msg = ""; for(String arg : args) msg += arg+" ";
////			gcServer.log.warning("Failed to find an output plugin! "+msg);
//			return true;
//		}
//		// get input / output
//		if(command.equals("get")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
//			return true;
//		}
//		// watch input / output
//		if(command.equals("watch")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
//			return true;
//		}
//
//		// ping
//		if(command.equals("ping")) {
//			//TODO:
//			gcServer.log.warning("command not yet implemented");
//			return true;
//		}
//
//		return false;
//	}


//	private static void listDevices() {
//	}
//	private static void listOutputs() {
//	}
//	private static void listInputs() {
//	}


//	if(command.equalsIgnoreCase("threads")) {
//	// Find the root thread group
//	ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
//	while (root.getParent() != null) {
//	    root = root.getParent();
//	}
//GrowControl.log.severe(Integer.toString(root.activeCount()));
//	// Visit each thread group
//GrowControl.log.severe(Integer.toString(Thread.activeCount()));
//	visitThread(root, 0);
//	return true;
//}
//else if(line.equals("list coms"))
//Serial.listPorts();
//	// This method recursively visits all thread groups under `group'.
//	private static void visitThread(ThreadGroup group, int level) {
//	    // Get threads in `group'
//	    int numThreads = group.activeCount();
//	    Thread[] threads = new Thread[numThreads*2];
//	    numThreads = group.enumerate(threads, false);
//	    // Enumerate each thread in `group'
//	    for(int i=0; i<numThreads; i++)
//	        Thread thread = threads[i];
//	    // Get thread subgroups of `group'
//	    int numGroups = group.activeGroupCount();
//	    ThreadGroup[] groups = new ThreadGroup[numGroups*2];
//	    numGroups = group.enumerate(groups, false);
//	    // Recursively visit each subgroup
//	    for(int i=0; i<numGroups; i++) {
//	    	visitThread(groups[i], level+1);
//	    }
//	}


}
