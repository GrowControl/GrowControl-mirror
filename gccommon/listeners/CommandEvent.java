package com.growcontrol.gccommon.listeners;

import com.poixson.commonjava.EventListener.xEventData;
import com.poixson.commonjava.Utils.utils;


public class CommandEvent extends xEventData {

	public final String commandStr;
	public final String[] arg;


	public CommandEvent(final String commandStr) {
		if(utils.isEmpty(commandStr)) throw new NullPointerException();
		this.commandStr = commandStr.trim();
		this.arg = this.commandStr.split(" ");
	}


	public String arg(final int index) {
		if(index < 0) throw new ArrayIndexOutOfBoundsException();
		if(index > this.arg.length-1) return null;
		return this.arg[index];
	}


}
