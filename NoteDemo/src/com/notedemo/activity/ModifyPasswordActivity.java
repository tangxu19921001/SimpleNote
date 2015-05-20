package com.notedemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyPasswordActivity extends Activity
{
	private TextView tv1_modify;
	private TextView tv2_modify;
	private EditText et1_modify;
	private EditText et2_modify;
	private Button btn_modify;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	public static final String PREFERENCE_NAME="SaveSetting";
	public static final int MODE=MODE_PRIVATE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifypassword);
		initView();
	}
	public void initView()
	{
		tv1_modify=(TextView)findViewById(R.id.tv1_modify);
		tv2_modify=(TextView)findViewById(R.id.tv2_modify);
		et1_modify=(EditText)findViewById(R.id.et1_modify);
		et2_modify=(EditText)findViewById(R.id.et2_modify);
		btn_modify=(Button)findViewById(R.id.btn_modify);
		
		tv1_modify.setText("«Î ‰»Î‘≠√‹¬Î");
		tv2_modify.setText("«Î ‰»Î–¬√‹¬Î");
		sharedPreferences=getSharedPreferences(PREFERENCE_NAME,MODE);
		editor=sharedPreferences.edit();
		btn_modify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwd1=et1_modify.getText().toString();
				String pwd2=et2_modify.getText().toString();
				String pwd0=sharedPreferences.getString("password", "test");
				if(pwd0.equals(pwd1))
				{
					
					editor.putString("password", pwd2);
					editor.commit();
					Toast.makeText(ModifyPasswordActivity.this,"√‹¬Î–ﬁ∏ƒ≥…π¶",Toast.LENGTH_SHORT).show();
					/*Intent in=new Intent(ModifyPasswordActivity.this,MainActivity.class);
					startActivity(in);*/
					finish();
				}
				else
				{
					Toast.makeText(ModifyPasswordActivity.this,"‘≠√‹¬Î¥ÌŒÛ",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
}
