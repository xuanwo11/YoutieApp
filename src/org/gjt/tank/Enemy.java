package org.gjt.tank;

import java.util.ArrayList;
import java.util.List;

import org.gjt.ui.AnimationSprite;
import org.gjt.ui.Layer;
import org.gjt.ui.Sprite;
import org.gjt.ui.Tiled;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy extends Layer {
	public List<Sprite> bullet = new ArrayList<Sprite>();
	protected long start;
	protected float rotate;
	private boolean isUp;
	private Bitmap bulletBitmap;
	public Enemy(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		for(int i=0;i<bullet.size();i++){
			bullet.get(i).draw(canvas);
		}
	}
	
	public void logic(float mWidth,float mHeight){
		//更新位置
		for(Sprite b : bullet){
//			logger("enemy:bullet:x="+b.getX()+",y="+b.getY());
			if(b.getRotate()>0){
//				b.setY(b.)
				double radian = Math.toRadians(b.getRotate());
				float y = (float) (Math.sin(radian)*MainScene.BULLET_SPEED);
				float x = (float) (Math.cos(radian)*MainScene.BULLET_SPEED);
				logger("fly::x="+x+",y="+y);
				b.setX(b.getX()-x);
				b.setY(b.getY()+y);
				b.setWidth(b.getWidth()-x);
				b.setHeight(b.getHeight()+y);
			}else{
				b.setX(b.getX()-MainScene.BULLET_SPEED);
				b.setWidth(b.getWidth()-MainScene.BULLET_SPEED);
			}
		}
		
		//删除经超出屏幕外的子弹
		for(int i=0;i<bullet.size();i++){
			Sprite b = bullet.get(i);
			//是否已经超出屏幕区域
			if(b.getWidth()<0){
				bullet.remove(b);
				continue;
			}
		}
		
		//增加发子弹
		long end = System.currentTimeMillis();
		if(end - start>5000){
			start = end;//很关键，不要忘记了
			Sprite b = new Sprite(x-bulletBitmap.getWidth(),
					y+bulletBitmap.getHeight()/3*2,
					x,
					y+bulletBitmap.getHeight()/3*2+bulletBitmap.getHeight());
			b.setRotate(rotate);
			b.setBitmap(bulletBitmap);
			bullet.add(b);
		}
	}

	//子弹是否与目标碰撞
	public boolean isCollision(Tiled tank){
		for(Sprite b :bullet){
			if(b.isIntersect(tank)){
				bullet.remove(b);
				b = null;
				return true;
			}
		}
		return false;
	}
	
	public boolean isCollision(List<AnimationSprite> list,float position){
		for(Sprite b :bullet){
			if(b.getHeight()>position){
				AnimationSprite anim = new AnimationSprite(b.getX(), b.getY(),
						b.getX()+BitmapManager.getInstance().explode2[0].getWidth(), 
						b.getY()+BitmapManager.getInstance().explode2[0].getHeight());
				anim.setBitmap(BitmapManager.getInstance().explode2);
				list.add(anim);
				anim = null;
				bullet.remove(b);
				b = null;
				return true;
			}
		}
		return false;
	}
	
	public boolean isCollision(List<AnimationSprite> list,Tiled pos){
		for(Sprite b :bullet){
			if(b.isIntersect(pos)){
				AnimationSprite anim = new AnimationSprite(b.getX(), b.getY(),
						b.getX()+BitmapManager.getInstance().explode2[0].getWidth(), 
						b.getY()+BitmapManager.getInstance().explode2[0].getHeight());
				anim.setBitmap(BitmapManager.getInstance().explode2);
				list.add(anim);
				anim = null;
				bullet.remove(b);
				b = null;
				return true;
			}
		}
		return false;
	}
	
	
	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public Bitmap getBulletBitmap() {
		return bulletBitmap;
	}

	public void setBulletBitmap(Bitmap bulletBitmap) {
		this.bulletBitmap = bulletBitmap;
	}

}
