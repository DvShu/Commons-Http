package com.shu.apache.http.exception;
/**
 * @ClassName: CodeErrorException 
 * @Description: ִ��HTTP����,�������벻��200-OKʱ�׳����쳣
 * @author haoran.shu
 * @date 2016-3-14 ����10:46:27
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
