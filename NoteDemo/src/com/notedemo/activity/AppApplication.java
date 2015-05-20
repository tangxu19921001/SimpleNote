package com.notedemo.activity;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.notedemo.db.MsgAttachDB;
import com.notedemo.db.MsgDB;



public class AppApplication extends Application {
	public static String gAppName; // Ó¦ÓÃÃû³Æ
//	public static int gNetWorkState = NetworkUtils.NETWORN_NONE; // ÍøÂç×´Ì¬£ºmobileºÍ3G
	public static int gVersionCode; // °æ±¾
	public static String gVersionName; // °æ±¾Ãû³Æ
	public static String gDownloadPath; // ÏÂÔØÂ·¾¶
	public static String gSdcardDataDir; // SD¿¨Â·¾¶
	public static String gApkDownloadUrl = null; // APKÏÂÔØÂ·¾¶
	private Map<String, Integer> gFaceMap = new LinkedHashMap<String, Integer>();
	private List<Activity> gActivities; // ÓÃÀ´±£´æÆÕÍ¨µÄÒ³Ãæ
	private static AppApplication gApp;
//	private UserDB gUserDB;
	private MsgDB gMsgDB;
	private MsgAttachDB gMsgAttachDB;
	private NotificationManager gNotificationManager;
	private Notification gNotification;

	@Override
	public void onCreate() {
		gApp = this;
		gActivities = new ArrayList<Activity>();
		initDB();
	//	initEnv();
		initFaceMap();
		//V5Platform.getInstance(this).init();//V5³õÊ¼»¯
	}

	/**
	 * ³õÊ¼»¯DB
	 */
	public void initDB() {
		gMsgDB = new MsgDB(this);
//		gUserDB = new UserDB(this);
		gMsgAttachDB = new MsgAttachDB(this);
		gNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	/*public void initEnv() {
		gAppName = "¸öÈË¼ÇÊÂ±¾";
		gDownloadPath = "/notedemo/download";
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/notedemo/config/");
			if (!file.exists()) {
				if (file.mkdirs()) {
					gSdcardDataDir = file.getAbsolutePath();
				}
			} else {
				gSdcardDataDir = file.getAbsolutePath();
			}
		}
	//	gNetWorkState = NetworkUtils.getNetworkState(this);
	}*/

	public Map<String, Integer> getFaceMap() {
		if (!gFaceMap.isEmpty())
			return gFaceMap;
		return null;
	}

	public NotificationManager getNotificationManager() {
		if (gNotificationManager == null)
			gNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return gNotificationManager;
	}

	public synchronized MsgDB getMsgDB() {
		if (gMsgDB == null)
			gMsgDB = new MsgDB(this);
		return gMsgDB;
	}

	/*public synchronized UserDB getUserDB() {
		if (gUserDB == null)
			gUserDB = new UserDB(this);
		return gUserDB
	}*/

	public synchronized MsgAttachDB getMsgAttachDB() {
		if (gMsgAttachDB == null)
			gMsgAttachDB = new MsgAttachDB(this);
		return gMsgAttachDB;
	}

	public void exitApp(Context context) {
	}

	public static AppApplication getInstance() {
		return gApp;
	}

	public void addActivity(Activity activity) {
		gActivities.add(activity);
	}

	public void exit() {
		for (Activity a : gActivities) {
			if (a != null) {
				a.finish();
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	public void exitActivity() {
		for (Activity a : gActivities) {
			if (a != null) {
				a.finish();
			}
		}
	}

	@Override
	public void onTerminate() {
		exit();
		super.onTerminate();
		//V5Platform.getInstance(this).release();//V5×ÊÔ´ÊÍ·Å
	}

	private void initFaceMap() {
		// TODO Auto-generated method stub
		gFaceMap.put("[ßÚÑÀ]", R.drawable.f000);
		gFaceMap.put("[µ÷Æ¤]", R.drawable.f001);
		gFaceMap.put("[Á÷º¹]", R.drawable.f002);
		gFaceMap.put("[ÍµÐ¦]", R.drawable.f003);
		gFaceMap.put("[ÔÙ¼û]", R.drawable.f004);
		gFaceMap.put("[ÇÃ´ò]", R.drawable.f005);
		gFaceMap.put("[²Áº¹]", R.drawable.f006);
		gFaceMap.put("[ÖíÍ·]", R.drawable.f007);
		gFaceMap.put("[Ãµ¹å]", R.drawable.f008);
		gFaceMap.put("[Á÷Àá]", R.drawable.f009);
		gFaceMap.put("[´ó¿Þ]", R.drawable.f010);
		gFaceMap.put("[Ðê]", R.drawable.f011);
		gFaceMap.put("[¿á]", R.drawable.f012);
		gFaceMap.put("[×¥¿ñ]", R.drawable.f013);
		gFaceMap.put("[Î¯Çü]", R.drawable.f014);
		gFaceMap.put("[±ã±ã]", R.drawable.f015);
		gFaceMap.put("[Õ¨µ¯]", R.drawable.f016);
		gFaceMap.put("[²Ëµ¶]", R.drawable.f017);
		gFaceMap.put("[¿É°®]", R.drawable.f018);
		gFaceMap.put("[É«]", R.drawable.f019);
		gFaceMap.put("[º¦Ðß]", R.drawable.f020);
		gFaceMap.put("[µÃÒâ]", R.drawable.f021);
		gFaceMap.put("[ÍÂ]", R.drawable.f022);
		gFaceMap.put("[Î¢Ð¦]", R.drawable.f023);
		gFaceMap.put("[·¢Å­]", R.drawable.f024);
		gFaceMap.put("[ÞÏÞÎ]", R.drawable.f025);
		gFaceMap.put("[¾ª¿Ö]", R.drawable.f026);
		gFaceMap.put("[Àäº¹]", R.drawable.f027);
		gFaceMap.put("[°®ÐÄ]", R.drawable.f028);
		gFaceMap.put("[Ê¾°®]", R.drawable.f029);
		gFaceMap.put("[°×ÑÛ]", R.drawable.f030);
		gFaceMap.put("[°ÁÂý]", R.drawable.f031);
		gFaceMap.put("[ÄÑ¹ý]", R.drawable.f032);
		gFaceMap.put("[¾ªÑÈ]", R.drawable.f033);
		gFaceMap.put("[ÒÉÎÊ]", R.drawable.f034);
		gFaceMap.put("[Ë¯]", R.drawable.f035);
		gFaceMap.put("[Ç×Ç×]", R.drawable.f036);
		gFaceMap.put("[º©Ð¦]", R.drawable.f037);
		gFaceMap.put("[°®Çé]", R.drawable.f038);
		gFaceMap.put("[Ë¥]", R.drawable.f039);
		gFaceMap.put("[Æ²×ì]", R.drawable.f040);
		gFaceMap.put("[ÒõÏÕ]", R.drawable.f041);
		gFaceMap.put("[·Ü¶·]", R.drawable.f042);
		gFaceMap.put("[·¢´ô]", R.drawable.f043);
		gFaceMap.put("[ÓÒºßºß]", R.drawable.f044);
		gFaceMap.put("[Óµ±§]", R.drawable.f045);
		gFaceMap.put("[»µÐ¦]", R.drawable.f046);
		gFaceMap.put("[·ÉÎÇ]", R.drawable.f047);
		gFaceMap.put("[±ÉÊÓ]", R.drawable.f048);
		gFaceMap.put("[ÔÎ]", R.drawable.f049);
		gFaceMap.put("[´ó±ø]", R.drawable.f050);
		gFaceMap.put("[¿ÉÁ¯]", R.drawable.f051);
		gFaceMap.put("[Ç¿]", R.drawable.f052);
		gFaceMap.put("[Èõ]", R.drawable.f053);
		gFaceMap.put("[ÎÕÊÖ]", R.drawable.f054);
		gFaceMap.put("[Ê¤Àû]", R.drawable.f055);
		gFaceMap.put("[±§È­]", R.drawable.f056);
		gFaceMap.put("[µòÐ»]", R.drawable.f057);
		gFaceMap.put("[·¹]", R.drawable.f058);
		gFaceMap.put("[µ°¸â]", R.drawable.f059);
		gFaceMap.put("[Î÷¹Ï]", R.drawable.f060);
		gFaceMap.put("[Æ¡¾Æ]", R.drawable.f061);
		gFaceMap.put("[Æ®³æ]", R.drawable.f062);

		gFaceMap.put("[¹´Òý]", R.drawable.f063);
		gFaceMap.put("[OK]", R.drawable.f064);
		gFaceMap.put("[°®Äã]", R.drawable.f065);
		gFaceMap.put("[¿§·È]", R.drawable.f066);
		gFaceMap.put("[Ç®]", R.drawable.f067);
		gFaceMap.put("[ÔÂÁÁ]", R.drawable.f068);
		gFaceMap.put("[ÃÀÅ®]", R.drawable.f069);
		gFaceMap.put("[µ¶]", R.drawable.f070);
		gFaceMap.put("[·¢¶¶]", R.drawable.f071);
		gFaceMap.put("[²î¾¢]", R.drawable.f072);
		gFaceMap.put("[È­Í·]", R.drawable.f073);
		gFaceMap.put("[ÐÄËé]", R.drawable.f074);
		gFaceMap.put("[Ì«Ñô]", R.drawable.f075);
		gFaceMap.put("[ÀñÎï]", R.drawable.f076);
		gFaceMap.put("[×ãÇò]", R.drawable.f077);
		gFaceMap.put("[÷¼÷Ã]", R.drawable.f078);
		gFaceMap.put("[»ÓÊÖ]", R.drawable.f079);
		gFaceMap.put("[ÉÁµç]", R.drawable.f080);
		gFaceMap.put("[¼¢¶ö]", R.drawable.f081);
		gFaceMap.put("[À§]", R.drawable.f082);
		gFaceMap.put("[ÖäÂî]", R.drawable.f083);
		gFaceMap.put("[ÕÛÄ¥]", R.drawable.f084);
		gFaceMap.put("[¿Ù±Ç]", R.drawable.f085);
		gFaceMap.put("[¹ÄÕÆ]", R.drawable.f086);
		gFaceMap.put("[ôÜ´óÁË]", R.drawable.f087);
		gFaceMap.put("[×óºßºß]", R.drawable.f088);
		gFaceMap.put("[¹þÇ·]", R.drawable.f089);
		gFaceMap.put("[¿ì¿ÞÁË]", R.drawable.f090);
		gFaceMap.put("[ÏÅ]", R.drawable.f091);
		gFaceMap.put("[ÀºÇò]", R.drawable.f092);
		gFaceMap.put("[Æ¹ÅÒÇò]", R.drawable.f093);
		gFaceMap.put("[NO]", R.drawable.f094);
		gFaceMap.put("[ÌøÌø]", R.drawable.f095);
		gFaceMap.put("[âæ»ð]", R.drawable.f096);
		gFaceMap.put("[×ªÈ¦]", R.drawable.f097);
		gFaceMap.put("[¿ÄÍ·]", R.drawable.f098);
		gFaceMap.put("[»ØÍ·]", R.drawable.f099);
		gFaceMap.put("[ÌøÉþ]", R.drawable.f100);
		gFaceMap.put("[¼¤¶¯]", R.drawable.f101);
		gFaceMap.put("[½ÖÎè]", R.drawable.f102);
		gFaceMap.put("[Ï×ÎÇ]", R.drawable.f103);
		gFaceMap.put("[×óÌ«¼«]", R.drawable.f104);
		gFaceMap.put("[ÓÒÌ«¼«]", R.drawable.f105);
		gFaceMap.put("[±Õ×ì]", R.drawable.f106);
	}

	/*public void initLocalVersion() {
		PackageInfo pinfo;
		try {
			pinfo = this.getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			gVersionCode = pinfo.versionCode;
			gVersionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}*/
}
