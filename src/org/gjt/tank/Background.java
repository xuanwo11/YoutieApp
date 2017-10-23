package org.gjt.tank;

import java.util.ArrayList;
import java.util.List;

import org.gjt.ui.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	private int width;
	private int height;
	private int count;
	private static final int CEL_WIDTH = 60;//每个条的宽
	private Bitmap[] bitmap;
	private List<Sprite> sprite = new ArrayList<Sprite>();
	private static final  int SPEED=10;
	public Background(int width,int height){
		this.width = width;
		this.height =height;
		count = BitmapManager.getInstance().bg.getWidth()/CEL_WIDTH;
		bitmap = new Bitmap[count];
	}
	public void init(){
		for(int i=0;i<bitmap.length;i++){
			bitmap[i] = splitBitmap(BitmapManager.getInstance().bg, i*CEL_WIDTH, 0, 
					CEL_WIDTH, BitmapManager.getInstance().bg.getHeight());
			Sprite s = new Sprite(i*CEL_WIDTH, 0, i*CEL_WIDTH+CEL_WIDTH, height);
			s.setBitmap(bitmap[i]);
			sprite.add(s);
		}
	}
	public void draw(Canvas canvas) {
		canvas.save();
		for(int i=0;i<sprite.size();i++){
			sprite.get(i).draw(canvas);
		}
		canvas.restore();
	}
	
	public void logic(){
		for(int i=0;i<sprite.size();i++){
			Sprite s = sprite.get(i);
			s.setX(s.getX()-SPEED);
			s.setWidth(s.getWidth()-SPEED);
			if(s.getWidth()<0){
				sprite.remove(s);
				s.setX(sprite.get(sprite.size()-1).getWidth());
				s.setWidth(s.getX()+s.getBitmap().getWidth());
				sprite.add(s);
			}
		}
	}
	//切图片
	public Bitmap splitBitmap(Bitmap source,int left,int top,int right,int bottom){
		return Bitmap.createBitmap(source, left, top, right, bottom);
	}
}
