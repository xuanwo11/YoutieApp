package org.gjt.plane.test;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Enemy {
	//敌人存活状态
	public static final int ENEMY_ALIVE_STATE=0;
	//敌人死亡状态
	public static final int ENEMY_DEATH_STATE=1;
	//敌人行走的Y轴速度
	public static final int ENEMY_STEP_Y=5;
	//子弹图片的宽度
	public static final int BULLET_WIDTH=40;
	//子弹的XY坐标
	public float posX;
	public float posY;
	//活着时的动画
	private Animation alive;
	//死了时的动画
	private Animation dead;
	public int mAnimState=0;//播放动画状态
	private boolean mFacus=false;//是否更新绘制敌人
	public int mState = 0;//敌人状态
	
	public Enemy(Bitmap[] enemy,Bitmap[] dead){
		alive = new Animation(enemy, true);
		this.dead = new Animation(dead, false);
	}
	public void init(float x,float y){
		posX=x;
		posY = y;
		mFacus = true;
		mAnimState =ENEMY_ALIVE_STATE;
		mState = ENEMY_ALIVE_STATE;
		alive.reset();
		alive.reset();
	}
	
	public void drawEnemy(Canvas canvas,Paint paint){
		if(mFacus){
			//绘制精灵活着的状态
			if(mAnimState == ENEMY_ALIVE_STATE){
				alive.drawAnimation(canvas, paint, posX, posY);
			//绘制精灵死亡的状态
			}else if(mAnimState==ENEMY_DEATH_STATE){
				dead.drawAnimation(canvas, paint, posX, posY);
			}
		}
	}
	//更新敌人状态
	public void updateEnemy(){
		if(mFacus){
			posY+=ENEMY_STEP_Y;//更新精灵的Y坐标位置
			Log.i("TAG", "Enemy is dead.posY="+posY);
			//如果精灵处在死亡状态时，就不在更新和绘制精灵
			if(mAnimState==ENEMY_DEATH_STATE){
				mFacus = false;
				mState = ENEMY_DEATH_STATE;
				Log.i("TAG", "Enemy is dead.");
			}
		}
	}
}
