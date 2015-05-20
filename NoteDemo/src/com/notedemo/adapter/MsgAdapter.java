package com.notedemo.adapter;


import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.notedemo.activity.AppApplication;
import com.notedemo.activity.R;
import com.notedemo.config.AppConfig;
import com.notedemo.db.MsgDB;
import com.notedemo.image.SmartImageView;
import com.notedemo.model.Msg;
import com.notedemo.utils.DateUtil;
import com.notedemo.utils.ExpressionUtil;


public class MsgAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Msg> mMsgList;
	private Context mContext;

	public MsgAdapter(Context context, List<Msg> data) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		mMsgList = data;
	}

	public void remove(int position) {
		if (position < mMsgList.size()) {
			mMsgList.remove(position);
			notifyDataSetChanged();
		}
	}

	public void remove(Msg item) {
		if (mMsgList.contains(item)) {
			mMsgList.remove(item);
			notifyDataSetChanged();
		}
	}

	public void add(Msg item) {
		if (mMsgList.contains(item)) {
			mMsgList.remove(item);
		}
		mMsgList.add(item);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMsgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private static class Holder {
		public ImageView item_msgtype;
		public TextView item_title;
		public TextView item_date;
		public TextView item_msg;
		public TextView item_remind;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.msg_listview_item, null);
			holder = new Holder();
			holder.item_title = (TextView) convertView
					.findViewById(R.id.item_title);
			holder.item_date = (TextView) convertView
					.findViewById(R.id.item_date);
			holder.item_msg = (TextView) convertView
					.findViewById(R.id.item_msg);
			holder.item_msgtype = (ImageView) convertView
					.findViewById(R.id.item_msgtype);
			holder.item_remind = (TextView) convertView
					.findViewById(R.id.item_remind);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (convertView != null) {
			final Msg item = mMsgList.get(position);
			if (item.getMsgType() == AppConfig.MsgType.JISHI) {
				holder.item_remind.setVisibility(View.GONE);
				holder.item_msgtype.setBackgroundResource(R.drawable.icon_work);
			} else if (item.getMsgType() == AppConfig.MsgType.SIMI) {
				holder.item_remind.setVisibility(View.GONE);
				holder.item_msgtype
						.setBackgroundResource(R.drawable.icon_goutong);
			} else if (item.getMsgType() == AppConfig.MsgType.TIXING) {
				holder.item_remind.setVisibility(View.VISIBLE);
				holder.item_msgtype
						.setBackgroundResource(R.drawable.icon_notice);
				if (item.getIsRemind() == 1) {
					holder.item_remind.setText("已提醒");
				} else {
					String curTime = DateUtil.getCurrentTime();
					String setTime = item.getMsgTime();
					if (DateUtil.timeCompare(curTime, setTime) > 0) {
						holder.item_remind.setText("已过期");
					} else {

						holder.item_remind.setText("未开始");
					}
				}
			}
			holder.item_title.setText(ExpressionUtil
					.convertNormalStringToSpannableString(mContext,
							item.getMsgTitle()), BufferType.SPANNABLE);
			holder.item_date.setText(DateUtil.getTimeDiff(item.getMsgTime()));
			holder.item_msg.setText(ExpressionUtil
					.convertNormalStringToSpannableString(mContext,
							item.getMsgContent()), BufferType.SPANNABLE);
		}
		return convertView;
	}
}
