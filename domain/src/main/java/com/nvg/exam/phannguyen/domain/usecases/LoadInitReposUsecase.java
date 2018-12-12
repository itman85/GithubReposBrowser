package com.nvg.exam.phannguyen.domain.usecases;

import android.util.Log;

import com.nvg.exam.phannguyen.domain.exceptions.NetworkDataException;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.IApiGitRepoService;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static com.nvg.exam.phannguyen.domain.DomainConstant.ITEMS_PER_PAGE_NUMBER;

/**
 * Created by phannguyen on 7/28/17.
 */

public class LoadInitReposUsecase {
    final String TAG = "LoadInitReposUsecase";
    IApiGitRepoService apiGitRepoService;
    ILocalGitRepoService localGitRepoService;

    @Inject
    public LoadInitReposUsecase(IApiGitRepoService apiGitRepoService, ILocalGitRepoService localGitRepoService) {
        this.apiGitRepoService = apiGitRepoService;
        this.localGitRepoService = localGitRepoService;
    }

    /*
    This usecase will load data from local first, if no data will load from api then.
    In case get data from local error, then get data from api
    In case get data from api error or no data, then emit completed.
     */
    public Observable<List<? extends IGitRepoModel>> loadInitRepos() {
        return localGitRepoService.getGitRepos(1)
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .flatMap(gitRepos -> {
                    if (gitRepos != null && !gitRepos.isEmpty()) {
                        return Observable.just(gitRepos);
                    } else {
                        return apiGitRepoService.loadGitReposList(1, ITEMS_PER_PAGE_NUMBER)
                                .timeout(15, TimeUnit.SECONDS, Observable.error(new NetworkDataException("Netwwork timeout!")))
                                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                                .filter(gitRepos1 -> gitRepos1 != null && gitRepos1.size() > 0)
                                .map(iGitRepoModels -> {
                                    int i = 1;
                                    for(IGitRepoModel gitRepoModel:iGitRepoModels){
                                        gitRepoModel.setNth(i++);
                                    }
                                    return iGitRepoModels;
                                })
                                .flatMap(gitRepos1 ->
                                        localGitRepoService.saveGitRepo(gitRepos1)
                                                .map(aVoid -> gitRepos1));
                    }
                });
    }

}
