package com.bmob.lostfound;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

import com.bmob.lostfound.bean.Found;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.User;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import android.provider.MediaStore.Video.VideoColumns;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
import entity.Shipin;

/**
 * 添加失物/招领信息界面
 * 
 * @ClassName: AddActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-21 上午11:41:06
 */
public class Ad2 extends BA1 implements OnClickListener {

	private ImageView ivHead;  ////////////////////////////////////////
	
	private static final int RESIZE_CODE = 2;// 调整大小  	  
	private static final String IMAGE_NAME = " ";// 图片字符串  
	LinearLayout openLayout;
	LinearLayout takeLayout;
	Shipin lost = new Shipin();//////////////
  
    private static final String IMAGE_TYPE = "image/*"; 
    private String rootUrl = null;  
    private String curFormatDateStr = null;////////
	String dateTime;
    ImageView albumPic;
	ImageView takePic;////////////////////////////////////
	private static final int VIDEO_CAPTURE0 = 0;
	private static final int VIDEO_CAPTURE1 = 1;
	
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
		setContentView(R.layout.avadd);
		
		
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
			tv_add.setText("发表视频");
		} else {
			tv_add.setText("发表视频");
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
			
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("video/*");

				startActivityForResult(intent, VIDEO_CAPTURE1);
			}else{
				//addFound();
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("video/*");

				startActivityForResult(intent, VIDEO_CAPTURE1);
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
		 Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 6);

			startActivityForResult(intent, VIDEO_CAPTURE0); 
	    }  
	 @Override  /////////////////////////////////////////
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode != RESULT_OK) {  
	            return;  
	        } else {  
	            switch (requestCode) {  
	            case VIDEO_CAPTURE1: 
	            	
	            	title = edit_title.getText().toString();
	    			describe = edit_describe.getText().toString();
	    			author=MyApplication.getInstance().getCurrentUser().getObjectId();/////////////
	    			final User user=MyApplication.getInstance().getCurrentUser();
	    			final String usernames=MyApplication.getInstance().getCurrentUser().getUsername();
	    			final String avatars=MyApplication.getInstance().getCurrentUser().getAvatar();
	            	Uri uri = data.getData();
	    			File file = getFileByUri(uri);
	    			 MediaMetadataRetriever mmr=new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象  
	    			 mmr.setDataSource(file.getAbsolutePath());
	    			 Bitmap bitmap=mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
	    			 String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
	    			 Log.d("ddd","duration=="+duration);
	    			int int_duration= Integer.parseInt(duration);
	    			
	    			 if(int_duration>600000){
	    				 Toast.makeText(getApplicationContext(), "视频时长已超过5分钟，请重新选择", Toast.LENGTH_SHORT).show();
	    				 
	    			 }
	    			
	                final BmobFile icon = new BmobFile(file); ///////////////
	                  
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
	    	                lost.save(Ad2.this);	    	                
	    	                Toast.makeText(Ad2.this,"视频上传成功~",Toast.LENGTH_SHORT).show(); 
	    	                Toast.makeText(Ad2.this,"视频发表成功~",Toast.LENGTH_SHORT).show();
	    	            }  
	    	  
	    	            @Override  
	    	            public void onFailure(int arg0, String arg1) {  
	    	                Toast.makeText(Ad2.this,"上传失败,请检查网络~",Toast.LENGTH_SHORT).show();  
	    	            }

	    				@Override
	    				public void onProgress(Integer arg0) {//////////////////////////////123
	    					// TODO Auto-generated method stub
	    					
	    				}  
	    	        }); 
	                //ivHead.setImageBitmap(HelpUtil.createRotateBitmap(bmap)); 
	                ivHead.setImageBitmap(bitmap);               
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
	            case VIDEO_CAPTURE0:///////////////////////////
	            	Bitmap bitmap1=null;
	            	Uri uri1 = data.getData();
	    			Cursor cursor = this.getContentResolver().query(uri1, null, null, null, null);
	    			title = edit_title.getText().toString();
	    			describe = edit_describe.getText().toString();
	    			author=MyApplication.getInstance().getCurrentUser().getObjectId();/////////////
	    			final User user1=MyApplication.getInstance().getCurrentUser();
	    			final String usernames1=MyApplication.getInstance().getCurrentUser().getUsername();
	    			final String avatars1=MyApplication.getInstance().getCurrentUser().getAvatar();////////////////
	    			if (cursor != null && cursor.moveToNext()) {
	    				int id = cursor.getInt(cursor.getColumnIndex(VideoColumns._ID));
	    				String filePath = cursor.getString(cursor.getColumnIndex(VideoColumns.DATA));
	    				 bitmap1 = Thumbnails.getThumbnail(getContentResolver(), id, Thumbnails.MICRO_KIND, null);
	    				// ThumbnailUtils类2.2以上可用
	    				// Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
	    				// Thumbnails.MICRO_KIND);
	    				
	    				Log.d("ddd", "filepath==" + filePath);
	    				File file1=new File(filePath);
	    				cursor.close();
	                final BmobFile icon1 = new BmobFile(file1);
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
	    	                lost.save(Ad2.this);
	    	                Toast.makeText(Ad2.this,"视频上传成功~",Toast.LENGTH_SHORT).show();
	    	                Toast.makeText(Ad2.this,"视频发表成功~",Toast.LENGTH_SHORT).show();  
	    	            }  
	    	  
	    	            @Override  
	    	            public void onFailure(int arg0, String arg1) {  
	    	                Toast.makeText(Ad2.this,"上传失败,请检查网络~",Toast.LENGTH_SHORT).show();  
	    	            }

	    				@Override
	    				public void onProgress(Integer arg0) {//////////////////////////////123
	    					// TODO Auto-generated method stub
	    					
	    				}  
	    	        });   
	                
	                ivHead.setImageBitmap(bitmap1);
    				
                   btn_true.setOnClickListener(new OnClickListener(){
	    	        	
	    	        	public void onClick(View v) {
	    	        		// TODO Auto-generated method stub
	    	        		finish();
	    	        	}///////////////////	
	            	});////////////// 
	    			}
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
		public void cun(Bitmap bitmap) {
			String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".png";
			FileOutputStream b = null;
			File file = new File("/sdcard/myImage/");
			file.mkdirs();// 创建文件夹
		
		}

		public File getFileByUri(Uri uri) {
			String path = null;
			if ("file".equals(uri.getScheme())) {
				path = uri.getEncodedPath();
				if (path != null) {
					path = Uri.decode(path);
					ContentResolver cr = this.getContentResolver();
					StringBuffer buff = new StringBuffer();
					buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
					Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI,
							new String[] { Images.ImageColumns._ID, Images.ImageColumns.DATA }, buff.toString(), null,
							null);
					int index = 0;
					int dataIdx = 0;
					for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
						index = cur.getColumnIndex(Images.ImageColumns._ID);
						index = cur.getInt(index);
						dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
						path = cur.getString(dataIdx);
					}
					cur.close();
					if (index == 0) {
					} else {
						Uri u = Uri.parse("content://media/external/images/media/" + index);
						System.out.println("temp uri is :" + u);
					}
				}
				if (path != null) {
					return new File(path);
				}
			} else if ("content".equals(uri.getScheme())) {
				// 4.2.2以后
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
				if (cursor.moveToFirst()) {
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					path = cursor.getString(columnIndex);
				}
				cursor.close();

				return new File(path);
			} else {
				Log.i(TAG, "Uri Scheme:" + uri.getScheme());
			}
			return null;
		}
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

