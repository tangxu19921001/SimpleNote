package com.notedemo.db;



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.notedemo.config.AppConfig;
import com.notedemo.model.Msg;

public class MsgDB {
	private SQLiteDatabase db;
	private DBHelper dbHelper;

	public MsgDB(Context context) {
		dbHelper = new DBHelper(context, DBHelper.Db_Version);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * ɾ����Ϣ
	 * 
	 * @param id
	 */
	public void delMsg(int id) {
		db.execSQL("delete from  " + DBHelper.Msg_Table + " where id=" + id);
	}

	/**
	 * ������Ϣ
	 * 
	 * @param entity
	 * @return
	 */
	public int saveMsg(Msg entity) {
		db.execSQL(
				"insert into "
						+ DBHelper.Msg_Table
						+ " (msgType,msgTitle,msgContent,msgTime,isRemind)"
						+ " values(?,?,?,?,?)",
				new Object[] { entity.getMsgType(),
						entity.getMsgTitle(), entity.getMsgContent(),
						entity.getMsgTime(), entity.getIsRemind() });

		Cursor c = db.rawQuery("SELECT * from " + DBHelper.Msg_Table
				+ " order by id desc limit 1", null);

		int msgId = 0;
		while (c.moveToNext()) {
			msgId = c.getInt(c.getColumnIndex("id"));
		}
		c.close();
		return msgId;

	}
	
	public boolean isExistMsg(int msgId) {
		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.Msg_Table
				+ " WHERE id =?", new String[] { msgId+"" });
		boolean b=c.moveToFirst();
		c.close();
		return b;
	}

	/**
	 * ������Ϣ����
	 * 
	 * @param entity
	 */
	public void updateMsg(Msg entity) {
		ContentValues cv = new ContentValues();
		cv.put("msgTitle", entity.getMsgTitle());
		cv.put("msgContent", entity.getMsgContent());
		cv.put("msgTime", entity.getMsgTime());
		cv.put("isRemind", "0");
		
		db.update(DBHelper.Msg_Table, cv, " id=? ",
				new String[] { entity.getMsgId() + "" });
	}

	/**
	 * ������Ϣ�Ѿ�����
	 * 
	 * @param msgId
	 */
	public void updateMsgHasRemind(int msgId) {
		ContentValues cv = new ContentValues();
		cv.put("isRemind", "1");

		db.update(DBHelper.Msg_Table, cv, " id=? ",
				new String[] { msgId + "" });
	}

	/**
	 * ��ȡ���д��ڵ�ǰʱ���δ���ѵ���Ϣ
	 * 
	 * @return
	 */
	public List<Msg> getAllUnRemindMsg() {
		List<Msg> list = new ArrayList<Msg>();
		Cursor c = db.rawQuery("SELECT * from " + DBHelper.Msg_Table
				+ " where msgType=?" + " and isRemind=?"
				+ " ORDER BY msgTime desc ", new String[] {
				"" + AppConfig.MsgType.TIXING, "0" });
		while (c.moveToNext()) {
			Msg entity = new Msg();
			entity.setMsgId(c.getInt(c.getColumnIndex("id")));
			entity.setMsgTitle(c.getString(c.getColumnIndex("msgTitle")));
			entity.setMsgType(c.getInt(c.getColumnIndex("msgType")));
			entity.setMsgTime(c.getString(c.getColumnIndex("msgTime")));
			entity.setMsgContent(c.getString(c.getColumnIndex("msgContent")));
			entity.setIsRemind(c.getInt(c.getColumnIndex("isRemind")));
			list.add(entity);
		}
		c.close();
		return list;
	}

	/**
	 * ��ȡĳ�����͵���Ϣ
	 * 
	 * @param msgType
	 * @return
	 */
	public List<Msg> getMsg(int msgType) {
		List<Msg> list = new ArrayList<Msg>();
		Cursor c = db.rawQuery("SELECT * from " + DBHelper.Msg_Table
				+ " where msgType=" + msgType
				+ " ORDER BY msgTime desc ", null);
		while (c.moveToNext()) {
			Msg entity = new Msg();
			entity.setMsgId(c.getInt(c.getColumnIndex("id")));
			entity.setMsgTitle(c.getString(c.getColumnIndex("msgTitle")));
			entity.setMsgType(c.getInt(c.getColumnIndex("msgType")));
			entity.setMsgTime(c.getString(c.getColumnIndex("msgTime")));
			entity.setMsgContent(c.getString(c.getColumnIndex("msgContent")));
			entity.setIsRemind(c.getInt(c.getColumnIndex("isRemind")));
			list.add(entity);
		}
		c.close();
		return list;
	}

	public void close() {
		if (db != null)
			db.close();
	}
}
