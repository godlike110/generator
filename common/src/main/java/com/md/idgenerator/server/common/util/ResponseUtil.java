package com.md.idgenerator.server.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class ResponseUtil {

	private static final String JSON_ERRORCODE_NAME = "errorCode";

	private static final String JSON_ERRORMSG_NAME = "errorMessage";

	private static final String JSON_DATA_NAME = "data";

	private static final String JSON_DATA_STATUS = "status";

	private static final String JSON_DATA_MESSAGE = "message";

	private ResponseUtil() {

	}

	/**
	 * <p>
	 * badResult.
	 * </p>
	 * 
	 * @param cause a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String badResult(String cause) {
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 1);
		result.put(JSON_ERRORMSG_NAME, cause);
		return result.toJSONString();
	}

	/**
	 * <p>
	 * ok.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public static String ok() {
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, "成功");
		return result.toString();
	}

	/**
	 * 成功带状态及=信息返回
	 * @param status
	 * @param message
	 * @return
	 */
	public static String okWithStatusAndMesage(int status, String message) {
		JSONObject result = new JSONObject();
		JSONObject ob = new JSONObject();
		ob.put(JSON_DATA_STATUS, status);
		ob.put(JSON_DATA_MESSAGE, message);
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, ob);
		return result.toString();
	}

	/**
	 * <p>
	 * ok.
	 * </p>
	 * 
	 * @param object a {@link java.lang.Object} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String ok(Object object) {
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, object);
		return result.toJSONString();
	}

	/**
	 * @Title ok
	 * @param object a {@link java.lang.Object} object.
	 * @param params a {@link java.util.Map} object.
	 * @return String(返回类型)
	 */
	public static String ok(Object object, Map<String, Object> params) {
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, object);
		if (params != null && params.size() > 0) {
			Iterator<Entry<String, Object>> keys = params.entrySet().iterator();
			while (keys.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) keys.next();
				result.put(String.valueOf(entry.getKey()), entry.getValue());
			}
		}
		return result.toJSONString();
	}

	/**
	 * 返回分页结果json字串
	 * 
	 * @param object 数据对象
	 * @param pageTotal 结果列表总共多少�?     * @param pageSize 每页多少条记�?     * @param pageNo 页号
	 * @return json 字串
	 */
	public static String okWithPaginate(Object object, int pageTotal, int pageSize,
			int pageNo) {
		JSONObject result = new JSONObject();
		result.put("pageTotal", pageTotal);
		result.put("pageSize", pageSize);
		result.put("pageNo", pageNo);
		result.put(JSON_DATA_NAME, object);
		result.put(JSON_ERRORCODE_NAME, 0);
		return result.toJSONString();
	}

	/**
	 * 返回list对象
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String ok(List list) {
		JSONObject result = new JSONObject();
		result.put(JSON_DATA_NAME, list);
		result.put(JSON_ERRORCODE_NAME, 0);
		System.out.println(result.toString());
		return result.toJSONString();
	}

	/**
	 * <p>
	 * jsonp.
	 * </p>
	 * 
	 * @param object a {@link java.lang.Object} object.
	 * @param callback a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String jsonp(Object object, String callback) {
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, object);
		if (StringUtils.isBlank(callback)) {
			return result.toJSONString();
		}
		return callback + "(" + result.toJSONString() + ")";
	}

	public static String okSupportJSONP(Object object, String fun) {
		if (StringUtils.isBlank(fun)) {
			return ok(object);
		}
		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, object);
		return fun + "(" + result.toJSONString() + ")";
	}

	/**
	 * 根据条件返回分页结果的jsonp
	 * 
	 * @param list
	 * @param callback
	 * @param params
	 * @param <T>
	 * @return
	 */
	public static <T> String okSupportJSONPWithPaginate(List<T> list, String callback,
			Map<String, Object> params) {

		JSONObject result = new JSONObject();
		result.put(JSON_ERRORCODE_NAME, 0);
		result.put(JSON_DATA_NAME, list);

		if (params != null && params.size() > 0) {
			Iterator<Map.Entry<String, Object>> keys = params.entrySet().iterator();
			while (keys.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) keys.next();
				result.put(String.valueOf(entry.getKey()), entry.getValue());
			}
		}
		if (StringUtils.isBlank(callback)) {
			return result.toJSONString();
		}
		return callback + "(" + result.toJSONString() + ")";
	}

}
