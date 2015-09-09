package com.md.idgenerator.server.common.constant;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.md.idgenerator.server.common.annotation.Config;

/**
 * 常量
 * @author zhiwei.wen
 * 2015年7月16日
 */
@Component
public class AptConstants {

	public static final String LOG_SPLIT = "|";

	/**
	 * 个应用分配的token 
	 */
	public static Map<Integer, String> TOKEN_MAP;

	/**
	 * 各业务名称映射
	 */
	public static Map<Integer, String> BUS_NAME_MAP;

	/**
	 * 触发队列填充时的队列大小
	 */
	@Config("queue.minsize")
	public static int MIN_QUEUE_SIZE;

	/**
	 * 队列长度
	 */
	@Config("queue.size")
	public static int QUEUE_SIZE;

	/**
	 * 数据库取数步长
	 */
	@Config("queue.size")
	public static long MILESTONE_STEP;

	/**
	 * 数据库没有找到相应的id生成记录是返回的默认里程碑
	 */
	public static final long HAS_NO_MILESTONE = -1;

	/**
	 * 错误的全局id
	 */
	public static final long ERROR_ID = -1;

}
