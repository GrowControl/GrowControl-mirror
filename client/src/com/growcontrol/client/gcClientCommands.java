package com.growcontrol.client;

import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.EventListener.xEvent;
import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public final class gcClientCommands implements xCommandListener {



	@Override
	@xEvent(
		priority=Priority.NORMAL,
		filtered=false,
		threaded=false,
		ignoreHandled=true,
		ignoreCancelled=true)
	public void onCommand(final xCommandEvent event) {
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



	private static void _test(final xCommandEvent event) {
		event.setHandled();
	}



	// shutdown command
	private static void _shutdown(final xCommandEvent event) {
		event.setHandled();
		xApp.get().Stop();
	}
	// kill command
	private static void _kill(final xCommandEvent event) {
		event.setHandled();
		System.out.println("Killed by command.");
		System.out.println();
		System.exit(0);
	}



	// clear command
	private static void _clear(final xCommandEvent event) {
		event.setHandled();
		xLog.getConsole().clear();
	}



	// trigger event manually
	private void _set(final xCommandEvent event) {
		event.setHandled();
	}



}
