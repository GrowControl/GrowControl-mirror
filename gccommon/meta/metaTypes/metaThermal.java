package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class metaThermal extends Meta {
	private static final long serialVersionUID = 11L;

	protected volatile Double value = null;


	protected metaThermal(final String typeStr) {
		super(typeStr);
	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public metaThermal clone() {
		return new metaThermal(typeStr());
	}


}
