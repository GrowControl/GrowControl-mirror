package com.growcontrol.server.configs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.growcontrol.common.configs.gcAppConfig;
import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.Utils.xTimeU;
import com.poixson.commonjava.Utils.xTimeUnmodifiable;
import com.poixson.commonjava.Utils.threads.xThreadPool;


public final class gcServerConfig extends gcAppConfig {

	public final boolean promptTickerEnabled;
	public final xTime   tickInterval;
	public final int     logicThreads;
	public final int     socketBacklog;

	private volatile Map<String, NetServerConfig> netConfigs = null;
	private final Object netLock = new Object();



	public gcServerConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		// prompt ticker enabled
		this.promptTickerEnabled = this.getBool(
				gcServerDefines.CONFIG_PROMPT_TICKER,
				gcServerDefines.DEFAULT_PROMPT_TICKER
		);
		// tick interval
		{
			Long   longValue = null;
			String strValue  = null;
			xTime time = null;
			try {
				longValue = this.getLong(gcServerDefines.CONFIG_TICK_INTERVAL);
			} catch (xConfigException ignore) {}
			try {
				strValue = this.getString(gcServerDefines.CONFIG_TICK_INTERVAL);
			} catch (xConfigException ignore) {}
			if(longValue != null)
				time = xTime.get(longValue.longValue(), xTimeU.MS);
			if(time == null && utils.notEmpty(strValue))
				time = xTime.parse(strValue);
			if(time == null)
				time = gcServerDefines.DEFAULT_TICK_INTERVAL;
			this.tickInterval = xTimeUnmodifiable.cast(time);
		}
		// logic threads
		{
			final int intValue = this.getInt(
					gcServerDefines.CONFIG_LOGIC_THREADS,
					gcServerDefines.DEFAULT_LOGIC_THREADS
			);
			this.logicThreads = utilsNumbers.MinMax(
					intValue,
					0,
					xThreadPool.POOL_LIMIT
			);
		}
		// socket backlog
		this.socketBacklog = this.getInt(
				gcServerDefines.CONFIG_SOCKET_BACKLOG,
				gcServerDefines.DEFAULT_SOCKET_BACKLOG
		);
	}



	// prompt ticker
	public boolean getPromptTickerEnabled() {
		return this.promptTickerEnabled;
	}
	// tick interval
	public xTime getTickInterval() {
		return this.tickInterval;
	}
	// logic threads (0 uses main thread)
	public int getLogicThreads() {
		return this.logicThreads;
	}
	// socket backlog
	public int getSocketBacklog() {
		return this.socketBacklog;
	}



	// socket server configs
	public Map<String, NetServerConfig> getNetConfigs()
			throws xConfigException {
		if(this.netConfigs == null) {
			synchronized(this.netLock) {
				final List<xConfig> configList = this.getConfigList(
						gcServerDefines.CONFIG_SOCKETS,
						NetServerConfig.class
				);
				final Map<String, NetServerConfig> configMap = new HashMap<String, NetServerConfig>();
				for(final xConfig cfg : configList) {
					configMap.put(
							((NetServerConfig) cfg).getKey(),
							((NetServerConfig) cfg)
					);
				}
				this.netConfigs = Collections.unmodifiableMap(configMap);
			}
//			final Set<Object> dataset = this.getSet(
//					Object.class,
//					gcServerDefines.CONFIG_SOCKETS
//			);
//			this.netConfigs = NetServerConfig.get(dataset);
		}
		return this.netConfigs;
	}
	public NetServerConfig getNetConfig(final String name) {
//		if(this.netConfigs == null)
//			this.getNetConfigs();
//		return this.netConfigs.get(name);
return null;
	}



}
