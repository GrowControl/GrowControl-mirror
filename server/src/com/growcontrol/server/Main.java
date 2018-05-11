package com.growcontrol.server;

import com.growcontrol.server.gcServerVars.APP_MODE;
import com.poixson.app.xVars;
import com.poixson.logger.xLog;
import com.poixson.tools.Keeper;
import com.poixson.tools.ShellArgs;
import com.poixson.utils.ThreadUtils;


public class Main {

	// keep things in memory
	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();

	// app instance
	private static gcServer server = null;



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgs argsTool = ShellArgs.Init(argsArray);
		// app mode flags
		{
			// server only mode
			if ( argsTool.getFlagBool(false,  "-S", "--server") ) {
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
		if (xVars.isDebug()) {
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
