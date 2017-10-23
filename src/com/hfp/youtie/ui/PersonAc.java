package com.hfp.youtie.ui;

import android.view.View;

import com.markupartist.android.widget.ActionBar.Action;
import com.hfp.youtie.R;
import com.hfp.youtie.ui.PersonalFragment.IProgressControllor;
import com.hfp.youtie.ui.base.BaseFragment;
import com.hfp.youtie.ui.base.BaseHomeActivity;

public class PersonAc extends BaseHomeActivity implements IProgressControllor{

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "个人主页";
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
	}

	@Override
	protected BaseFragment getFragment() {
		// TODO Auto-generated method stub
		return PersonFrag.newInstance();
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


