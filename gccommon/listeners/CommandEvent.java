package com.growcontrol.gccommon.listeners;

import com.poixson.commonjava.EventListener.xEventData;
import com.poixson.commonjava.Utils.utils;


public class CommandEvent extends xEventData {

	public final String commandStr;
	public final String[] args;
	public final boolean help;


	public CommandEvent(final String commandStr) {
		if(utils.isEmpty(commandStr)) throw new NullPointerException();
		this.commandStr = commandStr.trim();
		this.args = this.commandStr.split(" ");
		{
			final String firstArg = this.args[this.args.length - 1];
			this.help = (!utils.isEmpty(firstArg) && firstArg.equals("?"));
		}
	}


	public String arg(final int index) {
		if(index < 0) throw new ArrayIndexOutOfBoundsException();
		if(index > this.args.length-1) return null;
		return this.args[index];
	}


	public boolean isHelp() {
		return this.help;
	}


}
