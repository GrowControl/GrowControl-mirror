package com.growcontrol.server.commands;

import java.lang.ref.SoftReference;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.app.xApp;
import com.poixson.app.commands.xCommand;
import com.poixson.app.commands.xCommandEvent;
import com.poixson.logger.xLog;


public class gcServerCommands_Common {

	protected final xApp app;



	public gcServerCommands_Common(final xApp app) {
		this.app = app;
	}



//TODO: auto generate this
	// help
	protected void _help(final xCommandEvent event) {
		final xLog log = this.log();
		this.log().publish("[ Basic Commands ]");
		log.publish("list [plugins, devices, inputs, outputs]");
		log.publish("exit - Stops and closes the server");
//		log.publish("version - Displays the current running version.");
//		log.publish("start - Starts the scheduler.");
//		log.publish("stop  - Stops the scheduler.");
//		log.publish("pause - Pauses the scheduler.");
//		log.publish("clear - Clears the screen.");
//		log.publish("say - Broadcasts a message.");
//		log.publish("list plugins - Lists the loaded plugins.");
//		log.publish("list devices - Lists the loaded devices.");
//		log.publish("list outputs - Lists the available outputs.");
//		log.publish("list inputs  - Lists the available inputs.");
//		log.publish("[ Tools ]");
//		log.publish("ping - ");
//		log.publish("threads - ");
	}



	// exit
	@xCommand(Name="exit", Aliases="stop,quit,shutdown")
	public void cmd_exit(final xCommandEvent event) {
		if (event.isHelp()) this.exit_help(event);
		else                this.app.stop();
		event.setHandled();
	}

	public void exit_help(final xCommandEvent event) {
		this.publish();
		this.publish("Properly stops and closes the server.");
		this.publish();
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);
	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log == null) this._log.set(null);
				else             return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
//TODO
return xLog.Get();
//		return xLog.Get(this.type.toString());
	}

	public void publish(final String msg) {
		this.log()
			.publish(msg);
	}
	public void publish() {
		this.log()
			.publish();
	}



}
