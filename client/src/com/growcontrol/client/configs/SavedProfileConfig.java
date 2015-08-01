package com.growcontrol.client.configs;

import java.util.Map;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;


public class SavedServerProfile extends xConfig {

	public final String name;
	public final String host;
	public final int    port;
	public final String user;
	public final String pass;




	public SavedServerProfile(final String name,
			final String host, final int port,
			final String user, final String pass) {
		super(null);
		this.name = name;
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	public SavedServerProfile(final Map<String, Object> data) {
		super(data);
		this.name = this.getString(NAME);
		this.host = this.getString(HOST);
		this.port = this.getInteger(PORT).intValue();
		this.user = this.getString(USER);
		this.pass = this.getString(PASS);
	}



	public String formatForList() {
		final StringBuilder str = new StringBuilder();
		str.append(this.name);
		str.append("  ( ");
		if(utils.isEmpty(this.host) || "localhost".equalsIgnoreCase(this.host))
			str.append("localhost");
		else
			str.append(this.host);
		if(this.port != gcClientConfig.DEFAULT_LISTEN_PORT)
			str.append(":").append(this.port);
		str.append(" )");
		return str.toString();
	}



}
