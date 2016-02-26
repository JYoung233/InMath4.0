package com.example.inmath2;

import com.example.inmath2.R;
import com.swm.mini2048.GameActivity;

import rDataBase.DBopenHelper;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;

@SuppressWarnings("unused")
public class MainActivity extends Activity {
	private TextView textviewone;
	private TextView textviewtwo;
	private TextView textviewthree;
	private TextView textviewfour;
	private TextView textviewfive;
	private TextView textviewsix;
	private Button select;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private EditText selectcontent;
	Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView config_hidden = (TextView) this.findViewById(R.id.config_hidden);
		config_hidden.requestFocus();
		textviewone = (TextView) findViewById(R.id.textview_main1);
		textviewtwo = (TextView) findViewById(R.id.textview_main2);
		textviewthree = (TextView) findViewById(R.id.textview_main3);
		textviewfour = (TextView) findViewById(R.id.textview_main4);
		select = (Button) findViewById(R.id.startselect);
		selectcontent = (EditText) findViewById(R.id.select1);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		Animation an= AnimationUtils.loadAnimation(this,R.anim.scalebutton );
		an.setFillAfter(true);
		an.startNow();
		Animation an1= AnimationUtils.loadAnimation(this,R.anim.scalebutton1 );
		an1.setFillAfter(true);
		an1.startNow();
		Animation an2= AnimationUtils.loadAnimation(this,R.anim.scalebutton2 );
		an2.setFillAfter(true);
		an2.startNow();
		button1.setAnimation(an1);
		button2.setAnimation(an2);
		button3.setAnimation(an);
		button4.setAnimation(an1);
		button5.setAnimation(an2);
		button6.setAnimation(an);
		button7.setAnimation(an1);
		button8.setAnimation(an2);
		button9.setAnimation(an);
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("��ʽ��",
						getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("���")
				.setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("����")
				.setContent(R.id.view3));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("��")
				.setContent(R.id.view4));
		TextView textview = (TextView) this.findViewById(R.id.text5);
		textview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						NewActivity.class);
				startActivity(intent1);

			}
		});
		TextView register1 = (TextView) this.findViewById(R.id.text1);
		register1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(MainActivity.this,
						SaveSecretActivity.class);
				startActivity(intent2);

			}
		});
		TextView collect=(TextView) this.findViewById(R.id.text2);
		collect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(MainActivity.this,
						CollectView.class);
				startActivity(intent2);

			}
		});
		
		textviewfive = (TextView) this.findViewById(R.id.text8);
		textviewfive.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent3 = new Intent(MainActivity.this,
						NoteMain.class);
				startActivity(intent3);

			}
		});
		textviewsix = (TextView) this.findViewById(R.id.text7);
		textviewsix.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent(MainActivity.this, Calculator.class);
				startActivity(intent4);

			}
		});
		textviewone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
				intent.putExtra("parentId", 1);
				startActivity(intent);

			}
		});
		textviewtwo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
				intent.putExtra("parentId", 2);
				startActivity(intent);

			}
		});
		textviewthree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
				intent.putExtra("parentId", 3);
				startActivity(intent);

			}
		});
		textviewfour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
				intent.putExtra("parentId", 4);
				startActivity(intent);

			}
		});
		TextView feedback = (TextView) this.findViewById(R.id.text4);
		feedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						Feedback.class);
				startActivity(intent);

			}
		});
		TextView about= (TextView) this.findViewById(R.id.text5);
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						Feedback.class);
				startActivity(intent);

			}
		});

		TextView textviewnine = (TextView) this.findViewById(R.id.text9);
		textviewnine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(MainActivity.this,
						GameActivity.class);
				startActivity(intent1);

			}
		});
		
		select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String contrast = null;
				String content = null;
				Intent intent3 = new Intent(MainActivity.this, Selectpage.class);
				content = selectcontent.getText().toString();

				if (content.indexOf("����") != -1
						|| content.indexOf("����������") != -1)
					contrast = "1";

				else if (content.indexOf("���Ǻ���") != -1
						|| content.indexOf("���Ǻ�����") != -1
						|| content.indexOf("����������") != -1)
					contrast = "2";
				else if (content.indexOf("�������") != -1)
					contrast = "3";
				else if (content.indexOf("΢��") != -1)
					contrast = "4";
				else if (content.indexOf("�޶�") != -1
						|| content.indexOf("�޶�") != -1)
					contrast = "5";
				else if (content.indexOf("��������") != -1)
					contrast = "6";
				else if (content.indexOf("����") != -1)
					contrast = "7";
				else if (content.indexOf("̩��") != -1)
					contrast = "8";
				else if (content.indexOf("��������") != -1)
					contrast = "9";
				else if (content.indexOf("��Ԫ����") != -1)
					contrast = "10";
				else if (content.indexOf("�ֲ�����") != -1)
					contrast = "11";
				else if (content.indexOf("������") != -1)
					contrast = "12";
				else if (content.indexOf("�������޺���") != -1)
					contrast = "13";
				else if (content.indexOf("ţ���������") != -1)
					contrast = "14";
				else if (content.indexOf("�����ֻ�Ԫ����") != -1)
					contrast = "15";
				else if (content.indexOf("�����ֲַ�����") != -1)
					contrast = "16";
				else if (content.indexOf("�����Ӽ�") != -1)
					contrast = "17";
				else if (content.indexOf("�����˷�") != -1)
					contrast = "18";
				else if (content.indexOf("����������") != -1
						|| content.indexOf("������") != -1)
					contrast = "19";
				else if (content.indexOf("����������") != -1
						|| content.indexOf("������") != -1)
					contrast = "20";
				else if (content.indexOf("������ϻ�") != -1
						|| content.indexOf("��ϻ�") != -1)
					contrast = "21";
				else if (content.indexOf("ֱ�߷���") != -1)
					contrast = "22";
				else if (content.indexOf("��������") != -1)
					contrast = "23";
				else if (content.indexOf("������") != -1)
					contrast = "24";
				else if (content.indexOf("������") != -1
						|| content.indexOf("�ݶ�") != -1)
					contrast = "25";
				else if (content.indexOf("��Ԫ����̩�չ�ʽ") != -1)
					contrast = "26";
				else if (content.indexOf("���ػ���") != -1)
					contrast = "27";
				else if (content.indexOf("���ֹ�ʽ") != -1
						|| content.indexOf("����") != -1)
					contrast = "28";
				else if (content.indexOf("���ػ���") != -1
						|| content.indexOf("�۴λ���") != -1)
					contrast = "29";
				else if (content.indexOf("��˹") != -1)
					contrast = "31";
				else if (content.indexOf("˹�п�") != -1)
					contrast = "32";
				else if (content.indexOf("����Ҷ") != -1)
					contrast = "33";
				else if (content.indexOf("����ʽ") != -1)
					contrast = "34";
				else if (content.indexOf("����") != -1)
					contrast = "35";
				else if (content.indexOf("��Ҷ˹") != -1)
					contrast = "36";
				else if (content.indexOf("0-1�ֲ�") != -1
						|| content.indexOf("01�ֲ�") != -1)
					contrast = "37";
				else if (content.indexOf("����ֲ�") != -1)
					contrast = "38";
				else if (content.indexOf("���ɷֲ�") != -1)
					contrast = "39";
				else if (content.indexOf("�����ηֲ�") != -1)
					contrast = "40";
				else if (content.indexOf("���ηֲ�") != -1)
					contrast = "41";
				else if (content.indexOf("���ȷֲ�") != -1)
					contrast = "42";
				else if (content.indexOf("ָ���ֲ�") != -1)
					contrast = "43";
				else if (content.indexOf("��̬�ֲ�") != -1)
					contrast = "44";
				else if (content.indexOf("����ͳ��ѧ") != -1)
					contrast = "45";
				else if (content.indexOf("����") != -1)
					contrast = "46";
				else if (content.indexOf("����") != -1)
					contrast = "47";
				else if (content.indexOf("���ϵ��") != -1)
					contrast = "48";
				else if (content.indexOf("��") != -1)
					contrast = "49";
				else if (content.indexOf("�б�ѩ�򲻵�ʽ") != -1)
					contrast = "50";
				else if (content.indexOf("�б�ѩ�����") != -1)
					contrast = "51";
				else if (content.indexOf("��Ŭ������") != -1)
					contrast = "52";
				else if (content.indexOf("���ɴ���") != -1)
					contrast = "53";
				else if (content.indexOf("���մ���") != -1)
					contrast = "54";
				else if (content.indexOf("�����ֲ�") != -1)
					contrast = "55";
				else if (content.indexOf("t�ֲ�") != -1)
					contrast = "56";
				else if (content.indexOf("F�ֲ�") != -1)
					contrast = "57";
				else
					contrast = "none";

				bundle.putString("keyname", contrast);
				intent3.putExtras(bundle);
				startActivity(intent3);

			}
		});
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "2");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "9");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "35");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "8");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "7");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "33");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "39");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "4");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		button9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Selectpage.class);
				bundle.putString("keyname", "3");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}