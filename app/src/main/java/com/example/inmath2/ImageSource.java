package com.example.inmath2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rDataBase.DBopenHelper;

public class ImageSource {

	
	private Context mContext; 
	private Cursor cur;
	private SQLiteDatabase db;
	private DBopenHelper dh;
	public ImageSource(Context c) {
		// TODO Auto-generated constructor stub
		mContext=c;
	    dh=new DBopenHelper(c);
	     db=dh.getReadableDatabase();
	    cur=db.rawQuery("select * from photo",null);
		
	}
	public Cursor getCursor(){
		return cur;
	}
	public int getLength(){
		return cur.getCount();
	}
	public void CloseCur(){
		cur.close();
	} 
	public void CloseDb(){
		db.close();
	}

	/**
	 * @param args
	 */
	
}
