package com.growcontrol.common.packets.handshake;

import java.util.Map;

import com.growcontrol.common.packets.Packet;


public class Packet_1_Hey implements Packet {



	@Override
	public boolean handle(final String name, final Map<String, Object> json) {
		return false;
	}



}
