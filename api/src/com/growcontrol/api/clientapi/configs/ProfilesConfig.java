package com.growcontrol.api.clientapi.configs;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.growcontrol.api.clientapi.apiClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;


public class ProfilesConfig extends xConfig {

	public final String lastProfile;
	public final boolean autoConnect;

	private volatile Map<String, SavedProfile> profiles = null;
	private final Object profilesLock = new Object();



	// new config instance
	public ProfilesConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.lastProfile = this.getStr(
				apiClientDefines.PROFILE_LAST_PROFILE,
				apiClientDefines.DEFAULT_LAST_PROFILE
		);
		this.autoConnect = this.getBool(
				apiClientDefines.PROFILE_AUTO_CONNECT,
				apiClientDefines.DEFAULT_AUTO_CONNECT
		);
	}



	// last profile used
	public SavedProfile getLastUsedProfile() {
		if(utils.isEmpty(this.lastProfile))
			return null;
		return this.getProfile(this.lastProfile);
	}
	// auto connect
	public boolean isAutoConnect() {
		return this.autoConnect;
	}
	public SavedProfile getAutoConnectProfile() {
		if(!this.autoConnect)
			return null;
		final SavedProfile profile = this.getLastUsedProfile();
		if(profile != null)
			return profile;
		return null;
	}



	public Map<String, SavedProfile> getProfiles()
			throws xConfigException {
		if(this.profiles == null) {
			synchronized(this.profilesLock) {
				if(this.profiles == null) {
					final List<xConfig> configsList =
						this.getConfigList(
							apiClientDefines.PROFILES,
							SavedProfile.class
					);
					final LinkedHashMap<String, SavedProfile> profilesMap =
							new LinkedHashMap<String, SavedProfile>();
					for(final xConfig cfg : configsList) {
						final SavedProfile p = (SavedProfile) cfg;
						profilesMap.put(p.getKey(), p);
					}
					this.profiles = Collections.unmodifiableMap(profilesMap);
				}
			}
		}
		return this.profiles;
	}
	public SavedProfile getProfile(final String name) {
		try {
			final Map<String, SavedProfile> profiles = this.getProfiles();
			return profiles.get(name);
		} catch (xConfigException ignore) {}
		return null;
	}



}
