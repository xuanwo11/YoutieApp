package com.hfp.youtie.ui;

import com.hfp.youtie.entity.QiangYu;
import com.hfp.youtie.ui.base.BaseFragment;
import com.hfp.youtie.ui.base.BaseHomeActivity;
import com.hfp.youtie.utils.ActivityUtil;

import android.os.Bundle;
@Deprecated
public class PersonalEditActivity extends BaseHomeActivity{

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "修改个人信息";
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
		return PersonalEditFragment.newInstance();
	}

	@Override
	protected void addActions() {
		// TODO Auto-generated method stub
		
	}

}
