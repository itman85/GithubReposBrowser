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

public class LoadMoreReposUsecase {
    final String TAG = "LoadMoreReposUsecase";
    IApiGitRepoService apiGitRepoService;
    ILocalGitRepoService localGitRepoService;

    @Inject
    public LoadMoreReposUsecase(IApiGitRepoService apiGitRepoService, ILocalGitRepoService localGitRepoService) {
        this.apiGitRepoService = apiGitRepoService;
        this.localGitRepoService = localGitRepoService;
    }

    /*
        Load more data from local first, if no more local data, then load from api
     */
    public Observable<List<? extends IGitRepoModel>> loadMoreRepos(int currentPage) {
        return localGitRepoService.getGitRepos(currentPage)
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .flatMap(gitRepos -> {
                    if (gitRepos != null && !gitRepos.isEmpty()) {
                        return Observable.just(gitRepos);
                    } else {
                        return apiGitRepoService.loadGitReposList(currentPage, ITEMS_PER_PAGE_NUMBER)
                                .timeout(15, TimeUnit.SECONDS, Observable.error(new NetworkDataException("Netwwork timeout!")))
                                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                                .map(iGitRepoModels -> {
                                    Log.i(TAG, "Loadmore Size " + iGitRepoModels.size());
                                    if (iGitRepoModels != null && iGitRepoModels.size() > 0) {
                                        int i = (currentPage - 1) * ITEMS_PER_PAGE_NUMBER + 1;
                                        for (IGitRepoModel gitRepoModel : iGitRepoModels) {
                                            gitRepoModel.setNth(i++);
                                        }
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
