package com.hfp.youtie;

import java.io.File;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import cn.bmob.v3.BmobUser;

import com.bmob.lostfound.bean.Found;
import com.easemob.EMCallBack;

import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.hfp.youtie.entity.Comment;
import com.hfp.youtie.entity.QiangYu;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.utils.ActivityManagerUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application{

	public static String TAG;
	
	private static MyApplication myApplication = null;
	
	private QiangYu currentQiangYu = null;
	private Comment currentComment=null;////////////////////////////
	private entity.Comment currentComment1=null;////////////////////////////
	private Found currentFound=null;////////////////////////////
	
	public static Context applicationContext;
	private static MyApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();////////////
	public static MyApplication getInstance(){
		return myApplication;
	}
	public User getCurrentUser() {
		User user = BmobUser.getCurrentUser(myApplication, User.class);
		if(user!=null){
			return user;
		}
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		//由于Application类本身已经单例，所以直接按以下处理即可。
		myApplication = this;
		  applicationContext = this;//////////////////
	        instance = this;//////////////////////
	        hxSDKHelper.onInit(applicationContext);////////////////////
		initImageLoader();
	}
	
	
	
	public QiangYu getCurrentQiangYu() {
		return currentQiangYu;
	}

	public void setCurrentQiangYu(QiangYu currentQiangYu) {
		this.currentQiangYu = currentQiangYu;
	}

	public Comment getCurrentComment() {/////////////////////////////////////////////////
		return currentComment;
	}

	public void setCurrentComment(Comment currentComment) {
		this.currentComment = currentComment;
	}/////////////////////////////////////////////////
	public entity.Comment getCurrentComment1() {/////////////////////////////////////////////////
		return currentComment1;
	}

	public void setCurrentComment1(entity.Comment currentComment1) {
		this.currentComment1 = currentComment1;
	}/////////////////////////////////////////////////
	public Found getCurrentFound() {/////////////////////////////////////////////////
		return currentFound;
	}

	public void setCurrentFound(Found currentFound) {
		this.currentFound = currentFound;
	}/////////////////////////////////////////////////
	
	public void addActivity(Activity ac){
		ActivityManagerUtils.getInstance().addActivity(ac);
	}
	
	public void exit(){
		ActivityManagerUtils.getInstance().removeAllActivity();
	}
	
	public Activity getTopActivity(){
		return ActivityManagerUtils.getInstance().getTopActivity();
	}
	
	/**
	 * 初始化imageLoader
	 */
	public void initImageLoader(){
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
										.memoryCache(new LruMemoryCache(5*1024*1024))
										.memoryCacheSize(10*1024*1024)
										.discCache(new UnlimitedDiscCache(cacheDir))
										.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
										.build();
		ImageLoader.getInstance().init(config);
	}
	
	public DisplayImageOptions getOptions(int drawableId){
		return new DisplayImageOptions.Builder()
		.showImageOnLoading(drawableId)
		.showImageForEmptyUri(drawableId)
		.showImageOnFail(drawableId)
		.resetViewBeforeLoading(true)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}
	
	/**
	 * 获取内存中好友user list
	 * @return
	 */
	public Map<String, com.easemob.chatuidemo.domain.User> getContactList() {/////////////////////////////////////
	    return hxSDKHelper.getContactList();
	}

	/**
	 * 设置好友user list到内存中
	 * @param contactList
	 */
	public void setContactList(Map<String, com.easemob.chatuidemo.domain.User> contactList) {
	    hxSDKHelper.setContactList(contactList);
	}

	/**
	 * 获取当前登陆用户名
	 * @return
	 */
	public String getUserName() {
	    return hxSDKHelper.getHXId();
	}

	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	/**
	 * 设置用户名
	 * @param user
	 */
	public void setUserName(String username) {
	    hxSDKHelper.setHXId(username);
	}

	/**
	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
	 * 内部的自动登录需要的密码，已经加密存储了
	 *
	 * @param pwd
	 */
	public void setPassword(String pwd) {
	    hxSDKHelper.setPassword(pwd);
	}

	/**
	 * 退出登录,清空数据
	 */
	public void logout(final EMCallBack emCallBack) {
		// 先调用sdk logout，在清理app中自己的数据
	    hxSDKHelper.logout(emCallBack);
	}
}
