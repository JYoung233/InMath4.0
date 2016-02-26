package com.example.inmath2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.inmath2.model.Chapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityOne extends Activity {

	private ListView listView;
	private int[] imageIds=new int[]{R.drawable.gao,R.drawable.gai,R.drawable.xian};
	private String[] descs=new String[]{"高等数学","概率统计","线性代数"};
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
				 Intent intent=new Intent(ActivityOne.this,ActivityOne_2.class);
				 bundle.putLong("selectinfo", postion);
				 intent.putExtras(bundle);
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
