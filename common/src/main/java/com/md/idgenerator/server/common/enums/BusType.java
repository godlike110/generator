package com.md.idgenerator.server.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务类型（暂时没用）
 * @author zhiwei.wen
 * 2015年7月16日
 */
public enum BusType {

	ORDER(1, "mdsh_order"), TOB(2, "to_be"), UNKONW(0, "unknow");

	int type;
	String name;

	private static Map<Integer, BusType> btMap = new HashMap<Integer, BusType>();

	static {
		for (BusType bt : BusType.values()) {
			btMap.put(bt.type, bt);
		}
	}

	BusType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public static BusType getBusTypebyType(int type) {
		return btMap.get(type);
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

}
