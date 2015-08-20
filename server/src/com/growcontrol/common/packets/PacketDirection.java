package com.growcontrol.common.packets;


public enum PacketDirection {

	CLIENT_TO_SERVER,
	SERVER_TO_CLIENT,
	BOTH;



	public static PacketDirection invert(final PacketDirection direction) {
		if(direction == null)
			return null;
		// client to server
		if(CLIENT_TO_SERVER.equals(direction))
			return SERVER_TO_CLIENT;
		else
		// server to client
		if(SERVER_TO_CLIENT.equals(direction))
			return CLIENT_TO_SERVER;
		else
		// both directions
		if(BOTH.equals(direction))
			return BOTH;
		throw new IllegalArgumentException("Unknown direction: "+direction.toString());
	}



	// can send this packet
	public static boolean canSend(final PacketDirection expected,
			final PacketDirection current) {
		if(BOTH.equals(expected))
			return true;
		return expected.equals(
				current
		);
	}
	// can receive this packet
	public static boolean canReceive(final PacketDirection expected,
			final PacketDirection current) {
		if(BOTH.equals(expected))
			return true;
		return expected.equals(
				invert(current)
		);
	}




}
