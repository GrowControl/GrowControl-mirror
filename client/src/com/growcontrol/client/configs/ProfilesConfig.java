package com.growcontrol.client.configs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;


public class ProfilesConfig extends xConfig {

	private volatile LinkedHashMap<String, SavedProfile> profiles = null;
	private final Object profilesLock = new Object();



	// new config instance
	public ProfilesConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// last profile used
	public SavedProfile getLastUsedProfile() {
		final String value = this.getString(gcClientDefines.PROFILE_LAST_USED);
		if(utils.isEmpty(value))
			return null;
		return this.getProfile(value);
	}



	// auto connect
	public SavedProfile getAutoConnectProfile() {
		// boolean value
		final Boolean bool = this.getBoolean(gcClientDefines.PROFILE_AUTO_CONNECT);
		if(bool != null) {
			return bool.booleanValue()
					? this.getLastUsedProfile()
					: null;
		}
		// string value
		final String value = this.getString(gcClientDefines.PROFILE_AUTO_CONNECT);
		if(utils.isEmpty(value))
			return null;
		return this.getProfile(value);
	}



	public LinkedHashMap<String, SavedProfile> getProfiles() {
		if(this.profiles == null) {
			synchronized(this.profilesLock) {
				if(this.profiles == null) {
					final List<Object> dataset = this.getList(
							Object.class,
							gcClientDefines.PROFILES
					);
					this.profiles = SavedProfile.get(dataset);
				}
			}
		}
		return this.profiles;
	}
	public SavedProfile getProfile(final String name) {
		if(this.profiles == null)
			this.getProfiles();
		return this.profiles.get(name);
	}



}
