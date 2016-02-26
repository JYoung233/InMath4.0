package com.example.inmath2;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Feedback extends Activity {
	ImageView feedback;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       
		setContentView(R.layout.feedback);
        feedback=(ImageView)findViewById(R.id.suggestion);
        feedback.setAdjustViewBounds(true);
        feedback.setScaleType(ImageView.ScaleType.CENTER_CROP);
        
	}
}
