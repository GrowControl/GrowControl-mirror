package com.growcontrol.client.plugins;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.growcontrol.client.studio.gcStudio;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;
import com.poixson.plugins.loaders.xPluginFactory;


public class gcClientPluginFactory <T extends xJavaPlugin> extends xPluginFactory<T> {

	protected final gcStudio client;



	public gcClientPluginFactory(final gcStudio client) {
		super();
		this.client = client;
	}



	@Override
	protected Constructor<T> getConstruct(final Class<T> clss)
			throws IOException {
		try {
			return clss.getConstructor(gcStudio.class, xPluginManager.class, xPluginYML.class);
		} catch (NoSuchMethodException e) { throw new IOException(e);
		} catch (SecurityException     e) { throw new IOException(e); }
	}

	@Override
	protected T getPlugin(final Constructor<T> construct,
			final xPluginManager<T> manager, final xPluginYML yml)
			throws IOException {
		try {
			return construct.newInstance(this.client, manager, yml);
		} catch (InstantiationException    e) { throw new IOException(e);
		} catch (IllegalAccessException    e) { throw new IOException(e);
		} catch (IllegalArgumentException  e) { throw new IOException(e);
		} catch (InvocationTargetException e) { throw new IOException(e); }
	}



}
