package com.notedemo.service;

import java.io.File;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.notedemo.activity.AppApplication;
import com.notedemo.activity.NewItemActivity;
import com.notedemo.activity.R;
import com.notedemo.config.AppConfig;
import com.notedemo.db.MsgDB;
import com.notedemo.model.BaseAppData;
import com.notedemo.model.Msg;
import com.notedemo.utils.DateUtil;
import com.notedemo.utils.PreferencesUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class PollingService extends Service {
	public static final String ACTION = "com.notedemo.service.PollingService";
	// 有新的提醒信息
	private static final int NewRemindMsg = 0x001;
	private AppApplication app;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private Context mContext = this;
	private MsgDB mMsgDB;
	public boolean isRun = true;
	// 用来更新通知栏消息的handler
	private Handler mstReceivedHandler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NewRemindMsg:
				ArrayList list = (ArrayList) msg.getData()
						.getParcelableArrayList("msg");
				List<Msg> msgList = (List<Msg>) list.get(0);
				if (msgList.size() > 0) {
					// 更新通知栏
					int icon = R.drawable.icon_notice;
					CharSequence tickerText = "您有新的消息!";
					long when = System.currentTimeMillis();
					mNotification = new Notification(icon, tickerText, when);
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					String set_sound = PreferencesUtils.getStringPreference(
							mContext, "set_sound", "1");
					String set_shake = PreferencesUtils.getStringPreference(
							mContext, "set_shake", "0");
					// 铃声和震动提示
					if (set_sound.equals("1") && set_shake.equals("1")) {
						mNotification.defaults |= Notification.DEFAULT_SOUND;
					} else if (set_shake.equals("1"))// 铃声提醒
					{
						mNotification.defaults |= Notification.DEFAULT_VIBRATE;
					} else// 震动提醒
					{
						mNotification.defaults |= Notification.DEFAULT_SOUND;
					}
					mNotification.contentView = null;
					Bundle bundle = new Bundle();
					bundle.putSerializable("msg", (Serializable) msgList.get(0));
					Intent intent = new Intent(mContext, NewItemActivity.class);
					intent.putExtras(bundle);
					intent.putExtra("isFromService", true);

					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					PendingIntent contentIntent = PendingIntent.getActivity(
							mContext, 0, intent, 0);

					String From = msgList.get(0).getMsgTitle();
					String Content = msgList.get(0).getMsgContent();
					mNotification.setLatestEventInfo(mContext, From, Content,
							contentIntent);
					mNotificationManager.notify(AppConfig.NOTIFY_ID,
							mNotification);// 通知一下才会生效哦
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		app = AppApplication.getInstance();
		mMsgDB = app.getMsgDB();
		mNotificationManager = app.getNotificationManager();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		new PollingThread().start();
	}

	/**
	 * Polling thread 向Server轮询的异步线程
	 */
	class PollingThread extends Thread {
		@Override
		public void run() {
			if (isRun) {
				List<Msg> AllMyUnRemindMsgList = mMsgDB
						.getAllUnRemindMsg();
				List<Msg> MyUnRemindMsgList = getNewUnRemindMsgList(AllMyUnRemindMsgList);
				if (MyUnRemindMsgList.size() > 0) {
					Bundle bundle = new Bundle();
					ArrayList bundleList = new ArrayList();
					bundleList.add(MyUnRemindMsgList);
					bundle.putParcelableArrayList("msg", bundleList);
					Message message = mstReceivedHandler.obtainMessage();
					message.what = NewRemindMsg;
					message.getData().putAll(bundle);
					mstReceivedHandler.sendMessage(message);
				}
			}
		}
	}

	public List<Msg> getNewUnRemindMsgList(List<Msg> list) {
		String strRemind = PreferencesUtils.getStringPreference(mContext,
				"set_remindtime", "5");
		int RemindTime = Integer.parseInt(strRemind);
		List<Msg> MsgResult = new ArrayList<Msg>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (Msg msg : list) {
				String curTime = DateUtil.getCurrentTime();
				String setTime = msg.getMsgTime();
				if (DateUtil.timeCompare(setTime, curTime) > 0) {
					Date now = df.parse(curTime);
					Date date = df.parse(msg.getMsgTime());
					long l = date.getTime() - now.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					long hour = (l / (60 * 60 * 1000) - day * 24);
					long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
					if (day == 0 && hour == 0 && min < RemindTime) {
						MsgResult.add(msg);
					}
				}
			}
			return MsgResult;
		} catch (ParseException ex) {
			return MsgResult;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMsgDB != null)
			mMsgDB.close();
		mNotificationManager.cancel(AppConfig.NOTIFY_ID);
	}

}