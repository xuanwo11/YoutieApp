package com.hfp.youtie.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bmob.lostfound.i.IPopupItemClick;
import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMGroupChangeListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.TextMessageBody;

import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.activity.ChatAllHistoryFragment;
import com.easemob.chatuidemo.activity.ContactlistFragment;
import com.easemob.chatuidemo.activity.GroupsActivity;
import com.easemob.chatuidemo.activity.SettingsFragment;
import com.easemob.chatuidemo.db.InviteMessgeDao;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.InviteMessage;
import com.easemob.chatuidemo.domain.InviteMessage.InviteMesageStatus;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;
import com.easemob.util.EMLog;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;
import com.jay.example.fragmentforhost.Fragment1;
import com.jay.example.fragmentforhost.Fragment2;
import com.jay.example.fragmentforhost.Fragment4;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
//import com.umeng.analytics.MobclickAgent;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.LogUtils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class MainActivity extends SlidingFragmentActivity implements OnClickListener{

    public static final String TAG = "MainActivity";
	private NaviFragment naviFragment;
	private ImageView leftMenu;
	private ImageView rightMenu;
	private SlidingMenu mSlidingMenu;
	private Button huihua;
	private Button tongxunlu;
	private Button shezhi;
	PopupWindow morePop;
	protected int mScreenWidth;
	protected int mScreenHeight;
	RelativeLayout fragment_container;//////////////////
	//定义3个Fragment的对象
		private Fragment1 fg1;////////////////////////////
		private Fragment2 fg3;
		private Fragment4 fg4;
		private com.hfp.youtie.ui.SettingsFragment SettingsFragment;
		//帧布局对象,就是用来存放Fragment的容器
		private FrameLayout flayout;
		//定义底部导航栏的三个布局
		private RelativeLayout course_layout;
		private RelativeLayout found_layout;
		private RelativeLayout settings_layout;
		private RelativeLayout liaotian;
		//定义底部导航栏中的ImageView与TextView
		private ImageView course_image;
		private ImageView found_image;
		private ImageView settings_image;
		private ImageView liaotian_image;
		private TextView course_text;
		private TextView settings_text;
		private TextView found_text;
		private TextView liaotian_text;
		//private TextView liaotian;//////
		//定义要用的颜色值
		private int whirt = 0xFFFFFFFF;
		private int gray = 0xFF7597B3;
		private int blue =0xFF0AB2FB;
		//定义FragmentManager对象
		FragmentManager fManager;//////////////////
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//获取当前屏幕宽高
		setContentView(R.layout.center_frame);
		Bmob.initialize(this, "e06192b54b3f21069ab16fbdee95de6c");
		leftMenu = (ImageView)findViewById(R.id.topbar_menu_left);
		rightMenu = (ImageView)findViewById(R.id.topbar_menu_right);
		fragment_container = (RelativeLayout) findViewById(R.id.fragment_container);
		//liaotian= (TextView)findViewById(R.id.liaotian);////////////
		leftMenu.setOnClickListener(this);
		rightMenu.setOnClickListener(this);
		fragment_container.setOnClickListener(this);////////
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;////////////////////
		//liaotian.setOnClickListener(this);////////
		fManager = getSupportFragmentManager();///////////////////////////
		initFragment();

		
	}
	

	
	

	private void initFragment() {

		mSlidingMenu = getSlidingMenu();
		setBehindContentView(R.layout.frame_navi); // 给滑出的slidingmenu的fragment制定layout
		naviFragment = new NaviFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_navi, naviFragment).commit();
		// 设置slidingmenu的属性
		mSlidingMenu.setMode(SlidingMenu.LEFT);// 设置slidingmeni从哪侧出现
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);// 只有在边上才可以打开
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);// 偏移量
		mSlidingMenu.setFadeEnabled(true);
		mSlidingMenu.setFadeDegree(0.5f);
		mSlidingMenu.setMenu(R.layout.frame_navi);
		

		Bundle mBundle = null;
		// 导航打开监听事件
		mSlidingMenu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
			}
		});
		// 导航关闭监听事件
		mSlidingMenu.setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {
			}
		});
		
		course_image = (ImageView) findViewById(R.id.course_image);//////////////////////////
		found_image = (ImageView) findViewById(R.id.found_image);
		settings_image = (ImageView) findViewById(R.id.setting_image);
		liaotian_image=(ImageView) findViewById(R.id.liaotian_image);
		course_text = (TextView) findViewById(R.id.course_text);
		found_text = (TextView) findViewById(R.id.found_text);
		settings_text = (TextView) findViewById(R.id.setting_text);
		liaotian_text=(TextView) findViewById(R.id.liaotian_text);//////////////////////
		course_layout = (RelativeLayout) findViewById(R.id.course_layout);
		liaotian = (RelativeLayout) findViewById(R.id.liaotian);//////////////////////
		found_layout = (RelativeLayout) findViewById(R.id.found_layout);
		settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
		course_layout.setOnClickListener(this);
		liaotian.setOnClickListener(this);//////////////////
		found_layout.setOnClickListener(this); 
		settings_layout.setOnClickListener(this);
		FragmentTransaction transaction = fManager.beginTransaction();
		//transaction.show(fg1);
	}

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    	
    	
        switch(v.getId()) {
            case R.id.topbar_menu_left:
                mSlidingMenu.toggle();
                break;
           /* case R.id.liaotian:
            	 Intent intent0 = new Intent();
                 intent0.setClass(MainActivity.this, com.easemob.chatuidemo.activity.MainActivity.class);//EditActivity
                 startActivity(intent0);
                 overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
                break; */
           
    			
            case R.id.topbar_menu_right:
              //当前用户登录
                BmobUser currentUser = BmobUser.getCurrentUser(MainActivity.this);
                if (currentUser != null) {
                    // 允许用户使用应用,即有了用户的唯一标识符，可以作为发布内容的字段
                    String name = currentUser.getUsername();
                    String email = currentUser.getEmail();                   
                    LogUtils.i(TAG,"username:"+name+",email:"+email);                
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, EditActivity.class);//EditActivity
                    startActivity(intent);
                } else {
                    // 缓存用户对象为空时， 可打开用户注册界面…
                    Toast.makeText(MainActivity.this, "请先登录。",
                            Toast.LENGTH_SHORT).show();
//                  redictToActivity(mContext, RegisterAndLoginActivity.class, null);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, RegisterAndLoginActivity.class);
                    startActivity(intent);
                }
                break;
               
            case R.id.course_layout:////////////////////////////////////
    			setChioceItem(0);
    			
    			
    			break;
    	    case R.id.found_layout:
    	    	setChioceItem(1);
    	    	break;
    	    case R.id.setting_layout:
    	    	setChioceItem(2);
    	    	break;
    	    case R.id.liaotian:
    	    	//fragment_container.setVisibility(View.VISIBLE);
    	    	setChioceItem(3);
    	    	/* liaotian_image.setImageResource(R.drawable.ic_tabbar_found_pressed);  
				 liaotian_text.setTextColor(blue);
				 liaotian.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
				// hideFragments(transaction);
				 Intent intent0 = new Intent();
                 intent0.setClass(MainActivity.this, com.easemob.chatuidemo.activity.MainActivity.class);//EditActivity
                 startActivity(intent0);
                 overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);
                 break;*//////////////////////////
    	    	break;
    	    	
    	    case R.id.btn_conversation:
    	    	fragment_container.setVisibility(View.GONE);
    	    	Intent intenth =new Intent(this,com.easemob.chatuidemo.activity.Mhuihua.class); 		            
    	    	 startActivity(intenth);
    	    	break;
    	    case R.id.btn_address_list:
    	    	fragment_container.setVisibility(View.GONE);
   	    	Intent intentt =new Intent(this,com.easemob.chatuidemo.activity.Mtongxunlu.class); 		            
   	    	 startActivity(intentt);
   	    	break;	
    	    case R.id.btn_setting:
    	    	fragment_container.setVisibility(View.GONE);
      	    	Intent intents =new Intent(this,com.easemob.chatuidemo.activity.Mshezhi.class); 		            
      	    	 startActivity(intents);
      	    	break;	
            default:
                break;
        }
    }

   
	private static long firstTime;
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			MyApplication.getInstance().exit();
			super.onBackPressed();
		} else {
			ActivityUtil.show(MainActivity.this, "再按一次退出程序");
		}
		firstTime = System.currentTimeMillis();
	}
	
	//定义一个选中一个item后的处理
		public void setChioceItem(int index)
		{
			//重置选项+隐藏所有Fragment
			FragmentTransaction transaction = fManager.beginTransaction();  
			clearChioce();
			hideFragments(transaction);
			switch (index) {
			case 0:	
				fragment_container.setVisibility(View.GONE);
				course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);  
				course_text.setTextColor(blue);
				course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
	           /* if (fg1 == null) {  
	                // 如果fg1为空，则创建一个并添加到界面上  
	               fg1 = new Fragment1();  
	               transaction.add(R.id.center, fg1); 
	              
	            } else {  
	                // 如果MessageFragment不为空，则直接将它显示出来  
	                transaction.show(fg1);  
	            }  */
				hideFragments(transaction);
	          break;  

			case 1:
				fragment_container.setVisibility(View.GONE);
				found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);  
				found_text.setTextColor(blue);
				found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
	            if (fg3 == null) {  
	                // 如果fg1为空，则创建一个并添加到界面上  
	                fg3 = new Fragment2();  
	                transaction.add(R.id.center, fg3);  
	            } else {  
	                // 如果MessageFragment不为空，则直接将它显示出来  
	                transaction.show(fg3);  
	            }  
	            break;      
			
			 case 2:
				 fragment_container.setVisibility(View.GONE);
				settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);  
				settings_text.setTextColor(blue);
				settings_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
	            if (SettingsFragment == null) {  
	                // 如果fg1为空，则创建一个并添加到界面上  
	            	SettingsFragment = new com.hfp.youtie.ui.SettingsFragment();  
	                transaction.add(R.id.center, SettingsFragment);  
	            } else {  
	                // 如果MessageFragment不为空，则直接将它显示出来  
	                transaction.show(SettingsFragment);  
	            }  
	            break; 
			 case 3:
				 fragment_container.setVisibility(View.GONE);
				 liaotian_image.setImageResource(R.drawable.liaotian1);  
				 liaotian_text.setTextColor(blue);
				 liaotian.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
				 if (fg4 == null) {  
		                // 如果fg1为空，则创建一个并添加到界面上  
		                fg4 = new Fragment4();  
		                transaction.add(R.id.center, fg4);  
		            } else {  
		                // 如果MessageFragment不为空，则直接将它显示出来  
		                transaction.show(fg4);  
		            }  
		            
				 // hideFragments(transaction);
				/* Intent intent0 = new Intent();
                 intent0.setClass(MainActivity.this, com.easemob.chatuidemo.activity.MainActivity.class);//EditActivity
                 startActivity(intent0);
                 overridePendingTransition(R.anim.out_to_right,R.anim.in_from_left);*/
				 //showListPop();/////////////////////////////////////////////////////////////
                 break;/////////////////////////
			}
			transaction.commit();
		}
		
		private void showListPop() {
			//View view = LayoutInflater.from(this).inflate(R.layout.pops, null);
			// 注入
			
			huihua = (Button) findViewById(R.id.btn_conversation);
			tongxunlu= (Button) findViewById(R.id.btn_address_list);
			shezhi= (Button) findViewById(R.id.btn_setting);
			huihua.setOnClickListener(this);
			tongxunlu.setOnClickListener(this);
			shezhi.setOnClickListener(this);
			
		/*	morePop = new PopupWindow(fragment_container, mScreenWidth, 600);

			morePop.setTouchInterceptor(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						morePop.dismiss();
						return true;
					}
					return false;
				}
			});

			morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
			morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
			morePop.setTouchable(true);
			morePop.setFocusable(true);
			morePop.setOutsideTouchable(true);
			morePop.setBackgroundDrawable(new BitmapDrawable());
			// 动画效果 从顶部弹下
			morePop.setAnimationStyle(R.style.MenuPops);
			morePop.showAsDropDown(rightMenu, 0, -dip2px(MainActivity.this, 2.0F));*////////
		}
		public  int getStateBar(){
			Rect frame = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;
			return statusBarHeight;
		}
		public static int dip2px(Context context,float dipValue){
			float scale=context.getResources().getDisplayMetrics().density;		
			return (int) (scale*dipValue+0.5f);		
		}
		//隐藏所有的Fragment,避免fragment混乱
		private void hideFragments(FragmentTransaction transaction) {  
	        /*if (fg1 != null) {  
	            transaction.hide(fg1);  
	        }  */
	        if (fg3 != null) {  
	            transaction.hide(fg3);  
	        }  
	        if (SettingsFragment != null) {  
	            transaction.hide(SettingsFragment);  
	        } 
	        if (fg4 != null) {  
	            transaction.hide(fg4);  
	        } 
	    }  
			
		
		//定义一个重置所有选项的方法
		public void clearChioce()
		{
			course_image.setImageResource(R.drawable.ic_tabbar_course_normal);
			course_layout.setBackgroundColor(whirt);
			course_text.setTextColor(Color.rgb(51, 153, 102));//gray
			found_image.setImageResource(R.drawable.ic_tabbar_found_normal);
			found_layout.setBackgroundColor(whirt);
			found_text.setTextColor(Color.rgb(51, 153, 102));
			settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
			settings_layout.setBackgroundColor(whirt);
			settings_text.setTextColor(Color.rgb(51, 153, 102));
			liaotian_image.setImageResource(R.drawable.liaotian);
			liaotian.setBackgroundColor(whirt);
			liaotian_text.setTextColor(Color.rgb(51, 153, 102));
		}///////////////////////////
		
	    
		
		
		
		

		@Override
		protected void onDestroy() {
			super.onDestroy();
			
			
		}



	
}
