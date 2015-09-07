package com.growcontrol.common.meta;

import java.io.Serializable;

import com.growcontrol.common.meta.metaTypes.MetaString;


public abstract class MetaType implements Serializable {
	private static final long serialVersionUID = 31L;



	public static MetaType get(final String data) {
		return MetaString.get(data);
	}
	protected MetaType() {
	}
	@Override
	public abstract MetaType clone();



	public static MetaType convert(final Class<? extends MetaType> clss, final MetaType type)
			throws ReflectiveOperationException {
		if(type.getClass().equals(clss))
			return type;
		final MetaType meta = clss.newInstance();
		meta.set(
			type.getStringValue()
		);
		return meta;
	}



	// set value
	public void set(final MetaType meta) {
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
