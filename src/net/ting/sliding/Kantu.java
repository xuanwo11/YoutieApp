package net.ting.sliding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.hfp.youtie.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Kantu extends Activity {
	
	 private ImageView imageView;	
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.kantu);      
	        imageView = (ImageView) this.findViewById(R.id.imageView);
	        new Thread(access).start();
	    }
	    // ��һ���̴߳�������ͼƬ
	    private Runnable access = new Runnable() {
	        @Override
	        public void run() {
	            getImg();
	        }
	    };
	    // ���ͼƬ
	    public void getImg() {
	       
	    	Bitmap bitmap=null;///////////
	    	try {
	        	
	        	InputStream inputStream = null;
	        	Bundle bundle0 = this.getIntent().getExtras();/////////////////	            
	            //����nameֵ
	            String URL_PATH= bundle0.getString("name");/////////////////////////////	            
	            URL url = new URL(URL_PATH);
	            if (url != null) {
	                HttpURLConnection httpURLConnection = (HttpURLConnection) url
	                        .openConnection();
	                httpURLConnection.setConnectTimeout(3000);
	                httpURLConnection.setRequestMethod("GET");
	                httpURLConnection.setDoInput(true);
	                int response_code = httpURLConnection.getResponseCode();
	                if (response_code == 200) {
	                    inputStream = httpURLConnection.getInputStream();
	                }
	            }	           
	            bitmap = BitmapFactory.decodeStream(inputStream);
	            Looper.prepare();// ������ô˷�����Ҫ��Ȼ�ᱨ��
	            Message msg = new Message();
	            msg.what = 0;
	            msg.obj = bitmap;
	            handler.sendMessage(msg);	          
	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "��ȡͼƬ����", 1).show();
	        }
	    	
	    }	   


	    // ����UI��Ϣ
	    private Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            if (msg.what == 0) {
	                if (msg.obj == null) {
	                    Toast.makeText(getApplicationContext(), "ͼƬ������", 1).show();
	                } else {
	                    Toast.makeText(getApplicationContext(), "����ͼƬ...", 1)
	                            .show();
	                    setImg((Bitmap) msg.obj);	                 
	                }
	            }
	        }
	    };
	       // ����ͼƬ
	    private void setImg(Bitmap bm) {
	        imageView.setImageBitmap(bm);
	        final  Bitmap Bmp=bm;       
	        imageView.setOnClickListener(new OnClickListener() {  
	            @Override  
	            public void onClick(View v) {  
	                // TODO Auto-generated method stub  
	            	 new AlertDialog.Builder(Kantu.this).setTitle("ͼƬ����")//���öԻ������  	            	  
	                 .setMessage("�Ƿ񱣴浽�ֻ���")//������ʾ������              
	                 .setPositiveButton("��",new DialogInterface.OnClickListener() {//���ȷ����ť  	              
	                     @Override                
	                     public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�                
	                         // TODO Auto-generated method stub  
	                    	 dialog.dismiss();
	                    	// GetandSaveCurrentImage(); ///////////////	                    	
	                    	// Bitmap bitmap = getHttpBitmap(URL_PATH);
	                    	 savePicture(Bmp);//����ͼƬ��SD��
	                    	 Toast.makeText(getBaseContext(), "ͼƬ�ѱ�����SDCard/Test/�ļ���~", Toast.LENGTH_SHORT).show(); 
	                         finish();  
	              
	                     }  
	              
	                 }).setNegativeButton("��",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  	           
	                     @Override  	              
	                     public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  	              
	                         // TODO Auto-generated method stub  
	                    	 dialog.dismiss();              
	                     }                
	                 }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���              		
	    }
	    
	        });
	    }
	    /** 
	     * ��ȡ�ͱ��浱ǰ��Ļ�Ľ�ͼ 
	     */  
	    private void GetandSaveCurrentImage() {  
	        // 1.����Bitmap  
	    	Bundle bundle = this.getIntent().getExtras();/////////////////	            
            //����nameֵ
            String URL_PATH= bundle.getString("name");/////////////////////////////	
	    	Bitmap Bmp= getHttpBitmap(URL_PATH);///////////
	    	     
	       /* WindowManager windowManager = getWindowManager();  
	        Display display = windowManager.getDefaultDisplay();  
	        int w =display.getWidth();  
	        int h =display.getHeight();  
	        Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);  
	        // 2.��ȡ��Ļ  
	        View decorview = this.getWindow().getDecorView();  
	        decorview.setDrawingCacheEnabled(true);  
	        Bmp = decorview.getDrawingCache();  */
	    	
	        String SavePath = getSDCardPath() + "/AndyDemo/ScreenImage";  
	        // 3.����Bitmap 
	        String aaa = Environment.getExternalStorageDirectory()
	        	    + File.separator + "aaa.txt";
	        File file1 = new File(aaa);
            try{
	        if(!file1.exists()){           
			file1.createNewFile();	    
	        
	        }
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	      /*  int j=1;
            FileWriter pw1 = new FileWriter(file1, true);
            pw1.write(j);
            pw1.flush();
            pw1.close();   */
            
	        try {  
	        	
	            File path = new File(SavePath);  
	            // �ļ�  	            
	            InputStream fr=null;
	            fr=new FileInputStream(file1);	           
	             int i= 1+Integer.parseInt(""+fr.read());////////////////////
	                     
	             String filepath = SavePath + "/Pic_"+i+".png"; 	////////////////           
	            File file = new File(filepath);  
	            if (!path.exists()) {  
	                path.mkdirs();  
	            }  
	            if (!file.exists()) {  
	                file.createNewFile();  
	            }  
	            FileOutputStream fos = null;  
	            fos = new FileOutputStream(file);  
	            if (null != fos) {  
	            	Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);  
	                fos.flush();  
	                fos.close();  
	                Toast.makeText(this, "ͼƬ�ѱ�����SDCard/AndyDemo/ScreenImage/�ļ���~",  
	                        Toast.LENGTH_LONG).show();  
	            } 
	            
	            int s=i+1;
	            FileWriter fw = new FileWriter(file1, false);//������д
	            fw.flush();
                fw.write(s);
                fw.close();
	            ////////////////////////////// 
	            
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	       
	    }  
	  
	    /** 
	     * ��ȡSDCard��Ŀ¼·������ 
	     */  
	    private String getSDCardPath() {  
	        File sdcardDir = null;  
	        // �ж�SDCard�Ƿ����  
	        boolean sdcardExist = Environment.getExternalStorageState().equals(  
	                android.os.Environment.MEDIA_MOUNTED);  
	        if (sdcardExist) {  
	            sdcardDir = Environment.getExternalStorageDirectory();  
	        }  
	        return sdcardDir.toString();  
	    }  
	 
	    public Bitmap getHttpBitmap(String url)   {   ///////////////////////////
	    	Bitmap bitmap = null; 	    	
	    	try  {    
	    		URL pictureUrl = new URL(url); 	    		
	            InputStream in = pictureUrl.openStream();  
		        bitmap = BitmapFactory.decodeStream(in);  		       
		    	in.close();  
                 } catch (MalformedURLException e)  {    
                	 e.printStackTrace();  
                } catch (IOException e)   {   
	                 e.printStackTrace();  
	             }   
	    	return bitmap;  
	    	    }  
	    public void savePicture(Bitmap bitmap)   {  
	    	String path=Environment.getExternalStorageDirectory().getPath()+"/Test";
	    	String pictureName =path+"/" + System.currentTimeMillis()+".png";   
	    	File file = new File(path);  
	    	FileOutputStream out;  
	    	 //�ļ��в����ڣ��򴴽���  
	        if(!file.exists()){  
	            file.mkdir();  
	        }  
	    	try  {    
	    		out = new FileOutputStream(pictureName);
	    		bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);  
	    		out.flush(); 
	    		out.close();  
	    		} catch (FileNotFoundException e)   { 
	    			e.printStackTrace();  
	    			} catch (IOException e)   { 
	    				e.printStackTrace();  
	    				}  
	    	}   
	    
}
