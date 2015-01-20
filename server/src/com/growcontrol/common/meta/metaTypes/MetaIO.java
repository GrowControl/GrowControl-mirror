package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.InvalidMetaFormatException;
import com.growcontrol.common.meta.Meta;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaIO extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Boolean value = null;



	public static MetaIO get(final String str) {
		final MetaIO meta = new MetaIO();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaIO clone() {
		final MetaIO meta = new MetaIO();
		if(this.value != null)
			meta.set(this.getStringValue());
		return meta;
	}



	// set value
	public void set(final boolean value) {
		this.value = new Boolean(value);
	}
	public void set(final Boolean value) {
		if(value == null)
			this.value = null;
		else
			this.value = new Boolean(value.booleanValue());
	}
	@Override
	public void set(final String str) {
		if(str == null) {
			this.value = null;
			return;
		}
		final Boolean b = utilsNumbers.toBoolean(str);
		if(b == null) throw new InvalidMetaFormatException("'"+str+"'");
		this.value = b;
	}



	// get value
	public Boolean value() {
		if(this.value == null)
			return null;
		return new Boolean(this.value.booleanValue());
	}
	@Override
	public String getStringValue() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
