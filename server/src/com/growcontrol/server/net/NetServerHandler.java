package com.growcontrol.server.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.growcontrol.common.packets.PacketState;
import com.poixson.commonjava.xLogger.xLog;


// one instance per ServerSocketState
public class NetServerHandler extends SimpleChannelInboundHandler<String> {

	private final NetServer server;
	private final ServerSocketState socketState;



	public NetServerHandler(final NetServer server, final ServerSocketState socketState) {
		if(server == null) throw new NullPointerException("server argument is required!");
		this.server = server;
		this.socketState = socketState;
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
	public void channelInactive(final ChannelHandlerContext context) throws Exception {
		super.channelInactive(context);
		// unregister closed socket
		this.server.unregister(this.socketState);
		this.log().info("Connection closed");
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
this.log().publish("");
this.log().publish("SERVER RECEIVED PACKET:");
this.log().publish("==>"+msg+"<==");
this.log().publish("");
//this.log().publish("==> "+msg.toString(Charset.forName("utf8"))+" <==");
//this.log().publish();

		final PacketState packetState = this.socketState.getPacketState();
		packetState.handle(msg);
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
	public void exceptionCaught(final ChannelHandlerContext context,
			final Throwable cause) throws Exception {
		// IOException
		if("Connection reset by peer".equals(cause.getMessage())) {
			this.log().warning("Connection reset by peer "+context.toString());
			return;
		}
		this.log().trace(cause);
		context.close()
			.sync();
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
		return this.socketState.log();
	}



}
