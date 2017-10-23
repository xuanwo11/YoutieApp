package com.cn.test.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hfp.youtie.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private ListView mListView;
	private List<VideoInfo> videoList=new ArrayList<VideoInfo>();
	private VideoInfo video;
	private myAdapter adapter;
	private int currentIndex=-1;
	private String url1="http://bmob-cdn-6280.b0.upaiyun.com/2016/11/15/dc7c275a40b74a0780fbc118b383df3d.mp4";
	private String url2="http://bmob-cdn-6280.b0.upaiyun.com/2016/11/15/e9fec81840047bf18010bd4eb7b86e13.mp4";
	private String url3="http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/HollinsGlassmov-3462827_8382664.mp4";

	//http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/HollinsGlassmov-3462827_8382664.mp4
	private VideoView mVideoView;
	MediaController mMediaCtrl;
	private int playPosition=-1;
	private boolean isPaused=false;
	private boolean isPlaying=false;
	Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kanshipin);
		//构造视频数据
		Bundle bundle0 = this.getIntent().getExtras();/////////////////	            
        //接收name值
        String URL_PATH= bundle0.getString("name");/////////////////////////////
        ImageView img=(ImageView)findViewById(R.id.video_image);
		Bitmap bitmap = createVideoThumbnail(URL_PATH, 400, 400);
		video=new VideoInfo(URL_PATH,"",bitmap);
		videoList.add(video);
		//img.setImageBitmap(bitmap);
		//BitmapDrawable bd= new BitmapDrawable(bitmap);
		/*for(int i=0;i<3;i++){
			if(i==0){
				ImageView img=(ImageView)findViewById(R.id.video_image);
				Bitmap bitmap = createVideoThumbnail(url1, 400, 400);
				
				//img.setImageBitmap(bitmap);
				video=new VideoInfo(url1,"迎新--《小幸运》"+i,bitmap);
				
			}
			if(i==1){
				ImageView img=(ImageView)findViewById(R.id.video_image);
				Bitmap bitmap1 = createVideoThumbnail(url2, 400, 400);
				video=new VideoInfo(url2,"迎新热点"+i,bitmap1);//R.drawable.video1
			}
			if(i==2){
				ImageView img=(ImageView)findViewById(R.id.video_image);
				Bitmap bitmap2 = createVideoThumbnail(url3, 400, 400);
				video=new VideoInfo(url3,"迎新热点"+i,bitmap2);//R.drawable.video1
			}
			videoList.add(video);
		}*/
		btn_back=(Button)findViewById(R.id.btn_back);
		mListView=(ListView) findViewById(R.id.video_listview);
		adapter = new myAdapter();
		mListView.setAdapter(adapter);
		btn_back.setOnClickListener(new OnClickListener() {  
		    @Override  
		    public void onClick(View v) {  
		    	finish();//////////////////////////////////     	
		            }  
		        });  
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
						if((currentIndex<firstVisibleItem || currentIndex>mListView.getLastVisiblePosition())&&isPlaying){
							System.out.println("滑动的："+mVideoView.toString());
								playPosition=mVideoView.getCurrentPosition();
								mVideoView.pause();
								mVideoView.setMediaController(null);
								isPaused=true;
								isPlaying=false;
								System.out.println("视频已经暂停："+playPosition);
							}
						}	
		});
/*		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentIndex=position;
				adapter.notifyDataSetChanged();
			}
		});*/
	}
	private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }
	class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stubs
			return videoList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return videoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			final int mPosition=position;
			if(convertView==null){
                Log.d("1",convertView+"convertView==null");
				convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.video_item_layout, null);
				holder=new ViewHolder();
				holder.videoImage=(ImageView) convertView.findViewById(R.id.video_image);
				holder.videoNameText=(TextView)convertView.findViewById(R.id.video_name_text);
				holder.videoPlayBtn=(ImageButton)convertView.findViewById(R.id.video_play_btn);
				holder.mProgressBar=(ProgressBar) convertView.findViewById(R.id.progressbar);
				convertView.setTag(holder);
			}else{
                Log.d("2",convertView+"convertView!=null");
				holder=(ViewHolder) convertView.getTag();
			}
			
			holder.videoImage.setImageBitmap(videoList.get(position).getVideoImage());
			//holder.videoImage.setImageBitmap(bitmap);
			holder.videoNameText.setText(videoList.get(position).getVideoName());
			holder.videoPlayBtn.setVisibility(View.VISIBLE);
			holder.videoImage.setVisibility(View.VISIBLE);
			holder.videoNameText.setVisibility(View.VISIBLE);
			mMediaCtrl = new MediaController(MainActivity.this,false);
			if(currentIndex == position){
                Log.d("3",currentIndex+"currentIndex == position");
				holder.videoPlayBtn.setVisibility(View.INVISIBLE);
				holder.videoImage.setVisibility(View.INVISIBLE);
				holder.videoNameText.setVisibility(View.INVISIBLE);
				
				if(isPlaying || playPosition==-1){
					if(mVideoView!=null){
                        Log.d("4",mVideoView+"mVideoView!=null");

                        mVideoView.setVisibility(View.GONE);
						mVideoView.stopPlayback();
						holder.mProgressBar.setVisibility(View.GONE);
					}
				}
				mVideoView=(VideoView) convertView.findViewById(R.id.videoview);
				mVideoView.setVisibility(View.VISIBLE);
				mMediaCtrl.setAnchorView(mVideoView);
				mMediaCtrl.setMediaPlayer(mVideoView);
		        mVideoView.setMediaController(mMediaCtrl);
		        mVideoView.requestFocus();
		        holder.mProgressBar.setVisibility(View.VISIBLE);
				if(playPosition>0 && isPaused){
					mVideoView.start();
					isPaused=false;
					isPlaying=true;
					holder.mProgressBar.setVisibility(View.GONE);
				}else{
			        mVideoView.setVideoPath(videoList.get(mPosition).getUrl());
			        isPaused=false;
			        isPlaying=true;
			        System.out.println("播放新的视频");
				}
				mVideoView.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						if(mVideoView!=null){
							mVideoView.seekTo(0);
							mVideoView.stopPlayback();
							currentIndex=-1;
							isPaused=false;
					        isPlaying=false;
					        holder.mProgressBar.setVisibility(View.GONE);
							adapter.notifyDataSetChanged();
						}
					}
				});
				mVideoView.setOnPreparedListener(new OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mp) {
						holder.mProgressBar.setVisibility(View.GONE);
						mVideoView.start();
					}
				});
				
			}else{
				holder.videoPlayBtn.setVisibility(View.VISIBLE);
				holder.videoImage.setVisibility(View.VISIBLE);
				holder.videoNameText.setVisibility(View.VISIBLE);
				holder.mProgressBar.setVisibility(View.GONE);
			}
			
			holder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					currentIndex=mPosition;
					playPosition=-1;
					adapter.notifyDataSetChanged();
				}
			});
			return convertView;
		};
	}
	static class ViewHolder{
		ImageView videoImage;
		TextView videoNameText;
		ImageButton videoPlayBtn;
		ProgressBar mProgressBar;
	}
	static class VideoInfo {
		private String url;
		private String videoName;
		private Bitmap videoImage;
		public VideoInfo(String url,String name,Bitmap path) {
			this.videoName=name;
			this.videoImage=path;
			this.url=url;
		}
		public String getVideoName() {
			return videoName;
		}
		public void setVideoName(String videoName) {
			this.videoName = videoName;
		}
		public Bitmap getVideoImage() {
			return videoImage;
		}
		public void setVideoImage(Bitmap videoImage) {
			this.videoImage = videoImage;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	} 
}
