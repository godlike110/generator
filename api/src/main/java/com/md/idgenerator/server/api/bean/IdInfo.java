package com.md.idgenerator.server.api.bean;

import java.io.Serializable;


/**
 * 全局id信息
 * @author zhiwei.wen
 * 2015年7月16日
 */
public class IdInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当前取出的全局id
	 */
	private long id;

	/**
	 * 当前队列大小（数据量）
	 */
	private int size;

	/**
	 * 归属哪个里程碑
	 */
	private long mileStone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getMileStone() {
		return mileStone;
	}

	public void setMileStone(long mileStone) {
		this.mileStone = mileStone;
	}

}
