package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaTrigger extends Meta {
	private static final long serialVersionUID = 11L;


	protected MetaTrigger(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		return null;
	}
	@Override
	public MetaTrigger clone() {
		return new MetaTrigger(typeStr());
	}


}
