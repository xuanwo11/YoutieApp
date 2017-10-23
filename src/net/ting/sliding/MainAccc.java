package net.ting.sliding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chen.photodemo.Mac;
import com.chen.photodemo.MainActivity1;
import com.example.adrotatorcomponent.view.Advertisements;
import com.example.adrotatorcomponent.volley.RequestManager;
import com.example.slidingmenu.fragment.RightFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.Lunbo;
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
import entity.Tuzhai;
import entity.Xingji;
import entity.Xingtu;
import entity.Xinyu;
import entity.Youmou;


public class MainAccc extends FragmentActivity implements OnClickListener{
	
	
	RightFragment rightFragment;////////////////
	private TextView ciyuan,yue,xinyu,paoquan,xiaoyuan,xingchen;////////////////////
	private LinearLayout llAdvertiseBoard,llAdvertiseBoard1;
	private LayoutInflater inflater;////////////////////////////lunbo
	private TextView chahua,hangwu;
	private Button tuzhai;
	private ImageView ch,xy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fg1);
		RequestManager.init(this);
		inflater = LayoutInflater.from(this);///////////////////////////lunbo
		
		Button btn_back = (Button) findViewById(R.id.btn_back);///////btn_back
		chahua=(TextView)findViewById(R.id.chahua);//////
		hangwu=(TextView)findViewById(R.id.hangwu);//////
		ch=(ImageView)findViewById(R.id.ch);//////
		xy=(ImageView)findViewById(R.id.xy);//////
		tuzhai=(Button)findViewById(R.id.tuzhai);//////
		btn_back.setOnClickListener(this);////////////////////
		
		chahua.setOnClickListener(this);
		hangwu.setOnClickListener(this);
		tuzhai.setOnClickListener(this);
		
		initViews();
		
	}
	
	
	//完成组件的初始化//////////////////////////////////////////////////////
		public void initViews()
		{
	 
			 llAdvertiseBoard = (LinearLayout) this.findViewById(R.id.llAdvertiseBoard);//////
				Bmob.initialize(MainAccc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
		       	 BmobQuery<Tansuo> query1 = new BmobQuery<Tansuo>();
		            query1.findObjects(MainAccc.this, new FindListener<Tansuo>() {

						@Override
						public void onError(int code, String msg) {
							// TODO Auto-generated method stub
							  // TODO Auto-generated method stub
							Log.i("查询失败：",msg);
						}

						@Override
						public void onSuccess(List<Tansuo> object) {
							// TODO Auto-generated method stub
							 Log.i("查询成功：共",object.size()+"条数据。");
							 int j=object.size();
							 System.out.print(j+"\n");					 
							 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
							 int i=0;
							 int k=0;
							 HashMap<String, Object> map = new HashMap<String, Object>();
							 
							 for (Tansuo qiangyu:object) {
								   
					               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
					                                  //文件名称
					                                //qiangyu.getContentfigureurl().getFilename();						            	  					            	  
					                                //文件下载地址
					            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAccc.this);
					            	   
										
										  
					            	   map.put(qiangyu.getContent().toString(),qiangyu.getContentfigureurl().getFileUrl(MainAccc.this));
					          	 
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
				
				
				advertiseArray.put(head_img0);				
				advertiseArray.put(head_img1);
				advertiseArray.put(head_img2);
				
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		 
			llAdvertiseBoard.addView(new Advertisements(MainAccc.this, true, inflater, 3000).initView(advertiseArray));			
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
    case R.id.chahua:    		   		
   	 Bmob.initialize(MainAccc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
   	 BmobQuery<Xinyu> query7 = new BmobQuery<Xinyu>();
        query7.findObjects(MainAccc.this, new FindListener<Xinyu>() {
				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}
				@Override
				public void onSuccess(List<Xinyu> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");
					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Xinyu qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAccc.this);
		            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAccc.this));
			            	 
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
							.displayImage(urls[0], ch, 
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
   	Intent intent =new Intent(MainAccc.this,MainActivity1.class); 
   	intent.putExtras(bundle); 
   	intent.putExtras(bundle1);
   	startActivity(intent);
				}
        });  
         break;
   case R.id.hangwu:   //xiaoyuan 		   		
  	 Bmob.initialize(MainAccc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
  	 BmobQuery<Keji> query8 = new BmobQuery<Keji>();
       query8.findObjects(MainAccc.this, new FindListener<Keji>() {
				@Override
				public void onError(int code, String msg) {
					// TODO Auto-generated method stub
					  // TODO Auto-generated method stub
					Log.i("查询失败：",msg);
				}
				@Override
				public void onSuccess(List<Keji> object) {
					// TODO Auto-generated method stub
					 Log.i("查询成功：共",object.size()+"条数据。");
					 int j=object.size();
					 System.out.print(j+"\n");
					 
					 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
					 int i=0;
					 int k=0;
					 HashMap<String, Object> map = new HashMap<String, Object>();
					 
					 for (Keji qiangyu:object) {
						   
			               if(qiangyu.getContentfigureurl() != null && qiangyu.getContent() != null){
			                                  //文件名称
			                                //qiangyu.getContentfigureurl().getFilename();			            	   
			                                //文件下载地址
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAccc.this);
		            	   map.put(qiangyu.getContent().toString()+"【"+qiangyu.getCreatedAt()+"】",qiangyu.getContentfigureurl().getFileUrl(MainAccc.this));
			            	 
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
							.displayImage(urls[0], xy, 
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
  	Intent intent =new Intent(MainAccc.this,MainActivity1.class); 
  	intent.putExtras(bundle); 
  	intent.putExtras(bundle1);
  	startActivity(intent);
				}
       });  
        break;      
          
    case R.id.tuzhai:    		   		
     	
    	Bmob.initialize(MainAccc.this, "e06192b54b3f21069ab16fbdee95de6c");// 初始化Bmob 	
   	 BmobQuery<Tuzhai> query1 = new BmobQuery<Tuzhai>();
        query1.findObjects(MainAccc.this, new FindListener<Tuzhai>() {

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
			            	   String imgs = qiangyu.getContentfigureurl().getFileUrl(MainAccc.this);
			            	   
								
								  
			            	   map.put(qiangyu.getContent().toString(),qiangyu.getContentfigureurl().getFileUrl(MainAccc.this));
			            	 
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
				        //用Bundle携带数据
				        Bundle bundle=new Bundle();
				       Bundle bundle1=new Bundle();
				        //传递name参数为tinyphp
				        bundle.putStringArray("name", urls);
				       bundle1.putStringArray("name1", content);
  	Intent intent =new Intent(MainAccc.this,Mac.class); 
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

