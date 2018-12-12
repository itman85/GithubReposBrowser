package com.nvg.exam.phannguyen.domain.usecases;

import android.util.Log;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by phannguyen on 7/28/17.
 */

public class GetDetailRepoUsecase {
    final String TAG = "GetDetailRepoUsecase";
    private ILocalGitRepoService localGitRepoService;
    @Inject
    public GetDetailRepoUsecase(ILocalGitRepoService localGitRepoService) {
        this.localGitRepoService = localGitRepoService;
    }

    public Observable<IGitRepoModel> getDetailGitRepo(String fullname){
        return localGitRepoService.getDetailGitRepo(fullname)
                .doOnError(throwable -> Log.e(TAG,throwable.getMessage()))
                .onErrorReturn(throwable -> null)
                .flatMap(iGitRepoModel ->  Observable.just(iGitRepoModel));
    }


}
