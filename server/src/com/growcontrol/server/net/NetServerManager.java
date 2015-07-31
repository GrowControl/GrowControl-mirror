package com.growcontrol.server.net;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.growcontrol.common.netty.NetConfig;
import com.growcontrol.common.netty.NettyDetailedLogger;
import com.poixson.commonjava.xVars;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xCloseableMany;
import com.poixson.commonjava.Utils.xStartable;
import com.poixson.commonjava.Utils.threads.xThreadFactory;
import com.poixson.commonjava.xLogger.xLog;


public class NetServerManager implements xStartable, xCloseableMany {

	public static final boolean DETAILED_LOGGING = false;
	public static final int BACKLOG = 10;
	protected final boolean debug;

	// manager instance
	private static volatile NetServerManager instance = null;
	private static final Object instanceLock = new Object();
	protected volatile boolean running = false;

	// server instances
	protected final Map<String, NetServer> servers = new HashMap<String, NetServer>();
	// temporary storage of configs, used when starting servers
	protected final Set<NetConfig> tempConfigs = new HashSet<NetConfig>();

	// threads
	protected final EventLoopGroup bossGroup;
	protected final EventLoopGroup workGroup;

	// firewall
//	protected final NetFirewall firewall;

	// ssl
	protected final SslContext sslContext;

//	protected final NetHandler handler;



	public static NetServerManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new NetServerManager();
			}
		}
		return instance;
	}
	private NetServerManager() {
		Keeper.add(this);
		this.debug = (xVars.debug() && DETAILED_LOGGING);
		if(this.debug)
			NettyDetailedLogger.Install(this.log());
		// thread groups
		final int cores = Runtime.getRuntime().availableProcessors();
		this.bossGroup = new NioEventLoopGroup(1,     new xThreadFactory("netty-boss", true));
		this.workGroup = new NioEventLoopGroup(cores, new xThreadFactory("netty-work", true));
//TODO:
		// ssl
		this.sslContext = null;
//TODO:
		// firewall
//		this.firewall = new NetFirewall();


		// protocol handler
//		this.handler = new NetServerHandler(this);
//		this.bootstrap.childHandler(
//				new ServerSocketChannelInitializer(this.log(), this.sslContext)
//		);


	}



	@Override
	public void Start() {
		this.running = true;
		final Map<String, NetConfig> cfgs = new HashMap<String, NetConfig>();
		synchronized(this.tempConfigs) {
			for(final NetConfig cfg : this.tempConfigs) {
				final String key = cfg.toString();
				if(utils.isEmpty(key))    continue;
				if(cfgs.containsKey(key)) continue;
				cfgs.put(key, cfg);
			}
		}
		synchronized(this.servers) {
			// stop removed servers
			if(!this.servers.isEmpty()) {
				final Iterator<Entry<String, NetServer>> it = this.servers.entrySet().iterator();
				while(it.hasNext()) {
					final Entry<String, NetServer> entry = it.next();
					// stop no longer needed servers
					final NetConfig cfg = cfgs.get(entry.getKey());
					if(cfg == null || !cfg.enabled) {
						final NetServer server = entry.getValue();
						utils.safeClose(server);
						it.remove();
					}
				}
			}
			// start new servers
			if(cfgs.isEmpty()) {
				this.log().warning("No socket server configs loaded.");
			} else {
				this.log().info("Starting socket servers..");
				for(final Entry<String, NetConfig> entry : cfgs.entrySet()) {
					final String key = entry.getKey();
					final NetConfig cfg = entry.getValue();
					// server already exists
					if(this.servers.containsKey(key))
						continue;
					// not enabled
					if(!cfg.enabled)
						continue;
					try {
						final NetServer server = new NetServer(cfg);
						this.servers.put(key, server);
					} catch (UnknownHostException e) {
						this.log().severe("Failed to start socket server "+key);
						this.log().trace(e);
						continue;
					} catch (SocketException e) {
						if("Permission denied".equals(e.getMessage()))
							this.log().severe("Valid port numbers are >= 1024 for non-root users");
						this.log().trace(e);
					} catch (InterruptedException e) {
						this.log().severe("Failed to start socket server "+key);
						this.log().trace(e);
						this.Stop();
						return;
					}
				}
			}
		}
		// unexpected stop
		if(!this.running)
			this.Stop();
	}
	@Override
	public void Stop() {
		this.log().info("Stopping socket servers..");
		synchronized(this.servers) {
this.log().severe("SERVER COUNT: "+Integer.toString(this.servers.size()));
			this.running = false;
			final Iterator<NetServer> it = this.servers.values().iterator();
			while(it.hasNext()) {
				it.next().close();
				it.remove();
			}
this.log().severe("SERVER COUNT: "+Integer.toString(this.servers.size()));
			this.servers.clear();
this.log().severe("SERVER COUNT: "+Integer.toString(this.servers.size()));
		}
	}



	@Override
	public void run() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isRunning() {
		return this.running;
	}



	// close all socket connections
	@Override
	public void CloseAll() {
		synchronized(this.servers) {
			if(this.servers.isEmpty())
				return;
			this.log().info("Closing sockets..");
			for(final NetServer server : this.servers.values()) {
				server.CloseAll();
			}
		}
	}
	@Override
	public void close() throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isClosed() {
		return !this.running;
	}



	public void setConfigs(final Collection<NetConfig> configs) {
		if(configs == null) throw new NullPointerException("configs argument is required!");
this.log().severe("CONFIGS: "+Integer.toString(configs.size()));
		synchronized(this.tempConfigs) {
			this.tempConfigs.clear();
			if(!configs.isEmpty())
				this.tempConfigs.addAll(configs);
		}
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot();
		return this._log;
	}



}
