package com.growcontrol.server.commands;

import com.growcontrol.server.gcServer;
import com.growcontrol.server.configs.gcServerConfig;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.xCommandEvent;
import com.poixson.commonjava.xLogger.handlers.xCommandListener;


public class gcServerCommands_config implements xCommandListener {

	private final gcServerCommands parent;



	public static gcServerCommands_config get(final gcServerCommands parent, final xCommandEvent event) {
		handled(event);
		return new gcServerCommands_config(parent);
	}
	protected gcServerCommands_config(final gcServerCommands parent) {
		this.parent = parent;
		xLog.getConsole().setPrompt(" config> ");
	}



	@Override
	public void onCommand(xCommandEvent event) {
		switch(event.arg(0)) {
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
		xLog.getConsole().setPrompt(" #>");
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
		final gcServer server = gcServer.get();
		final gcServerConfig config = server.getConfig();
//		final String[] zones = config.getZones().toArray(new String[0]);
		this.publish();
		this.log().title("Server Config");
		this.publish(" Config Version: "+config.getVersion());
		this.publish(" Log Level:      "+config.getLogLevel().toString());
		this.publish(" Debug:          "+config.getDebug().toString());
		this.publish(" Tick Interval:  "+config.getTickInterval().toString());
		this.publish(" Listen Port:    "+Integer.toString(config.getListenPort()));
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
			this._log = xApp.log();
		return this._log;
	}
	public void publish(final String msg) {
		this.log().publish(msg);
	}
	public void publish() {
		this.log().publish();
	}



}
