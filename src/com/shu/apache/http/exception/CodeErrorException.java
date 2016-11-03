package com.shu.apache.http.exception;
/**
 * @ClassName: CodeErrorException 
 * @Description: 执行HTTP请求,当返回码不是200-OK时抛出的异常
 * @author haoran.shu
 * @date 2016-3-14 上午10:46:27
 */
public class CodeErrorException extends Exception {

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1L;
	
	private int code; // responseCode
	private String message; // responseMessage
	
	public CodeErrorException(int code, String message){
		super("code = " + code + " ; message : " + message);
		this.code = code;
		this.message = message;
	}

	public int getResponseCode() {
		return code;
	}
	
	public String getResponseMessage() {
		return message;
	}
}
