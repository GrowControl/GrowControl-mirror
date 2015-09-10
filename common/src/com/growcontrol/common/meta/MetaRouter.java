package com.growcontrol.common.meta;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.growcontrol.common.meta.exceptions.UnknownAddressException;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xEvents.xEventData;
import com.poixson.commonjava.xEvents.xEventListener;
import com.poixson.commonjava.xEvents.xHandlerGeneric;


public class MetaRouter extends xHandlerGeneric {

	private static volatile MetaRouter instance = null;
	private static final Object instanceLock = new Object();

	// listener addresses
	private final Map<MetaAddress, MetaListener> dests =
			new ConcurrentHashMap<MetaAddress, MetaListener>();
	private final Set<MetaAddress> sources =
			new CopyOnWriteArraySet<MetaAddress>();



	public static MetaRouter get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null) {
					instance = new MetaRouter();
					Keeper.add(instance);
				}
			}
		}
		return instance;
	}
	private MetaRouter() {
		super();
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



	public String register(final String addrStr, final MetaListener listener) {
		if(utils.isEmpty(addrStr)) throw new NullPointerException("addrStr argument is required!");
		if(listener == null)       throw new NullPointerException("listener argument is required!");
		final MetaAddress address = MetaAddress.get(addrStr);
		if(address == null) throw new UnknownAddressException(addrStr);
		return this.register(
			address,
			listener
		);
	}
	public String register(final MetaAddress address, final MetaListener listener) {
		if(address  == null) throw new NullPointerException("address argument is required!");
		if(listener == null) throw new NullPointerException("listener argument is required!");
		final MetaAddress addr =
				address == null
				? MetaAddress.getRandom()
				: address;
		if(this.dests.containsKey(addr))
			log().fine("Replacing meta event listener: "+addr.hash);
		else
			log().finer("Registered meta address: "+addr.hash);
		this.dests.put(addr, listener);
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
		if(metaValue == null) throw new InvalidMetaValueException(value);
		this.route(
				destAddr,
				metaValue
		);
	}
	public void route(final String destAddr, final MetaType value) {
		if(utils.isEmpty(destAddr)) throw new NullPointerException("destAddr argument is required!");
		if(value == null)           throw new NullPointerException("value argument is required!");
		final MetaAddress addr = MetaAddress.get(destAddr);
		if(addr == null) throw new UnknownAddressException(destAddr);
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



	// get known destination addresses
	public MetaAddress[] getKnownDestinations() {
		return this.dests.keySet()
				.toArray(new MetaAddress[0]);
	}



	// get known source addresses
	public MetaAddress[] getKnownSources() {
		return this.sources
				.toArray(new MetaAddress[0]);
	}



	// get listener by address
	public MetaListener getListener(final String destAddr) {
		final MetaAddress address = MetaAddress.get(destAddr);
		if(address == null)
			return null;
		return getListener(address);
	}
	public MetaListener getListener(final MetaAddress destAddr) {
		return this.dests.get(destAddr);
	}



	@Override
	public void unregisterAll() {
		super.unregisterAll();
		this.dests.clear();
		this.sources.clear();
	}



}
