package com.nvg.exam.phannguyen.ui.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RecyclerViewObservableUtil {
    public static Observable<RecyclerViewScrollInfo> observeScroll(RecyclerView view) {

        return Observable.create(subscriber -> {
            view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if(dy > 0) {
                        RecyclerViewScrollInfo info = new RecyclerViewScrollInfo(
                                ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition(),
                                recyclerView.getLayoutManager().getChildCount(),
                                recyclerView.getLayoutManager().getItemCount(),
                                recyclerView.getScrollState());
                        Log.d("RecycleView", info.toString());
                        subscriber.onNext(info);
                    }else{
                        RecyclerViewScrollInfo info = new RecyclerViewScrollInfo(-1,0,0,0);
                        subscriber.onNext(info);
                    }
                }

            });
        });

    }

    public static Observable<Integer> observeState(RecyclerView view) {

        return Observable.create(subscriber -> {
            view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    subscriber.onNext(newState);
                }
            });
        });
    }

    public static Observable<Boolean> observeLoadMore1(RecyclerView view, long time) {
        return
                observeState(view).debounce(time, TimeUnit.MILLISECONDS)
                        .filter(state -> state == RecyclerView.SCROLL_STATE_IDLE)
                        .flatMap(state -> observeScroll(view))
                        .filter(info -> info.getVisibleItemCount() + info.getPastVisiblesItems() >= info.getTotalItemCount())
                        .flatMap(info -> Observable.just(true));

    }

    public static Observable<Boolean> observeLoadMore(RecyclerView view, long time) {
        return Observable.combineLatest(observeState(view).debounce(time, TimeUnit.MILLISECONDS)
                ,observeScroll(view),(state, scrollInfo) -> {
                    if(state.intValue() == RecyclerView.SCROLL_STATE_IDLE &&
                            scrollInfo.getVisibleItemCount() + scrollInfo.getPastVisiblesItems() >= scrollInfo.getTotalItemCount()) {
                        return true;
                    }
                    return false;
                });


    }
}

