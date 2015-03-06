package com.growcontrol.server.net;

import io.netty.util.internal.logging.AbstractInternalLogger;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import com.poixson.commonjava.Utils.utilsString;
import com.poixson.commonjava.xLogger.xLog;


public class CustomNettyLogger extends AbstractInternalLogger {
	private static final long serialVersionUID = 1L;

	protected final xLog log;



	public static void Install(final xLog log) {
		InternalLoggerFactory.setDefaultFactory(
				new CustomNettyLoggerFactory(log)
		);
	}
	public static class CustomNettyLoggerFactory extends InternalLoggerFactory {
		private final xLog log;
		public CustomNettyLoggerFactory(final xLog log) {
			this.log = log;
		}
		@Override
		protected InternalLogger newInstance(final String name) {
			return new CustomNettyLogger(this.log);
		}
	}
	protected CustomNettyLogger(final xLog log) {
		super("CustomNettyLogger");
		this.log = log;
	}



	@Override
	public boolean isTraceEnabled() {
		return true;
	}
	@Override
	public boolean isDebugEnabled() {
		return true;
	}
	@Override
	public boolean isInfoEnabled() {
		return true;
	}
	@Override
	public boolean isWarnEnabled() {
		return true;
	}
	@Override
	public boolean isErrorEnabled() {
		return true;
	}



	@Override
	public void trace(final String msg) {
		this.log.finest(msg);
	}
	@Override
	public void trace(final String format, final Object arg) {
		this.log.finest(
				utilsString.FormatMessage(format, arg)
		);
	}
	@Override
	public void trace(final String format, final Object argA, final Object argB) {
		this.log.finest(
				utilsString.FormatMessage(format, argA, argB)
		);
	}
	@Override
	public void trace(final String format, Object... args) {
		this.log.finest(
				utilsString.FormatMessage(format, args)
		);
	}
	@Override
	public void trace(final String msg, final Throwable t) {
		this.log.warning(msg);
		this.log.trace(t);
	}



	@Override
	public void debug(final String msg) {
		this.log.finest(msg);
	}
	@Override
	public void debug(final String format, final Object arg) {
		this.log.finest(
				utilsString.FormatMessage(format, arg)
		);
	}
	@Override
	public void debug(final String format, final Object argA, final Object argB) {
		this.log.finest(
				utilsString.FormatMessage(format, argA, argB)
		);
	}
	@Override
	public void debug(final String format, final Object... args) {
		this.log.finest(
				utilsString.FormatMessage(format, args)
		);
	}
	@Override
	public void debug(final String msg, final Throwable t) {
		this.log.warning(msg);
		this.log.trace(t);
	}



	@Override
	public void info(final String msg) {
		this.log.fine(msg);
	}
	@Override
	public void info(final String format, final Object arg) {
		this.log.fine(
				utilsString.FormatMessage(format, arg)
		);
	}
	@Override
	public void info(final String format, final Object argA, final Object argB) {
		this.log.fine(
				utilsString.FormatMessage(format, argA, argB)
		);
	}
	@Override
	public void info(final String format, final Object... args) {
		this.log.fine(
				utilsString.FormatMessage(format, args)
		);
	}
	@Override
	public void info(final String msg, final Throwable t) {
		this.log.warning(msg);
		this.log.trace(t);
	}



	@Override
	public void warn(final String msg) {
		this.log.warning(msg);
	}
	@Override
	public void warn(final String format, final Object arg) {
		this.log.warning(
				utilsString.FormatMessage(format, arg)
		);
	}
	@Override
	public void warn(final String format, final Object argA, final Object argB) {
		this.log.warning(
				utilsString.FormatMessage(format, argA, argB)
		);
	}
	@Override
	public void warn(final String format, final Object... args) {
		this.log.warning(
				utilsString.FormatMessage(format, args)
		);
	}
	@Override
	public void warn(final String msg, final Throwable t) {
		this.log.warning(msg);
		this.log.trace(t);
	}



	@Override
	public void error(final String msg) {
		this.log.severe(msg);
	}
	@Override
	public void error(final String format, final Object arg) {
		this.log.severe(
				utilsString.FormatMessage(format, arg)
		);
	}
	@Override
	public void error(final String format, final Object argA, final Object argB) {
		this.log.severe(
				utilsString.FormatMessage(format, argA, argB)
		);
	}
	@Override
	public void error(final String format, final Object... args) {
		this.log.severe(
				utilsString.FormatMessage(format, args)
		);
	}
	@Override
	public void error(final String msg, final Throwable t) {
		this.log.severe(msg);
		this.log.trace(t);
	}



}
