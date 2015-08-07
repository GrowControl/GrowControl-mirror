package com.growcontrol.common.packets.handshake;

import java.util.Map;

import com.growcontrol.common.packets.Packet;
import com.growcontrol.common.packets.PacketState;
import com.growcontrol.common.packets.annotations.PacketProperty;


@PacketProperty(
		name="*",
		stateful=true
)
public class Packet_Handshake implements Packet {



	public static void initPackets(final PacketState packetState) {
		packetState.clear();
		packetState.register(Packet_0_Hello.class);
	}



	@Override
	public boolean handle(final String name, final Map<String, Object> json) {

		return false;
	}



}
