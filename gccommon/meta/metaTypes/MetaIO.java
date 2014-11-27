package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaIO extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Boolean value = null;



	public static MetaIO get(final String str) {
		final MetaIO meta = new MetaIO();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaIO clone() {
		final MetaIO meta = new MetaIO();
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
