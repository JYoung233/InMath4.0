package com.example.inmath2;

import java.util.List;

import rDataBase.DBopenHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.inmath2.model.Chapter;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

@SuppressWarnings("unused")
public class Register2 extends Activity {
	private EditText editview1;
	private EditText editview2;
	private EditText editview3;
	private Button button1;
	SQLiteDatabase db = null;
	//DBopenHelper oph1 = new DBopenHelper(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avtivity_register2);
		editview1 = (EditText) this.findViewById(R.id.editText1);
		editview2 = (EditText) this.findViewById(R.id.editText2);
		editview3 = (EditText) this.findViewById(R.id.editText3);
		button1 = (Button) this.findViewById(R.id.button3);
		String usrname = editview1.getText().toString();
		String pasword = editview2.getText().toString();
		String nopasword = editview3.getText().toString();

		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String usrname = editview1.getText().toString();
				String pasword = editview2.getText().toString();
				String nopasword = editview3.getText().toString();
				int flag = -1;

				if (usrname.equals("") || pasword.equals("")
						|| nopasword.equals("")) {
					Toast.makeText(Register2.this, "用户名或密码不能为空哦~",
							Toast.LENGTH_SHORT).show();
					flag = 0;
				}
				if (!(pasword.equals(nopasword))) {
					Toast.makeText(Register2.this, "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
					flag = 0;
				}

				// db = oph1.getWritableDatabase();
				// String sql =
				// "insert into userinfo(username,password) values('"
				// + usrname + "','" + pasword + "')";
				//
				// try {
				// db.execSQL(sql);
				// } catch (SQLException e) {
				// Log.i("err", "insert failed");
				// flag = 0;
				// Toast.makeText(Register2.this, "该用户名太受欢迎啦，已被被人抢占啦！！！",
				// Toast.LENGTH_SHORT).show();
				// }
				// db.close();
				//
				// if (flag == -1) {
				// Toast.makeText(Register2.this, "添加成功！！", Toast.LENGTH_SHORT)
				// .show();
				// } else if (flag == 0)
				// Toast.makeText(Register2.this, "添加失败！", Toast.LENGTH_SHORT)
				// .show();

				registTask(usrname, pasword);
			}

		});

	}

	public void registTask(String name, String password) {
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("password", password);
		http.send(HttpMethod.POST, "http://www.liuminhua.cn/Math/RegistAction",
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(Register2.this, "网络问题，请重试", 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						String result = info.result;
						JSONObject jsonObject = JSONObject.parseObject(result);
						int errorCode = jsonObject.getInteger("errorCode");
						if (errorCode == 1000) {
							Toast.makeText(Register2.this, "注册成功", 0).show();
							Intent intent1=new Intent(Register2.this,SaveSecretActivity.class);//这里回到主窗口，一般回到信息窗口
							startActivity(intent1);
							Register2.this.finish();
						} else {
							Toast.makeText(Register2.this, "注册失败", 0).show();
						}
					}

				});
	}

	/***
	 * 数据库使用方法
	 * 
	 * @throws DbException
	 */
	public void db() {
		DbUtils dbUtils = DbUtils.create(this,  "Math");
		Chapter chapter = new Chapter();
		chapter.setChapterName("线性代数");
		try {
			dbUtils.save(chapter);
			List<Chapter> chapters = dbUtils.findAll(Chapter.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
