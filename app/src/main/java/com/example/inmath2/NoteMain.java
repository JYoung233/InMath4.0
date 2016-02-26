package com.example.inmath2;

import android.app.Activity;
import android.app.AlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rDataBase.DBopenHelper;


import android.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;


import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NoteMain extends Activity {
	
	SQLiteDatabase db=null;
	DBopenHelper oph1=new DBopenHelper(this);
	DBopenHelper oph2=new DBopenHelper(this);
	private Button addNote;
	private int index;//长按listview的索引值
	public int MID;
	private ListView Msglist;
	private List<Map<String,Object>> listItems;
	private SimpleAdapter Ada;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_main);
		 Msglist=(ListView)findViewById(R.id.Msglist);
		 listItems=new ArrayList<Map<String,Object>>();
		 db=oph1.getReadableDatabase();
		 String sql="select _id,title from note1";
		 Cursor cur=db.rawQuery(sql,null);
		 cur.moveToFirst();
		 while(!cur.isAfterLast()){
			 HashMap<String,Object>map=new  HashMap<String,Object>();
			 map.put("title", cur.getString(1));
			 map.put("id",cur.getString(0));
			 listItems.add(map);
			 cur.moveToNext();
		 }
		 cur.close();
		 db.close();
		
		Ada=new SimpleAdapter(this,listItems,R.layout.sublist,new String[]{"title"},new int[]{ R.id.wbtext});
		Msglist.setAdapter( Ada);
		Msglist.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent,View view,int postion,long id){//点击跳入view界面
        	   
	           Intent intent = new Intent(NoteMain.this,ViewActivity.class);
	           Bundle bundle=new Bundle();
	          
   				bundle.putInt("id", postion+1);
   				
   				intent.putExtras(bundle);
   				
	           startActivity(intent);
	                 }
	     });
		// 添加长按点击,得到点中的index，即参数arg2
		registerForContextMenu(Msglist);
		Msglist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> ada, View view,
					int arg2, long longIndex) {
				index=arg2;
				Msglist.showContextMenu();
				return true;
			}

		});	
	}
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo){
		menu.setHeaderTitle("数学笔记");
		menu.add(0,0, Menu.NONE,"删除");
		menu.add(0,1, Menu.NONE,"修改");
		
	}
	
	public boolean onContextItemSelected(MenuItem item) {
		//AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item  
               // .getMenuInfo();  
        // MID = (int) info.id;// 这里的info.id对应的就是数据库中_id的值  
		switch (item.getItemId()) {
		case 0:
			listItems.remove(index);
			 db=oph1.getReadableDatabase();
			  
			    db.execSQL("DELETE FROM " + "note1" + " WHERE _id="  
			            + Integer.toString(index+1));  
			    db.close();  
			  
			Ada.notifyDataSetChanged();
			Msglist.invalidate();
			break;
		case 1:
			Intent intent =new Intent(NoteMain.this, AddNoteActivity.class);
			startActivity(intent);
			NoteMain.this.finish();
			break;
         }
		return true;
		}
	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
	    MenuInflater inflater = getMenuInflater();  
	    inflater.inflate(R.menu.note, menu);  
	    return super.onCreateOptionsMenu(menu);  
	}  
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	    case R.id.action_add:
	    	Intent intent =new Intent(NoteMain.this, AddNoteActivity.class);
			startActivity(intent);
			NoteMain.this.finish();
	     break;
	   }
	    return true;
	}
}