package com.growcontrol.common.meta;

import com.growcontrol.common.meta.exceptions.UnknownAddressException;
import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;
import com.poixson.commonjava.xEvents.xEventData;


public class MetaEvent extends xEventData {

	public final MetaAddress destination;
	public final MetaType value;



	public MetaEvent(final String addressStr, final MetaType value) {
		super();
		if(addressStr == null) throw new RequiredArgumentException("addressStr");
		if(value      == null) throw new RequiredArgumentException("meta value");
		this.destination = MetaAddress.get(addressStr);
		if(this.destination == null) throw new UnknownAddressException(addressStr);
		this.value = value;
	}
	public MetaEvent(final MetaAddress address, final MetaType value) {
		super();
		if(address == null) throw new RequiredArgumentException("address");
		if(value   == null) throw new RequiredArgumentException("meta value");
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
