package com.notedemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.notedemo.activity.ModifyPasswordActivity;
import com.notedemo.activity.NewMsgSetActivity;
import com.notedemo.adapter.DealAdapter;
import com.notedemo.config.AppConfig;
import com.notedemo.image.SmartImageView;
import com.notedemo.image.WebImage;
import com.notedemo.model.BaseAppData;
import com.notedemo.service.PollingService;
import com.notedemo.service.PollingUtils;
import com.notedemo.utils.PreferencesUtils;


public class SettingActivity extends Activity {

	private RelativeLayout set_myaccount, set_newmsg, set_about, set_feedback,
			set_version;
	private Button btnExitLogin;
	private TextView mUserName;
	private DealAdapter myDealAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		AppApplication.getInstance().addActivity(this);
		myDealAdapter = new DealAdapter(SettingActivity.this);
		initView();
	}

	private void initView() {
		
		set_newmsg = (RelativeLayout) findViewById(R.id.set_newmsg);
		set_about = (RelativeLayout) findViewById(R.id.set_about);
		//set_feedback = (RelativeLayout) findViewById(R.id.set_feedback);
		//set_version = (RelativeLayout) findViewById(R.id.set_version);
		//btnExitLogin = (Button) findViewById(R.id.btnExitLogin);
		
		// if (BaseAppData.getInstance().getUserInfo().getUserFace() != null) {
		// mUserface.setImageBitmap(myDealAdapter.getPictureBmp(
		// AppConfig.AttachType.PICTURE, BaseAppData.getInstance()
		// .getUserInfo().getUserFace()));
		// }
		// else
		// {
		// mUserface.setImageUrl(BaseAppData.getInstance()
		// .getUserInfo().getUserFace(), R.drawable.mini_avatar,
		// R.drawable.mini_avatar);
		// }
		
		// set_myaccount.setOnClickListener(listener);
		
		set_newmsg.setOnClickListener(listener);
		set_about.setOnClickListener(listener);
		
	}

	private View.OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.set_newmsg: // 鏂版秷鎭缃�
				Intent newmsg = new Intent(SettingActivity.this,
						NewMsgSetActivity.class);
				startActivity(newmsg);
				break;
			case R.id.set_about:
				Intent password = new Intent(SettingActivity.this,
						ModifyPasswordActivity.class);
				startActivity(password);
				break;
		
			}
		}
	};

}
