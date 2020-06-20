package com.poixson.app.steps;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import com.poixson.app.Failure;
import com.poixson.app.xApp;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.app.xShellDefines;
import com.poixson.app.xVars;
import com.poixson.app.commands.xCommandHandler;
import com.poixson.app.commands.xCommandHandlerJLine;
import com.poixson.exceptions.IORuntimeException;
import com.poixson.logger.xConsole;
import com.poixson.logger.xLogRoot;
import com.poixson.tools.CoolDown;
import com.poixson.tools.Keeper;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ShellUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;


public class xAppSteps_Console implements xConsole {
	protected static final String THREAD_NAME = "Console-Input";

	protected static final AtomicReference<xAppSteps_Console> instance =
			new AtomicReference<xAppSteps_Console>(null);
	protected static final AtomicReference<Terminal>   terminal = new AtomicReference<Terminal>(null);
	protected static final AtomicReference<LineReader> reader   = new AtomicReference<LineReader>(null);
	protected static final AtomicReference<History>    history  = new AtomicReference<History>(null);

	protected final AtomicReference<Thread> thread = new AtomicReference<Thread>(null);
	protected final AtomicBoolean running  = new AtomicBoolean(false);
	protected final AtomicBoolean stopping = new AtomicBoolean(false);

	protected final AtomicBoolean isreading = new AtomicBoolean(false);
	protected final AtomicReference<CoolDown> readCool =
			new AtomicReference<CoolDown>(null);



	public static xAppSteps_Console get() {
		// existing instance
		{
			final xAppSteps_Console console = instance.get();
			if (console != null)
				return console;
		}
		// new instance
		{
			final xAppSteps_Console console = new xAppSteps_Console();
			if (instance.compareAndSet(null, console))
				return console;
			return instance.get();
		}
	}
	protected xAppSteps_Console() {
		Keeper.add(this);
	}
	public void unload() {
		Keeper.remove(this);
	}



	// terminal
	public static Terminal getTerminal() {
		{
			final Terminal term = terminal.get();
			if (term != null)
				return term;
		}
		synchronized (terminal) {
			final Terminal term;
			try {
				term = TerminalBuilder.builder()
					.system(true)
					.streams(
						xVars.getOriginalIn(),
						xVars.getOriginalOut()
					)
					.build();
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
			if ( ! terminal.compareAndSet(null, term) )
				terminal.get();
			AnsiConsole.systemInstall();
			return term;
		}
	}
	// line reader
	public static LineReader getReader() {
		{
			final LineReader read = reader.get();
			if (read != null)
				return read;
		}
		synchronized (reader) {
			final Terminal term = getTerminal();
			final LineReader read =
				LineReaderBuilder.builder()
					.terminal(term)
					.build();
			if ( ! reader.compareAndSet(null, read) )
				return reader.get();
			read.setVariable(
				LineReader.BELL_STYLE,
				( xShellDefines.BELL_ENABLED ? "audible" : "visible" )
			);
			getHistory();
			return read;
		}
	}
	// history
	public static History getHistory() {
		{
			final History hist = history.get();
			if (hist != null)
				return hist;
		}
		{
			final String historyFile =
				FileUtils.MergePaths(",", xShellDefines.HISTORY_FILE);
			final LineReader read = getReader();
			read.setVariable(LineReader.HISTORY_FILE, historyFile);
			read.setVariable(LineReader.HISTORY_SIZE, xShellDefines.HISTORY_SIZE);
			final History hist = new DefaultHistory(read);
			if ( ! history.compareAndSet(null, hist) )
				return history.get();
			return hist;
		}
	}



	// ------------------------------------------------------------------------------- //
	// console input



	@Override
	public void start() {
		if (this.running.get())
			return;
		this.stopping.set(false);
		// start console input thread
		final Thread thread = new Thread(this);
		thread.setName(THREAD_NAME);
		thread.setDaemon(true);
		thread.start();
	}
	@Override
	public void stop() {
		this.stopping.set(true);
		final Thread thread = this.thread.get();
		if (thread != null) {
			try {
				thread.interrupt();
			} catch (Exception ignore) {}
			try {
				thread.notifyAll();
			} catch (Exception ignore) {}
			ThreadUtils.Sleep(50L);
		}
	}



	@Override
	public void run() {
		if (this.stopping.get()) return;
		if ( ! this.running.compareAndSet(false, true) )
			throw new RuntimeException("Console thread already running!");
		xVars.setConsole(this);
		final Thread currentThread = Thread.currentThread();
		{
			this.thread.compareAndSet(null, currentThread);
			if (this.thread.get() != currentThread)
				throw new RuntimeException("Inconsistent console reader threads!");
		}
		currentThread.setName(THREAD_NAME);
		if (xVars.isDebug()) {
			xLogRoot.Get()
				.detail("Starting console input thread..");
		}
		final LineReader read = getReader();
		READER_LOOP:
		while (true) {
			if (this.stopping.get())           break READER_LOOP;
			if (currentThread.isInterrupted()) break READER_LOOP;
			final String line;
			try {
				this.setReadCool();
				// read console input
				line = read.readLine(
					this.getPrompt(),
					this.getMask()
				);
			} catch (UserInterruptException ignore) {
				break READER_LOOP;
			} catch (Exception e) {
				this.resetReadCool();
				xLogRoot.Get().trace(e);
				try {
					Thread.sleep(100L);
				} catch (InterruptedException ignore) {
					break READER_LOOP;
				}
				continue READER_LOOP;
			} finally {
				this.resetReadCool();
			}
			// handle line
			if (Utils.notBlank(line)) {
				final xCommandHandler handler = ShellUtils.GetCommandHandler();
				if (handler == null) {
					xLogRoot.Get().warning("No command handler set!");
				}
				final boolean result =
					handler.process(line);
				if ( ! result ) {
					xLogRoot.Get().warning("Unknown command:", line);
				}
			}
		} // end READER_LOOP
		if ( ! this.stopping.get() ) {
			xApp.kill();
			this.stopping.set(true);
		}
		xVars.setConsole(null);
		this.running.set(false);
		this.thread.set(null);
		xLogRoot.Get().finest("Stopped console input thread");
		final PrintStream out = xVars.getOriginalOut();
		out.flush();
		// save command history
		{
			final History hist = history.get();
			if (hist != null) {
				if (hist instanceof DefaultHistory) {
					try {
						((DefaultHistory) hist)
							.save();
					} catch (IOException e) {
						xLogRoot.Get().trace(e);
					}
				}
			}
		}
	}



	@Override
	public boolean isRunning() {
		if (this.stopping.get())
			return false;
		return this.running.get();
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	public boolean waitReadCool() {
		if ( ! this.isreading.get() )
			return false;
		if (this.readCool.get() == null)
			return true;
		while (true) {
			try {
				Thread.sleep(5L);
			} catch (InterruptedException ignore) {
				break;
			}
			final CoolDown cool = this.readCool.get();
			if (cool == null) break;
			if (cool.runAgain()) {
				this.readCool.set(null);
			}
		}
		return true;
	}
	public void setReadCool() {
		final CoolDown cool = CoolDown.getNew(20L);
		this.readCool.set(cool);
		this.isreading.set(true);
	}
	public void resetReadCool() {
		this.isreading.set(false);
		this.readCool.set(null);
	}



	// ------------------------------------------------------------------------------- //
	// startup steps



	@xAppStep( Type=StepType.START, Title="Commands", StepValue=100 )
	public void _START_commands(final xApp app) {
		final xCommandHandler handler = new xCommandHandlerJLine();
		ShellUtils.SetCommandHandler(handler);
	}
	@xAppStep( Type=StepType.START, Title="Console", StepValue=105 )
	public void _START_console(final xApp app) {
		// initialize console and enable colors
		if (System.console() != null) {
			if ( ! Utils.isJLineAvailable() ) {
				Failure.fail("jline library not found");
				return;
			}
		}
		// start reading console input
		this.start();
	}



	// ------------------------------------------------------------------------------- //
	// shutdown steps



	// stop console input
	@xAppStep( Type=StepType.STOP, Title="Console", StepValue=105)
	public void _STOP_console() {
		// stop reading console input
		this.stop();
	}



	// ------------------------------------------------------------------------------- //
	// publish to console



	@Override
	public void doPublish(final String line) {
		final Terminal   term = getTerminal();
		final LineReader read = getReader();
		final PrintWriter out = term.writer();
		{
			final boolean isread =
				this.waitReadCool();
			if (isread) {
				try {
					read.callWidget(LineReader.CLEAR);
				} catch (Exception ignore) {}
			}
		}
		out.println(line);
		{
			final boolean isread =
				this.waitReadCool();
			if (isread) {
				try {
					read.callWidget(LineReader.REDRAW_LINE);
					read.callWidget(LineReader.REDISPLAY);
				} catch (Exception ignore) {}
			}
		}
		out.flush();
	}



	@Override
	public void doClearScreen() {
		final boolean isread =
			this.waitReadCool();
		try {
			RETRY_LOOP:
			for (int i=0; i<5; i++) {
				if (this.stopping.get())
					break RETRY_LOOP;
				if (isread) {
					try {
						final Terminal   term = getTerminal();
						final LineReader read = getReader();
						read.callWidget(LineReader.CLEAR_SCREEN);
						term.writer().flush();
						break RETRY_LOOP;
					} catch (Exception ignore) {}
					if (this.stopping.get())
						break RETRY_LOOP;
					ThreadUtils.Sleep(20L);
				} else {
					final PrintStream out = xVars.getOriginalOut();
					out.print(
						Ansi.ansi()
							.eraseScreen()
							.cursor(0, 0)
							.toString()
					);
					out.flush();
					break RETRY_LOOP;
				}
			} // end RETRY_LOOP
		} catch (Exception ignore) {}
	}
	@Override
	public void doFlush() {
		this.waitReadCool();
		try {
			getTerminal().flush();
		} catch (Exception ignore) {}
	}
	@Override
	public void doBeep() {
		final boolean isread = this.waitReadCool();
		try {
			if (isread) {
				getReader().callWidget(LineReader.BEEP);
			} else {
				getTerminal().puts(Capability.bell);
			}
		} catch (Exception ignore) {}
	}



	// ------------------------------------------------------------------------------- //
	// settings



	// prompt
	public String getPrompt() {
		return StringUtils.ForceStarts("\r", xShellDefines.DEFAULT_PROMPT);
	}
	@Override
	public void setPrompt(final String prompt) {
//TODO:
throw new UnsupportedOperationException("Unfinished");
	}



	// mask
	public Character getMask() {
		return null;
	}
	@Override
	public void setMask(final Character mask) {
//TODO:
throw new UnsupportedOperationException("Unfinished");
	}



}
