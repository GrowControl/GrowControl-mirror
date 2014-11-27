package com.growcontrol.gccommon.meta;

import com.growcontrol.gccommon.meta.Meta;
import com.poixson.commonjava.EventListener.xEventData;
import com.poixson.commonjava.Utils.utils;


public class MetaEvent extends xEventData {

	public final String destStr;
	public final Meta meta;


	public MetaEvent(final String destStr, final Meta meta) {
		if(utils.isEmpty(destStr)) throw new NullPointerException();
		if(meta == null) throw new NullPointerException();
		this.destStr = destStr;
		this.meta = meta;
	}


}
