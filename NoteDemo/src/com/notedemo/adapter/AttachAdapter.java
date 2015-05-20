package com.notedemo.adapter;

import java.io.File;
import java.util.List;
import com.notedemo.activity.ImageBigActivity;
import com.notedemo.activity.R;
import com.notedemo.config.AppConfig;
import com.notedemo.image.SmartImageView;
import com.notedemo.model.MsgAttach;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Toast;

//附件列表
public class AttachAdapter extends BaseAdapter {

	private Context mContext;
	private List<MsgAttach> lstAttach;
	private DealAdapter myDealAdapter;
	private RecordProcessID myProcessID;

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};

	public AttachAdapter(Context context, List<MsgAttach> lstAttach) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.myDealAdapter = new DealAdapter(context);
		this.lstAttach = lstAttach;
		myProcessID = new RecordProcessID();
		myProcessID.positon = -1;
		myProcessID.playingId = 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.attach_list_item, null);
			holder = new Holder();
			holder.imageview = (SmartImageView) convertView
					.findViewById(R.id.item_attach);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final MsgAttach attach = this.lstAttach.get(position);
		if (attach.getAttachType() == AppConfig.AttachType.VEDIO) {
			holder.imageview.setImageResource(R.drawable.right_play_normal);
			holder.imageview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (new File(attach.getAttachContent()).exists()) {
						setProcessId(position, 0);
						myDealAdapter.onPlayButton(attach.getAttachContent());
						new AdapterPlayUI().start();
					} else {
						Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG)
								.show();
					}
				}
			});
		} else {
			if (myDealAdapter.getPictureBmp(AppConfig.AttachType.PICTURE,
					attach.getAttachContent()) != null) {
				holder.imageview
						.setImageBitmap(myDealAdapter.getPictureBmp(
								AppConfig.AttachType.PICTURE,
								attach.getAttachContent()));
			}
			holder.imageview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!attach.getAttachContent().equals("")) {
						Intent intent = new Intent();
						intent.setClass(mContext, ImageBigActivity.class);
						intent.putExtra("path", attach.getAttachContent());
						mContext.startActivity(intent);
					} else {
						Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG)
								.show();
					}
				}
			});
		}
		return convertView;
	}

	/** View holder for the views we need access to */
	private static class Holder {
		public SmartImageView imageview;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.lstAttach.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setProcessId(int position, int playingId) {
		myProcessID.playingId = playingId;
		myProcessID.positon = position;
	}

	class RecordProcessID {
		int positon;
		int playingId;
	}

	class AdapterPlayUI extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (myDealAdapter.getIsPlaying()) {
				try {
					sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
				setProcessId(myProcessID.positon,
						(myProcessID.playingId + 1) % 3);
				myHandler.sendEmptyMessage(1);
			}
			setProcessId(-1, 0);
			myHandler.sendEmptyMessage(1);
			super.run();
		}

	}
}
