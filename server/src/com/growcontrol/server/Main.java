package com.growcontrol.server;

import com.growcontrol.server.gcServerVars.APP_MODE;
import com.poixson.utils.Keeper;
import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.xLog;


public class Main {

	@SuppressWarnings("unused")
	private final Keeper keeper = Keeper.get();



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.init(argsArray);
		boolean hasStarted = false;
		// debug flag
		if (argsTool.getFlagBool(false, "-d", "--debug")) {
			xVars.debug(true);
		}
		// app mode flags
		{
			final boolean serverMode =
				argsTool.getFlagBool(false,  "-S", "--server");
			// server only mode
			if (serverMode) {
				gcServerVars.setAppMode(
					APP_MODE.SERVER_ONLY
				);
			}
		}
		// start app mode
		{
			final APP_MODE appMode =
				gcServerVars.getAppMode();
			gcServer server = null;
			switch (appMode) {
			case SERVER_ONLY:
				server = new gcServer();
				server.Start();
				break;
			default:
				server = new gcServer();
				server.Start();
				break;
			}
		}
		if (xVars.debug()) {
			xLog.getRoot()
				.fine("Initial thread returning..");
		}
		ThreadUtils.Sleep(150L);
	}



	public static void StopServer() {
		final APP_MODE appMode =
				gcServerVars.getAppMode();
		final boolean isServerOnly =
			APP_MODE.SERVER_ONLY.equals(appMode);
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
