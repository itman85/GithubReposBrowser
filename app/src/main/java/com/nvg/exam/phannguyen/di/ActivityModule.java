package com.nvg.exam.phannguyen.di;

import android.app.Activity;

import com.nvg.exam.phannguyen.ui.utils.ProgressBarUIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phannguyen on 7/29/17.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ProgressBarUIService provideProgressBarUIService() {
        return new ProgressBarUIService(mActivity);
    }
}
