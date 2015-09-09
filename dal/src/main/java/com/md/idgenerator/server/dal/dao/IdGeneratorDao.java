package com.md.idgenerator.server.dal.dao;

import java.util.List;

import com.md.idgenerator.server.bean.entry.IdGenerator;

@MyBatisRepository
public interface IdGeneratorDao {

	public List<IdGenerator> getGenerators();

	public IdGenerator getGenerator(int type);

	public void updateIdGenerator(IdGenerator ig);

	public void insertIdGenerator(IdGenerator ig);
}
