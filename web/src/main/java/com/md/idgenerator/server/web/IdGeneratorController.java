package com.md.idgenerator.server.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.api.bean.Result;
import com.md.idgenerator.server.bean.entry.IdGenerator;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.common.util.ResponseUtil;
import com.md.idgenerator.server.service.GeneratorService;
import com.md.idgenerator.server.service.IdGeneratorService;
import com.md.idgenerator.server.service.impl.IdCreatorFactory;

@Controller
@RequestMapping("")
public class IdGeneratorController {

	@Autowired
	GeneratorService generatorService;

	@Autowired
	private IdGeneratorService idGeneratorService;

	@Autowired
	private IdCreatorFactory idCreatorFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(IdGeneratorController.class);

	@RequestMapping(value = "genid", method = RequestMethod.GET)
	@ResponseBody
	public String getIdGennerator() {
		List<IdGenerator> gList = generatorService.getGenerators();
		return JSONObject.toJSONString(gList);
	}

	@RequestMapping(value = "genidinsert", method = RequestMethod.GET)
	@ResponseBody
	public String insertIdGennerator() {
		IdGenerator ig = new IdGenerator();
		ig.setBusKey("test");
		ig.setCreateTime(new Date());
		ig.setMilestone(1000000l);
		ig.setType(2);
		ig.setUpdateTime(new Date());
		generatorService.insert(ig);
		List<IdGenerator> gList = generatorService.getGenerators();
		return JSONObject.toJSONString(gList);
	}

	@RequestMapping(value = "genidsingle", method = RequestMethod.GET)
	@ResponseBody
	public String getIdGenneratorOne(@RequestParam("type") int type) {

		IdGenerator ig = generatorService.getGenerator(type);
		logger.info(JSONObject.toJSONString(ig));
		return JSONObject.toJSONString(ig);
	}

	@RequestMapping(value = "updateid", method = RequestMethod.GET)
	@ResponseBody
	public String updateIdGenerator(@RequestParam("type") int type,
			@RequestParam("id") long id) {

		IdGenerator ig = generatorService.getGenerator(type);
		ig.setMilestone(id);
		ig.setUpdateTime(new Date());
		generatorService.update(ig);
		logger.info(JSONObject.toJSONString(ig));
		return JSONObject.toJSONString(ig);
	}

	@RequestMapping(value = "getsize")
	@ResponseBody
	public String getQueueSize() {
		return String.valueOf(AptConstants.QUEUE_SIZE);
	}

	@RequestMapping(value = "getid", method = RequestMethod.GET)
	@ResponseBody
	public String getIdGennerator(@RequestParam("type") int type,
			@RequestParam("key") String key) {

		String jsonString = "";
		Result<IdInfo> resultMsg = null;
		IdInfo idInfo = null;
		try {
			//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
			//logger.info("JSON:" + JSONObject.toJSONString(wac.getBeanDefinitionNames()));
			//GeneratorService generatorService = (GeneratorService) wac.getBean("generatorService");
			//logger.info("name:",generatorService.getClass());
			resultMsg = idCreatorFactory.getId(type, key);

		} catch (Exception e) {
			System.out.println("jsonSring :" + jsonString + " idInfo:" + idInfo);
			e.printStackTrace();
		}

		return JSONObject.toJSONString(resultMsg);
	}

	@RequestMapping(value = "createId", method = RequestMethod.GET)
	@ResponseBody
	public String createIdGennerator(@RequestParam("type") int type,
			@RequestParam("key") String key) {

		String jsonString = "";
		IdInfo idInfo = null;
		try {
			//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
			//logger.info("JSON:" + JSONObject.toJSONString(wac.getBeanDefinitionNames()));
			//GeneratorService generatorService = (GeneratorService) wac.getBean("generatorService");
			//logger.info("name:",generatorService.getClass());
			idInfo = idGeneratorService.getNextId(type, key);
			jsonString = ResponseUtil.ok(idInfo);
		} catch (Exception e) {
			System.out.println("jsonSring :" + jsonString + " idInfo:" + idInfo);
			e.printStackTrace();
		}

		return jsonString;
	}

}
