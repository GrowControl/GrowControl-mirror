package com.growcontrol.gccommon.meta;

import java.io.Serializable;


public abstract class Meta implements Serializable {
	private static final long serialVersionUID = 11L;

	protected final String typeStr;


	protected Meta(final String typeStr) {
		if(typeStr == null) throw new NullPointerException();
		this.typeStr = typeStr;
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


}
