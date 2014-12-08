package com.growcontrol.gccommon.meta;

import com.poixson.commonjava.EventListener.xEventData;


public class MetaEvent extends xEventData {

	public final MetaAddress destination;
	public final Meta meta;



	public MetaEvent(final MetaAddress address, final Meta meta) {
		if(address == null) throw new NullPointerException();
		if(meta    == null) throw new NullPointerException();
		this.destination = address;
		this.meta = meta;
	}



}
