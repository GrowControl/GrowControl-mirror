package com.growcontrol.server.commands;

import com.poixson.app.xApp;
import com.poixson.app.commands.xCommand;
import com.poixson.app.commands.xCommandEvent;
import com.poixson.app.commands.xCommandProcessor;


public class gcServerCommands {

	protected final xApp app;



	public gcServerCommands(final gcServer server) {
		this.app = app;
	}



	@xCommand(Name="config", Aliases="cnf,conf,configure")
	public void cmd_config(final xCommandEvent event) {
//		if (event.isHelp()) {
//			this.config_help(event);
//			return;
//		}
		final xCommandProcessor proc = this.app.getCommandProcessor();
		proc.unregisterAll();
		proc.register(
			new gcCommands_Server_Config(this.app)
		);
		event.setHandled();
	}



}
