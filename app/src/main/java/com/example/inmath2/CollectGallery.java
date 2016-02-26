package com.example.inmath2;

import com.example.inmath2.adapter.ImageAdapterG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class CollectGallery extends Activity {

	public int i_position = 0;
	private DisplayMetrics dm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.gallerycollect);	 
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获得Gallery对象	
		GalleryExt  g = (GalleryExt) findViewById(R.id.ga);
		//通过Intent得到GridView传过来的图片位置
		Intent intent = getIntent();
		i_position = intent.getIntExtra("position", 0);	 
		// 添加ImageAdapter给Gallery对象
		ImageAdapterG ia=new ImageAdapterG(this);		
		g.setAdapter(ia);
	 	g.setSelection(i_position); 	
	 	
	 	//加载动画
	 	//Animation an= AnimationUtils.loadAnimation(this,R.anim.scale );
        //g.setAnimation(an); 

	} 
}
