package com.growcontrol.server.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

import com.growcontrol.common.netty.JsonObjectDecoder;
import com.growcontrol.common.netty.SocketState.SessionState;
import com.growcontrol.common.packets.handshake.Packet_0_Hello;
import com.poixson.commonapp.net.firewall.NetFirewall;
import com.poixson.commonjava.xLogger.xLog;


// one instance per NetServer
public class NetServerInitializer extends ChannelInitializer<SocketChannel> {

	protected final NetServer server;

	// handlers
	protected static final Charset charset = Charset.forName("UTF8");
	protected static final StringDecoder strDecoder = new StringDecoder(charset);
	protected static final StringEncoder strEncoder = new StringEncoder(charset);



	public NetServerInitializer(final NetServer server) {
		this.server  = server;
	}



	@Override
	protected void initChannel(final SocketChannel channel) throws Exception {
		// register new socket state
		final ServerSocketState socketState = new ServerSocketState(
				this.server,
				channel
		);
		socketState.setSessionState(SessionState.PREAUTH);
		this.server.register(socketState);
		socketState.log();
		this.log(socketState).info("Accepted connection from: "+socketState.getStateKey());
//TODO:
//		if(this.config.ssl) {
//		if(this.manager.sslContext != null) {
//			pipe.addLast(this.sslContext.newHandler(ch.alloc()));
//		}
		// check firewall
		{
			final xLog log = NetFirewall.log();
			final NetFirewall firewall = this.server.getFirewall();
			final Boolean result = firewall.check(
					channel.localAddress(),
					channel.remoteAddress()
			);
			// default firewall action
			if(result == null) {
				log.warning("No matching firewall rule for connection: "+channel.remoteAddress().toString()+" to "+channel.localAddress().toString());
			} else
			// firewall allowed
			if(result.booleanValue()) {
				log.info("Firewall allowed connection from: "+channel.remoteAddress().toString()+" to "+channel.localAddress().toString());
			// firewall blocked
			} else {
				log.warning("Firewall blocked connection from: "+channel.remoteAddress().toString()+" to "+channel.localAddress().toString());
				channel.close().sync();
				return;
			}
		}
		// setup pipeline
		final ChannelPipeline pipe = channel.pipeline();
		pipe.addLast(new JsonObjectDecoder());
		pipe.addLast(strDecoder);
		pipe.addLast(strEncoder);
		pipe.addLast(new JsonObjectDecoder());
		pipe.addLast(socketState.getHandler());
		// listen for hello packet
		Packet_0_Hello.init(
				socketState.getPacketState()
		);
	}



	// logger
	public xLog log(final ServerSocketState socketState) {
		return socketState.log();
	}



}
