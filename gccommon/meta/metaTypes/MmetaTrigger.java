package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class metaTrigger extends Meta {
	private static final long serialVersionUID = 11L;


	protected metaTrigger(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		return null;
	}
	@Override
	public metaTrigger clone() {
		return new metaTrigger(typeStr());
	}


}
