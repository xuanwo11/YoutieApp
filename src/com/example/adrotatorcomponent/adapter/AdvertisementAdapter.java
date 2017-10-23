package com.example.adrotatorcomponent.adapter;

import java.util.List;

import org.json.JSONArray;

import com.example.adrotatorcomponent.utils.ImageLoaderUtil;
import com.hfp.youtie.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import net.ting.sliding.Kantu;

/**
 * 广告轮播adapter
 *@author dong
 *@data 2015年3月8日下午3:46:35
 *@contance dong854163@163.com
 */
public class AdvertisementAdapter extends PagerAdapter {

	private Context context;
	private List<View> views;
	JSONArray advertiseArray,adc;
	
	public AdvertisementAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvertisementAdapter(Context context, List<View> views, JSONArray advertiseArray) {
		this.context = context;
		this.views = views;
		this.advertiseArray = advertiseArray;	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		final int POSITION = position;
		View view = views.get(position);			
		try {
			String head_img = advertiseArray.optJSONObject(position).optString("head_img");
			
			ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
			// 加载网络图片
			ImageLoaderUtil.getImage(context,ivAdvertise,head_img, R.drawable.ic_launcher, R.drawable.ic_launcher,0,0);
			// item的点击监听
			final String u=head_img;
			
			ivAdvertise.setOnClickListener(new OnClickListener() {
				@SuppressLint("ShowToast")
				@Override
				public void onClick(View v) {					
					Toast.makeText(context, "点击下方版块浏览~", 3000).show();
					Bundle bundle=new Bundle(); 
					//Bundle bundle1=new Bundle(); 
 			        //传递name参数为tinyphp
 			        bundle.putString("name", u);
 			      // bundle1.putString("name",c);
   				    Intent intent = new Intent();
    				intent.setClass(context,Kantu.class);          				
    				intent.putExtras(bundle);
    				//intent.putExtras(bundle1);
    				context.startActivity(intent); /////在adapter中监听实现跳转,加context
				
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
}
