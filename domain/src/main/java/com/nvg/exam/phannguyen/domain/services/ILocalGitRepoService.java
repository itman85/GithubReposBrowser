package com.nvg.exam.phannguyen.domain.services;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

import java.util.List;

import rx.Observable;

/**
 * Created by phannguyen on 7/28/17.
 */

public interface ILocalGitRepoService {
    Observable<List<? extends IGitRepoModel>> getGitRepos(int page);
    Observable<Void> saveGitRepo(List<? extends IGitRepoModel> repos);
    Observable<IGitRepoModel> getDetailGitRepo(String name);
    Observable<List<? extends IGitRepoModel>> searchRepos(String text);
}
