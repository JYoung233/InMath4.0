package com.example.inmath2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.inmath2.model.Moni;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
public class ActivityOne_2 extends Activity {

	private ListView listView;
	private int[] imageIds1=new int[]{R.drawable.yi,R.drawable.wei,R.drawable.chang};
	private int[] imageIds2=new int[]{R.drawable.hang,R.drawable.ju,R.drawable.n,R.drawable.xian,R.drawable.ju,R.drawable.er};
	private int[] imageIds3=new int[]{R.drawable.sui,R.drawable.sui,R.drawable.duo,R.drawable.sui,R.drawable.da,R.drawable.shu,R.drawable.can};
	private String[] gaodeng=new String[]{"一元函数的导数与微积分","微分中值定理","常微分方程"};
	private String[] xianxing=new String[]{"行列式","矩阵","n维向量与向量空间","线性方程组","矩阵的特征值与特征方程","二次型"};
	private String[] gailv=new String[]{"随机事件和概率","随机变量及其分布","多维随机变量及其分布","随机变量的数字特征","大数定律和中心极限定律","数理统计","参数估计和假设分析"};
	private SimpleAdapter Adapter1;
	//private static List<Chapter> dataFromNet = new ArrayList<Chapter>();
	private List<Moni> monis = new ArrayList<Moni>();
	/*static {
		initData();
	}
	*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_one);
		listView = (ListView)findViewById(R.id.listView);
		Bundle bundle = this.getIntent().getExtras(); 
	    Long selectinfo=bundle.getLong("selectinfo");
	   
		List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
		if(selectinfo==0){
			for(int i=0;i<gaodeng.length;i++){
				Map<String,Object> listItem=new HashMap<String,Object>();
				listItem.put("header",imageIds1[i] );
				listItem.put("desc",gaodeng[i] );
				listItems.add(listItem);}
			
		}
		else if(selectinfo==1){
			for(int i=0;i<xianxing.length;i++){
				Map<String,Object> listItem=new HashMap<String,Object>();
				listItem.put("header",imageIds2[i] );
				listItem.put("desc",xianxing[i] );
				listItems.add(listItem);}
			
		}
		else if(selectinfo==2){
			for(int i=0;i<gailv.length;i++){
				Map<String,Object> listItem=new HashMap<String,Object>();
				listItem.put("header",imageIds3[i] );
				listItem.put("desc",gailv[i] );
				listItems.add(listItem);}
			
		}
	
		 Adapter1=new SimpleAdapter(this,listItems,R.layout.item2,new String[]{"header","desc"},new int[]{R.id.header,R.id.desc});
		 listView.setAdapter( Adapter1);
		 listView.setOnItemClickListener(new OnItemClickListener(){
			 public void onItemClick(AdapterView<?> parent,View view,int postion,long id){
				 
				 @SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) Adapter1.getItem(postion);
				 String name = (String) map.get("desc");
				 Intent intent=new Intent(ActivityOne_2.this,PaperShow.class);
//				 Moni moni = monis.get(postion);
				 intent.putExtra("name", name);
//				 intent.putExtra("path", moni.getPath());
				 //intent.putExtras(bundle);
				 startActivity(intent);
				 //这里要考虑图片存储在哪里的问题！！！！
			 }
			 
		 });
		 
		
		
	}
	/*public static void initData(){
		
		Chapter chapter = new Chapter();
		chapter.setResId(R.id.imageView1);
		chapter.setChapterName("");
		dataFromNet.add(chapter);
	}*/
	public void getMoni(String name){
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name+" ");
		http.send(HttpMethod.POST, "http://www.liuminhua.cn/Math/MoniAction",
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(ActivityOne_2.this, "网络问题，请重试", 0).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String info = responseInfo.result;
					    JSONObject infoJson = JSONObject.parseObject(info);
					    int errorCode = infoJson.getInteger("errorCode");
					    if(errorCode==1000){
					    	JSONArray jsonDataFromNet = infoJson
									.getJSONArray("model");
					    	List<Moni> dataFromNet = JSONArray.parseArray(
									jsonDataFromNet.toJSONString(), Moni.class);
					    	if (dataFromNet != null && !dataFromNet.isEmpty()) {
					    		monis.addAll(dataFromNet);
					    		Adapter1.notifyDataSetChanged();
					    		}
					    	}
					    else{
					    	Toast.makeText(ActivityOne_2.this,"没有啦~",0).show();
					    }
					    
					}
		});
	}
}

