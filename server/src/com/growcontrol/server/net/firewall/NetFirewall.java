package com.growcontrol.server.net.firewall;

import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class NetFirewall {

	protected final List<NetFirewallRule> rules
			= new CopyOnWriteArrayList<NetFirewallRule>();



	public NetFirewall() {
	}



	public void addRule(final NetFirewallRule rule) {
		if(rule == null) throw new NullPointerException("rule argument is required!");
		this.rules.add(rule);
	}



	/**
	 * Check an accepted socket connection.
	 * @param channel
	 * @return true if ok to accept, false to deny.
	 */
	public boolean check(final Channel channel) {
		return true;
	}



}
