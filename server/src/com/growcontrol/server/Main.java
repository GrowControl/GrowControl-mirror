package com.growcontrol.server;

import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
//	public static final AtomicReference<gcServer> server = new AtomicReference<gcServer>(null);



	public static void main(final String[] args) {
System.out.println("GOOD");
//		final gcServer server = gcServer.getServer();
//		if (!Main.server.compareAndSet(null, server))
//			throw new RuntimeException("Server instance already exists");
//		server.setArgs(args);
//		server.start();
//		// start main thread pool
//		xThreadPool_Main.Get().go();
	}



}
