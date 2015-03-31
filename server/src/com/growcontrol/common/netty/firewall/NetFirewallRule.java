package com.growcontrol.server.net.firewall;

import io.netty.channel.Channel;


public interface NetFirewallRule {


	public boolean check(final Channel channel);


}
