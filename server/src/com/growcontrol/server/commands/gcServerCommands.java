package com.growcontrol.server.commands;

import com.growcontrol.common.meta.Meta;
import com.growcontrol.common.meta.MetaAddress;
import com.growcontrol.common.meta.MetaRouter;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public final class gcServerCommands implements xCommandListener {

	protected volatile gcServerCommands_config inconfig = null;



	// server commands
	@Override
	@xEvent(
			priority=Priority.NORMAL,
			threaded=false,
			filterHandled=true,
			filterCancelled=true)
	public void onCommand(final xCommandEvent event) {
		if(this.inconfig != null) {
			this.inconfig.onCommand(event);
			return;
		}
		switch(event.arg(0)) {
		// config mode
		case "config":
			if(event.isHelp()) {
				this._config_help(event);
				return;
			}
			this.inconfig = new gcServerCommands_config(this);
			event.setHandled();
			break;
		// say a message
		case "say":
		case "wall":
		case "broadcast":
			this._say(event);
			break;
		case "uptime":
			this._uptime(event);
			break;
		case "memory":
		case "mem":
			this._mem(event);
			break;
//		// get current value
//		case "get":
//			this._get(event);
//			break;
		// manually trigger an event
		case "set":
			this._set(event);
			break;
		case "list":
			this._list(event);
			break;
//		// start/resume schedulers
//		case "start":
//		case "resume":
//			this._start(event);
//			break;
//		// pause/resume schedulers
//		case "pause":
//		case "p":
//			this._pause(event);
//			break;

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
//		// input / output
//		// tools
//		addCommand("ping")
//			.setUsage("");
//		addCommand("threads")
//			.setUsage("Displays number of loaded threads, and optional details.");
//		setAllPriority(EventPriority.LOWEST);

		}
	}



//	@Override
//	protected void _test(final xCommandEvent event) {
//		event.setHandled();
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
//	}



	protected void _config_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Enters config command mode. Use the exit command to return.");
		this.publish();
	}



	// say command
	protected void _say(final xCommandEvent event) {
		if(event.isHelp()) {
			this._say_help(event);
			return;
		}
		event.setHandled();
		final StringBuilder msg = new StringBuilder();
		msg.append(" (console) ");
		msg.append(event.commandStr.substring(event.arg(0).length() + 1));
		this.publish(msg.toString());
	}
	protected void _say_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Broadcasts a message.");
		this.publish();
	}



	protected void _uptime(final xCommandEvent event) {
		if(event.isHelp()) {
			this._uptime_help(event);
			return;
		}
		event.setHandled();
		this.publish();
		this.publish(xApp.get().getUptimeString());
		this.publish();
	}
	protected void _uptime_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Displays current uptime for the server.");
		this.publish();
	}



	protected void _mem(final xCommandEvent event) {
		if(event.isHelp()) {
			this._mem_help(event);
			return;
		}
		event.setHandled();
		this.publish();
		if("gc".equals(event.arg(1))) {
			this.publish("Performing garbage collection..");
			System.gc();
		}
		utils.MemoryStats();
		this.publish();
	}
	protected void _mem_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Displays current memory stats.");
		this.publish("  <gc> optional argument to perform garbage collection");
		this.publish();
	}



//	// get command
//	protected void _get(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._get_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	protected void _get_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Gets a current value.");
//		this.publish();
//	}



	// set command (trigger event manually)
	protected void _set(final xCommandEvent event) {
		if(event.isHelp()) {
			this._set_help(event);
			return;
		}
		event.setHandled();
		final MetaAddress addr = MetaAddress.get(event.arg(1));
		final Meta meta = Meta.get(event.arg(2));
		this.log().stats("Setting [ "+addr.toString()+" ] to [ "+meta.toString()+" ]");
		MetaRouter.get().route(addr, meta);
	}
	protected void _set_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Manually triggers an event.");
		this.publish("  set <address> <value>");
		this.publish();
	}



	protected void _list(final xCommandEvent event) {
		if(event.isHelp()) {
			this._list_help(event);
			return;
		}
		event.setHandled();
		switch(event.arg(1)) {
		case "threads":
			this.publish("THREADS... (sorry, this is unfinished)");
			break;
		case "addresses":
			this.publish("Addresses");
			this.publish("=========");
			final MetaRouter router = MetaRouter.get();
			for(final MetaAddress addr : router.getKnown())
				this.publish("  "+addr.toString());
			this.publish();
			break;
		}
	}
	protected void _list_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("List something:");
		this.publish("  threads");
		this.publish("  addresses");
		this.publish();
	}






//	// start/resume schedulers
//	protected void _start(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._start_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	protected void _start_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Starts or resumes server tasks and schedulers.");
//		this.publish();
//	}



//	// pause schedulers
//	protected void _pause(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._pause_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	protected void _pause_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Pauses or resumes the scheduler and some plugin tasks.");
//		this.publish("Optional argument: [on/off/true/false/1/0]");
//		this.publish();
//	}



//		// list plugins/devices/inputs/outputs
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



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot("SERVER");
		return this._log;
	}
	public void publish(final String msg) {
		this.log().publish(msg);
	}
	public void publish() {
		this.log().publish();
	}



}
