package com.growcontrol.common.packets.handshake;

import java.util.Map;

import com.growcontrol.common.packets.Packet;
import com.growcontrol.common.packets.PacketState;
import com.growcontrol.common.packets.annotations.PacketProperty;


@PacketProperty(
		name="hey",
		stateful=true
)
public class Packet_1_Hey extends Packet {



	public static void init(final PacketState packetState) {
		packetState.clear();
		packetState.register(Packet_0_Hello.class);
	}



	@Override
	public boolean handle(final String name, final Map<String, Object> json) {
		return false;
	}



}
