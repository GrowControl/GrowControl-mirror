package com.growcontrol.common.packets;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLog;


public class PacketState {

	protected final List<PacketDAO> packets = new CopyOnWriteArrayList<PacketDAO>();

//	public final Map<String, Packet> packets = new LinkedHashMap<String, Packet>();

//	protected final Map<String, Class<? extends Packet>> packetClasses =
//			new LinkedHashMap<String, Class<? extends Packet>>();
//	protected final Map<String, Packet> packetInstances =
//			new HashMap<String, Packet>();
//	protected final Map<String, PacketProperty> packerProps =
//			new HashMap<String, PacketProperty>();



//	public PacketState(final NetServer server, final ServerSocketState socketState) {
//	this.server = server;
//	this.socketState = socketState;
//}



	public void register(final Class<? extends Packet> packetClass) {
		synchronized(this.packets) {
			final PacketDAO dao = new PacketDAO(packetClass);
			this.packets.add(dao);
//			final String name = packetClass.getName();
//System.out.println("NAME::::::::::::: "+name);
//System.exit(1);
//			this.packetClasses.put(name, packetClass);
		}
	}
	public void clear() {
		synchronized(this.packets) {
			this.packets.clear();
		}
	}
//	public void clearAll() {
//		this.clear();
//		synchronized(this.packetInstances) {
//			this.packetInstances.clear();
//		}
//	}



	public boolean handle(final Map<String, Object> json) {
		final String name = (String) json.get("packet");
		boolean handled = false;
		synchronized(this.packets) {
			for(final PacketDAO dao : this.packets) {
				try {
					if(utils.isEmpty(dao.name) || "*".equals(dao.name))
						handled = dao.getInstance().handle(name, json);
					else
					if(name.equals(dao.name))
						handled = dao.getInstance().handle(name, json);
				} catch (InstantiationException e) {
xLog.getRoot("NET").trace(e);
					return false;
				} catch (IllegalAccessException e) {
xLog.getRoot("NET").trace(e);
					return false;
				}
				if(handled)
					break;
			}
		}
		if(!handled)
xLog.getRoot("NET").warning("Unhandled packet! "+name);
		return handled;
	}



}
