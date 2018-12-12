package com.nvg.exam.phannguyen.domain.usecases;

import android.util.Log;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by phannguyen on 7/30/17.
 */

public class SearchReposUsecase {
    final String TAG = "SearchReposUsecase";
    ILocalGitRepoService localGitRepoService;

    @Inject
    public SearchReposUsecase(ILocalGitRepoService localGitRepoService) {
        this.localGitRepoService = localGitRepoService;
    }


    /*
    Search repo similar name in local db.
     */
    public Observable<List<? extends IGitRepoModel>> searchRepos(String name){
        return localGitRepoService.searchRepos(name)
                .doOnError(throwable -> Log.e(TAG,throwable.getMessage()))
                .onErrorReturn(throwable -> null);

    }
}
