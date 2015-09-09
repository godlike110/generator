package com.md.idgenerator.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.bean.entry.IdGenerator;
import com.md.idgenerator.server.common.constant.AptConstants;
import com.md.idgenerator.server.dal.dao.IdGeneratorDao;
import com.md.idgenerator.server.service.GeneratorService;
import com.md.idgenerator.server.task.IdGeneratorQueue;
import com.md.idgenerator.server.task.IdGeneratorTask;
import com.md.idgenerator.server.task.QueueUtil;

@Service
public class GeneratorServiceImpl implements GeneratorService {

	public static final Logger logger = LoggerFactory
			.getLogger(GeneratorServiceImpl.class);

	@Autowired
	private IdGeneratorDao idGeneratorDao;

	@Override
	public List<IdGenerator> getGenerators() {
		// TODO Auto-generated method stub
		List<IdGenerator> gList = idGeneratorDao.getGenerators();
		return gList;
	}

	@Override
	public IdGenerator getGenerator(int type) {
		// TODO Auto-generated method stub
		IdGenerator ig = idGeneratorDao.getGenerator(type);
		return ig;
	}

	@Override
	public void update(IdGenerator ig) {
		// TODO Auto-generated method stub
		idGeneratorDao.updateIdGenerator(ig);
	}

	@Override
	public void insert(IdGenerator ig) {
		// TODO Auto-generated method stub
		idGeneratorDao.insertIdGenerator(ig);
	}

	@Override
	public IdInfo createId(int type) {

		if (StringUtils.isBlank(AptConstants.BUS_NAME_MAP.get(type))) {
			logger.error("create id error,error busType : {}", type);
		}

		try {
			// TODO Auto-generated method stub
			logger.info("start create id ,busType : {}",
					AptConstants.BUS_NAME_MAP.get(type));
			IdGeneratorQueue igQueue = QueueUtil
					.getIdGeneratorQueue(AptConstants.BUS_NAME_MAP.get(type));
			//队列为空
			if (null == igQueue) {
				logger.error("create id error,no such queu : {}",
						AptConstants.BUS_NAME_MAP.get(type));
				return null;
			}

			IdInfo qi = new IdInfo();
			qi.setId(igQueue.getId());
			qi.setSize(igQueue.getSize());
			qi.setMileStone(igQueue.getMileStone());

			//看情况跑任务 补充数据
			runTaskIfNec(qi, type);
			logger.info("create id success! idinfo : {}", JSONObject.toJSONString(qi));
			return qi;
		} catch (Exception e) {
			logger.error("error create id busType : {}",
					AptConstants.BUS_NAME_MAP.get(type));
			logger.error("", e);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long nextMilestone(int type) {
		// TODO Auto-generated method stub

		logger.info("start set new milestone of busType : {}",
				AptConstants.BUS_NAME_MAP.get(type));
		try {
			IdGenerator ig = getGenerator(type);
			if (ig == null) {
				logger.error("busType : {} has no milestone in db.",
						AptConstants.BUS_NAME_MAP.get(type));
				return AptConstants.HAS_NO_MILESTONE;
			}

			long mileStone = ig.getMilestone();
			logger.info("current milestone of busType : {} is {}",
					AptConstants.BUS_NAME_MAP.get(type), mileStone);
			mileStone = mileStone + AptConstants.MILESTONE_STEP;

			//更新新的里程碑到数据库记录
			ig.setMilestone(mileStone);
			update(ig);
			logger.info("update milestone of busType : {} sucess,now milestone is {}",
					AptConstants.BUS_NAME_MAP.get(type), mileStone);
			return mileStone;

		} catch (Exception e) {
			logger.error("get next milesotone of busType : {} failed",
					AptConstants.BUS_NAME_MAP.get(type));
			logger.error("", e);
			return AptConstants.HAS_NO_MILESTONE;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long getNextMilestone(IdGenerator idg) {
		// TODO Auto-generated method stub
		logger.info("start set new milestone of busType : {}", idg.getName());
		try {
			IdGenerator ig = getGenerator(idg.getType());
			if (ig == null) {
				logger.error("busType : {} has no milestone in db.", idg.getName());
				return AptConstants.HAS_NO_MILESTONE;
			}

			long mileStone = ig.getMilestone();
			logger.info("current milestone of busType : {} is {}", idg.getName(),
					mileStone);
			mileStone = mileStone + AptConstants.MILESTONE_STEP;

			//更新新的里程碑到数据库记录
			ig.setMilestone(mileStone);
			ig.setUpdateTime(new Date());
			update(ig);
			logger.info("update milestone of busType : {} sucess,now milestone is {}",
					idg.getName(), mileStone);
			return mileStone;

		} catch (Exception e) {
			logger.error("get next milesotone of busType : {} failed", idg.getName());
			logger.error("", e);
			return AptConstants.HAS_NO_MILESTONE;
		}

	}

	/**
	 * 根据情况启动更新任务
	 * @param idInfo
	 * @param busType
	 */
	private void runTaskIfNec(IdInfo idInfo, int type) {
		int remain = idInfo.getSize();
		if (remain <= AptConstants.MIN_QUEUE_SIZE) {
			logger.info("check result queue :{} need to fill data,remaining size:{}",
					AptConstants.BUS_NAME_MAP.get(type), remain);
			new Thread(new IdGeneratorTask(type, idInfo.getMileStone())).start();
		}
	}

}
