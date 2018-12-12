package com.nvg.exam.phannguyen.ui.utils;

import android.support.v7.widget.SearchView;
import android.util.Log;

import rx.Observable;

/**
 * Created by phannguyen on 7/30/17.
 */

public class SearchViewObservableUtil {
    public static Observable<String> observeTextChange(SearchView view) {

        return Observable.create(subscriber -> {
            view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    view.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d("SEARCH",newText);
                    subscriber.onNext(newText);
                    return true;
                }
            });
        });

    }
}
