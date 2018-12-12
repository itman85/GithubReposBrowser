package com.nvg.exam.phannguyen.ui.repodetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.nvg.exam.phannguyen.R;
import com.nvg.exam.phannguyen.databinding.ActivityRepoDetailBinding;
import com.nvg.exam.phannguyen.ui.base.BaseActivity;
import com.nvg.exam.phannguyen.ui.repodetail.di.RepoDetailModule;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RepoDetailActivity extends BaseActivity<ActivityRepoDetailBinding> {
    private RepoDetailViewModel mViewModel;
    private String repoFullname;
    @Override
    public void onSetContentView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail);
        mBinding.setViewModel(mViewModel);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onInitializeViewModels() {
        Bundle bundle = getIntent().getExtras();
        repoFullname = bundle.getString("name");
        mViewModel = mActivityComponent.plus(new RepoDetailModule(repoFullname)).getRepoDetailViewModel();
        addViewModel(mViewModel);
    }


}
