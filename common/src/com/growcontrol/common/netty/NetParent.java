package com.growcontrol.common.netty;

import com.growcontrol.common.packets.PacketDirection;
import com.poixson.commonjava.Utils.xCloseableMany;


public interface NetParent extends xCloseableMany {


	public PacketDirection getDirection();
	public String getKeyHash();


}
