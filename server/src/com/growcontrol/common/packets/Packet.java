package com.growcontrol.common.packets;

import java.util.Map;


public abstract class Packet {

	protected volatile PacketDAO dao = null;



	public void setDAO(final PacketDAO dao) {
		this.dao = dao;
	}



	public abstract Object generate();
	public abstract boolean handle(final String name, final Map<String, Object> json);



}
