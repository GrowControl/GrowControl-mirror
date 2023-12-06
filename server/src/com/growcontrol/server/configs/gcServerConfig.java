package com.growcontrol.server.configs;

import java.util.Map;

import com.poixson.logger.xLevel;
import com.poixson.tools.config.xConfig;


public final class gcServerConfig extends xConfig {

	private final String  version;
	private final xLevel  level;
	private final boolean debug;



	public gcServerConfig(final Map<String, Object> datamap) {
//TODO
//			throws xConfigException {
		super(datamap);
//TODO
this.version = "";
this.level = xLevel.ALL;
this.debug = true;
//// config version
//this.version = this.getString(gcCommonDefines.CONFIG_VERSION);
//if (isEmpty(this.version)) throw new xConfigException("Version is missing from config!");
//// log level
//{
//	final String levelStr = this.getString(gcCommonDefines.CONFIG_VERSION);
//	final xLevel lvl = xLevel.parse(levelStr);
//	this.level =
//		lvl == null
//		? gcCommonDefines.DEFAULT_LOG_LEVEL
//		: lvl;
//}
//// debug mode
//this.debug = this.getBool(
//	gcCommonDefines.CONFIG_DEBUG,
//	gcCommonDefines.DEFAULT_DEBUG
//);
	}



	// config version
	public String getVersion() {
		return this.version;
	}
	// log level
	public xLevel getLogLevel() {
		return this.level;
	}
	// debug mode
	public boolean getDebug() {
		return this.debug;
	}



}
/*
	private final boolean promptTickerEnabled;
	private final xTime   tickInterval;
	private final int     logicThreads;
	private final int     socketBacklog;

	private volatile Map<String, NetServerConfig> netConfigs = null;
	private final Object netLock = new Object();



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
			if (longValue != null) {
				time = xTime.get(longValue.longValue(), xTimeU.MS);
			}
			if (time == null && !IsEmpty(strValue))
				time = xTime.parse(strValue);
			if (time == null) {
				time = gcServerDefines.DEFAULT_TICK_INTERVAL;
			}
			this.tickInterval = time.setFinal();
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
		if (this.netConfigs == null) {
			synchronized(this.netLock) {
				final List<xConfig> configList = this.getConfigList(
						gcServerDefines.CONFIG_SOCKETS,
						NetServerConfig.class
				);
				final Map<String, NetServerConfig> configMap = new HashMap<String, NetServerConfig>();
				for (final xConfig cfg : configList) {
					configMap.put(
						((NetServerConfig) cfg).getKey(),
						((NetServerConfig) cfg)
					);
				}
				this.netConfigs = Collections.unmodifiableMap(configMap);
			}
//			final Set<Object> dataset = this.getSet(
//				Object.class,
//				gcServerDefines.CONFIG_SOCKETS
//			);
//			this.netConfigs = NetServerConfig.get(dataset);
		}
		return this.netConfigs;
	}
	public NetServerConfig getNetConfig(final String name) {
//		if (this.netConfigs == null) {
//			this.getNetConfigs();
//		}
//		return this.netConfigs.get(name);
return null;
	}



}
*/
