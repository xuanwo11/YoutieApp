package org.gjt.tank;

import org.gjt.ui.Layer;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Bullet extends Layer {
	private float rotate;
	private Paint paint;
	public Bullet(float x, float y, float width, float height) {
		super(x, y, width, height);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(4);
	}

	@Override
	public void draw(Canvas canvas) {
//		canvas.drawRect(x, y, width, height, paint);
		
		Matrix matrix = new Matrix();
		matrix.postTranslate(x, y);
		matrix.postRotate(rotate, x+bitmap.getWidth()/2, y+bitmap.getHeight()/2);
		canvas.drawBitmap(bitmap, matrix, null);
	}

	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

}
