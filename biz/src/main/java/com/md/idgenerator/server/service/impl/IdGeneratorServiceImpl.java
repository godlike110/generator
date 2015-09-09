package com.md.idgenerator.server.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.service.GeneratorService;
import com.md.idgenerator.server.service.IdGeneratorService;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {

	public static final Logger logger = LoggerFactory
			.getLogger(IdGeneratorServiceImpl.class);

	@Autowired
	private GeneratorService generatorService;

	@Override
	public IdInfo getNextId(int type, String key) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		IdInfo idInfo = null;

		try {

			if (StringUtils.isBlank(AptConstants.TOKEN_MAP.get(type))) {
				logger.error("getnextid error,no such busType type : {}", type);
				return idInfo;
			}

			//空
			if (StringUtils.isBlank(key)) {
				logger.error("getnextid error,key is null,type : ", type);
				return idInfo;
			}

			//无
			String tokenString = AptConstants.TOKEN_MAP.get(type);
			if (!tokenString.equals(key)) {
				logger.error("getnextid error,key error ,type:{},key:{}", type,key);
				return idInfo;
			}

			//not matched
			if (!tokenString.equals(key)) {
				logger.error("getnextid error,key is not match inf the system,key : {}",
						key);
			}

			idInfo = generatorService.createId(type);

			logger.info("getnextid success,idinfo : {},usetime:{}",
					JSONObject.toJSONString(idInfo), System.currentTimeMillis()
							- startTime);

			return idInfo;

		} catch (Exception e) {
			logger.error("getnextid error:{}", e);
		}

		return null;
	}

}
