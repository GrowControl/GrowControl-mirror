package com.growcontrol.gcClient;

import com.growcontrol.gcCommon.pxnCommand.pxnCommandListenerGroup;
import com.growcontrol.gcCommon.pxnCommand.pxnCommandsHolder;


public class ClientListeners {
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// single instance
	private static volatile ClientListeners listeners = null;
	private static final Object lock = new Object();


	public static ClientListeners get() {
		if(listeners == null) {
			synchronized(lock) {
				if(listeners == null)
					listeners = new ClientListeners();
			}
		}
		return listeners;
	}
	// init listeners
	private ClientListeners() {
		// client commands listener
		register(ClientCommands.get());
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
