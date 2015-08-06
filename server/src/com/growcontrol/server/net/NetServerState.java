package com.growcontrol.server.net;

import io.netty.channel.socket.SocketChannel;


public class NetServerState {

	public static enum STATE { CLOSED, AUTHED }

	protected volatile STATE state = STATE.CLOSED;

	protected final NetServer server;
	protected final SocketChannel channel;
	protected final NetServerHandler handler;



	public NetServerState(final NetServer server, final SocketChannel channel) {
		this.server  = server;
		this.channel = channel;
		this.handler = new NetServerHandler(this);
	}



//	public NetServerHandler getHandler() {
//		return this.handler;
//	}
//	public SocketChannel getChannel() {
//		return this.channel;
//	}
//	public STATE getState() {
//		return this.state;
//	}



}
