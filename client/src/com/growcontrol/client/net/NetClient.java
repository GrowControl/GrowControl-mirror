package com.growcontrol.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.growcontrol.client.configs.SavedProfileConfig;
import com.growcontrol.common.net.NetParent;
import com.growcontrol.common.packets.PacketDirection;
import com.poixson.commonjava.xLogger.xLog;


public class NetClient implements NetParent {

	public final SavedProfileConfig profile;
	public final String clientKey;
	protected volatile boolean closed = false;

	protected final Bootstrap bootstrap;
	protected final SocketChannel channel;

	protected final ConcurrentMap<Channel, ClientSocketState> states =
			new ConcurrentHashMap<Channel, ClientSocketState>();



	public NetClient(final SavedProfileConfig profile)
			throws InterruptedException {
//			throws UnknownHostException, InterruptedException {
		if(profile == null) throw new NullPointerException("profile argument is required!");
		this.profile = profile;
		this.clientKey = this.profile.toString();
		this.log().finer("Starting socket client..");
		// client bootstrap
		{
			final NetClientManager manager = NetClientManager.get();
			this.bootstrap = new Bootstrap();
			this.bootstrap.group(manager.workGroup);
		}
		this.bootstrap.channel(NioSocketChannel.class);
		this.bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		// debug
		if(NetClientManager.DETAILED_LOG) {
			final LoggingHandler handlerLogger = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(handlerLogger);
//			this.bootstrap.childHandler(handlerLogger);
		}
		// initialize socket
		final ChannelFuture future = this.bootstrap.connect(
				this.profile.host,
				this.profile.port
		);
		future.sync();
this.channel = null;
//		future.sync();
//		future.channel().closeFuture().sync();
//		this.channel = future.sync().channel().parent();
		
		
		
		
		
//		final ClientSocketState socketState = new ClientSocketState(
//				this,
//				channel
//		);
		


//		// socket initializer
//		this.initer = new NetClientInitializer(this);
//		this.bootstrap.childHandler(this.initer);
//		// start connecting
//		this.clientChannel = this.bootstrap.bind(
//				this.config.getInetAddress(),
//				this.config.port
//		).sync().channel();
		this.log().info("Socket client is connecting..");
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
	 * Stop connection.
	 */
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
		this.closed = true;
		this.log().info("Stopping socket client..");
		return this.channel.close();
	}
	/**
	 *  Disconnect all connected sockets.
	 */
	@Override
	public void CloseAll() {
//		// clone states list
//		final Collection<ClientSocketState> states = this.states.values();
//		if(states.isEmpty())
//			return;
//		this.log().info("Closing sockets..");
//		// close sockets
//		final Set<ChannelFuture> futureCloses = new HashSet<ChannelFuture>();
//		for(final ClientSocketState state : states) {
//			futureCloses.add(
//					state.closeSoon()
//			);
//		}
//		// wait for sockets to close
//		for(final ChannelFuture future : futureCloses) {
//			try {
//				future.sync();
//			} catch (InterruptedException e) {
//				this.log().trace(e);
//				break;
//			}
//		}
//		this.log().info("Closed "+Integer.toString(states.size())+" sockets");
//		final int stillConnected = this.states.size();
//		if(stillConnected > 0)
//			this.log().warning(Integer.toString(stillConnected)+" sockets still connected!");
	}
	@Override
	public boolean isClosed() {
		return this.closed;
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot("NET")
					.get("CLIENT|"+this.clientKey);
		return this._log;
	}



}
