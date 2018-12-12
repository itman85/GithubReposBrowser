package com.nvg.exam.phannguyen.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nvg.exam.phannguyen.data.models.local.LocalGitRepoModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static com.nvg.exam.phannguyen.data.db.AppDB.REPOS_QUERY;
import static com.nvg.exam.phannguyen.data.db.AppDB.REPO_DETAIL_QUERY;
import static com.nvg.exam.phannguyen.data.db.AppDB.REPO_SEARCH_QUERY;

/**
 * Created by phannguyen on 7/29/17.
 */
@Dao
public interface GitRepoDAO {
    @Query(REPOS_QUERY)
    List<LocalGitRepoModel> loadGitReposFromNth(int nth);

    @Query(REPO_DETAIL_QUERY)
    LocalGitRepoModel loadDetailRepoByFullname(String fullname);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceGitRepo(List<LocalGitRepoModel>repos);

    @Query(REPO_SEARCH_QUERY)
    List<LocalGitRepoModel> searchReposByName(String name);
}
