package com.miaotian;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BombGame extends Activity {
	/** Called when the activity is first created. */

	public static BombGame instence;
	public MainView myView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// �ޱ���
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��

//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// ��Ļ����

		instence = this;
		myView = new MainView(this);
		
		setContentView(myView);
	}

	@Override
	protected void onPause() {
		myView.gameState = myView.GAME_START;
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}