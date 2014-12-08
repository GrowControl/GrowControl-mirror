package com.growcontrol.server;

import com.growcontrol.gccommon.meta.MetaRouter;
import com.poixson.commonjava.EventListener.xHandler;


public class gcServerVars {



	// single instance
	protected static volatile gcServerVars instance = null;
	protected static final Object instanceLock = new Object();

	// get instance
	public static gcServerVars get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcServerVars();
			}
		}
		return instance;
	}
	// new instance
	protected gcServerVars() {
		this.system   = new xHandler();
		this.commands = new xHandler();
		this.plugins  = new xHandler();
		this.router   = MetaRouter.get();
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



	// meta router handler
	private final MetaRouter router;
	public MetaRouter router() {
		return this.router;
	}



}
