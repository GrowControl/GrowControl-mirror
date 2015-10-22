package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;
import com.growcontrol.common.meta.exceptions.InvalidMetaValueException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaPH extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "pH";

	protected volatile Double value = null;



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
	public MetaPH(final double value) {
		this.set(value);
	}
	public MetaPH(final String value) {
		this.set(value);
	}
	public MetaPH(final MetaPH value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
	public void set(final double value) {
		this.value = new Double(value);
	}
	public void set(final Double value) {
		this.value =
				value == null
				? null
				: new Double(value.doubleValue());
	}
	@Override
	public void set(final String value) {
		if(utils.isEmpty(value)) {
			this.value = null;
		} else {
			final Double d = utilsNumbers.toDouble(value);
			if(d == null) throw new InvalidMetaValueException(value);
			this.value = d;
		}
	}
	public void set(final MetaPH value) {
		this.value = value.value;
	}



	// get value
	@Override
	public Double getValue() {
		if(this.value == null)
			return null;
		return new Double(this.value.doubleValue());
	}
	@Override
	public String getString() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
