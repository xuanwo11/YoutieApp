package org.gjt.plane.test;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewActivity extends Activity {
	private AnimView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics display = this.getResources().getDisplayMetrics();
		view = new AnimView(this,display.widthPixels, display.heightPixels);
		setContentView(view);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			view.updateTouchEvent(event.getX(), event.getY(), true);
			break;
		case MotionEvent.ACTION_UP:
			view.updateTouchEvent(event.getX(), event.getY(), false);
			break;
		}
		return super.onTouchEvent(event);
	}
	public class AnimView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
		//屏幕的宽高
		private int mScreenWidth;
		private int mScreenHeight;
		//游戏主菜单状态
		private static final int STATE_GAME=0;
		//游戏状态
		private int mState = STATE_GAME;
		private Paint mPaint = null;
		//记录两张背景图片时时更新的Y坐标
		private int mBitmapPosY0;
		private int mBitmapPosY1;
		//飞机动画帧数
		private final static int PLAN_ANIM_COUNT=6;
		//子弹动画帧数
		private final static int BULLET_ANIM_COUNT=4;
		//子弹动画帧数
		private final static int BULLET_POOL_COUNT=15;
		//飞机移动步长
		private final static int PLAN_STEP=10;
		//每过500毫秒发射一颗子弹
		private final static int PLAN_TIME=500;
		//子弹图片向上偏移量处理触屏
		private final static int BULLET_UP_OFFSET=40;
		//子弹图片向左偏移量处理触屏
		private final static int BULLET_LEFT_OFFSET=5;	
		//敌人对象的数量
		private final static int ENEMY_POOL_COUNT=5; 
		//敌人行走动画帧数
		private final static int ENEMY_ALIVE_COUNT=1;
		//敌人死亡动画帧数
		private final static int ENEMY_DEATH_COUNT=6;
		//敌人飞机偏移量
		private final static int ENEMY_POS_OFF=65;
		//游戏主线程
		private Thread mThread;
		//线程循环标志
		private boolean mIsRunning;
		private SurfaceHolder holder;
		private Canvas canvas;
		private Context context;
		
		//飞机动画
		private Animation mAircraft;
		//飞机在屏幕中的坐标
		private float mAirPosX;
		private float mAirPosY;
		//敌人类
		private Enemy[] mEnemy;
		//子弹
		private Bullet[] mBullet;
		//初始化发射子弹ID升序
		private int mSendId;
		//上一颗子弹发射的时间
		private Long mSendTime=0L;
		//手指在屏幕触摸的坐标
		private float mTouchPosX=0;
		private float mTouchPosY=0;
		//标志手指在屏幕触摸中
		private boolean mTouching;
		
		public AnimView(Context context,int width,int height) {
			super(context);
			this.context = context;
			mPaint = new Paint();
			mScreenHeight = height;
			mScreenWidth = width;
			holder = this.getHolder();
			holder.addCallback(this);
			this.setFocusable(true);
			BitmapManager.getInsance().loadSource(this.getContext());
			init();
			setGameState(STATE_GAME);
		}
		
		private void drawGame(Canvas canvas){
			switch (mState) {
			case STATE_GAME:
				drawBackground(canvas);
				updateBackground();
				break;
			}
		}
		//更新数据
		private void updateBackground() {
			mBitmapPosY0+=10;
			mBitmapPosY1+=10;
			if(mBitmapPosY0==mScreenHeight)
				mBitmapPosY0=-mScreenHeight;
			if(mBitmapPosY1==mScreenHeight)
				mBitmapPosY1 = -mScreenHeight;
			
			//点击事件
			if(mTouching){
				if(mAirPosX<mTouchPosX)
					mAirPosX += PLAN_STEP;
				else
					mAirPosX-= PLAN_STEP;
				
				if(mAirPosY<mTouchPosY)
					mAirPosY+=PLAN_STEP;
				else
					mAirPosY-=PLAN_STEP;
				
				if(Math.abs(mAirPosX-mTouchPosX)<=PLAN_STEP){
					mAirPosX = mTouchPosX;
				}
				if(Math.abs(mAirPosY-mTouchPosY)<=PLAN_STEP){
					mAirPosY = mTouchPosY;
				}
			}
			//更新子弹动画 
			for(int i=0;i<mBullet.length;i++){
				mBullet[i].updateBullet();
			}
			for(int i=0;i<mEnemy.length;i++){
				mEnemy[i].updateEnemy();
				//敌机死亡或者敌机超过屏幕还未死亡重置坐标
				if(mEnemy[i].mState==Enemy.ENEMY_DEATH_STATE||
						mEnemy[i].posY>=mScreenHeight){
					//把屏分5块，敌机的贴图的宽剩以所在的块数
					mEnemy[i].init(utilRandom(0,ENEMY_POOL_COUNT)*ENEMY_POS_OFF, 0);
				}
			}
			
			//根据时间初始化为发射的子弹
			if(mSendId<BULLET_POOL_COUNT){
				long now = System.currentTimeMillis();
				if(now-mSendId>=PLAN_TIME){
					mBullet[mSendId].init(mAirPosX-BULLET_LEFT_OFFSET, mAirPosY-BULLET_UP_OFFSET);
					mSendTime = now;
					mSendId++;
				}
			}else{
				mSendId =0;
			}
			
			//更新子弹与敌人的碰撞
			collision();
		}

		private void collision() {
			for(int i=0;i<mBullet.length;i++){
				for(int j=0;j<mEnemy.length;j++){
					if(mBullet[i].posX>=mEnemy[j].posX
							&&mBullet[i].posX<=mEnemy[j].posX+20
							&&mBullet[i].posY>=mEnemy[j].posY
							&&mBullet[i].posY<=mEnemy[j].posY+20){
						mEnemy[j].mAnimState = Enemy.ENEMY_DEATH_STATE;
					}
				}
			}
		}
		public void updateTouchEvent(float x,float y,boolean touching){
			switch (mState) {
			case STATE_GAME:
				mTouching = touching;
				mTouchPosX = x;
				mTouchPosY=y;
				break;
			}
		}
		private int utilRandom(int x1, int x2) {
			return Math.abs(new Random().nextInt())%(x2-x1)+x1;
		}

		private void drawBackground(Canvas canvas) {
			//绘制游戏地图
			canvas.drawBitmap(BitmapManager.getInsance().bg1, 0, mBitmapPosY0, null);
			canvas.drawBitmap(BitmapManager.getInsance().bg2, 0, mBitmapPosY1, null);
			//绘制飞机
			mAircraft.drawAnimation(canvas, mPaint,mAirPosX, mAirPosY);
		
			//绘制子弹
			for(int i=0;i<mBullet.length;i++){
				mBullet[i].drawBullet(canvas, mPaint);
			}
			
			//绘制敌人动画
			for(int i=0;i<mEnemy.length;i++){
				mEnemy[i].drawEnemy(canvas, mPaint);
			}
		}

		//设置游戏的状态
		private void setGameState(int stateGame) {
			mState = stateGame;
		}

		private void init(){
			//创建主角飞机动画对象
			mAircraft = new Animation(BitmapManager.getInsance().plane, true);
			//第一张图的位置在(0,0),第二张图片(0,-mScreenHeight)
			mBitmapPosY0 = 0;
			mBitmapPosY1 = -mScreenHeight;
			//初始化飞机的坐标位置
			mAirPosX = 150;
			mAirPosY = 400;
			
			//创建敌人
			mEnemy = new Enemy[ENEMY_POOL_COUNT];
			for(int i=0;i<mEnemy.length;i++){
				mEnemy[i] = new Enemy(BitmapManager.getInsance().enemy,
						BitmapManager.getInsance().enemyDead);;
				mEnemy[i].init(i*ENEMY_POS_OFF,0);
			}
			
			//创建子弹对象
			mBullet = new Bullet[BULLET_POOL_COUNT];
			for(int i=0;i<mBullet.length;i++){
				mBullet[i] = new Bullet(BitmapManager.getInsance().bullet);
			}
			//加载初始化完成时间
			mSendTime = System.currentTimeMillis();
		}
		@Override
		public void run() {
			while (mIsRunning) {
				synchronized (holder) {
					Canvas canvas = holder.lockCanvas();
					drawGame(canvas);
					holder.unlockCanvasAndPost(canvas);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mIsRunning  = true;
			mThread = new Thread(this);
			mThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			mIsRunning = false;
			mThread.interrupt();
		}
		
	}
	public void logger(String msg){
		Log.i("TAG", msg);
	}
}
