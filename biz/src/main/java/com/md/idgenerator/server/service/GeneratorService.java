package com.md.idgenerator.server.service;

import java.util.List;

import com.md.idgenerator.server.api.bean.IdInfo;
import com.md.idgenerator.server.bean.entry.IdGenerator;

public interface GeneratorService {

	public List<IdGenerator> getGenerators();

	public IdGenerator getGenerator(int type);

	public void update(IdGenerator ig);

	public void insert(IdGenerator ig);

	/**
	 * 根据业务获取全局id
	 * @param type 业务类型 1：订单 0：未知
	 * @return
	 */
	public IdInfo createId(int type);

	/**
	 * 获取下一个里程碑
	 * @param type
	 * @return
	 */
	public long nextMilestone(int type);

	/**
	 * 获取下一个里程碑
	 * @param idg
	 * @return
	 */
	public long getNextMilestone(IdGenerator idg);

}
