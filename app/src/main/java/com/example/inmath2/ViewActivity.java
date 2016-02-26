package com.example.inmath2;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import rDataBase.DBopenHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewActivity extends Activity {
	private TextView title;
	private TextView text;
	private ImageView pic1;
	private ImageButton home;
	private ImageButton share;
	private String filename="";
	private int id;
	private String title1="";
	SQLiteDatabase db=null;
	DBopenHelper oph1=new DBopenHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		title=(TextView)findViewById(R.id.title);
	    text=(TextView)findViewById(R.id.text);
	    pic1=(ImageView)findViewById(R.id.pic1);
	    home=(ImageButton)findViewById(R.id.home);
	    share=(ImageButton)findViewById(R.id.share);
	    Bundle bundle=getIntent().getExtras();
	    id=bundle.getInt("id");
	 
	    db=oph1.getReadableDatabase();
		String sql="select title from note1 where _id=?";
		Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});  
		if(c.moveToFirst()){
			title1=c.getString(0);
			 title.setText(title1);
		}
       
	    try {  
	         FileInputStream fis = openFileInput(title1.toString().trim()+".txt");  
	         
	    	 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    	byte[] buffer = new byte[1024];  
	    	 int len = 0;  
	        while ((len = fis.read(buffer)) != -1) {  
	    	 baos.write(buffer, 0, len);  
	            }
	        text.setText(baos.toString());
	        fis.close();  
	         baos.close(); 
	         
	    }
	catch (Exception e) {  
		 e.printStackTrace();  
	 } 
	    String path = Environment.getExternalStorageDirectory().getPath() +"/myImage1/"+title1+".jpg";
        File file = new File(path);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(path);
            pic1.setImageBitmap(bm);}
       
		 
        home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ViewActivity.this,NoteMain.class);
				startActivity(intent);
				ViewActivity.this.finish();
			}
        });
       share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ViewActivity.this,NoteMain.class);
				startActivity(intent);
			}
        });
        
	

	        

		
         
	}
}