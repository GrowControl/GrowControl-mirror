package com.growcontrol.common.netty.firewall.rules;

import io.netty.channel.Channel;

import com.growcontrol.common.netty.firewall.NetFirewallRule;


public class ruleHostname implements NetFirewallRule {



	@Override
	public boolean check(Channel channel) {
		return false;
	}



}