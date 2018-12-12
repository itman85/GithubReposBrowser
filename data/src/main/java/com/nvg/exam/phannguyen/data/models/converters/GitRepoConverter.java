package com.nvg.exam.phannguyen.data.models.converters;

import com.nvg.exam.phannguyen.data.models.converters.base.DataConverter;
import com.nvg.exam.phannguyen.data.models.local.LocalGitRepoModel;
import com.nvg.exam.phannguyen.domain.models.IGitRepoModel;

/**
 * Created by phannguyen on 7/29/17.
 */

public class GitRepoConverter implements DataConverter<IGitRepoModel,LocalGitRepoModel>{
    @Override
    public LocalGitRepoModel convert(IGitRepoModel fromData) {
        if(fromData instanceof LocalGitRepoModel)
            return (LocalGitRepoModel) fromData;
        return new LocalGitRepoModel(fromData.getFullname(),fromData.getTitle(),fromData.getAvatar(),
                fromData.getDetailDescription(),fromData.getNth());

    }
}
