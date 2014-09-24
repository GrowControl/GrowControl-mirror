package com.growcontrol.server.commands;

import com.growcontrol.gccommon.commands.gcCommands;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public final class gcServerCommands extends gcCommands {



	// server commands
	@Override
	@xEvent(
		priority=Priority.NORMAL,
		threaded=false,
		filterHandled=true,
		filterCancelled=true)
	public void onCommand(final xCommandEvent event) {
		super.onCommand(event);
		switch(event.arg(0)) {
		// help
		case "help":
		case "h":
		case "?":
			this._help(event);
			break;
		// shutdown server
		case "shutdown":
		case "stop":
		case "exit":
		case "quit":
			this._shutdown(event);
			break;
		// kill program
		case "kill":
		case "end":
			this._kill(event);
			break;
		// clear terminal
		case "clear":
		case "cls":
			this._clear(event);
			break;
		// start/resume schedulers
		case "start":
		case "resume":
			this._start(event);
			break;
		// pause/resume schedulers
		case "pause":
		case "p":
			this._pause(event);
			break;
		// manually trigger an event
		case "set":
			this._set(event);
			break;
//		addCommand("get")
//		addCommand("watch")
//		case "log":
//			this._log(event);
//			break;
//		case "level":
//			this._level(event);
//			break;
//		addCommand("show")
//			.setUsage("Displays additional information.");
//		addCommand("version")
//			.setUsage("Displays the current running version, and the latest available (if enabled)");
//		case "say":
//			String msg = commandEvent.raw;
//			if(msg.contains(" ")) msg = msg.substring(msg.indexOf(" ") + 1);
//			return _say(msg);
		// say a message
		case "say":
		case "wall":
		case "broadcast":
			this._say(event);
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
			this._test(event);
			break;
		}
	}



	private void _test(final xCommandEvent event) {
//		handled(event);
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



	private void _help(final xCommandEvent event) {
//		handled(event);
	}



	// shutdown command
	private void _shutdown(final xCommandEvent event) {
		if(event.isHelp()) {
			this._shutdown_help(event);
			return;
		}
		handled(event);
		xApp.get().Stop();
	}
	private void _shutdown_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Properly shuts down and stops the server.");
		this.publish();
	}



	// kill command
	private void _kill(final xCommandEvent event) {
		if(event.isHelp()) {
			this._kill_help(event);
			return;
		}
		System.out.println();
		System.out.println("Killed by command..");
		System.out.println();
		System.exit(0);
	}
	private void _kill_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Emergency shutdown. No saving or power-downs. Don't use this unless you need to.");
		this.publish();
	}



	// clear command
	private void _clear(final xCommandEvent event) {
		if(event.isHelp()) {
			this._clear_help(event);
			return;
		}
		handled(event);
		xLog.getConsole().clear();
	}
	private void _clear_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Clears the console screen.");
		this.publish();
	}



	// start/resume schedulers
	private void _start(final xCommandEvent event) {
		if(event.isHelp()) {
			this._start_help(event);
			return;
		}
//		handled(event);
	}
	private void _start_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Starts or resumes server tasks and schedulers.");
		this.publish();
	}



	// pause schedulers
	private void _pause(final xCommandEvent event) {
		if(event.isHelp()) {
			this._pause_help(event);
			return;
		}
//		handled(event);
	}
	private void _pause_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Pauses or resumes the scheduler and some plugin tasks.");
		this.publish("Optional argument: [on/off/true/false/1/0]");
		this.publish();
	}



	// trigger event manually
	private void _set(final xCommandEvent event) {
		if(event.isHelp()) {
			this._set_help(event);
			return;
		}
		handled(event);
String str = "";
int i = 0;
for(final String a : event.args)
	str += Integer.toString(++i)+")"+a+" ";

this.publish("ROUTE: "+str);

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
	private void _set_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Manually triggers an event.");
		this.publish();
	}



//	private void _log(final xCommandEvent event) {
//		if(event.arg(1) == null) {
//			this._level(event);
//			return;
//		}
//		if(event.isHelp()) {
//			handled(event);
//			log().publish();
//			log().publish("");
//			log().publish();
//			return;
//		}
//		handled(event);
//TODO:
//	}
//	private void _level(final xCommandEvent event) {
//		if(event.isHelp()) {
//			handled(event);
//			this.publish();
//			this.publish("level         - Display the current log level.");
//			this.publish("level <level> - Sets the root log level.");
//			this.publish();
//			for(final xLevel level : xLevel.getKnownLevels())
//				this.log().publish(level, "This is "+level.toString()+" level.");
//			this.publish();
//			return;
//		}
//		handled(event);
//		final xLog log = xLog.getRoot();
//		this.publish("Current log level: "+log.getLevel().toString());
//	}



	// say command
	private void _say(final xCommandEvent event) {
		if(event.isHelp()) {
			this._say_help(event);
			return;
		}
		handled(event);
		final StringBuilder msg = new StringBuilder();
		msg.append(" (console) ");
		msg.append(event.commandStr.substring(event.arg(0).length() + 1));
		this.publish(msg.toString());
	}
	private void _say_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Broadcasts a message.");
		this.publish();
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
