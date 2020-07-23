package com.growcontrol.client.lab;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
	public static final AtomicReference<gcLab> lab = new AtomicReference<gcLab>(null);



	public static void main(final String[] args) {
		final gcLab lab = gcLab.getLab();
		if (!Main.lab.compareAndSet(null, lab))
			throw new RuntimeException("Lab instance already exists");
		lab.setArgs(args);
		lab.start();
		// start main thread pool
		xThreadPool_Main.Get().go();
	}



}
