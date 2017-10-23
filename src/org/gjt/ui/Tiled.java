package org.gjt.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Tiled {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	public Tiled(float x,float y,float width,float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract void draw(Canvas canvas);
	
	public abstract boolean touch(MotionEvent ev);
	
	
	
	public boolean isContains(float x,float y){
		RectF rect = new RectF(x,y,width,height);
		return rect.contains(x,y);
	}
	public boolean isIntersect(Tiled tiled){
		RectF rect = new RectF(x,y,width,height);
		RectF dst = new RectF(tiled.getX(),tiled.getY(),
				tiled.getWidth(),tiled.getHeight());
		return rect.intersect(dst);
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public void logger(String msg){
		Log.i("TAG", msg);
	}
	public float getW() {
		return width-x;
	}
	public float getH() {
		return height-y;
	}
}
