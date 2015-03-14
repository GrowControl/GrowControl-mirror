package com.growcontrol.server;

import com.growcontrol.common.meta.MetaRouter;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.scheduler.ticker.xTicker;


public class gcServerVars {



	private static volatile boolean inited = false;
	public static void init() {
		if(!inited)
			Keeper.add(new gcServerVars());
	}
	private gcServerVars() {
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



	// tick events
	private static final xTicker ticker = new xTicker();
	public static xTicker ticker() {
		return ticker;
	}



	// meta router handler
	private static final MetaRouter router = MetaRouter.get();
	public static MetaRouter router() {
		return router;
	}



}
