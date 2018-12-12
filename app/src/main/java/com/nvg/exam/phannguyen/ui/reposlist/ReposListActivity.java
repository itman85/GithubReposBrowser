package com.nvg.exam.phannguyen.ui.reposlist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.nvg.exam.phannguyen.R;
import com.nvg.exam.phannguyen.databinding.ActivityReposlistBinding;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.ui.base.BaseActivity;
import com.nvg.exam.phannguyen.ui.repodetail.RepoDetailActivity;
import com.nvg.exam.phannguyen.ui.reposlist.di.ReposListModule;
import com.nvg.exam.phannguyen.ui.reposlist.items.RepoItemViewHolder;
import com.nvg.exam.phannguyen.ui.utils.DialogUtil;
import com.nvg.exam.phannguyen.ui.utils.RecyclerViewObservableUtil;
import com.nvg.exam.phannguyen.ui.utils.SearchViewObservableUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.henrytao.mvvmlifecycle.MVVMObserver;
import me.henrytao.mvvmlifecycle.recyclerview.RecyclerViewBindingAdapter;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ReposListActivity extends BaseActivity<ActivityReposlistBinding> {
    private static final String TAG = ReposListActivity.class.getSimpleName();

    private ReposListViewModel mViewModel;

    private ReposAdapter mAdapter;

    @Override
    public void onSetContentView(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reposlist);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void onInitializeViewModels() {
        mViewModel = mActivityComponent.plus(new ReposListModule()).getReposListViewModel();
        addViewModel(mViewModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        setupSearchView(searchViewAndroidActionBar);
        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if(mViewModel.isSearchingMode())
                    mViewModel.resetReposList();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(SearchView searchView){
        SearchViewObservableUtil.observeTextChange(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe(s -> {
                    if((s==null || "".equals(s))){
                        if(mViewModel.isSearchingMode())
                            mViewModel.searchRepos("");
                    }else{
                        mViewModel.searchRepos(s);
                    }
                },throwable -> Log.e(TAG,throwable.getMessage()));
    }


    private void init(){
        mBinding.progressbar.setVisibility(View.GONE);
        mAdapter = new ReposAdapter(this);
        mBinding.reposlist.setAdapter(mAdapter);
        mBinding.reposlist.setLayoutManager(new LinearLayoutManager(this));
        setupListScroll();
        //
        manageSubscription(mViewModel.getState().observeOn(AndroidSchedulers.mainThread()).subscribe(state -> {
            switch (state.getName()) {
                case RELOADED_REPOS:
                    List<IGitRepoModel> reposList = state.getData().getList("repos", IGitRepoModel.class);
                    mAdapter.setRepos(reposList);
                    mBinding.reposlist.scrollToPosition(0);
                    break;
                case CLICK_REPO_DETAIL:
                    Intent intent = new Intent(this, RepoDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", state.getData().getString("name"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case LOADMORE_REPOS:
                    mBinding.progressbar.setVisibility(View.GONE);
                    List<IGitRepoModel> moreReposList = state.getData().getList("repos", IGitRepoModel.class);
                    mAdapter.addMoreData(moreReposList);
                    break;
                case RELOADED_REPOS_ERROR:
                    mBinding.progressbar.setVisibility(View.GONE);
                    String messageError = state.getData().get("errmsg",String.class);
                    DialogUtil.showDialog(this,"Error Message",messageError);
                    break;
            }
        }), UnsubscribeLifeCycle.DESTROY_VIEW);
    }

    private void setupListScroll() {
        RecyclerViewObservableUtil.observeLoadMore(mBinding.reposlist, 300)
                .filter(abool -> abool && !mViewModel.isDataLoading() && !mViewModel.isSearchingMode())
                .subscribe(aObj -> {
                    Log.d(TAG, "OnLoadMore");
                    ReposListActivity.this.runOnUiThread(() -> mBinding.progressbar.setVisibility(View.VISIBLE));
                    mViewModel.loadMoreRepo();
                });
    }
    private static class ReposAdapter extends RecyclerViewBindingAdapter<IGitRepoModel, RepoItemViewHolder> {

        public ReposAdapter(MVVMObserver observer) {
            super(RepoItemViewHolder.class, observer, new ArrayList<>());
        }

        public void setRepos(List<IGitRepoModel> repos) {
            mData.clear();
            mData.addAll(repos);
            notifyDataSetChanged();
        }

        public void addMoreData(List<IGitRepoModel> repos) {
            int currentSize = mData.size();
            mData.addAll(repos);
            notifyItemRangeChanged(currentSize, repos.size());
        }
    }
}
