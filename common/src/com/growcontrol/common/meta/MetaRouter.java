package com.growcontrol.common.meta;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.growcontrol.common.meta.exceptions.UnknownAddressException;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;
import com.poixson.commonjava.xEvents.xEventData;
import com.poixson.commonjava.xEvents.xEventListener;
import com.poixson.commonjava.xEvents.xEventListener.ListenerPriority;
import com.poixson.commonjava.xEvents.xHandlerSimple;
import com.poixson.commonjava.xEvents.xListenerDAO;
import com.poixson.commonjava.xEvents.xRunnableEvent;


public class MetaRouter extends xHandlerSimple {
	private static final String LISTENER_METHOD_NAME = "onMetaEvent";

	private static volatile MetaRouter instance = null;
	private static final Object instanceLock = new Object();

	// listener addresses
	private final Map<MetaAddress, xListenerDAO> dests =
			new ConcurrentHashMap<MetaAddress, xListenerDAO>();
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
		// super();
		this.listeners = null;
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
	// fixed method name
	@Override
	protected String getMethodName() {
		return LISTENER_METHOD_NAME;
	}



	public String register(final String addrStr, final MetaListener listener) {
		if(utils.isEmpty(addrStr)) throw new RequiredArgumentException("addrStr");
		if(listener == null)       throw new RequiredArgumentException("listener");
		final MetaAddress address = MetaAddress.get(addrStr);
		if(address == null) throw new UnknownAddressException(addrStr);
		return this.register(
			address,
			listener
		);
	}
	public String register(final MetaAddress address, final MetaListener listener) {
		if(address  == null) throw new RequiredArgumentException("address");
		if(listener == null) throw new RequiredArgumentException("listener");
		final MetaAddress addr =
				address == null
				? MetaAddress.getRandom()
				: address;
		final Method method;
		try {
			method = listener.getClass().getMethod(
					this.getMethodName(),
					MetaEvent.class
			);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		if(method == null) throw new RuntimeException("Failed to find listener method: "+this.getMethodName());
		if(this.dests.containsKey(addr))
			log().fine("Replacing meta event listener: "+addr.hash);
		else
			log().finer("Registered meta address: "+addr.hash);
		final xListenerDAO dao = new xListenerDAO(
				listener,
				method,
				ListenerPriority.NORMAL,
				false, // filter handled,
				true   // filter cancelled
		);
		this.dests.put(addr, dao);
		return addr.hash;
	}
	@Override
	public void register(final xEventListener listener) {
		throw new UnsupportedOperationException();
	}



//TODO: this is untested
	@Override
	public void unregister(final xEventListener listener) {
		if(listener == null) throw new RequiredArgumentException("listener");
		if(!(listener instanceof MetaListener))
			throw new UnsupportedOperationException();
		final MetaListener expected = (MetaListener) listener;
		final Iterator<Entry<MetaAddress, xListenerDAO>> it =
				this.dests.entrySet().iterator();
		int count = 0;
		while(it.hasNext()) {
			final Entry<MetaAddress, xListenerDAO> entry = it.next();
			final MetaAddress  address = entry.getKey();
			final xListenerDAO dao     = entry.getValue();
			if(expected.equals(dao.listener)) {
				this.dests.remove(address);
				this.log().finest("Removed listener: "+address.getKey()+
						":"+dao.listener.getClass().getName());
//				return;
			}
		}
		if(count == 0) {
			this.log().finest("Listener not found to remove");
		} else {
			this.log().finest("Removed [ "+Integer.toString(count)+
					" ] listeners of type: "+listener.getClass().getName());
		}
	}
	@Override
	public void unregisterType(final Class<?> listenerClass) {
		if(listenerClass == null) throw new RequiredArgumentException("listener class");
		final Iterator<Entry<MetaAddress, xListenerDAO>> it =
				this.dests.entrySet().iterator();
		int count = 0;
		while(it.hasNext()) {
			final Entry<MetaAddress, xListenerDAO> entry = it.next();
			final MetaAddress  address = entry.getKey();
			final xListenerDAO dao     = entry.getValue();
			if(listenerClass.equals(dao.listener.getClass())) {
				this.dests.remove(address);
				count++;
				this.log().finest("Removed listener: "+dao.listener.getClass().getName());
			}
		}
		if(count == 0) {
			this.log().finest("Listener not found to remove");
		} else {
			this.log().finest("Removed [ "+Integer.toString(count)+
					" ] listeners of type: "+listenerClass.getName());
		}
	}
	@Override
	public void unregisterAll() {
		super.unregisterAll();
		this.dests.clear();
		this.sources.clear();
	}



	// route an event
	public void route(final String destAddr, final String value) {
		if(utils.isEmpty(destAddr)) throw new RequiredArgumentException("destAddr");
		if(utils.isEmpty(value))    throw new RequiredArgumentException("value");
		final MetaType metaValue = MetaType.get(value);
		if(metaValue == null) throw new InvalidMetaValueException(value);
		this.route(
				destAddr,
				metaValue
		);
	}
	public void route(final String destAddr, final MetaType value) {
		if(utils.isEmpty(destAddr)) throw new RequiredArgumentException("destAddr");
		if(value == null)           throw new RequiredArgumentException("value");
		final MetaAddress addr = MetaAddress.get(destAddr);
		if(addr == null) throw new UnknownAddressException(destAddr);
		this.route(
				addr,
				value
		);
	}
	public void route(final MetaAddress dest, final MetaType value) {
		if(dest  == null) throw new RequiredArgumentException("dest");
		if(value == null) throw new RequiredArgumentException("value");
		final MetaEvent event = new MetaEvent(dest, value);
		this.trigger(event);
	}




	@Override
	public void trigger(final xEventData event) {
		// ensure main thread
//TODO: how did I do this before?


//TODO:
//this.log().warning("xHandler->trigger() function is unfinished!");


		if(event == null) throw new RequiredArgumentException("event");
		final MetaEvent metaEvent = (MetaEvent) event;
		this.log().finest("Triggering meta event: "+event.toString());
//		final Set<xRunnableEvent> waitFor = new HashSet<xRunnableEvent>();
		final Iterator<Entry<MetaAddress, xListenerDAO>> it = this.dests.entrySet().iterator();
		// LOOP_LISTENERS:
		while(it.hasNext()) {
			if(event.isCancelled())
				break;
			final Entry<MetaAddress, xListenerDAO> entry = it.next();
			final MetaAddress  address  = entry.getKey();
			final xListenerDAO dao      = entry.getValue();

			if(!metaEvent.destination.matches(address))
				continue;

			// run event
			final xRunnableEvent run = new xRunnableEvent(
					dao,
					event,
					ListenerPriority.NORMAL
			);
//TODO:
//			waitFor.add(run);
//			xThreadPool.getMainPool()
//					.runLater(run);
			run.run();
		} // listeners loop
//TODO:
//		// wait for event tasks to complete
//		for(final xRunnableEvent run : waitFor) {
//			run.waitUntilRun();
//		}
		if(event.isCancelled())
			this.log().fine("Event was cancelled: "+event.toString());
		if(!event.isHandled())
			this.log().fine("Event was not handled: "+event.toString());
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
		final xListenerDAO dao = this.dests.get(destAddr);
		if(dao == null)
			return null;
		return (MetaListener) dao.listener;
	}



}
