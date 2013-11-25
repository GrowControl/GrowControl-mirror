package com.growcontrol.gcClient;

import java.io.File;

import javax.swing.ImageIcon;

import com.growcontrol.gcCommon.pxnLogger.pxnLog;


public class guiUtils {
	private guiUtils() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}


	// load image file/resource
	public static ImageIcon loadImageResource(String path) {
		ImageIcon image = null;
		// open file
		File file = new File(path);
		if(file.exists()) {
			try {
				image = new ImageIcon(path);
				if(image != null) {
					pxnLog.get().debug("Loaded image file: "+path);
					return image;
				}
			} catch(Exception ignore) {}
		}
		// open resource
		try {
			image = new ImageIcon(ClassLoader.getSystemResource(path));
//			image = new ImageIcon(guiManager.get().getClass().getResource(path));
			if(image != null) {
				pxnLog.get().debug("Loaded image resource: "+path);
				return image;
			}
		} catch(Exception ignore) {}
		pxnLog.get().warning("Failed to load image: "+path);
		return null;
	}


}
