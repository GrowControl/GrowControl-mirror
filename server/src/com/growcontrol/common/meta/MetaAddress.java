package com.growcontrol.common.meta;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsString;


public class MetaAddress {

	public final String hash;
	protected final Map<String, String> tags = new HashMap<String, String>();

	protected static final Map<String, MetaAddress> addresses = new ConcurrentHashMap<String, MetaAddress>();



	public static MetaAddress get(final String address) {
		if(utils.isEmpty(address))
			return getRandom();
		MetaAddress addr = addresses.get(address);
		if(addr == null) {
			synchronized(addresses) {
				addr = addresses.get(address);
				if(addr == null) {
					addr = new MetaAddress(address);
					addresses.put(address, addr);
				}
			}
		}
		return addr;
	}
	// generate random unique hash
	public static MetaAddress getRandom() {
		final MetaAddress addr;
		synchronized(addresses) {
			String hash = null;
			for(int i=0; i<100; i++) {
				// generate random hash
				hash = utilsString.RandomString(MetaRouter.ADDRESS_LENGTH);
				// be sure hash is unique (rarely a problem)
				if(!addresses.containsKey(hash))
					break;
				MetaRouter.log().finest("Duplicate address hash generated, trying again..");
			}
			if(utils.isEmpty(hash))
				throw new RuntimeException("Failed to generate a unique address hash");
			MetaRouter.log().finest("Generated a unique random address hash: "+hash);
			addr = get(hash);
		}
		return addr;
	}
	protected MetaAddress(final String hash) {
		this.hash = hash;
	}



	public MetaAddress setTag(final String key, final String value) {
		if(utils.isEmpty(key)) throw new NullPointerException();
		synchronized(this.tags) {
			this.tags.put(key, value);
		}
		return this;
	}
	public String getTag(final String key) {
		final String value;
		synchronized(this.tags) {
			value = this.tags.get(key);
		}
		return value;
	}



	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder();
		str.append(this.hash);
		if(utils.notEmpty(this.tags)) {
			boolean first = true;
			str.append("[");
			for(final Entry<String, String> entry : this.tags.entrySet()) {
				if(first)
					first = false;
				else
					str.append(",");
				str.append(entry.getKey())
					.append("=")
					.append(entry.getValue());
			}
			str.append("]");
		}
		return str.toString();
	}



}
