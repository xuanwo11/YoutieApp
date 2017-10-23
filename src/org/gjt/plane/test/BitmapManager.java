package org.gjt.plane.test;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {

	private static BitmapManager instance;
	private  BitmapManager(){};
	public static BitmapManager getInsance(){
		if(instance==null)
			instance = new BitmapManager();
		return instance;
	}
	public Bitmap[] bg=new Bitmap[2];
	public Bitmap[] plane = new Bitmap[6];
	public Bitmap bg1;
	public Bitmap bg2;
	public Bitmap[] bullet = new Bitmap[4];
	public Bitmap[] enemy=new Bitmap[1];
	public Bitmap[] enemyDead = new Bitmap[6];
	public Bitmap blood;
	public Bitmap bomb;
	public void loadSource(Context context){
	}
	
	
	public Bitmap loadSource(Context context,int res){
		Bitmap bit = null;
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inPreferredConfig = Bitmap.Config.ARGB_4444;
		option.inInputShareable = true;
		option.inPurgeable = true;
		InputStream in = context.getResources().openRawResource(res);
		bit = BitmapFactory.decodeStream(in,null, option);
		return bit;
	}
}
