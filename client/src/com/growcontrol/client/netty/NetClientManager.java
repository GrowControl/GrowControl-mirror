package com.growcontrol.client.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.net.ssl.SSLException;

import com.growcontrol.client.configs.SavedProfileConfig;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utilsThread;
import com.poixson.commonjava.Utils.xCloseableMany;
import com.poixson.commonjava.Utils.xTimeU;
import com.poixson.commonjava.Utils.threads.xThreadFactory;
import com.poixson.commonjava.xLogger.xLog;


/*
NetClientManager
  -> NetClient
     -> ClientSocketState
        -> NetClientHandler
           PacketState
 */
public class NetClientManager implements xCloseableMany {
	private static final String LOG_NAME = "NET";

	public static final boolean DETAILED_LOG = false;

	// manager instance
	private static volatile NetClientManager instance = null;
	private static final Object instanceLock = new Object();

	// clients
	protected final ConcurrentMap<String, NetClient> netClients =
			new ConcurrentHashMap<String, NetClient>();

	// thread group
	protected final EventLoopGroup workGroup;

	// ssl
	protected final SslContext sslContext;



	public static NetClient get(final SavedProfileConfig profile) {
		if(profile == null) throw new NullPointerException("profile argument is required!");
		try {
			return get().getClient(profile);
		} catch (UnknownHostException e) {
			log().trace(e);
			return null;
		} catch (InterruptedException e) {
			log().trace(e);
			return null;
		}
	}
	public static NetClientManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				try {
					if(instance == null)
						instance = new NetClientManager();
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
	protected NetClientManager() throws SSLException, CertificateException {
		Keeper.add(this);
		// thread group
		final int cores = Runtime.getRuntime().availableProcessors();
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
//TODO:
//		// ssl packet handler
//		this.bootstrap.childHandler(
//				new ServerSocketChannelInitializer(this.log(), this.sslContext)
//		);


//		this.debug = (xVars.debug() && DETAILED_LOGGING);
//		if(this.debug)
//			NettyDetailedLogger.Install(this.log());
	}



	public NetClient getClient(final SavedProfileConfig profile)
			throws UnknownHostException, InterruptedException {
		if(profile == null) throw new NullPointerException("profile argument is required!");
		final String key = profile.toString();
		// get existing client
		{
			final NetClient client = this.netClients.get(key);
			if(client != null && !client.isClosed())
				return client;
		}
		// new client
		synchronized(this.netClients){
			// check once more
			{
				final NetClient client = this.netClients.get(key);
				if(client != null) {
					if(client.isClosed())
						this.ClearClosed();
					else
						return client;
				}
			}
			// new client
			{
				final NetClient client = new NetClient(profile);
				this.netClients.put(key, client);
				return client;
			}
		}
	}



	@Override
	public boolean isClosed() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void close() throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public void CloseAll() {
		if(this.netClients.size() == 0)
			return;
		log().info("Closing socket connections..");
		int clientsCount = 0;
		int socketsCount = 0;
		synchronized(this.netClients) {
			final Collection<NetClient> clients = this.netClients.values();
			// close clients
			{
				final Set<ChannelFuture> futureCloses = new HashSet<ChannelFuture>();
				final Iterator<NetClient> it = clients.iterator();
				while(it.hasNext()) {
					final NetClient client = it.next();
					if(!client.isClosed())
						clientsCount++;
					futureCloses.add(
							client.closeSoon()
					);
				}
				// wait for clients to stop connections
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
				final Iterator<NetClient> it = clients.iterator();
				while(it.hasNext()) {
					final NetClient client = it.next();
					socketsCount += client.getSocketsCount();
					client.CloseAll();
				}
			}
			this.ClearClosed();
		}
		if(clientsCount > 0) {
			log().info(
					(new StringBuilder())
					.append("Closed ")
					.append(clientsCount)
					.append(" socket clients, and ")
					.append(socketsCount)
					.append(" sockets")
					.toString()
			);
		}
		// stop thread pool
		this.workGroup.shutdownGracefully(
				500L,
				800L,
				xTimeU.MS
		);
	}
	public void ClearClosed() {
		if(this.netClients.size() == 0)
			return;
		synchronized(this.netClients) {
			final Iterator<NetClient> it = this.netClients.values().iterator();
			while(it.hasNext()) {
				final NetClient client = it.next();
				if(client.isClosed())
					this.netClients.remove(client.getClientKey());
			}
		}
	}



	// logger
	private static volatile xLog _log = null;
	public static xLog log() {
		if(_log == null)
			_log = xLog.getRoot(LOG_NAME);
		return _log;
	}



}
