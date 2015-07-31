package com.growcontrol.server.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.commonjava.xLogger.xLog;


public class NetServerHandler extends SimpleChannelInboundHandler<String> {

	protected static final AtomicInteger count = new AtomicInteger(0);

	protected final int id;

	protected final NetServer server;
	protected final NetStateDAO dao;



	public NetServerHandler(final NetStateDAO dao) {
		if(dao == null) throw new NullPointerException("dao argument is required!");
		this.dao = dao;
		this.server = dao.server;
		this.id = count.incrementAndGet();
	}



	// socket connected
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		final Channel channel = ctx.channel();
		// ensure channel matches
		if(!channel.equals(this.dao.channel)) {
			this.log().severe("Channel doesn't match!");
			throw new RuntimeException();
		}
		this.log().info("Accepted connection from: "+channel.remoteAddress().toString());
//		ctx.write("Welcome!\r\n");
//		ctx.flush();
//		this.log().publish("===> ACTIVE");
	}
	// socket disconnected
	@Override
	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		final Channel channel = ctx.channel();
//		this.server.unregister(channel);
		this.log().info("Connection closed: "+channel.remoteAddress().toString());
	}



//	@Override
//	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//		ctx.fireChannelRegistered();
//		this.log().publish("===> REGISTERED");
//	}
//	@Override
//	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//		ctx.fireChannelUnregistered();
//		this.log().publish("===> UNREGISTERED");
//	}



	@Override
	public void channelRead0(final ChannelHandlerContext ctx,
			final String msg) throws Exception {
		this.log().publish("==> "+msg+" <==");
//TODO:

//		this.log().publish();
//		this.log().publish("CLASS NAME: "+msg.getClass().getName());
//		this.log().publish("==> "+msg.toString(Charset.forName("utf8"))+" <==");
//		this.log().publish();

//		if (msg instanceof HttpRequest) {
//		HttpRequest req = (HttpRequest) msg;
//		if (HttpHeaders.is100ContinueExpected(req))
//			ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
//		boolean keepAlive = HttpHeaders.isKeepAlive(req);
//		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
//		response.headers().set(CONTENT_TYPE, "text/plain");
//		response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
//		if (!keepAlive) {
//			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//		} else {
//			response.headers().set(CONNECTION, Values.KEEP_ALIVE);
//			ctx.write(response);
//		}

	}
	@Override
	public void channelReadComplete(final ChannelHandlerContext ctx) {
		ctx.flush();
	}














	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		// IOException
		if("Connection reset by peer".equals(cause.getMessage())) {
			this.log().warning("Connection reset by peer "+ctx.toString());
			return;
		}
		this.log().trace(cause);
		ctx.close();
//		String msg = "";
//		try {
//			final Channel ch = ctx.channel();
//			msg += " - " +ch.localAddress().toString();
//			msg += " <= "+ch.remoteAddress().toString();
//		} catch (Exception ignore) {}
//		this.log.warning(cause.getMessage()+msg);
//		ctx.fireExceptionCaught(cause);
	}



	// logger
	public xLog log() {
		return this.server.log();
	}



}
