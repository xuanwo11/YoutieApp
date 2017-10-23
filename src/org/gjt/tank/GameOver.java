package org.gjt.tank;

import org.gjt.base.IScene;
import org.gjt.base.Surface;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class GameOver implements IScene {
	private Context context;
	private Surface view;
	private boolean isStart;
	private boolean isLoad;
	public GameOver(Context context,Surface view){
		this.context = context;
		this.view = view;
	}
	@Override
	public void load() {

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(BitmapManager.getInstance().gameover, 0,0, null);
	}

	@Override
	public void logic() {
		if(isStart&&!isLoad){
			isLoad = true;//加载状态标志
			isStart = false;//关闭开始标志
			MainScene game = new MainScene(view, context);
			game.load();//载入初始数据
			view.setScene(game);
		}
	}

	@Override
	public void touchDown(MotionEvent event) {
		isStart = true;
	}

	@Override
	public void touchUp(MotionEvent event) {

	}

	@Override
	public void touchMove(MotionEvent event) {

	}

	@Override
	public void distory() {

	}

}
