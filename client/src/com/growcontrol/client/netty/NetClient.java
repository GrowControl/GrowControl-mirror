package com.growcontrol.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.growcontrol.client.configs.SavedProfileConfig;
import com.growcontrol.common.netty.NetParent;
import com.growcontrol.common.netty.SocketState;
import com.growcontrol.common.packets.PacketDirection;
import com.poixson.commonjava.xLogger.xLog;


public class NetClient implements NetParent {
	private static final String LOG_NAME = "NET";

	public final SavedProfileConfig profile;
	public final String clientKey;
	protected volatile boolean closed = false;

	protected final Bootstrap bootstrap;
//	protected final NetClientInitializer initer;
//	protected final Channel clientChannel;

	protected final ConcurrentMap<Channel, ClientSocketState> states =
			new ConcurrentHashMap<Channel, ClientSocketState>();



	//throws UnknownHostException, SocketException, InterruptedException {
@SuppressWarnings("unused")
	public NetClient(final SavedProfileConfig profile)
			throws UnknownHostException, InterruptedException {
		if(profile == null) throw new NullPointerException("profile argument is required!");
		this.profile = profile;
		this.clientKey = this.profile.toString();
		this.log().finer("Starting socket connection..");
		// server bootstrap
		{
			final NetClientManager manager = NetClientManager.get();
			this.bootstrap = new Bootstrap();
			this.bootstrap.group(
//					manager.bossGroup,
					manager.workGroup
			);
		}
//		// socket backlog
//		{
//			final int backlog = gcClientVars.getConfig().getSocketBacklog();
//			this.bootstrap.option(
//					ChannelOption.SO_BACKLOG,
//					backlog
//			);
//		}
		this.bootstrap.channel(NioSocketChannel.class);
		this.bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		// debug
		if(NetClientManager.DETAILED_LOG) {
			final LoggingHandler handlerLogger = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(handlerLogger);
//			this.bootstrap.childHandler(handlerLogger);
		}
		// initialize socket
//		final ChannelFuture future = this.bootstrap.connect(
//				this.profile.host,
//				this.profile.port
//		);
//		this.channel = future.sync().channel();

//		// socket initializer
//		this.initer = new NetServerInitializer(this);
//		this.bootstrap.childHandler(this.initer);
//		// start listening
//		this.serverChannel = this.bootstrap.bind(
//				this.config.getInetAddress(),
//				this.config.port
//		).sync().channel();
		this.log().info("Socket is connecting..");
	}








	/**
	 * New socket connection established.
	 * @param state
	 */
	public void register(final ClientSocketState socketState) {
		if(socketState == null) throw new NullPointerException("socketState argument is required!");
		final SocketChannel channel = socketState.getChannel();
		if(channel == null) throw new NullPointerException("Channel is null, socket may have disconnected already!");
		this.states.put(channel, socketState);
//TODO: store the socket
	}
	/**
	 * Remove disconnected socket.
	 * @param state
	 */
	// socket disconnected
	public void unregister(final ClientSocketState socketState) {
		if(socketState == null) throw new NullPointerException("socketState argument is required!");
		this.states.remove(socketState);
	}



	public ClientSocketState getClientSocketState(final Channel channel) {
		if(channel == null)
			return null;
		return this.states.get(channel);
	}
	@Override
	public SocketState getSocketState(final Channel channel) {
		return this.getClientSocketState(channel);
	}



	@Override
	public int getSocketsCount() {
		return this.states.size();
	}



	public String getClientKey() {
		return this.clientKey;
	}
	@Override
	public String getKeyHash() {
		return this.getClientKey();
	}



	@Override
	public PacketDirection getDirection() {
		return PacketDirection.CLIENT_TO_SERVER;
	}



	/**
	 * Close the connection.
	 */
	@Override
	public void close() {
		throw new UnsupportedOperationException();
//		try {
//			this.closeSoon()
//					.sync();
//		} catch (InterruptedException e) {
//			this.log().trace(e);
//		}
	}
	public ChannelFuture closeSoon() {
		throw new UnsupportedOperationException();
//		this.log().info("Closing socket client..");
//		this.closed = true;
//		return this.clientChannel.close();
	}
	/**
	 * Disconnect all connected sockets.
	 */
	@Override
	public void CloseAll() {
		// clone states list
		final Collection<ClientSocketState> states = this.states.values();
		if(states.isEmpty())
			return;
		this.log().info("Closing sockets..");
		// close sockets
		final Set<ChannelFuture> futureCloses = new HashSet<ChannelFuture>();
		for(final ClientSocketState state : states) {
			futureCloses.add(
					state.closeSoon()
			);
		}
		// wait for sockets to close
		for(final ChannelFuture future : futureCloses) {
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
		if(stillConnected > 0) {
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
		if(this._log == null)
			this._log = xLog.getRoot(LOG_NAME)
					.get("C|"+this.clientKey);
		return this._log;
	}



}
