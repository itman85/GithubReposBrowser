package com.nvg.exam.phannguyen.data.services.api;

import android.util.Log;

import com.nvg.exam.phannguyen.data.models.api.CoinTickerModel;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.IApiGitRepoService;

import java.util.List;

import rx.Observable;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ApiGitRepoServiceImpl implements IApiGitRepoService{
    static final String TAG = "ApiGitRepoServiceImpl";
    private IGitReposWS gitReposWS;
    public ApiGitRepoServiceImpl(IGitReposWS gitReposWS) {
        this.gitReposWS = gitReposWS;
    }

    @Override
    public Observable<List<? extends IGitRepoModel>> loadGitReposList(int page, int size) {
       /* gitReposWS.getCoinsTickerData()
                .doOnError(throwable -> Log.e(TAG,throwable.getMessage()))
                .map(coinTickerModels -> {
                  // for(CoinTickerModel coin : coinTickerModels)
                       Log.i(TAG,coinTickerModels.body().getSymbol());
                   return coinTickerModels;
                });

        return null;*/

        return gitReposWS.getGitReposByPage(size,page)
                .doOnError(throwable -> Log.e(TAG,throwable.getMessage()))
                .flatMap(apiGitRepoModels -> Observable.just(apiGitRepoModels));

    }

    @Override
    public Observable<IGitRepoModel> getDetaiGitRepo(String repoName) {
        return gitReposWS.getDetailGitRepo(repoName)
                .doOnError(throwable -> Log.e(TAG,throwable.getMessage()))
                .flatMap(apiGitRepoModel -> Observable.just(apiGitRepoModel));
    }
}
