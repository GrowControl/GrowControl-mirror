package com.growcontrol.client.gui;

import com.growcontrol.client.gcClient;
import com.growcontrol.client.gui.dash.gcWindowDash;
import com.growcontrol.client.gui.login.gcWindowLogin;
import com.poixson.commonapp.app.xApp;
import com.poixson.commonapp.gui.guiUtils;
import com.poixson.commonjava.Utils.Keeper;
import com.poixson.commonjava.Utils.utils;


public class guiManager {

	// display mode
	public enum GUI_MODE {
		LOGIN,
		DASH
	}
	protected volatile GUI_MODE currentMode = null;
	protected volatile GUI_MODE lastMode    = null;
//	protected final Object modeLock = new Object();

	// windows
	protected volatile gcWindowLogin windowLogin = null;
	protected volatile gcWindowDash  windowDash  = null;



	// single manager instance
	private static volatile guiManager manager = null;
	private static final Object lock = new Object();

	public static guiManager get() {
		if(manager == null) {
			synchronized(lock) {
				if(manager == null)
					manager = new guiManager();
			}
		}
		return manager;
	}
	public static guiManager peak() {
		return manager;
	}



	// new instance
	private guiManager() {
		// just to prevent gc
		Keeper.add(this);
	}



	// show window
	public void Show(final GUI_MODE mode) {
		if(mode == null) throw new NullPointerException();
		// run in event dispatch thread
		if(guiUtils.forceDispatchThread(this, "Show", mode)) return;
		switch(mode) {
		// login window
		case LOGIN: {
			if(this.windowLogin == null)
				this.windowLogin = new gcWindowLogin();
			this.windowLogin.Show();
			break;
		}
		// dashboard window
		case DASH: {
			if(this.windowDash == null)
				this.windowDash = new gcWindowDash();
			break;
		}
		}
		// mode has changed
		if(!mode.equals(this.currentMode)) {
			xApp.log().info("Displaying mode: "+mode.toString());
			this.lastMode = this.currentMode;
			this.currentMode = mode;
		}
	}



	// close windows
	public void shutdown() {
		// run in event dispatch thread
		if(guiUtils.forceDispatchThread(this, "shutdown")) return;
		utils.safeClose(this.windowLogin);
		utils.safeClose(this.windowDash);
		this.windowLogin = null;
		this.windowDash  = null;
	}



	// hook login window close event
	public void doLoginWindowClosed() {
		// exit if dash not loaded
		if(this.windowDash == null)
			gcClient.get().Stop();
	}



}
