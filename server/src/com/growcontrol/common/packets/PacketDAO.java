package com.growcontrol.common.packets;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.poixson.commonjava.Utils.utils;


public class PacketDAO {

	public final Class<? extends Packet> packetClass;
	public volatile Packet instance = null;

	// properties
	public final String  name;
	public final boolean stateful;
	public final boolean async;

	protected Yaml yaml = null;



	public PacketDAO(final Class<? extends Packet> packetClass) {
		if(packetClass == null) throw new NullPointerException("packetClass argument is required!");
		this.packetClass = packetClass;
		// load properties annotation
		final PacketProperties props = packetClass.getAnnotation(PacketProperties.class);
		// packet name
		if(props != null) {
			this.name = props.name();
		} else {
			String name = packetClass.getName();
			if(name.startsWith("Packet_"))
				name = name.substring("Packet_".length());
			this.name = name;
		}
		if(utils.isEmpty(this.name))
			throw new NullPointerException("Packet name is required!");
		// more packet properties
		this.stateful = props.stateful();
		this.async    = props.async();
	}



	public Packet getInstance() throws InstantiationException, IllegalAccessException {
		// stateful instance
		if(this.stateful) {
			if(this.instance == null) {
				this.instance = this.packetClass.newInstance();
				this.instance.setDAO(this);
			}
			return this.instance;
		}
		// not stateful
		{
			final Packet instance = this.packetClass.newInstance();
			instance.setDAO(this);
			return instance;
		}
	}



	public Yaml getYaml() {
		if(this.yaml == null) {
			final DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
			options.setPrettyFlow(true);
			this.yaml = new Yaml(options);
		}
		return this.yaml;
	}



}
