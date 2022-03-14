/*
package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;
import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaEC extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "EC";

	protected volatile Integer value = null;



	public static MetaEC get() {
		return new MetaEC();
	}
	public static MetaEC get(final int value) {
		return new MetaEC(value);
	}
	public static MetaEC get(final String value) {
		return new MetaEC(value);
	}
	@Override
	public MetaEC clone() {
		return new MetaEC(this);
	}



	public MetaEC() {
	}
	public MetaEC(final int value) {
		this.set(value);
	}
	public MetaEC(final String value) {
		this.set(value);
	}
	public MetaEC(final MetaEC value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
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
	public void set(final MetaEC value) {
		this.value = value.value;
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
*/
