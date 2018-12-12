package com.nvg.exam.phannguyen.domain.models;

/**
 * Created by phannguyen on 7/28/17.
 */

public interface IGitRepoModel {
    String getTitle();
    String getAvatar();
    String getDetailDescription();
    String getFullname();
    int getNth();
    void setNth(int nth);
}
