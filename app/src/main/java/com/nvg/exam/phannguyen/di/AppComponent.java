package com.nvg.exam.phannguyen.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by phannguyen on 7/29/17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    ActivityComponent plus(ActivityModule activityModule);
}
