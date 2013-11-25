package com.growcontrol.gcClient.frames.Dashboard;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.growcontrol.gcClient.gcClient;
import com.growcontrol.gcClient.frames.gcFrameHandlerInterface;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;


public class DashboardHandler implements gcFrameHandlerInterface {
	protected static final String logName = "DashFrame";
	protected static volatile DashboardHandler handler = null;
	protected static final Object lock = new Object();

	// display mode
	public enum DASH {ROOM, TREE, DIAGRAM};
	private volatile DASH dashMode     = null;
	private volatile DASH lastDashMode = null;
	private final Object modeLock = new Object();

	// objects
	protected volatile DashboardFrame frame = null;


	// dash frame handler
	public static DashboardHandler get() {
		if(handler == null) {
			synchronized(lock) {
				if(handler == null)
					handler = new DashboardHandler();
			}
		}
		return handler;
	}
	private DashboardHandler() {}


	// get frame
	public DashboardFrame getDashFrame() {
		return (DashboardFrame) getFrame();
	}
	@Override
	public JFrame getFrame() {
		if(frame == null) {
			synchronized(frame) {
				if(frame == null)
					frame = new DashboardFrame(handler);
			}
		}
		return frame;
	}


	// show frame
	@Override
	public void Show() {
		synchronized(modeLock) {
			pxnLog.get(logName).info("Displaying window: Dashboard");
//			if(dashMode == null)
//				dashMode = DASH.ROOM;
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						getFrame();
					}
				});
			} catch (InvocationTargetException e) {
				pxnLog.get(logName).exception(e);
			} catch (InterruptedException ignore) {}
		}
	}


	// close frame
	@Override
	public void Close() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				doClose();
			}
		});
	}
	private void doClose() {
		if(frame != null) {
			synchronized(modeLock) {
				if(frame != null) {
					pxnLog.get(logName).debug("Closing window: Dashboard");
					if(frame != null)
						frame.dispose();
					frame = null;
				}
			}
		}
		gcClient.get().Shutdown();
	}


	// get/set gui mode
	public DASH getMode() {
		synchronized(modeLock) {
			if(dashMode == null)
				return DASH.ROOM;
			return dashMode;
		}
	}
	public DASH setMode(DASH mode) {
		if(mode == null) throw new NullPointerException("mode cannot be null!");
		synchronized(modeLock) {
			lastDashMode = dashMode;
			dashMode = mode;
			return lastDashMode;
		}
	}


}
