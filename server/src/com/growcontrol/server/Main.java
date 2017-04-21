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
			final gcServer server = new gcServer();
			server.Start();
			hasStarted = true;
		}
		// default
		if (!hasStarted) {
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



}
