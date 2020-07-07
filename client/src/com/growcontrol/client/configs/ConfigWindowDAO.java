/*
package com.growcontrol.client.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.Utils.xHashable;
import com.poixson.commonjava.xLogger.xLog;


public class ConfigWindowDAO extends xConfig {

	public final String name;

	public final Integer x;
	public final Integer y;
	public final Integer w;
	public final Integer h;



	// configs from set
	public static Map<String, WindowConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, WindowConfig> configs = new HashMap<String, WindowConfig>();
		for(final Object obj : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								obj
						);
				final WindowConfig window = get(datamap);
				configs.put(window.getWindowName(), window);
			} catch (Exception e) {
xLog.getRoot("config").trace(e);
			}
		}
		return configs;
	}
	// config from map
	public static WindowConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		final String name = config.getString (gcClientDefines.CONFIG_WINDOW_NAME);
		final int    x    = config.getInteger(gcClientDefines.CONFIG_WINDOW_X);
		final int    y    = config.getInteger(gcClientDefines.CONFIG_WINDOW_Y);
		final int    w    = config.getInteger(gcClientDefines.CONFIG_WINDOW_W);
		final int    h    = config.getInteger(gcClientDefines.CONFIG_WINDOW_H);
		return new WindowConfig(
				name,
				x,
				y,
				w,
				h
		);
	}



	// new config instance
	public WindowConfig(final String name,
			final Integer x, final Integer y,
			final Integer w, final Integer h) {
		if(utils.isEmpty(name)) throw new NullPointerException("name argument is required!");
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;


//		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

//		final int screenWidth  = (int) Math.floor(screenSize.getWidth());
//		final int screenHeight = (int) Math.floor(screenSize.getHeight());

//System.out.println("SCREEN WIDTH:  "+Integer.toString(screenWidth));
//System.out.println("SCREEN HEIGHT: "+Integer.toString(screenHeight));
//System.exit(1);

//		this.w = (w == null ? null : utilsNumbers.MinMax(w, 100, screenWidth));
//		this.h = (h == null ? null : utilsNumbers.MinMax(h, 100, screenHeight));
//		this.x = (x == null ? null : utilsNumbers.MinMax(x, 0, screenWidth  - this.w));
//		this.y = (y == null ? null : utilsNumbers.MinMax(y, 0, screenHeight - this.h));

	}



	public String getWindowName() {
		return this.name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public String getKey() {
		return this.name;
	}
	@Override
	public boolean matches(final xHashable hashable) {
		throw new UnsupportedOperationException();
	}



}
*/
