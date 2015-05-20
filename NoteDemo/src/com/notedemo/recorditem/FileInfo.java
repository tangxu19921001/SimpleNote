package com.notedemo.recorditem;

/*
 * class name：FIleInfo
 * class description：use FIleInfo to operate file
 * @version 11:02 2013/1/29
 * @author ZhangJianLin

 <!-- 在SDCard中创建与删除文件权限 -->  
 <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>  
 <!-- 往SDCard写入数据权限 -->  
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>   
 */
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.notedemo.activity.R;
import com.notedemo.config.AppConfig;

public class FileInfo {
	Context context;

	public FileInfo(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void mkProjectDirect() {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this.context, R.string.sdcardmiss, Toast.LENGTH_LONG)
					.show();
		} else {
			creatSDDir(AppConfig.ProjectDir);
			creatSDDir(AppConfig.CameraDir);
			creatSDDir(AppConfig.RecordDir);
			creatSDDir(AppConfig.BackUpDir);
		}
	}

	// FileSize and LastModifiedInfo
	public String getFileInfo(String path) {
		String fileInfo = this.getFileSize(path);
		return fileInfo;
	}

	// translate byte into KB or KB
	public String getFileSize(String path) {
		File file = new File(path);
		String size;
		long length = file.length();
		if (length >= 1024.0 * 1024) {
			size = String.valueOf(length / (1024.0 * 1024)) + "M";
		} else {
			size = String.valueOf(length / 1024.0) + "K";
		}
		if (size.length() > 7)
			size = size.substring(0, 7)
					+ size.substring(size.length() - 1, size.length());
		return size;
	}

	public boolean delFile(String path) {
		File file = new File(path);
		boolean isDel = false;
		if (file.exists()) {
			isDel = file.delete();

		}
		return isDel;
	}

	// the last time modifiedinfo
	public String getFileLastModifiedInfo(String path) {
		File file = new File(path);
		Long time = file.lastModified();
		String timeinfo = this.calenderTime(time);
		return timeinfo;
	}

	// translate date into string
	public String calenderTime(long time) {
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(time);
		return String.valueOf(cd.getTime());
	}

	// create dirPath direct
	public File creatSDDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	// create file by filePath that given
	public File CreatSDFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}

	public boolean isDirExit(String path) {
		boolean isExit;
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this.context, "sd卡不存在", Toast.LENGTH_LONG).show();
			isExit = false;
		} else {
			File file = new File(path);
			if (file.exists() || file.isDirectory()) {
				isExit = true;
			} else {
				isExit = false;
			}

		}
		return isExit;
	}

	/**
	 * 
	 * @param path
	 *            the path of file
	 * @return true or false
	 */
	public boolean isFileExit(String path) {
		boolean isExit;
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this.context, "sd卡不存在", Toast.LENGTH_LONG).show();
			isExit = false;
		} else {
			File file = new File(path);
			if (file.exists() || file.isFile()) {
				isExit = true;
			} else {
				isExit = false;
			}

		}
		return isExit;
	}

	private String getMIMEType(File path) {

		String end = path
				.getName()
				.substring(path.getName().lastIndexOf(".") + 1,
						path.getName().length()).toLowerCase();
		String type = "";
		if (end.equals("mp3") || end.equals("aac") || end.equals("amr")
				|| end.equals("mpeg") || end.equals("mp4")) {
			type = "audio";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg")) {
			type = "image";
		} else {
			type = "*";
		}
		type += "/";
		return type;
	}

	/**
	 * 
	 * @param dir
	 *            the dir of path
	 * @param name
	 *            the name of file,no fuffix
	 * @param suffix
	 *            the suffix of file,such as .mp3
	 * @return the FillPath of a file
	 */
	public String getFillPath(String dir, String name, String suffix) {
		File file = new File(dir, name + suffix);
		return file.getPath();
	}

	/**
	 * get SD PATH
	 * 
	 * @return sdPath + "/"
	 */
	public String getSDDir() {
		String SDPATH = null;
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this.context, "sd卡不存在", Toast.LENGTH_LONG).show();
		} else {
			SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
		}
		return SDPATH;
	}

}
