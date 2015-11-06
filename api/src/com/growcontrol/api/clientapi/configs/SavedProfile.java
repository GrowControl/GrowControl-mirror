package com.growcontrol.api.clientapi.configs;

import java.util.Map;

import com.growcontrol.api.clientapi.apiClientDefines;
import com.growcontrol.common.gcCommonDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xHashable;


public class SavedProfile extends xConfig implements xHashable {

	private final String name;
	private final boolean ssl;
	private final String host;
	private final int    port;
	private final String user;
	private final String pass;



	public SavedProfile(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.name = this.getString(apiClientDefines.PROFILE_NAME);
		this.ssl  = this.getBool(  apiClientDefines.PROFILE_SSL, false);
		this.host = this.getString(apiClientDefines.PROFILE_HOST);
		if(utils.isEmpty(this.name)) throw new xConfigException("Name is missing from profiles config!");
		if(utils.isEmpty(this.host)) throw new xConfigException("Host is missing from profiles config!");
		this.port = this.getInt (
				apiClientDefines.PROFILE_PORT,
				gcCommonDefines.DEFAULT_SOCKET_PORT(this.ssl)
		);
		this.user = this.getString(apiClientDefines.PROFILE_USER);
		this.pass = this.getString(apiClientDefines.PROFILE_PASS);
	}
//	public SavedProfile(final String name,
//			final String host, final int port,
//			final String user, final String pass)
//			throws xConfigException {
//		this.name = name;
//		this.host = host;
//		this.port = port;
//		this.user = user;
//		this.pass = pass;
//	}



	public String getName() {
		return this.name;
	}
	public boolean useSSL() {
		return this.ssl;
	}
	public String getHost() {
		return this.host;
	}
	public int getPort() {
		return this.port;
	}
	public String getUser() {
		return this.user;
	}
//TODO: secure this better
	public String getPass() {
		return this.pass;
	}



	@Override
	public String toString() {
		return this.getName();
	}
	@Override
	public String getKey() {
		return this.getName();
	}
	@Override
	public boolean matches(final xHashable hashable) {
		if(hashable == null || !(hashable instanceof SavedProfile) )
			return false;
		final SavedProfile profile = (SavedProfile) hashable;
		return this.getKey().equalsIgnoreCase(profile.getKey());
	}



}
