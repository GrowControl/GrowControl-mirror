package com.growcontrol.common.netty.firewall;

import io.netty.channel.Channel;


public interface NetFirewallRule {


	public boolean check(final Channel channel);


}
