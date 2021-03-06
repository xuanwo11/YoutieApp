package com.wwj.download;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.loopj.android.http.AsyncHttpClient;
import com.wwj.download.util.Player;
import com.wwj.net.download.DownloadProgressListener;
import com.wwj.net.download.FileDownloader;
import com.hfp.youtie.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import pl.droidsonroids.gif.GifImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Mainload extends Activity {
	private static final int PROCESSING = 1;
	private static final int FAILURE = -1;

	private EditText pathText; // url地址
	private TextView resultView;
	private Button downloadButton;
	private Button stopButton;
	private ProgressBar progressBar;
	private Button playBtn;
	private Player player;
	private SeekBar musicProgress;

	private Handler handler = new UIHandler();
	private GifImageView network_gifimageview;
	private AsyncHttpClient asyncHttpClient;
	private final class UIHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PROCESSING: // 更新进度
				progressBar.setProgress(msg.getData().getInt("size"));
				float num = (float) progressBar.getProgress()
						/ (float) progressBar.getMax();
				int result = (int) (num * 100); // 计算进度
				resultView.setText(result + "%");
				if (progressBar.getProgress() == progressBar.getMax()) { // 下载完成
					Toast.makeText(getApplicationContext(), R.string.success,
							Toast.LENGTH_SHORT).show();
				}
				break;
			case FAILURE: // 下载失败
				Toast.makeText(getApplicationContext(), R.string.error,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acmain1);
		pathText = (EditText) findViewById(R.id.path);
		resultView = (TextView) findViewById(R.id.resultView);
		downloadButton = (Button) findViewById(R.id.downloadbutton);
		stopButton = (Button) findViewById(R.id.stopbutton);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		Button btn_back=(Button)findViewById(R.id.btn_back);////////
		ButtonClickListener listener = new ButtonClickListener();
		downloadButton.setOnClickListener(listener);
		stopButton.setOnClickListener(listener);
		btn_back.setOnClickListener(listener);////////////
		playBtn = (Button) findViewById(R.id.btn_online_play);
		playBtn.setOnClickListener(listener);
		musicProgress = (SeekBar) findViewById(R.id.music_progress);
		player = new Player(musicProgress);
		musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		network_gifimageview = (GifImageView)findViewById(R.id.network_gifimageview);/////
		
		
	}

	private final class ButtonClickListener implements View.OnClickListener {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			
			case R.id.downloadbutton: // 开始下载
				// http://abv.cn/music/光辉岁月.mp3，可以换成其他文件下载的链接
				Bundle bundle0 = getIntent().getExtras();/////////////////	            
			    //接收name值
			    String string= bundle0.getString("name");/////////////////////////////
				String path = string;//pathText.getText().toString();
				String filename = path.substring(path.lastIndexOf('/') + 1);

				try {
					// URL编码（这里是为了将中文进行URL编码）
					filename = URLEncoder.encode(filename, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// File savDir =
					// Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
					// 保存路径
					File savDir = Environment.getExternalStorageDirectory();
					download(path, savDir);
				} else {
					Toast.makeText(getApplicationContext(),
							R.string.sdcarderror, Toast.LENGTH_SHORT).show();
				}
				downloadButton.setEnabled(false);
				stopButton.setEnabled(true);
				break;
			case R.id.stopbutton: // 暂停下载
				exit();
				Toast.makeText(getApplicationContext(),
						"已暂停下载~", Toast.LENGTH_SHORT).show();
				downloadButton.setEnabled(true);
				stopButton.setEnabled(false);
				break;
			case R.id.btn_online_play:
				Bundle bundle1 = getIntent().getExtras();/////////////////	            
			    //接收name值
			    final String string1= bundle1.getString("name");/////////////////////////////
				new Thread(new Runnable() {

					@Override
					public void run() {
						
						player.playUrl(string1);
					}
				}).start();
				break;
				
			case R.id.btn_back:
				finish();
			}
		}

		/*
		 * 由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
		 * 此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
		 * 所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
		 * 导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
		 */
		private DownloadTask task;

		private void exit() {
			if (task != null)
				task.exit();
		}

		private void download(String path, File savDir) {
			task = new DownloadTask(path, savDir);
			new Thread(task).start();
		}

		/**
		 * 
		 * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
		 * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
		 * 
		 */
		private final class DownloadTask implements Runnable {
			private String path;
			private File saveDir;
			private FileDownloader loader;

			public DownloadTask(String path, File saveDir) {
				this.path = path;
				this.saveDir = saveDir;
			}

			/**
			 * 退出下载
			 */
			public void exit() {
				if (loader != null)
					loader.exit();
			}

			DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
				@Override
				public void onDownloadSize(int size) {
					Message msg = new Message();
					msg.what = PROCESSING;
					msg.getData().putInt("size", size);
					handler.sendMessage(msg);
				}
			};

			public void run() {
				try {
					// 实例化一个文件下载器
					loader = new FileDownloader(getApplicationContext(), path,
							saveDir, 3);
					// 设置进度条最大值
					progressBar.setMax(loader.getFileSize());
					loader.download(downloadProgressListener);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(handler.obtainMessage(FAILURE)); // 发送一条空消息对象
				}
			}
		}
	}

	class SeekBarChangeEvent implements OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			this.progress = progress * player.mediaPlayer.getDuration()
					/ seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			player.mediaPlayer.seekTo(progress);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.stop();
			player = null;
		}
	}

}
