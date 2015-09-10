package com.growcontrol.common.meta.exceptions;

import com.growcontrol.common.meta.MetaAddress;


public class UnknownAddressException extends RuntimeException {
	private static final long serialVersionUID = 1L;



	public UnknownAddressException() {
		super();
	}
	public UnknownAddressException(final String addressStr) {
		super("Unknown meta address: "+addressStr);
	}
	public UnknownAddressException(final MetaAddress address) {
		this(address.hash);
	}



}
