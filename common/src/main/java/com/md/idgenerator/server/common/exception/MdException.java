package com.md.idgenerator.server.common.exception;

/**
 * 
 * MdException 
 * @author chenchao
 * @date Jul 14, 2015 8:22:16 PM
 *
 */
public class MdException extends RuntimeException {

	private static final long serialVersionUID = 2195068675053227207L;

	private int errorCode;

	private String errorMsg;

	public MdException() {
		super();
	}

	public MdException(String message, int errorCode, String errorMsg) {
		super(message);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
