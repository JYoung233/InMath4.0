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
	
	//用于保存空格的位置
	List<Integer> spaceList = new ArrayList<Integer>();
	
	//所有非空的格子
	NumberList numberList = new NumberList();
	
	//用于保存每次操作时，已经升级过的格子
	List<Integer> changeList = new ArrayList<Integer>();
	
	//用于表示本次滑动是否有格子移动过
	boolean moved = false;
	
	GestureDetector gd = null;
	
	/**
	 * 图标数组
	 */
	private final int[] icons = { R.drawable.but_empty, R.drawable.but2,
			R.drawable.but4, R.drawable.but8, R.drawable.but16,
			R.drawable.but32, R.drawable.but64, R.drawable.but128,
			R.drawable.but256, R.drawable.but512, R.drawable.but1024,
			R.drawable.but2048, R.drawable.but4096 };
	
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("程序启动");
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
	 * 初始化界面，填充空白格，并随机生成两个数字格
	 */
	public void init(){
		System.out.println("初始化");
		scoreText.setText(score+"");
		topScoreText.setText(topScore.getTopScode()+"");
		
		//首先在16个各种都填上空白的图片
		for(int i=0;i<16;i++){
			View view = View.inflate(this, R.layout.item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			
			image.setBackgroundResource(icons[0]);
			spaceList.add(i);
			gridLayout.addView(view);	
		}
		
		//在界面中随机加入两个2或者4
		addRandomItem();
		addRandomItem();
		
		//反馈SDK初始化
		SixthBoxk.i(this, "011n1W00");
	}
	
	/**
	 * 从空格列表中随机获取位置
	 * @return 在gridLayout中的位置
	 */
	public int getRandomIndex(){
		Random random = new Random();
		if(spaceList.size()>0)
		 return random.nextInt(spaceList.size());
		else 
		 return -1;	
	}
	
	/**
	 * 在空白格中随机加入数字2或4
	 */
	public void addRandomItem(){
		int index = getRandomIndex();
		System.out.println("获取空白列随机位置"+index);
		if(index!=-1){
			System.out.println("随机生成数字 位置"+spaceList.get(index));
			//获取对应坐标所对应的View
			View view = gridLayout.getChildAt(spaceList.get(index));
			ImageView image = (ImageView) view.findViewById(R.id.image);
			//随机生成数字1或2
			int i = (int) Math.round(Math.random()+1);
			//将当前格子的图片置换为2或者4
			image.setBackgroundResource(icons[i]);	
		
			//在numList中加入该格子的信息
			numberList.add(spaceList.get(index), i);
			
			//在空白列表中去掉这个格子
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
			
	        // 参数解释：      
	        // e1：第1个ACTION_DOWN MotionEvent      
	        // e2：最后一个ACTION_MOVE MotionEvent      
	        // velocityX：X轴上的移动速度，像素/秒      
	        // velocityY：Y轴上的移动速度，像素/秒      
	        
	        // 触发条件 ：      
	        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒    
			
			if(e1.getX()-e2.getX()>100){
				System.out.println("向左");
				move(LEFT);
				return true;
			}else	if(e1.getX()-e2.getX()<-100){
				System.out.println("向右");
				move(RIGHT);
				return true;
			}else	if(e1.getY()-e2.getY()>100){
				System.out.println("向上");
				move(UP);
				return true;
			}else	if(e1.getY()-e2.getY()<-00){
				System.out.println("向下");
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
	 * 用于获取移动方向上下一个格子的位置
	 * @param index      当前格子的位置
	 * @param direction  滑动方向
	 * @return 如果在边界在返回-1
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
	 * 用于获取移动方向上前一个格子的位置
	 * @param index      当前格子的位置
	 * @param direction  滑动方向
	 * @return 如果在边界在返回-1
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
	 * 该方法用来交换当前格与目标空白格的位置
	 * @param thisIdx 当前格子的坐标
	 * @param nextIdx 目标空白格的坐标
	 */
	public void replace(int thisIdx, int nextIdx){
		moved = true;
		//获取当前格子的view，并将其置成空白格
		View thisView = gridLayout.getChildAt(thisIdx);
		ImageView image = (ImageView) thisView.findViewById(R.id.image);
		image.setBackgroundResource(icons[0]);
		
		//获取空白格的view,并将其背景置成当前格的背景
		View nextView = gridLayout.getChildAt(nextIdx);
		ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
		nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(thisIdx)]);
		
		//在空白格列表中，去掉目标格，加上当前格
		spaceList.remove(spaceList.indexOf(nextIdx));
		spaceList.add(thisIdx);
		
		//在数字格列表中，当前格的坐标置换成目标格的坐标
		numberList.changeIndex(thisIdx, nextIdx);
	}
	
	/**
	 * 刚方法用于合并在移动方向上两个相同的格子
	 * @param thisIdx 当前格子的坐标
	 * @param nextIdx 目标格子的坐标
	 */
	public void levelup(int thisIdx, int nextIdx){
		
		//一次移动中，每个格子最多只能升级一次
		if(!changeList.contains(nextIdx)){
			moved = true;
			//获取当前格子的view，并将其置成空白格
			View thisView = gridLayout.getChildAt(thisIdx);
			ImageView image = (ImageView) thisView.findViewById(R.id.image);
			image.setBackgroundResource(icons[0]);
			
			
			//获取目标格的view,并将其背景置成当前格升级后的背景
			View nextView = gridLayout.getChildAt(nextIdx);
			ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
			nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(nextIdx)+1]);
			
			//在空白格列表中加入当前格
			spaceList.add(thisIdx);
			//在数字列中删掉第一个格子
			numberList.remove(thisIdx);
			//将数字列表对应的内容升级
			numberList.levelup(nextIdx);
			
			changeList.add(nextIdx);
			
			//更新分数
			updateScore((int)Math.pow(2, numberList.getNumberByIndex(nextIdx)));

		}

	}
	
	/**
	 * 该方法为不同的滑动方向，执行不同的遍历顺序
	 * @param direction 滑动方向
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
		
		//如果本次滑动有格子移动过，则随机填充新的格子
		if(moved)
			addRandomItem();
		
		if(spaceList.size()==0){
			if(!numberList.hasChance())
				over();
		}

	}
	
	/**
	 * 该方法，为每个符合条件的格子执行变动的操作，如置换，升级等
	 * @param thisIdx     当前格子的坐标
	 * @param direction   滑动方向
	 */
	public void Change(int thisIdx,int direction){
		if(numberList.contains(thisIdx)){
						
			int nextIdx = getLast(thisIdx, direction);
			if(nextIdx == thisIdx){
				//不能移动
				return;
			}else if(spaceList.contains(nextIdx)){
				//存在可以置换的空白格
				replace(thisIdx,nextIdx);
			}else{				
				if(numberList.getNumberByIndex(thisIdx) == numberList.getNumberByIndex(nextIdx)){
					//可以合并
					levelup(thisIdx, nextIdx);
				}else{
					int before = getBefore(nextIdx, direction);
					if(before != thisIdx){
						//存在可以置换的空白格
						replace(thisIdx,before);
					}
				}
			}
		}
	}
	
	/**
	 * 用于获取移动方向上最后一个空白格之后的位置
	 * @param index      当前格子的坐标
	 * @param direction  移动方向
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
	 * 该方法用于更新分数
	 * @param add 新增的分数
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
			.setTitle("哎！结束了")
			.setMessage("游戏结束，您的本局的分数是"+score+"分，继续加油哦！")
			.setPositiveButton("重新开始",new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					reset();
				}
			})
			.setNegativeButton("结束游戏", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GameActivity.this.finish();
					
				}
			}).show();		
	}
	
	/**
	 * 清空界面，重新初始化
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
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
