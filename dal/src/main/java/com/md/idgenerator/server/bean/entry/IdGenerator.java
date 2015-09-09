package com.md.idgenerator.server.bean.entry;

import java.util.Date;

import com.md.idgenerator.server.common.util.BaseObject;

/**
 * 
 * @author zhiwei.wen
 * 2015年7月15日
 */
public class IdGenerator extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 645342352804372255L;

	private int id;
	//类型
	private int type;
	//类型名称
	private String name;
	//类型对应的KEY
	private String busKey;
	//已取id记录
	private long milestone;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBusKey() {
		return busKey;
	}

	public void setBusKey(String busKey) {
		this.busKey = busKey;
	}

	public long getMilestone() {
		return milestone;
	}

	public void setMilestone(long milestone) {
		this.milestone = milestone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
