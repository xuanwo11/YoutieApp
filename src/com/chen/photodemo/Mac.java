package com.chen.photodemo;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.hfp.youtie.R;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import net.ting.sliding.Kantu;

/**
 * 
 * @author jianrong.zheng
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class Mac extends Activity {
    private static final int column = 3;//3列
    private static final int pageCount = 6; //每页加载个数
    private int currentPage = 0; //当前页
    private int columnWidth = 0;//列宽
    private LinearLayout mianContainer;//主容器
    private RequestQueue queue;
    private List<LinearLayout> columnLayouts = new ArrayList<LinearLayout>();
    Button btn_back;/////////////////////
    ActionBar actionbar;//////////////////////////////////
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
     
        mianContainer = (LinearLayout) findViewById(R.id.mianContainer);
        btn_back = (Button) findViewById(R.id.btn_back);///////////////////// 
                    
        queue = Volley.newRequestQueue(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        columnWidth = (width - 4)/ 3 ;//4为中间2条空隙 /3
        ((MyScrollView)findViewById(R.id.scrollView)).setScrollCallBack(new MyScrollCallBack());
        addColumn();
        
        btn_back.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub       			
        			finish();
        		}	
        });          
    }
    
    /**
     * 滚动回调
     */
   
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
            LinearLayout columnLayout = new LinearLayout(this);
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
   

    	  //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String[] urls= bundle.getStringArray("name");
           
        //System.out.println("获取到的name值为"+urls); 
    	
    	
        //网上抄的方法,目前没发现什么BUG
        int columnIndex = 0;
        int imageCount = urls.length;//ImageConst.
        for(int i = currentPage * pageCount;i< (currentPage +1)*pageCount && i < imageCount;i++) {
            columnIndex = columnIndex >= column ? columnIndex = 0 : columnIndex;
            ImageView itemImage = new ImageView(this);//////////////////////////////////////
            itemImage.setLayoutParams(new LayoutParams(columnWidth,LayoutParams.WRAP_CONTENT));
            itemImage.setPadding(4, 4, 4, 4);////////////////////2，2，2，2           
            columnLayouts.get(columnIndex).addView(itemImage);
            downloadImage(itemImage,i);            
            columnIndex++;
                                               
        }
        
    }

    
    /**
     * 下载图片，自带缓存
     * @param itemImage
     * @param index
     */
    @SuppressWarnings("deprecation")
	private void downloadImage(final ImageView itemImage, int index) {
    	

  	  //新页面接收数据
      Bundle bundle = this.getIntent().getExtras();
      Bundle bundle1 = this.getIntent().getExtras();
      //接收name值
      String[] urls= bundle.getStringArray("name");
      String[] content= bundle1.getStringArray("name1");
        //System.out.println("获取到的name值为"+urls); 
    	//int c=urls.length;
        //columnWidth这个是设置下载图片的maxWidth,0代表不限定      
    	  String str=content[index]; //.substring(0,10)
    	  final String str1=str;   
    	 final int in=index;
    //  final String str=content[index].substring(0,10)+"\n"+content[index].substring(11,m)+"...";
     
    	 final String u=urls[index];
    	 final String c=content[index];
        //   final String str=content[index];
     
        ImageRequest request = new ImageRequest(urls[index], new Listener<Bitmap>() {
            @Override//ImageConst.
            public void onResponse(Bitmap response) {
               // itemImage.setImageBitmap(response);   
            	
            	Drawable mDrawable = Mac.this.getResources().getDrawable(R.drawable.fens);
            	Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();//////////////
            	
            	
            	
                int width =response.getWidth(), hight =response.getHeight();
                System.out.println("宽"+width+"高"+hight);
                Bitmap icon = Bitmap.createBitmap(response.getWidth(),response.getHeight(), Bitmap.Config.ARGB_8888); //建立一个空的BItMap  
                Canvas canvas = new Canvas(icon);//初始化画布绘制的图像到icon上  
                 
                Paint photoPaint = new Paint(); //建立画笔  
                photoPaint.setDither(true); //获取跟清晰的图像采样  
                photoPaint.setFilterBitmap(true);//过滤一些  
                 
                Rect src = new Rect(0, 0, response.getWidth(),response.getHeight());//创建一个指定的新矩形的坐标  
                Rect dst = new Rect(0, 0, width, hight);//创建一个指定的新矩形的坐标  
                canvas.drawBitmap(response, src, dst, photoPaint);//将photo 缩放或则扩大到 dst使用的填充区photoPaint  
                src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());// 截取bmp1中的矩形区域
                dst = new Rect(0, hight, width,
                		response.getHeight()+114);// bmp2在目标画布中的位置
                //canvas.drawBitmap(mBitmap, src, dst, photoPaint);//////////////////////
                
                TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//设置画笔  
                textPaint.setTextSize(22.0f);//字体大小  
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);//采用默认的宽度  
                textPaint.setColor( Color.WHITE);//采用的颜色  Color.GREEN
                //textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置  
               // StaticLayout layout = new StaticLayout("『主题』"+str1,textPaint,icon.getWidth(),Alignment.ALIGN_NORMAL,1.0F,0.0F,false);               
               // canvas.translate(0,response.getHeight()+4);
              //  layout.draw(canvas);                             
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
        				intent.setClass(Mac.this,Kantu.class);  
        				//intent.putExtras(bundle2);
        				//intent.putExtras(bundle1);
        				intent.putExtras(bundle);
        				startActivity(intent);
        			}			
        		});/////////////////////////////////////////
             
            }
        }, columnWidth, 0, Config.RGB_565, null);
        request.setShouldCache(true);//设置缓存 缓存路径看我以前的帖子
        queue.add(request);
        
   
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
     
}

