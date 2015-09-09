package com.growcontrol.common.meta;

import com.poixson.commonjava.xEvents.xEventData;


public class MetaEvent extends xEventData {

	public final MetaAddress destination;
	public final MetaType value;



	public MetaEvent(final MetaAddress address, final MetaType value) {
		if(address == null) throw new NullPointerException("address argument is required!");
		if(value   == null) throw new NullPointerException("meta argument is required!");
		this.destination = address;
		this.value       = value;
	}



	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		str.append(this.destination.toString())
			.append("|")
			.append(this.value.toString());
		return str.toString();
	}



}
