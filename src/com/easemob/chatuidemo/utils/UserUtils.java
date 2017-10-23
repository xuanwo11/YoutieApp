package com.easemob.chatuidemo.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.easemob.chatuidemo.domain.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import entity.Xingji;
import net.ting.sliding.MainAcc;

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static User getUserInfo(String username){
        User user = MyApplication.getInstance().getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
            user.setNick(username);
//            user.setAvatar("http://downloads.easemob.com/downloads/57.png");
        }
        return user;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
        final User user = getUserInfo(username);//////////////
        com.hfp.youtie.entity.User users =new com.hfp.youtie.entity.User();
        final Context ct=context;
        final String um=username;
        final ImageView im=imageView;
        Bmob.initialize(context, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
     	 BmobQuery<com.hfp.youtie.entity.User> query1 = new BmobQuery<com.hfp.youtie.entity.User>();
          query1.findObjects(context, new FindListener<com.hfp.youtie.entity.User>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
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
				
        if(user != null){
        	for(i = 0; i < urls.length; i++){//MyApplication.getInstance().getCurrentUser().getUsername()
        	if(um.equals(urls[i].toLowerCase())){//字符串全以小写输出。
        		String avatarUrl = null;
        		if( content[i]!=null){
        			avatarUrl =  content[i];//MyApplication.getInstance().getCurrentUser().getAvatar();
        		}      	
        		ImageLoader.getInstance()
        		.displayImage(avatarUrl, im, 
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
        	}else{
        		Picasso.with(ct).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(im);
        	}
        	
        	}	//placeholder(R.drawable.default_avatar)
        }else{
            Picasso.with(ct).load(R.drawable.default_avatar).into(im);
        }
				}
          });
    }
    
}
