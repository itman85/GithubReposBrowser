package com.nvg.exam.phannguyen.ui.reposlist.items;

import android.databinding.ObservableField;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.ui.base.BaseViewModel;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RepoItemViewModel extends BaseViewModel {
    public ObservableField<String> avatar = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();
    private IGitRepoModel repoModel;

    public RepoItemViewModel() {
        register(this, Event.ON_REPO_ITEM_CLICK);
    }

    public void setRepoData(IGitRepoModel data) {
        repoModel = data;
        avatar.set(repoModel.getAvatar());
        title.set(repoModel.getTitle());
    }

    public void onItemClick() {
        dispatch(Event.ON_REPO_ITEM_CLICK, this.repoModel);
    }

    public enum Event {
        ON_REPO_ITEM_CLICK
    }

}
