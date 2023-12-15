package com.growcontrol.server;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xConsolePrompt;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.logger.formatters.xLogFormat_Color;
import com.poixson.logger.handlers.xLogHandler_ConsolePrompt;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.tools.Keeper;


public class Main {

	// keep things in memory
	public static final Keeper keeper = Keeper.get();

	// app instance
	public static final AtomicReference<gcServer> instance_server = new AtomicReference<gcServer>(null);



	public static void main(final String[] args) {
		final gcServer server = new gcServer(args);
		instance_server.set(server);
		// logger
		{
			final xLog log = xLogRoot.Get();
			final xConsolePrompt console = new xConsolePrompt();
			final xLogFormat_Color format = new xLogFormat_Color();
			log.setConsole(console);
			final xLogHandler_ConsolePrompt handler = console.getHandler();
			handler.setFormat(format);
			log.addHandler(handler);
		}
		// start main thread pool
		try {
			xThreadPool_Main.Get()
				.run();
		} catch (Exception e) {
			xLog.Get().trace(e);
		}
	}



}
