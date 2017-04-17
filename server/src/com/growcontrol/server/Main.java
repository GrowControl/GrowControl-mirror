package com.growcontrol.server;

import com.poixson.app.xApp;
import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;


public class Main {



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.init(argsArray);
		boolean hasStarted = false;
		if (argsTool.getFlagBool(false, "-S", "--server")) {
			xApp.StartApp(
				gcServer.class
			);
			hasStarted = true;
		}
		// default
		if (!hasStarted) {
			xApp.StartApp(
				gcServer.class
			);
		}
//		if (debug) {
//			System.out.println("Initial thread returning..");
//		}
		ThreadUtils.Sleep(150L);
	}



}
