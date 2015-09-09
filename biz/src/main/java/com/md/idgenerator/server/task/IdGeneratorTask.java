package com.md.idgenerator.server.task;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.service.GeneratorService;

/**
 * 生成器任务类,补充id到对应队列
 * @author zhiwei.wen
 * 2015年7月16日
 */
public class IdGeneratorTask implements Runnable {

	public static final Logger logger = LoggerFactory.getLogger(IdGeneratorTask.class);

	public static GeneratorService generatorService;

	/**
	 * 是否已经启动任务 true有任务在跑 false 可以跑
	 */
	public static volatile Map<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();

	/**
	 *  哪个业务线
	 */
	private int type;

	/**
	 * 当前里程碑
	 */
	private long milestone;

	public IdGeneratorTask(int type, long milestone) {
		this.type = type;
		this.milestone = milestone;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long starttime = System.currentTimeMillis();

		boolean started = statusMap.get(type);
		if (!started) {

			statusMap.put(type, true);
			logger.info(
					"task of id add to queue of busType : {} started,current milestone : {}",
					AptConstants.BUS_NAME_MAP.get(type), milestone);
			try {
				IdGeneratorQueue queue = QueueUtil
						.getIdGeneratorQueue(AptConstants.BUS_NAME_MAP.get(type));

				if (null == queue) {
					logger.error(
							"task of add id to queue faile , no queue of name: {} finded",
							AptConstants.BUS_NAME_MAP.get(type));
					return;
				}

				logger.info("queue : {} has {} id not uesed.", queue.getSize());
				long nextMilestone = generatorService.nextMilestone(type);
				logger.info("task of add id to queue,nextMilestone is {}", nextMilestone);

				if (nextMilestone <= milestone) {
					logger.error(
							"task of get next milestone error,nextMileStone is smaller, busType: {},miletone : {}",
							AptConstants.BUS_NAME_MAP.get(type), milestone);
					return;
				}

				long start = milestone + 1;
				long end = nextMilestone + 1;

				logger.info("start add id to queue : {},start : {},end : {}",
						AptConstants.BUS_NAME_MAP.get(type), start, end);
				queue.setMileStone(nextMilestone);
				//填充数据 满等待
				for (long i = start; i <= end; i++) {
					queue.putId(i);
				}
				logger.info("success add id to queue! busType : {}",
						AptConstants.BUS_NAME_MAP.get(type));
				statusMap.put(type, false);

			} catch (Exception e) {
				logger.error("task of id add to queue of busType : {} failed ",
						AptConstants.BUS_NAME_MAP.get(type));
				logger.error("", e);
			}
		}
		long endtime = System.currentTimeMillis();

		logger.info("task off add to queue ended,busType : {},usetime : {}",
				AptConstants.BUS_NAME_MAP.get(type), endtime - starttime);
	}

}
