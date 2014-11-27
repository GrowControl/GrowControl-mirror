package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaPH extends Meta {
	private static final long serialVersionUID = 31L;

	private volatile Integer value = null;



	public static MetaPH get(final String str) {
		final MetaPH meta = new MetaPH();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaPH clone() {
		final MetaPH meta = new MetaPH();
		if(this.value != null)
			meta.set(this.getValueStr());
		return meta;
	}



	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
