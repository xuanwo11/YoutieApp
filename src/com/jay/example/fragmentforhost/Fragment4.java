package com.jay.example.fragmentforhost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
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
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Fragment4 extends Fragment {
		
	private GifImageView network_gifimageview,network_gifimageview1,network_gifimageview2,network_gifimageview3,network_gifimageview4;
	private AsyncHttpClient asyncHttpClient;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg5, container,false);
		
		return view;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	     final Button button4b = (Button) getActivity().findViewById(R.id.youxi);
	     button4b.setVisibility(View.GONE);////////VISIBLE
	     final Button button5b = (Button) getActivity().findViewById(R.id.Button05);
	     button5b.setVisibility(View.VISIBLE);
	     final Button button6b = (Button) getActivity().findViewById(R.id.Button06);
	     button6b.setVisibility(View.VISIBLE);
	     final Button button4a = (Button) getActivity().findViewById(R.id.youxia);
	     button4a.setVisibility(View.GONE);
	     final Button button5a = (Button) getActivity().findViewById(R.id.Button05a);
	     button5a.setVisibility(View.GONE);
	     final Button button6a = (Button) getActivity().findViewById(R.id.Button06a);
	     button6a.setVisibility(View.GONE);
	     final Button button7b = (Button) getActivity().findViewById(R.id.music);
	     button7b.setVisibility(View.VISIBLE);
	     final Button button7a = (Button) getActivity().findViewById(R.id.musica);
	     button7a.setVisibility(View.GONE);
	     final Button button8b = (Button) getActivity().findViewById(R.id.Button08);
	     button8b.setVisibility(View.VISIBLE);
	     final Button button8a = (Button) getActivity().findViewById(R.id.Button08a);
	     button8a.setVisibility(View.GONE);
	     network_gifimageview = (GifImageView)getActivity().findViewById(R.id.network_gifimageview);
	     network_gifimageview1 = (GifImageView)getActivity().findViewById(R.id.network_gifimageview1);
	     network_gifimageview2 = (GifImageView)getActivity().findViewById(R.id.network_gifimageview2);
	     network_gifimageview3 = (GifImageView)getActivity().findViewById(R.id.network_gifimageview3);
	     network_gifimageview4 = (GifImageView)getActivity().findViewById(R.id.network_gifimageview4);
	    /* network_gifimageview.setVisibility(View.GONE);
	     network_gifimageview1.setVisibility(View.GONE);
	     network_gifimageview3.setVisibility(View.GONE);
	     network_gifimageview4.setVisibility(View.GONE);
	     network_gifimageview2.setVisibility(View.VISIBLE);*/
	     //setImage();
	     button4b.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
            switch (v.getId()) {
	            	
	            	case R.id.youxi:
	            		 network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.VISIBLE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button4b.setVisibility(View.GONE);
	            	     button4a.setVisibility(View.VISIBLE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),net.ting.sliding.Myouxi.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     
	     button5b.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
         switch (v.getId()) {
	            	
	            	case R.id.Button05:
	            		 network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.VISIBLE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button5b.setVisibility(View.GONE);
	            	     button5a.setVisibility(View.VISIBLE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Msp.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button6b.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
         switch (v.getId()) {
	            	
	            	case R.id.Button06:
	            		 network_gifimageview.setVisibility(View.VISIBLE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button6b.setVisibility(View.GONE);
	            	     button6a.setVisibility(View.VISIBLE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Mdt.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     
	     button4a.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
         switch (v.getId()) {
	            	
	            	case R.id.youxia:
	            		 network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.VISIBLE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button4b.setVisibility(View.VISIBLE);
	            	     button4a.setVisibility(View.GONE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),net.ting.sliding.Myouxi.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     
	     button5a.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
      switch (v.getId()) {
	            	
	            	case R.id.Button05a:
	            		network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.VISIBLE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button5b.setVisibility(View.VISIBLE);
	            	     button5a.setVisibility(View.GONE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Msp.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button6a.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
      switch (v.getId()) {
	            	
	            	case R.id.Button06a:
	            		 network_gifimageview.setVisibility(View.VISIBLE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     button6b.setVisibility(View.VISIBLE);
	            	     button6a.setVisibility(View.GONE);
	            		//((MainActivity) getActivity()).finish();
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Mdt.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button7a.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
switch (v.getId()) {
	            	
	            	case R.id.musica:
	            		network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.VISIBLE);
	            	     button7b.setVisibility(View.VISIBLE);
	            	     button7a.setVisibility(View.GONE);
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Music.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button7b.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
   switch (v.getId()) {
	            	
	            	case R.id.music:
	            		network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.VISIBLE);
	            	     button7a.setVisibility(View.VISIBLE);
	            	     button7b.setVisibility(View.GONE);
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Music.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     
	     button8a.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
switch (v.getId()) {
	            	
	            	case R.id.Button08a:
	            		network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.VISIBLE);
	            	     button8b.setVisibility(View.VISIBLE);
	            	     button8a.setVisibility(View.GONE);
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Mload.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	     button8b.setOnClickListener(new View.OnClickListener() {  //////////////////////////
	            @Override  
	            public void onClick(View v) {  	                     	
switch (v.getId()) {
	            	
	            	case R.id.Button08:
	            		network_gifimageview.setVisibility(View.GONE);
	            	     network_gifimageview1.setVisibility(View.GONE);
	            	     network_gifimageview2.setVisibility(View.GONE);
	            	     network_gifimageview3.setVisibility(View.GONE);
	            	     network_gifimageview4.setVisibility(View.VISIBLE);
	            	     button8a.setVisibility(View.VISIBLE);
	            	     button8b.setVisibility(View.GONE);
	            		startActivity(new Intent(getActivity(),com.bmob.lostfound.Mload.class)); 		            
		            	
	            		break;
	            	
	            	 default:  
		                    break; 
		            	}
		            }  
		        });
	}
	public void setImage() {
		Bundle bundle0 = getActivity().getIntent().getExtras();/////////////////	            
	    //接收name值
	    String string= bundle0.getString("name");/////////////////////////////
		asyncHttpClient = new AsyncHttpClient();
		asyncHttpClient.get(string, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub

				GifDrawable drawable = null;
				try {
					drawable = new GifDrawable(arg2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				drawable.start();
				network_gifimageview.setBackgroundDrawable(drawable);

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
}

