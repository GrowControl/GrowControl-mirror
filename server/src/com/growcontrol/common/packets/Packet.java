package com.growcontrol.common.packets;

import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.xLogger.xLog;


public abstract class Packet {

	protected volatile PacketDAO dao = null;



	public void setDAO(final PacketDAO dao) {
		this.dao = dao;
	}



	public void send(final Packet packet) {
		this.dao.packetState
				.send(packet);
	}
	public void send(final Class<? extends Packet> packetClass) throws PacketException {
		this.dao.packetState
				.send(packetClass);
	}
	public void send(final Object json) {
		this.dao.packetState
				.send(json);
	}



	public abstract Object generate();
	public abstract void handle(final String name, final Map<String, Object> json);



	public static String convert(final Yaml yaml, final Object json) {
		if(yaml == null) throw new NullPointerException("yaml argument is required!");
		if(json == null) throw new NullPointerException("json argument is required!");
		return yaml.dump(json);
	}
	public static Map<String, Object> convertMap(final Yaml yaml, final String data) throws PacketException {
		if(yaml == null)        throw new NullPointerException("yaml argument is required!");
		if(utils.isEmpty(data)) throw new NullPointerException("data argument is required!");
		final Object obj;
		try {
			obj = yaml.load(data);
		} catch (Exception e) {
xLog.getRoot().warning("Failed to parse packet json!");
			throw new PacketException(e);
		}
		return utilsObject.castMap(
				String.class,
				Object.class,
				obj
		);
	}
	public static List<Object> convertList(final Yaml yaml, final String data) throws PacketException {
		if(yaml == null)        throw new NullPointerException("yaml argument is required!");
		if(utils.isEmpty(data)) throw new NullPointerException("data argument is required!");
		final Object obj;
		try {
			obj = yaml.load(data);
		} catch (Exception e) {
xLog.getRoot().warning("Failed to parse packet json!");
			throw new PacketException(e);
		}
		return utilsObject.castList(
				Object.class,
				obj
		);
	}



}
