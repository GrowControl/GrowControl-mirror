package com.growcontrol.client;

import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.Utils.Keeper;


public class gcClientVars {



	private static volatile boolean inited = false;
	public static void init() {
		if(!inited)
			Keeper.add(new gcClientVars());
	}
	private gcClientVars() {
	}



	// system event handler
	private static final xHandler system = new xHandler();
	public static xHandler system() {
		return system;
	}



	// command event handler
	private static final xHandler commands = new xHandler();
	public static xHandler commands() {
		return commands;
	}



	// plugin event handler
	private static final xHandler plugins = new xHandler();
	public static xHandler plugins() {
		return plugins;
	}



//	// meta router handler
//	private static final xHandler router;
//	public static xHandler router() {
//		return router;
//	}



}
