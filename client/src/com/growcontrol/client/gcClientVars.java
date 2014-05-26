package com.growcontrol.client;

import com.poixson.commonapp.xAppVars;
import com.poixson.commonjava.EventListener.xHandler;


public class gcClientVars extends xAppVars {


	// singleton instance
	private static volatile gcClientVars instance = null;
	public static gcClientVars get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcClientVars();
			}
		}
		return instance;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


	// new instance of holder
	protected gcClientVars() {
		super();
		// clone event handlers
		if(instance != null) {
			this.system   = instance.system;
			this.commands = instance.commands;
			this.plugins  = instance.plugins;
//			this.router   = instance.router;
		// new instance
		} else {
			this.system   = new xHandler();
			this.commands = new xHandler();
			this.plugins  = new xHandler();
//			this.router   = new xHandler();
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


//	// meta router handler
//	private final xHandler router;
//	public xHandler router() {
//		return this.router;
//	}


}
