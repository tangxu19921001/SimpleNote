package com.notedemo.widget;

import com.notedemo.activity.R;
import com.notedemo.recorditem.MediaRecondModel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecordButton extends Button {
	public RecordButton(Context context) {
		super(context);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 录音结束
	 * 
	 * @param listener
	 */
	public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
		finishedListener = listener;
	}

	/**
	 * 点击录音的时候
	 * 
	 * @param listener
	 */
	public void setOnStartTouchRecordListener(
			OnStartTouchRecordListener listener) {
		starttouchrecordListener = listener;
	}

	private OnStartTouchRecordListener starttouchrecordListener;
	private OnFinishedRecordListener finishedListener;

	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	private long startTime;

	private Dialog recordIndicator;

	private static int[] res = { R.drawable.record0, R.drawable.record1,
			R.drawable.record2, R.drawable.record3, R.drawable.record4,
			R.drawable.record5 };

	private static ImageView imageView;

	private TextView txtRecord;

	private LinearLayout mShow, mHide;

	private MediaRecondModel mRecorder;

	private ObtainDecibelThread thread;

	private Handler volumeHandler;

	/**
	 * 判断是否取消录音
	 */
	private boolean IsCancelRecord = false;

	private void init() {
		mRecorder = new MediaRecondModel(getContext());
		volumeHandler = new ShowVolumeHandler();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			initDialogAndStartRecord();
			break;
		case MotionEvent.ACTION_UP:
			if (IsCancelRecord) {
				cancelRecord();
			} else {
				finishRecord();
			}
			break;
		case MotionEvent.ACTION_CANCEL:// 当手指移动到view外面，会cancel
			cancelRecord();
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getX() < this.getLeft() || event.getX() > this.getRight()
					|| event.getY() > this.getBottom()
					|| event.getY() < this.getTop()) {
				mShow.setVisibility(View.GONE);
				mHide.setVisibility(View.VISIBLE);
				txtRecord.setText("松开手指,取消发送");
				txtRecord.setBackgroundColor(Color.RED);
				IsCancelRecord = true;
			} else {
				mShow.setVisibility(View.VISIBLE);
				mHide.setVisibility(View.GONE);
				txtRecord.setText("手指上滑,取消发送");
				txtRecord.setBackgroundColor(Color.TRANSPARENT);
				IsCancelRecord = false;
			}
			break;
		}
		return true;
	}

	private void initDialogAndStartRecord() {
		recordIndicator = new Dialog(getContext(), R.style.dialog);
		startTime = System.currentTimeMillis();
		LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext())
				.inflate(R.layout.record_pop, null);
		imageView = (ImageView) layout.findViewById(R.id.imgrecord);
		txtRecord = (TextView) layout.findViewById(R.id.txtRecord);
		mShow = (LinearLayout) layout.findViewById(R.id.ll_show);
		mHide = (LinearLayout) layout.findViewById(R.id.ll_hide);
		recordIndicator.setContentView(layout);
		recordIndicator.setOnDismissListener(onDismiss);
		startRecording();
		recordIndicator.show();
		WindowManager.LayoutParams layoutParams = recordIndicator.getWindow()
				.getAttributes();
		layoutParams.width = LayoutParams.WRAP_CONTENT;
		layoutParams.height = 300;
		recordIndicator.getWindow().setAttributes(layoutParams);
		this.setEnabled(false);
		if (starttouchrecordListener != null)
			starttouchrecordListener.onStartTouchRecord();
	}

	private void finishRecord() {
		stopRecording();
		this.setEnabled(true);
		recordIndicator.dismiss();
		long intervalTime = System.currentTimeMillis() - startTime;
		if (intervalTime < MIN_INTERVAL_TIME) {
			LinearLayout layout = (LinearLayout) LayoutInflater.from(
					getContext()).inflate(R.layout.record_short, null);
			Toast toast = new Toast(getContext());
			toast.setDuration(1000);
			toast.setView(layout);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			mRecorder.removeRecordFile();
			return;
		}
		if (finishedListener != null)
			finishedListener.onFinishedRecord(mRecorder.getRecordPath());
	}

	private void cancelRecord() {
		this.setEnabled(true);
		stopRecording();
		recordIndicator.dismiss();
		Toast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT).show();
		mRecorder.removeRecordFile();
	}

	private void startRecording() {
		mRecorder.startRecord();
		thread = new ObtainDecibelThread();
		thread.start();

	}

	private void stopRecording() {
		if (thread != null) {
			thread.exit();
			thread = null;
		}
		mRecorder.stopRecord();
	}

	private class ObtainDecibelThread extends Thread {
		private volatile boolean running = true;

		public void exit() {
			running = false;
		}

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				MediaRecorder recorder = mRecorder.getRecordInstance();
				if (recorder == null || !running) {
					break;
				}
				int x = recorder.getMaxAmplitude();
				if (x != 0) {
					int f = (int) (10 * Math.log(x) / Math.log(10));
					if (f < 26)
						volumeHandler.sendEmptyMessage(0);
					else if (f < 32)
						volumeHandler.sendEmptyMessage(1);
					else if (f < 38)
						volumeHandler.sendEmptyMessage(2);
					else
						volumeHandler.sendEmptyMessage(3);

				}
			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener() {

		@Override
		public void onDismiss(DialogInterface dialog) {
			stopRecording();
		}
	};

	static class ShowVolumeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			imageView.setImageResource(res[msg.what]);
		}
	}

	/**
	 * 录音完成
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnFinishedRecordListener {
		public void onFinishedRecord(String audioPath);
	}

	/**
	 * 点击录音时候
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnStartTouchRecordListener {
		public void onStartTouchRecord();
	}
}
