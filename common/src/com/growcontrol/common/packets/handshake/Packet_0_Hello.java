package com.growcontrol.common.packets.handshake;

import java.util.HashMap;
import java.util.Map;

import com.growcontrol.common.packets.Packet;
import com.growcontrol.common.packets.PacketDirection;
import com.growcontrol.common.packets.PacketProperties;
import com.growcontrol.common.packets.PacketState;


/*
 * Description: Protocol initializer
 * Direction:   client to server
 * JSON:
 * {
 *     packet: hello,
 *     version: <client-version>
 * }
 */
@PacketProperties(
		name      = "hello",
		stateful  = true,
		async     = false,
		direction = PacketDirection.CLIENT_TO_SERVER
)
public class Packet_0_Hello extends Packet {



	public static void init(final PacketState packetState) {
		packetState.clearAll();
		packetState.register(Packet_0_Hello.class);
	}



	@Override
	public Object generate() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("packet", "hello");
		map.put("version", "3.5.99");
		return map;
	}



	@Override
	public void handle(final String name, final Map<String, Object> json) {

		this.send(new Packet_1_Hey());
//		this.send(Packet_1_Hey.class);


System.out.println();
System.out.println();
System.out.println("GOT PACKET! HELLO");
System.out.println(name);
System.out.println();
System.out.println();
	}



}
