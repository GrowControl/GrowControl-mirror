package com.growcontrol.client.studio;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
	public static final AtomicReference<gcStudio> studio = new AtomicReference<gcStudio>(null);



	public static void main(final String[] args) {
		final gcStudio studio = gcStudio.getStudio();
		if (!Main.studio.compareAndSet(null, studio))
			throw new RuntimeException("Studio instance already exists");
		studio.setArgs(args);
		studio.start();
		// start main thread pool
		xThreadPool_Main.Get().go();
	}



}
