package org.gjt.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Layer extends Tiled {
	protected boolean isMove;
	public boolean isMove() {
		return isMove;
	}

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	protected Bitmap bitmap;
	
	public Layer(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		RectF rect = new RectF(x,y,width,height);
		if(bitmap!=null)
			canvas.drawBitmap(bitmap, null, rect, null);
		else
			canvas.drawRect(rect, null);
		canvas.restore();
		rect = null;
	}

	@Override
	public boolean touch(MotionEvent ev) {
		return this.isContains(ev.getX(), ev.getY());
	}

}
