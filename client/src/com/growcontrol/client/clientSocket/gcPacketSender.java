package com.growcontrol.gcClient.clientSocket;

import com.growcontrol.gcCommon.pxnSocket.worker.pxnSocketWorker;


public final class gcPacketSender {
	private gcPacketSender() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
@SuppressWarnings("unused")
	private static final String logName = "gcPacketSender";


	// HELLO <client-version> [<username> [<password>]]
	// (login packet)
	public static void sendHELLO(pxnSocketWorker worker, String clientVersion) {
		sendHELLO(worker, clientVersion, null, null);
	}
	public static void sendHELLO(pxnSocketWorker worker, String clientVersion, String username, String password) {
		if(clientVersion == null) throw new NullPointerException("clientVersion can't be null!");
		String packet = "HELLO "+clientVersion;
		if(username != null && !username.isEmpty()) {
			packet += " "+username;
			if(password != null && !password.isEmpty()) {
				packet += " "+password;
			}
		}
		worker.Send(packet);
System.out.println("Sent HEY packet! "+clientVersion);
	}


	// LIST zones
	// LIST plugins client
	// (client list requests)
	public static void sendLIST(pxnSocketWorker worker, String args) {
		if(args == null) throw new NullPointerException("LIST args can't be null!");
		if(args.isEmpty()) throw new IllegalArgumentException("LIST args can't be empty!");
		worker.Send("LIST "+args);
System.out.println("Sent LIST packet! "+args);
	}


}
