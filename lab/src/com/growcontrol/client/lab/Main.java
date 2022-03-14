package com.growcontrol.client.lab;

import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
//	public static final AtomicReference<gcLab> lab = new AtomicReference<gcLab>(null);



	public static void main(final String[] args) {
System.out.println("Lab OK");
//		final gcLab lab = gcLab.getLab();
//		if (!Main.lab.compareAndSet(null, lab))
//			throw new RuntimeException("Lab instance already exists");
//		lab.setArgs(args);
//		lab.start();
//		// start main thread pool
//		xThreadPool_Main.Get().go();
	}



}
