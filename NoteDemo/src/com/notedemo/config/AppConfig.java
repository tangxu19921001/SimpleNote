package com.notedemo.config;


import android.os.Environment;

public class AppConfig {
	/**
	 * ��Ϣ����:1�����±���2������+���ѣ�3��˽��
	 * 
	 * @author Administrator
	 * 
	 */
	public class MsgType {
		public static final int JISHI = 1;
		public static final int TIXING = 2;
		public static final int SIMI = 3;
	}

	/**
	 * �������ͣ�1��ͼƬ��2����Ƶ
	 * 
	 * @author Administrator
	 * 
	 */
	public class AttachType {
		public static final int PICTURE = 1;
		public static final int VEDIO = 2;
	}

	public static String ProjectDir = Environment.getExternalStorageDirectory()
			.getPath() + "/dialy/";
	public static String CameraDir = ProjectDir + "images";
	public static String RecordDir = ProjectDir + "records";
	public static String VideoDir = ProjectDir + "videos";
	public static String BackUpDir = ProjectDir + "backup";
	public static String BackinDir = ProjectDir + "backupin";
	public static final int NOTIFY_ID = 0x9999;// ֪ͨID
}