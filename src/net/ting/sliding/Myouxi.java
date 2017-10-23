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
import com.mario.load.LoadActivity;
import com.mario.load.LoadResource;
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
import android.widget.RelativeLayout;
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


public class Myouxi extends FragmentActivity implements OnClickListener{
	
	
	
	private TextView tanchishe,wuziqi,mali,tank,paopao;////////////////////
	private ImageView she,smali,tanke,paopaotang;
	private LayoutInflater inflater;////////////////////////////lunbo
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fg4);
		RequestManager.init(this);
		inflater = LayoutInflater.from(this);///////////////////////////lunbo
		
		Button btn_back = (Button) findViewById(R.id.btn_back);///////btn_back
		tanchishe=(TextView)findViewById(R.id.tanchishe);//////
		//wuziqi=(TextView)findViewById(R.id.wuziqi);//////
		mali=(TextView)findViewById(R.id.mali);//////
		tank=(TextView)findViewById(R.id.tank);//////
		paopao=(TextView)findViewById(R.id.paopao);//////
		she=(ImageView)findViewById(R.id.she);
		smali=(ImageView)findViewById(R.id.smali);
		tanke=(ImageView)findViewById(R.id.tanke);
		paopaotang=(ImageView)findViewById(R.id.paopaotang);
		
		btn_back.setOnClickListener(this);////////////////////
		
		tanchishe.setOnClickListener(this);
		//wuziqi.setOnClickListener(this);
		mali.setOnClickListener(this);
		tank.setOnClickListener(this);
		paopao.setOnClickListener(this);
		she.setOnClickListener(this);
		smali.setOnClickListener(this);
		tanke.setOnClickListener(this);
		paopaotang.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;//////////////////////////////////
		case R.id.tanchishe:    		   		
			 
			 Intent intent0 =new Intent(this,com.example.android.snake.Snake.class); 		            
	      	 startActivity(intent0);
	    	break;
		case R.id.she:    		   		
			 
			 Intent intentz =new Intent(this,com.example.android.snake.Snake.class); 		            
	      	 startActivity(intentz);
	    	break;
	    	
   case R.id.mali:   //xiaoyuan 		   		
	 
	  Intent intent1 =new Intent(this,com.mario.load.LoadActivity.class); 	
	 
   	  startActivity(intent1);
  
          break;
   case R.id.smali:   //xiaoyuan 		   			    
		  Intent intents =new Intent(this,com.mario.load.LoadActivity.class); 	
		 
	   	  startActivity(intents);
	   	
	          break;
          
    case R.id.tank:    		   		
    	 Intent intent =new Intent(this,org.gjt.tank.MainActivity.class); 		            
      	 startActivity(intent);
    			        
          break; 	   
    case R.id.tanke:    		   		
   	 Intent intente =new Intent(this,org.gjt.tank.MainActivity.class); 		            
     	 startActivity(intente);
   			        
         break; 	
    case R.id.paopao: 
    	
   	 Intent intentp =new Intent(this,com.miaotian.BombGame.class); 		            
     	 startActivity(intentp);
   			        
         break; 	   
   case R.id.paopaotang:
	   
  	 Intent intentpt =new Intent(this,com.miaotian.BombGame.class); 		            
    	 startActivity(intentpt);
  			        
        break; 	  
         
    default:
		break;
	}
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		
	}
	
}
