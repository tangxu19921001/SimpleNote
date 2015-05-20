package com.notedemo.model;



import java.io.Serializable;

/**
 * 消息
 */
public class Msg implements Serializable {
	private static final long serialVersionUID = 1L;
	private int msgId;
	/**
	 * 1、记事本，2、备忘+提醒，3、私密
	 */
	private int msgType;
	private String msgTitle;
	private String msgContent;
	private String msgTime;
	/**
	 * 如果是提醒消息，0：未提醒，1：已提醒
	 */
	private int isRemind;

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public int getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(int isRemind) {
		this.isRemind = isRemind;
	}
}

