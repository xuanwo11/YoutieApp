package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.hfp.youtie.R;
import com.hfp.youtie.entity.Lunbo;
import com.hfp.youtie.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class Fragment2 extends Fragment {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg3, container,false);
		
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		 Button button = (Button) getActivity().findViewById(R.id.Button02);
	     Button button1 = (Button) getActivity().findViewById(R.id.Button01);
	     Button button2 = (Button) getActivity().findViewById(R.id.Button03);
	     Button button3 = (Button) getActivity().findViewById(R.id.Button04);
	    
	     button.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
                  switch (v.getId()) {
	            	
	            	case R.id.Button02:
	            		
	            		Intent intent =new Intent(getActivity(),com.bmob.lostfound.Mainac.class); 		            
		            	startActivity(intent);
	            		break;	            	
	            		
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button1.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
               switch (v.getId()) {
	            		            	
	            	case R.id.Button01:
	            		Intent intent1 =new Intent(getActivity(),net.ting.sliding.MainAcc.class); 		            
		            	startActivity(intent1);
	            		
	            		break;
	            		
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button2.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
            switch (v.getId()) {
	            		            	
	            	case R.id.Button03:
	            		Intent intent1 =new Intent(getActivity(),net.ting.sliding.MainAccc.class); 		            
		            	startActivity(intent1);
	            		
	            		break;
	            		
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button3.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
               switch (v.getId()) {
	            	
	            	case R.id.Button04:
	            		
	            		Intent intent =new Intent(getActivity(),com.bmob.lostfound.Ma.class); 		            
		            	startActivity(intent);
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     
	}
}
