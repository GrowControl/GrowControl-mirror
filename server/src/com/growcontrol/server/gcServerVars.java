package com.growcontrol.server;

import com.poixson.commonapp.xAppVars;
import com.poixson.commonjava.EventListener.xHandler;


public final class gcServerVars extends xAppVars {


	// singleton instance
	private static volatile gcServerVars instance = null;
	public static gcServerVars get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcServerVars();
			}
		}
		return instance;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


	// new instance of holder
	protected gcServerVars() {
		super();
		// clone vars
		if(instance != null) {
System.out.println("CLONING gcServerVars");
			this.system   = instance.system;
			this.commands = instance.commands;
			this.plugins  = instance.plugins;
			this.router   = instance.router;
		// new instance
		} else {
			this.system   = new xHandler();
			this.commands = new xHandler();
			this.plugins  = new xHandler();
			this.router   = new xHandler();
		}
	}


	// system event handler
	private final xHandler system;
	public xHandler system() {
		return this.system;
	}


	// command event handler
	private final xHandler commands;
	public xHandler commands() {
		return this.commands;
	}


	// plugin event handler
	private final xHandler plugins;
	public xHandler plugins() {
		return this.plugins;
	}


	// meta router handler
	private final xHandler router;
	public xHandler router() {
		return this.router;
	}


}
