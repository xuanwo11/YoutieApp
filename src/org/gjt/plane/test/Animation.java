package org.gjt.plane.test;

import android.graphics.Bitmap;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Animation {
	//上一帧播放时间
	private long mLastPlayTime=0;
	//播放当前帧的ID
	private int mPlayID = 0;
	//动画frame数量
	private int mFrameCount =0;
	//用于储存动画资源图片
	private Bitmap[] mFrameBitmap;
	//是否循环播放
	private boolean mIsLoop;
	//播放结束
	private boolean mIsEnd;
	//动画播放间隙时间
	private static final int ANIM_TIME=100;
	/********************
	 * 构造方法
	 * @param frameBitmap 帧动画
	 * @param isLoop 是否循环播放
	 */
	public Animation(Bitmap[] frameBitmap,boolean isLoop){
		mFrameCount = frameBitmap.length;
		mFrameBitmap = frameBitmap;
		mIsLoop = isLoop;
	}
	
	//重置动画
	public void reset(){
		mLastPlayTime = 0;
		mPlayID = 0;
		mIsEnd = false;
	}
	
	public void drawFrame(Canvas canvas,Paint paint,float x,float y,int frameId){
		canvas.drawBitmap(mFrameBitmap[frameId], x,y, paint);
	}
	
	public void drawAnimation(Canvas canvas,Paint paint,float x,float y){
		
		if(!mIsEnd){
			canvas.drawBitmap(mFrameBitmap[mPlayID], x, y, paint);
			long time = System.currentTimeMillis();
			if(time-mLastPlayTime>ANIM_TIME){
				mPlayID++;
				mLastPlayTime = time;
				if(mPlayID>= mFrameCount){
					//标志动画播放结束
					mIsEnd = true;
					if(mIsLoop){
						//设置循环播放
						mIsEnd = false;
						mPlayID = 0;
					}
				}
			}
		}	
	}
}
