package com.nvg.exam.phannguyen.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nvg.exam.phannguyen.data.models.local.LocalGitRepoModel;
import com.nvg.exam.phannguyen.domain.DomainConstant;

/**
 * Created by phannguyen on 7/29/17.
 */

@Database(entities = {LocalGitRepoModel.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract GitRepoDAO gitRepoDAO();

    public static final String FULLNAME_FIELD = "fullname";
    public static final String TITLE_FIELD = "title";
    public static final String NTH_FIELD = "nth";
    public static final String REPO_TABLE = "gitrepos";
    public static final String REPOS_QUERY = "SELECT * FROM "+ REPO_TABLE + " WHERE " + NTH_FIELD + " >= :nth ORDER BY " + NTH_FIELD
            + " LIMIT "+ DomainConstant.ITEMS_PER_PAGE_NUMBER;
    public static final String REPO_DETAIL_QUERY = "SELECT * FROM " + REPO_TABLE + " WHERE " + FULLNAME_FIELD  + " = :fullname";
    public static final String REPO_SEARCH_QUERY = "SELECT * FROM " + REPO_TABLE + " WHERE " + TITLE_FIELD  + " LIKE '%' || :name || '%'";

}
