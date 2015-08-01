package com.growcontrol.client.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.xLogger.xLog;


public class SavedProfileConfig {

	public final String name;
	public final String host;
	public final int    port;
	public final String user;
	public final String pass;



	public static Map<String, SavedProfileConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, SavedProfileConfig> profiles =
				new HashMap<String, SavedProfileConfig>();
		for(final Object o : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								o
						);
				final SavedProfileConfig profile = get(datamap);
				profiles.put(profile.name, profile);
			} catch (Exception e) {
xLog.getRoot("config").trace(e);
			}
		}
		return profiles;
	}
	public static SavedProfileConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		return new SavedProfileConfig(
				config.getString(gcClientDefines.PROFILE_NAME),
				config.getString(gcClientDefines.PROFILE_HOST),
				config.getInt(gcClientDefines.PROFILE_PORT, gcClientDefines.DEFAULT_SOCKET_PORT),
				config.getString(gcClientDefines.PROFILE_USER),
				config.getString(gcClientDefines.PROFILE_PASS)
		);
	}



	public SavedProfileConfig(final String name,
			final String host, final int port,
			final String user, final String pass) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}



}
