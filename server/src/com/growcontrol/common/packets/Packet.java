package com.growcontrol.common.packets;

import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.commonjava.Utils.utilsObject;


public abstract class Packet {

	protected volatile PacketDAO dao = null;



	public void setDAO(final PacketDAO dao) {
		this.dao = dao;
	}



	public abstract Object generate();
	public abstract boolean handle(final String name, final Map<String, Object> json);



	public String convert(final Object json) {
		return this.getYaml()
				.dump(json);
	}
	public Map<String, Object> convertMap(final String str) {
		return utilsObject.castMap(
				String.class,
				Object.class,
				this.getYaml()
					.load(str)
		);
	}
	public List<Object> convertList(final String str) {
		return utilsObject.castList(
				Object.class,
				this.getYaml()
					.load(str)
		);
	}
	protected Yaml getYaml() {
		return this.dao.getYaml();
	}



}
