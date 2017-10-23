package com.example.gif;

import java.io.IOException;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.hfp.youtie.R;

import android.app.Activity;
import android.os.Bundle;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends Activity {

	private GifImageView network_gifimageview;
	private AsyncHttpClient asyncHttpClient;
	
	//String string = "http://p1.so.qhimg.com/bdr/_240_/t01ee58aec4ea33ecfb.gif";

	// private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kandongtu);
		
		network_gifimageview = (GifImageView) findViewById(R.id.network_gifimageview);
		setImage();
	}

	public void setImage() {
		Bundle bundle0 = this.getIntent().getExtras();/////////////////	            
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
