package com.growcontrol.api.clientapi.configs;

import java.util.Map;

import com.growcontrol.api.clientapi.apiClientDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonapp.config.xConfigException;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.xHashable;


public class WindowConfig extends xConfig implements xHashable {

	public final String name;

	public final Integer x;
	public final Integer y;
	public final Integer w;
	public final Integer h;



//	// configs from set
//	public static Map<String, WindowConfig> get(final Set<Object> dataset) {
//		if(utils.isEmpty(dataset))
//			return null;
//		final Map<String, WindowConfig> configs = new HashMap<String, WindowConfig>();
//		for(final Object obj : dataset) {
//			try {
//				final Map<String, Object> datamap =
//						utilsObject.castMap(
//								String.class,
//								Object.class,
//								obj
//						);
//				final WindowConfig window = get(datamap);
//				configs.put(window.getWindowName(), window);
//			} catch (Exception e) {
//				xLog.getRoot(LOG_NAME).trace(e);
//			}
//		}
//		return configs;
//	}
//	// config from map
//	public static WindowConfig get(final Map<String, Object> datamap) {
//		if(utils.isEmpty(datamap))
//			return null;
//		final xConfig config = new xConfig(datamap);
//		final String name = config.getString (apiClientDefines.CONFIG_WINDOW_NAME);
//		final int    x    = config.getInteger(apiClientDefines.CONFIG_WINDOW_X);
//		final int    y    = config.getInteger(apiClientDefines.CONFIG_WINDOW_Y);
//		final int    w    = config.getInteger(apiClientDefines.CONFIG_WINDOW_W);
//		final int    h    = config.getInteger(apiClientDefines.CONFIG_WINDOW_H);
//		return new WindowConfig(
//				name,
//				x,
//				y,
//				w,
//				h
//		);
//	}



	// new config instance
	public WindowConfig(final Map<String, Object> datamap)
			throws xConfigException {
		super(datamap);
		this.name = this.getString(apiClientDefines.CONFIG_WINDOW_NAME);
		if(utils.isEmpty(this.name)) throw new xConfigException("Window name is not set in config!");
		final Integer xInt = this.getInteger(apiClientDefines.CONFIG_WINDOW_X);
		final Integer yInt = this.getInteger(apiClientDefines.CONFIG_WINDOW_Y);
		final Integer wInt = this.getInteger(apiClientDefines.CONFIG_WINDOW_W);
		final Integer hInt = this.getInteger(apiClientDefines.CONFIG_WINDOW_H);
		if(xInt == null) throw new xConfigException("Window X is not set in config!");
		if(yInt == null) throw new xConfigException("Window Y is not set in config!");
		if(wInt == null) throw new xConfigException("Window W is not set in config!");
		if(hInt == null) throw new xConfigException("Window H is not set in config!");
		this.x = xInt.intValue();
		this.y = yInt.intValue();
		this.w = wInt.intValue();
		this.h = hInt.intValue();
	}
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
