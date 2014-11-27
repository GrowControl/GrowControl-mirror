package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class metaIO extends Meta {
	private static final long serialVersionUID = 11L;

	protected volatile Boolean value = null;


	protected metaIO(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public metaIO clone() {
		return new metaIO(typeStr());
	}


}
