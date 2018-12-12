package com.nvg.exam.phannguyen.ui.reposlist.di;

import com.nvg.exam.phannguyen.domain.usecases.LoadInitReposUsecase;
import com.nvg.exam.phannguyen.domain.usecases.LoadMoreReposUsecase;
import com.nvg.exam.phannguyen.domain.usecases.SearchReposUsecase;
import com.nvg.exam.phannguyen.ui.reposlist.ReposListViewModel;
import com.nvg.exam.phannguyen.ui.utils.ProgressBarUIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phannguyen on 7/29/17.
 */
@Module
public class ReposListModule {
    @Provides
    ReposListViewModel provideReposListViewModel(LoadInitReposUsecase loadInitReposUsecase, LoadMoreReposUsecase loadMoreReposUsecase,
                                                 SearchReposUsecase searchReposUsecase, ProgressBarUIService progressBarUIService){
        return new ReposListViewModel(loadInitReposUsecase,loadMoreReposUsecase,searchReposUsecase,progressBarUIService);
    }
}
