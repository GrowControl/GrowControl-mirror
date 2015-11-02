package com.growcontrol.common.meta.metaTypes;

import com.growcontrol.common.meta.MetaType;
import com.poixson.commonjava.Utils.xRunnable;
import com.poixson.commonjava.Utils.exceptions.RequiredArgumentException;


public class MetaTrigger extends MetaType {
	private static final long serialVersionUID = 31L;
	public static final String TYPE_NAME = "Trigger";

	protected volatile xRunnable run = null;



	public static MetaTrigger get() {
		return new MetaTrigger();
	}
	public static MetaTrigger get(final String value) {
		final MetaTrigger meta = new MetaTrigger();
		meta.set(value);
		return meta;
	}
	@Override
	public MetaTrigger clone() {
		return new MetaTrigger(this);
	}



	public MetaTrigger() {
	}
	public MetaTrigger(final MetaTrigger value) {
		this.set(value);
	}
	public MetaTrigger(final String value) {
		this.set(value);
	}



	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}



	// set value
	public void set(final MetaTrigger value) {
		this.run = value.run;
	}
	public void set(final Runnable run) {
		this.run = xRunnable.cast(run);
	}
	public void set(final xRunnable run) {
		if(run == null) throw new RequiredArgumentException("run");
		this.run = run;
	}
	@Override
	public void set(final String value) {
		throw new UnsupportedOperationException();
	}



	// get value
	@Override
	public xRunnable getValue() {
		return this.run;
	}
	@Override
	public String getString() {
		if(this.run == null)
			return null;
		return this.run.getClass().getName();
	}



}
