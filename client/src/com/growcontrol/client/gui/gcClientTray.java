package com.growcontrol.client.gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import javax.swing.ImageIcon;

import com.poixson.tools.xFont;
import com.poixson.tools.xSystemTray;
import com.poixson.utils.guiUtils;


public class gcClientTray extends xSystemTray {
	protected static final String GC_ICON = "images/treeicon.png";

	protected final PopupMenu menu;



	public gcClientTray() {
		super();
		this.trayIcon.setImageAutoSize(true);
		// menu
		final Font font = xFont.Build("size=+8").font();
		this.menu = new PopupMenu();
		final MenuItem itemShowHide = new MenuItem("Show/Hide");
		itemShowHide.setFont(font);
		this.menu.add(itemShowHide);
		final MenuItem itemExit = new MenuItem("Exit");
		itemExit.setFont(font);
		this.menu.add(itemExit);
		this.setMenu(this.menu);
	}



	@Override
	protected Image loadIcon() {
		final ImageIcon icon = guiUtils.LoadImageResource(GC_ICON);
		if (icon == null)
			return null;
		return icon.getImage();
	}



}
