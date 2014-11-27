package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaVariable extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Integer value = null;



	public static MetaVariable get(final String str) {
		final MetaVariable meta = new MetaVariable();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaVariable clone() {
		final MetaVariable meta = new MetaVariable();
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
