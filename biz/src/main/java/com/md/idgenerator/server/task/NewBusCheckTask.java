package com.md.idgenerator.server.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.md.idgenerator.server.bean.entry.IdGenerator;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.service.GeneratorService;

/**
 * 新业务加入自动检测任务
 * @author zhiwei.wen
 * 2015年7月17日
 */
public class NewBusCheckTask {

	public static Logger logger = LoggerFactory.getLogger(NewBusCheckTask.class);

	public static GeneratorService generatorService;

	/**
	 * 当前有多少个业务线
	 */
	public static int num;

	public void doCheck() {
		logger.info("start to check businesses in db!");

		try {
			List<IdGenerator> idGenerators = generatorService.getGenerators();

			if (idGenerators.size() > num) {
				//有新业务加入
				logger.info("find new business add to the system!");
				for (IdGenerator idg : idGenerators) {

					if (StringUtils.isBlank(AptConstants.BUS_NAME_MAP.get(idg.getType()))) {
						//如果当前配置没有此业务
						AptConstants.BUS_NAME_MAP.put(idg.getType(), idg.getName());
						AptConstants.TOKEN_MAP.put(idg.getType(), idg.getBusKey());

						//初始化对应队列
						logger.info("start to init new queue : {}", idg.getName());
						long milestone = generatorService.nextMilestone(idg.getType());
						long start = idg.getMilestone() + 1;
						long end = milestone;
						IdGeneratorQueue idqueue = new IdGeneratorQueue(idg.getName(),
								AptConstants.QUEUE_SIZE, milestone);
						try {
							for (; start <= end; start++) {
								idqueue.putId(start);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							logger.error("init queue error:{}", e);
						}
						QueueUtil.putIdGeneratorQueue(idqueue, idg.getName());
						logger.info("queue : {} add to queueus success!", idg.getName());
					}
				}
				NewBusCheckTask.num = idGenerators.size();
			}
		} catch (Exception e) {
			logger.error("check new bus error:{}", e);
		}
		logger.info("check new business task done succes,new business num is {}", num);
	}

}
