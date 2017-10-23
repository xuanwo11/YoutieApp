package net.ting.sliding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chen.photodemo.MainActivity1;
import com.example.adrotatorcomponent.view.Advertisements;
import com.example.adrotatorcomponent.volley.RequestManager;
import com.example.slidingmenu.fragment.RightFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.Lunbo;
import com.hfp.youtie.ui.MainActivity;
import com.hfp.youtie.utils.LogUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import entity.DongMan;
import entity.Keji;
import entity.ManHua;
import entity.Paotu;
import entity.QiangYu;
import entity.Tansuo;
import entity.Xingji;
import entity.Xingtu;
import entity.Xinyu;
import entity.Youmou;


public class MainAcc extends FragmentActivity implements OnClickListener{
	
	
	
	private TextView ciyuan,yue,xinyu,paoquan,xiaoyuan,xingchen;////////////////////
	private TextView button,button1,button2,button3,button4,button5;
	private ImageView ys,kj,sss,ym,ecy,hw;
	private LinearLayout llAdvertiseBoard,llAdvertiseBoard1;
	private LayoutInflater inflater;////////////////////////////lunbo
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fg2);
		RequestManager.init(this);
		inflater = LayoutInflater.from(this);///////////////////////////lunbo
		
		Button btn_back = (Button) findViewById(R.id.btn_back);///////btn_back
		 button = (TextView) findViewById(R.id.btnDownloadImage);
		 button1 = (TextView) findViewById(R.id.btnSelectImage);
		button2 = (TextView) findViewById(R.id.Button01);
		 button3 = (TextView) findViewById(R.id.Button02);
		 button4 = (TextView) findViewById(R.id.hangwu);
		 button5 = (TextView) findViewById(R.id.youmou);
		 
		 ys= (ImageView) findViewById(R.id.ys);
		 kj= (ImageView) findViewById(R.id.kj);
		 hw= (ImageView) findViewById(R.id.hw);
		 sss= (ImageView) findViewById(R.id.ss);
		 ecy= (ImageView) findViewById(R.id.ecy);
		 ym= (ImageView) findViewById(R.id.ym);
		 
	     btn_back.setOnClickListener(this);
	     button.setOnClickListener(this);
	     button1.setOnClickListener(this);
	     button2.setOnClickListener(this);
	     button3.setOnClickListener(this);
	     button4.setOnClickListener(this);
	     button5.setOnClickListener(this);
	     ///////////////////////////////////	

		initViews();
		
	}
	
	
	//完成组件的初始化//////////////////////////////////////////////////////
		public void initViews()
		{
	 
			
			 llAdvertiseBoard = (LinearLayout) this.findViewById(R.id.llAdvertiseBoard);//////
				Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
		       	 BmobQuery<Lunbo> query1 = new BmobQuery<Lunbo>();
		            query1.findObjects(MainAcc.this, new FindListener<Lunbo>() {

						@Override
						public void onError(int code, String msg) {
							// TODO Auto-generated method stub
							  // TODO Auto-generated method stub
							Log.i("查询失败：",msg);
						}

						@Override
						public void onSuccess(List<Lunbo> object) {
							// TODO Auto-generated method stub
							 Log.i("查询成功：共",object.size()+"条数据。");
							 int j=object.size();
							 System.out.print(j+"\n");					 
							 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
							 int i=0;
							 int k=0;
							 HashMap<String, Object> map = new HashMap<String, Object>();
							 
							 for (Lunbo qiangyu:object) {
								   
					               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
					                                  //文件名称
					                                //qiangyu.getContentfigureurl().getFilename();						            	  					            	  
					                                //文件下载地址
					            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
					            	   
										
										  
					            	   map.put(qiangyu.getContent().toString(),qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
					          	 
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
			
								// 娣诲姞鍥剧墖鐨刄rl鍦板潃
	     JSONArray advertiseArray = new JSONArray();
	     JSONArray adc = new JSONArray();///
		 try {	
				JSONObject head_img0 = new JSONObject();
				head_img0.put("head_img",urls[0]);
				JSONObject head_img1 = new JSONObject();
				head_img1.put("head_img",urls[1]);
				JSONObject head_img2 = new JSONObject();
				head_img2.put("head_img",urls[2]);
				JSONObject head_img3 = new JSONObject();
				head_img3.put("head_img",urls[3]);	
				JSONObject head_img4 = new JSONObject();
				head_img4.put("head_img",urls[4]);	
				JSONObject head_img5 = new JSONObject();
				head_img5.put("head_img",urls[5]);	
				
				advertiseArray.put(head_img0);				
				advertiseArray.put(head_img1);
				advertiseArray.put(head_img2);
				advertiseArray.put(head_img3);
				advertiseArray.put(head_img4);
				advertiseArray.put(head_img5);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		 
			llAdvertiseBoard.addView(new Advertisements(MainAcc.this, true, inflater, 3000).initView(advertiseArray));			
		//////////////////////////////lunbo
						}
            }); 
          }
		
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
		finish();
		break;//////////////////////////////////
		case R.id.btnDownloadImage: 
       	 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
       	 BmobQuery<Xingji> query1 = new BmobQuery<Xingji>();
            query1.findObjects(MainAcc.this, new FindListener<Xingji>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}

				@Override
				public void onSuccess(List<Xingji> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Xingji qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();	
			            	  
			            	  
			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
			            	   
								
								  
			            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
			            	 
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
				        if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], sss, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
				        
				         //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
					 //String section =String.valueOf(data.toString());////get每一行的数据				      	            	
       	Intent intent =new Intent(MainAcc.this,MainActivity1.class); 
       	intent.putExtras(bundle); 
       	intent.putExtras(bundle1);
       	startActivity(intent);     	
				}
            });
            break; 
		case R.id.btnSelectImage: 		
       	 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
       	 BmobQuery<DongMan> query = new BmobQuery<DongMan>();
            query.findObjects(MainAcc.this, new FindListener<DongMan>() {

				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}

				@Override
				public void onSuccess(List<DongMan> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (DongMan qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();	
			            	  
			            	  
			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
			            	   
								
								  
			            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
			            	 
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
				        if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], ecy, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
				        
				        
				        //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
					 //String section =String.valueOf(data.toString());////get每一行的数据				      	            	
       	Intent intent =new Intent(MainAcc.this,MainActivity1.class); 
       	intent.putExtras(bundle); 
       	intent.putExtras(bundle1);
       	startActivity(intent);       	       	
				}
            });
            break; 	       
            
		case R.id.Button01:    		   		
       	 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
       	 BmobQuery<ManHua> query3 = new BmobQuery<ManHua>();
            query3.findObjects(MainAcc.this, new FindListener<ManHua>() {
				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}
				@Override
				public void onSuccess(List<ManHua> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");
					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (ManHua qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
		            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
			            	 
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
				        if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], ys, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
				        
				        
				        //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
       	Intent intent =new Intent(MainAcc.this,MainActivity1.class); 
       	intent.putExtras(bundle); 
       	intent.putExtras(bundle1);
       	startActivity(intent);
       	
 				}
            });
            break; 	     
            
		case R.id.Button02:       	
   		 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
       	 BmobQuery<Xingtu> query2 = new BmobQuery<Xingtu>();
            query2.findObjects(MainAcc.this, new FindListener<Xingtu>() {

   				@Override
   				public void onError(int code, String msg) {
   					// TODO Auto-generated method stub
   					  // TODO Auto-generated method stub
   					Log.i("查询失败：",msg);
   				}

   				@Override
   				public void onSuccess(List<Xingtu> object) {
   					// TODO Auto-generated method stub
   					 Log.i("查询成功：共",object.size()+"条数据。");
   					 int j=object.size();
   					 System.out.print(j+"\n"); 					 
   					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
   					 int i=0;
   					 int k=0;
   					 HashMap<String, Object> map = new HashMap<String, Object>();
   					 
   					 for (Xingtu qiangyu:object) {
   						   
   			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
   			                                  //文件名称
   			                                //qiangyu.getContentfigureurl().getFilename();	
   			            	        //文件下载地址
   			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
   			            	   
   			            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));			            	 
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
   				     if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], kj, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
   				        
   				        
   				        
   				        //用Bundle携带数据
   				        Bundle bundle=new Bundle();
   				       Bundle bundle1=new Bundle();
   				        //传递name参数为tinyphp
   				        bundle.putStringArray("name", urls);
   				       bundle1.putStringArray("name1", content);  				      	            	
       	Intent intent3 =new Intent(MainAcc.this,com.chen.photodemo.MainActivity1.class); 
       	intent3.putExtras(bundle); 
       	intent3.putExtras(bundle1);
       	startActivity(intent3);
       	  	
   				}
            });/////////////
            break; 	 
   
   
    
   
   
    case R.id.hangwu:    		   		
      	 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
      	 BmobQuery<Paotu> query5 = new BmobQuery<Paotu>();
           query5.findObjects(MainAcc.this, new FindListener<Paotu>() {
				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}
				@Override
				public void onSuccess(List<Paotu> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");
					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Paotu qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
		            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
			            	 
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
				        if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], hw, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
				        
				        
				        //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
      	Intent intent =new Intent(MainAcc.this,MainActivity1.class); 
      	intent.putExtras(bundle); 
      	intent.putExtras(bundle1);
      	startActivity(intent);
				}
           });
           break; 	
    case R.id.youmou:    		   		
     	 Bmob.initialize(MainAcc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
     	 BmobQuery<Youmou> query6 = new BmobQuery<Youmou>();
          query6.findObjects(MainAcc.this, new FindListener<Youmou>() {
				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}
				@Override
				public void onSuccess(List<Youmou> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");
					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Youmou qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAcc.this);
		            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAcc.this));
			            	 
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
				        if(null != urls[0]){///////////////////////////////////////////////
							ImageLoader.getInstance()
							.displayImage(urls[0], ym, 
									MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
									new SimpleImageLoadingListener(){
					
										@Override
										public void onLoadingComplete(String imageUri, View view,
												Bitmap loadedImage) {
											// TODO Auto-generated method stub
											super.onLoadingComplete(imageUri, view, loadedImage);
											LogUtils.i("MainAcc","load personal icon completed.");
										}
								
							});			
						}
				        
				        
				        //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
     	Intent intent =new Intent(MainAcc.this,MainActivity1.class); 
     	intent.putExtras(bundle); 
     	intent.putExtras(bundle1);
     	startActivity(intent);
				}
          });
          break; 	   
           
   
          
    default:
		break;
	}
	}
	
	
	
}

