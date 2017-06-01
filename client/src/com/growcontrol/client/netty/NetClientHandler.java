/*
package com.growcontrol.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.growcontrol.common.packets.PacketState;
import com.poixson.commonjava.xLogger.xLog;


public class NetClientHandler extends SimpleChannelInboundHandler<String> {

	private final NetClient client;
	private final ClientSocketState socketState;



	public NetClientHandler(final NetClient client,
			ClientSocketState socketState) {
		if(client      == null) throw new NullPointerException("client argument is required!");
		if(socketState == null) throw new NullPointerException("socketState argument is required!");
		this.client = client;
		this.socketState = socketState;
	}



	@Override
	public void channelInactive(final ChannelHandlerContext context) throws Exception {
		super.channelInactive(context);
		// unregister closed socket
		this.client.unregister(this.socketState);
		this.log().info("Connection closed");
	}



	@Override
	public void channelRead0(final ChannelHandlerContext context, final String msg) throws Exception {
this.log().publish("");
this.log().publish("CLIENT RECEIVED PACKET:");
this.log().publish("==>"+msg+"<==");
this.log().publish("");
//this.log().publish("==> "+msg.toString(Charset.forName("utf8"))+" <==");
//this.log().publish();

		final PacketState packetState = this.socketState.getPacketState();
		packetState.handle(msg);
	}




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
*/
