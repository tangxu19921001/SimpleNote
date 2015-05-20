package com.notedemo.adapter;

import java.io.File;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import com.notedemo.activity.R;
import com.notedemo.recorditem.MediaPlayModel;
import com.notedemo.config.AppConfig;
import com.notedemo.utils.ExpressionUtil;

/**
 * 消息处理
 * 
 * @author Administrator
 * 
 */

public class DealAdapter {

	Context context;
	MediaPlayModel myMediaPlayModel;
	int screenW;
	int screenH;

	public DealAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		myMediaPlayModel = new MediaPlayModel(this.context);
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		screenH = dm.heightPixels;
	}

	/********************************/
	public SpannableString getSpanString(String str) {
		String zhengze = "f0[0-9]{2}|f10[0-7]";
		SpannableString mystring = ExpressionUtil.getExpressionString(
				this.context, str, zhengze);
		return mystring;
	}

	public int getVoiceLeftPlayingId(int playingType) {
		// int[] playingId = {R.drawable.edit_voice_playing_f3,
		// R.drawable.edit_voice_playing_f1, R.drawable.edit_voice_playing_f2};
		int[] playingId = { R.drawable.right_play_active_1,
				R.drawable.right_play_active_2, R.drawable.right_play_active_3 };
		return playingId[playingType];
	}

	public int getVoiceRightPlayingId(int playingType) {
		int[] playingId = { R.drawable.left_play_active_3,
				R.drawable.left_play_active_2, R.drawable.left_play_active_1 };
		return playingId[playingType];
	}

	public Bitmap getPictureBmp(int type, String content) {
		Bitmap pictureBmp = null;
		pictureBmp = getStringToPath(content, 80, 80);
		return pictureBmp;
	}

	public Bitmap getVideoFromUrl(Uri videouri) {
		long id = ContentUris.parseId(videouri);
		ContentResolver mContentResolver = context.getContentResolver();

		// 缩略图类型：MINI_KIND FULL_SCREEN_KIND MICRO_KIND
		Bitmap miniThumb = Video.Thumbnails.getThumbnail(mContentResolver, id,
				Video.Thumbnails.MICRO_KIND, null);
		return miniThumb;
	}

	public Bitmap getStringToPath(String path, int dstWidth, int dstHeight) {
		File newFile = new File(path);
		if (newFile.exists()) {

			return creatScaleBitmap(BitmapFactory.decodeFile(path));
			// return
			// MyBitMap.createScaledBitmap(BitmapFactory.decodeFile(path),
			// dstWidth, dstHeight, ScaleType.FIT_XY);
		} else {
			return null;
		}
	}

	/**********************************************/
	public Bitmap creatScaleBitmap(Bitmap unscaleBitmap) {
		Rect srcRect = new Rect(0, 0, unscaleBitmap.getWidth(),
				unscaleBitmap.getHeight());
		Rect dstRect = getDstRect(unscaleBitmap.getWidth(),
				unscaleBitmap.getHeight());
		Bitmap scaleBitmap = Bitmap.createBitmap(dstRect.width(),
				dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaleBitmap);
		canvas.drawBitmap(unscaleBitmap, srcRect, dstRect, new Paint(
				Paint.FILTER_BITMAP_FLAG));
		return scaleBitmap;
	}

	public Bitmap creatScaleBitmap(Bitmap unscaleBitmap, int dstWidth,
			int dstHeight) {
		Rect srcRect = new Rect(0, 0, dstWidth, dstHeight);
		Rect dstRect = getDstRect(dstWidth, dstHeight);
		Bitmap scaleBitmap = Bitmap.createBitmap(dstRect.width(),
				dstRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(scaleBitmap);
		canvas.drawBitmap(unscaleBitmap, srcRect, dstRect, new Paint(
				Paint.FILTER_BITMAP_FLAG));
		return scaleBitmap;
	}

	public Rect getDstRect(int srcWidth, int srcHeight) {
		float srcAspect = (float) srcWidth / (float) srcHeight;
		float dstAspect = (float) screenW / (float) screenH;
		if (srcAspect > dstAspect) {
			return new Rect(0, 0, screenW / 3,
					(int) ((screenW / 3) / srcAspect));
		} else {
			return new Rect(0, 0, (int) ((screenH / 3) * srcAspect),
					screenH / 3);
		}
	}

	/*****************************/
	public void stopMyMedia() {
		if (myMediaPlayModel.getisPlaying()) {
			myMediaPlayModel.stopMedia();
		}
	}

	public void onPlayButton(String path) {
		if (myMediaPlayModel.getisPlaying()) {
			if (myMediaPlayModel.getPlayPath().equals(path)) {
				myMediaPlayModel.stopMedia();
			} else {
				myMediaPlayModel.stopMedia();
				myMediaPlayModel.playMedia(path);
			}
		} else {
			myMediaPlayModel.playMedia(path);
		}
	}

	public boolean getIsPlaying() {
		boolean isPlaying = myMediaPlayModel.getisPlaying();
		return isPlaying;
	}
	/******************************/

}
