package com.nvg.exam.phannguyen.ui.repodetail.di;

import com.nvg.exam.phannguyen.domain.usecases.GetDetailRepoUsecase;
import com.nvg.exam.phannguyen.ui.repodetail.RepoDetailViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phannguyen on 7/29/17.
 */
@Module
public class RepoDetailModule {

    private String repoFullname;

    public RepoDetailModule(String repoFullname) {
        this.repoFullname = repoFullname;
    }

    @Provides
    RepoDetailViewModel provideRepoDetailViewModel(GetDetailRepoUsecase getDetailRepoUsecase){
        return new RepoDetailViewModel(getDetailRepoUsecase,repoFullname);
    }

}
