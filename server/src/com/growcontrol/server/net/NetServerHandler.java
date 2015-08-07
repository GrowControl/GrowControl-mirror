package com.growcontrol.server.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.poixson.commonjava.xLogger.xLog;


// one instance per ServerSocketState
public class NetServerHandler extends SimpleChannelInboundHandler<String> {

	protected final NetServer server;



	public NetServerHandler(final NetServer server) {
		if(server == null) throw new NullPointerException("server argument is required!");
		this.server = server;
	}



	public ServerSocketState getServerSocketState(final Channel channel) {
		return this.server.getServerSocketState(channel);
	}



//	@Override
//	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);
//		final Channel channel = ctx.channel();
//this.log().info("Accepted connection from: "+channel.remoteAddress().toString());
//ctx.write("Welcome!\r\n");
//ctx.flush();
//this.log().publish("===> ACTIVE");
//	}
	@Override
	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		final Channel channel = ctx.channel();
		final ServerSocketState socketState = this.getServerSocketState(channel);
		if(socketState == null) throw new RuntimeException();
		this.server.unregister(socketState);
		this.log(socketState).info("Connection closed");
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
	public void channelRead0(final ChannelHandlerContext context, final String msg) throws Exception {
xLog.getRoot("NET").publish("");
xLog.getRoot("NET").publish("==> "+msg+" <==");
xLog.getRoot("NET").publish("CLASS NAME: "+msg.getClass().getName());
xLog.getRoot("NET").publish("");
//this.log().publish("==> "+msg+" <==");
//this.log().publish();
//this.log().publish("CLASS NAME: "+msg.getClass().getName());
//this.log().publish("==> "+msg.toString(Charset.forName("utf8"))+" <==");
//this.log().publish();
	}
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
//	@Override
//	public void channelReadComplete(final ChannelHandlerContext ctx) {
//		ctx.flush();
//	}



	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		// IOException
		if("Connection reset by peer".equals(cause.getMessage())) {
xLog.getRoot("NET").warning("Connection reset by peer "+ctx.toString());
			return;
		}
xLog.getRoot("NET").trace(cause);
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
	public xLog log(final Channel channel) {
		final ServerSocketState socketState = this.server.getServerSocketState(channel);
		return this.log(socketState);
	}
	public xLog log(final ServerSocketState socketState) {
		return socketState.log();
	}



}
