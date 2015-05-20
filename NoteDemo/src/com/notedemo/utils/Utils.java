package com.notedemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static float getScreenDensity(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			return dm.density;
		} catch (Exception ex) {

		}
		return 1.0f;
	}

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				res.getDisplayMetrics());
	}

	public static String GetUserType(int UserType) {
		String Result = "老师";
		switch (UserType) {
		case 0:
			Result = "老师";
			break;
		case 1:
			Result = "学生";
			break;
		case 2:
			Result = "家长";
			break;
		}
		return Result;
	}

	public static String GetMsgType(int MsgType) {
		String Result = "通知公告";
		switch (MsgType) {
		case 0:
			Result = "通知公告";
			break;
		case 1:
			Result = "对话交流";
			break;
		case 2:
			Result = "布置作业";
			break;
		case 3:
			Result = "考试成绩";
			break;
		case 4:
			Result = "在校表现评价";
			break;
		case 5:
			Result = "平安校园";
			break;
		}
		return Result;
	}

}
