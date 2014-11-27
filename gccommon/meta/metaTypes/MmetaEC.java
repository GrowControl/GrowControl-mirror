package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class metaEC extends Meta {
	private static final long serialVersionUID = 11L;

	protected volatile Integer value = null;


	protected metaEC(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public metaEC clone() {
		return new metaEC(typeStr());
	}


}
