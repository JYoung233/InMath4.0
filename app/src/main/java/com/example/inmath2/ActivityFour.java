package com.example.inmath2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.inmath2.model.Chapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityFour extends Activity {

	private ListView listView;
	private int[] imageIds=new int[]{R.drawable.yi,R.drawable.yi,R.drawable.yi,R.drawable.yi,R.drawable.yi,R.drawable.yi};
	private String[] descs=new String[]{"2010年考研卷","2011年考研卷","2012年考研卷","2013年考研卷","2014年考研卷","2015年考研卷"};
	Bundle bundle=new Bundle();
	private static List<Chapter> dataFromNet = new ArrayList<Chapter>();
	
	static {
		initData();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_one);
		listView = (ListView)findViewById(R.id.listView);
		List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
		for(int i=0;i<descs.length;i++){
			Map<String,Object> listItem=new HashMap<String,Object>();
			listItem.put("header",imageIds[i] );
			listItem.put("desc",descs[i] );
			listItems.add(listItem);
		}
		SimpleAdapter Adapter1=new SimpleAdapter(this,listItems,R.layout.item2,new String[]{"header","desc"},new int[]{R.id.header,R.id.desc});
		 listView.setAdapter( Adapter1);
		 listView.setOnItemClickListener(new OnItemClickListener(){
			 public void onItemClick(AdapterView<?> parent,View view,int postion,long id){
				 Intent intent=new Intent(ActivityFour.this,PaperShow.class);
				 //bundle.putLong("selectinfo", postion);
				// intent.putExtras(bundle);
				 startActivity(intent);
				 
			 }
			 
		 });
		 
		
		
	}
	
	/****
	 * 
	 */
	public static void initData(){
		
		Chapter chapter = new Chapter();
		chapter.setResId(R.id.imageView1);
		chapter.setChapterName("");
		dataFromNet.add(chapter);
	}
	
}
