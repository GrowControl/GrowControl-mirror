package com.growcontrol.common.packets;

import com.growcontrol.common.packets.annotations.PacketProperty;
import com.poixson.commonjava.Utils.utils;


public class PacketDAO {

	public final String name;
	public final Class<? extends Packet> clss;
	public final PacketProperty props;
	public volatile Packet instance = null;



	public PacketDAO(final Class<? extends Packet> clss) {
		if(clss == null) throw new NullPointerException("clss argument is required!");
		this.clss = clss;
		this.props = clss.getAnnotation(PacketProperty.class);
		String name = null;
		if(this.props != null)
			name = this.props.name();
		this.name = (utils.isEmpty(name) ? clss.getName() : name);
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
