package com.growcontrol.server.net;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLException;

import com.growcontrol.server.configs.NetServerConfig;
import com.poixson.commonapp.net.firewall.NetFirewall;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utilsThread;
import com.poixson.commonjava.Utils.xCloseableMany;
import com.poixson.commonjava.Utils.threads.xThreadFactory;
import com.poixson.commonjava.xLogger.xLog;


public class NetServerManager implements xCloseableMany {

	public static final boolean DETAILED_LOG = false;

	// manager instance
	private static volatile NetServerManager instance = null;
	private static final Object instanceLock = new Object();

	// servers
	protected final Map<String, NetServer> netServers = new ConcurrentHashMap<String, NetServer>();

	// thread groups
	protected final EventLoopGroup bossGroup;
	protected final EventLoopGroup workGroup;

	// firewall
	protected final NetFirewall firewall;

	// ssl
	protected final SslContext sslContext;



	public static NetServer get(final NetServerConfig config) {
		try {
			return get().getServer(config);
		} catch (UnknownHostException e) {
			log().trace(e);
			return null;
		} catch (InterruptedException e) {
			log().trace(e);
			return null;
		}
	}
	public static NetServerManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				try {
					if(instance == null)
						instance = new NetServerManager();
				} catch (SSLException e) {
					instance = null;
					log().trace(e);
					return null;
				} catch (CertificateException e) {
					instance = null;
					log().trace(e);
					return null;
				}
			}
		}
		return instance;
	}
	protected NetServerManager() throws SSLException, CertificateException {
		Keeper.add(this);
		// thread groups
		final int cores = Runtime.getRuntime().availableProcessors();
		this.bossGroup = new NioEventLoopGroup(1,     new xThreadFactory("netty-boss", true));
		this.workGroup = new NioEventLoopGroup(cores, new xThreadFactory("netty-work", true));
		// this method is bad for servers
		//   bossGroup = Executors.newCachedThreadPool();
		//   workGroup = Executors.newCachedThreadPool();
		// this method is used in netty examples
		//   bossGroup = new NioEventLoopGroup();
		//   workGroup = new NioEventLoopGroup();
		// ssl
		final SelfSignedCertificate cert = new SelfSignedCertificate();
		this.sslContext = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
		// firewall
		this.firewall = new NetFirewall();
//TODO:
//		// packet handler
//		this.handler = new NetServerHandler(this);
//		this.bootstrap.childHandler(
//				new ServerSocketChannelInitializer(this.log(), this.sslContext)
//		);


//		this.debug = (xVars.debug() && DETAILED_LOGGING);
//		if(this.debug)
//			NettyDetailedLogger.Install(this.log());
	}



	public NetServer getServer(final NetServerConfig config) throws UnknownHostException, InterruptedException {
		if(config == null) throw new NullPointerException("config argument is required!");
		final String key = config.toString();
		// get existing server
		{
			final NetServer server = this.netServers.get(key);
			if(server != null && !server.isClosed())
				return server;
		}
		// new server
		synchronized(this.netServers){
			// check once more
			{
				final NetServer server = this.netServers.get(key);
				if(server != null) {
					if(server.isClosed())
						this.ClearClosed();
					else
						return server;
				}
			}
			// new server
			{
				final NetServer server = new NetServer(config);
				this.netServers.put(key, server);
				return server;
			}
		}
	}



//	@Override
//	public void Start() {
//		if(!this.running.compareAndSet(false, true))
//			return;

//		final Map<String, NetConfig> cfgs = new HashMap<String, NetConfig>();
//		synchronized(this.tempConfigs) {
//			for(final NetConfig cfg : this.tempConfigs) {
//				final String key = cfg.toString();
//				if(utils.isEmpty(key))    continue;
//				if(cfgs.containsKey(key)) continue;
//				cfgs.put(key, cfg);
//			}
//		}
//		synchronized(this.servers) {
//			// stop removed servers
//			if(!this.servers.isEmpty()) {
//				final Iterator<Entry<String, NetServer>> it = this.servers.entrySet().iterator();
//				while(it.hasNext()) {
//					final Entry<String, NetServer> entry = it.next();
//					// stop no longer needed servers
//					final NetConfig cfg = cfgs.get(entry.getKey());
//					if(cfg == null || !cfg.enabled) {
//						final NetServer server = entry.getValue();
//						utils.safeClose(server);
//						it.remove();
//					}
//				}
//			}
//			// start new servers
//			if(cfgs.isEmpty()) {
//				this.log().warning("No socket server configs loaded.");
//			} else {
//				this.log().info("Starting socket servers..");
//				for(final Entry<String, NetConfig> entry : cfgs.entrySet()) {
//					final String key = entry.getKey();
//					final NetConfig cfg = entry.getValue();
//					// server already exists
//					if(this.servers.containsKey(key))
//						continue;
//					// not enabled
//					if(!cfg.enabled)
//						continue;
//					try {
//						final NetServer server = new NetServer(cfg);
//						this.servers.put(key, server);
//					} catch (UnknownHostException e) {
//						this.log().severe("Failed to start socket server "+key);
//						this.log().trace(e);
//						continue;
//					} catch (SocketException e) {
//						if("Permission denied".equals(e.getMessage()))
//							this.log().severe("Valid port numbers are >= 1024 for non-root users");
//						this.log().trace(e);
//					} catch (InterruptedException e) {
//						this.log().severe("Failed to start socket server "+key);
//						this.log().trace(e);
//						this.Stop();
//						return;
//					}
//				}
//			}
//		}
//		// unexpected stop
//		if(!this.running.get())
//			this.Stop();
//	}



	@Override
	public boolean isClosed() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void close() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void CloseAll() {
		if(this.netServers.size() == 0)
			return;
		log().info("Closing socket servers..");
		int serversCount = 0;
		int socketsCount = 0;
		synchronized(this.netServers) {
			final Collection<NetServer> servers = this.netServers.values();
			// close servers
			{
				final Set<ChannelFuture> futureCloses = new HashSet<ChannelFuture>();
				final Iterator<NetServer> it = servers.iterator();
				while(it.hasNext()) {
					final NetServer server = it.next();
					if(!server.isClosed())
						serversCount++;
					futureCloses.add(
							server.closeSoon()
					);
				}
				// wait for servers to stop listening
				for(final ChannelFuture future : futureCloses) {
					try {
						future.sync();
					} catch (InterruptedException e) {
						log().trace(e);
						break;
					}
				}
			}
			// wait a moment
			utilsThread.Sleep(50L);
			// close sockets
			{
				log().info("Closing sockets..");
				final Iterator<NetServer> it = servers.iterator();
				while(it.hasNext()) {
					final NetServer server = it.next();
					socketsCount += server.getSocketsCount();
					server.CloseAll();
				}
			}
			this.ClearClosed();
		}
		if(serversCount > 0)
			log().info("Closed "+Integer.toString(serversCount)+
					" socket servers, and "+Integer.toString(socketsCount)+" sockets");
	}
	public void ClearClosed() {
		if(this.netServers.size() == 0)
			return;
		synchronized(this.netServers) {
			final Iterator<NetServer> it = this.netServers.values().iterator();
			while(it.hasNext()) {
				final NetServer server = it.next();
				if(server.isClosed())
					this.netServers.remove(server.getServerKey());
			}
		}
	}



	public NetFirewall getFirewall() {
		return this.firewall;
	}



	// logger
	private static volatile xLog _log = null;
	public static xLog log() {
		if(_log == null)
			_log = xLog.getRoot("NET");
		return _log;
	}



}
