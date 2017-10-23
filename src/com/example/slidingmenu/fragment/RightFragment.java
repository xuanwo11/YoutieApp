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

	 private static final int column = 2;//3��
	    private static final int pageCount = 6; //ÿҳ���ظ���
	    private int currentPage = 0; //��ǰҳ
	    private int columnWidth = 0;//�п�
	    private LinearLayout mianContainer;//������
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
        columnWidth = width;//4Ϊ�м�2����϶ /3
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
     * ������
     */
    private void addColumn() {
        for(int i = 0;i < column;i++) {//������
            LinearLayout columnLayout = new LinearLayout(getActivity());
            columnLayout.setLayoutParams(new LayoutParams(columnWidth, LayoutParams.MATCH_PARENT));
            columnLayout.setOrientation(LinearLayout.VERTICAL);
            columnLayouts.add(columnLayout);
            mianContainer.addView(columnLayout);
        }
        addImageView2Column();
    }
    
    /**
     * �������ʼ����imageView������
     */
    private void addImageView2Column() {
   
    	Bmob.initialize(getActivity(), "e06192b54b3f21069ab16fbdee95de6c");// ��ʼ��Bmob 	
    	 BmobQuery<Tuzhai> query1 = new BmobQuery<Tuzhai>();
         query1.findObjects(getActivity(), new FindListener<Tuzhai>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("��ѯʧ�ܣ�",msg);
				}

				@Override
				public void onSuccess(List<Tuzhai> object) {
					// TODO Auto-generated method stub
					 Log.i("��ѯ�ɹ�����",object.size()+"�����ݡ�");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Tuzhai qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //�ļ�����
			                                //qiangyu.getContentfigureurl().getFilename();	
			            	  
			            	  
			            	   
			                                //�ļ����ص�ַ
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
           
        //System.out.println("��ȡ����nameֵΪ"+urls);    	
        //���ϳ��ķ���,Ŀǰû����ʲôBUG
        int columnIndex = 0;
        int imageCount = urls.length;//ImageConst.
        for(int q = currentPage * pageCount;q< (currentPage +1)*pageCount && q< imageCount;q++) {
            columnIndex = columnIndex >= column ? columnIndex = 0 : columnIndex;
            ImageView itemImage = new ImageView(getActivity());//////////////////////////////////////
            itemImage.setLayoutParams(new LayoutParams(columnWidth,LayoutParams.WRAP_CONTENT));
            itemImage.setPadding(2, 2, 2, 2);////////////////////2��2��2��2           
            columnLayouts.get(columnIndex).addView(itemImage);
            downloadImage(itemImage,q);            
            columnIndex++;
                                               
        }
				}
         });
    }

    
    /**
     * ����ͼƬ���Դ�����
     * @param itemImage
     * @param index
     */
    @SuppressWarnings("deprecation")
	private void downloadImage(final ImageView itemImage, int index) {
    	
    	final int index1=index;
    	Bmob.initialize(getActivity(), "19eb5c8b13478c6d6ee43cc1256d1344");// ��ʼ��Bmob 	
     	 BmobQuery<Tuzhai> query1 = new BmobQuery<Tuzhai>();
          query1.findObjects(getActivity(), new FindListener<Tuzhai>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("��ѯʧ�ܣ�",msg);
				}

				@Override
				public void onSuccess(List<Tuzhai> object) {
					// TODO Auto-generated method stub
					 Log.i("��ѯ�ɹ�����",object.size()+"�����ݡ�");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Tuzhai qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //�ļ�����
			                                //qiangyu.getContentfigureurl().getFilename();	
			                                //�ļ����ص�ַ
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
				      
			
        //System.out.println("��ȡ����nameֵΪ"+urls); 
    	//int c=urls.length;
        //columnWidth�������������ͼƬ��maxWidth,0�����޶�      
			        
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
                System.out.println("��"+width+"��"+hight);
                Bitmap icon = Bitmap.createBitmap(width, hight+30, Bitmap.Config.ARGB_8888); //����һ���յ�BItMap  
                Canvas canvas = new Canvas(icon);//��ʼ���������Ƶ�ͼ��icon��  
                 
                Paint photoPaint = new Paint(); //��������  
                photoPaint.setDither(true); //��ȡ��������ͼ�����  
                photoPaint.setFilterBitmap(true);//����һЩ  
                 
                Rect src = new Rect(0, 0, response.getWidth(),response.getHeight());//����һ��ָ�����¾��ε�����  
                Rect dst = new Rect(0, 0, width, hight);//����һ��ָ�����¾��ε�����  
                canvas.drawBitmap(response, src, dst, photoPaint);//��photo ���Ż������� dstʹ�õ������photoPaint  
                 
                TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//���û���  
                textPaint.setTextSize(24.0f);//�����С  
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);//����Ĭ�ϵĿ��  
                textPaint.setColor(Color.BLUE);//���õ���ɫ  
                //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//Ӱ��������  
                StaticLayout layout = new StaticLayout("",textPaint,icon.getWidth(),Alignment.ALIGN_NORMAL,1.0F,0.0F,false);               
               // canvas.translate(0,hight+4);
                layout.draw(canvas);                             
               // canvas.drawText("�����⡻"+str1, 0, hight+24, textPaint);//������ȥ�֣���ʼδ֪x,y������ֻ�ʻ��� 
                canvas.save(Canvas.ALL_SAVE_FLAG); 
                canvas.restore(); 
                itemImage.setImageBitmap(icon);
                
                itemImage.setOnClickListener(new View.OnClickListener() {/////////////////////////       			
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				
        			   Bundle bundle=new Bundle();
        			       Bundle bundle1=new Bundle();
        			        //����name����Ϊtinyphp
        			        bundle.putString("name", u);
        			       bundle1.putString("name1", c);
        				
        				Bundle bundle2=new Bundle();
        			        //����name����Ϊtinyphp
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
        request.setShouldCache(true);//���û��� ����·��������ǰ������
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
