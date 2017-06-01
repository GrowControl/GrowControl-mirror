package com.growcontrol.client;

import com.growcontrol.server.gcServer;
import com.growcontrol.server.gcServerVars;
import com.growcontrol.server.gcServerVars.APP_MODE;
import com.poixson.utils.Failure;
import com.poixson.utils.Keeper;
import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.xLog;


//TODO: https://docs.oracle.com/cd/E19830-01/819-4721/6n6rrfqlv/index.html
//TODO: http://blog.markturansky.com/archives/21
public abstract class MainBootstrap {

	private static Keeper keeper;



	protected MainBootstrap() {
		if (keeper != null) throw new RuntimeException("MainBootstrap already loaded?");
		keeper = Keeper.get();
	}



	protected abstract gcClient newClient();



//TODO: detect gui/shell
	public void doMain(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.init(argsArray);
		// app mode flags
		{
			final boolean internalMode =
				argsTool.getFlagBool(false, "-I", "--internal");
			final boolean serverMode =
				argsTool.getFlagBool(false, "-S", "--server");
			final boolean clientMode =
				argsTool.getFlagBool(false, "-C", "--client");
			// internal mode
			if (internalMode) {
				gcServerVars
					.setAppMode(APP_MODE.INTERNAL);
			} else
			// client and local server
			if (serverMode && clientMode) {
				gcServerVars
					.setAppMode(APP_MODE.SERVER_CLIENT);
			} else
			// server only
			if (serverMode) {
				gcServerVars
					.setAppMode(APP_MODE.SERVER_ONLY);
			} else
			// client only
			if (clientMode) {
				gcServerVars
					.setAppMode(APP_MODE.CLIENT_ONLY);
			}
		}
		// start app mode
		{
			final APP_MODE appMode = gcServerVars.getAppMode();
			// default
			if (appMode == null) {
//TODO:
throw new RuntimeException("UNFINISHED MODE");
			} else {
				gcServer server = null;
				gcClient client = null;
				switch (appMode) {
				case INTERNAL:
//TODO:
//					final gcClient client = newClient();
//					client.Start();
throw new RuntimeException("UNFINISHED MODE");
				case SERVER_CLIENT:
					try {
						server = new gcServer();
						server.Start();
					} catch (Exception e) {
						Failure.fail(e);
					}
					ThreadUtils.Sleep(100L);
					try {
						client = this.newClient();
						client.Start();
					} catch (Exception e) {
						Failure.fail(e);
					}
					break;
				case SERVER_ONLY:
					try {
						server = new gcServer();
						server.Start();
					} catch (Exception e) {
						Failure.fail(e);
					}
					break;
				case CLIENT_ONLY:
					try {
						client = this.newClient();
						client.Start();
					} catch (Exception e) {
						Failure.fail(e);
					}
					break;
				default:
					//TODO:
					throw new RuntimeException("UNFINISHED MODE");
				}
			}
		}
		// finished starting
		if (xVars.debug()) {
			xLog.getRoot()
				.fine("Initial thread returning..");
		}
		ThreadUtils.Sleep(100L);
	}



}
