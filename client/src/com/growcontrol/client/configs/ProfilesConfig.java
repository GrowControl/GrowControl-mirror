package com.growcontrol.client.configs;

import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;


public class ProfilesConfig extends xConfig {

	private volatile Map<String, SavedProfileConfig> profiles = null;



	public ProfilesConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// last profile used
	public String getLastUsedProfile() {
		final String value = this.getString(gcClientDefines.PROFILE_LAST_USED);
		if(utils.isEmpty(value))
			return null;
		return value;
	}



	// auto connect
	public String getAutoConnect() {
		// boolean value
		final Boolean bool = this.getBoolean(gcClientDefines.PROFILE_AUTO_CONNECT);
		if(bool != null) {
			return bool.booleanValue()
					? this.getLastUsedProfile()
					: null;
		}
		// string value
		final String value = this.getString(gcClientDefines.PROFILE_AUTO_CONNECT);
		if(!utils.isEmpty(value))
			return value;
		return null;
	}
	public SavedProfileConfig getAutoConnectProfile() {
		final String name = this.getAutoConnect();
		if(utils.isEmpty(name))
			return null;
		return this.getProfile(name);
	}



	public Map<String, SavedProfileConfig> getProfiles() {
		if(this.profiles == null) {
			final Set<Object> dataset = this.getSet(
					Object.class,
					gcClientDefines.PROFILES
			);
			this.profiles = SavedProfileConfig.get(dataset);
		}
		return this.profiles;
	}
	public SavedProfileConfig getProfile(final String name) {
		if(this.profiles == null)
			this.getProfiles();
		return this.profiles.get(name);
	}



}
