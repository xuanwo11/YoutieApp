package com.bmob.lostfound;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bmob.lostfound.Mainac.SerializableMap;
import com.bmob.lostfound.adapter.BaseAdapterHelper;
import com.bmob.lostfound.adapter.QuickAdapter;
import com.bmob.lostfound.base.EditPopupWindow;
import com.bmob.lostfound.bean.Found;
import com.bmob.lostfound.config.Constants;
import com.bmob.lostfound.i.IPopupItemClick;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.entity.QiangYu;
import com.hfp.youtie.ui.ComActivity;
import com.hfp.youtie.ui.CommentActivity;
import com.hfp.youtie.ui.RegisterAndLoginActivity;
import com.hfp.youtie.utils.ActivityUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.Ciyuanjie1;
import entity.Comment;
import entity.Person;
import net.ting.sliding.Kantu;



/**
 * Lost/Found
 * 
 * @ClassName: MainActivity
 * @Description: TODO
 * @author smile
 * @date 2014-5-21 上午11:12:36
 */
public class Music extends BA1 implements OnClickListener,
		IPopupItemClick, OnItemLongClickListener {

	RelativeLayout layout_action,mu;//
	LinearLayout layout_all;
	TextView tv_lost;
	ListView listview;
	ListAdapter clist;
	Button btn_add,btn_back;

	
	protected QuickAdapter<Comment> clistAdapter;// 失物
	protected QuickAdapter<Ciyuanjie1> FoundAdapter;// 招领
	private TextView comment,comlist;/////////////////////////
	private ArrayList<Ciyuanjie1> mListItems;
	private Comment card = new Comment();
	private ArrayList<Ciyuanjie1> mListItem;
	 private ImageView ivHead, ivDownload,imageview;///////////////////////////
	private Button layout_found;
	private Button layout_lost;
	PopupWindow morePop;

	RelativeLayout progress;
	LinearLayout layout_no;
	TextView tv_no;
	 private ImageView image;
	    private TextView title, content;
	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.acmusic);
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
		
		mListItems = new ArrayList<Ciyuanjie1>();/////////////////////////////////
		mListItem= new ArrayList<Ciyuanjie1>();/////////////////////
		
		progress = (RelativeLayout) findViewById(R.id.progress);
		layout_no = (LinearLayout) findViewById(R.id.layout_no);
		tv_no = (TextView) findViewById(R.id.tv_no);

		layout_action = (RelativeLayout) findViewById(R.id.layout_action);
		layout_all = (LinearLayout) findViewById(R.id.layout_all);
		// 默认是失物界面
		tv_lost = (TextView) findViewById(R.id.tv_lost);
		tv_lost.setTag("Found");///
		listview = (ListView) findViewById(R.id.list_lost);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_back = (Button) findViewById(R.id.btn_back);
		//comment = (TextView)findViewById(R.id.item_action_comment);////////////////////////
		//comlist=(TextView)findViewById(R.id.tvcomment);///////////////////////////
		 ivDownload = (ImageView) findViewById(R.id.content_image);///////////////////////////
		
		// 初始化长按弹窗
		initEditPop();
	}
	
	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		listview.setOnItemLongClickListener(this);
		btn_add.setOnClickListener(this);
		layout_all.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		//comment.setTag(position); ////////////////////////
		//comment.setOnClickListener(this);
		//mListItems = new ArrayList<Person>();/////////////////////////////////
		/*comment.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    
				//Bundle bundle=new Bundle();
			   // bundle.putInt("name", index);
				
				
				// Intent intent =new Intent(MainActivity.this,addcomment.class); 				
	            // startActivity(intent);
				//Person user=mListItems.get(position-1);///////////////////////////////////
				//final int index = (Integer) v.getTag(); 
				showCreateBankCardDialog();
			}
			
		});
		comlist.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// final int index = (Integer) v.getTag(); 
				// Bundle bundle=new Bundle();
				// bundle.putInt("name", index);
				 
				// Intent intent =new Intent(MainActivity.this,ComActivity.class); 
				// intent.putExtra("data", mListItems.get(position-1));
	           //  startActivity(intent);
				findMyCards();
			}
			
		});*/
		//comlist.setOnClickListener(this);
		 
	}

	
	  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == layout_all) {
		    showListPop();/////////////////////////////////////////////////////////////
		} else if (v == btn_add) {
			if(MyApplication.getInstance().getCurrentUser()==null){//////////////////////
				ActivityUtil.show(Music.this, "你还未登录~");
				Intent intent = new Intent();
				intent.setClass(Music.this, RegisterAndLoginActivity.class);
				MyApplication.getInstance().getTopActivity().startActivity(intent);
				return;
			}else{	
			/*Intent intent = new Intent(this, Ad1.class);
			intent.putExtra("from", tv_lost.getTag().toString());
			startActivityForResult(intent, Constants.REQUESTCODE_ADD);*/
			}
		} else if (v == layout_found) {
			changeTextView(v);
			morePop.dismiss();
			queryFounds();
		} else if (v == layout_lost) {
			changeTextView(v);
			morePop.dismiss();
			queryLosts();
		}/*else if(v==comment){/////////////////////////////
			showCreateBankCardDialog();
		}else if(v==comlist){
			findMyCards();
		}*/
		else if(v==btn_back){			
				finish();		
		}
		
			
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		
		if (FoundAdapter == null) {
			FoundAdapter = new QuickAdapter<Ciyuanjie1>(this, R.layout.music) {///item
				@Override
				protected void convert(BaseAdapterHelper helper,  Ciyuanjie1 found) {
					//String f=found.getUser().getAvatar().toString();
					
					if(found.getIcon()==null){////////////.equals("")
						//helper.setLayParams(R.id.mu,0,1);
					    helper.setVisible(R.id.content_image, false);
					    helper.setVisible(R.id.video_play_btn, false);
					helper.setText(R.id.tv_title, found.getTitle())	
					//.setImageUrl(R.id.content_image,found.getIcon().getUrl())////////////////////
					.setImageUrl(R.id.head_image, found.getAtavars())
					.setText(R.id.author, found.getUsernames())
					.setText(R.id.tv_describe, found.getDescribe())
							.setText(R.id.tv_time, found.getCreatedAt());
					}else{
						helper.setText(R.id.tv_title, found.getTitle())	
						.setImageUrl(R.id.content_image,found.getIcon().getUrl())////////////////////
						.setImageUrl(R.id.head_image, found.getAtavars())
						.setText(R.id.author, found.getUsernames())
						.setText(R.id.tv_describe, found.getDescribe())
								.setText(R.id.tv_time, found.getCreatedAt());
					}
				}
			};
		}
		listview.setAdapter(FoundAdapter);
		// 默认加载失物界面
		queryFounds();
		
		
	}

	private void changeTextView(View v) {
		if (v == layout_found) {
			tv_lost.setTag("Ciyuanjie1");
			tv_lost.setText("音   乐");
		} else {
			tv_lost.setTag("Ciyuanjie1");
			tv_lost.setText("音   乐");
		}
	}

	@SuppressWarnings("deprecation")
	private void showListPop() {
		View view = LayoutInflater.from(this).inflate(R.layout.popmusic, null);
		// 注入
		layout_found = (Button) view.findViewById(R.id.layout_found);
		layout_lost = (Button) view.findViewById(R.id.layout_lost);
		layout_found.setOnClickListener(this);
		layout_lost.setOnClickListener(this);
		morePop = new PopupWindow(view, mScreenWidth, 600);

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
		morePop.setAnimationStyle(R.style.MenuPop);
		morePop.showAsDropDown(layout_action, 0, -dip2px(this, 2.0F));/////////0
	}

	private void initEditPop() {
		mPopupWindow = new EditPopupWindow(this, 200, 48);
		mPopupWindow.setOnPopupItemClickListner(this);
		 
	}

	EditPopupWindow mPopupWindow;
	int position;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		/*position = arg2;
		int[] location = new int[2];
		arg1.getLocationOnScreen(location);
		mPopupWindow.showAtLocation(arg1, Gravity.RIGHT | Gravity.END,
				location[0], getStateBar() + location[1]);////////TOP//Gravity.RIGHT | Gravity.END
		*/
	
		return false;
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case Constants.REQUESTCODE_ADD:// 添加成功之后的回调
			String tag = tv_lost.getTag().toString();
			if (tag.equals("Ciyuanjie1")) {
				queryFounds();
			} else {
				queryFounds();
			}
			break;
		}
	}

	/**
	 * 查询全部失物信息 queryLosts
	 * 
	 * @return void
	 * @throws
	 */
	private void queryLosts() {
		showView();
		BmobQuery<Ciyuanjie1> query = new BmobQuery<Ciyuanjie1>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Ciyuanjie1>() {

			@Override
			public void onSuccess(List<Ciyuanjie1> losts) {
				// TODO Auto-generated method stub
			
				FoundAdapter.clear();
				if (losts == null || losts.size() == 0) {
					showErrorView(0);
					FoundAdapter.notifyDataSetChanged();
					return;
				}
				progress.setVisibility(View.GONE);
				FoundAdapter.addAll(losts);
				listview.setAdapter(FoundAdapter);
				
				final List<Ciyuanjie1> list=losts;	////////////////		
				listview.setOnItemClickListener(new OnItemClickListener() {///////////////
					@Override
					public void onItemClick(AdapterView<?> parent, View view,/////////////////////////////////
					int position, long id) {
						
						//Toast.makeText(Music.this, "长按可进行评论哦~", Toast.LENGTH_SHORT).show();
						final int pos=position;////////////////////////////
						
					      
					                 String u=list.get(pos).getContentfigureurl().getFileUrl(Music.this);//getPhone();
									 Bundle bundle0=new Bundle();
			        				 bundle0.putString("name", u);
			        				 Intent intent = new Intent();
			         				intent.setClass(Music.this, com.wwj.download.MainActivity.class);          				
			         				intent.putExtras(bundle0);
			         				startActivity(intent);
					               
					       
					
			}
				});
			}
			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(0);
			}
		});
	}

	public void queryFounds() {
	/*	showView();
		BmobQuery<Found> query = new BmobQuery<Found>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Found>() {

			@Override
			public void onSuccess(List<Found> arg0) {
				// TODO Auto-generated method stub
				LostAdapter.clear();
				FoundAdapter.clear();
				if (arg0 == null || arg0.size() == 0) {
					showErrorView(1);
					FoundAdapter.notifyDataSetChanged();
					return;
				}
				FoundAdapter.addAll(arg0);
				listview.setAdapter(FoundAdapter);
				progress.setVisibility(View.GONE);
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(1);
			}
		});*/
		showView();
		BmobQuery<Ciyuanjie1> query = new BmobQuery<Ciyuanjie1>();
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Ciyuanjie1>() {

			@Override
			public void onSuccess(List<Ciyuanjie1> losts) {
				// TODO Auto-generatedLostAdapter.clear();
				FoundAdapter.clear();
				if (losts == null || losts.size() == 0) {
					showErrorView(1);
					FoundAdapter.notifyDataSetChanged();
					return;
				}
				progress.setVisibility(View.GONE);
				FoundAdapter.addAll(losts);
				listview.setAdapter(FoundAdapter);
				
				final List<Ciyuanjie1> list=losts;	////////////////		
				listview.setOnItemClickListener(new OnItemClickListener() {///////////////
					@Override
					public void onItemClick(AdapterView<?> parent, View view,/////////////////////////////////
					int position, long id) {
						
						//Toast.makeText(Music.this, "长按可进行评论哦~", Toast.LENGTH_SHORT).show();
						final int pos=position;////////////////////////////
						
						  String u=list.get(pos).getContentfigureurl().getFileUrl(Music.this);//getPhone();
							 Bundle bundle0=new Bundle();
	        				 bundle0.putString("name", u);
	        				 Intent intent = new Intent();
	         				intent.setClass(Music.this, com.wwj.download.MainActivity.class);          				
	         				intent.putExtras(bundle0);
	         				startActivity(intent);
					}
					});
			}

			@Override
			public void onError(int code, String arg0) {
				// TODO Auto-generated method stub
				showErrorView(1);
			}
		});
	}

	/**
	 * 请求出错或者无数据时候显示的界面 showErrorView
	 * 
	 * @return void
	 * @throws
	 */
	private void showErrorView(int tag) {
		progress.setVisibility(View.GONE);
		listview.setVisibility(View.GONE);
		layout_no.setVisibility(View.VISIBLE);
		if (tag == 0) {
			tv_no.setText(getResources().getText(R.string.list_no_data_lost));
		} else {
			tv_no.setText(getResources().getText(R.string.list_no_data_found));
		}
	}

	private void showView() {
		listview.setVisibility(View.VISIBLE);
		layout_no.setVisibility(View.GONE); 		
	}

	@Override
	public void onEdit(View v) {
		// TODO Auto-generated method stub
		Ciyuanjie1 user = new Ciyuanjie1();
		user.setObjectId(FoundAdapter.getItem(position).getObjectId());
		
		//Person user=mListItems.get(position-1);
		BmobQuery<Comment> cards = new BmobQuery<Comment>();
		/**
		 * 注意这里的查询条件
		 * 第一个参数：是User表中的cards字段名
		 * 第二个参数：是指向User表中的某个用户的BmobPointer对象
		 */
		cards.addWhereRelatedTo("cards", new BmobPointer(user));
		cards.findObjects(this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> arg0) {
				// TODO Auto-generated method stub
				ShowToast("有"+arg0.size()+"条评论");
				 int i=0;///////////////////////////////////////
				 String[] items =new String[arg0.size()];////////////////////
				 String[] authors =new String[arg0.size()];////////////////////
				 HashMap<String, Object> map = new HashMap<String, Object>();
				 HashMap<String, Object> map1 = new HashMap<String, Object>();
				 ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
				for (Comment bankCard : arg0) {
					//Log.d("bmob", "objectId:"+bankCard.getObjectId()+",银行名称："+bankCard.getDescribe()+",卡号："+bankCard.getTitle());					
					items[i] =bankCard.getTitle()+":"+bankCard.getDescribe()+"\n";/////
					
					map.put((i+1)+"楼"+" "+bankCard.getUsernames(),"["+bankCard.getTitle()+"]"+bankCard.getDescribe());
					map1.put(bankCard.getAtavars(),bankCard.getAtavars());
					i++;
					listData.add(map);
				}
			    
				//clistAdapter.addAll(arg0);
				// ((ListView) clist).setAdapter(clistAdapter);	
				final String[]  auth=authors;				
				 final SerializableMap myMap=new SerializableMap();
                 myMap.setMap(map);//将map数据添加到封装的myMap中				 
				 //bundle.putSerializable("name",  myMap);;//putStringArray("name", item);							 
				 Intent intent =new Intent(Music.this,M2.class); 
			       	//intent.putExtras(bundle);
				    intent.putExtra("map",map);	
				    intent.putExtra("map1", map1);
			       	startActivity(intent); 
			     /* ListAdapter catalogsAdapter = new ArrayAdapter<String>(Mainac.this, R.layout.online_user_list_item, items);
			      AlertDialog.Builder builder = new AlertDialog.Builder(Mainac.this);
			      builder.setIcon(R.drawable.duihua);
			      builder.setTitle("展示评论");      
			      builder.setAdapter(catalogsAdapter,new DialogInterface.OnClickListener() {  
			          @Override  
			          public void onClick(DialogInterface dialogInterface, int which) {  			         
			             

			        	  Toast toast=Toast.makeText(Mainac.this, item[which],10000);//Toast.LENGTH_LONG//"点中了第" + which+"条评论"+"\n"+
			        	  toast.setGravity(Gravity.CENTER, 0, 0);  
			              //创建图片视图对象 
			              ImageView imageView= new ImageView(Mainac.this); 
			              //设置图片 
			              imageView.setImageResource(R.drawable.tx1); 
			              //获得toast的布局 
			              LinearLayout toastView = (LinearLayout) toast.getView(); 
			              //设置此布局为横向的 
			              toastView.setOrientation(LinearLayout.VERTICAL); 
			              //将ImageView在加入到此布局中的第一个位置 
			              toastView.addView(imageView, 0); 
			              toast.show(); 
			          
			          }  
			      });  
			    	
			    builder.create().show();//builder.show();*/
	            
			}
			
			
		        
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("查询评论失败");
			}
		});
	}
	public class SerializableMap implements Serializable {

	    private Map<String,Object> map;

	    public Map<String, Object> getMap() {
	        return map;
	    }

	    public void setMap(Map<String, Object> map) {
	        this.map = map;
	    }
	}
	
	@Override
	public void onDelete(View v) {
		// TODO Auto-generated method stub
		/*String tag = tv_lost.getTag().toString();/////////////////////////////////
		if (tag.equals("Lost")) {
			deleteLost();
		} else {
			deleteFound();
		}*/
		showCreateBankCardDialog();
	}

	private void deleteLost() {
		Ciyuanjie1 lost = new Ciyuanjie1();
		lost.setObjectId(FoundAdapter.getItem(position).getObjectId());
		lost.delete(this, new DeleteListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				FoundAdapter.remove(position);
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void deleteFound() {
		Ciyuanjie1 found = new Ciyuanjie1();
		found.setObjectId(FoundAdapter.getItem(position).getObjectId());
		found.delete(this, new DeleteListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				FoundAdapter.remove(position);
			}

			@Override
			public void onFailure(int code, String arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

private void showCreateBankCardDialog() {//////////////////////////////////////////
		
	LayoutInflater inflater = getLayoutInflater();
	final View layout = inflater.inflate(R.layout.dialog_card,
			(ViewGroup) findViewById(R.id.dialog));
	
	new AlertDialog.Builder(this)		        
			.setTitle("编辑评论")
			.setView(layout)
			.setIcon(R.drawable.duihua)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					EditText etname = (EditText) layout
							.findViewById(R.id.etbackname);
					EditText etcard = (EditText) layout
							.findViewById(R.id.etcard);
					if (TextUtils.isEmpty(etname.getText().toString())
							|| TextUtils
									.isEmpty(etcard.getText().toString())) {
						ShowToast("评论内容和昵称不能为空");
					} else {
						saveBankCardInfo(etname.getText().toString(), etcard.getText().toString());
					}
				}
			})
			.setNegativeButton("取消", null).show();
	}




private void saveBankCardInfo(String describe, String title){///////////////////////////////
	
	
	Ciyuanjie1 user = new Ciyuanjie1();
	user.setObjectId(FoundAdapter.getItem(position).getObjectId());
	//Person user=mListItems.get(position-1);
	if(TextUtils.isEmpty(user.getObjectId())){
		ShowToast("当前用户的object为空");
		return;
	}
	String usernames=MyApplication.getInstance().getCurrentUser().getUsername();
    String avatars=MyApplication.getInstance().getCurrentUser().getAvatar();////////////
	card = new Comment();
	card.setDescribe(describe);		// 设置银行名称
	card.setTitle(title);	// 设置银行卡号
	card.setUser6(user);
	card.setUsernames(usernames);////////////////////////
	card.setAtavars(avatars);//////////////////////////
	// 设置银行卡户主
	card.save(this, new SaveListener() {
		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			ShowToast("成功发表一条评论！");
			addCardToUser();
		}

		@Override
		public void onFailure(int arg0, String arg1) {
			// TODO Auto-generated method stub
			ShowToast("评论发表失败！");
		}
	});
	
	
}
private void addCardToUser(){
	Ciyuanjie1 user = new Ciyuanjie1();
	user.setObjectId(FoundAdapter.getItem(position).getObjectId());
	//Person user=mListItems.get(position-1);///////////////////////////////////////////
	if(TextUtils.isEmpty(user.getObjectId()) || 
			TextUtils.isEmpty(card.getObjectId())){
		ShowToast("当前用户或者当前评论对象的object为空");//card
		return;
	}
	
	BmobRelation cards = new BmobRelation();
	cards.add(card);
	user.setCards(cards);
	user.update(this, new UpdateListener() {
		
		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			ShowToast("已成功添加评论~");
		}
		
		@Override
		public void onFailure(int arg0, String arg1) {
			// TODO Auto-generated method stub
			ShowToast("评论添加失败~");
		}
	});
}


/*private void findMyCards(){
	Person user = new Person();
	user.setObjectId(LostAdapter.getItem(position).getObjectId());	
	//Person user=mListItems.get(position-1);
	BmobQuery<Comment> cards = new BmobQuery<Comment>();	
	 * 注意这里的查询条件
	 * 第一个参数：是User表中的cards字段名
	 * 第二个参数：是指向User表中的某个用户的BmobPointer对象	 
	cards.addWhereRelatedTo("cards", new BmobPointer(user));
	cards.findObjects(this, new FindListener<Comment>() {		
		@Override
		public void onSuccess(List<Comment> arg0) {
			// TODO Auto-generated method stub
			ShowToast("我现在有"+arg0.size()+"张银行卡");
			 int i=0;//////////
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
			for (Comment bankCard : arg0) {
				Log.d("bmob", "objectId:"+bankCard.getObjectId()+",银行名称："+bankCard.getDescribe()+",卡号："+bankCard.getTitle());
			
				 map.put(bankCard.getTitle(),bankCard.getDescribe());
				i++;
				listData.add(map);
			}
			//clistAdapter.addAll(arg0);
			// ((ListView) clist).setAdapter(clistAdapter);			
			Set<String> set = map.keySet();//////////////////////////////////////
			  Iterator<String> it = set.iterator();
			  String[][] ss = new String[map.size()][2];
			  for (i = 0; i < map.size(); i++) {
			   ss[i][0] = it.next();
			   ss[i][1] = (String) map.get(ss[i][0]);
			   //mString[i][1]="http://file.bmob.cn/"+ss[i][1];
			   System.out.print(ss[i][1]+"\n");
			  }			  
			  System.out.println(ss.length);
			  String [] urls = new String[(ss.length)*2];
			  String [] content= new String[(ss.length)*2];
			  String[] items =new String[(ss.length)*2];
		        for ( i = 0; i < ss.length; i++) {
		            for (int j = 0; j < ss[i].length; j++) {
		                urls[i+j] = ss[i][j+1];
		               // mString[i+j]=urls[i+j];	     			                
		                System.out.println(urls[i+j]);		               	     					        
		                j++;
		                content[i]=ss[i][0];
				        items[i]=content[i]+":"+urls[i+j];
		            }
		           
		         System.out.println(content[i]);
		        }		      		      	 
		      ListAdapter catalogsAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.online_user_list_item, items);
		      AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		      builder.setIcon(R.drawable.duihua);
		      builder.setTitle("展示评论");
		      builder.setAdapter( catalogsAdapter, new DialogInterface.OnClickListener()
		      {
		          @Override
		          public void onClick(DialogInterface dialog, int which)
		          {
		             // Toast.makeText(MainActivity.this, "第" + clistAdapter[which]+"条评论", Toast.LENGTH_SHORT).show();
		          }
		      });
		    builder.show();//builder.show();
            
		}	     
		@Override
		public void onError(int arg0, String arg1) {
			// TODO Auto-generated method stub
			ShowToast("查询评论失败~");
		}
	});
	
}*/



}

