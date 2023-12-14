package com.growcontrol.server.events;


public class gcTickEvent {

	protected final long index;



	public gcTickEvent(final long index) {
		this.index = index;
	}



	public long getIndex() {
		return this.index;
	}



}
