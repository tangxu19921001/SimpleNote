package com.notedemo.activity;

import com.notedemo.config.AppConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.notedemo.model.BaseAppData;

public class PasswordActivity extends Activity
{
	private TextView tv_password;
	private EditText et_password;
	private Button obtn_password;
	private int isVerify;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	public static final String PREFERENCE_NAME="SaveSetting";
	public static final int MODE=MODE_PRIVATE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
			initView();		
			Log.i("psdAc","onCreate()");
	}
	

	public void initView()
	{
		tv_password=(TextView)findViewById(R.id.tv_password);
		et_password=(EditText)findViewById(R.id.et_password);
		obtn_password=(Button)findViewById(R.id.obtn_password);
		
		sharedPreferences=getSharedPreferences(PREFERENCE_NAME,MODE);
		editor=sharedPreferences.edit();
		String password=sharedPreferences.getString("password", "test");

		if("test".equals(password))
		{
			tv_password.setText("请设置您的密码");
			isVerify=0;
		}
		else
		{
			tv_password.setText("请输入您的密码");
			isVerify=1;
		}

		
		obtn_password.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(0==isVerify)
				{
					editor.putString("password", et_password.getText().toString());
					editor.commit();
					/*Intent intent=new Intent(PasswordActivity.this,ListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Bundle  bundle = new Bundle();
					bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
					intent.putExtras(bundle);
					Window w = FirstGroupTab.group.getLocalActivityManager()
                            .startActivity("ListActivity",intent);
					View view = w.getDecorView();
					//把View添加大ActivityGroup中
					FirstGroupTab.group.setContentView(view);*/
					Intent intent=new Intent(PasswordActivity.this,ListActivity.class);
					Bundle  bundle = new Bundle();
					bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
					intent.putExtras(bundle);
					startActivity(intent);
					BaseAppData.getInstance().setLoginStatus(1);
				}
				if(1==isVerify)
				{
					String pw=sharedPreferences.getString("password", "test");
					if(pw.equals(et_password.getText().toString()))
					{
						/*Intent intent=new Intent(PasswordActivity.this,ListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle  bundle = new Bundle();
						bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
						intent.putExtras(bundle);
						Window w = FirstGroupTab.group.getLocalActivityManager()
	                            .startActivity("ListActivity",intent);
						View view = w.getDecorView();
						//把View添加大ActivityGroup中
						FirstGroupTab.group.setContentView(view);*/
						Intent intent=new Intent(PasswordActivity.this,ListActivity.class);
						Bundle  bundle = new Bundle();
						bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
						intent.putExtras(bundle);
						startActivity(intent);
						BaseAppData.getInstance().setLoginStatus(1);
						finish();
					}
					else
					{
						Toast.makeText(PasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					}
							
				}
				
			}
		});
		
	}
	
}
