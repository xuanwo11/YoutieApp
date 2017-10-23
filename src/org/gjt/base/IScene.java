package org.gjt.base;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IScene {
	
	public void load();
	
	public void draw(Canvas canvas);
	
	public void logic();
	
	public void touchDown(MotionEvent event);
	
	public void touchUp(MotionEvent event);
	
	public void touchMove(MotionEvent event);
	
	public void distory();
	
}
