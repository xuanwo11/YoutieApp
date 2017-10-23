package com.hfp.youtie.ui;


import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.ui.base.BaseActivity;
import com.hfp.youtie.utils.LogUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-2-21
 * TODO 闪屏界面，根据指定时间进行跳转
 * 		在activity_splash.xml中加入background属性并传入图片资源ID即可
 */
public class SplashActivity extends BaseActivity {
	private RelativeLayout rootLayout;
	private static final long DELAY_TIME = 2000L;
	private static final int sleepTime = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		LogUtils.i(TAG,TAG + " Launched ！");
		//MobclickAgent.openActivityDurationTrack(UmengStat.IS_OPEN_ACTIVITY_AUTO_STAT);
		//FeedbackAgent agent = new FeedbackAgent(this);
		//agent.sync();
		redirectByTime();		
		/*if(sputil.getValue("isPushOn", true)){
			PushAgent mPushAgent = PushAgent.getInstance(mContext);
			mPushAgent.enable();
			LogUtils.i(TAG,"device_token:"+UmengRegistrar.getRegistrationId(mContext));
		}else{
			PushAgent mPushAgent = PushAgent.getInstance(mContext);
			mPushAgent.disable();
		}
		
		AdManager.getInstance(mContext).init("67daabfc8ffec9c7", "7748a02fe32d6532", false);
		OffersManager.getInstance(mContext);*/
	}
	
	/**
	 * 根据时间进行页面跳转
	 */
	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(MyApplication.getInstance().getCurrentUser()!=null){
					
					// ** 免登陆情况 加载所有本地群和会话
					//不是必须的，不加sdk也会自动异步去加载(不会重复加载)；
					//加上的话保证进了主页面会话和群组都已经load完毕
					
					
					redictToActivity(SplashActivity.this,MainActivity.class, null);
				}else{
					
					
				redictToActivity(SplashActivity.this, RegisterAndLoginActivity.class, null);
				}
				finish();
			}
		}, DELAY_TIME);
	}

}
