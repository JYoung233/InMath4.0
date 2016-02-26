package com.example.inmath2;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class SaveSecretActivity extends Activity {
	public static final String SP_INFOS="SPDATA_Files";
	public static final String USERID="UserID";
	public static final String PASSWORD="PassWord";
	private static EditText etUid;
	private static EditText etPwd;
	private static CheckBox cb;
	private static String uidstr;
	private static String pwdstr;
	boolean flag;
	//SQLiteDatabase db=null;
	//DBopenHelper oph1=new DBopenHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Button button1=(Button)this.findViewById(R.id.button1);
	    Button button2=(Button)this.findViewById(R.id.button2);
		etUid=(EditText)findViewById(R.id.yonghuming2);
		etPwd=(EditText)findViewById(R.id.mima2);
		cb=(CheckBox)findViewById(R.id.checkBox1);
		
		checkIfRemember();
        button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = etUid.getText().toString();
				String password = etPwd.getText().toString();
				LoginTask(name,password);
				/*
				if(flag){//在数据库中有对应的用户名好密码
					Toast.makeText(SaveSecretActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();//登录成功之后怎么记登录状态
					Intent intent1=new Intent(SaveSecretActivity.this,MainActivity.class);//这里回到主窗口，一般回到信息窗口
					startActivity(intent1);
					} 
				else{
					Toast.makeText(SaveSecretActivity.this, "你还没有注册呢~", Toast.LENGTH_SHORT).show();
				}*/
				
				}
        });
        button2.setOnClickListener(new View.OnClickListener(){//登陆
        	public void onClick(View arg0) {
        		Intent intent2=new Intent(SaveSecretActivity.this,Register2.class);
				startActivity(intent2);
				SaveSecretActivity.this.finish();
        		
        	}
        	
        });

			
	}
	public void LoginTask(String name, String password) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("password", password);
		http.send(HttpMethod.POST, "http://www.liuminhua.cn/Math/LoginAction",
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(SaveSecretActivity.this, "网络问题，请重试", 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						String result = info.result;
						JSONObject jsonObject = JSONObject.parseObject(result);
						int errorCode = jsonObject.getInteger("errorCode");
						if (errorCode == 1000) {
							Toast.makeText(SaveSecretActivity.this, "登陆成功", 0).show();
							Intent intent1=new Intent(SaveSecretActivity.this,MainActivity.class);//这里回到主窗口，一般回到信息窗口
							startActivity(intent1);
						} else {
							Toast.makeText(SaveSecretActivity.this, "登陆失败", 0).show();
							
						}
					}

				});
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(cb.isChecked()){
			uidstr=etUid.getText().toString().trim();
			pwdstr=etPwd.getText().toString().trim();
			rememberMe(uidstr,pwdstr);
			}
		
}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public void checkIfRemember(){
		SharedPreferences sp=getSharedPreferences(SP_INFOS,MODE_PRIVATE);
		uidstr=sp.getString(USERID, null);
		pwdstr=sp.getString(PASSWORD, null);
		if(uidstr!=null&&pwdstr!=null){
			etUid.setText(uidstr);
			etPwd.setText(pwdstr);
			cb.setChecked(true);
		}
		
	}
	public void rememberMe(String uid,String pwd ){
		SharedPreferences sp=getSharedPreferences(SP_INFOS,MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(USERID, uid);
		editor.putString(PASSWORD, pwd);
		editor.commit();
		
	}
	/*public boolean Select() {
		boolean s = false;
		
		String sql="select password from userinfo where username=? and password=?";
		
		db=oph1.getReadableDatabase();
		Cursor cursor=db.rawQuery(sql, new String[]{etUid.getText().toString(),etPwd.getText().toString()});
		if(cursor.moveToFirst()==true){
			        cursor.close();
					s=true;}
		else {
			
			s=false;
		}
		return s;}
		

		
		*/
		
		
		
		
	}


	
	
	
	

	


