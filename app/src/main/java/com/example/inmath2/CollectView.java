package com.example.inmath2;





import java.io.IOException;
import java.io.InputStream;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class CollectView extends Activity {
	private GridView g;
	private DisplayMetrics dm;
	private int imageCol = 3;
	AssetManager am =null;
	ImageSource im;
	
    private GridImageAdapter ia;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygridview);
		im=new ImageSource(this);
		g = (GridView) findViewById(R.id.myGrid);
		ia = new GridImageAdapter(this);
		g.setAdapter(ia);
		g.setOnItemClickListener(new OnItemClick(this)); 
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		am=getAssets();
		
	}
	public void onConfigurationChanged(Configuration newConfig) {
		try {

			super.onConfigurationChanged(newConfig);
			//如果屏幕是竖屏，则显示3列，如果是横屏，则显示4列
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				imageCol = 4;
			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				imageCol = 3;
			}
			g.setNumColumns(imageCol);
			g.setAdapter(new GridImageAdapter(this));//这里需要改正
			// ia.notifyDataSetChanged();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public class OnItemClick implements OnItemClickListener {
		private Context mContext;
		public OnItemClick(Context c) {
			mContext = c;
		} 
		@Override
		public void onItemClick(AdapterView aview, View view, int position,
				long arg3) {
			Intent intent = new Intent();
			intent.setClass(CollectView.this, CollectGallery.class);
			intent.putExtra("position", position);
			CollectView.this.startActivity(intent);
		} 
		
	}
	public class GridImageAdapter extends BaseAdapter{
		Drawable btnDrawable;
		private Context mContext;
		
		private Cursor cur;
		private String filename="";
		
		public GridImageAdapter(Context c){
			mContext=c;
			Resources resources = c.getResources();
			
		}
		public int getCount() {
			return im.getLength();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageViewExt image;
			Bitmap image1;
			if (convertView == null) {
				image = new ImageViewExt(mContext);
				//如果是横屏，GridView会展示4列图片，需要设置图片的大小 
				if (imageCol == 4) {
					image.setLayoutParams(new GridView.LayoutParams(
							dm.heightPixels / imageCol - 6, dm.heightPixels
									/ imageCol - 6));
				} else {//如果是竖屏，GridView会展示3列图片，需要设置图片的大小 
					image.setLayoutParams(new GridView.LayoutParams(
							dm.widthPixels / imageCol - 6, dm.widthPixels
									/ imageCol - 6));
				}
				image.setAdjustViewBounds(true);
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);

				// imageView.setPadding(3, 3, 3, 3);
			} else {
				image =(ImageViewExt) convertView;
			}
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
				image.setImageBitmap(image1);
			}
			if(cur.isAfterLast()){
				im.CloseCur();
				im.CloseDb();
			}
			
			return image;
			
			
		}
		
		
	}

}
