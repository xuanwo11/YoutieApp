package com.hfp.youtie.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import entity.Person;
import entity.Shipin;
import net.ting.sliding.Kantu;

import com.bmob.lostfound.Mainac;
import com.bmob.lostfound.bean.Found;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.adapter.ComAdapter;
import com.hfp.youtie.adapter.ComAp;
import com.hfp.youtie.adapter.CommentAdapter;
import com.hfp.youtie.db.DatabaseUtil;
import com.hfp.youtie.entity.User;
import com.hfp.youtie.sns.TencentShare;
import com.hfp.youtie.sns.TencentShareEntity;
import com.hfp.youtie.ui.base.BasePageActivity;
import com.hfp.youtie.utils.ActivityUtil;
import com.hfp.youtie.utils.Constant;
import com.hfp.youtie.utils.LogUtils;

import entity.Comment;

/**
 * @author kingofglory
 *         email: kingofglory@yeah.net
 *         blog:  http:www.google.com
 * @date 2014-4-2
 * TODO
 */

public class ComAc extends BasePageActivity implements OnClickListener{
	
	private ActionBar actionbar;
	private ListView commentList;
	private TextView footer;
	
	private EditText commentContent ;
	private Button commentCommit;
	
	private TextView author;
	private TextView tv_title;
	private TextView tv_describe;
	private ImageView content_image;
	
	private ImageView head_image;
	private TextView comment;

	
	private TextView tv_time;/////////////////	
	private Shipin found;
	private String commentEdit = "";
	
	private ComAp madapter;
	
	private List<entity.Comment> comts= new ArrayList<entity.Comment>();
	
	private int pageNum;
	
	@Override
	protected void setLayoutView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.accomment1);
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		actionbar = (ActionBar)findViewById(R.id.actionbar_comment);
		commentList = (ListView)findViewById(R.id.comment_list);
		footer = (TextView)findViewById(R.id.loadmore);
		
		commentContent = (EditText)findViewById(R.id.comment_content);
		commentCommit = (Button)findViewById(R.id.comment_commit);
		
		author = (TextView)findViewById(R.id.author);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_describe = (TextView)findViewById(R.id.tv_describe);
		content_image = (ImageView)findViewById(R.id.content_image);
		
		head_image = (ImageView)findViewById(R.id.head_image);		
		comment = (TextView)findViewById(R.id.item_action_comment);
		
		tv_time= (TextView)findViewById(R.id.tv_time);//////////////////////	
		
	}

	@Override
	protected void setupViews(Bundle bundle) {
		// TODO Auto-generated method stub
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		found = (Shipin)getIntent().getSerializableExtra("data");//MyApplication.getInstance().getCurrentQiangYu();
		pageNum = 0;
		
		
		actionbar.setTitle("评论区");
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeAction(new Action() {
			
			@Override
			public void performAction(View view) {
				// TODO Auto-generated method stub
				finish();
			}

			@Override
			public int getDrawable() {
				// TODO Auto-generated method stub
				return 0;//R.drawable.logo;
			}						
		});
		
		madapter = new ComAp(ComAc.this, comts);
		commentList.setAdapter(madapter);
		setListViewHeightBasedOnChildren(commentList);
		commentList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				/////ActivityUtil.show(ComAc.this, "po"+position);
			}
		});
		commentList.setCacheColorHint(0);
		commentList.setScrollingCacheEnabled(false);
		commentList.setScrollContainer(false);
		commentList.setFastScrollEnabled(true);
		commentList.setSmoothScrollbarEnabled(true);
		
		initMoodView(found);
	}

	private void initMoodView(Shipin mood2) {
		// TODO Auto-generated method stub
		if(mood2 == null){
			return;
		}
		
		tv_time.setText(found.getCreatedAt());/////////////////
		author.setText(found.getUsernames());
		tv_title.setText(found.getTitle());
		tv_describe.setText(found.getDescribe());
		if(null == found.getIcon()){
			content_image.setVisibility(View.GONE);
		}else{
			content_image.setVisibility(View.VISIBLE);
			ImageLoader.getInstance()
			.displayImage(found.getIcon().getFileUrl(ComAc.this)==null?"":found.getIcon().getFileUrl(ComAc.this), content_image, 
					MyApplication.getInstance().getOptions(R.drawable.bg_pic_loading),
					new SimpleImageLoadingListener(){
	
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
							 float[] cons=ActivityUtil.getBitmapConfiguration(loadedImage, content_image, 1.0f);
	                         RelativeLayout.LayoutParams layoutParams=
	                             new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
	                         layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
	                         content_image.setLayoutParams(layoutParams);
						}
				
			});
		}
				
		String avatar = found.getAtavars();
		if(null != avatar){
			ImageLoader.getInstance()
			.displayImage(avatar, head_image, 
					MyApplication.getInstance().getOptions(R.drawable.content_image_default),
					new SimpleImageLoadingListener(){
	
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
							LogUtils.i(TAG,"load personal icon completed.");
						}
				
			});			
		}
		
	}
	
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		footer.setOnClickListener(this);
		commentCommit.setOnClickListener(this);
		
		head_image.setOnClickListener(this);		
		comment.setOnClickListener(this);				
		content_image.setOnClickListener(this);////////////////////
	}

	@Override
	protected void fetchData() {
		// TODO Auto-generated method stub
		fetchComment();
	}
	
	private void fetchComment(){
		BmobQuery<entity.Comment> query = new BmobQuery<entity.Comment>();
		query.addWhereRelatedTo("relation", new BmobPointer(found));
		query.include("user2");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.findObjects(this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> data) {
				// TODO Auto-generated method stub
				LogUtils.i(TAG,"get comment success!"+data.size());
				if(data.size()!=0 && data.get(data.size()-1)!=null){
					
					if(data.size()<Constant.NUMBERS_PER_PAGE){
						ActivityUtil.show(mContext, "已加载完所有评论~");
						footer.setText("暂无更多评论~");
					}
					
					madapter.getDataList().addAll(data);
					madapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
					LogUtils.i(TAG,"refresh");
				}else{
					ActivityUtil.show(mContext, "暂无更多评论~");
					footer.setText("暂无更多评论~");
					pageNum--;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ActivityUtil.show(ComAc.this, "获取评论失败。请检查网络~");
				pageNum--;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_image:
	//	onClickUserLogo();
			break;
		case R.id.loadmore:
			onClickLoadMore();
			break;
		case R.id.comment_commit:
			onClickCommit();
			break;				
		case R.id.item_action_comment:
			onClickComment();
			break;
		
		case R.id.content_image:
			
			/* String u=found.getIcon().getFileUrl(ComActivity.this);
			 Bundle bundle0=new Bundle();
			 bundle0.putString("name", u);
			 Intent intent = new Intent();
				intent.setClass(ComActivity.this, Kantu.class);          				
				intent.putExtras(bundle0);
				startActivity(intent);*/
			
			break;//////////////////
		default:
			break;
		}
	}  
	
	private void onClickUserLogo() {
		// TODO Auto-generated method stub
		//跳转到个人信息界面
		User currentUser = BmobUser.getCurrentUser(this,User.class);
		if(MyApplication.getInstance().getCurrentUser()==null){
			ActivityUtil.show(mContext, "请先登录。");
			Intent intent = new Intent();
			intent.setClass(mContext, RegisterAndLoginActivity.class);
			MyApplication.getInstance().getTopActivity().startActivity(intent);
			return;
		}
		//MyApplication.getInstance().setCurrentFound(found);
//MyApplication.getInstance().getTopActivity()
		/*Bundle bundle0=new Bundle();
		String u=found.getAuthor();
		bundle0.putString("name", u);
			Intent intent = new Intent();
			intent.setClass(this, PersonA.class);
			intent.putExtras(bundle0);
			mContext.startActivity(intent);*/
		/*if(currentUser != null){//已登录
			Intent intent = new Intent();
			intent.setClass(MyApplication.getInstance().getTopActivity(), PersonalActivity.class);
			mContext.startActivity(intent);
		}else{//未登录
			ActivityUtil.show(this, "请先登录。");
			Intent intent = new Intent();
			intent.setClass(this, RegisterAndLoginActivity.class);
			startActivityForResult(intent, Constant.GO_SETTINGS);
		}*/
	}

	private void onClickLoadMore() {
		// TODO Auto-generated method stub
		fetchData();
	}

	
	private void onClickCommit() {
		// TODO Auto-generated method stub
		User currentUser = BmobUser.getCurrentUser(this,User.class);
		if(currentUser != null){//已登录
			commentEdit = commentContent.getText().toString().trim();
			if(TextUtils.isEmpty(commentEdit)){
				ActivityUtil.show(this, "评论内容不能为空。");
				return;
			}
			//comment now
			publishComment(currentUser,commentEdit);
		}else{//未登录
			ActivityUtil.show(this, "发表评论前请先登录。");
			Intent intent = new Intent();
			intent.setClass(this, RegisterAndLoginActivity.class);
			startActivityForResult(intent, Constant.PUBLISH_COMMENT);
		}
		
	}

	private void publishComment(com.hfp.youtie.entity.User user,String content){
		
		final Comment comment = new Comment();
		comment.setUser2(user);
		comment.setTitle(content);
		comment.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ActivityUtil.show(ComAc.this, "评论成功。");
				if(madapter.getDataList().size()<Constant.NUMBERS_PER_PAGE){
					madapter.getDataList().add(comment);
					madapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);
				}
				commentContent.setText("");
				hideSoftInput();
				
				//将该评论与强语绑定到一起
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				found.setRelation(relation);
				found.update(mContext, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						LogUtils.i(TAG, "更新评论成功。");
//						fetchData();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						LogUtils.i(TAG, "更新评论失败。"+arg1);
					}
				});
				
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ActivityUtil.show(ComAc.this, "评论失败。请检查网络~");
			}
		});
	}
	

	
	private void onClickComment() {
		// TODO Auto-generated method stub
		commentContent.requestFocus();

		InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  

		imm.showSoftInput(commentContent, 0);  
	}
	
	private void hideSoftInput(){
		InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  

		imm.hideSoftInputFromWindow(commentContent.getWindowToken(), 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case Constant.PUBLISH_COMMENT:
				//登录完成
				commentCommit.performClick();
				break;
			
			case Constant.GET_FAVOURITE:
				
				break;
			case Constant.GO_SETTINGS:
				head_image.performClick();
				break;
			default:
				break;
			}
		}
		
	}
	
	
	/*** 
     * 动态设置listview的高度 
     *  item 总布局必须是linearLayout
     * @param listView 
     */  
    public void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount()-1))  
                +15;  
        listView.setLayoutParams(params);  
    }


}

