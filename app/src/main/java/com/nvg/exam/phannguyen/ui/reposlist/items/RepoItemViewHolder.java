package com.nvg.exam.phannguyen.ui.reposlist.items;

import android.databinding.DataBindingUtil;
import android.view.ViewGroup;

import com.nvg.exam.phannguyen.R;
import com.nvg.exam.phannguyen.databinding.GitRepoItemBinding;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

import me.henrytao.mvvmlifecycle.MVVMObserver;
import me.henrytao.mvvmlifecycle.recyclerview.RecyclerViewBindingViewHolder;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RepoItemViewHolder extends RecyclerViewBindingViewHolder<IGitRepoModel> {
    private final GitRepoItemBinding mBinding;
    private RepoItemViewModel mViewModel;

    public RepoItemViewHolder(MVVMObserver observer, ViewGroup parent) {
        super(observer, parent, R.layout.git_repo_item);
        mBinding = DataBindingUtil.bind(itemView);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void bind(IGitRepoModel data) {
        mViewModel.setRepoData(data);
        mBinding.executePendingBindings();
    }

    @Override
    public void onInitializeViewModels() {
        mViewModel = new RepoItemViewModel();
        addViewModel(mViewModel);
    }
}
