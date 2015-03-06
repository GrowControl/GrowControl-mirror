package com.growcontrol.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

import com.growcontrol.server.configs.gcSocketDAO;
import com.poixson.commonjava.xVars;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xStartable;
import com.poixson.commonjava.Utils.threads.xThreadFactory;
import com.poixson.commonjava.xLogger.xLog;


public class NetManager implements xStartable {

	public static final int BACKLOG = 10;

	private static volatile NetManager instance = null;
	private static final Object instanceLock = new Object();

	protected final AtomicBoolean running = new AtomicBoolean(false);
	protected final Set<NetServer> servers = new HashSet<NetServer>();
	protected final Set<gcSocketDAO> configs = new CopyOnWriteArraySet<gcSocketDAO>();

	protected final ServerBootstrap bootstrap;
	protected final EventLoopGroup bossGroup;
	protected final EventLoopGroup workGroup;



	public static NetManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new NetManager();
			}
		}
		return instance;
	}



	public NetManager() {
		Keeper.add(this);
		//if(xVars.get().debug())
		CustomNettyLogger.Install(this.log());
		// thread groups
		final int cores = Runtime.getRuntime().availableProcessors();
		this.bossGroup = new NioEventLoopGroup(1,     new xThreadFactory("netty-boss", true));
		this.workGroup = new NioEventLoopGroup(cores, new xThreadFactory("netty-work", true));
		// server bootstrap
		this.bootstrap = new ServerBootstrap();
		this.bootstrap.group(this.bossGroup, this.workGroup);
		this.bootstrap.option(ChannelOption.SO_BACKLOG, BACKLOG);
		this.bootstrap.channel(NioServerSocketChannel.class);
		// handlers
		if(xVars.get().debug()) {
			final LoggingHandler logHandler = new LoggingHandler(LogLevel.INFO);
			this.bootstrap.handler(     logHandler);
			this.bootstrap.childHandler(logHandler);
		}
		final SslContext sslContext = null;
		this.bootstrap.childHandler(
				new ServerSocketChannelInitializer(this.log(), sslContext)
		);
//		// this method is bad for servers
//		this.bossGroup = Executors.newCachedThreadPool();
//		this.workGroup = Executors.newCachedThreadPool();
//		// this is used in netty examples
//		this.bossGroup = new NioEventLoopGroup();
//		this.workGroup = new NioEventLoopGroup();
//		this.childHandler = new ChannelInitializer<SocketChannel>() {
//			@Override
//			protected void initChannel(SocketChannel ch) throws Exception {
//			}
//		}
	}



	protected static class ServerSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
		private final xLog log;

		private final SslContext sslContext;

		public ServerSocketChannelInitializer(final xLog log, final SslContext sslContext) {
			this.log = log;
			this.sslContext = sslContext;
		}

		@Override
		protected void initChannel(final SocketChannel ch) throws Exception {
			final ChannelPipeline pipe = ch.pipeline();
			if(this.sslContext != null)
				pipe.addLast(this.sslContext.newHandler(ch.alloc()));
			pipe.addLast(new HttpServerCodec());
			pipe.addLast(
					new ServerSocketChannelHandler(this.log)
			);
		}

	}



	protected static class ServerSocketChannelHandler extends ChannelInboundHandlerAdapter {
		private final xLog log;

		private static final byte[] CONTENT = "Hello World!!!".getBytes();

		public ServerSocketChannelHandler(final xLog log) {
			this.log = log;
		}

		@Override
		public void channelReadComplete(final ChannelHandlerContext context) {
			context.flush();
		}

		@Override
		public void channelRead(final ChannelHandlerContext context, final Object msg) {
			if(msg instanceof HttpRequest) {
				final HttpRequest request = (HttpRequest) msg;
				if(HttpHeaders.is100ContinueExpected(request)) {
					context.write(
						new DefaultFullHttpResponse(
							HttpVersion.HTTP_1_1,
							HttpResponseStatus.CONTINUE
						)
					);
				}
				final boolean keepalive = HttpHeaders.isKeepAlive(request);
				final FullHttpResponse response = new DefaultFullHttpResponse(
						HttpVersion.HTTP_1_1,
						HttpResponseStatus.OK,
						Unpooled.wrappedBuffer(CONTENT)
				);
				response.headers().set(
						HttpHeaders.Names.CONTENT_TYPE,
						"text/plain"
				);
				response.headers().set(
						HttpHeaders.Names.CONTENT_LENGTH,
						response.content().readableBytes()
				);
				if(!keepalive) {
					context.write(response).addListener(ChannelFutureListener.CLOSE);
				} else {
					response.headers().set(
							HttpHeaders.Names.CONNECTION,
							HttpHeaders.Values.KEEP_ALIVE
					);
					context.write(response);
				}
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			// IOException
			if("Connection reset by peer".equals(cause.getMessage())) {
				String msg = "";
				try {
					final Channel ch = ctx.channel();
					msg += " - " +ch.localAddress().toString();
					msg += " <= "+ch.remoteAddress().toString();
				} catch (Exception ignore) {}
				this.log.warning(cause.getMessage()+msg);
			} else {
				this.log.trace((Exception) cause);
			}
			ctx.close();
		}

	}



	public void setConfigs(final Collection<gcSocketDAO> configs) {
		if(configs == null) throw new NullPointerException();
		this.configs.clear();
		if(utils.notEmpty(configs))
			this.configs.addAll(configs);
	}



	@Override
	public void Start() {
		// ensure stopped
		if(this.running.get())
			this.Stop();
		// get configs
		final Set<gcSocketDAO> cfgs = new HashSet<gcSocketDAO>(this.configs);
		if(cfgs.isEmpty()) {
			this.log().fine("No socket server configs loaded");
			return;
		}
		// only start once
		if(!this.running.compareAndSet(false, true))
			return;
		// start socket servers
		synchronized(this.servers) {
			for(final gcSocketDAO cfg : cfgs) {
				if(!cfg.enabled) continue;
				try {
					final NetServer server = new NetServer(this, cfg);
					this.servers.add(server);
				} catch (UnknownHostException e) {
					this.log().severe("Failed to start socket server "+cfg.toString());
					this.log().trace(e);
				} catch (SocketException e) {
					if("Permission denied".equals(e.getMessage()))
						this.log().severe("Valid port numbers are >= 1024");
					this.log().trace(e);
				} catch (InterruptedException e) {
					this.log().trace(e);
					this.Stop();
					return;
				}
			}
		}
		// unexpected shutdown
		if(!this.running.get())
			this.Stop();
	}
	@Override
	public void Stop() {
		synchronized(this.servers) {
			this.running.set(false);
			final Iterator<NetServer> it = this.servers.iterator();
			while(it.hasNext()) {
				it.next().close();
				it.remove();
			}
			this.servers.clear();
		}
	}
	public void CloseAll() {
//		synchronized(this.channels) {
//			final Iterator<Channel> it = this.channels.iterator();
//			while(it.hasNext()) {
//				it.next().close().sync();
//				it.remove();
//			}
//			this.channels.clear();
//		}
	}



	@Override
	public void run() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isRunning() {
		return this.running.get();
	}



	// logger
	private volatile xLog _log = null;
	private volatile xLog _log_default = null;
	public xLog log() {
		if(this._log != null)
			return this._log;
		if(this._log_default == null)
			this._log_default = xLog.getRoot().get("netty");
		return this._log_default;
	}
	public void setLog(final xLog log) {
		this._log = log;
	}



}
