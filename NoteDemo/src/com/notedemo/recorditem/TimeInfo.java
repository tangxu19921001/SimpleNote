package com.notedemo.recorditem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

import com.notedemo.activity.R;

public class TimeInfo {
	Context context;

	public TimeInfo(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/* gettime by format yyyy_MM_dd_HH_mm_ss */

	public static String translateTime(long longtime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longtime);
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		int Month = mMonth + 1;
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		return new String(mYear + "-" + Month + "-" + mDay + "-" + hour
				+ minute);
	}

	public String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String time = formatter.format(curDate);
		return time;
	}

	public String duringTime(int minute, int second) {
		String duringtime = String.valueOf(minute) + ":"
				+ this.timeForment(second);
		return duringtime;
	}

	public String timeForment(int value) {
		String dealtime = null;
		if (value < 10 && value >= 0) {
			dealtime = "0" + String.valueOf(value);
		} else if (value > 9 && value < 60) {
			dealtime = String.valueOf(value);
		} else {
			Toast.makeText(context, "时间格式不正确", Toast.LENGTH_LONG).show();
			dealtime = "00";
		}
		return dealtime;
	}

}
