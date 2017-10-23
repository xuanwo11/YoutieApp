/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.slidingmenu.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.chen.photodemo.MainActivity1;
import com.chen.photodemo.MyScrollView;
import com.hfp.youtie.R;
import com.hfp.youtie.ui.MainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import entity.Tuzhai;
import net.ting.sliding.Kantu;

public class RightFragment extends Fragment {

	 private static final int column = 2;//3列
	    private static final int pageCount = 6; //每页加载个数
	    private int currentPage = 0; //当前页
	    private int columnWidth = 0;//列宽
	    private LinearLayout mianContainer;//主容器
	    private RequestQueue queue;
	    private List<LinearLayout> columnLayouts = new ArrayList<LinearLayout>();//////////////	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.right, null);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mianContainer = (LinearLayout)getActivity().findViewById(R.id.mianContainer);                           
        queue = Volley.newRequestQueue(getActivity());
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        columnWidth = width;//4为中间2条空隙 /3
        ((MyScrollView)getActivity().findViewById(R.id.scrollView)).setScrollCallBack(new MyScrollCallBack());
        addColumn();///////////////////////////////////////
		ImageView dlam = (ImageView)getActivity().findViewById(R.id.ImageView01);
		dlam.setOnClickListener(new OnClickListener(){	 			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
					//if (v == btn_back) {
						Intent intent = new Intent(getActivity(), MainActivity1.class);					   
						getActivity().startActivity(intent); 
				//}	
			}
			
		});
	
		
		
	}
	class MyScrollCallBack implements MyScrollView.ScrollCallBack {

        @Override
        public void onTop() {
            
        }

        @Override
        public void onBottom() {
            currentPage++;
            addImageView2Column();
        }

        @Override
        public void onScroll() {
            
        }
        
    }

    /**
     * 构造列
     */
    private void addColumn() {
        for(int i = 0;i < column;i++) {//构造列
            LinearLayout columnLayout = new LinearLayout(getActivity());
            columnLayout.setLayoutParams(new LayoutParams(columnWidth, LayoutParams.MATCH_PARENT));
            columnLayout.setOrientation(LinearLayout.VERTICAL);
            columnLayouts.add(columnLayout);
            mianContainer.addView(columnLayout);
        }
        addImageView2Column();
    }
    
    /**
     * 构造完后开始加入imageView到列中
     */
    private void addImageView2Column() {
   
    	Bmob.initialize(getActivity(), "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
    	 BmobQuery<Tuzhai> query1 = new BmobQuery<Tuzhai>();
         query1.findObjects(getActivity(), new FindListener<Tuzhai>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}

				@Override
				public void onSuccess(List<Tuzhai> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Tuzhai qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();	
			            	  
			            	  
			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(getActivity());
			            	   
								
								  
			            	   map.put(qiangyu.getContent().toString(),qiangyu.getContentfigureurl().getFileUrl(getActivity()));
			            	 
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
				          System.out.println(content[i]);
				        }
           
        //System.out.println("获取到的name值为"+urls);    	
        //网上抄的方法,目前没发现什么BUG
        int columnIndex = 0;
        int imageCount = urls.length;//ImageConst.
        for(int q = currentPage * pageCount;q< (currentPage +1)*pageCount && q< imageCount;q++) {
            columnIndex = columnIndex >= column ? columnIndex = 0 : columnIndex;
            ImageView itemImage = new ImageView(getActivity());//////////////////////////////////////
            itemImage.setLayoutParams(new LayoutParams(columnWidth,LayoutParams.WRAP_CONTENT));
            itemImage.setPadding(2, 2, 2, 2);////////////////////2，2，2，2           
            columnLayouts.get(columnIndex).addView(itemImage);
            downloadImage(itemImage,q);            
            columnIndex++;
                                               
        }
				}
         });
    }

    
    /**
     * 下载图片，自带缓存
     * @param itemImage
     * @param index
     */
    @SuppressWarnings("deprecation")
	private void downloadImage(final ImageView itemImage, int index) {
    	
    	final int index1=index;
    	Bmob.initialize(getActivity(), "19eb5c8b13478c6d6ee43cc1256d1344");// 初始化Bmob 	
     	 BmobQuery<Tuzhai> query1 = new BmobQuery<Tuzhai>();
          query1.findObjects(getActivity(), new FindListener<Tuzhai>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}

				@Override
				public void onSuccess(List<Tuzhai> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Tuzhai qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();	
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(getActivity());
			            	   
								
								  
			            	   map.put(qiangyu.getContent().toString(),qiangyu.getContentfigureurl().getFileUrl(getActivity()));
			            	 
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
				          System.out.println(content[i]);
				        }
				      
			
        //System.out.println("获取到的name值为"+urls); 
    	//int c=urls.length;
        //columnWidth这个是设置下载图片的maxWidth,0代表不限定      
			        
    	  String str=content[index1]; //.substring(0,10)
    	  final String str1=str;   
    	 final int in=index1;
    //  final String str=content[index].substring(0,10)+"\n"+content[index].substring(11,m)+"...";
     
    	 final String u=urls[index1];
    	 final String c=content[index1];
        //   final String str=content[index];
     
        ImageRequest request = new ImageRequest(urls[index1], new Listener<Bitmap>() {
            @Override//ImageConst.
            public void onResponse(Bitmap response) {
               // itemImage.setImageBitmap(response);
                
               
                int width =response.getWidth(), hight =response.getHeight();
                System.out.println("宽"+width+"高"+hight);
                Bitmap icon = Bitmap.createBitmap(width, hight+30, Bitmap.Config.ARGB_8888); //建立一个空的BItMap  
                Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上  
                 
                Paint photoPaint = new Paint(); //建立画笔  
                photoPaint.setDither(true); //获取跟清晰的图像采样  
                photoPaint.setFilterBitmap(true);//过滤一些  
                 
                Rect src = new Rect(0, 0, response.getWidth(),response.getHeight());//创建一个指定的新矩形的坐标  
                Rect dst = new Rect(0, 0, width, hight);//创建一个指定的新矩形的坐标  
                canvas.drawBitmap(response, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint  
                 
                TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔  
                textPaint.setTextSize(24.0f);//字体大小  
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度  
                textPaint.setColor(Color.BLUE);//采用的颜色  
                //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置  
                StaticLayout layout = new StaticLayout("",textPaint,icon.getWidth(),Alignment.ALIGN_NORMAL,1.0F,0.0F,false);               
               // canvas.translate(0,hight+4);
                layout.draw(canvas);                             
               // canvas.drawText("『主题』"+str1, 0, hight+24, textPaint);//绘制上去字，开始未知x,y采用那只笔绘制 
                canvas.save(Canvas.ALL_SAVE_FLAG); 
                canvas.restore(); 
                itemImage.setImageBitmap(icon);
                
                itemImage.setOnClickListener(new View.OnClickListener() {/////////////////////////       			
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				
        			   Bundle bundle=new Bundle();
        			       Bundle bundle1=new Bundle();
        			        //传递name参数为tinyphp
        			        bundle.putString("name", u);
        			       bundle1.putString("name1", c);
        				
        				Bundle bundle2=new Bundle();
        			        //传递name参数为tinyphp
        			        bundle2.putInt("name2", in);
        				Intent intent = new Intent();
        				intent.setClass(getActivity(), Kantu.class);  
        				intent.putExtras(bundle2);
        				intent.putExtras(bundle1);
        				intent.putExtras(bundle);
        				startActivity(intent);
        			}			
        		});/////////////////////////////////////////
             
            }
        }, columnWidth, 0, Config.RGB_565, null);
        request.setShouldCache(true);//设置缓存 缓存路径看我以前的帖子
        queue.add(request);
        
  	  
				}
          });
     
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	getActivity().getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
