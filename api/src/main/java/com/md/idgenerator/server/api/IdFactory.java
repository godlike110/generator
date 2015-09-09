package com.md.idgenerator.server.api;

import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.api.bean.Result;

/**
 * 全局id生产工厂
 * @author zhiwei.wen
 * 2015年7月17日
 */
public interface IdFactory {

	/**
	 * 全局id生产
	 * @param type 业务类型
	 * @param key  业务调用秘钥
	 * @return
	 */
	public Result<IdInfo> getId(int type, String key);

}
