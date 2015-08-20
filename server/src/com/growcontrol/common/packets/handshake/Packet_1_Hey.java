package com.growcontrol.common.packets.handshake;

import java.util.HashMap;
import java.util.Map;

import com.growcontrol.common.packets.Packet;
import com.growcontrol.common.packets.PacketProperties;
import com.growcontrol.common.packets.PacketState;


/*
{
	packet: hey,
	version: <server-version>
}
*/
@PacketProperties(
		name="hey",
		stateful=true,
		async=false
)
public class Packet_1_Hey extends Packet {



	public static void init(final PacketState packetState) {
		packetState.clearAll();
		packetState.register(Packet_1_Hey.class);
	}



	@Override
	public Object generate() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("packet", "hey");
		map.put("version", "3.5.99");
		return map;
	}



	@Override
	public boolean handle(final String name, final Map<String, Object> json) {
System.out.println();
System.out.println();
System.out.println("GOT PACKET! HEY");
System.out.println(name);
System.out.println();
System.out.println();
		return true;
	}



}
