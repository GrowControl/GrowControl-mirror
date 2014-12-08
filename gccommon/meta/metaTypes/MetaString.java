package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;
import com.poixson.commonjava.Utils.utils;


public class MetaString extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile String value = null;



	public static MetaString get(final String str) {
		final MetaString meta = new MetaString();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaString clone() {
		final MetaString meta = new MetaString();
		if(utils.notEmpty(this.value))
			meta.set(this.getStringValue());
		return meta;
	}



	// set value
	@Override
	public void set(final String str) {
		this.value = str;
	}



	// get value
	public String value() {
		if(this.value == null)
			return null;
		return new String(this.value);
	}
	@Override
	public String getStringValue() {
		return this.value;
	}



}
