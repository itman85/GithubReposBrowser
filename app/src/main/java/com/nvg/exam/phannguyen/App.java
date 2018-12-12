package com.nvg.exam.phannguyen;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nvg.exam.phannguyen.di.AppComponent;
import com.nvg.exam.phannguyen.di.AppModule;
import com.nvg.exam.phannguyen.di.DaggerAppComponent;

/**
 * Created by phannguyen on 7/29/17.
 */

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        Fresco.initialize(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
