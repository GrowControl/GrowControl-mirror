package com.growcontrol.server.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

import com.growcontrol.common.net.JsonObjectDecoder;
import com.growcontrol.common.net.SocketState;
import com.poixson.commonapp.net.firewall.NetFirewall;
import com.poixson.commonjava.xLogger.xLog;


// one instance per NetServer
public class NetServerInitializer extends ChannelInitializer<SocketChannel> {

	protected final NetServer        server;
//	protected final NetServerHandler handler;
//	protected final NetConfig        config;

	// handlers
	protected static final Charset charset = Charset.forName("UTF8");
	protected static final StringDecoder strDecoder = new StringDecoder(charset);
	protected static final StringEncoder strEncoder = new StringEncoder(charset);



	public NetServerInitializer(final NetServer server) {
		this.server  = server;
//		this.handler = server.handler;
//		this.config  = server.config;
	}
//	public NetServerInitializer(final NetServer server,
//			final NetServerHandler handler, final NetConfig config) {
//		this.server  = server;
//		this.handler = handler;
//		this.config  = config;
//	}



	@Override
	protected void initChannel(final SocketChannel channel) throws Exception {
		// register new socket state
		final ServerSocketState stateDAO = new ServerSocketState(
				this.server,
				channel
		);
		stateDAO.setState(SocketState.PREAUTH);
		this.server.register(stateDAO);
		stateDAO.log();
		this.log(stateDAO).info("Accepted connection from: "+stateDAO.getStateKey());
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
		pipe.addLast(this.server.handler);
	}



	// logger
	public xLog log(final ServerSocketState stateDAO) {
		return stateDAO.log();
	}



}
