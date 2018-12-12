package com.nvg.exam.phannguyen.ui.repodetail.di;

import com.nvg.exam.phannguyen.ui.repodetail.RepoDetailViewModel;

import dagger.Subcomponent;

/**
 * Created by phannguyen on 7/29/17.
 */
@Subcomponent(modules = RepoDetailModule.class)
public interface RepoDetailComponent {
    RepoDetailViewModel getRepoDetailViewModel();
}
