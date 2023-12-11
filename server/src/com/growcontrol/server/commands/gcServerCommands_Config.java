package com.growcontrol.server.commands;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xApp;
import com.poixson.app.commands.xCommand;
import com.poixson.app.commands.xCommandDAO;
import com.poixson.app.commands.xCommandEvent;
import com.poixson.app.commands.xCommandProcessor;


public class gcCommands_Server_Config {

	protected final xApp app;
	protected final AtomicReference<xCommandDAO[]> parent = new AtomicReference<xCommandDAO[]>(null);



	public gcCommands_Server_Config(final xApp app) {
		this.app = app;
	}



	public void setParent(final xCommandDAO[] commands) {
		this.parent.set(commands);
	}



	@xCommand(Name="exit")
	public void cmd_exit(final xCommandEvent event) {
//		if(event.isHelp()) {
//			this.exit_help(event);
//			return;
//		}
		final xCommandProcessor proc = this.app.getCommandProcessor();
		proc.replace(this.parent.getAndSet(null));
		event.setHandled();
	}



}
