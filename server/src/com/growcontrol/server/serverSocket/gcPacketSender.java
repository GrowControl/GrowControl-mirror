package com.growcontrol.gcServer.serverSocket;

import java.util.List;

import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnSocket.worker.pxnSocketWorker;
import com.growcontrol.gcServer.gcServer;
import com.growcontrol.gcServer.serverPlugin.gcServerPluginManager;


public final class gcPacketSender {
	private gcPacketSender() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	private static final String logName = "gcPacketSender";


	// HEY <server-version>
	// (login is ok)
	public static void sendHEY(pxnSocketWorker worker, String serverVersion) {
		if(serverVersion == null) throw new NullPointerException("serverVersion can't be null!");
		worker.Send("HEY "+serverVersion);
pxnLog.get(logName).severe("Sent HEY packet!");
	}


	// LIST zones
	// (list loaded zones)
	public static void sendLISTZones(pxnSocketWorker worker) {
		List<String> zones = gcServer.get().getZones();
		for(String zoneName : zones)
			sendZONE(worker, zoneName);
pxnLog.get(logName).severe("Sent ZONE packets!");
	}
	// ZONE
	// (send a zone)
	public static void sendZONE(pxnSocketWorker worker, String zoneName) {
		worker.Send("ZONE "+zoneName);
	}


	// LIST client plugins
	// (list loaded client plugins)
	// 0 | plugin name
	// 1 | version
	// 2 | filename
	public static void sendLISTPluginsClient(pxnSocketWorker worker) {
		List<String[]> clientPlugins = gcServerPluginManager.get().getClientPlugins();
		for(String[] info : clientPlugins) {
			sendPLUGIN(worker, info[2]);
		}
pxnLog.get(logName).severe("Sent PLUGIN packets!");
	}
	// PLUGIN
	// (send a plugin)
	public static void sendPLUGIN(pxnSocketWorker worker, String pluginName) {
		worker.Send("PLUGIN "+pluginName);
	}


}
