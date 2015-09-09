package com.md.idgenerator.server.service;

import com.md.idgenerator.server.api.bean.IdInfo;



public interface IdGeneratorService {

	/**
	 * 获取全局id
	 * @param type
	 * @param key
	 * @return
	 */
	public IdInfo getNextId(int type, String key);

}
