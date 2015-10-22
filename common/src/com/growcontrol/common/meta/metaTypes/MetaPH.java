package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;
import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaPH extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "pH";

	protected volatile Integer value = null;



	public static MetaPH get() {
		return new MetaPH();
	}
	public static MetaPH get(final double value) {
		return new MetaPH(value);
	}
	public static MetaPH get(final String value) {
		return new MetaPH(value);
	}
	@Override
	public MetaPH clone() {
		return new MetaPH(this);
	}



	public MetaPH() {
	}
	public MetaPH(final MetaPH value) {
		this.set(value);
	}
	public MetaPH(final String value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
	public void set(final MetaPH value) {
		this.value = value.value;
	}
	public void set(final int value) {
		this.value = new Integer(value);
	}
	public void set(final Integer value) {
		this.value =
				value == null
				? null
				: new Integer(value.intValue());
	}
	@Override
	public void set(final String value) {
		if(utils.isEmpty(value)) {
			this.value = null;
		} else {
			final Integer i = utilsNumbers.toInteger(value);
			if(i == null) throw new InvalidMetaValueException(value);
			this.value = i;
		}
	}



	// get value
	@Override
	public Integer getValue() {
		if(this.value == null)
			return null;
		return new Integer(this.value.intValue());
	}
	@Override
	public String getString() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
