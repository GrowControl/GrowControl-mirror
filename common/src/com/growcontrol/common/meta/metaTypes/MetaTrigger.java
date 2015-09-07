package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;


public class MetaTrigger extends MetaType {
	private static final long serialVersionUID = 31L;



	public static MetaTrigger get(final String str) {
		final MetaTrigger meta = new MetaTrigger();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaTrigger clone() {
		final MetaTrigger meta = new MetaTrigger();
		return meta;
	}



	// set value
	public void set() {
	}
	@Override
	public void set(String str) {
	}



	// get value
	@Override
	public String getStringValue() {
		return null;
	}



}
