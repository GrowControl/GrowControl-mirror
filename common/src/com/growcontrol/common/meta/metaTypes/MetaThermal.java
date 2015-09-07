package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.InvalidMetaFormatException;
import com.growcontrol.common.meta.MetaType;
import com.poixson.commonjava.Utils.utilsNumbers;


public class MetaThermal extends MetaType {
	private static final long serialVersionUID = 31L;

	protected volatile Double value = null;



	public static MetaThermal get(final String str) {
		final MetaThermal meta = new MetaThermal();
		meta.set(str);
		return meta;
	}
	@Override
	public MetaThermal clone() {
		final MetaThermal meta = new MetaThermal();
		if(this.value != null)
			meta.set(this.getStringValue());
		return meta;
	}



	// set value
	public void set(final double value) {
		this.value = new Double(value);
	}
	public void set(final Double value) {
		if(value == null)
			this.value = null;
		else
			this.value = new Double(value.doubleValue());
	}
	@Override
	public void set(final String str) {
		if(str == null) {
			this.value = null;
			return;
		}
		final Double d = utilsNumbers.toDouble(str);
		if(d == null) throw new InvalidMetaFormatException("'"+str+"'");
		this.value = d;
	}



	// get value
	public Double value() {
		if(this.value == null)
			return null;
		return new Double(this.value.doubleValue());
	}
	@Override
	public String getStringValue() {
		if(this.value == null)
			return null;
		return this.value.toString();
	}



}
