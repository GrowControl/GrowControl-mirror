package com.growcontrol.server.net;

import io.netty.channel.Channel;

import java.net.UnknownHostException;

import com.growcontrol.server.configs.gcSocketDAO;
import com.poixson.commonjava.Utils.xCloseable;
import com.poixson.commonjava.xLogger.xLog;


public class NetServer implements xCloseable {

	public final NetManager manager;
	public final gcSocketDAO config;

	protected final Channel serverChannel;
	protected volatile boolean closed = false;



	public NetServer(final NetManager manager, final gcSocketDAO config)
			throws UnknownHostException, InterruptedException {
		if(manager == null) throw new NullPointerException();
		if(config  == null) throw new NullPointerException();
		if(!config.enabled) throw new UnsupportedOperationException("This socket server is disabled");
		this.manager = manager;
		this.config = config;
		this.log().finest("Starting socket server.. "+this.config.toString());
		// start listening
		this.serverChannel = manager.bootstrap.bind(
				this.config.getInetAddress(),
				this.config.port
		).sync().channel();
		this.log().info("Socket server listening on "+this.config.toString());
	}



	@Override
	public void close() {
		this.closed = true;
		try {
			this.serverChannel.close().sync();
		} catch (InterruptedException e) {
			this.manager.log().trace(e);
		}
	}
	@Override
	public boolean isClosed() {
		return this.closed;
	}



	// logger
	public xLog log() {
		return this.manager.log();
	}



}
