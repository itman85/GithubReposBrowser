package com.nvg.exam.phannguyen.di;

import android.app.Application;

import com.nvg.exam.phannguyen.App;
import com.nvg.exam.phannguyen.data.di.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phannguyen on 7/29/17.
 */

@Module(includes = {DataModule.class})
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Application provideAppContext() {
        return mApp;
    }
}
