package com.growcontrol.server;

import java.util.concurrent.atomic.AtomicReference;

import com.growcontrol.server.app.steps.gcServer_Logo;
import com.growcontrol.server.commands.gcCommands_Common;
import com.growcontrol.server.commands.gcCommands_Server;
import com.growcontrol.server.configs.gcServerConfig;
import com.growcontrol.server.scripting.gcScriptManager;
import com.poixson.app.xApp;
import com.poixson.app.xAppMoreSteps;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepType;
import com.poixson.app.commands.xCommandProcessor;
import com.poixson.app.commands.xCommands_ExitKill;
import com.poixson.app.steps.xAppSteps_UserNotRoot;
import com.poixson.logger.xConsole;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.config.xConfigLoader;


/*
 * Startup sequence
 *   5 | prevent root   | xAppSteps_UserNotRoot
 *  10 | startup time   | xApp
 *  20 | display logo   | gcServer_Logo
 *  50 | load configs
 *  90 | commands
 *  95 | command prompt | xAppSteps_ConsolePrompt
 * 450 | start scripts
 *
 * Shutdown sequence
 * 450 | stop scripts
 *  95 | command prompt    | xAppSteps_ConsolePrompt
 *  60 | display uptime    | xApp
 *  50 | stop thread pools | xApp
 *  10 | garbage collect   | xApp
 *   1 | exit              | xApp
 */
public class gcServer extends xApp {

	protected final AtomicReference<xCommandProcessor> commands = new AtomicReference<xCommandProcessor>(null);

	protected final AtomicReference<gcServerConfig>  config  = new AtomicReference<gcServerConfig>(null);
	protected final AtomicReference<gcScriptManager> scripts = new AtomicReference<gcScriptManager>(null);



	public gcServer(final String[] args) {
		super(args);
		// commands
		{
			final xCommandProcessor commands = new xCommandProcessor(this);
			this.commands.set(commands);
		}
	}



	@xAppMoreSteps
	public Object[] steps(final xAppStepType type) {
		return new Object[] {
			new xAppSteps_UserNotRoot(), //  5
			new gcServer_Logo(this),     // 20
			this.log().getConsole()      // 95
		};
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// load configs
	@xAppStep(type=xAppStepType.STARTUP, title="Configs", step=50)
	public void __STARTUP_load_configs() {
//TODO: find config
		final String file = "gc.conf";
		final gcServerConfig config = xConfigLoader.FromFile(file, gcServerConfig.class);
		this.config.set(config);
		try {
//			xVars.setJLineHistorySize(200);
//			xVars.setJLineHistoryFile(
//				FileUtils.MergePaths(
//					FileUtils.cwd(),
//					"history.txt"
//				)
//			);
		} catch (Exception e) {
			this.fail(e);
		}
//		final gcServerConfig config = gcServerVars.getConfig();
//		{
//			// debug mode
//			final Boolean debug = config.getDebug();
//			if (debug != null && debug.booleanValue()) {
//				xVars.debug(debug.booleanValue());
//			}
//		}
//		{
//			// log level
//			if (!xVars.debug()) {
//				final xLevel lvl = config.getLogLevel();
//				if (lvl != null) {
//					xLog.getRoot()
//						.setLevel(lvl);
//				}
//			}
//		}
//		// load zones
//		synchronized(zones) {
//			config.PopulateZones(zones);
//			log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//		}
	}



	@xAppStep(type=xAppStepType.STARTUP, title="Commands", step=90)
	public void __START__commands() {
		final xCommandProcessor proc = this.getCommandProcessor();
		proc.unregisterAll();
		proc.register(
			this.getCommands()
		);
	}
	@Override
	public Object[] getCommands() {
		return new Object[] {
			new xCommands_ExitKill(this),
			new gcCommands_Common(this),
			new gcCommands_Server(this)
		};
	}

	@xAppStep(type=xAppStepType.STARTUP, title="ConsolePrompt", step=95)
	public void __START__command_prompt() {
		// start reading console input
		final xConsole console = xLogRoot.Get().getConsole();
		if (console != null)
			console.start();
	}



	@xAppStep(type=xAppStepType.STARTUP, title="Scripts", step=450)
	public void __START__scripts() {
		try {
			final gcScriptManager manager = new gcScriptManager(this);
			final gcScriptManager previous = this.scripts.getAndSet(manager);
			if (previous != null)
				previous.stop();
//TODO: load scripts
			manager.start();
		} catch (Exception e) {
			this.fail(e);
		}
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	@xAppStep(type=xAppStepType.SHUTDOWN, title="Scripts", step=450)
	public void __STOP__scripts() {
		try {
			final gcScriptManager manager = this.scripts.getAndSet(null);
			if (manager != null)
				manager.stop();
//TODO: wait for scripts to finish
		} catch (Exception e) {
			this.fail(e);
		}
	}



	// stop console input
	@xAppStep(type=xAppStepType.SHUTDOWN, title="ConsolePrompt", step=95)
	public void __STOP__console() {
		// stop reading console input
		final xConsole console = xLogRoot.Get().getConsole();
		if (console != null)
			console.stop();
	}



	// -------------------------------------------------------------------------------



	@Override
	public xCommandProcessor getCommandProcessor() {
		return this.commands.get();
	}



}
