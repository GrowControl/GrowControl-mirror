package com.growcontrol.server;

import com.growcontrol.gccommon.listeners.CommandEvent;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.xLogger.xLog;


public final class ServerCommands implements xListener {


	@xEvent(
		priority=Priority.NORMAL,
		filtered=false,
		threaded=false,
		ignoreHandled=true,
		ignoreCancelled=true)
	public void onCommand(final CommandEvent event) {
		switch(event.arg(0)) {
		// shutdown
		//.setUsage("Stops and closes the server.");
		case "shutdown":
		case "stop":
		case "exit":
		case "quit":
			_shutdown(event);
			break;
		// kill
		//.setUsage("Emergency shutdown, no saves or power-downs. Don't use this unless you need to.");
		case "kill":
		case "end":
			_kill(event);
			System.exit(0);
			break;
		// clear
//		.setUsage("Clears the console screen.");
		case "clear":
		case "cls":
			_clear(event);
			break;
//		addCommand("start")
//			.addAlias("resume")
//			.setUsage("Starts or resumes server tasks and schedulers.");
//		addCommand("pause")
//			.setUsage("Pauses or resumes the scheduler and some plugin tasks. Optional argument: [on/off/true/false/1/0]");
		case "set":
			_set(event);
			break;
//		addCommand("get")
//		addCommand("watch")

//		addCommand("help")
//			.addAlias("?");
		// log
		case "log":
			_log(event);
			break;
		// log level
		case "level":
			_level(event);
			break;
//		addCommand("log")
//			.addAlias("level")
//			.setUsage("log level info - Sets or displays the log level.");
//		addCommand("show")
//			.setUsage("Displays additional information.");
//		addCommand("version")
//			.setUsage("Displays the current running version, and the latest available (if enabled)");
//		case "say":
//			String msg = commandEvent.raw;
//			if(msg.contains(" ")) msg = msg.substring(msg.indexOf(" ") + 1);
//			return _say(msg);
		// say
		case "say":
		case "wall":
		case "broadcast":
			_say(event);
			break;
//		addCommand("list")
//		// input / output
//		// tools
//		addCommand("ping")
//			.setUsage("");
//		addCommand("threads")
//			.setUsage("Displays number of loaded threads, and optional details.");
//		setAllPriority(EventPriority.LOWEST);
//		addCommand("route")
//			.addAlias("send")
//			.setUsage("Sends an event to the meta data router, which passes to plugins.\nUsage: route <ReceiverName> [MetaType] <Value>");
		case "test":
			_test(event);
			break;
		}
	}


	private static void _test(final CommandEvent event) {
		event.setHandled();
//final long tim = System.nanoTime();
//xThreadPool.get().runNow(
//new Runnable() {
//@Override
//public void run() {
//final long ln = (System.nanoTime() - tim) / 1000;
//final double ns = (double) ln;
//System.out.println(Double.toString( ns / 1000 )+"ms");
//}
//}
//);
	}


	// shutdown command
	private static void _shutdown(final CommandEvent event) {
		event.setHandled();
		xApp.get().shutdown();
	}
	// kill command
	private static void _kill(final CommandEvent event) {
		event.setHandled();
		System.out.println("Killed by command..");
		System.out.println();
		System.exit(0);
	}


	// clear command
	private static void _clear(final CommandEvent event) {
		event.setHandled();
		xLog.getConsole().clear();
	}


	// trigger event manually
	private void _set(final CommandEvent event) {
String str = "";
int i = 0;
for(final String a : event.arg)
	str += Integer.toString(++i)+")"+a+" ";

System.out.println("ROUTE: "+str);
event.setHandled();

//		// route an action
//		public void processMeta(final String destStr, final Meta value) {
//			getThreadPool().runLater(new Runnable() {
//				private volatile String destStr2 = null;;
//				private volatile Meta value2 = null;
//				protected Runnable init(final String destStr1, final Meta value1) {
//					this.destStr2 = destStr1;
//					this.value2 = value1;
//					return this;
//				}
//				@Override
//				public void run() {
//					ServerListeners.get()
//						.router().trigger(
//							new MetaEvent(
//								this.destStr2,
//								this.value2
//							)
//						);
//				}
//			}.init(destStr, value));
//		}
	}


	private static void _log(final CommandEvent event) {
		if(event.arg(1) == null) {
			_level(event);
			return;
		}
//		event.setHandled();
//TODO:
	}
	private static void _level(final CommandEvent event) {
		event.setHandled();
		final xLog log = xLog.getRoot();
		log.publish("Current log level: "+log.getLevel().toString());
	}


	// say command
	private static void _say(final CommandEvent event) {
		event.setHandled();
		final StringBuilder msg = new StringBuilder();
		msg.append(" (console) ");
		msg.append(event.commandStr.substring(event.arg(0).length() + 1));
		xLog.getRoot().publish(msg.toString());
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
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
