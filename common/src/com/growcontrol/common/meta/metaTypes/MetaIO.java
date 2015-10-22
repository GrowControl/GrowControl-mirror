package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;
import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaIO extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "IO";

	protected volatile Boolean value = null;



	public static MetaIO get() {
		return new MetaIO();
	}
	public static MetaIO get(final boolean value) {
		return new MetaIO(value);
	}
	public static MetaIO get(final String value) {
		return new MetaIO(value);
	}
	@Override
	public MetaIO clone() {
		return new MetaIO(this);
	}



	public MetaIO() {
	}
	public MetaIO(final MetaIO value) {
		this.set(value);
	}
	public MetaIO(final String value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
	public void set(final MetaIO value) {
		this.value = value.value;
	}
	public void set(final boolean value) {
		this.value = new Boolean(value);
	}
	public void set(final Boolean value) {
		this.value =
				value == null
				? null
				: new Boolean(value.booleanValue());
	}
	@Override
	public void set(final String value) {
		if(utils.isEmpty(value)) {
			this.value = null;
		} else {
			final Boolean b = utilsNumbers.toBoolean(value);
			if(b == null) throw new InvalidMetaValueException(value);
			this.value = b;
		}
	}



	// get value
	@Override
	public Boolean getValue() {
		if(this.value == null)
			return null;
		return new Boolean(this.value.booleanValue());
	}
	@Override
	public String getString() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
