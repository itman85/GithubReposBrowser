package com.nvg.exam.phannguyen.di;

import com.nvg.exam.phannguyen.ui.repodetail.di.RepoDetailComponent;
import com.nvg.exam.phannguyen.ui.repodetail.di.RepoDetailModule;
import com.nvg.exam.phannguyen.ui.reposlist.di.ReposListComponent;
import com.nvg.exam.phannguyen.ui.reposlist.di.ReposListModule;

import dagger.Subcomponent;

/**
 * Created by phannguyen on 7/29/17.
 */

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    ReposListComponent plus(ReposListModule module);
    RepoDetailComponent plus(RepoDetailModule module);
}
