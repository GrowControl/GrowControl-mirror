package com.growcontrol.gccommon.meta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.poixson.commonjava.EventListener.xEvent.Priority;
import com.poixson.commonjava.EventListener.xEventData;
import com.poixson.commonjava.EventListener.xHandler;
import com.poixson.commonjava.EventListener.xListener;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xRunnable;
import com.poixson.commonjava.Utils.xThreadPool;


public class MetaRouter extends xHandler {

	public static final int ADDRESS_LENGTH = 8;

	private static volatile MetaRouter instance = null;
	private static final Object lock = new Object();

	// listener addresses
	protected static final Map<MetaAddress, MetaListener> listenerAddresses =
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
		synchronized(listenerAddresses) {
			if(listenerAddresses.containsKey(addr))
				log().fine("Replacing meta event listener: "+addr.hash);
			else
				log().finer("Registered meta address: "+addr.hash);
			listenerAddresses.put(addr, listener);
		}
		return addr.hash;
	}



	public MetaAddress[] getAddresses() {
		final MetaAddress[] addresses;
		synchronized(listenerAddresses) {
			addresses =
				listenerAddresses.isEmpty()
				? new MetaAddress[0]
				: listenerAddresses.keySet().toArray(new MetaAddress[0]);
		}
		return addresses;
	}



	public void route(final String address, final String value) {
		if(utils.isEmpty(address)) throw new NullPointerException();
		if(utils.isEmpty(value))   throw new NullPointerException();
		this.route(
			MetaAddress.get(address),
			Meta.get(value)
		);
	}
	public void route(final MetaAddress address, final Meta meta) {
		if(address == null) throw new NullPointerException();
		if(meta    == null) throw new NullPointerException();
		this.route(
			new MetaEvent(
				address,
				meta
			)
		);
	}
	public boolean route(final MetaEvent event) {
		if(event == null) throw new NullPointerException();
		return this.route( (xThreadPool) null, event );
	}
	public boolean route(final xThreadPool pool, final MetaEvent event) {
		if(event == null) throw new NullPointerException();
		// get address listener
		final MetaListener listener = this.getAddressListener(event.destination);
		if(listener == null) {
			log().warning("No destination found for meta event: "+event.toString());
			return false;
		}
		// trigger the event
		final xThreadPool p =
			pool == null
				? xThreadPool.get()
				: pool;
		p.runLater(
			this.getRunnable(listener, event)
		);
		return true;
	}



	// get listener by address
	public MetaListener getAddressListener(final MetaAddress address) {
		if(address == null) throw new NullPointerException();
		return listenerAddresses.get(address);
	}



	// xRunnableEvent
	protected xRunnable getRunnable(final MetaListener listener, final MetaEvent event) {
		return new xRunnable("MetaEvent-"+event.destination+"-"+event.meta.toString()) {
			private volatile MetaListener listener;
			private volatile MetaEvent event;
			public xRunnable init(final MetaListener listener, final MetaEvent event) {
				this.listener = listener;
				this.event = event;
				return this;
			}
			@Override
			public void run() {
				if(this.event == null) throw new NullPointerException();
				doRoute(this.listener, this.event);
			}
		}.init(listener, event);
	}



	public void doRoute(final MetaListener listener, final MetaEvent event) {
		if(listener == null) throw new NullPointerException();
		if(event == null)    throw new NullPointerException();
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
