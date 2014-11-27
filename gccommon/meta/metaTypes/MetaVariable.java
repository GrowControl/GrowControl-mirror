package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.Meta;


public class MetaVariable extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Integer value = null;


	}


	@Override
	public String getValueStr() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}
	@Override
	public MetaVariable clone() {
		return new MetaVariable(typeStr());
	}


}
