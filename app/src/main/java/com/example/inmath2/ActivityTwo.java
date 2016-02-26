package com.example.inmath2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.inmath2.adapter.ExamAdpter;
import com.example.inmath2.model.Pager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class ActivityTwo extends Activity implements OnScrollListener {
	private int parentId = 1;

	private static final String TAG = ActivityTwo.class.getSimpleName();

	private ListView listView;

	private View moreView; // 加载更多页面

	private int lastItem;

	private List<Pager> exams = new ArrayList<Pager>();

	private ExamAdpter adpter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_one);

		parentId = getIntent().getIntExtra("parentId", 2);

		listView = (ListView) findViewById(R.id.listView);
		moreView = getLayoutInflater().inflate(R.layout.load, null);
		listView.addFooterView(moreView); // 添加底部view，一定要在setAdapter之前添加，否则会报错。
		moreView.setVisibility(View.GONE);
		adpter = new ExamAdpter(exams, this);
		listView.setAdapter(adpter);
		listView.setOnScrollListener(this); // 设置listview的滚动事件
		// 初次加载数据
		getData(0, LIMINT_COUNT);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int postion, long id) {
				Pager pager = (Pager) adpter.getItem(postion);

				if (!TextUtils.isEmpty(pager.getPath())) {
					Intent intent = new Intent(ActivityTwo.this,
							PaperShow.class);
					intent.putExtra("name", pager.getName());
					intent.putExtra("path", pager.getPath());
					startActivity(intent);
				} else {
					Intent intent = new Intent(ActivityTwo.this,
							ActivityTwo.class);
					intent.putExtra("parentId", pager.getId());
					startActivity(intent);
				}
			}
		});

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Log.i(TAG, "firstVisibleItem=" + firstVisibleItem
				+ "\nvisibleItemCount=" + visibleItemCount + "\ntotalItemCount"
				+ totalItemCount);
		//
		lastItem = firstVisibleItem + visibleItemCount - 1; // 减1是因为上面加了个addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "scrollState=" + scrollState);
		// 下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
		if (lastItem == adpter.getCount()
				&& scrollState == this.SCROLL_STATE_IDLE) {
			Log.i(TAG, "拉到最底部");
			if (adpter.getCount() == 0) {
				// 没有数据，第一次加载失败了加载
				getData(0, LIMINT_COUNT);
			} else {
				getData(adpter.getCount(), LIMINT_COUNT);
			}
		}
	}

	// 每次加载的数量
	final int LIMINT_COUNT = 5;

	public void getData(int first, final int count) {
		moreView.setVisibility(View.VISIBLE);
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("parentId", parentId + "");
		params.addBodyParameter("first", first + "");
		params.addBodyParameter("count", count + "");
		httpUtils.send(HttpMethod.POST,
				"http://www.liuminhua.cn/Math/PagerAction", params,
				new RequestCallBack<String>() {

					@Override
					public void onCancelled() {
						super.onCancelled();
					}

					@Override
					public void onFailure(HttpException e, String arg1) {
						Log.d(ActivityTwo.class.getName(), e.getMessage());
						Toast.makeText(ActivityTwo.this, "网络连接失败，请重试", 0)
								.show();
						moreView.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						moreView.setVisibility(View.GONE);
						String info = responseInfo.result;
						JSONObject infoJson = JSONObject.parseObject(info);
						int errorCode = infoJson.getInteger("errorCode");
						if (errorCode == 1000) {
							// 获取数据成功
							JSONArray jsonDataFromNet = infoJson
									.getJSONArray("model");
							List<Pager> dataFromNet = JSONArray.parseArray(
									jsonDataFromNet.toJSONString(), Pager.class);
							if (dataFromNet != null && !dataFromNet.isEmpty()) {
								exams.addAll(dataFromNet);
								adpter.notifyDataSetChanged();
								if (dataFromNet.size() < count) {
									// 提示或显示没有更多可以查询了
									Toast.makeText(ActivityTwo.this, "木有更多数据！",
											3000).show();
									listView.removeFooterView(moreView);
								}
							} else {
								// 提示或显示没有更多可以查询了
								Toast.makeText(ActivityTwo.this, "木有更多数据！",
										3000).show();
								listView.removeFooterView(moreView);
							}
						}
					}

				});
	}
}
