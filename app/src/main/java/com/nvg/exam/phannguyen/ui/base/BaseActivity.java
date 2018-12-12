package com.nvg.exam.phannguyen.ui.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.nvg.exam.phannguyen.App;
import com.nvg.exam.phannguyen.di.ActivityComponent;
import com.nvg.exam.phannguyen.di.ActivityModule;
import com.nvg.exam.phannguyen.di.AppComponent;

import me.henrytao.mvvmlifecycle.MVVMActivity;

/**
 * Created by phannguyen on 7/29/17.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends MVVMActivity {
    protected T mBinding;
    protected ActivityComponent mActivityComponent;

    public AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivityComponent = getAppComponent().plus(new ActivityModule(this));
        super.onCreate(savedInstanceState);
    }
}
