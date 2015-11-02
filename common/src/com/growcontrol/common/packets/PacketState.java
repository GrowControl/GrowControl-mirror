package com.growcontrol.common.packets;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.growcontrol.common.netty.NetParent;
import com.growcontrol.common.netty.SocketState;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;
import com.poixson.commonjava.xLogger.xLog;


public class PacketState {

	protected final Map<String, PacketDAO> packetTypes =
			new LinkedHashMap<String, PacketDAO>();

	protected final NetParent netParent;
	protected final SocketState socketState;

	protected Yaml yaml = null;



	public PacketState(final NetParent netParent, final SocketState socketState) {
		this.netParent   = netParent;
		this.socketState = socketState;
	}



	public void register(final Class<? extends Packet> packetClass) {
		synchronized(this.packetTypes) {
			final PacketDAO dao = new PacketDAO(this, packetClass);
			this.packetTypes.put(dao.name, dao);
xLog.getRoot("NET").finer("Registered packet type: "+dao.name);
		}
	}
	public boolean clear(final Class<? extends Packet> packetClass) {
		if(this.packetTypes.isEmpty())
			return false;
		synchronized(this.packetTypes) {
			final Iterator<Entry<String, PacketDAO>> it = this.packetTypes.entrySet().iterator();
			while(it.hasNext()) {
				final Entry<String, PacketDAO> entry = it.next();
				final PacketDAO dao = entry.getValue();
				if(dao.classEquals(packetClass)) {
					it.remove();
					return true;
				}
			}
		}
		return false;
	}
	public void clearAll() {
		if(this.packetTypes.isEmpty())
			return;
		synchronized(this.packetTypes) {
			this.packetTypes.clear();
		}
	}



	public PacketDAO getPacketDAO(final String name) {
		synchronized(this.packetTypes) {
			return this.packetTypes
					.get(name);
		}
	}
	public PacketDAO getPacketDAO(final Class<? extends Packet> packetClass) {
		if(this.packetTypes.isEmpty())
			return null;
		synchronized(this.packetTypes) {
			final Iterator<PacketDAO> it = this.packetTypes.values().iterator();
			while(it.hasNext()) {
				final PacketDAO dao = it.next();
				if(dao.classEquals(packetClass))
					return dao;
			}
		}
		return null;
	}



	public void send(final Packet packet) {
		final Object json = packet.generate();
		this.send(json);
	}
	public void send(final Class<? extends Packet> packetClass) throws PacketException {
		if(packetClass == null) throw new RequiredArgumentException("packetClass");
		final PacketDAO dao = this.getPacketDAO(packetClass);
		if(dao == null) throw new PacketException("Packet type not registered: "+packetClass.getName());
		// get packet instance
		final Packet packet;
		try {
			packet = dao.getInstance();
		} catch (InstantiationException e) {
xLog.getRoot("NET").severe("Failed to create packet instance for: "+packetClass.getName());
			throw new PacketException(e);
		} catch (IllegalAccessException e) {
xLog.getRoot("NET").severe("Failed to create packet instance for: "+packetClass.getName());
			throw new PacketException(e);
		}
		if(packet == null) throw new RuntimeException("Failed to get packet instance!");
		this.send(packet);
	}
	public void send(final Object json) {
		if(json == null) throw new RequiredArgumentException("json");
		final String data = Packet.convert(this.getYaml(), json);
		if(utils.isEmpty(data)) throw new RuntimeException("Failed to parse json data!");
		this.socketState.send(data);
xLog.getRoot("NET").publish("");
xLog.getRoot("NET").publish("SERVER SENDING PACKET:");
xLog.getRoot("NET").publish("==>"+data+"<==");
xLog.getRoot("NET").publish("");
	}



	public void handle(final String str) throws PacketException {
		if(utils.isEmpty(str)) throw new PacketException("empty packet data");
		// parse json
		final Map<String, Object> json = Packet.convertMap(
				this.getYaml(),
				str
		);
		final String name = (String) json.get("packet");
		if(utils.isEmpty(name)) throw new PacketException("invalid packet; packet name is required");
		final PacketDAO dao = this.getPacketDAO(name);
		// unknown packet
		if(dao == null) {
xLog.getRoot("NET").warning("Unhandled packet! "+name);
			return;
		}
		// check packet direction
		{
			final PacketDirection expected = this.netParent.getDirection();
			final PacketDirection current  = dao.direction;
			if(!PacketDirection.canReceive(expected, current)) {
xLog.getRoot("NET").severe("Illegal packet direction!");
				throw new IllegalStateException(
						"Packet type: "+current.toString()+
						" is not handled by: "+expected.toString()+" !"
				);
			}
		}
		// handle packet
		try {
			dao.getInstance()
				.handle(name, json);
		} catch (InstantiationException e) {
xLog.getRoot("NET").warning("Problem handling packet! "+name);
xLog.getRoot("NET").trace(e);
			return;
		} catch (IllegalAccessException e) {
xLog.getRoot("NET").warning("Problem handling packet! "+name);
xLog.getRoot("NET").trace(e);
			return;
		}
	}



	public Yaml getYaml() {
		if(this.yaml == null) {
			final DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
			options.setPrettyFlow(true);
			this.yaml = new Yaml(options);
		}
		return this.yaml;
	}



}
