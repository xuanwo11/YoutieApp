package com.hfp.youtie.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;

import com.easemob.chatuidemo.DemoHXSDKHelper;



import com.easemob.chatuidemo.db.UserDao;
import com.easemob.exceptions.EaseMobException;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.proxy.UserProxy;
import com.hfp.youtie.proxy.UserProxy.ILoginListener;
import com.hfp.youtie.proxy.UserProxy.IResetPasswordListener;
import com.hfp.youtie.proxy.UserProxy.ISignUpListener;
import com.hfp.youtie.ui.base.BasePageActivity;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.Constant;
import com.hfp.youtie.utils.LogUtils;
import com.hfp.youtie.utils.StringUtils;
import com.hfp.youtie.view.DeletableEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-3-13
 * TODO
 */

public class RegisterAndLoginActivity extends BasePageActivity 
	implements OnClickListener,ILoginListener,ISignUpListener,IResetPasswordListener{

	ActionBar actionbar;
	TextView loginTitle;
	TextView registerTitle;
	TextView resetPassword;
	
	DeletableEditText userNameInput;
	DeletableEditText userPasswordInput;
	DeletableEditText userEmailInput;
	
	Button registerButton;
	SmoothProgressBar progressbar;
	UserProxy userProxy;
	public static final int REQUEST_CODE_SETNICK = 1;//////////////////////
	DeletableEditText usernameEditText;
	DeletableEditText passwordEditText;

	private boolean progressShow;
	private boolean autoLogin = false;

	private String currentUsername;
	private String currentPassword;
	DeletableEditText userNameEditText;
	DeletableEditText passwordEditText1;
	DeletableEditText confirmPwdEditText;
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();///////////////////////////////////////
	private enum UserOperation{
		LOGIN,REGISTER,RESET_PASSWORD
	}
	
	UserOperation operation = UserOperation.LOGIN;
	
	@Override
	protected void setLayoutView() {
		// TODO Auto-generated method stub
		// 如果用户名密码都有，直接进入主页面
				/*if (DemoHXSDKHelper.getInstance().isLogined()) {
					autoLogin = true;
					startActivity(new Intent(RegisterAndLoginActivity.this, com.xgr.wonderful.ui.MainActivity.class));
					return;
				}*/
		
		setContentView(R.layout.activity_register);
		Bmob.initialize(this, "e06192b54b3f21069ab16fbdee95de6c");
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		actionbar = (ActionBar)findViewById(R.id.actionbar_register);
		
		loginTitle = (TextView)findViewById(R.id.login_menu);
		registerTitle = (TextView)findViewById(R.id.register_menu);
		resetPassword = (TextView)findViewById(R.id.reset_password_menu);
		
		userNameInput = (DeletableEditText)findViewById(R.id.user_name_input);
		userPasswordInput = (DeletableEditText)findViewById(R.id.user_password_input);
		userEmailInput = (DeletableEditText)findViewById(R.id.user_email_input);
		
		registerButton = (Button)findViewById(R.id.register);
		progressbar = (SmoothProgressBar)findViewById(R.id.sm_progressbar);
		
		usernameEditText=(DeletableEditText)findViewById(R.id.user_name_input);///////////////
		passwordEditText=(DeletableEditText)findViewById(R.id.user_password_input);///////////
		userNameEditText=(DeletableEditText)findViewById(R.id.user_name_input);///////////
		passwordEditText1=(DeletableEditText)findViewById(R.id.user_password_input);///////////
		confirmPwdEditText=(DeletableEditText)findViewById(R.id.user_password_input);///////////
		// 如果用户名改变，清空密码
				usernameEditText.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						passwordEditText.setText(null);
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {

					}
				});
				
	}

	@Override
	protected void setupViews(Bundle bundle) {
		// TODO Auto-generated method stub
		 actionbar.setTitle("用户登录与注册");
		 actionbar.setDisplayHomeAsUpEnabled(true);
		 actionbar.setHomeAction(new Action() {
			
			@Override
			public void performAction(View view) {
				// TODO Auto-generated method stub
				finish();
			}
			
			@Override
			public int getDrawable() {
				// TODO Auto-generated method stub
				return 0;//R.drawable.logo;
			}
		});
		 updateLayout(operation);

		 userProxy = new UserProxy(mContext);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub'
		loginTitle.setOnClickListener(this);
		registerTitle.setOnClickListener(this);
		resetPassword.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		
	}

	@Override
	protected void fetchData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register:
			if(operation == UserOperation.LOGIN){
				
				currentUsername = usernameEditText.getText().toString().trim();
				currentPassword = passwordEditText.getText().toString().trim();
				
				if(TextUtils.isEmpty(userNameInput.getText())){
					userNameInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(userPasswordInput.getText())){
					userPasswordInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
					return;
				}
				
				userProxy.setOnLoginListener(this);
				LogUtils.i(TAG,"login begin....");
				progressbar.setVisibility(View.GONE);//////////////////VISIBLE
				
				final long start = System.currentTimeMillis();
				
			
				// 调用sdk登陆方法登陆聊天服务器
				userProxy.login(userNameInput.getText().toString().trim(), userPasswordInput.getText().toString().trim());
				
				/*EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

					@Override
					public void onSuccess() {
						if (!progressShow) {
							return;
						}
						// 登陆成功，保存用户名密码
						DemoApplication.getInstance().setUserName(currentUsername);
						DemoApplication.getInstance().setPassword(currentPassword);

						try {
							// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
							// ** manually load all local groups and
						    EMGroupManager.getInstance().loadAllGroups();
							EMChatManager.getInstance().loadAllConversations();
							// 处理好友和群组
							initializeContacts();
						} catch (Exception e) {
							e.printStackTrace();
							// 取好友或者群聊失败，不让进入主页面
							runOnUiThread(new Runnable() {
								public void run() {
									
									DemoApplication.getInstance().logout(null);
									Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
								}
							});
							return;
						}
						// 进入主页面
						Intent intent = new Intent(RegisterAndLoginActivity.this,com.xgr.wonderful.ui.MainActivity.class);
						startActivity(intent);
						
						finish();
					}

					@Override
					public void onProgress(int progress, String status) {
					}

					@Override
					public void onError(final int code, final String message) {
						if (!progressShow) {
							return;
						}
						runOnUiThread(new Runnable() {
							public void run() {
								
								Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
							}
						});
					}
					});*/

				
			}else if(operation == UserOperation.REGISTER){
				
				final String username = userNameEditText.getText().toString().trim();
				final String pwd = passwordEditText.getText().toString().trim();
				String confirm_pwd = confirmPwdEditText.getText().toString().trim();
				if(TextUtils.isEmpty(userNameInput.getText())){
					userNameInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(userPasswordInput.getText())){
					userPasswordInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(userEmailInput.getText())){
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtils.isValidEmail(userEmailInput.getText())){
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
					return;
				}
				
				userProxy.setOnSignUpListener(this);
				LogUtils.i(TAG,"register begin....");
				progressbar.setVisibility(View.GONE);////VISIBLE
				
						
					
				
			
			
				userProxy.signUp(userNameInput.getText().toString().trim(),
						userPasswordInput.getText().toString().trim(), 
						userEmailInput.getText().toString().trim());
				
				/*if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
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
										if (!RegisterAndLoginActivity.this.isFinishing())
											pd.dismiss();
										// 保存用户名
										DemoApplication.getInstance().setUserName(username);
										Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), 0).show();
										finish();
									}
								});
							} catch (final EaseMobException e) {
								runOnUiThread(new Runnable() {
									public void run() {
										if (!RegisterAndLoginActivity.this.isFinishing())
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
				}*/
			}else{
				if(TextUtils.isEmpty(userEmailInput.getText())){
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!StringUtils.isValidEmail(userEmailInput.getText())){
					userEmailInput.setShakeAnimation();
					Toast.makeText(mContext, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
					return;
				}
				
				userProxy.setOnResetPasswordListener(this);
				LogUtils.i(TAG,"reset password begin....");
				progressbar.setVisibility(View.GONE);/////VISIBLE
				userProxy.resetPassword(userEmailInput.getText().toString().trim());
			}
			break;
		case R.id.login_menu:
			operation = UserOperation.LOGIN;
			updateLayout(operation);
			break;
		case R.id.register_menu:
			operation = UserOperation.REGISTER;
			updateLayout(operation);
			break;
		case R.id.reset_password_menu:
			operation = UserOperation.RESET_PASSWORD;
			updateLayout(operation);
			break;
		default:
			break;
		}
	}

	private void updateLayout(UserOperation op){
		if(op == UserOperation.LOGIN){
			loginTitle.setTextColor(Color.parseColor("#339966"));
			loginTitle.setBackgroundResource(R.drawable.bg_login_tab);
			loginTitle.setPadding(16, 16, 16, 16);
			loginTitle.setGravity(Gravity.CENTER);
			
			
			registerTitle.setTextColor(Color.parseColor("#888888"));
			registerTitle.setBackgroundDrawable(null);
			registerTitle.setPadding(16, 16, 16, 16);
			registerTitle.setGravity(Gravity.CENTER);
			
			resetPassword.setTextColor(Color.parseColor("#888888"));
			resetPassword.setBackgroundDrawable(null);
			resetPassword.setPadding(16, 16, 16, 16);
			resetPassword.setGravity(Gravity.CENTER);
			
			userNameInput.setVisibility(View.VISIBLE);
			userPasswordInput.setVisibility(View.VISIBLE);
			userEmailInput.setVisibility(View.GONE);
			registerButton.setText("登录");
		}else if(op == UserOperation.REGISTER){
			loginTitle.setTextColor(Color.parseColor("#888888"));
			loginTitle.setBackgroundDrawable(null);
			loginTitle.setPadding(16, 16, 16, 16);
			loginTitle.setGravity(Gravity.CENTER);
			
			registerTitle.setTextColor(Color.parseColor("#339966"));
			registerTitle.setBackgroundResource(R.drawable.bg_login_tab);
			registerTitle.setPadding(16, 16, 16, 16);
			registerTitle.setGravity(Gravity.CENTER);
			
			resetPassword.setTextColor(Color.parseColor("#888888"));
			resetPassword.setBackgroundDrawable(null);
			resetPassword.setPadding(16, 16, 16, 16);
			resetPassword.setGravity(Gravity.CENTER);
			
			userNameInput.setVisibility(View.VISIBLE);
			userPasswordInput.setVisibility(View.VISIBLE);
			userEmailInput.setVisibility(View.VISIBLE);
			registerButton.setText("注册");
		}else{
			loginTitle.setTextColor(Color.parseColor("#888888"));
			loginTitle.setBackgroundDrawable(null);
			loginTitle.setPadding(16, 16, 16, 16);
			loginTitle.setGravity(Gravity.CENTER);
			
			registerTitle.setTextColor(Color.parseColor("#888888"));
			registerTitle.setBackgroundDrawable(null);
			registerTitle.setPadding(16, 16, 16, 16);
			registerTitle.setGravity(Gravity.CENTER);
			
			resetPassword.setTextColor(Color.parseColor("#339966"));
			//resetPassword.setBackgroundResource(R.drawable.bg_login_tab);
			resetPassword.setPadding(16, 16, 16, 16);
			resetPassword.setGravity(Gravity.BOTTOM);////////////////////+
			
			
			userNameInput.setVisibility(View.GONE);
			userPasswordInput.setVisibility(View.GONE);
			userEmailInput.setVisibility(View.VISIBLE);
			registerButton.setText("找回密码");
		}
	}

	private void dimissProgressbar(){
		if(progressbar!=null&&progressbar.isShown()){
			progressbar.setVisibility(View.GONE);
		}
	}

	@Override
	public void onLoginSuccess() {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.show(this, "登录成功。");
		LogUtils.i(TAG,"login sucessed!");
		setResult(RESULT_OK);
		startActivity(new Intent(RegisterAndLoginActivity.this, MainActivity.class));////////////
		finish();
	}

	@Override
	public void onLoginFailure(String msg) {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.show(this, "登录失败，请确保输入正确或确认网络是否已连接~");
		LogUtils.i(TAG,"login failed!"+msg);
	}

	@Override
	public void onSignUpSuccess() {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.show(this, "注册成功");
		LogUtils.i(TAG,"register successed！");
		operation = UserOperation.LOGIN;
		updateLayout(operation);
	}

	@Override
	public void onSignUpFailure(String msg) {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.show(this, "注册失败。用户名已被注册或请确认网络是否已连接~");
		LogUtils.i(TAG,"register failed！"+msg);
	}

	@Override
	public void onResetSuccess() {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.showL(this, "请到邮箱修改密码后再登录。");
		LogUtils.i(TAG,"reset successed！");
		operation = UserOperation.LOGIN;
		updateLayout(operation);
	}

	@Override
	public void onResetFailure(String msg) {
		// TODO Auto-generated method stub
		dimissProgressbar();
		ActivityUtil.show(this, "重置密码失败。请确认网络是否已连接~");
		LogUtils.i(TAG,"register failed！");
	}

	
	private void initializeContacts() {
		Map<String,  com.easemob.chatuidemo.domain.User
> userlist = new HashMap<String,  com.easemob.chatuidemo.domain.User
>();
		// 添加user"申请与通知"
		 com.easemob.chatuidemo.domain.User newFriends = new  com.easemob.chatuidemo.domain.User
();
		newFriends.setUsername(com.easemob.chatuidemo.Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);
		userlist.put(com.easemob.chatuidemo.Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		 com.easemob.chatuidemo.domain.User groupUser = new  com.easemob.chatuidemo.domain.User
();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(com.easemob.chatuidemo.Constant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(com.easemob.chatuidemo.Constant.GROUP_USERNAME, groupUser);

		// 存入内存
		MyApplication.getInstance().setContactList(userlist);
		// 存入db
		UserDao dao = new UserDao(RegisterAndLoginActivity.this);
		List< com.easemob.chatuidemo.domain.User> users = new ArrayList< com.easemob.chatuidemo.domain.User
>(userlist.values());
		dao.saveContactList(users);
	}
	
	
}
