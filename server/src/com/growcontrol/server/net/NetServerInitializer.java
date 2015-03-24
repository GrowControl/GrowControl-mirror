package com.growcontrol.server.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

import com.growcontrol.common.netty.JsonObjectDecoder;
import com.growcontrol.common.netty.NetConfig;
import com.poixson.commonjava.xLogger.xLog;


public class NetServerInitializer extends ChannelInitializer<SocketChannel> {

	protected final NetServer        server;
//	protected final NetServerHandler handler;
	protected final NetConfig        config;

	// handlers
	protected static final Charset charset = Charset.forName("UTF8");
	protected static final StringDecoder strDecoder = new StringDecoder(charset);
	protected static final StringEncoder strEncoder = new StringEncoder(charset);



	public NetServerInitializer(final NetServer server) {
		this.server  = server;
//		this.handler = server.handler;
		this.config  = server.config;
	}
//	public NetServerInitializer(final NetServer server,
//			final NetServerHandler handler, final NetConfig config) {
//		this.server  = server;
//		this.handler = handler;
//		this.config  = config;
//	}



	@Override
	protected void initChannel(final SocketChannel channel) throws Exception {
		final NetStateDAO dao = new NetStateDAO(this.server, channel);
		final ChannelPipeline pipe = channel.pipeline();
		if(this.config.ssl) {
//TODO:
//			if(this.manager.sslContext != null) {
//			pipe.addLast(this.sslContext.newHandler(ch.alloc()));
		}
		// check firewall
		if(!this.server.register(dao)) {
			this.log().warning("Firewall blocked connection from: "+channel.remoteAddress().toString());
			channel.close().sync();
		}
		pipe.addLast(new JsonObjectDecoder());
		pipe.addLast(strDecoder);
		pipe.addLast(strEncoder);
		pipe.addLast(dao.handler);
//		pipe.addLast(new NetServerHandler(this.server, channel));
//		pipe.addLast(this.handler);
	}



	// logger
	public xLog log() {
		return this.server.log();
	}



}
