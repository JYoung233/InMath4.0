package com.example.inmath2;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.widget.ImageView;

public class PaperShow extends Activity {// 这个用来接收图片的选择信息
	private ImageView iv;

	private String name;
	private String path;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.papershow);
		iv = (ImageView) findViewById(R.id.papershow1);
		name = getIntent().getStringExtra("name");
		path = getIntent().getStringExtra("path");
		iv.setAdjustViewBounds(true);
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		if(TextUtils.isEmpty(path)){
			//去掉你写的根据名字获path的方法
		}else{
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
			// 正在加载显示
			//displayConfig.setLoadingDrawable(getResources().getDrawable(
					//R.drawable.duo));
			// 加载失败显示
			//displayConfig.setLoadFailedDrawable(getResources().getDrawable(
			//		R.drawable.da));
			//加载图片
			bitmapUtils.display(iv, path, displayConfig);
			//后台加数据
		}
		
		
		
	}

}
