package org.gjt.base;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Surface extends SurfaceView implements Runnable, Callback {
	private Thread drawThread;
	private boolean flag;
	private IScene scene;
	private Canvas canvas;
	private long fps = 60;
	private boolean isFPS=true;
	private Paint paint;
	private long current;
	public Surface(Context context) {
		super(context);
		this.getHolder().addCallback(this);
		this.setKeepScreenOn(true);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(12);
		paint.setTextSize(16);
	}

	public synchronized void setScene(IScene s){
		if(s==null)return;
		if(scene!=null)
			scene.distory();
		scene = null;
		scene = s;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		scene.load();
		drawThread = new Thread(this);
		drawThread.start();
		logicThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = true;
		while(flag){
			try {
				drawThread.join();
				logicThread.join();
				flag = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(scene!=null)scene.distory();
	}

	@Override
	public void run() {
		while(!flag){
			long start = System.currentTimeMillis();
			try {
				canvas = getHolder().lockCanvas();
				if(canvas!=null&&scene!=null){
					scene.draw(canvas);
					showFPS();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(canvas!=null)
					getHolder().unlockCanvasAndPost(canvas);
			}
			long end = System.currentTimeMillis();
			if(end -start<fps){
				//所用的时间
				current = end -start;
				try {
					//睡眠
					Thread.sleep(fps-current);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*********显示帧时间，和内存状况***********/
	private  void showFPS(){
		if(isFPS){
			long max=Runtime.getRuntime().maxMemory()/1024/1024;
			long free = Runtime.getRuntime().freeMemory()/1024/1024;
			canvas.save();
			canvas.drawText("FPS:"+current, 5,15, paint);
			canvas.drawText("Max:"+max+" M", 5,15+12+5, paint);
			canvas.drawText("Free:"+free+" M", 5, 40+5, paint);
			canvas.restore();
		}
	}
	public Thread logicThread = new Thread(){
		public void run() {
			while(!flag){
				if(scene!=null)
					scene.logic();
				try {
					Thread.sleep(fps);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(scene==null)return true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			scene.touchDown(event);
			break;
		case MotionEvent.ACTION_UP:
			scene.touchUp(event);
			break;
		case MotionEvent.ACTION_MOVE:
			scene.touchMove(event);
			break;
		}
		return true;
	}

	public long getFps() {
		return fps;
	}

	public void setFps(long fps) {
		this.fps = fps;
	}
	
	
}
