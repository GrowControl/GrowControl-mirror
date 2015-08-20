package com.growcontrol.common.packets;

import com.poixson.commonjava.Utils.utils;


public class PacketDAO {

	public final Class<? extends Packet> packetClass;
	public volatile Packet instance = null;

	// properties
	public final String  name;
	public final boolean stateful;
	public final boolean async;
	public final PacketDirection direction;



	public PacketDAO(final Class<? extends Packet> packetClass) {
		if(packetClass == null) throw new NullPointerException("packetClass argument is required!");
		this.packetClass = packetClass;
		// load properties annotation
		final PacketProperties props = packetClass.getAnnotation(PacketProperties.class);
		// packet name
		if(props != null) {
			this.name = props.name();
		} else {
			String name = packetClass.getName();
			if(name.startsWith("Packet_"))
				name = name.substring("Packet_".length());
			this.name = name;
		}
		if(utils.isEmpty(this.name))
			throw new NullPointerException("Packet name is required!");
		// more packet properties
		this.stateful = props.stateful();
		this.async    = props.async();
		this.direction = props.direction();
	}



	public Packet getInstance() throws InstantiationException, IllegalAccessException {
		// stateful instance
		if(this.stateful) {
			if(this.instance == null) {
				this.instance = this.packetClass.newInstance();
				this.instance.setDAO(this);
			}
			return this.instance;
		}
		// not stateful
		{
			final Packet instance = this.packetClass.newInstance();
			instance.setDAO(this);
			return instance;
		}
	}



	public boolean classEquals(final Class<? extends Packet> packetClass) {
		if(packetClass == null)
			return false;
		final String expected = packetClass.getName();
		final String actual   = this.packetClass.getName();
		if(utils.isEmpty(expected) || utils.isEmpty(actual))
			return false;
		return expected.equals(actual);
	}



}
