package com.growcontrol.common.packets;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.xLogger.xLog;


public class PacketState {

	protected final Map<String, PacketDAO> packetTypes =
			new LinkedHashMap<String, PacketDAO>();

	protected Yaml yaml = null;



//	public PacketState(final NetServer server, final ServerSocketState socketState) {
//		this.server = server;
//		this.socketState = socketState;
//	}



	public void register(final Class<? extends Packet> packetClass) {
		synchronized(this.packetTypes) {
			final PacketDAO dao = new PacketDAO(packetClass);
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



	public void handle(final String str) throws PacketException {
		if(utils.isEmpty(str)) throw new PacketException("empty packet data");
		// parse json
		final Map<String, Object> json = Packet.convertMap(
				this.getYaml(),
				str
		);
		final String name = (String) json.get("packet");
		if(utils.isEmpty(name)) throw new PacketException("invalid packet; packet name is required");
		final PacketDAO dao;
		synchronized(this.packetTypes) {
			dao = this.packetTypes.get(name);
		}
		// unknown packet
		if(dao == null) {
xLog.getRoot("NET").warning("Unhandled packet! "+name);
			return;
		}
		try {
			// handle packet
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
