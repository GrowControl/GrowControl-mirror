package com.growcontrol.gcClient.clientSocket;

import com.growcontrol.gcCommon.pxnLogger.pxnLog;
import com.growcontrol.gcCommon.pxnParser.pxnParser;
import com.growcontrol.gcCommon.pxnSocket.processor.pxnSocketProcessor;
import com.growcontrol.gcCommon.pxnSocket.worker.pxnSocketWorker;


public class gcPacketReader implements pxnSocketProcessor {
	private static final String logName = "gcPacketReader";


	public gcPacketReader() {}


	@Override
	public void ProcessData(pxnSocketWorker worker, pxnParser line) {
System.out.println("PROCESSING: "+line.getOriginal());
		String first = line.getFirst();
		switch(first.toUpperCase()) {
		case "HEY":
pxnLog.get(logName).severe("Got HEY packet!");
			// request zones list
			gcPacketSender.sendLIST(worker, "zones");
			// request client plugins list
			gcPacketSender.sendLIST(worker, "plugins client");
			break;
		case "ZONE":
System.out.println("Got ZONE packet! "+line.getRest());
			break;
		case "PLUGIN":
System.out.println("Got PLUGIN packet! "+line.getRest());
			break;
		default:
			// unknown packet
			pxnLog.get(logName).warning("Unknown Packet: "+line.getOriginal());
			break;
		}
	}


	@Override
	public void Closing() {}


//private boolean sendingFile = false;
//private boolean sendingFile = true;

//if(sendingFile) return;
//System.out.println("PROCESSING: "+line.getOriginal());

//processor.sendData("TEST");
//boolean b = true;
//if(b) return;
//System.out.println("SETTING READY!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//Main.getConnectState().setStateReady();
//for(int i=0; i<1000; i++)
//sendData("TEST"+Integer.toString(i));
//			sendData("HEYBACK! gimme some plugins!");
//			gcLogger.getLogger().severe("Got HEY packet back!");
//sendingFile = true;
//			sendData("FILE");
//			return;
//		}
//		gcLogger.getLogger().warning("Unknown Packet: "+line.getOriginal());


}
