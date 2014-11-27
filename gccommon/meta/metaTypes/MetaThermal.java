package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaThermal extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Double value = null;



	public static MetaThermal get(final String str) {
		final MetaThermal meta = new MetaThermal();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaThermal clone() {
		final MetaThermal meta = new MetaThermal();
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
