package com.growcontrol.client;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.AnsiConsole;

import com.poixson.commonapp.app.xApp;
import com.poixson.commonapp.config.xConfigLoader;
import com.poixson.commonapp.listeners.CommandEvent;
import com.poixson.commonapp.plugin.xPluginManager;
import com.poixson.commonjava.Failure;
import com.poixson.commonjava.xLogger.xLevel;
import com.poixson.commonjava.xLogger.xLog;
import com.poixson.commonjava.xLogger.handlers.CommandHandler;


public class gcClient extends xApp {
	public static final String APPNAME = "gcClient";
	public static final String VERSION = "3.1.0";

	private volatile ClientConfig config = null;

//	// client socket
//	private volatile pxnSocketClient socket = null;
//	// client connection state
//	private final gcConnectState state = new gcConnectState();

	// zones
@SuppressWarnings("unused")
	private final List<String> zones = new ArrayList<String>();


	/**
	 * Get the client class instance.
	 * @return client instance object.
	 */
	public static gcClient get() {
		return (gcClient) xApp.get();
	}


	/**
	 * Application start entry point.
	 * @param args Command line arguments.
	 */
	public static void main(final String[] args) {
		displayStartupVars();
		displayLogoHeader();
		initMain(args, new gcClient());
	}
	protected gcClient() {
		super();
	}


	// init config
	@Override
	protected void initConfig() {
		log().fine("Loading client config..");
		this.config = (ClientConfig) xConfigLoader.Load(
			ClientConfig.CONFIG_FILE,
			ClientConfig.class
		);
		if(this.config == null) {
			Failure.fail("Failed to load "+ClientConfig.CONFIG_FILE);
			return;
		}
		updateConfig();
	}
	protected void updateConfig() {
		// version
		{
@SuppressWarnings("unused")
			final String version = this.config.getVersion();
		}
		// log level
		{
			final xLevel level = this.config.getLogLevel();
			if(level != null)
				xLog.getRoot().setLevel(level);
		}
		// debug
		{
			final Boolean debug = this.config.getDebug();
			if(debug != null)
				gcClientVars.get().debug(debug.booleanValue());
		}
	}


	@Override
	protected void processArgs(final String[] args) {
	}


	/**
	 * Client startup sequence.
	 *   2. Listeners
	 *   3.
	 *   4.
	 *   5. Load plugins and sockets
	 *   6. Start plugins and sockets
	 *   7. Start GUI
	 * @return true if success, false if problem.
	 */
	@Override
	protected boolean startup(final int step) {
		switch(step) {
		// listeners
		case 2:
			// init listeners
			final gcClientVars vars = gcClientVars.get();
			// client command listener
			vars.commands().register(
				new ClientCommands()
			);
			// io event listener
			//getLogicQueue();
			return true;
		// command prompt
		case 3:
			// command processor
			xLog.setCommandHandler(new CommandHandler() {
				@Override
				public void processCommand(String line) {
					final CommandEvent event = new CommandEvent(line);
					gcClientVars.get()
						.commands().triggerNow(
							event
						);
					if(!event.isHandled())
						log().warning("Unknown command: "+event.arg(0));
				}
			});
			// start console input thread
			initConsole();
			return true;
		case 4:
			return true;
		// load plugins and sockets
		case 5:
			{
				final xPluginManager manager = xPluginManager.get();
				manager.setClassField("Client Main");
				manager.loadAll();
				manager.initAll();
			}
			return true;
		// start plugins and sockets
		case 6:
			{
				final xPluginManager manager = xPluginManager.get();
				manager.enableAll();
			}
			return true;
		// start gui manager
		case 7:
///////			guiManager.get();
//			// show connect window
//			state.setStateClosed();
//			// connect to server
//			conn = new connection("192.168.3.3", 1142);
//			conn.sendPacket(clientPacket.sendHELLO(version, "lorenzo", "pass"));
		}
		return false;
	}
	/**
	 * Server shutdown sequence.
	 *   7.
	 *   6. Stop plugins and sockets
	 *   5. Unload plugins and sockets
	 *   4.
	 *   3.
	 *   2.
	 * @return true if success, false if problem.
	 */
	@Override
	protected boolean shutdown(final int step) {
		switch(step) {
		// stop gui
		case 7:
			// close windows
///////			guiManager.Shutdown();
			return true;
		// stop plugins and sockets
		case 6:
//			// close socket listener
//			if(socket != null)
//				socket.Close();
//			// pause scheduler
//			pxnScheduler.PauseAll();
			{
				final xPluginManager manager = xPluginManager.get();
				manager.disableAll();
			}
			return true;
		// unload plugins and sockets
		case 5:
//			// end schedulers
//			pxnScheduler.ShutdownAll();
			{
				final xPluginManager manager = xPluginManager.get();
				manager.unloadAll();
			}
//			// close sockets
			return true;
		case 4:
			return true;
		case 3:
			return true;
		case 2:
			return true;
		}
		return false;
	}


	@Override
	public String getAppName() {
		return APPNAME;
	}
	@Override
	public String getVersion() {
		return VERSION;
	}


//	// connect to server
//	public void Connect(String host, int port, String user, String pass) {
//		pxnThreadQueue.addToMain("SocketConnect",
//			new doConnect(host, port, user, pass));
//	}


//	private class doConnect implements Runnable {
//
//		public pxnSocketClient socket = null;
//		private final String host;
//		private final int    port;
//@SuppressWarnings("unused")
//		private final String user;
//@SuppressWarnings("unused")
//		private final String pass;
//
//
//		public doConnect(String host, int port, String user, String pass) {
//			if(host == null || host.isEmpty())
//				host = "127.0.0.1";
//			if(port < 1) port = 1142;
//			if(user == null || user.isEmpty()) user = null;
//			if(pass == null || pass.isEmpty()) pass = null;
//			this.host = host;
//			this.port = pxnUtilsMath.MinMax(port, 1, 65535);
//			this.user = user;
//			this.pass = pass;
//		}
//
//
//		// connect to server
//		@Override
//		public synchronized void run() {
//pxnLog.get().info("Connecting..");
//			// create socket
//			if(socket == null)
//				socket = new pxnSocketClient();
//			socket.setHost(this.host);
//			socket.setPort(this.port);
//			// create processor
//			socket.setFactory(new pxnSocketProcessorFactory() {
//				@Override
//				public gcPacketReader newProcessor() {
//					return new gcPacketReader();
//				}
//			});
//			socket.Start();
//			if(!pxnSocketState.CONNECTED.equals(socket.getState())) {
//				pxnLog.get().warning("Failed to connect!");
//				return;
//			}
//			// send HELLO packet
//			gcPacketSender.sendHELLO(
//				socket.getWorker(),
//				gcClient.version);
////				connectInfo.username,
////				connectInfo.password);
//pxnLog.get().severe("CONNECTED!!!!!!!!!!!!!!!!!!!");
//			guiManager.get().Update(guiManager.GUI.DASH);
//		}
//
//
//	}


//	// get zones
//	public List<String> getZones() {
//		synchronized(zones) {
//			return new ArrayList<String>(zones);
//		}
//	}
//	public String[] getZonesArray() {
//		synchronized(zones) {
//			return (String[]) zones.toArray();
//		}
//	}


	// ascii header
	public static void displayStartupVars() {
		AnsiConsole.out.println(" Grow Control Client "+gcClient.VERSION);
		AnsiConsole.out.println(" Running as:  "+System.getProperty("user.name"));
		AnsiConsole.out.println(" Current dir: "+System.getProperty("user.dir"));
		AnsiConsole.out.println(" java home:   "+System.getProperty("java.home"));
//		if(gcServer.get().forceDebug())
//			AnsiConsole.out.println(" Force Debug: true");
//		String argsMsg = getArgsMsg();
//		if(argsMsg != null && !argsMsg.isEmpty())
//			AnsiConsole.out.println(" args: [ "+argsMsg+" ]");
		AnsiConsole.out.println();
		AnsiConsole.out.flush();
	}
	protected static void displayLogoHeader() {
	}


}
