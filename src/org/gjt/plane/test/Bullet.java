package org.gjt.plane.test;


import java.util.logging.Logger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Bullet {
	//子弹的X轴速度
	private static final int BULLET_SETP_X=3;
	//子弹的Y轴速度
	private static final int BULLET_STEP_Y=15;
	//子弹图片的宽度
	private static final int BULLET_WIDTH=40;
	//子弹的X，Y坐标
	public float posX;
	public float posY;
	
	//子弹动画
	private  Animation mAnimation;
	//是否更新绘制子弹
	private boolean mFacus = false;
	
	public Bullet(Bitmap[] bitmap){
		mAnimation = new Animation(bitmap,true);
	}
	
	//初始化坐标
	public void init(float x,float y){
		posX = x;
		posY = y;
		mFacus = true;
	}
	
	public void drawBullet(Canvas canvas,Paint paint){
		if(mFacus)
			mAnimation.drawAnimation(canvas, paint, posX, posY);
	}
	
	
	public void updateBullet(){
		if(mFacus)
			posY -= BULLET_STEP_Y;
		Log.i("TAG", "posY="+posY);
	}
}
