package com.bmob.lostfound;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.hfp.youtie.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import entity.Comment;


public class M3 extends Activity implements View.OnClickListener {

    private Button comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private ListView comment_list;
    private AdapterComment adapterComment;
    private List<Comment> data;

    
    private Context sContext;
	private List<String> sNewsList;
	private NewsAdapter sNewsAdapter;
	private com.hfp.youtie.ImgListView sListView;
	 public Drawable drawable;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acm1);//acomment
       // initView();     
        //Bundle bundle = M2.this.getIntent().getExtras();
        comment_list = (ListView) findViewById(R.id.xListView);
        
       
        Bitmap bitmap = null;
        //����nameֵ
        //String[] item= bundle.getStringArray("name");
        //SerializableMap serializableMap = (SerializableMap) bundle.get("name"); 
        HashMap map = (HashMap) getIntent().getSerializableExtra("map");///////////////////
       
        Set<String> set = map.keySet();
		  Iterator<String> it = set.iterator();
		  String[][] ss = new String[map.size()][2];
		  for (int i = 0; i < map.size(); i++) {
		   ss[i][0] = it.next();
		   ss[i][1] = (String) map.get(ss[i][0]);					  
		   System.out.print(ss[i][1]+"\n");
		  }
		  
		  System.out.println(ss.length);
		
		  String [] urls = new String[(ss.length)*2];
		 String [] content= new String[(ss.length)*2];
	        for (int i = 0; i < ss.length; i++) {
	            for (int j = 0; j < ss[i].length; j++) {
	                urls[i+j] = ss[i][j+1];				                    			                
	                System.out.println(urls[i+j]);				               	     					        
	                j++;
	            }
	           content[i]=ss[i][0];
	          System.out.println(content[i]);
	        }
	        
	     
	        String[] name =content;
	        String[] desc =urls;
	      /*  HashMap map1 = (HashMap) getIntent().getSerializableExtra("map1");////////////
			 Set<String> set1 = map1.keySet();/////////////////////////////
			  Iterator<String> it1 = set1.iterator();
			  String[][] ss1 = new String[map1.size()][2];
			  for (int i = 0; i < map1.size(); i++) {
			   ss1[i][0] = it1.next();
			   ss1[i][1] = (String) map1.get(ss1[i][0]);					  
			   System.out.print(ss1[i][1]+"\n");
			  }
			  
			  System.out.println(ss1.length);
						 
			String [] toux= new String[(ss1.length)*2];
		        for (int i = 0; i < ss1.length; i++) {
		            for (int j = 0; j < ss1[i].length; j++) {
		                toux[i+j] = ss1[i][j+1];				                    			                
		                System.out.println(toux[i+j]);				               	     					        
		                j++;
		            }		          
		        }///////////////////////////////////////  
     /*  Bitmap[] bitm=new Bitmap[toux.length];
       List<Map<String, Object>> listems =new  ArrayList<Map<String, Object>>();  
       for (int i = 0; i < name.length; i++) {
    	   Map<String, Object> listem = new HashMap<String, Object>(); 
           URL url = null;
           Bitmap bitmaps = null;
		try {
			url = new URL(toux[i]);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
		try {
			 HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
	            //���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������  
	            conn.setConnectTimeout(6000);  
	            //�������û��������  
	            conn.setDoInput(true);  
	            //��ʹ�û���  
	            conn.setUseCaches(false);  
	            //�����п��ޣ�û��Ӱ��  
	            //conn.connect();  
	            //�õ�������  
	            InputStream is = conn.getInputStream();  
	            //�����õ�ͼƬ  
	            bitmaps = BitmapFactory.decodeStream(is);  
	             
           bitm[i]=bitmaps;  
           //�ر�������  
           is.close();                    
           listem.put("head",bitm[i]);  //returnBitMap(touimg[i])
           listem.put("name", name[i]);  
           listem.put("desc", desc[i]);  
           listems.add(listem);  
           
       }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
       }  */
       
        sContext = this;
		//sNewsList = new ArrayList<String>();
		//geneItems();
		//sNewsList.add("");
        
		
			//sListView = (com.xgr.wonderful.ImgListView) findViewById(R.id.xListView);//��SimpleAdapter
			
		//sListView.setImageBitmap(bmap);
		
			
        List<Map<String, Object>> listems =new  ArrayList<Map<String, Object>>(); 
       for (int i = 0; i < name.length; i++) {  
            Map<String, Object> listem = new HashMap<String, Object>();                      
            listem.put("head","");  //returnBitMap(touimg[i])
            listem.put("name", name[i]);  
            listem.put("desc", desc[i]);  
            listems.add(listem);  
            
        } 
        
        SimpleAdapter simplead = new SimpleAdapter(this, listems,  
                R.layout.t, new String[] { "name", "head", "desc" },  
                new int[] {R.id.name,R.id.head,R.id.desc}); 
        
        simplead.setViewBinder(new MyViewBinder());
		//ListAdapter catalogsAdapter = new ArrayAdapter<String>(M2.this, R.layout.online_user_list_item, R.id.online_user_list_item_textview,item);
 		// sListView.setImageId(R.drawable.ic_launcher);//��������Դ�ļ���ͨ�� imglistview:headimage="@drawable/top_img"   ����ͼƬ��Ҳ��ͨ��������
		//sNewsAdapter = new NewsAdapter();
        comment_list.setAdapter(simplead);
		 comment = (Button) findViewById(R.id.btn_back);//////////////////////////     
	        comment_content = (EditText) findViewById(R.id.comment_content);    
	        setListener();
	        
		
	}
	
	 public List<HashMap<String, Object>> getListData() {
		 HashMap map1 = (HashMap) getIntent().getSerializableExtra("map1");////////////
		 Set<String> set1 = map1.keySet();/////////////////////////////
		  Iterator<String> it1 = set1.iterator();
		  String[][] ss1 = new String[map1.size()][2];
		  for (int i = 0; i < map1.size(); i++) {
		   ss1[i][0] = it1.next();
		   ss1[i][1] = (String) map1.get(ss1[i][0]);					  
		   System.out.print(ss1[i][1]+"\n");
		  }
		  
		  System.out.println(ss1.length);
					 
		String [] toux= new String[(ss1.length)*2];//*2
	        for (int i = 0; i < ss1.length; i++) {
	            for (int j = 0; j < ss1[i].length; j++) {
	                toux[i+j] = ss1[i][j+1];				                    			                
	                System.out.println(toux[i+j]);				               	     					        
	                j++;
	            }		          
	        }///////////////////////////////////////
	        HashMap map = (HashMap) getIntent().getSerializableExtra("map");///////////////////
	        
	        Set<String> set = map.keySet();
			  Iterator<String> it = set.iterator();
			  String[][] ss = new String[map.size()][2];
			  for (int i = 0; i < map.size(); i++) {
			   ss[i][0] = it.next();
			   ss[i][1] = (String) map.get(ss[i][0]);					  
			   System.out.print(ss[i][1]+"\n");
			  }
			  
			  System.out.println(ss.length);
			
			  String [] urls = new String[(ss.length)*2];//*2
			 String [] content= new String[(ss.length)*2];//*2
		        for (int i = 0; i < ss.length; i++) {
		            for (int j = 0; j < ss[i].length; j++) {
		                urls[i+j] = ss[i][j+1];				                    			                
		                System.out.println(urls[i+j]);				               	     					        
		                j++;
		            }
		           content[i]=ss[i][0];
		          System.out.println(content[i]);
		        }
		        
		     String[] touimg=toux;
		        String[] name =content;
		        String[] desc =urls;///////////////////////////////////////////////
	        
		        
	        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	        HashMap<String, Object> map2 = null;
	        for (int i = 0; i < touimg.length; i++) {
	        	
	        	
	            map2 = new HashMap<String, Object>();	            
	            map2.put("head","" );//+returnBitMap(toux[i])
	            map2.put("name", name[i]);  
	            map2.put("desc", desc[i]);  
	            list.add(map2);
	        }
	        return list;
	    }
	
	 public Bitmap returnBitMap(String url) {
	        URL myFileUrl = null;
	        Bitmap bitmap = null;
	        try{  
	            myFileUrl = new URL(url);  
	            //�������  
	            HttpURLConnection conn=(HttpURLConnection)myFileUrl.openConnection();  
	            //���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������  
	            conn.setConnectTimeout(6000);  
	            //�������û��������  
	            conn.setDoInput(true);  
	            //��ʹ�û���  
	            conn.setUseCaches(false);  
	            //�����п��ޣ�û��Ӱ��  
	            conn.connect();  
	            //�õ�������  
	            InputStream is = conn.getInputStream();  
	            //�����õ�ͼƬ  
	            bitmap = BitmapFactory.decodeStream(is);  
	            //�ر�������  
	            is.close();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        return bitmap;
	    }
	

	private void geneItems() {
		for (int i = 0; i != 10; ++i) {
			sNewsList.add(""+i);
		}
	}

    private void initView() {

    	 Bundle bundle = this.getIntent().getExtras();
         //����nameֵ
         String[] item= bundle.getStringArray("name");
        // SerializableMap serializableMap = (SerializableMap) bundle.get("name"); 
         
        // ��ʼ�������б�
        comment_list = (ListView) findViewById(R.id.comment_list);
        // ��ʼ������      
        //data = new ArrayList<Comment>();
        // ��ʼ��������       
        ListAdapter catalogsAdapter = new ArrayAdapter<String>(M3.this, R.layout.online_user_list_item, item);
        //adapterComment = new AdapterComment(getApplicationContext(), data);
        // Ϊ�����б�����������
        comment_list.setAdapter(catalogsAdapter);

        //comment = (Button) findViewById(R.id.btn_back);//////////////////////////     
        //comment_content = (EditText) findViewById(R.id.comment_content);    
        setListener();
    }

    /**
     * ���ü���
     */
    public void setListener(){
        comment.setOnClickListener(this);

       
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                // �������뷨
            	finish();          
                break;
           
           
            default:
                break;
        }
    }

    public class SerializableMap implements Serializable {  
        private Map<String,Object> map;  
        public Map<String,Object> getMap()  
        {  
            return map;  
        }  
        public void setMap(Map<String,Object> map)  
        {  
            this.map=map;  
        }  
    }  
    
    private class NewsAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public NewsAdapter() {
			mInflater = LayoutInflater.from(sContext);
		}

		@Override
		public int getCount() {
			return sNewsList.size();
		}

		@Override
		public Object getItem(int position) {
			return sNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				
				convertView = mInflater.inflate(R.layout.acomment, null);
				
		        // SerializableMap serializableMap = (SerializableMap) bundle.get("name"); 
				 Bundle bundle = M3.this.getIntent().getExtras();
		         //����nameֵ
		         String[] item= bundle.getStringArray("name");
		        // ��ʼ�������б�
		        comment_list = (ListView)convertView.findViewById(R.id.comment_list);
		        // ��ʼ������      
		        //data = new ArrayList<Comment>();
		        // ��ʼ��������       
		        ListAdapter catalogsAdapter = new ArrayAdapter<String>(M3.this, R.layout.online_user_list_item, item);
		        //adapterComment = new AdapterComment(getApplicationContext(), data);
		        // Ϊ�����б�����������
		        comment_list.setAdapter(catalogsAdapter);

		        comment = (Button) findViewById(R.id.btn_back);//////////////////////////     
		        //comment_content = (EditText) findViewById(R.id.comment_content);
		       
		        

		        setListener();
			} else {
				
			}
			
			return convertView;
		}

		
	}
    /**
     * ��������
     
    public void sendComment(){
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "���۲���Ϊ�գ�", Toast.LENGTH_SHORT).show();
        }else{
            // ������������
            Comment comment = new Comment();
            comment.setName("������"+(data.size()+1)+"��");
            comment.setContent(comment_content.getText().toString());
            adapterComment.addComment(comment);
            // �����꣬��������
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "���۳ɹ���", Toast.LENGTH_SHORT).show();
        }
    }*/

	
}

