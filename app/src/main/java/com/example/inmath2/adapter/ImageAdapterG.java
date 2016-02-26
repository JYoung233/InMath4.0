package com.example.inmath2.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.inmath2.GalleryExt;
import com.example.inmath2.ImageSource;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Gallery.LayoutParams;

public class ImageAdapterG extends BaseAdapter{

	private int ownposition;
	private ImageSource im;
    private Cursor cur;
    private Bitmap image1;
    AssetManager am =null;
    private String filename="";
	

	private Context mContext; 

	// 定义整型数组 即图片源

	// 声明 ImageAdapter
	public ImageAdapterG(Context c) {
		mContext = c;
		im=new ImageSource(c);
		Resources resources = c.getResources();
		am=resources.getAssets();
		
	}
	public int getOwnposition() {
		return ownposition;
	}

	public void setOwnposition(int ownposition) {
		this.ownposition = ownposition;
	}

	// 获取图片的个数
	public int getCount() {
		return im.getLength();
	}

	// 获取图片在库中的位置
	public Object getItem(int position) { 
		ownposition=position;
		return position;
	}

	// 获取图片ID
	public long getItemId(int position) {
		ownposition=position; 
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		
		ownposition=position;
		ImageView imageview = new ImageView(mContext);
		imageview.setBackgroundColor(0xFF000000);
		imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageview.setLayoutParams(new GalleryExt.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		cur=im.getCursor();
		if(cur.moveToPosition(position)){
			filename=cur.getString(0);
			
			InputStream is=null;
		    try {
		    	
				is = am.open(filename);
		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            image1=BitmapFactory.decodeStream(is); 
			imageview.setImageBitmap(image1);
		}
		if(cur.isAfterLast()){
			im.CloseCur();
			im.CloseDb();
		}
		
		return imageview;
		
		
	}
}