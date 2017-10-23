package org.gjt.tank;

import java.io.InputStream;

import com.hfp.youtie.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapManager {
	private static BitmapManager instance;
	private BitmapManager(){};
	public static BitmapManager getInstance(){
		if(instance==null)
			instance = new BitmapManager();
		return instance;
	}
	public Bitmap bg;
	public Bitmap gameover;
	public Bitmap tank_body;
	public Bitmap tank_chassis;
	public Bitmap tank_shadow;
	public Bitmap tank_wheel1;
	public Bitmap tank_wheel2;
	public Bitmap tank_gun;
	public Bitmap bullet1;
	public Bitmap bullet2;
	public Bitmap bullet3;
	public Bitmap blood;
	public Bitmap fly;
	public Bitmap[] tank = new Bitmap[4];
	public Bitmap[] explode1 = new Bitmap[9];
	public Bitmap[] explode2 = new Bitmap[5];
	public void loadSource(Context context){
		bg = loadSource(context, R.drawable.gdbj);
		gameover = loadSource(context, R.drawable.gameover);
		tank_body = loadSource(context, R.drawable.tkbody);
		tank_chassis = loadSource(context, R.drawable.chassis);
		tank_shadow = loadSource(context, R.drawable.shadow);
		tank_wheel1 = loadSource(context, R.drawable.wheel);
		tank_wheel2 = loadSource(context, R.drawable.wheel2);
		tank_gun = loadSource(context, R.drawable.gun);
		bullet1 = loadSource(context, R.drawable.zidan1);
		bullet2 = loadSource(context, R.drawable.zidan2);
		bullet3 = loadSource(context, R.drawable.zidan3);
		blood = loadSource(context, R.drawable.xuekuai);
		fly = loadSource(context, R.drawable.tk004);
		
		for(int i=0;i<tank.length;i++){
			tank[i] = loadSource(context, R.drawable.tk46+i);
		}
		for(int i=0;i<explode1.length;i++){
			explode1[i] = loadSource(context, R.drawable.baozha1+i);
		}
		for(int i=0;i<explode2.length;i++){
			explode2[i] = loadSource(context, R.drawable.bz1+i);
		}
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
