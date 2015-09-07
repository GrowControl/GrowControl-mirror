package com.growcontrol.common.meta;

import java.io.Serializable;

import com.growcontrol.common.meta.metaTypes.MetaString;


public abstract class MetaType implements Serializable {
	private static final long serialVersionUID = 030601L;



	public static MetaType get(final String data) {
		return MetaString.get(data);
	}
	protected MetaType() {
	}
	@Override
	public abstract MetaType clone();



	public abstract String getTypeName();
//	// meta type
//	public String getTypeName() {
//		final String str = this.getClass().getSimpleName();
//		if(str.substring(0, 4).equalsIgnoreCase("meta"))
//			return str.substring(4);
//		return str;
//	}



	// set value
	public void set(final MetaType value) {
		this.set(value.toString());
	}

	public abstract void set(final String value);



	// get value
	public abstract Object getValue();
	public abstract String getString();

	@Override
	public String toString() {
		return (new StringBuilder())
				.append(this.getTypeName())
				.append('@')
				.append(this.getString())
				.toString();
	}



//	public static MetaType convert(final Class<? extends MetaType> clss, final MetaType type)
//			throws ReflectiveOperationException {
//		if(type.getClass().equals(clss))
//			return type;
//		final MetaType meta = clss.newInstance();
//		meta.set(
//				type.getStringValue()
//		);
//		return meta;
//	}



}
