package com.growcontrol.server;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
	public static final AtomicReference<gcServer> server = new AtomicReference<gcServer>(null);



	public static void main(final String[] args) {
		final gcServer server = gcServer.getServer();
		if (!Main.server.compareAndSet(null, server))
			throw new RuntimeException("Server instance already exists");
		server.setArgs(args);
		server.start();
		// start main thread pool
		xThreadPool_Main.Get().go();
	}



}
