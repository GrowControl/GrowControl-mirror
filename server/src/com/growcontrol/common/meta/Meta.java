package com.growcontrol.common.meta;

import java.io.Serializable;

import com.growcontrol.common.meta.metaTypes.MetaString;


public abstract class Meta implements Serializable {
	private static final long serialVersionUID = 31L;



	public static Meta get(final String data) {
		return MetaString.get(data);
	}
	protected Meta() {
	}
	@Override
	public abstract Meta clone();



	public static Meta convert(final Class<? extends Meta> clss, final Meta meta)
			throws ReflectiveOperationException {
		if(meta.getClass().equals(clss))
			return meta;
		final Meta m = clss.newInstance();
		m.set(
			meta.getStringValue()
		);
		return m;
	}



	// set value
	public void set(final Meta meta) {
		this.set(meta.getStringValue());
	}
	public abstract void set(final String str);



	// get value
	@Override
	public String toString() {
		return this.getTypeName() + "@" + this.getStringValue();
	}
	public abstract String getStringValue();



	// meta type
	public String getTypeName() {
		final String str = this.getClass().getSimpleName();
		if(str.substring(0, 4).equalsIgnoreCase("meta"))
			return str.substring(4);
		return str;
	}



}
