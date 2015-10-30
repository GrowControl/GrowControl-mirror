package com.growcontrol.server.commands;

import com.growcontrol.server.gcServerDefines;
import com.growcontrol.server.gcServerVars;
import com.growcontrol.server.configs.gcServerConfig;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.commands.xCommandEvent;
import com.poixson.commonjava.xLogger.commands.xCommandListener;


public class gcServerCommands_config implements xCommandListener {
	private static final String LOG_NAME = gcServerDefines.LOG_NAME;
	private static final String LISTENER_NAME = gcServerCommands.LISTENER_NAME;

	private final gcServerCommands parent;



	protected gcServerCommands_config(final gcServerCommands parent) {
		this.parent = parent;
		xLog.getConsole().setPrompt(" config> ");
	}
	@Override
	public String getName() {
		return LISTENER_NAME;
	}
	@Override
	public String toString() {
		return this.getName();
	}



	@Override
	public void onCommand(xCommandEvent event) {
		switch(event.getArg(0)) {
		case "exit": {
			this._exit(event);
			break;
		}
		case "list":
			this._list(event);
			break;
		}
	}



	protected void _exit(final xCommandEvent event) {
		if(event.isHelp()) {
			this._list_help(event);
			return;
		}
		event.setHandled();
		this.parent.inconfig = null;
//		xLog.getConsole().setPrompt(" "+jlineConsole.DEFAULT_PROMPT);
	}
	protected void _exit_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Exit config mode and return to the main command prompt.");
		this.publish();
	}



	protected void _list(final xCommandEvent event) {
		if(event.isHelp()) {
			this._list_help(event);
			return;
		}
		event.setHandled();
		final gcServerConfig config = gcServerVars.getConfig();
//		final String[] zones = config.getZones().toArray(new String[0]);
		this.publish();
		this.log().title("Server Config");
		this.publish(" Config Version: "+config.getVersion());
		this.publish(" Log Level:      "+config.getLogLevel().toString());
		this.publish(" Debug:          "+Boolean.toString(config.getDebug()));
		this.publish(" Tick Interval:  "+config.getTickInterval().toString());
//		this.publish(" Listen Port:    "+Integer.toString(config.getListenPort()));
		this.publish(" Logic Threads:  "+Integer.toString(config.getLogicThreads()));
//		this.publish(" Zones:  [ "+Integer.toString(zones.length)+" ]");
//		for(final String zone : zones)
//			this.publish("   - "+zone);
		this.publish();
	}
	protected void _list_help(final xCommandEvent event) {
		event.setHandled();
		this.publish();
		this.publish("Lists current config values.");
		this.publish();
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot(LOG_NAME);
		return this._log;
	}
	public void publish(final String msg) {
		this.log().publish(msg);
	}
	public void publish() {
		this.log().publish();
	}



}
