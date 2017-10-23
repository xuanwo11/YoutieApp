package com.hfp.youtie.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.easemob.EMCallBack;
import com.easemob.chatuidemo.activity.LoginActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.ui.PersonalFragment.IProgressControllor;
import com.hfp.youtie.ui.base.BaseHomeFragment;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.CacheUtils;
import com.hfp.youtie.utils.Constant;
import com.hfp.youtie.utils.LogUtils;

public class SettingsFragment extends BaseHomeFragment implements OnClickListener,OnCheckedChangeListener{

	TextView logout,youxiang;
	RelativeLayout update ;
	RelativeLayout cleanCache;
	CheckBox pushSwitch;
	CheckBox sexSwitch;
	
	RelativeLayout iconLayout;
	ImageView userIcon;
	
	RelativeLayout nickLayout;
	TextView nickName;
	
	RelativeLayout signLayout;
	TextView signature;
	RelativeLayout youliao;///////////////////
	TextView liao;
	RelativeLayout sz;///////////////////
	TextView liaotianshezhi;
	IProgressControllor mIProgressControllor;
	
	static final int UPDATE_SEX = 11;
	static final int UPDATE_ICON = 12;
	static final int GO_LOGIN = 13;
	static final int UPDATE_SIGN = 14;
	static final int EDIT_SIGN = 15;
	
	public static SettingsFragment newInstance(){
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_settings;
	}

	@Override
	protected void findViews(View view) {
		// TODO Auto-generated method stub
		logout = (TextView)view.findViewById(R.id.user_logout);
		update = (RelativeLayout)view.findViewById(R.id.settings_update);
		cleanCache = (RelativeLayout)view.findViewById(R.id.settings_cache);
		pushSwitch = (CheckBox)view.findViewById(R.id.settings_push_switch);
		sexSwitch = (CheckBox)view.findViewById(R.id.sex_choice_switch);
		
		iconLayout = (RelativeLayout)view.findViewById(R.id.user_icon);
		userIcon = (ImageView)view.findViewById(R.id.user_icon_image);
		youxiang=(TextView)view.findViewById(R.id.youxiang);//////////////////
		nickLayout = (RelativeLayout)view.findViewById(R.id.user_nick);
		nickName = (TextView)view.findViewById(R.id.user_nick_text);
		
		signLayout = (RelativeLayout)view.findViewById(R.id.user_sign);
		signature = (TextView)view.findViewById(R.id.user_sign_text);
		
		youliao = (RelativeLayout)view.findViewById(R.id.youliao);/////////////////
		liao = (TextView)view.findViewById(R.id.liao);
		sz = (RelativeLayout)view.findViewById(R.id.sz);/////////////////
		liaotianshezhi = (TextView)view.findViewById(R.id.liaotianshezhi);
	}

	@Override
	protected void setupViews(Bundle bundle) {
		// TODO Auto-generated method stub
		initPersonalInfo();
	}

	private void initPersonalInfo(){
		User user = BmobUser.getCurrentUser(mContext,User.class);
		if(user != null){
			nickName.setText(user.getUsername());
			signature.setText(user.getSignature());
			youxiang.setText(user.getEmail());/////////////////////
			if(user.getSex().equals(Constant.SEX_FEMALE)){
				sexSwitch.setChecked(true);
				sputil.setValue("sex_settings", 0);
			}else{
				sexSwitch.setChecked(false);
				sputil.setValue("sex_settings", 1);
			}
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance()
				.displayImage(avatarFile, userIcon, 
						MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
						new SimpleImageLoadingListener(){

							@Override
							public void onLoadingComplete(String imageUri, View view,
									Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
					
				});
			}
			logout.setText("退出登录");
		}else{
			logout.setText("登录");
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			if(mIProgressControllor!=null)
			mIProgressControllor = (IProgressControllor)activity;
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断用户是否登录
	 * @return
	 */
	private boolean isLogined(){
		BmobUser user = BmobUser.getCurrentUser(mContext, User.class);
		if(user != null){
			return true;
		}
		return false;
	}
	
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		logout.setOnClickListener(this);
		update.setOnClickListener(this);
		cleanCache.setOnClickListener(this);
		pushSwitch.setOnCheckedChangeListener(this);
		sexSwitch.setOnCheckedChangeListener(this);
		
		iconLayout.setOnClickListener(this);
		nickLayout.setOnClickListener(this);
		signLayout.setOnClickListener(this);
		
		youliao.setOnClickListener(this);////////////////////////////////////
		sz.setOnClickListener(this);////////////////////////////////////
	}

	@Override
	protected void fetchData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_logout:
			if(isLogined()){
				BmobUser.logOut(mContext);
				logout();
				ActivityUtil.show(getActivity(), "已退出~");								
				//startActivity(new Intent(getActivity(), LoginActivity.class));
				//getActivity().finish();//////////////////
			}else{
				redictToLogin(GO_LOGIN);
			}
			
			
			break;
		case R.id.settings_update:
		/*	Toast.makeText(mContext, "正在检查。。。", Toast.LENGTH_SHORT).show();
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

				@Override
				public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
					// TODO Auto-generated method stub
					switch (updateStatus) {
			        case UpdateStatus.Yes: // has update
			            UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
			            break;
			        case UpdateStatus.No: // has no update
			            Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.NoneWifi: // none wifi
			            Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
			            break;
			        case UpdateStatus.Timeout: // time out
			            Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
			            break;
			        }
				}
			});
			UmengUpdateAgent.forceUpdate(mContext);*/
			break;
		case R.id.settings_cache:
			ImageLoader.getInstance().clearDiscCache();
			ActivityUtil.show(getActivity(), "清除缓存完毕");
			break;
			
		case R.id.user_icon:
			if(isLogined()){
				showAlbumDialog();
			}else{
				redictToLogin(UPDATE_ICON);
			}
			break;
		case R.id.user_nick:
			//无需设置
			break;
		case R.id.user_sign:
			if(isLogined()){
				Intent intent = new Intent();
				intent.setClass(mContext, EditSignActivity.class);
				startActivityForResult(intent, EDIT_SIGN);
			}else{
				redictToLogin(UPDATE_SIGN);
			}
			break;
			
		case R.id.youliao://////
			
			Intent intenth =new Intent(mContext,com.easemob.chatuidemo.activity.Mhuihua.class); 		            
	    	 startActivity(intenth);
		
			break;
        case R.id.sz://////
			
			Intent intentz =new Intent(mContext,com.easemob.chatuidemo.activity.Mshezhi.class); 		            
	    	 startActivity(intentz);
		
			break;	
		default:
			break;
		}
	}
	void logout() {////////////////////////////////////////////
		final ProgressDialog pd = new ProgressDialog(getActivity());
		String st = getResources().getString(R.string.Are_logged_out);
		pd.setMessage(st);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		MyApplication.getInstance().logout(new EMCallBack() {
			
			@Override
			public void onSuccess() {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						// 重新显示登陆页面
						getActivity().finish();
						//((com.easemob.chatuidemo.activity.MainActivity) getActivity()).finish();
						startActivity(new Intent(getActivity(), LoginActivity.class));
						
					}
				});
			}
			
			@Override
			public void onProgress(int progress, String status) {
				
			}
			
			@Override
			public void onError(int code, String message) {
				
			}
		});
	}////////////////////////////////////
	String dateTime;
	AlertDialog albumDialog;
	public void showAlbumDialog(){
		albumDialog = new AlertDialog.Builder(mContext).create();
		albumDialog.setCanceledOnTouchOutside(true);
		View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_usericon, null);
		albumDialog.show();
		albumDialog.setContentView(v);
		albumDialog.getWindow().setGravity(Gravity.CENTER);
		
		
		TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
		TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
		albumPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				albumDialog.dismiss();
				Date date1 = new Date(System.currentTimeMillis());
				dateTime = date1.getTime() + "";
				getAvataFromAlbum();
			}
		});
		cameraPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				albumDialog.dismiss();
				Date date = new Date(System.currentTimeMillis());
				dateTime = date.getTime() + "";
				getAvataFromCamera();
			}
		});
	}
	

	
	private void getAvataFromCamera(){
		File f = new File(CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime+".jpg");
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(f);
		Log.e("uri", uri + "");
		
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(camera, 1);
	}
	
	private void getAvataFromAlbum(){
		try {
            Intent intent;
            if(Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(intent, 2);
        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.settings_push_switch:
			if(isChecked){
				//接受推送，储存值
				sputil.setValue("isPushOn", true);
				//PushAgent mPushAgent = PushAgent.getInstance(mContext);
				//mPushAgent.enable();
			}else{
				//关闭推送，储存值
				sputil.setValue("isPushOn", false);
				//PushAgent mPushAgent = PushAgent.getInstance(mContext);
				//mPushAgent.disable();
			}
			break;
		case R.id.sex_choice_switch:
			if(isChecked){
				sputil.setValue("sex_settings", 0);
				updateSex(0);
			}else{
				sputil.setValue("sex_settings", 1);
				updateSex(1);
			}
			break;
		default:
			break;
		}
			
	}
	
	private void updateSex(int sex){
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if(user!=null){
			if(sex == 0){
				user.setSex(Constant.SEX_FEMALE);
			}else{
				user.setSex(Constant.SEX_MALE);
			}
			if(mIProgressControllor!=null)
			mIProgressControllor.showActionBarProgress();
			user.update(mContext, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					if(mIProgressControllor!=null)
					mIProgressControllor.hideActionBarProgress();
					ActivityUtil.show(getActivity(),"更新信息成功。");
					LogUtils.i(TAG,"更新信息成功。");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					if(mIProgressControllor!=null)
					mIProgressControllor.hideActionBarProgress();
					ActivityUtil.show(getActivity(), "更新信息失败。请检查网络~");
					LogUtils.i(TAG,"更新失败1-->"+arg1);
				}
			});
		}else{
			redictToLogin(UPDATE_SEX);
		}
		
	}

	private void redictToLogin(int requestCode){
		Intent intent = new Intent();
		intent.setClass(getActivity(), RegisterAndLoginActivity.class);
		startActivityForResult(intent, requestCode);
		ActivityUtil.show(mContext, "请先登录。");
	}
	
	String iconUrl;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case UPDATE_SEX:
				initPersonalInfo();
				break;
			case UPDATE_ICON:
				initPersonalInfo();
				iconLayout.performClick();
				break;
			case UPDATE_SIGN:
				initPersonalInfo();
				signLayout.performClick();
				break;
			case EDIT_SIGN:
				initPersonalInfo();
				break;
			case 1:
				String files =CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime+".jpg";
				File file = new File(files);
				if(file.exists()&&file.length() > 0){
					Uri uri = Uri.fromFile(file);
					startPhotoZoom(uri);
				}else{
					
				}
				break;
			case 2:
				if (data == null) {
					return;
				}
				startPhotoZoom(data.getData());
				break;
			case 3:
				if (data != null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bitmap = extras.getParcelable("data");
						// 锟斤拷锟斤拷图片
						iconUrl = saveToSdCard(bitmap);
						userIcon.setImageBitmap(bitmap);
						updateIcon(iconUrl);
					}
				}
				break;
			case GO_LOGIN:
				initPersonalInfo();
				logout.setText("退出登录");
				break;
			default:
				break;
			}
		}
	}
	
	private void updateIcon(String avataPath){
		if(avataPath!=null){
			final BmobFile file = new BmobFile(new File(avataPath));
			if(mIProgressControllor!=null)
			mIProgressControllor.showActionBarProgress();
			file.upload(mContext, new UploadFileListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					if(mIProgressControllor!=null)
					mIProgressControllor.hideActionBarProgress();
					LogUtils.i(TAG, "上传文件成功。"+file.getFileUrl(getActivity()));
					User currentUser = BmobUser.getCurrentUser(mContext, User.class);
					currentUser.setAvatar(file.getFileUrl(getActivity()));
					if(mIProgressControllor!=null)
					mIProgressControllor.showActionBarProgress();
					currentUser.update(mContext, new UpdateListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							if(mIProgressControllor!=null)
							mIProgressControllor.hideActionBarProgress();
							ActivityUtil.show(getActivity(), "更改头像成功。");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							if(mIProgressControllor!=null)
							mIProgressControllor.hideActionBarProgress();
							ActivityUtil.show(getActivity(), "更新头像失败。请检查网络~");
							LogUtils.i(TAG,"更新失败2-->"+arg1);
						}
					});
				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					if(mIProgressControllor!=null)
					mIProgressControllor.hideActionBarProgress();
					ActivityUtil.show(getActivity(), "上传头像失败。请检查网络~");
					LogUtils.i(TAG, "上传文件失败。"+arg1);
				}
			});
		}
	}
	
	
	
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 锟斤拷锟斤拷锟斤拷锟絚rop=true锟斤拷锟斤拷锟斤拷锟节匡拷锟斤拷锟斤拷Intent锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷VIEW锟缴裁硷拷
		// aspectX aspectY 锟角匡拷叩谋锟斤拷锟�
		intent.putExtra("crop", "true");///////
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 锟角裁硷拷图片锟斤拷锟�
		intent.putExtra("outputX", 320);//120
		intent.putExtra("outputY", 320);
		
		intent.putExtra("scale", true);// 去锟斤拷锟节憋拷
		intent.putExtra("scaleUpIfNeeded", true);// 去锟斤拷锟节憋拷
		// intent.putExtra("noFaceDetection", true);//锟斤拷锟斤拷识锟斤拷
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}
	
	public String saveToSdCard(Bitmap bitmap){
		String files =CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime+"_12"+".jpg";
		File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
	}
}
