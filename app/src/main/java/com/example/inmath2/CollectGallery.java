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
		// ���Gallery����	
		GalleryExt  g = (GalleryExt) findViewById(R.id.ga);
		//ͨ��Intent�õ�GridView��������ͼƬλ��
		Intent intent = getIntent();
		i_position = intent.getIntExtra("position", 0);	 
		// ���ImageAdapter��Gallery����
		ImageAdapterG ia=new ImageAdapterG(this);		
		g.setAdapter(ia);
	 	g.setSelection(i_position); 	
	 	
	 	//���ض���
	 	//Animation an= AnimationUtils.loadAnimation(this,R.anim.scale );
        //g.setAnimation(an); 

	} 
}
