package com.growcontrol.server;

import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.xLog;


public class Main {



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.init(argsArray);
		boolean hasStarted = false;
		if (argsTool.getFlagBool(false, "-S", "--server")) {
			gcServerVars.setAppMode(
				gcServerVars.APP_MODE.SERVER_ONLY
			);
			final gcServer server = new gcServer();
			server.Start();
			hasStarted = true;
		}
		// default
		if (!hasStarted) {
			gcServerVars.setAppMode(
				gcServerVars.APP_MODE.SERVER_ONLY
			);
			final gcServer server = new gcServer();
			server.Start();
			hasStarted = true;
		}
		if (xVars.debug()) {
			xLog.getRoot()
				.fine("Initial thread returning..");
		}
		ThreadUtils.Sleep(150L);
	}



	public static void StopServer() {
		final gcServerVars.APP_MODE appMode =
				gcServerVars.getAppMode();
		final boolean isServerOnly =
			gcServerVars.APP_MODE.SERVER_ONLY.equals(appMode);
		if (isServerOnly) {
			final Thread stopThread =
				new Thread() {
					@Override
					public void run() {
						ThreadUtils.Sleep(250L);
						System.exit(0);
					}
				};
			stopThread.start();
		}
	}



}
