package com.growcontrol.server;

import com.poixson.commonjava.EventListener.xHandler;


public final class ServerListeners {
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


	// single instance of holder class
	private static volatile ServerListeners instance = null;
	private static final Object lock = new Object();
	public static ServerListeners get() {
		if(instance == null) {
			synchronized(lock) {
				if(instance == null)
					instance = new ServerListeners();
			}
		}
		return instance;
	}


	private final xHandler system;
	private final xHandler commands;
	private final xHandler plugins;


	// new instance of holder
	private ServerListeners() {
		system   = new xHandler();
		commands = new xHandler();
		plugins  = new xHandler();
	}


	// system event handler
	public xHandler system() {
		return system;
	}
	// command event handler
	public xHandler commands() {
		return commands;
	}
	// plugin event handler
	public xHandler plugins() {
		return plugins;
	}


}
