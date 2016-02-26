package com.swm.mini2048;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.inmath2.R;
import com.symbian.staticfeed.SixthBoxk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	
	final static int LEFT = -1;
	final static int RIGHT = 1;
	final static int UP = -4;
	final static int DOWN = 4;

	GridLayout gridLayout = null;
	TextView scoreText = null;
	TextView topScoreText =  null;
	
	int score = 0;
	TopScore topScore;
	
	//���ڱ���ո��λ��
	List<Integer> spaceList = new ArrayList<Integer>();
	
	//���зǿյĸ���
	NumberList numberList = new NumberList();
	
	//���ڱ���ÿ�β���ʱ���Ѿ��������ĸ���
	List<Integer> changeList = new ArrayList<Integer>();
	
	//���ڱ�ʾ���λ����Ƿ��и����ƶ���
	boolean moved = false;
	
	GestureDetector gd = null;
	
	/**
	 * ͼ������
	 */
	private final int[] icons = { R.drawable.but_empty, R.drawable.but2,
			R.drawable.but4, R.drawable.but8, R.drawable.but16,
			R.drawable.but32, R.drawable.but64, R.drawable.but128,
			R.drawable.but256, R.drawable.but512, R.drawable.but1024,
			R.drawable.but2048, R.drawable.but4096 };
	
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("��������");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		
		gridLayout = (GridLayout) findViewById(R.id.GridLayout1);
		scoreText = (TextView) findViewById(R.id.score);
		topScoreText = (TextView) findViewById(R.id.topScore);
		topScore = new TopScore(this);
		
		init();
		
		MygestureDetector mg = new MygestureDetector();

		gd = new GestureDetector(mg);
		gridLayout.setOnTouchListener(mg);
		gridLayout.setLongClickable(true);
		
	}
	/**
	 * ��ʼ�����棬���հ׸񣬲���������������ָ�
	 */
	public void init(){
		System.out.println("��ʼ��");
		scoreText.setText(score+"");
		topScoreText.setText(topScore.getTopScode()+"");
		
		//������16�����ֶ����Ͽհ׵�ͼƬ
		for(int i=0;i<16;i++){
			View view = View.inflate(this, R.layout.item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			
			image.setBackgroundResource(icons[0]);
			spaceList.add(i);
			gridLayout.addView(view);	
		}
		
		//�ڽ����������������2����4
		addRandomItem();
		addRandomItem();
		
		//����SDK��ʼ��
		SixthBoxk.i(this, "011n1W00");
	}
	
	/**
	 * �ӿո��б��������ȡλ��
	 * @return ��gridLayout�е�λ��
	 */
	public int getRandomIndex(){
		Random random = new Random();
		if(spaceList.size()>0)
		 return random.nextInt(spaceList.size());
		else 
		 return -1;	
	}
	
	/**
	 * �ڿհ׸��������������2��4
	 */
	public void addRandomItem(){
		int index = getRandomIndex();
		System.out.println("��ȡ�հ������λ��"+index);
		if(index!=-1){
			System.out.println("����������� λ��"+spaceList.get(index));
			//��ȡ��Ӧ��������Ӧ��View
			View view = gridLayout.getChildAt(spaceList.get(index));
			ImageView image = (ImageView) view.findViewById(R.id.image);
			//�����������1��2
			int i = (int) Math.round(Math.random()+1);
			//����ǰ���ӵ�ͼƬ�û�Ϊ2����4
			image.setBackgroundResource(icons[i]);	
		
			//��numList�м���ø��ӵ���Ϣ
			numberList.add(spaceList.get(index), i);
			
			//�ڿհ��б���ȥ���������
			spaceList.remove(index);
		
		}
	}
	
	public class MygestureDetector implements OnGestureListener,OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub		
			return gd.onTouchEvent(event);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
	        // �������ͣ�      
	        // e1����1��ACTION_DOWN MotionEvent      
	        // e2�����һ��ACTION_MOVE MotionEvent      
	        // velocityX��X���ϵ��ƶ��ٶȣ�����/��      
	        // velocityY��Y���ϵ��ƶ��ٶȣ�����/��      
	        
	        // �������� ��      
	        // X�������λ�ƴ���FLING_MIN_DISTANCE�����ƶ��ٶȴ���FLING_MIN_VELOCITY������/��    
			
			if(e1.getX()-e2.getX()>100){
				System.out.println("����");
				move(LEFT);
				return true;
			}else	if(e1.getX()-e2.getX()<-100){
				System.out.println("����");
				move(RIGHT);
				return true;
			}else	if(e1.getY()-e2.getY()>100){
				System.out.println("����");
				move(UP);
				return true;
			}else	if(e1.getY()-e2.getY()<-00){
				System.out.println("����");
				move(DOWN);
				return true;
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	/**
	 * ���ڻ�ȡ�ƶ���������һ�����ӵ�λ��
	 * @param index      ��ǰ���ӵ�λ��
	 * @param direction  ��������
	 * @return ����ڱ߽��ڷ���-1
	 */
	public int getNext(int index,int direction){
		
		int y = index/4;
		int x = index%4;
		
		if(x==3 && direction==RIGHT)	
			return -1;			
		if(x==0 && direction==LEFT)
			return -1;
		if(y==0 && direction==UP)
			return -1;
		if(y==3 && direction==DOWN)
			return -1;	
		return index+direction;	
	}
	
	/**
	 * ���ڻ�ȡ�ƶ�������ǰһ�����ӵ�λ��
	 * @param index      ��ǰ���ӵ�λ��
	 * @param direction  ��������
	 * @return ����ڱ߽��ڷ���-1
	 */
	public int getBefore(int index,int direction){
		
		int y = index/4;
		int x = index%4;
		
		if(x==0 && direction==RIGHT)	
			return -1;			
		if(x==3 && direction==LEFT)
			return -1;
		if(y==3 && direction==UP)
			return -1;
		if(y==0 && direction==DOWN)
			return -1;	
		return index-direction;	
	}
	

	/**
	 * �÷�������������ǰ����Ŀ��հ׸��λ��
	 * @param thisIdx ��ǰ���ӵ�����
	 * @param nextIdx Ŀ��հ׸������
	 */
	public void replace(int thisIdx, int nextIdx){
		moved = true;
		//��ȡ��ǰ���ӵ�view���������óɿհ׸�
		View thisView = gridLayout.getChildAt(thisIdx);
		ImageView image = (ImageView) thisView.findViewById(R.id.image);
		image.setBackgroundResource(icons[0]);
		
		//��ȡ�հ׸��view,�����䱳���óɵ�ǰ��ı���
		View nextView = gridLayout.getChildAt(nextIdx);
		ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
		nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(thisIdx)]);
		
		//�ڿհ׸��б��У�ȥ��Ŀ��񣬼��ϵ�ǰ��
		spaceList.remove(spaceList.indexOf(nextIdx));
		spaceList.add(thisIdx);
		
		//�����ָ��б��У���ǰ��������û���Ŀ��������
		numberList.changeIndex(thisIdx, nextIdx);
	}
	
	/**
	 * �շ������ںϲ����ƶ�������������ͬ�ĸ���
	 * @param thisIdx ��ǰ���ӵ�����
	 * @param nextIdx Ŀ����ӵ�����
	 */
	public void levelup(int thisIdx, int nextIdx){
		
		//һ���ƶ��У�ÿ���������ֻ������һ��
		if(!changeList.contains(nextIdx)){
			moved = true;
			//��ȡ��ǰ���ӵ�view���������óɿհ׸�
			View thisView = gridLayout.getChildAt(thisIdx);
			ImageView image = (ImageView) thisView.findViewById(R.id.image);
			image.setBackgroundResource(icons[0]);
			
			
			//��ȡĿ����view,�����䱳���óɵ�ǰ��������ı���
			View nextView = gridLayout.getChildAt(nextIdx);
			ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
			nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(nextIdx)+1]);
			
			//�ڿհ׸��б��м��뵱ǰ��
			spaceList.add(thisIdx);
			//����������ɾ����һ������
			numberList.remove(thisIdx);
			//�������б��Ӧ����������
			numberList.levelup(nextIdx);
			
			changeList.add(nextIdx);
			
			//���·���
			updateScore((int)Math.pow(2, numberList.getNumberByIndex(nextIdx)));

		}

	}
	
	/**
	 * �÷���Ϊ��ͬ�Ļ�������ִ�в�ͬ�ı���˳��
	 * @param direction ��������
	 */
	public void move(int direction){
		
		moved = false;
		
		changeList.clear();
		
		numberList.printLog();
		
		switch(direction){
		case RIGHT:
			for(int y=0;y<4;y++){
				for(int x=2;x>=0;x--){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		case LEFT:
			for(int y=0;y<4;y++){
				for(int x=1;x<=3;x++){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		case UP:
			for(int x=0;x<4;x++){
				for(int y=1;y<=3;y++){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;	
		case DOWN:
			for(int x=0;x<4;x++){
				for(int y=2;y>=0;y--){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		}
		
		//������λ����и����ƶ��������������µĸ���
		if(moved)
			addRandomItem();
		
		if(spaceList.size()==0){
			if(!numberList.hasChance())
				over();
		}

	}
	
	/**
	 * �÷�����Ϊÿ�����������ĸ���ִ�б䶯�Ĳ��������û���������
	 * @param thisIdx     ��ǰ���ӵ�����
	 * @param direction   ��������
	 */
	public void Change(int thisIdx,int direction){
		if(numberList.contains(thisIdx)){
						
			int nextIdx = getLast(thisIdx, direction);
			if(nextIdx == thisIdx){
				//�����ƶ�
				return;
			}else if(spaceList.contains(nextIdx)){
				//���ڿ����û��Ŀհ׸�
				replace(thisIdx,nextIdx);
			}else{				
				if(numberList.getNumberByIndex(thisIdx) == numberList.getNumberByIndex(nextIdx)){
					//���Ժϲ�
					levelup(thisIdx, nextIdx);
				}else{
					int before = getBefore(nextIdx, direction);
					if(before != thisIdx){
						//���ڿ����û��Ŀհ׸�
						replace(thisIdx,before);
					}
				}
			}
		}
	}
	
	/**
	 * ���ڻ�ȡ�ƶ����������һ���հ׸�֮���λ��
	 * @param index      ��ǰ���ӵ�����
	 * @param direction  �ƶ�����
	 * @return
	 */
	public int getLast(int thisIdx, int direction){
		 int nextIdx = getNext(thisIdx, direction);
		 if(nextIdx < 0)
			 return thisIdx;
		 else{
			 if(spaceList.contains(nextIdx))
				 return getLast(nextIdx, direction);
			 else
				 return nextIdx;
		 }		
	}
	
	/**
	 * �÷������ڸ��·���
	 * @param add �����ķ���
	 */
	public void updateScore(int add){
		score += add;
		scoreText.setText(score+"");
		if(score>topScore.getTopScode())
			topScore.setTopScode(score);
		topScoreText.setText(topScore.getTopScode()+"");
	}
	
	public void over(){
		new AlertDialog.Builder(this)
			.setTitle("����������")
			.setMessage("��Ϸ���������ı��ֵķ�����"+score+"�֣���������Ŷ��")
			.setPositiveButton("���¿�ʼ",new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					reset();
				}
			})
			.setNegativeButton("������Ϸ", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GameActivity.this.finish();
					
				}
			}).show();		
	}
	
	/**
	 * ��ս��棬���³�ʼ��
	 */
	public void reset(){
		spaceList.clear();
		numberList.clear();
		score = 0;
		gridLayout.removeAllViews();
		init();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private long exitTime = 0;
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}else{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    }
}
