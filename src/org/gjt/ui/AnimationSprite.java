package org.gjt.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class AnimationSprite extends Tiled {
	private Bitmap[] bitmap;
	private boolean isLoop;
	private boolean isEnd;
	private int position;
	private long start_timer;
	private static final int ANIMA_TIMER=0X64;//每帧动画绘制所需要的时间
	public AnimationSprite(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas canvas) {
		if(bitmap==null)return;
		canvas.save();
		RectF rect = new RectF(x,y,width,height);
		canvas.drawBitmap(bitmap[position], null,rect, null);
		canvas.restore();
		if(!isEnd){
			long end = System.currentTimeMillis();
			if(end-start_timer>=ANIMA_TIMER){
				start_timer = end;
				position++;
				if(position>=bitmap.length-1){
					isEnd = true;
					//循环时，还原帧
					if(isLoop){
						isEnd = false;
						position = 0;
					}
				}
			}
		}
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	@Override
	public boolean touch(MotionEvent ev) {
		return this.isContains(ev.getX(), ev.getY());
	}

	public Bitmap[] getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap[] bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
