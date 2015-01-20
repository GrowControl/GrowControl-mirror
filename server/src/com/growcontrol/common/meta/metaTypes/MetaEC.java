package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.InvalidMetaFormatException;
import com.growcontrol.common.meta.Meta;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaEC extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Integer value = null;



	public static MetaEC get(final String str) {
		final MetaEC meta = new MetaEC();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaEC clone() {
		final MetaEC meta = new MetaEC();
		if(this.value != null)
			meta.set(this.getStringValue());
		return meta;
	}



	// set value
	public void set(final int value) {
		this.value = new Integer(value);
	}
	public void set(final Integer value) {
		if(value == null)
			this.value = null;
		else
			this.value = new Integer(value.intValue());
	}
	@Override
	public void set(final String str) {
		if(str == null) {
			this.value = null;
			return;
		}
		final Integer i = utilsNumbers.toInteger(str);
		if(i == null) throw new InvalidMetaFormatException("'"+str+"'");
		this.value = i;
	}



	// get value
	public Integer value() {
		if(this.value == null)
			return null;
		return new Integer(this.value.intValue());
	}
	@Override
	public String getStringValue() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
