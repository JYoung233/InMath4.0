package com.example.inmath2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import rDataBase.DBopenHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddNoteActivity extends Activity{
	//private SQLiteDatabase db;
	private ImageButton save;
	private EditText title;
	private EditText content;
	private ImageButton camera;
	//private ImageView pic;
	Bundle bundle=new Bundle();
	Bitmap bitmap;
	//private String filename=""; 
	private boolean ispicture=false;
	SQLiteDatabase db=null;
	DBopenHelper oph1=new DBopenHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnote); 
		 save=(ImageButton)findViewById(R.id.save);
	    title=(EditText)findViewById(R.id.title);
		content=(EditText)findViewById(R.id.content);
		camera=(ImageButton)findViewById(R.id.camera);
		//pic=(ImageView)findViewById(R.id.pic);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//dbHelper=new SqliteHelper(context,"Inmath.db", null, 2);
		       // db= dbHelper.getWritableDatabase();
			//insertData(db, title.getText().toString(), content.getText().toString());
				
			Intent intent4 =new Intent(AddNoteActivity.this, NoteMain.class);
			AddNoteActivity.this.finish();
			try{
				db=oph1.getWritableDatabase();
				String sql="insert into note1(title) values('"+title.getText().toString()+"')";
				db.execSQL(sql);
				FileOutputStream fos = openFileOutput(title.getText().toString().trim()+".txt",Context.MODE_PRIVATE);  
				fos.write(content.getText().toString().getBytes());  
				fos.close(); 
				if(ispicture){
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.v("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				FileOutputStream b = null;
				File file = new File("/sdcard/myImage1/");
				file.mkdirs();// 创建文件夹
				String fileName = "/sdcard/myImage1/"+title.getText().toString().trim()+".jpg";

				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
				
				intent4.putExtra("title", title.getText().toString());
				startActivity(intent4);
				}
				
			catch (Exception e) {  
				e.printStackTrace();  
				        } 
			}
			
		  
		
		
		

			}); 
		camera.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				
				
			}
		});
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			
            ispicture=true;
			bundle = data.getExtras();
			bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			((ImageView) findViewById(R.id.pic)).setImageBitmap(bitmap);
			
		

			// 将图片显示在ImageView里
		}
	}
			
		
	}


