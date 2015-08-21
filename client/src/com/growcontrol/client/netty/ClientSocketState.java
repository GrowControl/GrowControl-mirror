package com.growcontrol.client.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

import com.growcontrol.common.netty.SocketState;
import com.growcontrol.common.packets.PacketState;
import com.poixson.commonjava.xLogger.xLog;


// one instance per socket connection
public class ClientSocketState extends SocketState {

	private NetClient client;
	private SocketChannel channel;
	private final NetClientHandler socketHandler;
	private final PacketState packetState;



	public ClientSocketState(final NetClient client,
			final SocketChannel channel) {
		super();
		this.client  = client;
		this.channel = channel;
		this.socketHandler = new NetClientHandler(this.client, this);
		this.packetState   = new PacketState(     this.client, this);
	}



	// socket client
//	public NetClient getClient() {
//		return this.client;
//	}




	// socket channel
	public SocketChannel getChannel() {
		if(this.isClosed())
			return null;
		return this.channel;
	}
	// socket handler
	@Override
	public ChannelInboundHandlerAdapter getHandler() {
		return this.socketHandler;
	}
	// packet state holder
	@Override
	public PacketState getPacketState() {
		return this.packetState;
	}



	@Override
	public void send(final String data) {
		this.channel.writeAndFlush(data);
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
		if(!this.channel.isOpen()) {
			this.close();
			return true;
		}
		return super.isClosed();
	}



	@Override
	protected String genKey() {
		final StringBuilder str = new StringBuilder();
		final InetSocketAddress remote = this.channel.remoteAddress();
		str.append("[").append(this.id).append("] ");
		str.append(this.client.getClientKey());
		str.append(" -> ");
		str.append(remote.getHostName()).append(":").append(remote.getPort());
		return str.toString();
	}



}
