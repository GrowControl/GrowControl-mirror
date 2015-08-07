package com.growcontrol.common.packets.handshake;

import com.growcontrol.common.packets.PacketState;



public class HandshakeInit  {



	public static void initPackets(final PacketState packetState) {
		packetState.register(Packet_0_Hello.class);
	}



}
