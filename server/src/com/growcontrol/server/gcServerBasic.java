package com.growcontrol.server;

import com.poixson.commonapp.app.xAppBasic;
import com.poixson.commonapp.app.annotations.xAppStep;
import com.poixson.commonapp.app.annotations.xAppStep.StepType;


public class gcServerBasic extends xAppBasic {

	private static volatile gcServerBasic instance = null;
	private static final Object instanceLock = new Object();



	public static gcServerBasic get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcServerBasic();
			}
		}
		return instance;
	}
	public static gcServerBasic peak() {
		return instance;
	}
	// new instance
	protected gcServerBasic() {
		super();
		gcServerVars.init();
		gcServer.DisplayLogo();
	}



	@Override
	public String getTitle() {
		return "gcServerInternal";
	}



	// ------------------------------------------------------------------------------- //
	// startup



	// load config
	@xAppStep(type=StepType.STARTUP, title="Config", priority=20)
	public void __STARTUP_config() {
		gcServer._LoadConfig();
	}



	// load plugins
	@xAppStep(type=StepType.STARTUP, title="LoadPlugins", priority=50)
	public void __STARTUP_load_plugins() {
		gcServer._LoadPlugins();
	}



	// enable plugins
	@xAppStep(type=StepType.STARTUP, title="EnablePlugins", priority=55)
	public void __STARTUP_enable_plugins() {
		gcServer._EnablePlugins();
	}



	// tick scheduler
	@xAppStep(type=StepType.STARTUP, title="Ticker", priority=80)
	public void __STARTUP_ticker() {
		gcServer._StartTicker();
	}



	// scripts
	@xAppStep(type=StepType.STARTUP, title="Scripts", priority=95)
	public void __STARTUP_scripts() {
		gcServer._StartScripts();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown



	// scripts
	@xAppStep(type=StepType.SHUTDOWN, title="Scripts", priority=95)
	public void __SHUTDOWN_scripts() {
		gcServer._StopScripts();
	}



	// stop ticker
	@xAppStep(type=StepType.SHUTDOWN, title="Ticker", priority=80)
	public void __SHUTDOWN_ticker() {
		gcServer._StopTicker();
	}



	// disable plugins
	@xAppStep(type=StepType.SHUTDOWN, title="DisablePlugins", priority=55)
	public void __SHUTDOWN_disable_plugins() {
		gcServer._DisablePlugins();
	}



	// unload plugins
	@xAppStep(type=StepType.SHUTDOWN, title="UnloadPlugins", priority=50)
	public void __SHUTDOWN_unload_plugins() {
		gcServer._UnloadPlugins();
	}



	// ------------------------------------------------------------------------------- //



	@Override
	protected void displayLogo() {
		gcServer.DisplayLogo();
	}



}
