package com.nvg.exam.phannguyen.domain.services;

import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

import java.util.List;

import rx.Observable;

/**
 * Created by phannguyen on 7/28/17.
 */

public interface IApiGitRepoService {

    Observable<List<? extends IGitRepoModel>> loadGitReposList(int page, int size);

    Observable<IGitRepoModel> getDetaiGitRepo(String repoName);
}
