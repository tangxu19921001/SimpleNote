package com.notedemo.activity;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Window;

import com.notedemo.android.GestureImageView;
import com.notedemo.image.WebImageCache;

public class ImageBigActivity extends Activity {
	private Bitmap myBitmap;
	private GestureImageView myImageView;
	private static WebImageCache webImageCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagebig);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");

		if (path.contains("http")) {
			if (webImageCache == null) {
				webImageCache = new WebImageCache(this);
			}

			Bitmap bitmap = webImageCache.get(path);
			if (bitmap == null) {
				bitmap = getBitmapFromUrl(path);
				if (bitmap != null) {
					webImageCache.put(path, bitmap);
				}
			}
			myBitmap = bitmap;
		} else {
			myBitmap = myScaleBitmap(path);
		}

		init();
	}

	public void init() {

		myImageView = (GestureImageView) findViewById(R.id.dmImageView);
		myImageView.setImageBitmap(myBitmap);

	}

	public Bitmap myScaleBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(path, options);

		if (options.mCancel || options.outWidth == -1
				|| options.outHeight == -1) {

			return null;

		}

		options.inSampleSize = computeSampleSize(options, 600,
				(int) (1 * 1024 * 1024));

		options.inJustDecodeBounds = false;

		options.inDither = false;

		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public Bitmap getBitmapFromUrl(String url) {
		Bitmap bitmap = null;

		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			bitmap = BitmapFactory
					.decodeStream((InputStream) conn.getContent());

			if (bitmap.getWidth() > bitmap.getHeight()) {
				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						bitmap.getHeight(), matrix, true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}
}