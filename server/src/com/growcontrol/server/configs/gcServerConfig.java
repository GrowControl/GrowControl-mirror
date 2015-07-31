package com.growcontrol.server.configs;

import java.util.Map;

import com.growcontrol.server.gcServer;
import com.growcontrol.server.gcServerDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsNumbers;
import com.poixson.commonjava.Utils.xTime;
import com.poixson.commonjava.Utils.xTimeU;
import com.poixson.commonjava.Utils.threads.xThreadPool;
import com.poixson.commonjava.xLogger.xLevel;
import com.poixson.commonjava.xLogger.xLog;


public final class gcServerConfig extends xConfig {



	public gcServerConfig(final Map<String, Object> datamap) {
		super(datamap);
	}



	// version
	public String getVersion() {
		final String value = this.getString(gcServerDefines.CONFIG_VERSION);
		if(utils.isEmpty(value))
			return null;
		return value;
	}



	// log level
	public xLevel getLogLevel() {
		final String value = this.getString(gcServerDefines.CONFIG_LOG_LEVEL);
		if(utils.isEmpty(value))
			return null;
		return xLevel.parse(value);
	}



	// debug
	public Boolean getDebug() {
		return this.getBoolean(gcServerDefines.CONFIG_DEBUG);
	}



	// prompt ticker
	public boolean getPromptTickerEnabled() {
		if(!exists(gcServerDefines.CONFIG_PROMPT_TICKER))
			return gcServerDefines.DEFAULT_PROMPT_TICKER;
		return this.getBool(gcServerDefines.CONFIG_PROMPT_TICKER, gcServerDefines.DEFAULT_PROMPT_TICKER);
	}



	// tick interval
	public xTime getTickInterval() {
		if(!exists(gcServerDefines.CONFIG_TICK_INTERVAL))
			return gcServerDefines.DEFAULT_TICK_INTERVAL;
		{
			final Long value = this.getLong(gcServerDefines.CONFIG_TICK_INTERVAL);
			if(value != null)
				return xTime.get(value, xTimeU.MS);
		}
		{
			final String value = this.getString(gcServerDefines.CONFIG_TICK_INTERVAL);
			if(utils.notEmpty(value))
				return xTime.parse(value);
		}
		return gcServerDefines.DEFAULT_TICK_INTERVAL;
	}



	// logic threads (0 uses main thread)
	public int getLogicThreads() {
		if(!exists(gcServerDefines.CONFIG_LOGIC_THREADS))
			return gcServerDefines.DEFAULT_LOGIC_THREADS;
		final Integer value = this.getInteger(gcServerDefines.CONFIG_LOGIC_THREADS);
		return utilsNumbers.MinMax(value.intValue(), 0, xThreadPool.POOL_LIMIT);
	}



//	// zones (rooms)
//	public void populateZones(final Collection<String> zones) {
//		if(zones == null) throw new NullPointerException();
//		if(exists(gcServerDefines.CONFIG_ZONES))
//			zones.addAll(this.getStringList(gcServerDefines.CONFIG_ZONES));
//	}



	public Set<NetConfig> getSocketConfigs() {
		final Set<Object> dataset = this.getSet(
				Object.class,
				gcServerDefines.CONFIG_SOCKETS
		);
		final Set<NetConfig> configs = new HashSet<NetConfig>();
		if(utils.notEmpty(dataset)) {
			for(final Object o : dataset) {
				try {
					final Map<String, Object> datamap = utilsObject.castMap(String.class, Object.class, o);
					if(datamap == null) throw new RuntimeException("Failed to load socket config");
					final gcServerSocketConfig gcCfg = new gcServerSocketConfig(datamap);
					final NetConfig cfg = gcCfg.get();
					if(cfg == null) throw new RuntimeException("Failed to load socket config");
					configs.add(cfg);
				} catch (Exception e) {
					this.log().trace(e);
				}
			}
			// log configs
			for(final NetConfig dao : configs) {
				if(dao.enabled) {
					this.log().getWeak("sockets").finer(
							dao.host+":"+Integer.toString(dao.port)+
							(dao.ssl ? " (ssl)" : " (raw)")
					);
				}
			}
		}
		return configs;
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = gcServer.log()
				.getWeak("config");
		return this._log;
	}



}
