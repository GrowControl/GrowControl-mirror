package com.growcontrol.common.packets.handshake;

import com.growcontrol.common.packets.PacketHandler;


public class HandshakeInit  {



	public static void initPackets(final PacketHandler handler) {
		handler.register(Packet_0_Hello.class);
	}



}
