package com.hfp.youtie.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import com.easemob.chatuidemo.activity.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.ui.PersonAc;
import com.hfp.youtie.ui.PersonActivity;
import com.hfp.youtie.ui.PersonalActivity;
import com.hfp.youtie.ui.PersoncomActivity;
import com.hfp.youtie.ui.RegisterAndLoginActivity;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.LogUtils;

import entity.Comment;

public class ComAdapter extends BaseContentAdapter<Comment>{

	public ComAdapter(Context context, List<Comment> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ViewHolder viewHolder;//////////////////////
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.comment_item, null);
			viewHolder.userName = (TextView)convertView.findViewById(R.id.userName_comment);
			viewHolder.commentContent = (TextView)convertView.findViewById(R.id.content_comment);
			viewHolder.index = (TextView)convertView.findViewById(R.id.index_comment);
			viewHolder.userLogo = (ImageView)convertView.findViewById(R.id.user_logo);////////
			viewHolder.tv_time1 = (TextView)convertView.findViewById(R.id.tv_time1);////////////
			viewHolder.delete1 = (TextView)convertView.findViewById(R.id.delete1);////////////
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		final entity.Comment comment = dataList.get(position);
		//User user = comment.getUser();////////////////////
		if(comment.getUser2().getUsername()!=null){
			
			viewHolder.userName.setText(comment.getUser2().getUsername());
			
			LogUtils.i("CommentActivity","NAME:"+comment.getUser2().getUsername());
		}else{
			viewHolder.userName.setText("无名用户");
		}
		String avatarUrl = null;/////////////////////
		if(comment.getUser2().getAvatar()!=null){
			avatarUrl = comment.getUser2().getAvatar();
		}
		ImageLoader.getInstance()
		.displayImage(avatarUrl, viewHolder.userLogo, 
				MyApplication.getInstance().getOptions(R.drawable.user_icon_default_main),
				new SimpleImageLoadingListener(){

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
			
		});		
		
		if(MyApplication.getInstance().getCurrentUser()!=null){/////////////////
			// TODO Auto-generated method stub
			
			User cUser = BmobUser.getCurrentUser(mContext, User.class);
			String id=MyApplication.getInstance().getCurrentUser().getUsername();				
			Log.i("bmob",id);
			String idd=comment.getUser2().getUsername();				
		if(id.equals(idd)){///////////////////////////////String字符串的比较
			viewHolder.delete1.setVisibility(View.VISIBLE);
		}else{
			viewHolder.delete1.setVisibility(View.GONE);
		}
			}else{
				viewHolder.delete1.setVisibility(View.GONE);
			}
	///////////////////////////////////
        viewHolder.delete1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				User cUser = BmobUser.getCurrentUser(mContext, User.class);
				String id=MyApplication.getInstance().getCurrentUser().getUsername();				
				Log.i("bmob",id);
				String idd=comment.getUser2().getUsername();				
			if(id.equals(idd)){///////////////////////////////String字符串的比较
				comment.setObjectId(comment.getObjectId());
				comment.delete(mContext, new DeleteListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						//dataList.remove(pos);
						Log.i("bmob","删除成功");	
						Toast.makeText(mContext, "删除成功~", Toast.LENGTH_SHORT).show();  
					}

					@Override
					public void onFailure(int code, String arg0) {
						// TODO Auto-generated method stub
						Log.i("bmob","删除失败：");
						Toast.makeText(mContext, "删除失败，请检查网络~", Toast.LENGTH_SHORT).show();
					}
				});
			}
			}
		});///////////////////////////////////

     viewHolder.userLogo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(MyApplication.getInstance().getCurrentUser()==null){
					ActivityUtil.show(mContext, "请先登录。");
					Intent intent = new Intent();
					intent.setClass(mContext, RegisterAndLoginActivity.class);
					MyApplication.getInstance().getTopActivity().startActivity(intent);
					return;
				}else{	
					MyApplication.getInstance().setCurrentComment1(comment);
//				User currentUser = BmobUser.getCurrentUser(mContext,User.class);
//				if(currentUser != null){//已登录
					Intent intent = new Intent();
					intent.setClass(MyApplication.getInstance().getTopActivity(), PersonAc.class);
					mContext.startActivity(intent);
//				}else{//未登录
//					ActivityUtil.show(mContext, "请先登录。");
//					Intent intent = new Intent();
//					intent.setClass(MyApplication.getInstance().getTopActivity(), RegisterAndLoginActivity.class);
//					MyApplication.getInstance().getTopActivity().startActivityForResult(intent, Constant.GO_SETTINGS);
//				}
				}
			}
		});
		///////////////////////////////
		viewHolder.index.setText((position+1)+"楼");
		viewHolder.commentContent.setText(comment.getTitle());
		viewHolder.tv_time1.setText(comment.getCreatedAt());///////////////////
		return convertView;
	}

	public static class ViewHolder{
		public TextView userName;
		public TextView commentContent;
		public TextView index;
		public TextView tv_time1;////////////////////
		public ImageView userLogo;/////////////////
		public TextView delete1;
	}
}

