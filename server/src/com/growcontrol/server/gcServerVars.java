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
		this.system   = new xHandler();
		this.commands = new xHandler();
		this.plugins  = new xHandler();
	}


	// system event handler
	public xHandler system() {
		return this.system;
	}
	// command event handler
	public xHandler commands() {
		return this.commands;
	}
	// plugin event handler
	public xHandler plugins() {
		return this.plugins;
	}


}
