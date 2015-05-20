package com.notedemo.activity;

import com.notedemo.utils.DialogFactory;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

public class DetailBaseActivity extends Activity {

	private ProgressDialog m_pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 点击登录按钮后，弹出验证对话�?
	 */
	public Dialog mDialog = null;

	public void showRequestDialog(String msg) {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, msg);
		mDialog.show();
	}

	public void closeRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void openProgressDialog(String msg) {
		if (m_pDialog == null) {
			m_pDialog = new ProgressDialog(this);
			
			m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			m_pDialog.setMessage(msg);
		}
		m_pDialog.show();
	}

	public void closeProgressDialog() {
		if (m_pDialog != null) {
			m_pDialog.dismiss();
			m_pDialog = null;
		}

	}

	public void alert(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg, 1000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
