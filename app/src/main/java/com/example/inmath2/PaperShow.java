package com.example.inmath2;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.widget.ImageView;

public class PaperShow extends Activity {// �����������ͼƬ��ѡ����Ϣ
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
			//ȥ����д�ĸ������ֻ�path�ķ���
		}else{
			BitmapUtils bitmapUtils = new BitmapUtils(this);
			BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
			// ���ڼ�����ʾ
			//displayConfig.setLoadingDrawable(getResources().getDrawable(
					//R.drawable.duo));
			// ����ʧ����ʾ
			//displayConfig.setLoadFailedDrawable(getResources().getDrawable(
			//		R.drawable.da));
			//����ͼƬ
			bitmapUtils.display(iv, path, displayConfig);
			//��̨������
		}
		
		
		
	}

}
