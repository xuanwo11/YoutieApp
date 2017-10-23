package com.hfp.youtie.ui;

import android.content.Intent;
import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;
import com.hfp.youtie.MyApplication;
import com.hfp.youtie.R;
import com.hfp.youtie.ui.PersonalFragment.IProgressControllor;
import com.hfp.youtie.ui.base.BaseFragment;
import com.hfp.youtie.ui.base.BaseHomeActivity;

public class PersonActivity extends BaseHomeActivity implements IProgressControllor{

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "我的帖子";
	}

	@Override
	protected boolean isHomeAsUpEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onHomeActionClick() {
		// TODO Auto-generated method stub		
		finish();
		Intent intent2 = new Intent();
		intent2.setClass(this, MainActivity.class);
		startActivity(intent2);
	}

	@Override
	protected BaseFragment getFragment() {
		// TODO Auto-generated method stub
		return PersonFragment.newInstance();
	}
	

	@Override
	protected void addActions() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void showActionBarProgress(){
		actionBar.setProgressBarVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideActionBarProgress(){
		actionBar.setProgressBarVisibility(View.GONE);
	}
}

