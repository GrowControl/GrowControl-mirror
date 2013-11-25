package com.growcontrol.gcClient.clientPlugin;

import javax.swing.JInternalFrame;


public class gcPluginFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;


	public gcPluginFrame(String title) {
		this();
		this.setTitle(title);
	}
	public gcPluginFrame() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

//		JToggleButton tglbtnNewToggleButton = new JToggleButton("New toggle button");
//		tglbtnNewToggleButton.setBounds(150, 123, 169, 25);
//		getContentPane().add(tglbtnNewToggleButton);

	}


}
