package com.growcontrol.common.packets;

import java.util.Map;


public interface Packet {


	public boolean handle(String name, final Map<String, Object> json);


}
