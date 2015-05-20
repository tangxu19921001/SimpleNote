package com.notedemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtils {

	private static String preference = "MusicEduCloud_FILE";

	public static void setStringPreferences(Context context, String key,
			String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getStringPreference(Context context, String key,
			String defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultValue);
	}

	public static void setLongPreference(Context context, String key, long value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static long getLongPreference(Context context, String key,
			long defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, defaultValue);
	}

	public static void setBooleanPreference(Context context, String key,
			boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBooleanPreference(Context context, String key,
			boolean defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defaultValue);
	}

	// 表情翻页效果
	public static int getFaceEffect(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		return sharedPreferences.getInt("face_effects", 3);
	}

	public static void setFaceEffect(Context context, int effect) {
		if (effect < 0 || effect > 11)
			effect = 3;

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				preference, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("face_effects", effect);
		editor.commit();
	}

}
