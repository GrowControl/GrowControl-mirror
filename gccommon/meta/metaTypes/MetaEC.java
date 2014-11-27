package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaEC extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Integer value = null;



	public static MetaEC get(final String str) {
		final MetaEC meta = new MetaEC();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaEC clone() {
		final MetaEC meta = new MetaEC();
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
