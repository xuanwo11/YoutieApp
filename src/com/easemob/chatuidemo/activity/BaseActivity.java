/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.easemob.chatuidemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.easemob.applib.controller.HXSDKHelper;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.utils.Constant;
import com.hfp.youtie.utils.Sputil;
//import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity implements OnSharedPreferenceChangeListener{

	protected static String TAG ;
	protected MyApplication mMyApplication;
	protected Sputil sputil;
	protected Resources mResources;
	protected Context mContext;//////////////////
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        TAG = this.getClass().getSimpleName();
		initConfigure();
    }
    private void initConfigure() {
		mContext = this;
		if(null == mMyApplication){
			mMyApplication = MyApplication.getInstance();
		}
		mMyApplication.addActivity(this);
		if(null == sputil){
			sputil = new Sputil(this, Constant.PRE_NAME);
		}
		sputil.getInstance().registerOnSharedPreferenceChangeListener(this);
		mResources = getResources();
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		//可用于监听设置参数，然后作出响应
	}

	/**
	 * Activity跳转
	 * @param context
	 * @param targetActivity
	 * @param bundle
	 */
	public void redictToActivity(Context context,Class<?> targetActivity,Bundle bundle){
		Intent intent = new Intent(context, targetActivity);
		if(null != bundle){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
        HXSDKHelper.getInstance().getNotifier().reset();
        
        // umeng
      //  MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
      //  MobclickAgent.onPause(this);
    }


    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
