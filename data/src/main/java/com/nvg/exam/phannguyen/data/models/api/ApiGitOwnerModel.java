package com.nvg.exam.phannguyen.data.models.api;

import com.google.gson.annotations.Expose;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ApiGitOwnerModel {
    @Expose
    public String avatar_url;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
