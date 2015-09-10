package com.growcontrol.common.meta;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xEvents.xEventData;
import com.poixson.commonjava.xEvents.xEventListener;
import com.poixson.commonjava.xEvents.xHandlerGeneric;


public class MetaRouter extends xHandlerGeneric {

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



	// listener type
	@Override
	public Class<? extends xEventListener> getEventListenerType() {
		return MetaListener.class;
	}
	// event type
	@Override
	public Class<? extends xEventData> getEventDataType() {
		return MetaEvent.class;
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
	@Override
	public void register(final xEventListener listener) {
		throw new UnsupportedOperationException();
	}



	// route an event
	public void route(final String destAddr, final String value) {
		if(utils.isEmpty(destAddr)) throw new NullPointerException("destAddr argument is required!");
		if(utils.isEmpty(value))    throw new NullPointerException("value argument is required!");
		final MetaType metaValue = MetaType.get(value);
		if(metaValue == null) throw new IllegalArgumentException("Invalid meta value: "+value);
		this.route(
				destAddr,
				metaValue
		);
	}
	public void route(final String destAddr, final MetaType value) {
		if(utils.isEmpty(destAddr)) throw new NullPointerException("destAddr argument is required!");
		if(value == null)           throw new NullPointerException("value argument is required!");
		final MetaAddress addr = MetaAddress.get(destAddr);
		if(addr == null) throw new IllegalArgumentException("Invalid destination address: "+destAddr);
		this.route(
				addr,
				value
		);
	}
	public void route(final MetaAddress dest, final MetaType value) {
		if(dest  == null) throw new NullPointerException("dest argument is required!");
		if(value == null) throw new NullPointerException("value argument is required!");
		final MetaEvent event = new MetaEvent(dest, value);
		this.trigger(event);
	}



	// get all known addresses
	public MetaAddress[] getKnown() {
		return known.values().toArray(new MetaAddress[0]);
	}
//		final MetaAddress[] addresses;
//		synchronized(known) {
//			addresses =
//				known.isEmpty()
//				? new MetaAddress[0]
//				: known.keySet().toArray(new MetaAddress[0]);
//		}
//		return addresses;
//	}
//	// get listener by address string
//	public MetaListener getAddressListener(final MetaAddress address) {
//		if(address == null) throw new NullPointerException("address argument is required!");
//		return known.get(address);
//	}



	@Override
	public void unregisterAll() {
		super.unregisterAll();
		known.clear();
	}



}
