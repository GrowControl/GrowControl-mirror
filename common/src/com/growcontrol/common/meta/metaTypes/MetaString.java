package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;


public class MetaString extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "String";

	protected volatile String value = null;



	public static MetaString get() {
		return new MetaString();
	}
	public static MetaString get(final String value) {
		final MetaString meta = new MetaString();
		meta.set(value);
		return meta;
	}
	@Override
	public MetaString clone() {
		return new MetaString(this);
	}



	public MetaString() {
	}
	public MetaString(final String value) {
		this.set(value);
	}
	public MetaString(final MetaString value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
	public void set(final MetaString value) {
		this.value = value.value;
	}
	@Override
	public void set(final String value) {
		this.value = value;
	}



	// get value
	@Override
	public String getValue() {
		if(this.value == null)
			return null;
		return new String(this.value);
	}
	@Override
	public String getString() {
		return this.value;
	}



}
