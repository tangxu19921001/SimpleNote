package com.notedemo.model;



import java.io.Serializable;

/**
 * ��Ϣ
 */
public class Msg implements Serializable {
	private static final long serialVersionUID = 1L;
	private int msgId;
	/**
	 * 1�����±���2������+���ѣ�3��˽��
	 */
	private int msgType;
	private String msgTitle;
	private String msgContent;
	private String msgTime;
	/**
	 * �����������Ϣ��0��δ���ѣ�1��������
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

