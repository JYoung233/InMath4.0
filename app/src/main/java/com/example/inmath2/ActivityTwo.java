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

	private View moreView; // ���ظ���ҳ��

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
		listView.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		moreView.setVisibility(View.GONE);
		adpter = new ExamAdpter(exams, this);
		listView.setAdapter(adpter);
		listView.setOnScrollListener(this); // ����listview�Ĺ����¼�
		// ���μ�������
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
		lastItem = firstVisibleItem + visibleItemCount - 1; // ��1����Ϊ������˸�addFooterView
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Log.i(TAG, "scrollState=" + scrollState);
		// �����������ǣ������һ��item�����������ݵ�����ʱ�����и���
		if (lastItem == adpter.getCount()
				&& scrollState == this.SCROLL_STATE_IDLE) {
			Log.i(TAG, "������ײ�");
			if (adpter.getCount() == 0) {
				// û�����ݣ���һ�μ���ʧ���˼���
				getData(0, LIMINT_COUNT);
			} else {
				getData(adpter.getCount(), LIMINT_COUNT);
			}
		}
	}

	// ÿ�μ��ص�����
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
						Toast.makeText(ActivityTwo.this, "��������ʧ�ܣ�������", 0)
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
							// ��ȡ���ݳɹ�
							JSONArray jsonDataFromNet = infoJson
									.getJSONArray("model");
							List<Pager> dataFromNet = JSONArray.parseArray(
									jsonDataFromNet.toJSONString(), Pager.class);
							if (dataFromNet != null && !dataFromNet.isEmpty()) {
								exams.addAll(dataFromNet);
								adpter.notifyDataSetChanged();
								if (dataFromNet.size() < count) {
									// ��ʾ����ʾû�и�����Բ�ѯ��
									Toast.makeText(ActivityTwo.this, "ľ�и������ݣ�",
											3000).show();
									listView.removeFooterView(moreView);
								}
							} else {
								// ��ʾ����ʾû�и�����Բ�ѯ��
								Toast.makeText(ActivityTwo.this, "ľ�и������ݣ�",
										3000).show();
								listView.removeFooterView(moreView);
							}
						}
					}

				});
	}
}
