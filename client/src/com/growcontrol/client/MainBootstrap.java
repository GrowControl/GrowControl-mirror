package com.growcontrol.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.growcontrol.server.gcServer;
import com.growcontrol.server.gcServerVars;
import com.poixson.utils.Keeper;
import com.poixson.utils.ShellArgsTool;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.xVars;
import com.poixson.utils.xLogger.xLog;


public class Main {



	public static void main(final String[] argsArray) {
		@SuppressWarnings("unused")
		final Keeper keeper = Keeper.get();
		Utils.InitAll();
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.init(argsArray);
		// flags
		if (argsTool.getFlagBool(false, "-d", "--debug")) {
			xVars.debug(true);
		}
		boolean hasStarted = false;
		// client with internal server
		if (argsTool.getFlagBool(false, "-I", "--internal")) {
			gcServerVars.setAppMode(
				gcServerVars.APP_MODE.INTERNAL
			);
//TODO: internal mode
			final gcClient client = getClient();
			client.Start();
			hasStarted = true;
		} else {
			boolean startServer = false;
			boolean startClient = false;
			if (argsTool.getFlagBool(false, "-S", "--server")) {
				startServer = true;
			}
			if (argsTool.getFlagBool(false, "-C", "--client")) {
				startClient = true;
			}
			// start server and client
			if (startServer && startClient) {
				gcServerVars.setAppMode(
					gcServerVars.APP_MODE.SERVER_CLIENT
				);
			}
			// start server
			if (startServer) {
				gcServerVars.setAppMode(
					gcServerVars.APP_MODE.SERVER_ONLY
				);
				final gcServer server = new gcServer();
				server.Start();
				hasStarted = true;
			}
			// start client
			if (startClient) {
				gcServerVars.setAppMode(
					gcServerVars.APP_MODE.CLIENT_ONLY
				);
				final gcClient client = getClient();
				client.Start();
				hasStarted = true;
			}
		}
		// default start mode
		if ( ! hasStarted) {
//TODO: detect gui
//			hasStarted = true;
//ThreadUtils.displayStillRunning();
		}

System.out.println();
System.out.println(
	xVars.debug()
	? "YES"
	: "NO"
);
System.out.println();

		// done
		xLog.getRoot()
			.fine("Initial thread returning..");
		ThreadUtils.Sleep(150L);
	}



	private static gcClient getClient() {
		final ClientProps props = new ClientProps(Main.class);
		// get app class
		final Class<?> clss;
		try {
			clss = Class.forName(props.mainClass);
		} catch (ClassNotFoundException e) {
//TODO:
			e.printStackTrace();
			return null;
		}
		// get app constructor
		final Constructor<?> construct;
		try {
			construct = clss.getConstructor();
		} catch (NoSuchMethodException e) {
//TODO:
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		}
		// new instance of app class
		final gcClient client;
		try {
			final Object obj = construct.newInstance();
			client = (gcClient) obj;
		} catch (InstantiationException e) {
//TODO:
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return client;
	}



}
