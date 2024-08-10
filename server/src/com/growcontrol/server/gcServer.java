package com.growcontrol.server;

import static com.poixson.utils.Utils.IsEmpty;

import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.SSLException;

import com.growcontrol.datum.DatumRouter;
import com.growcontrol.server.app.steps.gcServer_Logo;
import com.growcontrol.server.commands.gcServerCommands;
import com.growcontrol.server.commands.gcServerCommands_List;
import com.growcontrol.server.configs.gcServerConfig;
import com.growcontrol.server.events.gcTickHandler;
import com.growcontrol.server.plugins.gcServerPlugin;
import com.growcontrol.server.plugins.gcServerPluginFactory;
import com.growcontrol.server.plugins.gcServerPluginManager;
import com.growcontrol.server.scripting.gcServerScript;
import com.poixson.app.xApp;
import com.poixson.app.xAppMoreSteps;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStepDAO;
import com.poixson.app.xAppStepMultiFinishedException;
import com.poixson.app.xAppStepType;
import com.poixson.app.commands.xCommands_ExitKill;
import com.poixson.app.steps.xAppSteps_LockFile;
import com.poixson.app.steps.xAppSteps_UserNotRoot;
import com.poixson.logger.xConsole;
import com.poixson.logger.xLogRoot;
import com.poixson.plugins.loaders.xPluginLoader_Dir;
import com.poixson.scripting.loader.xScriptLoader;
import com.poixson.scripting.loader.xScriptLoader_File;
import com.poixson.tools.xDebug;
import com.poixson.tools.commands.xCommandGroup;
import com.poixson.tools.commands.xCommandProcessor;
import com.poixson.tools.config.xConfigLoader;
import com.poixson.tools.netty.NetServer;
import com.poixson.tools.scheduler.xScheduler;


/*
 * Startup sequence
 *    5 | prevent root   | xAppSteps_UserNotRoot
 *   10 | startup time   | xApp
 *   15 | lock file      | xAppSteps_LockFile
 *   20 | display logo   | gcServer_Logo
 *   50 | load configs
 *   90 | command prompt
 *  400 | load plugins
 *  405 | init plugins
 *  410 | start plugins
 *  450 | start scripts
 *  500 | start ticker
 *  600 | start socket listener
 *
 * Shutdown sequence
 *  600 | stop socket listener
 *  500 | stop ticker
 *  450 | stop scripts
 *  410 | stop plugins
 *  400 | unload plugins
 *   90 | command prompt
 *   60 | display uptime    | xApp
 *   50 | stop thread pools | xApp
 *   10 | garbage collect   | xApp
 *    1 | exit              | xApp
 */
public class gcServer extends xApp {
	public static final String DEFAULT_LOCK_FILE = "gcServer.lock";
	public static final int    DEFAULT_TCP_PORT = 11420;

	protected final xCommandProcessor commands;

	protected final AtomicReference<gcServerConfig>     config     = new AtomicReference<gcServerConfig>(null);
	protected final AtomicReference<gcServerPluginManager> plugins = new AtomicReference<gcServerPluginManager>(null);
	protected final AtomicReference<xScheduler>         sched_main = new AtomicReference<xScheduler>(null);
	protected final AtomicReference<gcTickHandler>      ticker     = new AtomicReference<gcTickHandler>(null);

	protected final CopyOnWriteArraySet<gcServerScript> scripts = new CopyOnWriteArraySet<gcServerScript>();

	protected final AtomicReference<NetServer> socket_server = new AtomicReference<NetServer>(null);

	protected final AtomicReference<DatumRouter> router = new AtomicReference<DatumRouter>(null);



	public gcServer(final String[] args) {
		super(args);
		// commands
		{
			this.commands = new xCommandProcessor();
			final xCommandGroup group_root = new xCommandGroup();
			final Object[] objs = this.getCommands();
			if (!IsEmpty(objs)) {
				for (final Object obj : objs)
					group_root.scan(obj);
			}
			this.commands.setGroup(group_root);
		}
	}



	public DatumRouter getRouter() {
		// existing instance
		{
			final DatumRouter router = this.router.get();
			if (router != null)
				return router;
		}
		// new instance
		{
			final DatumRouter router = new DatumRouter();
			if (this.router.compareAndSet(null, router))
				return router;
		}
		return this.router.get();
	}



	@xAppMoreSteps
	public Object[] steps(final xAppStepType type) {
		final String lock_file = DEFAULT_LOCK_FILE;
		return new Object[] {
			new xAppSteps_UserNotRoot(),       //  5
			new xAppSteps_LockFile(lock_file), // 15
			new gcServer_Logo(this),           // 20
		};
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// 50 | load configs
	@xAppStep(type=xAppStepType.STARTUP, title="Configs", step=50)
	public void __STARTUP__load_configs() {
//TODO: find config
		final String file = "gc.json";
		final gcServerConfig config = xConfigLoader.FromFile(file, gcServerConfig.class);
		this.config.set(config);
		// debug mode
		{
			final Boolean debug = config.getBoolean("Debug");
			if (debug != null) {
				if (debug.booleanValue())
					xDebug.SetDebug(true);
			}
		}
//TODO
//			xVars.setJLineHistorySize(200);
//			xVars.setJLineHistoryFile(
//				FileUtils.MergePaths(
//					FileUtils.cwd(),
//					"history.txt"
//				)
//			);
//TODO
//			// log level
//			if (!xVars.debug()) {
//				final xLevel lvl = config.getLogLevel();
//				if (lvl != null) {
//					xLog.getRoot()
//						.setLevel(lvl);
//				}
//			}
//TODO
//		// load zones
//		synchronized(zones) {
//			config.PopulateZones(zones);
//			log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//		}
	}



	// 90 | command prompt
	@xAppStep(type=xAppStepType.STARTUP, title="Commands", step=90)
	public void __START__commands() {
		// start reading console input
		final xConsole console = xLogRoot.Get().getConsole();
		console.setProcessor(this.commands);
		if (console != null)
			console.start();
	}
	public Object[] getCommands() {
		return new Object[] {
			new xCommands_ExitKill(this),
			new gcServerCommands(this),
			new gcServerCommands_List(this),
		};
	}



	// 400 | load plugins
	@xAppStep(type=xAppStepType.STARTUP, title="LoadPlugins", step=400)
	public void __START__plugins_load() {
		final gcServerPluginManager manager = this.getPluginManager();
		// plugin loader
		{
//TODO: plugin dir
			final xPluginLoader_Dir<gcServerPlugin> loader =
				new xPluginLoader_Dir<gcServerPlugin>(
					manager,
					new gcServerPluginFactory<gcServerPlugin>(this),
					gcServerPlugin.KEY_CLASS_MAIN,
					null
				);
			manager.removeAllLoaders();
			manager.addLoader(loader);
		}
		manager.load();
	}

	// 405 | init plugins
	@xAppStep(type=xAppStepType.STARTUP, title="InitPlugins", step=405, multi=true)
	public void __START__plugins_init(final xAppStepDAO step) {
		final gcServerPluginManager manager = this.plugins.get();
		if (step.isFirstLoop())
			manager.initAll();
		if (manager.run())
			throw new xAppStepMultiFinishedException();
	}

	// 410 | start plugins
	@xAppStep(type=xAppStepType.STARTUP, title="StartPlugins", step=410, multi=true)
	public void __START__plugins_start(final xAppStepDAO step) {
		final gcServerPluginManager manager = this.plugins.get();
		if (step.isFirstLoop())
			manager.startAll();
		if (manager.run())
			throw new xAppStepMultiFinishedException();
	}



	// scripts
	@xAppStep(type=xAppStepType.STARTUP, title="Scripts", step=450)
	public void __START__scripts() {
//TODO: configs
		try {
			final boolean safe = false;
			final xScriptLoader loader =
				new xScriptLoader_File(
					"scripts/",
					"/",
					"test.js"
				);
			final gcServerScript script = new gcServerScript(loader, safe);
			script.setVariable("out", System.out);
			script.start();
		} catch (Exception e) {
			this.fail(e);
		}
	}



	// ticker
	@xAppStep(type=xAppStepType.STARTUP, title="Ticker", step=500)
	public void __START__ticker() {
		final gcTickHandler ticker = this.getTicker();
		ticker.start();
	}
	public gcTickHandler getTicker() {
		// existing instance
		{
			final gcTickHandler ticker = this.ticker.get();
			if (ticker != null)
				return ticker;
		}
		// new instance
		{
			final gcTickHandler ticker = new gcTickHandler();
			if (this.ticker.compareAndSet(null, ticker))
				return ticker;
		}
		return this.ticker.get();
	}



	// sockets
	@xAppStep(type=xAppStepType.STARTUP, title="SocketServer", step=600)
	public void __START__sockets() {
//TODO
		final String bind = null;
		final int    port = DEFAULT_TCP_PORT;
		final boolean ssl = false;
		try {
			final NetServer server = new NetServer(bind, port, ssl);
			if (this.socket_server.compareAndSet(null, server)) {
//TODO
			}
		} catch (CertificateException e) {
			this.fail(e);
		} catch (SSLException e) {
			this.fail(e);
		} catch (InterruptedException e) {
			this.fail(e);
		}
	}



	// -------------------------------------------------------------------------------
	// shutdown steps



	// 600 | close sockets
	@xAppStep(type=xAppStepType.SHUTDOWN, title="SocketServer", step=600)
	public void __STOP__sockets() {
		final NetServer server = this.socket_server.getAndSet(null);
		if (server != null)
			server.stop();
	}



	// ticker
	@xAppStep(type=xAppStepType.SHUTDOWN, title="Ticker", step=500)
	public void __STOP__ticker() {
		final xScheduler sched = this.sched_main.getAndSet(null);
		if (sched != null)
			sched.stop();
	}



	// scripts
	@xAppStep(type=xAppStepType.SHUTDOWN, title="Scripts", step=450)
	public void __STOP__scripts() {
		final Iterator<gcServerScript> it = this.scripts.iterator();
		while (it.hasNext()) {
			try {
				final gcServerScript script = it.next();
				script.stop();
			} catch (Exception e) {
				this.fail(e);
			}
//TODO: wait for scripts to finish
			it.remove();
		}
	}



	// 410 | stop plugins
	@xAppStep(type=xAppStepType.SHUTDOWN, title="StopPlugins", step=410, multi=true)
	public void __STOP__plugins_stop(final xAppStepDAO step) {
		final gcServerPluginManager manager = this.plugins.get();
		if (manager != null) {
			if (step.isFirstLoop())
				manager.stopAll();
			if (manager.run())
				throw new xAppStepMultiFinishedException();
		}
	}

	// 400 | unload plugins
	@xAppStep(type=xAppStepType.SHUTDOWN, title="UnloadPlugins", step=400, multi=true)
	public void __STOP__plugins_unload(final xAppStepDAO step) {
		final gcServerPluginManager manager = this.plugins.get();
		if (manager != null) {
			if (step.isFirstLoop())
				manager.termAll();
			if (manager.run()) {
				this.plugins.set(null);
				throw new xAppStepMultiFinishedException();
			}
		}
	}



	// 90 | stop command prompt
	@xAppStep(type=xAppStepType.SHUTDOWN, title="Commands", step=90)
	public void __STOP__commands() {
		// stop reading console input
		final xConsole console = xLogRoot.Get().getConsole();
		if (console != null)
			console.stop();
	}



	// -------------------------------------------------------------------------------



	public gcServerConfig getConfig() {
		return this.config.get();
	}



	public gcServerPluginManager getPluginManager() {
		// existing
		{
			final gcServerPluginManager manager = this.plugins.get();
			if (manager != null)
				return manager;
		}
		// new instance
		{
			final gcServerPluginManager manager = new gcServerPluginManager();
			if (this.plugins.compareAndSet(null, manager))
				return manager;
		}
		return this.plugins.get();
	}



	public xScheduler getScheduler() {
		return this.sched_main.get();
	}



}
