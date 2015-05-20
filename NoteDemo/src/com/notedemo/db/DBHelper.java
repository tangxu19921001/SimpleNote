package com.notedemo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

	private static final String Db_Name = "note.db";

	public static final int Db_Version = 1;

	public static final String Msg_Table = "msg";

	public static final String Msg_Attach = "msg_attach";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context, int version) {
		super(context, Db_Name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL("create table if not exists "+Msg_Table+
				" (id integer primary key autoincrement,"+
				"msgType integer,msgTitle text,msgContent text,"+
				"msgTime text,isRemind integer)");
		
		db.execSQL("create table if not exists "+Msg_Attach+
				" (id integer primary key autoincrement,"+
				"msgId integer,attachType integer,"+
				"attachContent text)");
	}

}
