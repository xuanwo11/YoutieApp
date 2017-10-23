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

import com.bmob.lostfound.AddActivity;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;

import com.easemob.exceptions.EaseMobException;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.proxy.UserProxy;
import com.hfp.youtie.proxy.UserProxy.ILoginListener;
import com.hfp.youtie.proxy.UserProxy.IResetPasswordListener;
import com.hfp.youtie.proxy.UserProxy.ISignUpListener;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.Constant;
import com.hfp.youtie.utils.LogUtils;
import com.hfp.youtie.utils.StringUtils;
import com.hfp.youtie.view.DeletableEditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity implements ISignUpListener {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;
	DeletableEditText userEmailInput;////////////////////////
	UserProxy userProxy;////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Bmob.initialize(this, "e06192b54b3f21069ab16fbdee95de6c");
		userNameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
		confirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		userEmailInput = (DeletableEditText)findViewById(R.id.user_email_input);////////////////
		 userProxy = new UserProxy(mContext);
	}

	/**
	 * 注册
	 * 
	 * @param view
	 */

	public void register(View view) {
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(userEmailInput.getText())){///////////////////////////
			userEmailInput.setShakeAnimation();
			Toast.makeText(this, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!StringUtils.isValidEmail(userEmailInput.getText())){
			userEmailInput.setShakeAnimation();
			Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
			return;
		}
		//userProxy.setOnSignUpListener(this);
		User user = new User();
		user.setUsername(userNameEditText.getText().toString().trim());
		user.setPassword(passwordEditText.getText().toString().trim());
		user.setEmail(userEmailInput.getText().toString().trim());
		user.setSex(Constant.SEX_FEMALE);
		user.setSignature("暂未发表心情~");
		userProxy.setOnSignUpListener(this);
		LogUtils.i(TAG,"register begin....");
		userProxy.signUp(userNameEditText.getText().toString().trim(),
		passwordEditText.getText().toString().trim(), 
				userEmailInput.getText().toString().trim());
		//////////////////////////////////////
	
	}
	
	public void back(View view) {
		finish();
	}
	@Override
	public void onSignUpSuccess() {
		// TODO Auto-generated method stub		
		//ActivityUtil.show(this, "注册成功");
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage(getResources().getString(R.string.Is_the_registered));
			pd.show();

			new Thread(new Runnable() {
				public void run() {
					try {
						// 调用sdk注册方法
						EMChatManager.getInstance().createAccountOnServer(username, pwd);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								
							    User user = new User();
								user.setUsername(username);
								user.setPassword(pwd);
								user.setEmail(userEmailInput.getText().toString().trim());
								user.setSex(Constant.SEX_FEMALE);
								user.setSignature("暂未发表心情~");
								user.save(RegisterActivity.this);
								// 保存用户名
								MyApplication.getInstance().setUserName(username);
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), 0).show();
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode=e.getErrorCode();
								if(errorCode==EMError.NONETWORK_ERROR){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_ALREADY_EXISTS){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.UNAUTHORIZED){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.ILLEGAL_USER_NAME){
								    Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();

		}
		LogUtils.i(TAG,"register successed！");		
	}

	@Override
	public void onSignUpFailure(String msg) {
		// TODO Auto-generated method stub
		//ActivityUtil.show(this, "注册失败:邮箱已被注册或请确认网络是否已连接~");
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
		//TextUtils.isEmpty(userEmailInput.getText());	
		LogUtils.i(TAG,"register failed！"+msg);
		
	}
	

}
