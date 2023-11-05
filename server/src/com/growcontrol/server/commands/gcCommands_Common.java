/*
package com.growcontrol.server.commands;

import java.lang.ref.SoftReference;

import com.poixson.app.xApp;
import com.poixson.utils.xEvents.xEvent;
import com.poixson.utils.xLogger.xCommandEvent;
import com.poixson.utils.xLogger.xCommandListener;
import com.poixson.utils.xLogger.xLog;


public class gcCommandsCommon implements xCommandListener {
	private static final String LISTENER_NAME = "CommonCommands";



	// common commands
	@Override
	@xEvent(
			priority=ListenerPriority.LOW,
//			async=false,
			filterHandled=true,
			filterCancelled=true)
	public void onCommand(final xCommandEvent event) {
		switch(event.getArg(0)) {
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
	@Override
	public String getName() {
		return LISTENER_NAME;
	}
	@Override
	public String toString() {
		return this.getName();
	}



	protected void _test(final xCommandEvent event) {}



	protected void _help(final xCommandEvent event) {
//		event.setHandled();
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
		event.setHandled();
		xApp.get()
			.stop();
	}
	protected void _shutdown_help(final xCommandEvent event) {
		event.setHandled();
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
		event.setHandled();
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
		event.setHandled();
		xLog.getConsole().clear();
	}
	protected void _clear_help(final xCommandEvent event) {
		event.setHandled();
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
//		event.setHandled();
//		log().publish();
//		log().publish("");
//		log().publish();
//		return;
//	}
//	event.setHandled();
//TODO:
//}
//private void _level(final xCommandEvent event) {
//	if(event.isHelp()) {
//		event.setHandled();
//		this.publish();
//		this.publish("level         - Display the current log level.");
//		this.publish("level <level> - Sets the root log level.");
//		this.publish();
//		for(final xLevel level : xLevel.getKnownLevels())
//			this.log().publish(level, "This is "+level.toString()+" level.");
//		this.publish();
//		return;
//	}
//	event.setHandled();
//	final xLog log = xLog.getRoot();
//	this.publish("Current log level: "+log.getLevel().toString());
//}



	// logger
	private volatile SoftReference<xLog> _log = null;
	public xLog log() {
		if (this._log != null) {
			final xLog log = this._log.get();
			if (log != null) {
				return log;
			}
		}
		final xLog log = xLog.getRoot();
		this._log = new SoftReference<xLog>(log);
		return log;
	}
	public void publish(final String msg) {
		this.log()
			.publish(msg);
	}
	public void publish() {
		this.log()
			.publish();
	}



}
*/
