package com.md.idgenerator.server.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 队列工具类
 * @author zhiwei.wen
 * 2015年7月16日
 */
public class QueueUtil {

	public static final Logger logger = LoggerFactory.getLogger(QueueUtil.class);

	private static Map<String, IdGeneratorQueue> qMap = new HashMap<String, IdGeneratorQueue>();

	/**
	 * 获取队列
	 * @param name
	 * @return
	 */
	public static IdGeneratorQueue getIdGeneratorQueue(String name) {
		return qMap.get(name);
	}

	/**
	 * 更新队列
	 * @param queue
	 * @param name
	 */
	public static void putIdGeneratorQueue(IdGeneratorQueue queue, String name) {
		qMap.put(name, queue);
	}

	public static void main(String argv[]) {
		Date time = new Date();
		System.out.print(time.getTime());
	}

}
