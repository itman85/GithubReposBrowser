package com.nvg.exam.phannguyen.ui.repodetail;

import android.databinding.ObservableField;
import android.util.Log;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.usecases.GetDetailRepoUsecase;
import com.nvg.exam.phannguyen.ui.base.BaseViewModel;

import me.henrytao.mvvmlifecycle.rx.Transformer;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RepoDetailViewModel  extends BaseViewModel {
    private static final String TAG = "RepoDetailModule";
    public ObservableField<String> description = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> avatar = new ObservableField<>();
    private GetDetailRepoUsecase getDetailRepoUsecase;
    private String repoFullname;

    public RepoDetailViewModel(GetDetailRepoUsecase getDetailRepoUsecase, String repoFullname) {
        this.getDetailRepoUsecase = getDetailRepoUsecase;
        this.repoFullname = repoFullname;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    protected void loadData(){
        manageSubscription(getDetailRepoUsecase.getDetailGitRepo(repoFullname)
                        .compose(Transformer.applyIoScheduler())
                        .subscribe(this::bindData, throwable -> Log.e(TAG, throwable.getMessage())),
                UnsubscribeLifeCycle.DESTROY);

    }

    private void bindData(IGitRepoModel repoModel){
        if(repoModel!=null) {
            title.set(repoModel.getTitle());
            description.set(repoModel.getDetailDescription());
            avatar.set(repoModel.getAvatar());
        }
    }
}
