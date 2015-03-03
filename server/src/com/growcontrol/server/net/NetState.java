package com.growcontrol.server.net;

import io.netty.channel.socket.SocketChannel;


public class NetState {

	public static enum STATE { CLOSED, AUTHED }

	protected volatile STATE state = STATE.CLOSED;
	public final SocketChannel channel;



	public NetState(final SocketChannel channel) {
		this.channel = channel;
	}



	public STATE getState() {
		return this.state;
	}



}
