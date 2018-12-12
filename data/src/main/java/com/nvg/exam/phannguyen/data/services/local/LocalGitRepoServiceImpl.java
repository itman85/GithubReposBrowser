package com.nvg.exam.phannguyen.data.services.local;

import com.nvg.exam.phannguyen.data.db.AppDB;
import com.nvg.exam.phannguyen.data.models.converters.GitRepoConverter;
import com.nvg.exam.phannguyen.data.models.local.LocalGitRepoModel;
import com.nvg.exam.phannguyen.domain.DomainConstant;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;
import com.nvg.exam.phannguyen.domain.services.ILocalGitRepoService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by phannguyen on 7/29/17.
 */

public class LocalGitRepoServiceImpl implements ILocalGitRepoService {
    private AppDB mAppDB;
    private GitRepoConverter dataConverter;
    public LocalGitRepoServiceImpl(AppDB mAppDB,GitRepoConverter dataConverter) {
        this.mAppDB = mAppDB;
        this.dataConverter = dataConverter;
    }

    @Override
    public Observable<List<? extends IGitRepoModel>> getGitRepos(int page) {
        return Observable.defer(() -> {
            try {
                int nth = page >= 1 ? (page - 1) * DomainConstant.ITEMS_PER_PAGE_NUMBER + 1 : 1;
                List<LocalGitRepoModel> repoModels = mAppDB.gitRepoDAO().loadGitReposFromNth(nth);
                return Observable.just(repoModels);
            } catch (Exception ex) {
                return Observable.error(ex);
            }
        });
    }

    @Override
    public Observable<Void> saveGitRepo(List<? extends IGitRepoModel> repos) {
        return Observable.defer(() -> {
            try{
                List<LocalGitRepoModel> localGitRepoModelList = new ArrayList<LocalGitRepoModel>();
                for(IGitRepoModel gitRepoModel:repos){
                    localGitRepoModelList.add(dataConverter.convert(gitRepoModel));
                }
                mAppDB.gitRepoDAO().insertOrReplaceGitRepo(localGitRepoModelList);
                return Observable.just(null);
            }catch (Exception ex){
                return Observable.error(ex);
            }
        });
    }

    @Override
    public Observable<IGitRepoModel> getDetailGitRepo(String name) {
        return Observable.defer(() -> {
            try {
                LocalGitRepoModel gitRepoModel =  mAppDB.gitRepoDAO().loadDetailRepoByFullname(name);
                return Observable.just(gitRepoModel);
            }catch (Exception ex){
                return Observable.error(ex);
            }
        });
    }

    @Override
    public Observable<List<? extends IGitRepoModel>> searchRepos(String name) {
        return Observable.defer(() -> {
            try {
                List<LocalGitRepoModel> gitReposList =  mAppDB.gitRepoDAO().searchReposByName(name);
                return Observable.just(gitReposList);
            }catch (Exception ex){
                return Observable.error(ex);
            }
        });
    }
}
