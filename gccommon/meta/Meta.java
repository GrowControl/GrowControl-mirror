package com.growcontrol.gccommon.meta;

import java.io.Serializable;


public abstract class Meta implements Serializable {
	private static final long serialVersionUID = 31L;



	protected Meta() {
	}
	@Override
	public abstract Meta clone();


	public String getString() {
		return "["+this.typeStr+":"+getValueStr()+"]";
	}


	public String typeStr() {
		return this.typeStr;
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
