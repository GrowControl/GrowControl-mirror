package com.growcontrol.server.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import com.growcontrol.common.net.SocketState;
import com.poixson.commonjava.Utils.xCloseable;
import com.poixson.commonjava.xLogger.xLog;


public class ServerSocketState implements xCloseable {

	private SocketState state = SocketState.CLOSED;
	private NetServer server;
	private SocketChannel channel;
//	protected final NetServerHandler handler = new NetServerHandler(this);

	protected static final AtomicInteger nextSocketId = new AtomicInteger(0);
	public final int    id;
	public final String key;



	public ServerSocketState(final NetServer server, final SocketChannel channel) {
		this.server  = server;
		this.channel = channel;
		this.id  = getNextId();
		this.key = this.genKey();
	}



	private static int getNextId() {
		return nextSocketId.incrementAndGet();
	}



	// socket state
	public SocketState getState() {
		return this.state;
	}
	public void setState(final SocketState state) {
		this.state = state;
	}



	// socket server
	public NetServer getServer() {
		return this.server;
	}



	// socket channel
	public SocketChannel getChannel() {
		if(SocketState.CLOSED.equals(this.state))
			return null;
		return this.channel;
	}
	public boolean ChannelMatches(final Channel channel) {
		if(channel == null)
			return false;
		return channel.equals(this.channel);
	}



//	public NetServerHandler getHandler() {
//		return this.handler;
//	}



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
		this.state = SocketState.CLOSED;
		final ChannelFuture futureClose = this.channel.close();
		return futureClose;
	}
	@Override
	public boolean isClosed() {
		return false;
	}



	public String getStateKey() {
		return this.key;
	}
	private String genKey() {
		final StringBuilder str = new StringBuilder();
		final InetSocketAddress remote = this.channel.remoteAddress();
		str.append("<").append(this.id).append(">");
		str.append(remote.getHostName()).append(":").append(remote.getPort());
		str.append("->");
		str.append(this.server.getServerKey());
		return str.toString();
	}
	@Override
	public String toString() {
		return this.getStateKey();
	}



	// logger
	public xLog log() {
		return xLog.getRoot("NET")
				.get(this.getStateKey());
	}



}
