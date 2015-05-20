package com.notedemo.activity;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_main);
		Button myButton=(Button)findViewById(R.id.bt1);
		myButton.setText("查看私密记事");
		TextView textview=(TextView)findViewById(R.id.t1);
		
		
		myButton.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				/*Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com.hk"));
				startActivity(intent);*/
				Intent intent=new Intent(TestActivity.this,FirstGroupTab.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent=new Intent(TestActivity.this,FirstGroupTab.class);
		startActivity(intent);
	}
	
}
