package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaThermal extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Double value = null;


	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public MetaThermal clone() {
		return new MetaThermal(typeStr());
	}


}
