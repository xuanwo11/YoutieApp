package org.gjt.tank;

import org.gjt.ui.AnimationSprite;
import org.gjt.ui.Layer;

import android.graphics.Canvas;

public class Explode extends Layer {
	private AnimationSprite anim1;
	private AnimationSprite anim2;
	private boolean flag;
	public Explode(float x, float y, float width, float height) {
		super(x, y, width, height);
		anim2 = new AnimationSprite(x,y,
				x+BitmapManager.getInstance().explode1[0].getWidth(),
				y+BitmapManager.getInstance().explode1[0].getHeight());
		anim2.setBitmap(BitmapManager.getInstance().explode1);
		anim1 = new AnimationSprite(x+50, y+50,
				x+50+BitmapManager.getInstance().explode2[0].getWidth(), 
				y+50+BitmapManager.getInstance().explode2[0].getHeight());
		anim1.setBitmap(BitmapManager.getInstance().explode2);
	}

	@Override
	public void draw(Canvas canvas) {
		if(!flag){
			anim1.draw(canvas);
			if(anim1.isEnd()){
				flag = true;
			}
		}else{
			anim2.draw(canvas);
		}
	}

	public boolean isEnd() {
		return anim2.isEnd();
	}
	
}
