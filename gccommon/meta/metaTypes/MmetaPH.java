package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class metaPH extends Meta {
	private static final long serialVersionUID = 11L;

	private volatile Integer value = null;


	protected metaPH(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public metaPH clone() {
		return new metaPH(typeStr());
	}


}
