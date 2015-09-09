package com.md.idgenerator.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.common.bean.IdInfo;
import com.md.idgenerator.server.common.enums.ErrType;
import com.md.idgenerator.server.common.util.Result;
import com.md.idgenerator.server.service.impl.IdCreatorFactory;

public class CreateidTest extends BaseTest {

	@Autowired
	private IdCreatorFactory idCeatorFactory;

	/**
	 * 成功获取id测试
	 */
	@Test
	public void testGetIdSucess() {
		int type = 1;
		String key = "MDF34jetorderHHD";
		Result<IdInfo> resultMsg = idCeatorFactory.getId(type, key);
		logger.info("getidSucess test result:{}", JSONObject.toJSONString(resultMsg));
		Assert.assertNotNull(resultMsg.getData());
		logger.info("getidsucess test sucess!");

	}

	@Test
	public void testGetIdNotype() {
		int type = 1000;
		String key = "Dgegsegetsgdsetes";
		Result<IdInfo> resultMsg = idCeatorFactory.getId(type, key);
		logger.info("getidNoType test result: {}", JSONObject.toJSONString(resultMsg));
		Assert.assertEquals(ErrType.NO_TYPE.getType(), resultMsg.getCode());
		logger.info("getidNoType test sucess!");
	}

	@Test
	public void testGetidNoKey() {
		int type = 1;
		String key = null;
		Result<IdInfo> resultMsg = idCeatorFactory.getId(type, key);
		logger.info("getidnokey result : {}", JSONObject.toJSONString(resultMsg));
		Assert.assertEquals(ErrType.KEY_ERR.getType(), resultMsg.getCode());
		logger.info("getidnokey test  success!");
	}

	@Test
	public void testGetidKeyWrong() {
		int type = 1;
		String key = "fsgsadteagadsge";
		Result<IdInfo> resultMsg = idCeatorFactory.getId(type, key);
		logger.info("getidkeyWrong result : {}", JSONObject.toJSON(resultMsg));
		Assert.assertEquals(ErrType.KEY_ERR.getType(), resultMsg.getCode());
		logger.info("getidkeyWrong test sucess!");
	}

	@Test
	public void testGetidSysError() {
		logger.info("systemErr test sucess!");
	}

}
