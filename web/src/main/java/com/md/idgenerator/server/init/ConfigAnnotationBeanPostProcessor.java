package com.md.idgenerator.server.init;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.bean.entry.IdGenerator;
import com.md.idgenerator.server.common.annotation.Config;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.service.GeneratorService;
import com.md.idgenerator.server.task.IdGeneratorQueue;
import com.md.idgenerator.server.task.IdGeneratorTask;
import com.md.idgenerator.server.task.NewBusCheckTask;
import com.md.idgenerator.server.task.QueueUtil;

/**
 * 属性值的注入 及系统初始化
 * @author zhiwei.wen
 * @time 2015/07
 */
@Component
public class ConfigAnnotationBeanPostProcessor extends
		InstantiationAwareBeanPostProcessorAdapter implements
		ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory
			.getLogger(ConfigAnnotationBeanPostProcessor.class);

	//创建简单类型转换器
	private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

	@Autowired
	private ConfigBean configBean;

	@Autowired
	private GeneratorService generatorService;

	@Override
	public boolean postProcessAfterInstantiation(final Object bean, String beanName)
			throws BeansException {
		if ("aptConstants".equals(beanName)) {
			findPropertyAutowiringMetadata(bean);
		}
		return true;
	}

	private void findPropertyAutowiringMetadata(final Object bean) {
		ReflectionUtils.doWithFields(bean.getClass(),
				new ReflectionUtils.FieldCallback() {
					public void doWith(Field field) throws IllegalAccessException {
						Config annotation = field.getAnnotation(Config.class);
						if (annotation != null) {
							Object strValue = configBean.getProperty(annotation.value());
							if (null != strValue) {
								Object value = typeConverter.convertIfNecessary(strValue,
										field.getType());
								ReflectionUtils.makeAccessible(field);
								field.set(null, value);
								logger.info("set field:" + field.getName() + " value :"
										+ value);
							}
						}
					}
				});
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		logger.info("start init app configs");

		List<IdGenerator> generators = generatorService.getGenerators();
		Map<Integer, String> configMap = new HashMap<>();
		Map<Integer, String> nameMap = new HashMap<>();
		long milestone = 0l;
		long start = 0;
		long end = 0;
		int num = 0;
		for (IdGenerator ig : generators) {

			//根据业务类型生成对应queue
			logger.info("start init queue :{}", ig.getName());
			milestone = generatorService.getNextMilestone(ig);
			configMap.put(ig.getType(), ig.getBusKey());
			nameMap.put(ig.getType(), ig.getName());
			start = ig.getMilestone() + 1;
			end = milestone;
			IdGeneratorQueue idqueue = new IdGeneratorQueue(ig.getName(),
					AptConstants.QUEUE_SIZE, milestone);
			try {
				for (; start <= end; start++) {
					idqueue.putId(start);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error("init queue error:{}", e);
			}
			QueueUtil.putIdGeneratorQueue(idqueue, ig.getName());
			IdGeneratorTask.statusMap.put(ig.getType(), false);

			IdGeneratorTask.generatorService = this.generatorService;
			NewBusCheckTask.generatorService = this.generatorService;
			AptConstants.TOKEN_MAP = configMap;
			AptConstants.BUS_NAME_MAP = nameMap;
			num++;
			logger.info("init queu : {} success", ig.getName());
		}
		logger.info("total num of bus is : {}", num);
		NewBusCheckTask.num = num;
		logger.info("finished init app configs,tokenmap:{},bus_name_map:{}",
				JSONObject.toJSONString(AptConstants.TOKEN_MAP),
				JSONObject.toJSONString(nameMap));
	}

}
