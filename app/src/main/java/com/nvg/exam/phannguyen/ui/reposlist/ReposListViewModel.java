package com.nvg.exam.phannguyen.ui.reposlist;

import android.util.Log;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.usecases.LoadInitReposUsecase;
import com.nvg.exam.phannguyen.domain.usecases.LoadMoreReposUsecase;
import com.nvg.exam.phannguyen.domain.usecases.SearchReposUsecase;
import com.nvg.exam.phannguyen.ui.base.BaseViewModel;
import com.nvg.exam.phannguyen.ui.reposlist.items.RepoItemViewModel;
import com.nvg.exam.phannguyen.ui.utils.ProgressBarUIService;

import me.henrytao.mvvmlifecycle.event.Event1;
import me.henrytao.mvvmlifecycle.rx.Transformer;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ReposListViewModel extends BaseViewModel<ReposListViewModel.State> {

    private static final String TAG = "ReposListViewModel";
    private LoadInitReposUsecase loadInitReposUsecase;
    private LoadMoreReposUsecase loadMoreReposUsecase;
    private SearchReposUsecase searchReposUsecase;
    private ProgressBarUIService progressBarUIService;
    private int currentPage;
    private boolean isDataLoading;
    private boolean isSearchingMode;

    public ReposListViewModel(LoadInitReposUsecase loadInitReposUsecase, LoadMoreReposUsecase loadMoreReposUsecase,
                              SearchReposUsecase searchReposUsecase,ProgressBarUIService progressBarUIService) {
        this.loadInitReposUsecase = loadInitReposUsecase;
        this.loadMoreReposUsecase = loadMoreReposUsecase;
        this.progressBarUIService = progressBarUIService;
        this.searchReposUsecase = searchReposUsecase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manageSubscription(subscribe(RepoItemViewModel.Event.ON_REPO_ITEM_CLICK, new Event1<>(this::onMovieItemClick)),
                UnsubscribeLifeCycle.DESTROY);
        resetReposList();
    }
    public void resetReposList(){
        currentPage = 1;
        isDataLoading = false;
        isSearchingMode = false;
        loadInitRepo();
    }
    public void loadInitRepo() {

        manageSubscription(loadInitReposUsecase.loadInitRepos()
                .compose(progressBarUIService.applyLoadingTransform(this))
                .compose(Transformer.applyIoScheduler())
                .subscribe(repos -> setState(State.RELOADED_REPOS,
                        "repos", repos),
                        throwable -> {
                            Log.e(TAG,throwable.getMessage());
                            setState(State.RELOADED_REPOS_ERROR,"errmsg",throwable.getMessage());
                }),
                UnsubscribeLifeCycle.DESTROY);
    }

    public void loadMoreRepo() {
        isDataLoading = true;
        manageSubscription(loadMoreReposUsecase.loadMoreRepos(++currentPage)
                .compose(Transformer.applyIoScheduler())
                .subscribe(repos -> {
                    Log.i(TAG,"Load more response size "+repos.size());
                    isDataLoading = false;
                    setState(State.LOADMORE_REPOS,
                            "repos", repos);
                }, throwable -> {
                    isDataLoading = false;
                    Log.e(TAG, throwable.getMessage());
                    setState(State.RELOADED_REPOS_ERROR,"errmsg",throwable.getMessage());
                }), UnsubscribeLifeCycle.DESTROY);
    }

    public void searchRepos(String name){
        manageSubscription(searchReposUsecase.searchRepos(name)
                        .compose(Transformer.applyIoScheduler())
                        .subscribe(repos -> {
                                    isSearchingMode = true;
                                    setState(State.RELOADED_REPOS, "repos", repos);},
                                throwable -> {
                                    isSearchingMode = true;
                                    Log.e(TAG,throwable.getMessage());
                                    setState(State.RELOADED_REPOS_ERROR,"errmsg",throwable.getMessage());
                                }),
                UnsubscribeLifeCycle.DESTROY);
    }

    protected void onMovieItemClick(IGitRepoModel repoModel) {
        setState(State.CLICK_REPO_DETAIL, "name", repoModel.getFullname());
    }

    public boolean isDataLoading() {
        return isDataLoading;
    }

    public boolean isSearchingMode() {
        return isSearchingMode;
    }

    public enum State {
        RELOADED_REPOS,
        LOADMORE_REPOS,
        RELOADED_REPOS_ERROR,
        CLICK_REPO_DETAIL
    }
}
