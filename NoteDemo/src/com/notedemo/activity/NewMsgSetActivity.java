package com.notedemo.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.notedemo.activity.AppApplication;

import com.notedemo.activity.R;
import com.notedemo.utils.PreferencesUtils;

public class NewMsgSetActivity extends Activity {

	private Button btnBack, btnSave;
	private ToggleButton img_sound, img_shake;
	private EditText txt_remind_time;
	private String set_sound = "1";
	private String set_shake = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_newmsg);
		AppApplication.getInstance().addActivity(this);
		initView();
	}

	private void initView() {

		img_sound = (ToggleButton) findViewById(R.id.img_sound);
		img_shake = (ToggleButton) findViewById(R.id.img_shake);

		txt_remind_time = (EditText) findViewById(R.id.txt_remind_time);

		txt_remind_time.setText(PreferencesUtils.getStringPreference(
				NewMsgSetActivity.this, "set_remindtime", "5"));

		btnBack = (Button) findViewById(R.id.btn_back);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(listener);
		btnBack.setOnClickListener(listener);

		set_sound = PreferencesUtils.getStringPreference(
				NewMsgSetActivity.this, "set_sound", "1");
		set_shake = PreferencesUtils.getStringPreference(
				NewMsgSetActivity.this, "set_shake", "0");

		if (set_sound.equals("1")) {

			img_sound.setChecked(true);
		} else {
			img_sound.setChecked(false);

		}

		if (set_shake.equals("1")) {
			img_shake.setChecked(true);
		} else {
			img_shake.setChecked(false);
		}

		img_sound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					PreferencesUtils.setStringPreferences(
							NewMsgSetActivity.this, "set_sound", "1");
				} else {
					PreferencesUtils.setStringPreferences(
							NewMsgSetActivity.this, "set_sound", "0");
				}
			}
		});

		img_shake.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					PreferencesUtils.setStringPreferences(
							NewMsgSetActivity.this, "set_shake", "1");
				} else {
					PreferencesUtils.setStringPreferences(
							NewMsgSetActivity.this, "set_shake", "0");
				}
			}
		});

	}
	public void alert(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg, 1000);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				finish();
				break;
			case R.id.btn_save:
				String strRemindTime = txt_remind_time.getText().toString();
				int remindTime = 5;
				if (strRemindTime.equals("")) {
					alert("璇疯緭鍏ユ彁鍓嶆彁閱掓椂闂�");
					return;
				}
				try {
					remindTime = Integer.parseInt(strRemindTime);
				} catch (NumberFormatException e) {
					alert("璇疯緭鍏ユ纭殑鎻愬墠鎻愰啋鏃堕棿");
					return;
				}
				PreferencesUtils.setStringPreferences(NewMsgSetActivity.this,
						"set_remindtime", remindTime + "");
				finish();
				break;
			}
		}
	};

}