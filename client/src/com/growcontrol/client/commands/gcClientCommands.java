/*
package com.growcontrol.client.commands;

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
		// say a message
		case "say":
		case "wall":
		case "broadcast":
			this._say(event);
			break;
//		case "io":
//			break;
//		// get current value
//		case "get":
//			this._get(event);
//			break;
//		// manually trigger an event
//		case "set":
//			this._set(event);
//			break;
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
		}
	}



//	@Override
//	private void _test(final xCommandEvent event) {
//		event.setHandled();
//	}



	// say command
	private void _say(final xCommandEvent event) {
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
	private void _say_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Broadcasts a message.");
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



//	// trigger event manually
//	private void _set(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._set_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	private void _set_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Manually triggers an event.");
//		this.publish();
//	}



//	// start/resume schedulers
//	private void _start(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._start_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	private void _start_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Starts or resumes server tasks and schedulers.");
//		this.publish();
//	}



//	// pause schedulers
//	private void _pause(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this._pause_help(event);
//			return;
//		}
//		event.setHandled();
//	}
//	private void _pause_help(final xCommandEvent event) {
//		event.setHandled();
//		this.publish();
//		this.publish("Pauses or resumes the scheduler and some plugin tasks.");
//		this.publish("Optional argument: [on/off/true/false/1/0]");
//		this.publish();
//	}



//	private void _log(final xCommandEvent event) {
//		if(event.arg(1) == null) {
//			this._level(event);
//			return;
//		}
//		if(event.isHelp()) {
//			event.setHandled();
//			this.publish();
//			this.publish("");
//			this.publish();
//			return;
//		}
//		event.setHandled();
//TODO:
//	}
//	private void _level(final xCommandEvent event) {
//		if(event.isHelp()) {
//			event.setHandled();
//			this.publish();
//			this.publish("level         - Display the current log level.");
//			this.publish("level <level> - Sets the root log level.");
//			this.publish();
//			for(final xLevel level : xLevel.getKnownLevels())
//				this.log().publish(level, "This is "+level.toString()+" level.");
//			this.publish();
//			return;
//		}
//		event.setHandled();
//		final xLog logroot = xLog.getRoot();
//		this.publish("Current log level: "+logroot.getLevel().toString());
//	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot("GUI");
		return this._log;
	}
	public void publish(final String msg) {
		this.log().publish(msg);
	}
	public void publish() {
		this.log().publish();
	}



}
*/