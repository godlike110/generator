package com.md.idgenerator.server.common.util;

/**
 * 
 * RES_STATUS 
 * @author chenchao
 * @date Jul 14, 2015 8:19:32 PM
 *
 */
public enum RES_STATUS {

	ERROR_PARAM(10001), SERVICE_ERROR(10011),

	SUCCESS(0);

	RES_STATUS(int code) {
		this.code = code;
	}

	public final int code;

	public static RES_STATUS findStatusByCode(int code) {
		for (RES_STATUS status : RES_STATUS.values()) {
			if (status.code == code) {
				return status;
			}
		}
		return null;
	}

}
