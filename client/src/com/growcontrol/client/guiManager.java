package com.growcontrol.gcClient;

import javax.swing.SwingUtilities;

import com.growcontrol.gcClient.frames.Dashboard.DashboardHandler;
import com.growcontrol.gcClient.frames.Login.LoginHandler;


public class guiManager {
	private static volatile guiManager manager = null;
	private static final Object lock = new Object();

	// display mode
	public enum GUI {LOGIN, DASH};
	private static volatile GUI startupMode      = null;
	private static volatile GUI startupMode_Last = null;
//	private final Object modeLock = new Object();

	// frames
	protected volatile LoginHandler     loginHandler = null;
	protected volatile DashboardHandler dashHandler  = null;


	public static guiManager get() {
		if(manager == null) {
			synchronized(lock) {
				if(manager == null)
					manager = new guiManager();
			}
		}
		return manager;
	}
	private guiManager() {
		Update();
	}
	public static void Shutdown() {
		synchronized(lock) {
			if(manager != null)
				get().doShutdown();
		}
	}
	private void doShutdown() {
		if(loginHandler != null)
			loginHandler.Close();
		if(dashHandler != null)
			dashHandler.Close();
	}


	// update display mode
	public void Update() {
		Update(null);
	}
	public void Update(GUI mode) {
		if(mode != null) startupMode = mode;
		if(SwingUtilities.isEventDispatchThread()) {
			doUpdate();
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				doUpdate();
			}
		});
	}
	private void doUpdate() {
		getStartupMode();
		synchronized(startupMode) {
			boolean hasChanged = !getStartupMode().equals(startupMode_Last);
			// close old frames
			if(hasChanged && startupMode_Last != null) {
				switch(startupMode_Last) {
				case LOGIN:
					if(loginHandler != null)
						loginHandler.Close();
					break;
				case DASH:
					if(dashHandler != null)
						dashHandler.Close();
					break;
				}
			}
			// load new frames
			switch(getStartupMode()) {
			case LOGIN:
				if(hasChanged)
					getLoginHandler().Show();
				break;
			case DASH:
				if(hasChanged)
					getDashboardHandler().Show();
				break;
			}
			startupMode_Last = startupMode;
		}
	}
	// get/set gui mode
	public synchronized GUI getStartupMode() {
		if(startupMode == null)
			startupMode = GUI.LOGIN;
		return startupMode;
	}


	public synchronized LoginHandler getLoginHandler() {
		if(loginHandler == null)
			loginHandler = LoginHandler.get();
		return loginHandler;
	}
	public synchronized DashboardHandler getDashboardHandler() {
		if(dashHandler == null)
			dashHandler = DashboardHandler.get();
		return dashHandler;
	}


	// gui mode to string
	public static String modeToString(GUI mode) {
		switch(mode) {
		case LOGIN:
			return "LOGIN";
		case DASH:
			return "DASH";
		default:
		}
		return "UNKNOWN";
	}


}
