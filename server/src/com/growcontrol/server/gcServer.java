package com.growcontrol.gcServer;

import java.util.ArrayList;
import java.util.List;

import com.growcontrol.gcCommon.pxnApp;
import com.growcontrol.gcCommon.pxnLogger.pxnLevel;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnLogger.pxnLogger;
import com.growcontrol.gcCommon.pxnScheduler.pxnScheduler;
import com.growcontrol.gcCommon.pxnScheduler.pxnTicker;
import com.growcontrol.gcCommon.pxnSocket.pxnSocketServer;
import com.growcontrol.gcCommon.pxnSocket.processor.pxnSocketProcessorFactory;
import com.growcontrol.gcServer.serverPlugin.gcServerPluginManager;
import com.growcontrol.gcServer.serverSocket.gcPacketReader;


public class gcServer extends pxnApp {
	public static final String appName = "gcServer";
	public static final String version = "3.0.9";

	// log level
	private volatile pxnLevel level = null;

	// server socket pool
	private volatile pxnSocketServer socket = null;

	// zones
	private final List<String> zones = new ArrayList<String>();


	// server instance
	public static gcServer get() {
		return (gcServer) appInstance;
	}
	protected gcServer() {
		super();
	}


	@Override
	public String getAppName() {
		return appName;
	}
	@Override
	public String getVersion() {
		return version;
	}


	// init server
	@Override
	public void Start() {
		super.Start();
		pxnLogger log = pxnLog.get();
if(!consoleEnabled) {
System.out.println("Console input is disabled due to noconsole command argument.");
//TODO: currently no way to stop the server with no console input
System.exit(0);
}

		log.info("GrowControl "+version+" Server is starting..");
		// load configs
		ServerConfig.get();
		if(!ServerConfig.isLoaded()) {
			log.fatal("Failed to load config.yml, exiting..");
			System.exit(1);
			return;
		}
		// set log level
		UpdateLogLevel();
		// start logic thread queue
//TODO:
//		getLogicQueue();
		// init listeners
		ServerListeners.get();
		// start console input thread
		StartConsole();

		// load zones
//		synchronized(this.zones) {
//			config.PopulateZones(this.zones);
//			log.info("Loaded [ "+Integer.toString(this.zones.size())+" ] zones.");
//		}

		// load scheduler
		log.info("Starting schedulers..");
		pxnScheduler.get().Start();
		pxnTicker.get().setInterval(ServerConfig.TickInterval());
		pxnTicker.get().Start();

		// load plugins
		try {
			gcServerPluginManager pluginManager = gcServerPluginManager.get("plugins/");
			pluginManager.LoadPluginsDir();
			pluginManager.InitPlugins();
			pluginManager.EnablePlugins();
		} catch (Exception e) {
			log.exception(e);
			Shutdown();
			System.exit(1);
		}

//		// load devices
//		deviceLoader.LoadDevices(Arrays.asList(new String[] {"Lamp"}));

		// start socket listener
		if(socket == null)
			socket = new pxnSocketServer();
//		socket.setHost();
		socket.setPort(ServerConfig.ListenPort());
		// create processor
		socket.setFactory(new pxnSocketProcessorFactory() {
			@Override
			public gcPacketReader newProcessor() {
				return new gcPacketReader();
			}
		});
		socket.Start();

//TODO: remove this
//log.severe("Listing Com Ports:");
//for(Map.Entry<String, String> entry : Serial.listPorts().entrySet())
//log.severe(entry.getKey()+" - "+entry.getValue());
		log.Major("GC Server Running!");


//TODO: remove temp scheduled task
// new task (multi-threaded / repeat)
//pxnSchedulerTask task = new pxnSchedulerTask(true, true) {
//	@Override
//	public void run() {
//		pxnLog.get().Publish("333333333 tick");
//	}
//	@Override
//	public String getTaskName() {
//		return "333tickname";
//	}
//};
//task.addTrigger(new triggerInterval("3s"));
//pxnScheduler.get("gcServer").newTask(task);

//System.out.println("next run: "+task.UntilNextTrigger().get(TimeU.MS));


	}


	@Override
	protected void doShutdown(int step) {
		switch(step) {
		// first step (announce)
		case 10:
			pxnLog.get().info("Stopping GC Server..");
			break;
		case 9:
			// close socket listener
			if(socket != null)
				socket.Close();
			// pause scheduler
			pxnScheduler.PauseAll();
			break;
		case 8:
			break;
		case 7:
			break;
		case 6:
			break;
		case 5:
			// stop plugins
			{
				gcServerPluginManager manager = gcServerPluginManager.get();
				if(manager != null)
					manager.DisablePlugins();
			}
			// end schedulers
			pxnScheduler.ShutdownAll();
			break;
		case 4:
			// unload plugins
			{
				gcServerPluginManager manager = gcServerPluginManager.get();
				if(manager != null)
					manager.UnloadPlugins();
			}
			break;
		case 3:
			// close sockets
			if(socket != null)
				socket.ForceClose();
			break;
		case 2:
			break;
		// last step
		case 1:
//TODO: display total time running
			break;
		}
	}
//	// reload server
//	public void Reload() {
//		pxnThreadQueue.addToMain("Reload", new Runnable() {
//			@Override
//			public void run() {
////TODO:reload
//			}
//		});
//	}


	// log level
	@Override
	public void setLogLevel(pxnLevel level) {
		if(level == null) return;
		this.level = level;
		UpdateLogLevel();
	} 
	@Override
	protected void UpdateLogLevel() {
		if(forceDebug) {
			pxnLog.get().setLevel(pxnLevel.DEBUG);
			return;
		}
		if(level == null)
			if(ServerConfig.isLoaded())
				level = ServerConfig.LogLevel();
		if(level != null)
			pxnLog.get().setLevel(level);
	}


	// process command
	@Override
	public void ProcessCommand(String line) {
		if(line == null) throw new NullPointerException("line cannot be null");
		line = line.trim();
		if(line.isEmpty()) return;
		// trigger event
		if(!ServerListeners.get().triggerCommand(line)) {
			// command not found
			pxnLog.get().Publish("Unknown command: "+line);
		}
	}


	// get zones
	public List<String> getZones() {
		synchronized(zones) {
			return new ArrayList<String>(zones);
		}
	}
	public String[] getZonesArray() {
		synchronized(zones) {
			return (String[]) zones.toArray();
		}
	}


}
