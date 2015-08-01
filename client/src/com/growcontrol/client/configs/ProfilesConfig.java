package com.growcontrol.client.configs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.xLogger.xLog;


public class SavedServersConfig extends xConfig {

	private volatile Map<String, SavedProfileConfig> profiles = null;

	protected final String lastUsedProfile;
	protected final String autoConnect;
	protected final Set<SavedServerProfile> profiles = new HashSet<SavedServerProfile>();



	@SuppressWarnings("unchecked")
	public SavedServersConfig(final Map<String, Object> data) {
		super(data);
		// last used profile
		this.lastUsedProfile = (String) data.get(LAST_USED);
		// auto connect
		this.autoConnect = (String) data.get(AUTO_CONNECT);
		// profiles
		final ArrayList<Object> list;
		{
			final Object o = data.get(SAVED_SERVERS);
			list = (ArrayList<Object>) o;
		}
		for(final Object o : list) {
			final Map<String, Object> map = (LinkedHashMap<String, Object>) o;
			final SavedServerProfile profile = new SavedServerProfile(map);
			this.profiles.add(profile);
		}
		xLog.getRoot().info("Loaded [ "+Integer.toString(this.profiles.size())+" ] server profiles");
	}



	public String getLastUsedProfile() {
		return this.lastUsedProfile;
	}



	public SavedServerProfile[] getProfiles() {
		return this.profiles.toArray(new SavedServerProfile[0]);
	}



}
