package com.nvg.exam.phannguyen.ui.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.nvg.exam.phannguyen.R;
import com.nvg.exam.phannguyen.ui.base.BaseViewModel;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.henrytao.mvvmlifecycle.rx.SubscriptionUtils;
import me.henrytao.mvvmlifecycle.rx.Transformer;
import me.henrytao.mvvmlifecycle.rx.UnsubscribeLifeCycle;
import rx.Observable;
import rx.subscriptions.Subscriptions;

/**
 * Created by phannguyen on 7/29/17.
 */

public class ProgressBarUIService {
    private final Activity mActivity;
    private final Context mContext;

    public ProgressBarUIService(Activity activity) {
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
    }

    public <T> Observable.Transformer<T, T> applyLoadingTransform(BaseViewModel observer) {
        return apply(observer, mContext.getString(R.string.text_loading));
    }

    private <T> Observable.Transformer<T, T> apply(BaseViewModel observer, CharSequence message) {
        String ID = UUID.randomUUID().toString();
        return observable -> observable
                .doOnSubscribe(() -> observer.manageSubscription(
                        ID,
                        Observable.just(null)
                                .delay(200, TimeUnit.MILLISECONDS)
                                .flatMap(o -> show(null, message, false, false).compose(Transformer.applyMainThreadScheduler()))
                                .subscribe(aVoid -> {
                                }, throwable -> Log.e("ERROR",throwable.getMessage())),
                        UnsubscribeLifeCycle.DESTROY_VIEW))
                .doOnCompleted(() -> observer.unsubscribe(ID))
                .doOnError(throwable -> observer.unsubscribe(ID))
                .doOnUnsubscribe(() -> observer.unsubscribe(ID));
    }

    private Observable<Void> show(CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return Observable.create(subscriber -> {
            ProgressDialog progressDialog = ProgressDialog.show(mActivity, title, message, indeterminate, cancelable);
            subscriber.add(Subscriptions.create(() -> {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }));
            SubscriptionUtils.onNext(subscriber);
        });
    }


}
