package com.growcontrol.server.events;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

import com.poixson.logger.xLog;
import com.poixson.tools.xTime;
import com.poixson.tools.abstractions.xStartable;
import com.poixson.tools.scheduler.xScheduler;
import com.poixson.tools.scheduler.xSchedulerTask;
import com.poixson.tools.scheduler.trigger.xSchedTrigger_Interval;


public class gcTickHandler implements xStartable, Runnable {
	public static final boolean DEBUG = false;

	public static final long DEFAULT_TICK_INTERVAL = xTime.ParseToLong("2s");

	protected final AtomicLong next_index = new AtomicLong(0L);

	protected final xScheduler scheduler;

	protected final CopyOnWriteArraySet<gcTickListener> listeners = new CopyOnWriteArraySet<gcTickListener>();



	public gcTickHandler() {
		final long interval = DEFAULT_TICK_INTERVAL;
		this.scheduler = new xScheduler("ticker");
		this.scheduler.add(
			(new xSchedulerTask(this, "Ticker"))
				.add(new xSchedTrigger_Interval(interval))
		);
	}



	public void register(final gcTickListener listener) {
		this.listeners.add(listener);
	}



	@Override
	public void start() {
		this.scheduler.start();
	}
	@Override
	public void stop() {
		this.scheduler.stop();
	}



	@Override
	public void run() {
		final long index = this.next_index.incrementAndGet();
		if (DEBUG) this.log().finest("Tick %d", Long.valueOf(index));
		final gcTickEvent event = new gcTickEvent(index);
		for (final gcTickListener listener : this.listeners)
			listener.onTick(event);
	}



	@Override
	public boolean isRunning() {
		return ! this.isStopping();
	}
	@Override
	public boolean isStopping() {
		return this.scheduler.isStopping();
	}



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return this.scheduler.log();
	}



}
