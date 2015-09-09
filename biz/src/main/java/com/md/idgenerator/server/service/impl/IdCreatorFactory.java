package com.md.idgenerator.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.api.IdFactory;
import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.api.bean.Result;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.common.enums.ErrType;
import com.md.idgenerator.server.service.IdGeneratorService;

@Service
public class IdCreatorFactory implements IdFactory {

	public static Logger logger = LoggerFactory.getLogger(IdCreatorFactory.class);

	@Autowired
	private IdGeneratorService idGeneratorService;

	@Override
	public Result<IdInfo> getId(int type, String key) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		logger.info("params:type={},key={}", type, key);
		Result<IdInfo> resultMsg = new Result<IdInfo>();
		IdInfo id = null;

		try {
			if (StringUtils.isBlank(AptConstants.BUS_NAME_MAP.get(type))) {
				logger.info("error bus type in createId,type:{}", type);
				resultMsg.setCode(ErrType.NO_TYPE.getType());
				resultMsg.setMsg(ErrType.NO_TYPE.getMsg());
				return resultMsg;
			} else if (StringUtils.isBlank(key)
					|| !key.equals(AptConstants.TOKEN_MAP.get(type))) {
				logger.info("error create id, the key is null or key is not correct.type:{},key:{}", type,key);
				resultMsg.setCode(ErrType.KEY_ERR.getType());
				resultMsg.setMsg(ErrType.KEY_ERR.getMsg());
				return resultMsg;
			}
			id = idGeneratorService.getNextId(type, key);
			logger.info("create Id success,infos:{}", JSONObject.toJSONString(id));
			resultMsg.setCode(ErrType.SUCESS.getType());
			resultMsg.setMsg(ErrType.SUCESS.getMsg());
			resultMsg.setData(id);
			return resultMsg;
		} catch (Exception e) {
			logger.error("createid error:{}", e);
			return new Result<>(ErrType.SYS_ERROR.getType(), ErrType.SYS_ERROR.getMsg());
		} finally {
			logger.info("createid cost time : {}", System.currentTimeMillis() - startTime);
		}
	}

}
