package com.growcontrol.client;

import com.poixson.commonapp.app.xApp;
import com.poixson.commonapp.listeners.CommandEvent;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.xLogger.xLog;


public final class gcClientCommands implements xListener {


	@xEvent(
		priority=Priority.NORMAL,
		filtered=false,
		threaded=false,
		ignoreHandled=true,
		ignoreCancelled=true)
	public void onCommand(final CommandEvent event) {
		switch(event.arg(0)) {
		// shutdown
		case "shutdown":
		case "stop":
		case "exit":
		case "quit":
			_shutdown(event);
			break;
		// kill
		case "kill":
		case "end":
			_kill(event);
			System.exit(0);
			break;
		// clear
		case "clear":
		case "cls":
			_clear(event);
			break;
		case "set":
			_set(event);
			break;
		case "test":
			_test(event);
			break;
		}
	}


	private static void _test(final CommandEvent event) {
		event.setHandled();
	}


	// shutdown command
	private static void _shutdown(final CommandEvent event) {
		event.setHandled();
		xApp.get().shutdown();
	}
	// kill command
	private static void _kill(final CommandEvent event) {
		event.setHandled();
		System.out.println("Killed by command.");
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
		event.setHandled();
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


}
