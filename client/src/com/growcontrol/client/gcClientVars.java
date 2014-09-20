package com.growcontrol.client;

import com.poixson.commonjava.EventListener.xHandler;


public class gcClientVars {



	// singleton instance
	protected static volatile gcClientVars instance = null;
	protected static final Object instanceLock = new Object();

	// get instance
	public static gcClientVars get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcClientVars();
			}
		}
		return instance;
	}
	// new instance
	protected gcClientVars() {
		this.system   = new xHandler();
		this.commands = new xHandler();
		this.plugins  = new xHandler();
//		this.router   = new xHandler();
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
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



//	// meta router handler
//	private final xHandler router;
//	public xHandler router() {
//		return this.router;
//	}



}
