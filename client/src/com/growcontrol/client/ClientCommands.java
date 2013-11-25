package com.growcontrol.gcClient;

import com.growcontrol.gcCommon.pxnCommand.pxnCommandEvent;
import com.growcontrol.gcCommon.pxnCommand.pxnCommandsHolder;


public final class ClientCommands extends pxnCommandsHolder {
	private ClientCommands() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static volatile ClientCommands instance = null;
	private static final Object lock = new Object();


	public static ClientCommands get() {
		if(instance == null) {
			synchronized(lock) {
				if(instance == null)
					instance = new ClientCommands();
			}
		}
		return instance;
	}
	@Override
	protected void initCommands() {
//TODO:
	}


	@Override
	protected boolean onCommand(pxnCommandEvent command) {
		return false;
	}


}
