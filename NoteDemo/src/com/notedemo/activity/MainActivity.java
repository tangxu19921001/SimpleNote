package com.notedemo.activity;

import java.util.ArrayList;
import java.util.List;

import com.notedemo.config.AppConfig;
import com.notedemo.model.BaseAppData;
import com.notedemo.service.PollingService;
import com.notedemo.service.PollingUtils;


import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;                                  
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import android.widget.TextView;

public class MainActivity extends Activity {

	Context context = null;
	LocalActivityManager manager = null;
	ViewPager pager = null;
	
	TextView t1,t2,t3,t4;
	
	private int offset = 0;// 锟斤拷锟斤拷图片偏锟斤拷锟斤拷
	private int currIndex = 0;// 锟斤拷前页锟斤拷锟斤拷锟�
	private int bmpW;// 锟斤拷锟斤拷图片锟斤拷锟�
	private ImageView iv;// 锟斤拷锟斤拷图片

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppApplication.getInstance().exitActivity();
		AppApplication.getInstance().addActivity(this);
		BaseAppData.getInstance().setLoginStatus(0);
		context = MainActivity.this;
		
		
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);

		InitImageView();
		initTextView();
		initPagerViewer();
		// 寮�惎瀹氭椂鏈嶅姟
				PollingUtils.startPollingService(MainActivity.this, 10,
						PollingService.class, PollingService.ACTION);
	}
	/**
	 * 锟斤拷始锟斤拷锟斤拷锟斤拷
	 */
	
	@SuppressWarnings("deprecation")
	
	
	private void initTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		t4 = (TextView) findViewById(R.id.text4);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	/**
	 * 锟斤拷始锟斤拷PageViewer
	 */
	private void initPagerViewer() {
		pager = (ViewPager) findViewById(R.id.viewpage);
		final ArrayList<View> list = new ArrayList<View>();
		
		Intent intent = new Intent(context, ListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("MsgType", AppConfig.MsgType.JISHI);
		intent.putExtras(bundle);

		list.add(getView("A", intent));
		
		Intent intent2 = new Intent(context, ListActivity.class);
		bundle = new Bundle();
		bundle.putInt("MsgType", AppConfig.MsgType.TIXING);
		intent2.putExtras(bundle);

		list.add(getView("B", intent2));
		
		Intent intent3 = new Intent(context,ListActivity.class);
		bundle = new Bundle();
		bundle.putInt("MsgType", AppConfig.MsgType.SIMI);
		intent3.putExtras(bundle);

		list.add(getView("C", intent3));
		
		Intent intent4 = new Intent(context, SettingActivity.class);
		list.add(getView("D", intent4));

		pager.setAdapter(new MyPagerAdapter(list));
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
		pager.getAdapter().notifyDataSetChanged();
	}

	/**
	 * 锟斤拷始锟斤拷锟斤拷锟斤拷
	 */
	private void InitImageView() {
		iv = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.roller)
		.getWidth();// 锟斤拷取图片锟斤拷锟�
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 锟斤拷取锟街憋拷锟绞匡拷锟�
		offset = (screenW / 4 - bmpW) / 2;// 锟斤拷锟斤拷偏锟斤拷锟斤拷
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		iv.setImageMatrix(matrix);// 锟斤拷锟矫讹拷锟斤拷锟斤拷始位锟斤拷
	}

	
	/**
	 * 通锟斤拷activity锟斤拷取锟斤拷图
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();             //adpter鐩存帴鏈変簡
	}

	/**
	 * Pager锟斤拷锟斤拷锟斤拷
	 */
	public class MyPagerAdapter extends PagerAdapter{
		List<View> list =  new ArrayList<View>();
		public MyPagerAdapter(ArrayList<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(ViewGroup container, int position,
				Object object) {
			ViewPager pViewPager = ((ViewPager) container);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			ViewPager pViewPager = ((ViewPager) arg0);
			pViewPager.addView(list.get(arg1));
			return list.get(arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}
	/**
	 * 页锟斤拷锟叫伙拷锟斤拷锟斤拷
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页锟斤拷1 -> 页锟斤拷2 偏锟斤拷锟斤拷
		int two = one * 2;// 页锟斤拷1 -> 页锟斤拷


		@Override
		public void onPageSelected(int arg0) {
			try{Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);  //闅忓綋鍓嶉〉鎸囬拡鍙婂浘琛ㄤ綅缃Щ鍔ㄦ粴鍔ㄦ潯鍜岄〉闈�
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停锟节讹拷锟斤拷锟斤拷锟斤拷位锟斤拷
		    animation.setDuration(300);
			iv.startAnimation(animation);
		
			}
			catch(SQLiteException e){}
			finally{
				//
		
iv.clearFocus();}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}
	/**
	 * 头锟斤拷锟斤拷锟斤拷锟斤拷
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			pager.setCurrentItem(index);
		}
	};
	
}


