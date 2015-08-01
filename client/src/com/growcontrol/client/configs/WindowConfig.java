package com.growcontrol.client.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.client.gcClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.xLogger.xLog;


public class WindowConfig {

	public final String name;
	public final Integer x;
	public final Integer y;
	public final Integer w;
	public final Integer h;



	public static Map<String, WindowConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, WindowConfig> configs = new HashMap<String, WindowConfig>();
		for(final Object o : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								o
						);
				final WindowConfig window = get(datamap);
				configs.put(window.getWindowName(), window);
			} catch (Exception e) {
xLog.getRoot("config").trace(e);
			}
		}
		return configs;
	}
	public static WindowConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		return new WindowConfig(
				config.getString (gcClientDefines.CONFIG_WINDOW_NAME),
				config.getInteger(gcClientDefines.CONFIG_WINDOW_X),
				config.getInteger(gcClientDefines.CONFIG_WINDOW_Y),
				config.getInteger(gcClientDefines.CONFIG_WINDOW_W),
				config.getInteger(gcClientDefines.CONFIG_WINDOW_H)
		);
	}



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



}
