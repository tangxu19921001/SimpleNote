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
	 * ɾ����Ϣ����
	 * 
	 * @param msgId
	 */
	public void delMsgAttach(int msgId) {
		db.execSQL("delete from  " + DBHelper.Msg_Attach + " where msgId="
				+ msgId);
	}

	/**
	 * ������Ϣ����
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
	 * ������Ϣ����
	 * 
	 * @param entity
	 */
	public void updateMsgAttach(MsgAttach entity) {
		delMsgAttach(entity.getMsgId());
		saveMsgAttach(entity);
	}

	/**
	 * ��ȡ��Ϣ����
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
