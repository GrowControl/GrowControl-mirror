package com.growcontrol.common.packets;

import com.growcontrol.common.packets.annotations.PacketProperty;
import com.poixson.commonjava.Utils.utils;


public class PacketDAO {

	public final Class<? extends Packet> packetClass;
	public volatile Packet instance = null;

	// properties
	public final String  name;
	public final boolean stateful;


	public PacketDAO(final Class<? extends Packet> packetClass) {
		if(packetClass == null) throw new NullPointerException("packetClass argument is required!");
		this.packetClass = packetClass;
		// load properties annotation
		final PacketProperties props = packetClass.getAnnotation(PacketProperties.class);
		String name = null;
		if(props != null)
			name = props.name();
		if(utils.isEmpty(name))
			name = packetClass.getName();
		this.name = name;
		this.stateful = props.stateful();
	}



	public Packet getInstance() throws InstantiationException, IllegalAccessException {
		// stateful instance
		if(this.props.stateful()) {
			if(this.instance == null) {
				this.instance = this.clss.newInstance();
				this.instance.setDAO(this);
			}
			return this.instance;
		}
		// not stateful
		{
			final Packet instance = this.clss.newInstance();
			instance.setDAO(this);
			return instance;
		}
	}



}
