package com.notedemo.model;



/**
 * 用来保存程序中使用到的
 * 
 * @author Administrator
 * 
 */
public class BaseAppData {
	private BaseAppData() {
	}

	private static BaseAppData instance;

	public static BaseAppData getInstance() {
		if (instance == null)
			instance = new BaseAppData();
		return instance;
	}
	
	private int loginStatus;

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	

}