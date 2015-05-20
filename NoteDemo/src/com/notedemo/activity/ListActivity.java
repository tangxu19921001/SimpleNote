package com.notedemo.activity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


import com.notedemo.activity.ListActivity;
import com.notedemo.utils.MatchSearch;
import com.notedemo.activity.AppApplication;
import com.notedemo.adapter.MsgAdapter;
import com.notedemo.activity.R;
import com.notedemo.widget.ClearEditText;
import com.notedemo.db.MsgAttachDB;
import com.notedemo.db.MsgDB;
import com.notedemo.model.BaseAppData;
import com.notedemo.model.Msg;
import com.notedemo.config.AppConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListActivity extends Activity
{
	private int mMsgType;
	private List<Msg> mMsgList;
	private MsgDB mMsgDB;
	private MsgAttachDB mMsgAttachDB;
	private ListView mMsglistView;
	private MsgAdapter mAdapter;
	private ImageButton mNewMsgBtn;
	private TextView txtTitle;
	private LinearLayout lpassword;
	private LinearLayout lsearch;
	private EditText mSearchEditText;
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
		setContentView(R.layout.msg_list_activity);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mMsgType = bundle.getInt("MsgType", AppConfig.MsgType.JISHI);
			
		}
		//隐藏输入框
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initView();
		if(mMsgType != AppConfig.MsgType.SIMI){
			initMsgList();
		}
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initMsgList();	
	}
	
	public void initView()
	{
		lpassword =(LinearLayout) findViewById(R.id.top_password);
		lsearch = (LinearLayout) findViewById(R.id.search_top_layout);
		mSearchEditText = (EditText) findViewById(R.id.search);
		mSearchEditText = (ClearEditText) findViewById(R.id.search);
		mNewMsgBtn = (ImageButton) findViewById(R.id.info_btn);
		txtTitle = (TextView) findViewById(R.id.info_title);
		switch (mMsgType) {
		case AppConfig.MsgType.JISHI:
			lpassword.setVisibility(View.GONE);
			lsearch.setVisibility(View.VISIBLE);
			txtTitle.setText("记事");
			break;
		case AppConfig.MsgType.TIXING:
			lpassword.setVisibility(View.GONE);
			lsearch.setVisibility(View.GONE);
			txtTitle.setText("提醒");
			break;
		case AppConfig.MsgType.SIMI:
			lpassword.setVisibility(View.VISIBLE);
			lsearch.setVisibility(View.GONE);
			txtTitle.setText("私密");
			initPassword();
			break;
		}
		mMsgDB = AppApplication.getInstance().getMsgDB();
		mMsgAttachDB = AppApplication.getInstance().getMsgAttachDB();
		mMsglistView = (ListView) findViewById(R.id.lv_msg);
		TextView textView = (TextView) findViewById(R.id.text_notuse);
		textView.requestFocus();
		mNewMsgBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListActivity.this,NewItemActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("MsgType",mMsgType);
				intent.putExtras(bundle);
				getParent().startActivityForResult(intent,0);
				Log.i("ss","ss");
			}
		});
		mMsglistView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Msg entity = mMsgList.get(arg2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msg", (Serializable) entity);
				Intent intent = new Intent(ListActivity.this,
						NewItemActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			//	ListActivity.this.finish();  
			}

		});
		mMsglistView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final Msg item = mMsgList.get(arg2);
				new AlertDialog.Builder(ListActivity.this.getParent()).setTitle("提示")
						.setIcon(android.R.drawable.ic_delete)
						.setItems(R.array.arrcontent, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String[] PK = getResources().getStringArray(
										R.array.arrcontent);
								if (PK[which].equals("删除")) {
									mMsgList.remove(item);
									mMsgDB.delMsg(item.getMsgId());
									mMsgAttachDB.delMsgAttach(item.getMsgId());
									initMsgList();
								}
								else if(PK[which].equals("返回")){
									initMsgList();
								}
							}
						}).show();
				return false;
			}

		});
		// 根据输入框输入值的改变来过滤搜索
				mSearchEditText.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}

					@Override
					public void afterTextChanged(Editable s) {
						filterData(mSearchEditText.getText().toString());
					}
				});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				initMsgList();
				
			}
		}
	}
	public void handleActivityResult(int requestCode, int resultCode, Intent data){  
		if(resultCode == RESULT_OK){//获取返回码，刷新界面  
		mMsgType=data.getExtras().getInt("type");
		initMsgList();
		}  
	} 
	
	public void initPassword(){
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
//					Intent intent=new Intent(PasswordActivity.this,ListActivity.class);
//					Bundle  bundle = new Bundle();
//					bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
//					intent.putExtras(bundle);
//					startActivity(intent);
//					BaseAppData.getInstance().setLoginStatus(1);
					lpassword.setVisibility(View.GONE);
					initMsgList();
					
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
						/*Intent intent=new Intent(PasswordActivity.this,ListActivity.class);
						Bundle  bundle = new Bundle();
						bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
						intent.putExtras(bundle);
						startActivity(intent);
						BaseAppData.getInstance().setLoginStatus(1);
						finish();*/
						lpassword.setVisibility(View.GONE);
						initMsgList();
					}
					else
					{
						Toast.makeText(ListActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					}
							
				}
				
			}
		});
	}
	
	public void initMsgList(){
		mMsgList=mMsgDB.getMsg(mMsgType);
		if(mMsgList.size()>0){
			mAdapter = new MsgAdapter(this, mMsgList);
			mMsglistView.setAdapter(mAdapter);
			mMsglistView.setVisibility(View.VISIBLE);
		} else {
			mMsglistView.setVisibility(View.GONE);
		}
	}
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<Msg> filterDateList = new LinkedList<Msg>();
		if (filterStr == null || filterStr.equals("")) {
			filterDateList = mMsgList;
		} else {
			for (Msg item : mMsgList) {
				String name = item.getMsgTitle();
				if (name.contains(filterStr)
						|| MatchSearch.containsUpLetters(name, filterStr)
						|| MatchSearch.containsPinyin(name, filterStr)) {
					filterDateList.add(item);
				}
			}
		}
		if (filterDateList != null && filterDateList.size() > 0) {
			mAdapter = new MsgAdapter(this, filterDateList);
			mMsglistView.setAdapter(mAdapter);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(mMsgType==AppConfig.MsgType.SIMI){
				Intent in=new Intent(this,MainActivity.class);
				startActivity(in);
				finish();
			}
			else{
				new AlertDialog.Builder(this.getParent())
					.setTitle("提示")
					.setMessage("亲，您要退出吗?")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									AppApplication.getInstance().exitActivity();
									finish();
								}
							}).setNegativeButton("取消", null).show();
			}
			

		}
		return super.onKeyDown(keyCode, event);
	}
	
}
