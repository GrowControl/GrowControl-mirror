package com.growcontrol.gccommon.meta;

import java.io.Serializable;


public abstract class Meta implements Serializable {
	private static final long serialVersionUID = 31L;



	protected Meta() {
	}
	@Override
	public abstract Meta clone();



	// set value
	public void set(final Meta meta) {
		this.set(meta.getValueStr());
	}
	public abstract void set(final String str);



	// get value
	public String getString() {
//		return "["+this.typeStr+":"+getValueStr()+"]";
		return null;
	}
	public abstract String getValueStr();



	// meta type
	public String getTypeName() {
		final String str = this.getClass().getSimpleName();
		if(str.substring(0, 4).equalsIgnoreCase("meta"))
			return str.substring(4);
		return str;
	}



}
