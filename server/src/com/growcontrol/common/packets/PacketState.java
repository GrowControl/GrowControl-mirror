package com.growcontrol.common.packets;

import java.util.HashMap;
import java.util.Map;


public class PacketState {

	public final Map<String, Class<? extends Packet>> packets = new HashMap<String, Class<? extends Packet>>();



	public PacketState() {
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
