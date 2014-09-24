package com.growcontrol.client.commands;

import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public final class gcClientCommands implements xCommandListener {



	// client commands
	@Override
	@xEvent(
		priority=Priority.NORMAL,
		threaded=false,
		filterHandled=true,
		filterCancelled=true)
	public void onCommand(final xCommandEvent event) {
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
		// say a message
		case "say":
		case "wall":
		case "broadcast":
			this._say(event);
			break;
		case "test":
			this._test(event);
			break;
		}
	}



	private void _test(final xCommandEvent event) {
//		handled(event);
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
//		handled(event);
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
//			this.publish();
//			this.publish("");
//			this.publish();
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
//		final xLog logroot = xLog.getRoot();
//		this.publish("Current log level: "+logroot.getLevel().toString());
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
