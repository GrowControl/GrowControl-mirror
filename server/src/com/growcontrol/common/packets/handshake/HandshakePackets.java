package com.growcontrol.common.packets.handshake;

import com.growcontrol.common.packets.PacketManager;


public class HandshakePackets {



	public static void initPackets(final PacketHandler handler) {
		handler.register(Packet_0_Hello.class);
	}



}
