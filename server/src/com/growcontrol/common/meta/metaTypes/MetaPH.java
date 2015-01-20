package com.growcontrol.gccommon.meta.metaTypes;

import com.growcontrol.gccommon.meta.InvalidMetaFormatException;
import com.growcontrol.gccommon.meta.Meta;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaPH extends Meta {
	private static final long serialVersionUID = 31L;

	protected volatile Integer value = null;



	public static MetaPH get(final String str) {
		final MetaPH meta = new MetaPH();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaPH clone() {
		final MetaPH meta = new MetaPH();
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
