package com.notedemo.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.notedemo.adapter.AttachAdapter;
import com.notedemo.adapter.FaceAdapter;
import com.notedemo.adapter.FacePageAdeapter;
import com.notedemo.config.AppConfig;
import com.notedemo.db.MsgAttachDB;
import com.notedemo.db.MsgDB;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.notedemo.model.BaseAppData;
import com.notedemo.model.Msg;
import com.notedemo.model.MsgAttach;
import com.notedemo.utils.CirclePageIndicator;
import com.notedemo.utils.DateUtil;
import com.notedemo.utils.ExpressionUtil;
import com.notedemo.utils.JazzyViewPager;
import com.notedemo.utils.PreferencesUtils;
import com.notedemo.utils.StreamItem;
import com.notedemo.utils.JazzyViewPager.TransitionEffect;
import com.notedemo.widget.RecordButton;
import com.notedemo.widget.RecordButton.OnFinishedRecordListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.BufferType;

public class NewItemActivity extends DetailBaseActivity {
	/**
	 * 控件
	 */
	private TextView mTitle, mMsgTime;
	private TextView mediaFace, mediaRecord, mediaImg, mediaCamera;
	private EditText mMsgTitle, mMsgContent;
	private ImageView mSelectTime;
	private Button mBtnBack, mBtnSave, mBtnRecordBack;
	private RecordButton mBtnRecord;
	private GridView mgvAttachlist;
	private RelativeLayout relAttach;
	private LinearLayout mLMedia, mLRecord, mLFace;
	private JazzyViewPager faceViewPager;
	private RelativeLayout rel_time;
//	private RadioGroup radiogroup;
	/**
	 * 数据
	 */
	private Msg mMsg;
	private int mMsgType;
	private List<MsgAttach> mAttachlist = new ArrayList<MsgAttach>();

	private InputMethodManager imm;
	private List<String> keys;
	/** 表情 **/
	private TransitionEffect mEffects[] = { TransitionEffect.Standard,
			TransitionEffect.Tablet, TransitionEffect.CubeIn,
			TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
			TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
			TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
			TransitionEffect.RotateUp, TransitionEffect.RotateDown,
			TransitionEffect.Accordion };
	private int currentPage = 0;

	private StreamItem myStreamItem;

	private MsgDB mMsgDB;
	private MsgAttachDB mMsgAttachDB;
	private boolean IsEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_activity);
		AppApplication.getInstance().addActivity(this);

		mMsgDB = AppApplication.getInstance().getMsgDB();
		mMsgAttachDB = AppApplication.getInstance().getMsgAttachDB();

		boolean isFromService = getIntent().getBooleanExtra(
				"isFromService", false);
		Bundle bundle = getIntent().getExtras();
		mMsg = null;
		if (bundle != null) {
			mMsg = (Msg) bundle.getSerializable("msg");
			mMsgType = bundle.getInt("MsgType", AppConfig.MsgType.JISHI);
			if (isFromService) {
				if (!mMsgDB.isExistMsg(mMsg.getMsgId())) {
					finish();
				}
				mMsgDB.updateMsgHasRemind(mMsg.getMsgId());
			}
		}
		InitialView();
		if (mMsg != null) {
			IsEdit = true;
			mMsgType = mMsg.getMsgType();
			SetTitle(mMsgType);
			mMsgTime.setText(mMsg.getMsgTime());
			mMsgTitle.setText(mMsg.getMsgTitle());
			mMsgContent.setText(ExpressionUtil
					.convertNormalStringToSpannableString(NewItemActivity.this,
							mMsg.getMsgContent()), BufferType.SPANNABLE);
			mAttachlist = mMsgAttachDB.getMsgAttach(mMsg.getMsgId());
			InitialAttachAdapter();
		} else {
			IsEdit = false;
			mMsgTime.setText(DateUtil.getCurrentDateTime());
			SetTitle(mMsgType);
		}
		if(isFromService)
		{
			mBtnSave.setVisibility(View.GONE);
		}
		else
		{
			mBtnSave.setVisibility(View.VISIBLE);
		}
		initFacePage();
	}

	private void SetTitle(int MsgType) {
		switch (MsgType) {
		case AppConfig.MsgType.JISHI:
			mLMedia.setVisibility(View.VISIBLE);
			mTitle.setText("记事");
			break;
		case AppConfig.MsgType.TIXING:
			mLMedia.setVisibility(View.GONE);
			mTitle.setText("提醒");
			break;
		case AppConfig.MsgType.SIMI:
			mLMedia.setVisibility(View.GONE);
			mTitle.setText("私密");
			break;
		}
	}

	private void InitialView() {
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mTitle = (TextView) findViewById(R.id.txt_title);
		mMsgTime = (TextView) findViewById(R.id.txt_msg_time);

		mMsgTitle = (EditText) findViewById(R.id.txt_msg_title);
		mMsgContent = (EditText) findViewById(R.id.txt_msg_content);

		mediaFace = (TextView) findViewById(R.id.txt_media_face);
		mediaRecord = (TextView) findViewById(R.id.txt_media_record);
		mediaImg = (TextView) findViewById(R.id.txt_media_img);
		mediaCamera = (TextView) findViewById(R.id.txt_media_camera);

		mSelectTime = (ImageView) findViewById(R.id.img_select_time);

		mBtnRecordBack = (Button) findViewById(R.id.btn_recorder_back);
		mBtnSave = (Button) findViewById(R.id.btn_save);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnRecord = (RecordButton) findViewById(R.id.btn_record);
		mBtnRecord.setOnFinishedRecordListener(new OnFinishedRecordListener() {
			@Override
			public void onFinishedRecord(String audioPath) {
				addRecordContent(audioPath);
			}
		});

		mgvAttachlist = (GridView) findViewById(R.id.gv_attachlist);

		relAttach = (RelativeLayout) findViewById(R.id.rel_attach);
		rel_time = (RelativeLayout) findViewById(R.id.rel_time);
		mLMedia = (LinearLayout) findViewById(R.id.ll_media_type);
		mLRecord = (LinearLayout) findViewById(R.id.ll_recorder);
		mLFace = (LinearLayout) findViewById(R.id.ll_face);

		faceViewPager = (JazzyViewPager) findViewById(R.id.face_pager);

		mBtnBack.setOnClickListener(listener);
		mBtnSave.setOnClickListener(listener);
		mSelectTime.setOnClickListener(listener);
		mediaFace.setOnClickListener(listener);
		mediaRecord.setOnClickListener(listener);
		mediaImg.setOnClickListener(listener);
		mediaCamera.setOnClickListener(listener);
		mBtnRecordBack.setOnClickListener(listener);
		rel_time.setOnClickListener(listener);

		Set<String> keySet = AppApplication.getInstance().getFaceMap().keySet();
		keys = new ArrayList<String>();
		keys.addAll(keySet);
	}

	/**
	 * 编辑时获取新增加的附件
	 * 
	 * @return
	 */
	private List<MsgAttach> getNewAddMsgAttachWhenEdit() {
		List<MsgAttach> result = new ArrayList<MsgAttach>();
		if (IsEdit && mAttachlist.size() > 0) {
			for (MsgAttach attach : mAttachlist) {
				if (attach.getAttachId() == 0) {
					result.add(attach);
				}
			}
		}
		return result;
	}

	private OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				// 返回
				setResult(RESULT_CANCELED);
				finish();
				break;
			case R.id.btn_save:
				// 保存
				if (mMsgTitle.getText().toString().equals("")) {
					alert("请输入标题");
					return;
				}
				if (mMsgTime.getText().toString().equals("")) {
					alert("请选择时间");
					return;
				}
				if (mMsgType == AppConfig.MsgType.TIXING) {
					String curTime = DateUtil.getCurrentTime();
					String setTime = mMsgTime.getText().toString();
					if (DateUtil.timeCompare(curTime, setTime) > 0) {
						alert("时间必须大于当前时间");
						return;
					}
				}
				if (!IsEdit) {
					Msg msg = new Msg();
					msg.setMsgTitle(mMsgTitle.getText().toString());
					msg.setMsgContent(mMsgContent.getText().toString());
					msg.setMsgTime(mMsgTime.getText().toString());
					msg.setMsgType(mMsgType);
					int msgId = mMsgDB.saveMsg(msg);
					if (mAttachlist.size() > 0) {
						for (MsgAttach attach : mAttachlist) {
							attach.setMsgId(msgId);
							mMsgAttachDB.saveMsgAttach(attach);
						}
					}
				} else {
					mMsg.setMsgTitle(mMsgTitle.getText().toString());
					mMsg.setMsgContent(mMsgContent.getText().toString());
					mMsg.setMsgTime(mMsgTime.getText().toString());
					mMsgDB.updateMsg(mMsg);
					List<MsgAttach> newAddMsgAttach = getNewAddMsgAttachWhenEdit();
					if (newAddMsgAttach.size() > 0) {
						for (MsgAttach attach : newAddMsgAttach) {
							attach.setMsgId(mMsg.getMsgId());
							mMsgAttachDB.saveMsgAttach(attach);
						}
					}
				}
				setResult(RESULT_OK);
				Intent in=new Intent(NewItemActivity.this,MainActivity.class);
				startActivity(in);
				finish();
				break;
			case R.id.img_select_time:
			case R.id.rel_time:
				// 选择时间
				getMsgTime();
				break;
			case R.id.txt_media_face:
				imm.hideSoftInputFromWindow(mMsgContent.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mLFace.setVisibility(View.VISIBLE);
				break;
			case R.id.txt_media_img:
				// 相册
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 7);
				break;
			case R.id.txt_media_camera:
				// 相机
				Intent intentCamere = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intentCamere, 0);
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
				break;
			case R.id.txt_media_record:
				// 录音
				mLMedia.setVisibility(View.GONE);
				mLRecord.setVisibility(View.VISIBLE);
				break;
			case R.id.btn_recorder_back:
				// 录音返回
				mLMedia.setVisibility(View.VISIBLE);
				mLRecord.setVisibility(View.GONE);
				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// String resultKey = data.getExtras().getString("key");
		switch (requestCode) {
		case 0:
			// 拍照
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				myStreamItem = new StreamItem();
				myStreamItem.bitmapToFIle(bitmap);
				addPicContent(myStreamItem.getPicturePath());
				Log.d("picturepath", myStreamItem.getPicturePath());
			}
			break;
		case 7:
			// 相册
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null,
						null, null);
				cursor.moveToFirst();
				addPicContent(cursor.getString(1));
				cursor.close();
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 录音
	 * 
	 * @param content
	 */
	public void addRecordContent(String content) {// path
		if (!content.equals("")) {
			MsgAttach model = new MsgAttach();
			model.setAttachType(AppConfig.AttachType.VEDIO);
			model.setAttachContent(content);
			this.mAttachlist.add(model);
			InitialAttachAdapter();
		}
	}

	/**
	 * 图片
	 * 
	 * @param content
	 */
	public void addPicContent(String content) {// path
		if (!content.equals("")) {
			MsgAttach model = new MsgAttach();
			model.setAttachType(AppConfig.AttachType.PICTURE);
			model.setAttachContent(content);
			this.mAttachlist.add(model);
			InitialAttachAdapter();
		}
	}

	public void InitialAttachAdapter() {
		if (this.mAttachlist.size() > 0) {
			relAttach.setVisibility(View.VISIBLE);
		}
		AttachAdapter adapter = new AttachAdapter(NewItemActivity.this,
				this.mAttachlist);
		mgvAttachlist.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void initFacePage() {
		List<View> lv = new ArrayList<View>();
		for (int i = 0; i < 6; ++i)
			lv.add(getGridView(i));
		FacePageAdeapter adapter = new FacePageAdeapter(lv, faceViewPager);
		faceViewPager.setAdapter(adapter);
		faceViewPager.setCurrentItem(currentPage);
		faceViewPager.setTransitionEffect(mEffects[PreferencesUtils
				.getFaceEffect(NewItemActivity.this)]);
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(faceViewPager);
		adapter.notifyDataSetChanged();
		mLFace.setVisibility(View.GONE);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentPage = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});
	}

	private OnTouchListener forbidenScroll() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		};
	}

	private GridView getGridView(int i) {
		// TODO Auto-generated method stub
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(this, i));
		gv.setOnTouchListener(forbidenScroll());
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 20) {// 删除键的位置
					int selection = mMsgContent.getSelectionStart();
					String text = mMsgContent.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							mMsgContent.getText().delete(start, end);
							return;
						}
						mMsgContent.getText().delete(selection - 1, selection);
					}
				} else {
					int count = currentPage * 20 + arg2;
					// 下面这部分，在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) AppApplication
									.getInstance().getFaceMap().values()
									.toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 计算缩放因子
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
								rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(NewItemActivity.this,
								newBitmap);
						String emojiStr = keys.get(count);
						SpannableString spannableString = new SpannableString(
								emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						mMsgContent.append(spannableString);
					} else {
						String ori = mMsgContent.getText().toString();
						int index = mMsgContent.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, keys.get(count));
						mMsgContent.setText(stringBuilder.toString());
						mMsgContent.setSelection(index
								+ keys.get(count).length());
					}
				}
			}
		});
		return gv;
	}

	private void getMsgTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.datatime_dialog, null);
		final DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) view
				.findViewById(R.id.time_picker);
		builder.setView(view);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
		builder.setTitle("选择时间");
		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append("  ");
						sb.append(timePicker.getCurrentHour()).append(":")
								.append(timePicker.getCurrentMinute());
						mMsgTime.setText(sb.toString());
						dialog.cancel();
					}
				});
		Dialog dialog = builder.create();
		dialog.show();
	}
	

}
