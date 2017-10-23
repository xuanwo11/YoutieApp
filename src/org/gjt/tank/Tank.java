package org.gjt.tank;

import org.gjt.ui.Tiled;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Tank extends Tiled {
	private Matrix matrix_gun;
	private Matrix matrix1;
	private Matrix matrix2;
	private Matrix matrix3;
	private Matrix matrix4;
	private Matrix matrix5;
	private float degrees;
	private float rotate;
	private Paint paint ;
	public Tank(float x, float y, float width, float height) {
		super(x, y, width, height);
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(4);
//		bun = new Bullet();
	}

	@Override
	public void draw(Canvas canvas) {
		Matrix matrix = new Matrix();
		//车身
		matrix.postTranslate(x, y);
		canvas.drawBitmap(BitmapManager.getInstance().tank_body, matrix,null);
		
		//底盘
		matrix.postTranslate(7, BitmapManager.getInstance().tank_body.getHeight()*76/100);
		canvas.drawBitmap(BitmapManager.getInstance().tank_chassis,	matrix,null);
		
		//阴影
		matrix.postTranslate(-7, BitmapManager.getInstance().tank_chassis.getHeight()*80/100);
		canvas.drawBitmap(BitmapManager.getInstance().tank_shadow,	matrix,null);
		
		matrix1 = new Matrix();
		//轮子1
		matrix1.postTranslate(x+15, y+BitmapManager.getInstance().tank_body.getHeight()*80/100);
		matrix1.postRotate(degrees,x+15+8, y+BitmapManager.getInstance().tank_body.getHeight()*80/100+8);
		canvas.drawBitmap(BitmapManager.getInstance().tank_wheel1, matrix1, null);
		
		matrix2 = new Matrix();
		//轮子2
		matrix2.postTranslate(x+35, y+BitmapManager.getInstance().tank_body.getHeight()*95/100);
		matrix2.postRotate(degrees, x+35+6, y+BitmapManager.getInstance().tank_body.getHeight()*95/100+6);
		canvas.drawBitmap(BitmapManager.getInstance().tank_wheel2, matrix2, null);
		
		matrix3 = new Matrix();
		//轮子3
		matrix3.postTranslate(x+50, y+BitmapManager.getInstance().tank_body.getHeight()*95/100);
		matrix3.postRotate(degrees, x+50+6, y+BitmapManager.getInstance().tank_body.getHeight()*95/100+6);
		canvas.drawBitmap(BitmapManager.getInstance().tank_wheel2, matrix3, null);
		
		matrix4 = new Matrix();
		//轮子4
		matrix4.postTranslate(x+65, y+BitmapManager.getInstance().tank_body.getHeight()*95/100);
		matrix4.postRotate(degrees, x+65+6, y+BitmapManager.getInstance().tank_body.getHeight()*95/100+6);
		canvas.drawBitmap(BitmapManager.getInstance().tank_wheel2, matrix4, null);
		
		matrix5 = new Matrix();
		//轮子5
		matrix5.postTranslate(x+81, y+BitmapManager.getInstance().tank_body.getHeight()*83/100);
		matrix5.postRotate(degrees, x+81+8, y+BitmapManager.getInstance().tank_body.getHeight()*83/100+8);
		canvas.drawBitmap(BitmapManager.getInstance().tank_wheel1, matrix5, null);
		
		//炮口
		matrix_gun = new Matrix();
		matrix_gun.postTranslate(x+BitmapManager.getInstance().tank_body.getWidth()*65/100, 
				y+BitmapManager.getInstance().tank_body.getHeight()*10/100);
		matrix_gun.postRotate(rotate, x+BitmapManager.getInstance().tank_body.getWidth()*65/100, 
				y+BitmapManager.getInstance().tank_body.getHeight()*10/100+7.5f);
		canvas.drawBitmap(BitmapManager.getInstance().tank_gun, matrix_gun, null);
		
		degrees=degrees+45>360?0:degrees+45;
	}
	
	public float getGunX(){
		return x+BitmapManager.getInstance().tank_body.getWidth()*65/100+BitmapManager.getInstance().tank_gun.getWidth();
	}
	public float getGunY(){
		return y+BitmapManager.getInstance().tank_body.getHeight()*1.5f/100;
	}
	public float getRotate() {
		return rotate;
	}

	public void gunUp(){
		if(rotate>-45)
			rotate--;
	}

	public void gunDown(){
		if(rotate<6){
			rotate++;
		}
	}
	@Override
	public boolean touch(MotionEvent ev) {
		return false;
	}

}
