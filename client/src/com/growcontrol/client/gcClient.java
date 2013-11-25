package com.growcontrol.gcClient;

import java.util.ArrayList;
import java.util.List;

import com.growcontrol.gcClient.clientPlugin.gcClientPluginManager;
import com.growcontrol.gcClient.clientSocket.gcPacketReader;
import com.growcontrol.gcClient.clientSocket.gcPacketSender;
import com.growcontrol.gcCommon.pxnApp;
import com.growcontrol.gcCommon.pxnLogger.pxnLevel;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnLogger.pxnLogger;
import com.growcontrol.gcCommon.pxnScheduler.pxnScheduler;
import com.growcontrol.gcCommon.pxnScheduler.pxnTicker;
import com.growcontrol.gcCommon.pxnSocket.pxnSocketClient;
import com.growcontrol.gcCommon.pxnSocket.pxnSocketUtils.pxnSocketState;
import com.growcontrol.gcCommon.pxnSocket.processor.pxnSocketProcessorFactory;
import com.growcontrol.gcCommon.pxnThreadQueue.pxnThreadQueue;
import com.growcontrol.gcCommon.pxnUtils.pxnUtilsMath;


public class gcClient extends pxnApp {
	public static final String appName = "gcClient";
	public static final String version = "3.0.10";
	public static final String defaultPrompt = "";

	// client socket
	private volatile pxnSocketClient socket = null;
//	// client connection state
//	private final gcConnectState state = new gcConnectState();

	// zones
	private final List<String> zones = new ArrayList<String>();


	// client instance
	public static gcClient get() {
		return (gcClient) appInstance;
	}
	public gcClient() {
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


	// init client
	@Override
	public void Start() {
		super.Start();
		pxnLogger log = pxnLog.get();
if(!consoleEnabled) {
System.out.println("Console input is disabled due to noconsole command argument.");
//TODO: currently no way to stop the server with no console input
System.exit(0);
}

		log.info("GrowControl "+version+" Client is starting..");
		// load configs
		ClientConfig.get();
		if(!ClientConfig.isLoaded()) {
			log.fatal("Failed to load config.yml, exiting..");
			System.exit(1);
			return;
		}
		// set log level
		UpdateLogLevel();
		// init listeners
		ClientListeners.get();
		// start console input thread
		StartConsole();

		// load scheduler
		log.info("Starting schedulers..");
		pxnScheduler.get(getAppName())
			.start();
		// load ticker
		pxnTicker.get();

		// load plugins
		try {
			gcClientPluginManager pluginManager = gcClientPluginManager.get("plugins/");
			pluginManager.LoadPluginsDir();
			pluginManager.InitPlugins();
			pluginManager.EnablePlugins();
		} catch (Exception e) {
			log.exception(e);
			Shutdown();
			System.exit(1);
		}

		// start gui manager
		guiManager.get();

//		// show connect window
//		state.setStateClosed();
//		// connect to server
//		conn = new connection("192.168.3.3", 1142);
//		conn.sendPacket(clientPacket.sendHELLO(version, "lorenzo", "pass"));

	}


	// connect to server
	public void Connect(String host, int port, String user, String pass) {
		pxnThreadQueue.addToMain("SocketConnect",
			new doConnect(host, port, user, pass));
	}


	private class doConnect implements Runnable {
	
		public pxnSocketClient socket = null;
		private final String host;
		private final int    port;
@SuppressWarnings("unused")
		private final String user;
@SuppressWarnings("unused")
		private final String pass;


		public doConnect(String host, int port, String user, String pass) {
			if(host == null || host.isEmpty())
				host = "127.0.0.1";
			if(port < 1) port = 1142;
			if(user == null || user.isEmpty()) user = null;
			if(pass == null || pass.isEmpty()) pass = null;
			this.host = host;
			this.port = pxnUtilsMath.MinMax(port, 1, 65535);
			this.user = user;
			this.pass = pass;
		}


		// connect to server
		@Override
		public synchronized void run() {
pxnLog.get().info("Connecting..");
			// create socket
			if(socket == null)
				socket = new pxnSocketClient();
			socket.setHost(this.host);
			socket.setPort(this.port);
			// create processor
			socket.setFactory(new pxnSocketProcessorFactory() {
				@Override
				public gcPacketReader newProcessor() {
					return new gcPacketReader();
				}
			});
			socket.Start();
			if(!pxnSocketState.CONNECTED.equals(socket.getState())) {
				pxnLog.get().warning("Failed to connect!");
				return;
			}
			// send HELLO packet
			gcPacketSender.sendHELLO(
				socket.getWorker(),
				gcClient.version);
//				connectInfo.username,
//				connectInfo.password);
pxnLog.get().severe("CONNECTED!!!!!!!!!!!!!!!!!!!");
			guiManager.get().Update(guiManager.GUI.DASH);
		}


	}


	@Override
	protected void doShutdown(int step) {
		switch(step) {
		// first step (announce)
		case 10:
			pxnLog.get().info("Stopping GC Client..");
			break;
		case 9:
			// close socket
			if(socket != null)
				socket.Close();
			// pause scheduler
			pxnScheduler.PauseAll();
			break;
		case 8:
			// close windows
			guiManager.Shutdown();
			break;
		case 7:
			break;
		case 6:
			break;
		case 5:
			// stop plugins
			{
				gcClientPluginManager manager = gcClientPluginManager.get();
				if(manager != null)
					manager.DisablePlugins();
			}
			// end schedulers
			pxnScheduler.ShutdownAll();
			break;
		case 4:
			// unload plugins
			{
				gcClientPluginManager manager = gcClientPluginManager.get();
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


	// log level
	@Override
	protected void UpdateLogLevel() {
		if(forceDebug) {
			pxnLog.get().setLevel(pxnLevel.DEBUG);
			return;
		}
		if(!ClientConfig.isLoaded()) return;
		String levelStr = ClientConfig.LogLevel();
		if(levelStr != null && !levelStr.isEmpty()) {
			pxnLog.get().setLevel(
				pxnLevel.Parse(levelStr)
			);
		}
	}


	// process command
	@Override
	public void ProcessCommand(String line) {
		if(line == null) throw new NullPointerException("line cannot be null");
		line = line.trim();
		if(line.isEmpty()) return;
		// trigger event
		if(!ClientListeners.get().triggerCommand(line)) {
			// command not found
			pxnLog.get().warning("Unknown command: "+line);
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


	@Override
	public void setLogLevel(pxnLevel level) {
	}


}
