package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaIO extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Boolean value = null;


	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public MetaIO clone() {
		return new MetaIO(typeStr());
	}


}
