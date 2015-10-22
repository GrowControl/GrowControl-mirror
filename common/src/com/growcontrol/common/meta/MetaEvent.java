package com.growcontrol.common.meta;

import com.growcontrol.common.meta.exceptions.UnknownAddressException;
import com.poixson.commonjava.xEvents.xEventData;


public class MetaEvent extends xEventData {

	public final MetaAddress destination;
	public final MetaType value;



	public MetaEvent(final String addressStr, final MetaType value) {
		super();
		if(addressStr == null) throw new NullPointerException("address argument is required!");
		if(value      == null) throw new NullPointerException("meta value argument is required!");
		this.destination = MetaAddress.get(addressStr);
		if(this.destination == null) throw new UnknownAddressException(addressStr);
		this.value = value;
	}
	public MetaEvent(final MetaAddress address, final MetaType value) {
		super();
		if(address == null) throw new NullPointerException("address argument is required!");
		if(value   == null) throw new NullPointerException("meta value argument is required!");
		this.destination = address;
		this.value       = value;
	}



	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		str.append(this.destination.toString())
			.append(">")
			.append(this.value.toString());
		return str.toString();
	}



}
