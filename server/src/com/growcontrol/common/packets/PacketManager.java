package com.growcontrol.common.packets;

import java.util.HashMap;
import java.util.Map;


public class PacketHandler {

	public final Map<String, Class<? extends Packet>> packets = new HashMap<String, Class<? extends Packet>>();



	public PacketHandler() {
	}



	public void register(final Class<? extends Packet> packetClass) {
		synchronized(this.packets) {
			final String name = packetClass.getName();



System.out.println("NAME::::::::::::: "+name);
System.exit(1);



			this.packets.put(name, packetClass);
		}
	}
	public void clear() {
		synchronized(this.packets) {
			this.packets.clear();
		}
	}






}
