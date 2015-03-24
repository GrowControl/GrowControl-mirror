package com.growcontrol.common.packets;

import java.util.Map;


public interface Packet {


	public boolean handle(final Map<String, Object> json);


}
