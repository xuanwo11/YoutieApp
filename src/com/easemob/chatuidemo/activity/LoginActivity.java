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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chen.photodemo.MainActivity1;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.Constant;

import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.chatuidemo.utils.UserUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.proxy.UserProxy;
import com.hfp.youtie.utils.LogUtils;
import com.hfp.youtie.view.DeletableEditText;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import entity.Xinyu;
import net.ting.sliding.MainAccc;

/**
 * 登陆页面
 * 
 */
public class LoginActivity extends BaseActivity {
	private static final String TAG = "LoginActivity";
	public static final int REQUEST_CODE_SETNICK = 1;
	private EditText usernameEditText;
	private EditText passwordEditText;

	private boolean progressShow;
	private boolean autoLogin = false;

	private String currentUsername;
	private String currentPassword;
	DeletableEditText userEmailInput;/////////////
	UserProxy userProxy;////////////////
	private CircularImage touxiang;/////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.activity_login);
		Bmob.initialize(this, "e06192b54b3f21069ab16fbdee95de6c");
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
        touxiang=(CircularImage)findViewById(R.id.touxiang);//////////////
        BmobQuery<com.hfp.youtie.entity.User> query1 = new BmobQuery<com.hfp.youtie.entity.User>();
        query1.findObjects(this, new FindListener<com.hfp.youtie.entity.User>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					//Log.i("查询失败：",msg);
				}

				@Override
				public void onSuccess(List<com.hfp.youtie.entity.User> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (com.hfp.youtie.entity.User qiangyu:object) {
						   
			               if(qiangyu.getUsername() != null ){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();	
			            	  
			            	   map.put(qiangyu.getAvatar(),qiangyu.getUsername());
			            	 
					i++;
			            	  data.add(map);
			               
			            }			               
			        }					
					 Set<String> set = map.keySet();
					  Iterator<String> it = set.iterator();
					  String[][] ss = new String[map.size()][2];
					  for (i = 0; i < map.size(); i++) {
					   ss[i][0] = it.next();
					   ss[i][1] = (String) map.get(ss[i][0]);					  
					   System.out.print(ss[i][1]+"\n");
					  }
					  
					  System.out.println(ss.length);
					  String [] urls = new String[(ss.length)*2];
					 String [] content= new String[(ss.length)*2];
				        for ( i = 0; i < ss.length; i++) {
				            for ( j = 0; j < ss[i].length; j++) {
				                urls[i+j] = ss[i][j+1];				                    			                
				                System.out.println(urls[i+j]);				               	     					        
				                j++;
				            }
				           content[i]=ss[i][0];
				         // System.out.println(content[i]);
				        }
				        
				    	for(i = 0; i < urls.length; i++){//MyApplication.getInstance().getCurrentUser().getUsername()
				        	if(MyApplication.getInstance().getUserName()==null){
				        		break;
				        	}
				        	else if(MyApplication.getInstance().getUserName().equals(urls[i])){//字符串全以小写输出。
				        		String avatarUrl = null;
				        		if( content[i]!=null){
				        			avatarUrl =  content[i];//MyApplication.getInstance().getCurrentUser().getAvatar();
				        		}      	
				        		ImageLoader.getInstance()
				        		.displayImage(avatarUrl, touxiang, 
				        				MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
				        				new SimpleImageLoadingListener(){

				        					@Override
				        					public void onLoadingComplete(String imageUri, View view,
				        							Bitmap loadedImage) {
				        						// TODO Auto-generated method stub
				        						super.onLoadingComplete(imageUri, view, loadedImage);
				        					}
				        			
				        		});
				        		break;
				        	 //Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
				        	}
				        
				        	}
				}
        });
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
		if (MyApplication.getInstance().getUserName() != null) {
			usernameEditText.setText(MyApplication.getInstance().getUserName());
		}
		userProxy = new UserProxy(mContext);/////
	}

	/**
	 * 登录
	 * 
	 * @param view
	 */
	public void login(View view) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
			return;
		}
		currentUsername = usernameEditText.getText().toString().trim();
		currentPassword = passwordEditText.getText().toString().trim();
        
		if (TextUtils.isEmpty(currentUsername)) {
			Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}

		progressShow = true;
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage(getString(R.string.Is_landing));
		pd.show();

		final long start = System.currentTimeMillis();
		// 调用sdk登陆方法登陆聊天服务器		
		
		//UserUtils.setUserAvatar(mContext, currentUsername, touxiang);		        
		final BmobUser user = new BmobUser();//////////////////
		user.setUsername(currentUsername);
		user.setPassword(currentPassword);
		userProxy.login(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());//////////////////////////////////////	
		EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

			@Override
			public void onSuccess() {
				if (!progressShow) {
					return;
				}
				// 登陆成功，保存用户名密码
				MyApplication.getInstance().setUserName(currentUsername);
				MyApplication.getInstance().setPassword(currentPassword);
				final BmobUser user = new BmobUser();//////////////////
				user.setUsername(currentUsername);
				user.setPassword(currentPassword);
			    //////////////////////////
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
							pd.dismiss();
							MyApplication.getInstance().logout(null);
							Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
						}
					});
					return;
				}
				// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
				boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
						MyApplication.currentUserNick.trim());
				if (!updatenick) {
					Log.e("LoginActivity", "update current user nick fail");
				}
				if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
					pd.dismiss();
				}
				// 进入主页面
				Intent intent = new Intent(LoginActivity.this,com.hfp.youtie.ui.MainActivity.class);
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
						pd.dismiss();
						Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
						Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
	}

	
	private void initializeContacts() {
		Map<String, com.easemob.chatuidemo.domain.User> userlist = new HashMap<String, com.easemob.chatuidemo.domain.User>();
		// 添加user"申请与通知"
		com.easemob.chatuidemo.domain.User newFriends = new com.easemob.chatuidemo.domain.User();
		newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);
		userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		com.easemob.chatuidemo.domain.User groupUser = new com.easemob.chatuidemo.domain.User();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(Constant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(Constant.GROUP_USERNAME, groupUser);

		// 存入内存
		MyApplication.getInstance().setContactList(userlist);
		// 存入db
		UserDao dao = new UserDao(LoginActivity.this);
		List<com.easemob.chatuidemo.domain.User> users = new ArrayList<com.easemob.chatuidemo.domain.User>(userlist.values());
		dao.saveContactList(users);
	}
	
	/**
	 * 注册
	 * @param view
	 */
	public void register(View view) {
		startActivityForResult(new Intent(this, RegisterActivity.class), 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (autoLogin) {
			return;
		}
	}
}
