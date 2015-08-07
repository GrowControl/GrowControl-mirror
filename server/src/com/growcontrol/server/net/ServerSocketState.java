package com.growcontrol.server.net;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

import com.growcontrol.common.net.SocketState;
import com.growcontrol.common.packets.PacketState;
import com.poixson.commonjava.xLogger.xLog;


// one instance per socket connection
public class ServerSocketState extends SocketState {

	private NetServer server;
	private SocketChannel channel;
	private final NetServerHandler socketHandler;
	private final PacketState packetState;



	public ServerSocketState(final NetServer server, final SocketChannel channel) {
		super();
		this.server  = server;
		this.channel = channel;
		this.socketHandler = new NetServerHandler(this.server, this);
		this.packetState = new PacketState();
//		this.packetState = new PacketState(server, this);
	}



	// socket server
	public NetServer getServer() {
		return this.server;
	}



	// socket channel
	public SocketChannel getChannel() {
		if(SessionState.CLOSED.equals(this.getSessionState()))
			return null;
		return this.channel;
	}
	// socket handler
	@Override
	public ChannelInboundHandlerAdapter getHandler() {
		return this.socketHandler;
	}
	// packet handler
	@Override
	public PacketState getPacketState() {
		return this.packetState;
	}



	// close the socket
	@Override
	public void close() {
		try {
			this.closeSoon()
				.sync();
		} catch (InterruptedException e) {
xLog.getRoot("NET").trace(e);
		}
	}
	public ChannelFuture closeSoon() {
		this.setSessionState(SessionState.CLOSED);
		final ChannelFuture futureClose = this.channel.close();
		return futureClose;
	}
	@Override
	public boolean isClosed() {
		if(!this.channel.isOpen())
			this.close();
		return SessionState.CLOSED.equals(this.sessionState);
	}



	@Override
	protected String genKey() {
		final StringBuilder str = new StringBuilder();
		final InetSocketAddress remote = this.channel.remoteAddress();
		str.append("<").append(this.id).append(">");
		str.append(remote.getHostName()).append(":").append(remote.getPort());
		str.append("->");
		str.append(this.server.getServerKey());
		return str.toString();
	}



}
