package com.nvg.exam.phannguyen.data.models.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.nvg.exam.phannguyen.data.db.AppDB;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

/**
 * Created by phannguyen on 7/29/17.
 */
@Entity(tableName = AppDB.REPO_TABLE)
public class LocalGitRepoModel implements IGitRepoModel {
    @PrimaryKey
    private String fullname;

    private String title;
    private String avatar;
    private String detailDescription;
    private int nth;

    public LocalGitRepoModel() {
    }

    @Ignore
    public LocalGitRepoModel(String fullname, String title, String avatar, String detailDescription, int nth) {
        this.fullname = fullname;
        this.title = title;
        this.avatar = avatar;
        this.detailDescription = detailDescription;
        this.nth = nth;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public String getDetailDescription() {
        return detailDescription;
    }

    @Override
    public String getFullname() {
        return fullname;
    }

    public void setNth(int nth) {
        this.nth = nth;
    }

    public int getNth() {
        return nth;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }
}
