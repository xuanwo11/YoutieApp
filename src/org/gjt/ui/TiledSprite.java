package org.gjt.ui;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class TiledSprite extends Layer {
	public TiledSprite(float x,float y,float width,float height,Bitmap bitmap){
		super(x, y, width, height);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	@Override
	public boolean touch(MotionEvent ev) {
		return super.touch(ev);
	}

}
