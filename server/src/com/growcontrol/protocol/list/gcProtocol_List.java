package com.growcontrol.protocol.list;
/*

import java.util.HashMap;
import java.util.Map;

import com.growcontrol.common.packets.Packet;
import com.growcontrol.common.packets.PacketDirection;
import com.growcontrol.common.packets.PacketProperties;


/ *
 * Description: 
 * Direction: client to server
 * JSON:
 * {
 *     packet: list,
 *     what: <plugins|zones|..>
 * }
 * /
@PacketProperties(
		name      = "list",
		stateful  = false,
		async     = false,
		direction = PacketDirection.CLIENT_TO_SERVER
)
public class Packet_List extends Packet {



	@Override
	public Object generate() {
		final Map<String, String> map = new HashMap<String, String>();
map.put("list plugins", "ArduinoGC");
		return map;
	}



	@Override
	public void handle(final String name, final Map<String, Object> json) {
System.out.println();
System.out.println();
System.out.println("GOT PACKET! LIST");
System.out.println(name);
System.out.println();
System.out.println();
	}







}
*/
