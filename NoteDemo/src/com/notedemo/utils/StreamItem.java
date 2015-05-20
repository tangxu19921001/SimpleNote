package com.notedemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import com.notedemo.config.AppConfig;

public class StreamItem {

	String picturePath;
	String videoPath;

	public void bitmapToFIle(Bitmap bitmap) {
		FileOutputStream b = null;
		try {
			getCameraPath();
			b = new FileOutputStream(picturePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文�?
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getCulVideoPath() {
		videoPath = AppConfig.VideoDir + "/" + getCultime() + ".3gp";
	}

	public String getVideoPath() {
		return videoPath;
	}

	/*
	 * try { AssetFileDescriptor videoAsset = getContentResolver()
	 * .openAssetFileDescriptor(data.getData(), "r"); myStreamItem = new
	 * StreamItem(); myStreamItem.videoTOFile(videoAsset); Log.d("dug",
	 * data.getData() + ""); } catch (FileNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 */

	public void videoTOFile(AssetFileDescriptor videoAsset) {
		try {
			getCulVideoPath();
			FileInputStream fis = videoAsset.createInputStream();
			File tmpFile = new File(videoPath);
			FileOutputStream fos = new FileOutputStream(tmpFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = fis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException io_e) {
			// TODO: handle error
		}
	}

	public void getCameraPath() {
		picturePath = AppConfig.CameraDir + "/" + getCultime() + ".jpg";

	}

	public String getPicturePath() {
		return picturePath;
	}

	public long getCultime() {
		long time = System.currentTimeMillis();
		return time;
	}

}
