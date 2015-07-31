package com.growcontrol.common.meta;

import com.poixson.commonjava.EventListener.xEventData;


public class MetaEvent extends xEventData {

	public final MetaAddress destination;
	public final Meta meta;



	public MetaEvent(final MetaAddress address, final Meta meta) {
		if(address == null) throw new NullPointerException("address argument is required!");
		if(meta    == null) throw new NullPointerException("meta argument is required!");
		this.destination = address;
		this.meta = meta;
	}



	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		str.append(this.destination.toString())
			.append("|")
			.append(this.meta.toString());
		return str.toString();
	}



}
