package com.notedemo.activity;

import java.lang.ref.WeakReference;

import com.notedemo.activity.AppApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class WelcomeActivity extends Activity {
	private static final int WELCOME_MSG = 0;
	private Handler handler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
	}

	@Override
	protected void onResume() {
		super.onResume();
		handler = new WelcomeHandler(WelcomeActivity.this);
		handler.postDelayed(new WelcomeRunnable(), 3000);
	}

	
	private static class WelcomeHandler extends Handler {
		private WeakReference<WelcomeActivity> thisActivity = null;
		public WelcomeHandler(WelcomeActivity welcomeActivity) {
			thisActivity = new WeakReference<WelcomeActivity>(welcomeActivity);
		}
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == WELCOME_MSG) {
				Intent intent = new Intent();
				intent.setClass(thisActivity.get(), MainActivity.class);
				thisActivity.get().startActivity(intent);
				thisActivity.get().finish();
			}
		}
	}

	
	class WelcomeRunnable implements Runnable {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = WELCOME_MSG;
			handler.sendMessage(msg);
		}
	}
}
