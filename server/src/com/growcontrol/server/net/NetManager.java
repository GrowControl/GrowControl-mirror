package com.growcontrol.server.net;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.growcontrol.server.configs.gcSocketDAO;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xStartable;


public class NetManager implements xStartable {

	private static volatile NetManager instance = null;
	private static final Object instanceLock = new Object();

	protected final Set<gcSocketDAO> configs = new CopyOnWriteArraySet<gcSocketDAO>();



	public static NetManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new NetManager();
			}
		}
		return instance;
	}



	public NetManager() {
		Keeper.add(this);
	}



	public void setConfigs(final Collection<gcSocketDAO> configs) {
		if(configs == null) throw new NullPointerException();
		this.configs.clear();
		if(utils.notEmpty(configs))
			this.configs.addAll(configs);
	}



	@Override
	public void Start() {
	}
	@Override
	public void Stop() {
	}



	@Override
	public void run() {
	}
	@Override
	public boolean isRunning() {
		return false;
	}



}
