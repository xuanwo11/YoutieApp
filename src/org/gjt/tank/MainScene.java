package org.gjt.tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gjt.base.IScene;
import org.gjt.base.Surface;
import org.gjt.ui.AnimationSprite;
import org.gjt.ui.Sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;

public class MainScene implements IScene {
	private Context context;
	private Surface view;
	private int mWidth,mHeight;
	private Background background;
	private Tank tank;
	private List<Bullet> bullet = new ArrayList<Bullet>();
	public static final float BULLET_SPEED=10;//子弹的速度
	private static final float TANK_SPEED=3;//我方坦克的速度
	private boolean isShootBullet;//是否发射子弹
	private List<Enemy> enemy = new ArrayList<Enemy>();//敌人坦克
	private List<AnimationSprite> explode = new ArrayList<AnimationSprite>();
	private List<Sprite> goods = new ArrayList<Sprite>();
	private Paint paint ;
	private float life;
	private List<Enemy> flyEnemy = new ArrayList<Enemy>();
	private int count;//击落的坦克和飞碟数量
	public MainScene(Surface view,Context context){
		this.context = context;
		this.view = view;
		mWidth = context.getResources().getDisplayMetrics().widthPixels;
		mHeight = context.getResources().getDisplayMetrics().heightPixels;
		paint = new Paint();
	}
	@Override
	public void load() {
		BitmapManager.getInstance().loadSource(context);
		background = new Background(mWidth, mHeight);
		background.init();
		tank = new Tank(mWidth*10/100, mHeight*80/100, mWidth*10/100+106,mHeight*80/100+62);
		life = 100;
	}

	@Override
	public void draw(Canvas canvas) {
		background.draw(canvas);
		//我方子弹
		for(int i=0;i<bullet.size();i++){
			bullet.get(i).draw(canvas);
		}
		//敌人---坦克
		for (int i = 0; i < enemy.size(); i++) {
			enemy.get(i).draw(canvas);
		}
		//敌人--飞碟
		for (int i = 0; i < flyEnemy.size(); i++) {
			flyEnemy.get(i).draw(canvas);
		}
		//我方坦克
		tank.draw(canvas);
		//爆炸特效
		for (int i = 0; i < explode.size(); i++) {
			explode.get(i).draw(canvas);
		}
		//物品
		for (int i = 0; i < goods.size(); i++) {
			goods.get(i).draw(canvas);
		}
		
		paint.reset();
		
		paint.setColor(0x70ffff00);
		canvas.drawRect(0, 0, mWidth, 30, paint);
		paint.setColor(0xf0ff0000);
		canvas.drawRect(0, 0, mWidth*life/100, 30, paint);
		
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		paint.setStrokeWidth(4);
		canvas.drawText("歼敌："+count,("歼敌："+count).length() , mHeight-18, paint);
	}
	private long bullet_start;
	private long tank_start;
	private long fly_start;
	private long goods_start;
	private boolean isGameOver;
	@Override
	public void logic() {
		//背景
		background.logic();
		//我方坦克
		tankLogic();
		//敌人坦克
		enemyLogic();
		//飞碟
		flyEnemyLogic();
		//子弹与坦克碰撞检测
		isCollision();
		//物品
		goodsLogic();
		
		//爆炸特效的帧动画播放完毕后，移除
		for (int i = 0; i < explode.size(); i++) {
			if(explode.get(i).isEnd()){
				explode.remove(i);
			}
		}
		
		if(life<0&&!isGameOver){
			//游戏结束标志
			isGameOver = true;
			GameOver scene = new GameOver(context, view);
			scene.load();//载入初始数据
			//交换场景
			view.setScene(scene);
		}
	}
	
	private void flyEnemyLogic() {
		long end = System.currentTimeMillis();
		if(end - fly_start>5000){
			fly_start = end;
			float x = getTankPosition(0, mWidth/2/BitmapManager.getInstance().fly.getWidth());
			Enemy t = new Enemy(mWidth*50/100+x*BitmapManager.getInstance().fly.getWidth(),
					-BitmapManager.getInstance().fly.getHeight(),
					mWidth*50/100+x*BitmapManager.getInstance().fly.getWidth()
					+BitmapManager.getInstance().fly.getWidth(),
					0);
			t.setBitmap(BitmapManager.getInstance().fly);
			t.setBulletBitmap(BitmapManager.getInstance().bullet3);
			t.setRotate(45);
			flyEnemy.add(t);
		}
		
		//更新坦克的位置
		for(Enemy t:flyEnemy){
			t.logic(mWidth,mHeight);
			t.setX(t.getX()-TANK_SPEED);
			t.setWidth(t.getWidth()-TANK_SPEED);
				
			if(t.isUp()){
				t.setY(t.getY()-TANK_SPEED);
				t.setHeight(t.getHeight()-TANK_SPEED);
			}else{
				t.setY(t.getY()+TANK_SPEED);
				t.setHeight(t.getHeight()+TANK_SPEED);
			}
//			Log.i("TAG", "fly:x="+t.getX()+",y="+t.getY()+",width="+t.getWidth()+",height="+t.getHeight());
			
			//如果已经mHeight*90/100差陆了，向上飞
			if(t.getHeight()>mHeight*90/100){
				t.setUp(true);
			}
			//如果已经到mHeight*15/100的高度向下飞
			if(t.isUp()&&t.getY()<mHeight*15/100&&t.getX()>0)
				t.setUp(false);
		}
		

		//坦克是否超过出屏幕
		for(int i=0;i<flyEnemy.size();i++){
			Enemy t = flyEnemy.get(i);
			if(t.getWidth()<0){
				flyEnemy.remove(t);
			}
		}
	}
	private void goodsLogic() {
		//碰撞
		for(Sprite s : goods){
			if(s.isIntersect(tank)){
				goods.remove(s);
				if(life<100)
					life+=10;
			}
		}
		//修改
		for(Sprite s : goods){
			s.setX(s.getX()-2);
			s.setWidth(s.getWidth()-2);
		}
		//删除
		for(Sprite s : goods){
			if(s.getWidth()<0){
				goods.remove(s);
			}
		}
		
		//增加物品
		long end = System.currentTimeMillis();
		if(end - goods_start>30000){
			goods_start = end;
			Sprite g = new Sprite(mWidth,mHeight*80/100,
					mWidth+BitmapManager.getInstance().blood.getWidth(),
					mHeight*80/100+BitmapManager.getInstance().blood.getHeight());
			g.setBitmap(BitmapManager.getInstance().blood);
			goods.add(g);
			g= null;
		}
	}
	private void isCollision(){
		//坦克和坦克的子弹
		for(int i=0;i<enemy.size();i++){
			Enemy t = enemy.get(i);
			for(int j=0;j<bullet.size();j++){
				Bullet b = bullet.get(j);
				//我方子弹击中敌人坦克时
				if(b.isIntersect(t)){
					AnimationSprite anim = new AnimationSprite(t.getX(),t.getY(),
							t.getX()+BitmapManager.getInstance().explode1[0].getWidth(),
							t.getY()+BitmapManager.getInstance().explode1[0].getHeight());
					anim.setBitmap(BitmapManager.getInstance().explode1);
					explode.add(anim);
					enemy.remove(t);
					bullet.remove(b);
					count++;
				}
				
				//双方子弹对撞
				if(t.isCollision(b)){
					AnimationSprite anim = new AnimationSprite(b.getX(), b.getY(),
							b.getX()+15+BitmapManager.getInstance().explode2[0].getWidth(), 
							b.getY()+10+BitmapManager.getInstance().explode2[0].getHeight());
					anim.setBitmap(BitmapManager.getInstance().explode2);
					explode.add(anim);
					bullet.remove(b);
				}
			}
			//敌人坦克与我方坦克相撞时
			if(t.isIntersect(tank)){
				AnimationSprite anim = new AnimationSprite(t.getX(),t.getY(),
						t.getX()+BitmapManager.getInstance().explode1[0].getWidth(),
						t.getY()+BitmapManager.getInstance().explode1[0].getHeight());
				anim.setBitmap(BitmapManager.getInstance().explode1);
				explode.add(anim);
				anim = null;
				enemy.remove(t);
				//掉血
				life -=10;
				count++;
			}
			//坦克的子弹是否击中我方坦克
			if(t.isCollision(tank)){
				AnimationSprite anim = new AnimationSprite(tank.getWidth()-15, tank.getY()+15,
						tank.getWidth()-15+BitmapManager.getInstance().explode2[0].getWidth(), 
						tank.getY()+15+BitmapManager.getInstance().explode2[0].getHeight());
				anim.setBitmap(BitmapManager.getInstance().explode2);
				explode.add(anim);
				//掉血
				life -=10;
			}
		}
		
		
		for(int i=0;i<flyEnemy.size();i++){
			Enemy t = flyEnemy.get(i);
			for(int j=0;j<bullet.size();j++){
				Bullet b = bullet.get(j);
				//我方子弹击中敌人飞碟时
				if(b.isIntersect(t)){
					AnimationSprite anim = new AnimationSprite(t.getX(),t.getY(),
							t.getX()+BitmapManager.getInstance().explode1[0].getWidth(),
							t.getY()+BitmapManager.getInstance().explode1[0].getHeight());
					anim.setBitmap(BitmapManager.getInstance().explode1);
					explode.add(anim);
					flyEnemy.remove(t);
					bullet.remove(b);
					count++;
				}
				
				//双方子弹对撞
				if(t.isCollision(b)){
					AnimationSprite anim = new AnimationSprite(b.getX(), b.getY(),
							b.getX()+15+BitmapManager.getInstance().explode2[0].getWidth(), 
							b.getY()+10+BitmapManager.getInstance().explode2[0].getHeight());
					anim.setBitmap(BitmapManager.getInstance().explode2);
					explode.add(anim);
					bullet.remove(b);
				}
			}
			
			//飞蝶与我方坦克相撞
			if(t.isIntersect(tank)){
				AnimationSprite anim = new AnimationSprite(t.getX(),t.getY(),
						t.getX()+BitmapManager.getInstance().explode1[0].getWidth(),
						t.getY()+BitmapManager.getInstance().explode1[0].getHeight());
				anim.setBitmap(BitmapManager.getInstance().explode1);
				explode.add(anim);
				flyEnemy.remove(t);
				count++;
			}
			
			//飞碟的子弹是否与我方坦克相撞
			if(t.isCollision(explode,tank)){
				//掉血
				life -=10;
			}
			
			//碰撞到地面的子弹
			t.isCollision(explode, mHeight*90/100);

		}
		
	}
	
	private void enemyLogic(){
		long end = System.currentTimeMillis();
		if(end - tank_start>3500){
			tank_start = end;
			int index = (int) (Math.random()*100%BitmapManager.getInstance().tank.length);
			float y = getTankPosition(0, 3);
			Enemy t = new Enemy(mWidth,
					mHeight*75/100+y*10,
					mWidth+BitmapManager.getInstance().tank[index].getWidth(),
					mHeight*75/100+y*10+BitmapManager.getInstance().tank[index].getHeight());
			t.setBitmap(BitmapManager.getInstance().tank[index]);
			t.setBulletBitmap(BitmapManager.getInstance().bullet2);
			enemy.add(t);
		}
		
		//更新坦克的位置
		for(Enemy t:enemy){
			t.logic(mWidth,mHeight);
			t.setX(t.getX()-TANK_SPEED);
			t.setWidth(t.getWidth()-TANK_SPEED);
		}
		

		//坦克是否超过出屏幕
		for(Enemy t:enemy){
			if(t.getWidth()<0){
				enemy.remove(t);
			}
		}
	}
	private float getTankPosition(float y1,float y2){
		return Math.abs(new Random().nextInt()%(y2-y1)+y1);
	}
	private float downX,downY;
	@Override
	public void touchDown(MotionEvent event) {
		downX = event.getX();
		downY = event.getY();
	}

	@Override
	public void touchUp(MotionEvent event) {
		if(event.getX()==downX&&downY==event.getY()){
			isShootBullet = true;
		}
	}

	@Override
	public void touchMove(MotionEvent event) {
		//GUN上升
		if(downY-event.getY()>120){
			tank.gunUp();
		}//下降
		else if(event.getY()-downY>120){
			tank.gunDown();
		}
		//向后退
		if(downX-event.getX()>120){
			if(tank.getX()>0){
				tank.setX(tank.getX()-1);
				tank.setWidth(tank.getWidth()-1);
			}
		}//向前进
		else if(event.getX()-downX>120){
			if(tank.getWidth()+TANK_SPEED<mWidth){
				tank.setX(tank.getX()+1);
				tank.setWidth(tank.getWidth()+1);
			}
		}
	}

	private void tankLogic(){
		// 更新我方子弹的位置
		for (int i = 0; i < bullet.size(); i++) {
			Bullet b = bullet.get(i);
			double degress = Math.toRadians(b.getRotate());
			// double degress = Math.abs(Math.toRadians(tank.getRotate()));
			float y = (float) (Math.sin(degress) * BULLET_SPEED);
			float x = (float) (Math.cos(degress) * BULLET_SPEED);
			b.setX(b.getX() + x);
			b.setWidth(b.getWidth() + x);
			b.setY(b.getY() + y);
			b.setHeight(b.getHeight() + y);
			Log.i("TEST", "bullet:x=" + b.getX() + ",y=" + b.getY());

		}

		// 检测我方子弹是否已经超出屏幕以外
		for (int i = 0; i < bullet.size(); i++) {
			Bullet b = bullet.get(i);
			if (b.getX() > mWidth || b.getHeight() < 0 || b.getY() > mHeight
					|| b.getWidth() < 0) {
				bullet.remove(b);
			}
		}

		// 发射子弹
		if (isShootBullet) {
			long end = System.currentTimeMillis();
			// 增加子弹
			if (end - bullet_start > 800) {
				bullet_start = end;
				// gun w=45
				double radians = Math.toRadians(tank.getRotate());
				float x = (float) (Math.sin(radians) * 20);
				float y = (float) (Math.cos(radians) * 20);
				Log.i("TAG", "tank--->x=" + x + ",y=" + y);
				Log.i("TAG","tank121--->x=" + tank.getGunX() + ",y="+ tank.getGunY());
				x += tank.getGunX();
				y += tank.getGunY();
				Log.i("TAG", "tank--->>>x=" + x + ",y=" + y);
				Bullet b = new Bullet(x	- BitmapManager.getInstance().bullet1.getWidth(),
						y- BitmapManager.getInstance().bullet1.getHeight(),
						x, y);
				b.setBitmap(BitmapManager.getInstance().bullet1);
				b.setRotate(tank.getRotate());
				bullet.add(b);
			}
			// 发射子弹完成
			isShootBullet = false;
		}
	}
	@Override
	public void distory() {

	}

}
