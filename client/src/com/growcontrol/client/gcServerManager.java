package com.growcontrol.client;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import com.poixson.commonjava.Utils.StreamBridge;
import com.poixson.commonjava.Utils.xStartable;
import com.poixson.commonjava.xLogger.xLog;


public class gcServerManager implements xStartable {

	private static volatile gcServerManager instance = null;
	private static final Object instanceLock = new Object();

	protected final AtomicBoolean running = new AtomicBoolean(false);

	protected volatile Process process = null;
	protected volatile StreamBridge bridgeOut = null;
	protected volatile StreamBridge bridgeErr = null;



	public static gcServerManager get() {
		if(instance == null) {
			synchronized(instanceLock) {
				if(instance == null)
					instance = new gcServerManager();
			}
		}
		return instance;
	}
	private gcServerManager() {
//		this.monitor = new OutputStream() {
//			final StringBuilder buffer = new StringBuilder();
//			@Override
//			public void write(int b) throws IOException {
//				if(b == '\r' || b == '\n') {
//					if(this.buffer.length() == 0)
//						return;
//
//if("test".equals(this.buffer.toString()))
//xLog.getRoot().warning("GOT IT!!!!!!!!!!!!!!!!!!!");
//
//					this.buffer.setLength(0);
//					return;
//				}
//				this.buffer.append((char) b);
//			}
//		};
	}



	@Override
	public void Start() {
		if(!this.running.compareAndSet(false, true))
			return;
		try {
			final ProcessBuilder processBuilder = new ProcessBuilder(
					"java", "-jar", "gcServer.jar", "--internal"
			);
			processBuilder.redirectErrorStream(true);
			this.process = processBuilder.start();
		} catch (IOException e) {
			this.process = null;
			this.running.set(false);
			xLog.getRoot().trace(e);
			throw new RuntimeException(e);
		}

//System.out.println("EXIT: ");
//System.out.print(this.process.exitValue());
if(this.process.isAlive())
xLog.getRoot().severe("IS ALIVE");

//BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
//String line;
//try {
//	while( (line = reader.readLine()) != null) {
//		xLog.getRoot().publish(line);
//	}
//} catch (IOException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}

		// stdout
		this.bridgeOut = new StreamBridge(
				this.process.getInputStream(),
				new OutputStream() {
					final StringBuilder buf = new StringBuilder();
					@Override
					public void write(final int b) throws IOException {
						if(b == '\r' || b == '\n') {

xLog.getRoot("SERVER").publish(this.buf.toString());

							this.buf.setLength(0);
							return;
						}
						this.buf.append((char) b);
					}
				}
		);
		this.bridgeOut.Start();
		// stderr
		this.bridgeErr = new StreamBridge(
				this.process.getErrorStream(),
				new OutputStream() {
					final StringBuilder buf = new StringBuilder();
					@Override
					public void write(final int b) throws IOException {
						if(b == '\r' || b == '\n') {

xLog.getRoot("ERR").publish(this.buf.toString());

							this.buf.setLength(0);
							return;
						}
						this.buf.append((char) b);
					}
				}
		);
		this.bridgeErr.Start();

	}
	@Override
	public void Stop() {
		this.running.set(false);
	}



	@Override
	public void run() {
	}
	@Override
	public boolean isRunning() {
		return this.running.get();
	}



}
