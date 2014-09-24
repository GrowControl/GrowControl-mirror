package com.growcontrol.gccommon.commands;

import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public abstract class gcCommands implements xCommandListener {



	// commands
	@Override
	public void onCommand(final xCommandEvent event) {
		switch(event.arg(0)) {
		case "test":
			this._test(event);
			break;
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
		}
	}



	protected void _test(final xCommandEvent event) {}



	protected void _help(final xCommandEvent event) {
//		handled(event);
//		// help
//		this.publish("[ Basic Commands ]");
//		this.publish("version - Displays the current running version.");
//		this.publish("start - Starts the scheduler.");
//		this.publish("stop  - Stops the scheduler.");
//		this.publish("pause - Pauses the scheduler.");
//		this.publish("clear - Clears the screen.");
//		this.publish("say - Broadcasts a message.");
//		this.publish("list plugins - Lists the loaded plugins.");
//		this.publish("list devices - Lists the loaded devices.");
//		this.publish("list outputs - Lists the available outputs.");
//		this.publish("list inputs  - Lists the available inputs.");
//		this.publish("[ Tools ]");
//		this.publish("ping - ");
//		this.publish("threads - ");
	}



	// shutdown command
	protected void _shutdown(final xCommandEvent event) {
		if(event.isHelp()) {
			this._shutdown_help(event);
			return;
		}
		handled(event);
		xApp.get().Stop();
	}
	protected void _shutdown_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Properly shuts down and stops the server.");
		this.publish();
	}



	// kill command
	protected void _kill(final xCommandEvent event) {
		if(event.isHelp()) {
			this._kill_help(event);
			return;
		}
		System.out.println();
		System.out.println("Killed by command..");
		System.out.println();
		System.exit(0);
	}
	protected void _kill_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Emergency shutdown. No saving or power-downs. Don't use this unless you need to.");
		this.publish();
	}



	// clear command
	protected void _clear(final xCommandEvent event) {
		if(event.isHelp()) {
			this._clear_help(event);
			return;
		}
		handled(event);
		xLog.getConsole().clear();
	}
	protected void _clear_help(final xCommandEvent event) {
		handled(event);
		this.publish();
		this.publish("Clears the console screen.");
		this.publish();
	}



//	private void _log(final xCommandEvent event) {
//	if(event.arg(1) == null) {
//		this._level(event);
//		return;
//	}
//	if(event.isHelp()) {
//		handled(event);
//		log().publish();
//		log().publish("");
//		log().publish();
//		return;
//	}
//	handled(event);
//TODO:
//}
//private void _level(final xCommandEvent event) {
//	if(event.isHelp()) {
//		handled(event);
//		this.publish();
//		this.publish("level         - Display the current log level.");
//		this.publish("level <level> - Sets the root log level.");
//		this.publish();
//		for(final xLevel level : xLevel.getKnownLevels())
//			this.log().publish(level, "This is "+level.toString()+" level.");
//		this.publish();
//		return;
//	}
//	handled(event);
//	final xLog log = xLog.getRoot();
//	this.publish("Current log level: "+log.getLevel().toString());
//}



	// set event handled
	public static void handled(final xCommandEvent event) {
		if(event == null) throw new NullPointerException();
		event.setHandled();
	}



	// logger
	private volatile xLog _log = null;
	private final Object logLock = new Object();
	public xLog log() {
		if(this._log == null) {
			synchronized(this.logLock) {
				if(this._log == null)
					this._log = xApp.log();
			}
		}
		return this._log;
	}
	public void publish(final String msg) {
		this.log().publish(msg);
	}
	public void publish() {
		this.log().publish();
	}



}
