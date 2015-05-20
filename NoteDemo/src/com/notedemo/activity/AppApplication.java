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
	public static String gAppName; // Ӧ������
//	public static int gNetWorkState = NetworkUtils.NETWORN_NONE; // ����״̬��mobile��3G
	public static int gVersionCode; // �汾
	public static String gVersionName; // �汾����
	public static String gDownloadPath; // ����·��
	public static String gSdcardDataDir; // SD��·��
	public static String gApkDownloadUrl = null; // APK����·��
	private Map<String, Integer> gFaceMap = new LinkedHashMap<String, Integer>();
	private List<Activity> gActivities; // ����������ͨ��ҳ��
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
		//V5Platform.getInstance(this).init();//V5��ʼ��
	}

	/**
	 * ��ʼ��DB
	 */
	public void initDB() {
		gMsgDB = new MsgDB(this);
//		gUserDB = new UserDB(this);
		gMsgAttachDB = new MsgAttachDB(this);
		gNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	/*public void initEnv() {
		gAppName = "���˼��±�";
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
		//V5Platform.getInstance(this).release();//V5��Դ�ͷ�
	}

	private void initFaceMap() {
		// TODO Auto-generated method stub
		gFaceMap.put("[����]", R.drawable.f000);
		gFaceMap.put("[��Ƥ]", R.drawable.f001);
		gFaceMap.put("[����]", R.drawable.f002);
		gFaceMap.put("[͵Ц]", R.drawable.f003);
		gFaceMap.put("[�ټ�]", R.drawable.f004);
		gFaceMap.put("[�ô�]", R.drawable.f005);
		gFaceMap.put("[����]", R.drawable.f006);
		gFaceMap.put("[��ͷ]", R.drawable.f007);
		gFaceMap.put("[õ��]", R.drawable.f008);
		gFaceMap.put("[����]", R.drawable.f009);
		gFaceMap.put("[���]", R.drawable.f010);
		gFaceMap.put("[��]", R.drawable.f011);
		gFaceMap.put("[��]", R.drawable.f012);
		gFaceMap.put("[ץ��]", R.drawable.f013);
		gFaceMap.put("[ί��]", R.drawable.f014);
		gFaceMap.put("[���]", R.drawable.f015);
		gFaceMap.put("[ը��]", R.drawable.f016);
		gFaceMap.put("[�˵�]", R.drawable.f017);
		gFaceMap.put("[�ɰ�]", R.drawable.f018);
		gFaceMap.put("[ɫ]", R.drawable.f019);
		gFaceMap.put("[����]", R.drawable.f020);
		gFaceMap.put("[����]", R.drawable.f021);
		gFaceMap.put("[��]", R.drawable.f022);
		gFaceMap.put("[΢Ц]", R.drawable.f023);
		gFaceMap.put("[��ŭ]", R.drawable.f024);
		gFaceMap.put("[����]", R.drawable.f025);
		gFaceMap.put("[����]", R.drawable.f026);
		gFaceMap.put("[�亹]", R.drawable.f027);
		gFaceMap.put("[����]", R.drawable.f028);
		gFaceMap.put("[ʾ��]", R.drawable.f029);
		gFaceMap.put("[����]", R.drawable.f030);
		gFaceMap.put("[����]", R.drawable.f031);
		gFaceMap.put("[�ѹ�]", R.drawable.f032);
		gFaceMap.put("[����]", R.drawable.f033);
		gFaceMap.put("[����]", R.drawable.f034);
		gFaceMap.put("[˯]", R.drawable.f035);
		gFaceMap.put("[����]", R.drawable.f036);
		gFaceMap.put("[��Ц]", R.drawable.f037);
		gFaceMap.put("[����]", R.drawable.f038);
		gFaceMap.put("[˥]", R.drawable.f039);
		gFaceMap.put("[Ʋ��]", R.drawable.f040);
		gFaceMap.put("[����]", R.drawable.f041);
		gFaceMap.put("[�ܶ�]", R.drawable.f042);
		gFaceMap.put("[����]", R.drawable.f043);
		gFaceMap.put("[�Һߺ�]", R.drawable.f044);
		gFaceMap.put("[ӵ��]", R.drawable.f045);
		gFaceMap.put("[��Ц]", R.drawable.f046);
		gFaceMap.put("[����]", R.drawable.f047);
		gFaceMap.put("[����]", R.drawable.f048);
		gFaceMap.put("[��]", R.drawable.f049);
		gFaceMap.put("[���]", R.drawable.f050);
		gFaceMap.put("[����]", R.drawable.f051);
		gFaceMap.put("[ǿ]", R.drawable.f052);
		gFaceMap.put("[��]", R.drawable.f053);
		gFaceMap.put("[����]", R.drawable.f054);
		gFaceMap.put("[ʤ��]", R.drawable.f055);
		gFaceMap.put("[��ȭ]", R.drawable.f056);
		gFaceMap.put("[��л]", R.drawable.f057);
		gFaceMap.put("[��]", R.drawable.f058);
		gFaceMap.put("[����]", R.drawable.f059);
		gFaceMap.put("[����]", R.drawable.f060);
		gFaceMap.put("[ơ��]", R.drawable.f061);
		gFaceMap.put("[Ʈ��]", R.drawable.f062);

		gFaceMap.put("[����]", R.drawable.f063);
		gFaceMap.put("[OK]", R.drawable.f064);
		gFaceMap.put("[����]", R.drawable.f065);
		gFaceMap.put("[����]", R.drawable.f066);
		gFaceMap.put("[Ǯ]", R.drawable.f067);
		gFaceMap.put("[����]", R.drawable.f068);
		gFaceMap.put("[��Ů]", R.drawable.f069);
		gFaceMap.put("[��]", R.drawable.f070);
		gFaceMap.put("[����]", R.drawable.f071);
		gFaceMap.put("[�]", R.drawable.f072);
		gFaceMap.put("[ȭͷ]", R.drawable.f073);
		gFaceMap.put("[����]", R.drawable.f074);
		gFaceMap.put("[̫��]", R.drawable.f075);
		gFaceMap.put("[����]", R.drawable.f076);
		gFaceMap.put("[����]", R.drawable.f077);
		gFaceMap.put("[����]", R.drawable.f078);
		gFaceMap.put("[����]", R.drawable.f079);
		gFaceMap.put("[����]", R.drawable.f080);
		gFaceMap.put("[����]", R.drawable.f081);
		gFaceMap.put("[��]", R.drawable.f082);
		gFaceMap.put("[����]", R.drawable.f083);
		gFaceMap.put("[��ĥ]", R.drawable.f084);
		gFaceMap.put("[�ٱ�]", R.drawable.f085);
		gFaceMap.put("[����]", R.drawable.f086);
		gFaceMap.put("[�ܴ���]", R.drawable.f087);
		gFaceMap.put("[��ߺ�]", R.drawable.f088);
		gFaceMap.put("[��Ƿ]", R.drawable.f089);
		gFaceMap.put("[�����]", R.drawable.f090);
		gFaceMap.put("[��]", R.drawable.f091);
		gFaceMap.put("[����]", R.drawable.f092);
		gFaceMap.put("[ƹ����]", R.drawable.f093);
		gFaceMap.put("[NO]", R.drawable.f094);
		gFaceMap.put("[����]", R.drawable.f095);
		gFaceMap.put("[���]", R.drawable.f096);
		gFaceMap.put("[תȦ]", R.drawable.f097);
		gFaceMap.put("[��ͷ]", R.drawable.f098);
		gFaceMap.put("[��ͷ]", R.drawable.f099);
		gFaceMap.put("[����]", R.drawable.f100);
		gFaceMap.put("[����]", R.drawable.f101);
		gFaceMap.put("[����]", R.drawable.f102);
		gFaceMap.put("[����]", R.drawable.f103);
		gFaceMap.put("[��̫��]", R.drawable.f104);
		gFaceMap.put("[��̫��]", R.drawable.f105);
		gFaceMap.put("[����]", R.drawable.f106);
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
