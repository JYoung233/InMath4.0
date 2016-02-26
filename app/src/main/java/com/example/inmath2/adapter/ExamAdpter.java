package com.example.inmath2.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.inmath2.R;
//import com.lidroid.xutils.BitmapUtils;
import com.example.inmath2.model.Pager;
import com.lidroid.xutils.BitmapUtils;

public class ExamAdpter extends BaseAdapter {
	private List<Pager> exams = new ArrayList<Pager>();
	private Context context;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;

	public ExamAdpter(List<Pager> exams, Context context) {
		super();
		this.exams = exams;
		//bitmapUtils = new BitmapUtils(context);
		this.context = context;
		inflater = LayoutInflater.from(context);
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return exams.size();
	}

	@Override
	public Object getItem(int arg0) {
		return exams.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if(arg1 == null){
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.exam_item, null);
			holder.examName = (TextView) arg1.findViewById(R.id.examName);
			holder.image = (ImageView) arg1.findViewById(R.id.icon);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		Pager exam = (Pager) getItem(arg0);
		holder.examName.setText(exam.getName());
		//网络获取图片
		bitmapUtils.display(holder.image, exam.getIcon());
//		if(arg0 % 2 ==0){
//			双行背景白色 字黑色，单行背景黑色  字白色只是为了好看点，没其他用
//			arg1.setBackgroundColor(0xffffffff);
//			holder.examName.setTextColor(0xff222222);
//		}else{
//			arg1.setBackgroundColor(0xff222222);
//			holder.examName.setTextColor(0xffffffff);
//		}
		return arg1;
	}
	
	class ViewHolder{
		TextView examName;
		ImageView image;
	}
}
