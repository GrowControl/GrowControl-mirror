package com.growcontrol.client.studio;

import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
//	public static final AtomicReference<gcStudio> studio = new AtomicReference<gcStudio>(null);



	public static void main(final String[] args) {
System.out.println("Studio OK");
//		final gcStudio studio = gcStudio.getStudio();
//		if (!Main.studio.compareAndSet(null, studio))
//			throw new RuntimeException("Studio instance already exists");
//		studio.setArgs(args);
//		studio.start();
//		// start main thread pool
//		xThreadPool_Main.Get().go();
	}



}
