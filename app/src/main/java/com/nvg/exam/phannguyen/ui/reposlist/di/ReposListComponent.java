package com.nvg.exam.phannguyen.ui.reposlist.di;

import com.nvg.exam.phannguyen.ui.reposlist.ReposListViewModel;

import dagger.Subcomponent;

/**
 * Created by phannguyen on 7/29/17.
 */
@Subcomponent(modules = ReposListModule.class)
public interface ReposListComponent {
    ReposListViewModel getReposListViewModel();
}
