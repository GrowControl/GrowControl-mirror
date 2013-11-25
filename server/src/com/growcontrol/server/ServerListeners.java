package com.growcontrol.gcServer;

import com.growcontrol.gcCommon.pxnCommand.pxnCommandListenerGroup;
import com.growcontrol.gcCommon.pxnCommand.pxnCommandsHolder;


public final class ServerListeners {
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// single instance
	private static volatile ServerListeners listeners = null;
	private static final Object lock = new Object();


	public static ServerListeners get() {
		if(listeners == null) {
			synchronized(lock) {
				if(listeners == null)
					listeners = new ServerListeners();
			}
		}
		return listeners;
	}
	// init listeners
	private ServerListeners() {
		// server commands listener
		register(ServerCommands.get());
	}


	// commands holder
	public void register(pxnCommandsHolder listener) {
		pxnCommandListenerGroup.get().register(listener);
	}
	// trigger command
	public boolean triggerCommand(String line) {
		return pxnCommandListenerGroup.get().triggerCommandEvent(line);
	}


}
