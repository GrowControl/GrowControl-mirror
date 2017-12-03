package com.growcontrol.server;

import com.growcontrol.server.gcServerVars.APP_MODE;
import com.poixson.utils.Keeper;
import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.xLog;


public class Main {

	// keep things in memory
	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();

	// app instance
	private static gcServer server = null;



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
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
		server = new gcServer();
		server.start();
//		{
//			final APP_MODE appMode =
//				gcServerVars.getAppMode();
//			gcServer server = null;
//			switch (appMode) {
//			case SERVER_ONLY:
//				server = new gcServer();
//				server.Start();
//				break;
//			default:
//				server = new gcServer();
//				server.Start();
//				break;
//			}
//		}
		if (xVars.debug()) {
			xLog.getRoot()
				.fine("Initial thread returning..");
		}
		ThreadUtils.Sleep(100L);
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
