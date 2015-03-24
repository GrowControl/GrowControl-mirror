package com.growcontrol.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArraySet;

import com.growcontrol.common.netty.NetConfig;
import com.poixson.commonjava.Utils.xCloseableMany;
import com.poixson.commonjava.xLogger.xLog;


public class NetServer implements xCloseableMany {

	protected final NetServerManager manager;

	protected final NetConfig config;
	protected final String configKey;
	protected volatile boolean closed = false;

	protected final ServerBootstrap bootstrap;
	protected final NetServerInitializer initer;
//	protected final NetServerHandler handler;
	protected final Channel serverChannel;

	protected final CopyOnWriteArraySet<NetStateDAO> states = new CopyOnWriteArraySet<NetStateDAO>();

	// firewall
//	protected final NetFirewall firewall;

	// debug logger
//	protected final LoggingHandler handlerLogger;



	public NetServer(final NetConfig config)
			throws UnknownHostException, SocketException, InterruptedException {
		if(config == null) throw new NullPointerException();
		if(!config.enabled) throw new UnsupportedOperationException("This socket server is disabled");
		this.config = config;
		this.configKey = config.toString();
		this.manager = NetServerManager.get();
		this.log().finer("Starting socket server.. "+this.configKey);
		// bootstrap
		this.bootstrap = new ServerBootstrap();
		this.bootstrap.group(
				this.manager.bossGroup,
				this.manager.workGroup
		);
		this.bootstrap.option(
				ChannelOption.SO_BACKLOG,
				NetServerManager.BACKLOG
		);
		this.bootstrap.channel(NioServerSocketChannel.class);
//		// this method is bad for servers
//		this.bossGroup = Executors.newCachedThreadPool();
//		this.workGroup = Executors.newCachedThreadPool();
//		// this is used in netty examples
//		this.bossGroup = new NioEventLoopGroup();
//		this.workGroup = new NioEventLoopGroup();
		// debug
		if(this.manager.debug) {
			final LoggingHandler handlerLogger = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(     handlerLogger);
			this.bootstrap.childHandler(handlerLogger);
//			this.handlerLogger = new LoggingHandler(LogLevel.INFO);
//			this.bootstrap.handler(     this.handlerLogger);
//			this.bootstrap.childHandler(this.handlerLogger);
//		} else {
//			this.handlerLogger = null;
		}
		// protocol handler
//		this.handler = new NetServerHandler(this);
		// socket initializer
		this.initer = new NetServerInitializer(this);
		this.bootstrap.childHandler(this.initer);
		// start listening
		this.serverChannel = this.bootstrap.bind(
				this.config.getInetAddress(),
				this.config.port
		).sync().channel();
		this.log().info("Socket server listening on "+this.config.toString());
	}



	/**
	 *  New socket connection accepted.
	 *  @return true to accept the connection or false to deny.
	 */
	public boolean register(final NetStateDAO dao) {
		if(dao == null) throw new NullPointerException();
		this.states.add(dao);
//		final SocketChannel channel = state.getChannel();
//		// check firewall
//		if(!this.manager.firewall.check(channel)) {
//			return false;
//		}
		//TODO: store the socket
		return true;
	}
	// socket disconnected
	public void unregister(final NetStateDAO dao) {
		if(dao == null) throw new NullPointerException();
		this.states.remove(dao);
//TODO:
	}



	@Override
	public void close() {
		this.closed = true;
//		this.log().info("Stopping socket server..");
		try {
			this.serverChannel.close().sync();
		} catch (InterruptedException e) {
			this.log().trace(e);
		}
		this.log().info("Stopped socket server: "+this.config.toString());
	}
	// close all socket connections
	@Override
	public void CloseAll() {
//TODO:
//		this.log().info("Closing sockets..");
//		final Iterator<Channel> it = this.channels.iterator();
//		while(it.hasNext()) {
//			it.next().close().sync();
//			it.remove();
//		}
//		this.channels.clear();
	}
	@Override
	public boolean isClosed() {
		return this.closed;
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = this.manager.log()
				.get(this.configKey);
		return this._log;
	}



}
