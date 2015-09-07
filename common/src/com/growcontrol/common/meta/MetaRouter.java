package com.growcontrol.common.meta;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.EventListener.xEventData;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xRunnable;
import com.poixson.commonjava.Utils.threads.xThreadPool;


public class MetaRouter extends xHandler {

	public static final int ADDRESS_LENGTH = 8;

	private static volatile MetaRouter instance = null;
	private static final Object lock = new Object();

	// listener addresses
	protected static final ConcurrentMap<MetaAddress, MetaListener> known =
			new ConcurrentHashMap<MetaAddress, MetaListener>();



	public static MetaRouter get() {
		if(instance == null) {
			synchronized(lock) {
				if(instance == null)
					instance = new MetaRouter();
			}
		}
		return instance;
	}
	public MetaRouter() {
		Keeper.add(this);
	}



	public String register(final String address, final MetaListener listener) {
		return this.register(
			MetaAddress.get(address),
			listener
		);
	}
	public String register(final MetaAddress address, final MetaListener listener) {
		final MetaAddress addr =
			address == null
				? MetaAddress.getRandom()
				: address;
		if(known.containsKey(addr))
			log().fine("Replacing meta event listener: "+addr.hash);
		else
			log().finer("Registered meta address: "+addr.hash);
		known.put(addr, listener);
		return addr.hash;
	}



	public MetaAddress[] getKnown() {
		final MetaAddress[] addresses;
		synchronized(known) {
			addresses =
				known.isEmpty()
				? new MetaAddress[0]
				: known.keySet().toArray(new MetaAddress[0]);
		}
		return addresses;
	}



	public void route(final String address, final String value) {
		if(utils.isEmpty(address)) throw new NullPointerException("address argument is required!");
		if(utils.isEmpty(value))   throw new NullPointerException("value argument is required!");
		this.route(
			MetaAddress.get(address),
			MetaType.get(value)
		);
	}
	public void route(final MetaAddress address, final MetaType value) {
		if(address == null) throw new NullPointerException("address argument is required!");
		if(value   == null) throw new NullPointerException("meta argument is required!");
		this.route(
			new MetaEvent(
				address,
				value
			)
		);
	}
	public boolean route(final MetaEvent event) {
		if(event == null) throw new NullPointerException("event argument is required!");
		return this.route( (xThreadPool) null, event );
	}
	public boolean route(final xThreadPool pool, final MetaEvent event) {
		if(event == null) throw new NullPointerException("event argument is required!");
		// get address listener
		final MetaListener listener = this.getAddressListener(event.destination);
		if(listener == null) {
			log().warning("No destination found for meta event: "+event.toString());
			return false;
		}
		// trigger the event
		final xThreadPool p =
			pool == null
			? xThreadPool.getMainPool()
			: pool;
		p.runLater(
			this.getRunnable(listener, event)
		);
		return true;
	}



	// get listener by address
	public MetaListener getAddressListener(final MetaAddress address) {
		if(address == null) throw new NullPointerException("address argument is required!");
		return known.get(address);
	}



	// xRunnableEvent
	protected xRunnable getRunnable(final MetaListener listener, final MetaEvent event) {
		return new xRunnable("MetaEvent-"+event.destination+"-"+event.value.toString()) {
			private volatile MetaListener listener;
			private volatile MetaEvent event;
			public xRunnable init(final MetaListener listener, final MetaEvent event) {
				this.listener = listener;
				this.event = event;
				return this;
			}
			@Override
			public void run() {
				if(this.event == null) throw new NullPointerException("event variable cannot be null!");
				doRoute(this.listener, this.event);
			}
		}.init(listener, event);
	}



	public void doRoute(final MetaListener listener, final MetaEvent event) {
		if(listener == null) throw new NullPointerException("listener argument is required!");
		if(event == null)    throw new NullPointerException("event argument is required!");
		listener.onMetaEvent(event);
	}



	@Override
	public void unregisterAll() {
		this.listeners.clear();
	}



	// ==================================================
	// disable these functions



	@Override
	public void register(final xListener listener) {
		throw new UnsupportedOperationException("Must supply MetaAddress argument "+
				"when registering a MetaListener");
	}
	@Override
	public void unregister(final xListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void triggerNow(final xEventData event) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void triggerNow(final xEventData event, final Priority onlyPriority) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void triggerNow(final xThreadPool pool, final xEventData event, final Priority onlyPriority) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void triggerLater(final xEventData event) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void triggerLater(final xEventData event, final Priority onlyPriority) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void triggerLater(final xThreadPool pool, final xEventData event, final Priority onlyPriority) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected xRunnable getRunnable(final xEventData event, final Priority onlyPriority) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doTrigger(final xEventData event, final Priority priority) {
		throw new UnsupportedOperationException();
	}



}
