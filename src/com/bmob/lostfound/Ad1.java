package com.bmob.lostfound;

import java.io.File;
import java.util.Calendar;

import com.bmob.lostfound.bean.Found;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import entity.Person;

/**
 * 添加失物/招领信息界面
 * 
 * @ClassName: AddActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-21 上午11:41:06
 */
public class Ad1 extends BA1 implements OnClickListener {

	private ImageView ivHead;  ////////////////////////////////////////
	private static final int IMAGE_CODE = 0;// 打开相册  
	private static final int RESIZE_CODE = 2;// 调整大小  	  
	private static final String IMAGE_NAME = " ";// 图片字符串  
	LinearLayout openLayout;
	LinearLayout takeLayout;
	Found lost = new Found();//////////////
	private static final int CAMERA_IMAGE_CODE = 1;  
    private static final String IMAGE_TYPE = "image/*"; 
    private String rootUrl = null;  
    private String curFormatDateStr = null;////////
	String dateTime;
    ImageView albumPic;
	ImageView takePic;////////////////////////////////////
	
	
	EditText edit_title, edit_photo, edit_describe;
	Button btn_back, btn_true;

	TextView tv_add;
	String from = "";
	
	String old_title = "";
	String old_describe = "";
	//String old_phone = "";
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.acadd);
		
		
		// 初始化控件  
	        init();  
	  
	        // 绑定点击事件  
	        bindClick();  
	}

	 // 初始化控件  
    private void init() {  
        ivHead = (ImageView) findViewById(R.id.ivHead);  
        openLayout = (LinearLayout)findViewById(R.id.open_layout);///////////////
        takeLayout = (LinearLayout)findViewById(R.id.take_layout);
    }  
  
    // 绑定点击事件  
    private void bindClick() {  
    	  
    	 openLayout.setOnClickListener(this);////////////////////////////////
 		 takeLayout.setOnClickListener(this);
    }  
	
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		tv_add = (TextView) findViewById(R.id.tv_add);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_true = (Button) findViewById(R.id.btn_true);
		//edit_photo = (EditText) findViewById(R.id.edit_photo);
		edit_describe = (EditText) findViewById(R.id.edit_describe);
		edit_title = (EditText) findViewById(R.id.edit_title);
		
		
		
		
		//ivHead = (ImageView) findViewById(R.id.ivHead); 
		/////////////////////
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		btn_back.setOnClickListener(this);
		btn_true.setOnClickListener(this);
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		rootUrl = Environment.getExternalStorageDirectory().getPath(); //////////////////
		from = getIntent().getStringExtra("from");
		old_title = getIntent().getStringExtra("title");
		//old_phone = getIntent().getStringExtra("phone");
		old_describe = getIntent().getStringExtra("describe");
		
		edit_title.setText(old_title);
		edit_describe.setText(old_describe);
	//	edit_photo.setText(old_phone);
		
		if (from.equals("Found")) {
			tv_add.setText("发表活动");
		} else {
			tv_add.setText("发表活动");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		
		if (v == btn_true) {
			addByType();
			
		} else 
			if (v == btn_back) {
			finish();
		}	
		switch (v.getId()) {	//////////////////////////////////
		case R.id.open_layout:
			/*title = edit_title.getText().toString();
			describe = edit_describe.getText().toString();
			photo = edit_photo.getText().toString();*/
			if(from.equals("Found")){			
				//addLost();			
			
				 Intent galleryIntent = new Intent(Intent.ACTION_PICK); 
	            galleryIntent.setType("image/*");//图片  
	            startActivityForResult(galleryIntent, IMAGE_CODE);   //跳转，传递打开相册请求码  
			}else{
				//addFound();
				 Intent galleryIntent = new Intent(Intent.ACTION_PICK);
		            galleryIntent.setType("image/*");//图片  
		            startActivityForResult(galleryIntent, IMAGE_CODE);   //跳转，传递打开相册请求码  
			}  
	            break;	
		case R.id.take_layout://///////////////////////
			processCamera();
            
			break;
		default:
			break;
	    }////////////////////////////
	}
	String title = "";
	String describe = "";
	//String photo="";
	String author="";//////////////////////////
	/**根据类型添加失物/招领
	  * addByType
	  * @Title: addByType
	  * @throws
	  */
	 private void processCamera() {  //////////////////////////////////////
	        curFormatDateStr = HelpUtil.getDateFormatString(Calendar.getInstance()  
	                .getTime());  
	        String fileName = "IMG_" + curFormatDateStr + ".png";  
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
	        intent.putExtra(MediaStore.EXTRA_OUTPUT,  
	                Uri.fromFile(new File(rootUrl, fileName)));  
	        intent.putExtra("fileName", fileName);  
	        startActivityForResult(intent, CAMERA_IMAGE_CODE);  
	    }  
	 @Override  /////////////////////////////////////////
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode != RESULT_OK) {  
	            return;  
	        } else {  
	            switch (requestCode) {  
	            case IMAGE_CODE: 
	            /*	title = edit_title.getText().toString();
	    			describe = edit_describe.getText().toString();
	    			photo = edit_photo.getText().toString();
	    			
	    			if(TextUtils.isEmpty(title)){
	    				ShowToast("请填写标题");
	    				return;
	    			}
	    			if(TextUtils.isEmpty(describe)){
	    				ShowToast("请填写描述");
	    				return;
	    			}
	    			if(TextUtils.isEmpty(photo)){
	    				ShowToast("请填写手机");
	    				return;
	    			}			
	    			if(from.equals("Lost")){			
	    				//addLost();*/		
	            	Bitmap bmap = null;
	            	title = edit_title.getText().toString();
	    			describe = edit_describe.getText().toString();
	    			author=MyApplication.getInstance().getCurrentUser().getObjectId();/////////////
	    			final User user=MyApplication.getInstance().getCurrentUser();
	    			final String usernames=MyApplication.getInstance().getCurrentUser().getUsername();
	    			final String avatars=MyApplication.getInstance().getCurrentUser().getAvatar();
	    			//	photo = edit_photo.getText().toString();
	                Uri uri = data.getData();  
	               // resizeImage(uri);  
	                // 将获取到的uri转换为String型  
	                String[] images = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径  
	                Cursor cursor = this  
	                        .managedQuery(uri, images, null, null, null);  
	                int index = cursor  
	                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
	              
	                cursor.moveToFirst();  
	                String img_url = cursor.getString(index); 
	                bmap = HelpUtil.getBitmapByUrl(img_url);
	                final BmobFile icon = new BmobFile(new File(img_url)); 
	                  
	                icon.upload(this, new UploadFileListener() {  
	              	  
	    	            @Override  
	    	            public void onSuccess() {  
	    	                //Person person = new Person();  	            	
	    	            	lost.setDescribe(describe);
	    	        		//lost.setPhone(photo);
	    	        		lost.setTitle(title);
	    	        		lost.setIcon(icon); 
	    	        		lost.setAuthor(author);///////////////////////////
	    	        		lost.setUser(user);///////////////////////////
	    	        		lost.setUsernames(usernames);////////////////////////
	    	        		lost.setAtavars(avatars);//////////////////////////
	    	                lost.save(Ad1.this);	    	                
	    	                Toast.makeText(Ad1.this,"图片上传成功~",Toast.LENGTH_SHORT).show(); 
	    	                Toast.makeText(Ad1.this,"活动发表成功~",Toast.LENGTH_SHORT).show();
	    	            }  
	    	  
	    	            @Override  
	    	            public void onFailure(int arg0, String arg1) {  
	    	                Toast.makeText(Ad1.this,"上传失败,请检查网络~",Toast.LENGTH_SHORT).show();  
	    	            }

	    				@Override
	    				public void onProgress(Integer arg0) {//////////////////////////////123
	    					// TODO Auto-generated method stub
	    					
	    				}  
	    	        }); 
	                ivHead.setImageBitmap(HelpUtil.createRotateBitmap(bmap)); 
	               	                
	               btn_true.setOnClickListener(new OnClickListener(){
	    	        	
	    	        	public void onClick(View v) {
	    	        		// TODO Auto-generated method stub
	    	        		finish();
	    	        	}///////////////////	
	            	});//////////////   
	               // upload(img_url);
	    			/*}else{
	    				addFound();
	    			}*/
	                break; 
	            case CAMERA_IMAGE_CODE:///////////////////////////
	            	String url = "";  
	                Bitmap bitmap = null;
	                title = edit_title.getText().toString();
	    			describe = edit_describe.getText().toString();
	    			author=MyApplication.getInstance().getCurrentUser().getObjectId();/////////////
	    			final User user1=MyApplication.getInstance().getCurrentUser();
	    			final String usernames1=MyApplication.getInstance().getCurrentUser().getUsername();
	    			final String avatars1=MyApplication.getInstance().getCurrentUser().getAvatar();////////////////
	            	url = rootUrl + "/" + "IMG_" + curFormatDateStr + ".png";	
	            	
	            	/* File temp = new File(url);//////////////////////
	            	 Uri urls = Uri.fromFile(temp);
	            	 resizeImage(urls);*//////////////////////
	                bitmap = HelpUtil.getBitmapByUrl(url); 
	                final BmobFile icon1 = new BmobFile(new File(url));
	                icon1.upload(this, new UploadFileListener() {  
		              	  
	    	            @Override  
	    	            public void onSuccess() {  
	    	                //Person person = new Person();  	            	
	    	            	lost.setDescribe(describe);
	    	        		//lost.setPhone(photo);
	    	        		lost.setTitle(title);
	    	        		lost.setIcon(icon1); 
	    	        		lost.setAuthor(author);///////////////////////////
	    	        		lost.setUser(user1);///////////////////////////
	    	        		lost.setUsernames(usernames1);////////////////////////
	    	        		lost.setAtavars(avatars1);//////////////////////////
	    	                lost.save(Ad1.this);
	    	                Toast.makeText(Ad1.this,"图片上传成功~",Toast.LENGTH_SHORT).show();
	    	                Toast.makeText(Ad1.this,"活动发表成功~",Toast.LENGTH_SHORT).show();  
	    	            }  
	    	  
	    	            @Override  
	    	            public void onFailure(int arg0, String arg1) {  
	    	                Toast.makeText(Ad1.this,"上传失败,请检查网络~",Toast.LENGTH_SHORT).show();  
	    	            }

	    				@Override
	    				public void onProgress(Integer arg0) {//////////////////////////////123
	    					// TODO Auto-generated method stub
	    					
	    				}  
	    	        });   
	                
	                ivHead.setImageBitmap(HelpUtil.createRotateBitmap(bitmap));
                   btn_true.setOnClickListener(new OnClickListener(){
	    	        	
	    	        	public void onClick(View v) {
	    	        		// TODO Auto-generated method stub
	    	        		finish();
	    	        	}///////////////////	
	            	});//////////////            
	            	break;
	            case RESIZE_CODE:  
	                if (data !=null) {  
	                    showResizeImage(data);  
	                }  
	                break;  
				default:
					break;
	            }  	          	           
                             
	        } 
	                 
	        super.onActivityResult(requestCode, resultCode, data);  
	    }  
		

	 
	    // 判断SD卡是否存在  
	    private boolean isSdcardExisting() {  
	        final String state = Environment.getExternalStorageState();  
	        if (state.equals(Environment.MEDIA_MOUNTED)) {  
	            return true;  
	        } else {  
	            return false;  
	        }  
	    }  /////////////////////////////////////////////////////////////////
	    
	 // 重塑图片大小  
	    public void resizeImage(Uri uri) {  
	        Intent intent = new Intent("com.android.camera.action.CROP");  
	        intent.setDataAndType(uri, "image/*");  
	        intent.putExtra("crop", "true");// 可以裁剪  
	        intent.putExtra("aspectX", 1);  
	        intent.putExtra("aspectY", 1);  
	        intent.putExtra("outputX", 150);  
	        intent.putExtra("outputY", 150);  
	        intent.putExtra("return-data", true);  
	        startActivityForResult(intent, RESIZE_CODE);// 跳转，传递调整大小请求码  
	    }  
	  
	    // 获取路径  
	    private Uri getImageUri() {  
	        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),  
	                IMAGE_NAME));  
	    }  
	  
	    // 显示图片  
	    private void showResizeImage(Intent data) {  
	        Bundle extras = data.getExtras();  
	        if (extras != null) {  
	            Bitmap photo = extras.getParcelable("data");  
	            Drawable drawable = new BitmapDrawable(photo);  
	            ivHead.setImageDrawable(drawable);  
	        }  
	    }  
	  
	    // 图片上传  
	 /*   private void upload(String imgpath) {  
	        final BmobFile icon = new BmobFile(new File(imgpath)); 
	    		
	        icon.upload(this, new UploadFileListener() {  
	  
	            @Override  
	            public void onSuccess() {  
	                //Person person = new Person();  	            	
	                lost.setIcon(icon);  
	                lost.save(AddActivity.this);  
	                Toast.makeText(AddActivity.this,"图片上传成功",Toast.LENGTH_SHORT).show();  
	            }  
	  
	            @Override  
	            public void onFailure(int arg0, String arg1) {  
	                Toast.makeText(AddActivity.this,"图片上传失败",Toast.LENGTH_SHORT).show();  
	            }

				@Override
				public void onProgress(Integer arg0) {//////////////////////////////123
					// TODO Auto-generated method stub
					
				}  
	        }); 
	        	
	    }  *///////////////////////////////////////////////////
	    
	    
	
	private void addByType(){
		title = edit_title.getText().toString();
		describe = edit_describe.getText().toString();
		//photo = edit_photo.getText().toString();
		
		if(TextUtils.isEmpty(title)){
			ShowToast("请填写主题~");
			return;
		}
		if(TextUtils.isEmpty(describe)){
			ShowToast("请编辑内容");
			return;
		}
		/*if(TextUtils.isEmpty(photo)){
			ShowToast("请填写手机");
			return;
		}*/
		if(from.equals("Found")){			
			addFound();			
		}else{
			addFound();
		}
		
		
	}

	/*private void addLost(){
		
		
		//Person lost = new Person();
		
		lost.setDescribe(describe);
		lost.setPhone(photo);
		lost.setTitle(title);
		
		lost.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("失物信息添加成功!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("添加失败:"+arg0);
			}
		});
	}*/
	
	private void addFound(){
		author=MyApplication.getInstance().getCurrentUser().getObjectId();/////////////
		final User user=MyApplication.getInstance().getCurrentUser();
		final String usernames=MyApplication.getInstance().getCurrentUser().getUsername();
		final String avatars=MyApplication.getInstance().getCurrentUser().getAvatar();
		Found found = new Found();
		found.setDescribe(describe);
		//found.setPhone(photo);
		found.setTitle(title);
		found.setAuthor(author);///////////////////////////
		found.setUser(user);///////////////////////////
		found.setUsernames(usernames);////////////////////////
		found.setAtavars(avatars);//////////////////////////
		
		found.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("发表成功!");
				setResult(RESULT_OK);
				finish();
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub
				ShowToast("发表失败:"+arg0);
			}
		});
	}
}

