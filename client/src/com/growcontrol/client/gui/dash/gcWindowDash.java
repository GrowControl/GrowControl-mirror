package com.growcontrol.client.gui.dash;

import com.growcontrol.client.gui.guiManager;
import com.poixson.commonapp.gui.xWindow;
import com.poixson.commonapp.gui.annotations.xWindowProperties;


@xWindowProperties(
		resizable = true)
public class gcWindowDash extends xWindow {
	private static final long serialVersionUID = 1L;
//	private static final boolean DEBUGGING = false;


	private static final String WINDOW_TITLE = "GrowControl";



	public gcWindowDash() {
		super();
		this.setTitle(WINDOW_TITLE);

		// resize and prepare

		// show window
		this.setVisible(true);
	}



	// close window
	@Override
	public void close() {
		if(!this.closing.compareAndSet(false, true))
			return;
		this.closing.set(false);
		super.close();
		guiManager.get().doDashWindowClosed();
	}



}
