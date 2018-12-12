package com.nvg.exam.phannguyen.data.models.api;

import com.google.gson.annotations.Expose;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ApiGitRepoModel implements IGitRepoModel {
    @Expose
    public String name;
    @Expose
    public String full_name;
    @Expose
    public String description;
    @Expose
    public ApiGitOwnerModel owner;

    private int nth;

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getAvatar() {
        if (owner != null)
            return owner.getAvatar_url();
        return null;
    }

    @Override
    public String getDetailDescription() {
        return description;
    }

    @Override
    public String getFullname() {
        return full_name;
    }

    @Override
    public int getNth() {
        return nth;
    }

    @Override
    public void setNth(int nth) {
        this.nth = nth;
    }
}
