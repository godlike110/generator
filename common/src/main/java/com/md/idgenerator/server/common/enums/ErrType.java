package com.md.idgenerator.server.common.enums;

/**
 * 错误
 * @author zhiwei.wen
 * 2015年7月17日
 */
public enum ErrType {

	SUCESS(0, "SUCESS"), KEY_ERR(1, "NO MATCHED TOKEN"), NO_TYPE(2, "NO MUTCHED BUSINESS TYPE"), SYS_ERROR(-1, "SYS ERROR");

	private int type;
	private String msg;

	ErrType(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
