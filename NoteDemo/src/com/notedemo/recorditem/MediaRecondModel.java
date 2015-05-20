package com.notedemo.recorditem;

import java.io.File;

import com.notedemo.config.AppConfig;

import android.content.Context;
import android.media.MediaRecorder;

public class MediaRecondModel {
	Context context;

	String SUFFIX = ".amr";
	File myRecAudioDir;
	TimeInfo myTimeInfo;
	FileInfo myFileInfo;
	MediaRecorder myMediaRecorder = null;
	File recondPath;

	public MediaRecorder getRecordInstance() {
		return myMediaRecorder;
	}

	public MediaRecondModel(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public void init() {
		myFileInfo = new FileInfo(this.context);
		myTimeInfo = new TimeInfo(this.context);
		myRecAudioDir = new File(AppConfig.RecordDir);
	}

	public void startRecord() {
		String mMinute1 = myTimeInfo.getTime();
		recondPath = new File(myRecAudioDir, mMinute1 + SUFFIX);
		intiRecorder(recondPath.getPath());
	}

	public void stopRecord() {
		if (myMediaRecorder != null) {
			myMediaRecorder.stop();
			myMediaRecorder.release();
			myMediaRecorder = null;
		}
	}

	public String getRecordPath() {
		return recondPath.getPath();
	}

	/**
	 * 取消录音
	 */
	public void removeRecordFile() {
		if (recondPath.exists()) {
			recondPath.delete();
		}
	}

	public void intiRecorder(String path) {
		try {
			myMediaRecorder = new MediaRecorder();// //初始化Recorder
			myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// //设置麦克风
			myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// 设置输出格式
			myMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// 设置音频编码Encoder
			myMediaRecorder.setOutputFile(path);// 设置音频文件保存路径
			myMediaRecorder.prepare();// 准备录制
			myMediaRecorder.start();// 开始录制
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}