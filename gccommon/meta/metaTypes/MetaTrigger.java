package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaTrigger extends Meta {
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


	@Override
	public String getValueStr() {
		return null;
	}
	@Override
	}


}
