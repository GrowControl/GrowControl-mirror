package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaPH extends Meta {
	private static final long serialVersionUID = 11L;

	private volatile Integer value = null;


	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public MetaPH clone() {
		return new MetaPH(typeStr());
	}


}
