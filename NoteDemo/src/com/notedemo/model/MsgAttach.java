package com.notedemo.model;

public class MsgAttach {
	private static final long serialVersionUID = 1L;
	private int attachId;
	private int msgId;
	/**
	 * 1、图片，2、音频
	 */
	private int attachType;
	private String attachContent;

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int msgAttachId) {
		this.attachId = msgAttachId;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getAttachType() {
		return attachType;
	}

	public void setAttachType(int attachType) {
		this.attachType = attachType;
	}

	public String getAttachContent() {
		return attachContent;
	}

	public void setAttachContent(String attachContent) {
		this.attachContent = attachContent;
	}
}
