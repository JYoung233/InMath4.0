package com.example.inmath2;

import java.io.IOException;
import java.io.InputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import rDataBase.DBopenHelper;
public class Selectpage extends Activity implements OnGestureListener {
	    
	    
	    
         private ImageView iv;
         private ImageButton sc;
        // private TextView tv;
         AssetManager am =null;
         Bitmap image;
         GestureDetector detector;
         private int width,height;
         private boolean iscollect=false;
         private String filename="";
         //private int displayWidth;  
         //private int displayHeight;  
         
         private float currentScale=1;
         Matrix matrix ;//控制图片缩放的Matrix对象
         //private DisplayMetrics dm; 
         DBopenHelper dh=new DBopenHelper(this);
         SQLiteDatabase db;
         
         @SuppressLint("NewApi")
		protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
            setContentView(R.layout.showselect);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
            iv=(ImageView)findViewById(R.id.imageView6);
            sc=(ImageButton)findViewById(R.id.shoucang);
            // dm = getResources().getDisplayMetrics();
            
            //displayWidth=dm.widthPixels; 
            //displayHeight=dm.heightPixels;   
            am=getAssets();
            matrix=new Matrix();
		    Bundle bundle = this.getIntent().getExtras(); 
		    filename=bundle.getString("keyname").toString()+".png";
		    
		    detector=new GestureDetector(this,this);
		    //matrix=new Matrix();
            InputStream is=null;
		    try {
		    	
				is = am.open(filename);
		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            image=BitmapFactory.decodeStream(is); 
            
            width=image.getWidth();
            height=image.getHeight();
            iv.setAdjustViewBounds(true);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		    iv.setImageBitmap(image);
		    sc.setBackgroundResource(R.drawable.shoucang);
            sc.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				if(!iscollect){
    				 sc.setBackgroundResource(R.drawable.favourite);
    				 
    				 iscollect=true;
    				 db=dh.getWritableDatabase();
    				 Cursor cur=db.rawQuery("select * from photo where photoname like ?",new String[]{filename});
    				 if(cur.moveToFirst()==true){
    					 Toast.makeText(Selectpage.this, "已经收藏过啦~",
    	  							Toast.LENGTH_SHORT).show();
    					 cur.close();
    					 db.close();
    				 }
    				 else{
    				 ContentValues values=new ContentValues();
    				 values.put("photoname", filename);
    				 db.insert("photo", null, values);
    				 db.close();
    				 Toast.makeText(Selectpage.this, "收藏成功~",
  							Toast.LENGTH_SHORT).show();
    				 }
    				}
    				else{
    					sc.setBackgroundResource(R.drawable.shoucang);
    					db=dh.getWritableDatabase();
    					db.delete("photo", "photoname=?",new String[]{filename});
    					db.close();
    					Toast.makeText(Selectpage.this, "取消收藏",
     							Toast.LENGTH_SHORT).show();
    					iscollect=false;
    					}
    				
    			}
            });
         }
		@Override
		public boolean onTouchEvent(MotionEvent me){
			return detector.onTouchEvent(me);
		}
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
				float velocityY) {
			//TODO Auto-generated method stub
			velocityX=velocityX>4000?4000:velocityX;
			velocityX=velocityX<-4000?-4000:velocityX;//根据手势的速度来计算缩放比，如果》0放大图片，否则缩小
			currentScale+=currentScale*velocityX/4000.0f;
			currentScale=currentScale>0.01?currentScale:0.01f;
			
			
			
			matrix.reset();
			matrix.setScale(currentScale,currentScale,160,200);
			

			BitmapDrawable tmp=(BitmapDrawable)iv.getDrawable();
			
			Bitmap bitmap=Bitmap.createBitmap(image,0,0,width,height,matrix,true);
			
				if(!tmp.getBitmap().isRecycled()){
					tmp.getBitmap().recycle();
					}
				iv.setImageBitmap(bitmap);
		   
			
			
			return true;
		}
		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			
			
				double scale=0.8; 
				
				currentScale=(float)(currentScale*scale);
				Matrix matrix ;
				matrix=new Matrix();
				matrix.reset();
				matrix.setScale(currentScale,currentScale,160,200);
				BitmapDrawable tmp=(BitmapDrawable)iv.getDrawable();
				
				if(!tmp.getBitmap().isRecycled()){
					tmp.getBitmap().recycle();
					}
				Bitmap bitmap=Bitmap.createBitmap(image,0,0,width,height,matrix,true);
				iv.setImageBitmap(bitmap);
				
			
			
			return true;
		}}
		   
		    

		
		    //Context context=getBaseContext();
			//FileService fileservice=new FileService(context);
		    
		    
