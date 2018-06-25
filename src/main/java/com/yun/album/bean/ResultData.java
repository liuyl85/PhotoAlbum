package com.yun.album.bean;

/**
 * 结果集
 */
public class ResultData {
	/** 状态 */
	private int status;
	/** 消息 */
	private String msg;
	/** 数据 */
	private Object data;

	public ResultData(int status) {
		this.status = status;
	}
	
	public ResultData(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public ResultData(int status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}
	
}
