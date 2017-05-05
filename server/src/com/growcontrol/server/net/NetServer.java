/*
package com.growcontrol.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.growcontrol.common.netty.NetParent;
import com.growcontrol.common.netty.SocketState;
import com.growcontrol.common.packets.PacketDirection;
import com.growcontrol.server.gcServerVars;
import com.growcontrol.server.configs.NetServerConfig;
import com.poixson.commonapp.net.firewall.NetFirewall;
import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;
import com.poixson.commonjava.xLogger.xLog;


public class NetServer implements NetParent {
	private static final String LOG_NAME = "NET";

	public final NetServerConfig netConfig;
	public final String serverKey;
	protected volatile boolean closed = false;

	protected final ServerBootstrap bootstrap;
	protected final NetServerInitializer initer;
	protected final Channel serverChannel;

	protected final ConcurrentMap<Channel, ServerSocketState> states =
			new ConcurrentHashMap<Channel, ServerSocketState>();



//throws UnknownHostException, SocketException, InterruptedException {
	public NetServer(final NetServerConfig netConfig)
			throws UnknownHostException, InterruptedException {
		if (netConfig == null)  throw new RequiredArgumentException("netConfig");
		if (!netConfig.isEnabled()) throw new UnsupportedOperationException("This socket server is disabled!");
		this.netConfig = netConfig;
		this.serverKey = this.netConfig.toString();
		this.log().finer("Starting socket server..");
		// server bootstrap
		{
			final NetServerManager manager = NetServerManager.get();
			this.bootstrap = new ServerBootstrap();
			this.bootstrap.group(
					manager.bossGroup,
					manager.workGroup
			);
		}
		// socket backlog
		{
			final int backlog = gcServerVars.getConfig().getSocketBacklog();
			this.bootstrap.option(
					ChannelOption.SO_BACKLOG,
					backlog
			);
		}
		this.bootstrap.channel(NioServerSocketChannel.class);
		// debug
		if (this.log().isLoggable(xLevel.DETAIL)) {
			final LoggingHandler handlerLogger = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(handlerLogger);
			this.bootstrap.childHandler(handlerLogger);
		}
		// socket initializer
		this.initer = new NetServerInitializer(this);
		this.bootstrap.childHandler(this.initer);
		// start listening
		this.serverChannel = this.bootstrap.bind(
				this.netConfig.getInetAddress(),
				this.netConfig.getPort()
		).sync().channel();
		this.log().info("Socket server listening for connections..");
	}



	/ **
	 * New socket connection accepted.
	 * @param state
	 * @return true to accept the connection or false to deny.
	 * /
	public boolean register(final ServerSocketState socketState) {
		if (socketState == null) throw new RequiredArgumentException("socketState");
		final SocketChannel channel = socketState.getChannel();
		if (channel == null) throw new RuntimeException("Channel is null, socket may have disconnected already!");
		this.states.put(channel, socketState);
		// check firewall
		{
			final NetFirewall firewall = this.getFirewall();
			final Boolean result = firewall.check(
					channel.localAddress(),
					channel.remoteAddress()
			);
			// default firewall action
			if (result == null) {
				this.log().finer("No matching firewall rule for this socket: "+socketState.toString());
//TODO:
this.log().severe("THIS IS UNFINISHED: NetServer->register() default firewall action");


			} else
			// allowed by firewall
			if (result) {
				this.log().fine("Firewall allowed connection: "+socketState.toString());
			} else {
				this.log().warning("Firewall blocked connection: "+socketState.toString());
				return false;
			}
		}
//TODO: store the socket
		return true;
	}
	/ **
	 * Remove disconnected socket.
	 * @param state
	 * /
	// socket disconnected
	public void unregister(final ServerSocketState socketState) {
		if (socketState == null) throw new RequiredArgumentException("socketState");
		this.states.remove(socketState);
	}



	public ServerSocketState getServerSocketState(final Channel channel) {
		if (channel == null)
			return null;
		return this.states.get(channel);
	}
	@Override
	public SocketState getSocketState(final Channel channel) {
		return this.getServerSocketState(channel);
	}



	@Override
	public int getSocketsCount() {
		return this.states.size();
	}



	public String getServerKey() {
		return this.serverKey;
	}
	@Override
	public String getKeyHash() {
		return this.getServerKey();
	}



	public NetFirewall getFirewall() {
		return NetServerManager.get()
				.getFirewall();
	}



	@Override
	public PacketDirection getDirection() {
		return PacketDirection.SERVER_TO_CLIENT;
	}



	/ **
	 * Stop listening for connections.
	 * /
	@Override
	public void close() {
		try {
			this.closeSoon()
				.sync();
		} catch (InterruptedException e) {
			this.log().trace(e);
		}
	}
	public ChannelFuture closeSoon() {
		this.log().info("Stopping socket server..");
		this.closed = true;
		return this.serverChannel.close();
	}
	/ **
	 *  Disconnect all connected sockets.
	 * /
	@Override
	public void CloseAll() {
		// clone states list
		final Collection<ServerSocketState> states = this.states.values();
		if (states.isEmpty())
			return;
		this.log().info("Closing sockets..");
		// close sockets
		final Set<ChannelFuture> futureCloses = new HashSet<ChannelFuture>();
		for (final ServerSocketState state : states) {
			futureCloses.add(
				state.closeSoon()
			);
		}
		// wait for sockets to close
		for (final ChannelFuture future : futureCloses) {
			try {
				future.sync();
			} catch (InterruptedException e) {
				this.log().trace(e);
				break;
			}
		}
		this.log().info(
				(new StringBuilder())
				.append("Closed ")
				.append(futureCloses.size())
				.append(" sockets")
				.toString()
		);
		final int stillConnected = this.states.size();
		if (stillConnected > 0) {
			this.log().warning(
				(new StringBuilder())
				.append(stillConnected)
				.append(" sockets still connected!")
				.toString()
			);
		}
	}
	@Override
	public boolean isClosed() {
		return this.closed;
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if (this._log == null)
			this._log = xLog.getRoot(LOG_NAME)
					.get("S|"+this.serverKey);
		return this._log;
	}



}
*/
