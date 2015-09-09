package com.md.idgenerator.server.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * id生成器队列
 * @author zhiwei.wen
 * 2015年7月16日
 */
public class IdGeneratorQueue {

	private BlockingQueue<Long> queue;

	@SuppressWarnings("unused")
	private String name;

	@SuppressWarnings("unused")
	private int size;

	private long milestone;

	/**
	 * 
	 * @param name 名称
	 * @param size 大小
	 * @param milestone 当前里程碑
	 */
	public IdGeneratorQueue(String name, int size, long milestone) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.size = size;
		this.milestone = milestone;
		this.queue = new LinkedBlockingDeque<Long>(size);
	}

	/**
	 * 放入队列
	 * @param id
	 * @throws InterruptedException 
	 */
	public void putId(long id) throws InterruptedException {
		this.queue.put(id);
		;
	}

	/**
	 * 获取id
	 * @return
	 * @throws InterruptedException 
	 */
	public long getId() throws InterruptedException {
		return this.queue.take();
	}

	/**
	 * 获取队列里数当前的里程碑
	 * @return
	 */
	public long getMileStone() {
		return this.milestone;
	}

	public void setMileStone(long mileStone) {
		this.milestone = mileStone;
	}

	/**
	 * 获取队列当前大小
	 * @return
	 */
	public int getSize() {
		return this.queue.size();
	}

}
