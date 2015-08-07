package com.growcontrol.client.configs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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



	public static LinkedHashMap<String, SavedProfileConfig> get(final List<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final LinkedHashMap<String, SavedProfileConfig> profiles =
				new LinkedHashMap<String, SavedProfileConfig>();
		for(final Object obj : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								obj
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
		final String name = config.getString(gcClientDefines.PROFILE_NAME);
		final boolean ssl = config.getBool(gcClientDefines.PROFILE_SSL, false);
		final String host = config.getString(gcClientDefines.PROFILE_HOST);
		final int    port = config.getInt   (
				gcClientDefines.PROFILE_PORT,
				ssl
				? gcClientDefines.DEFAULT_SOCKET_PORT
				: gcClientDefines.DEFAULT_SOCKET_PORT_SSL
		);
		final String user = config.getString(gcClientDefines.PROFILE_USER);
		final String pass = config.getString(gcClientDefines.PROFILE_PASS);
		return new SavedProfileConfig(
				name,
				host,
				port,
				user,
				pass
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
