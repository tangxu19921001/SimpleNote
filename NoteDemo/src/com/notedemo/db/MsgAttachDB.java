package com.notedemo.db;


import java.util.ArrayList;
import java.util.List;

import com.notedemo.model.MsgAttach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MsgAttachDB {
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public MsgAttachDB(Context context) {
		dbHelper = new DBHelper(context, DBHelper.Db_Version);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 删除消息附件
	 * 
	 * @param msgId
	 */
	public void delMsgAttach(int msgId) {
		db.execSQL("delete from  " + DBHelper.Msg_Attach + " where msgId="
				+ msgId);
	}

	/**
	 * 保存消息附件
	 * 
	 * @param entity
	 * @return
	 */
	public int saveMsgAttach(MsgAttach entity) {
		db.execSQL("insert into " + DBHelper.Msg_Attach
				+ " (msgId,attachType,attachContent)" + " values(?,?,?)",
				new Object[] { entity.getMsgId(), entity.getAttachType(),
						entity.getAttachContent() });

		Cursor c = db.rawQuery("SELECT * from " + DBHelper.Msg_Attach
				+ " order by id desc limit 1", null);

		int attachId = 0;
		while (c.moveToNext()) {
			attachId = c.getInt(c.getColumnIndex("id"));
		}
		return attachId;

	}

	/**
	 * 更新消息附件
	 * 
	 * @param entity
	 */
	public void updateMsgAttach(MsgAttach entity) {
		delMsgAttach(entity.getMsgId());
		saveMsgAttach(entity);
	}

	/**
	 * 获取消息附件
	 * 
	 * @param userId
	 * @param msgType
	 * @return
	 */
	public List<MsgAttach> getMsgAttach(int msgId) {
		List<MsgAttach> list = new ArrayList<MsgAttach>();
		Cursor c = db.rawQuery("SELECT * from " + DBHelper.Msg_Attach
				+ " where msgId=" + msgId + " ORDER BY id asc ", null);
		while (c.moveToNext()) {
			MsgAttach entity = new MsgAttach();
			entity.setAttachId(c.getInt(c.getColumnIndex("id")));
			entity.setMsgId(c.getInt(c.getColumnIndex("msgId")));
			entity.setAttachType(c.getInt(c.getColumnIndex("attachType")));
			entity.setAttachContent(c.getString(c
					.getColumnIndex("attachContent")));
			list.add(entity);
		}
		c.close();
		return list;
	}
}
