package com.nvg.exam.phannguyen.ui.utils;

/**
 * Created by phannguyen on 7/29/17.
 */

public class RecyclerViewScrollInfo {
    int pastVisiblesItems;
    int visibleItemCount;
    int totalItemCount;
    int state;

    public RecyclerViewScrollInfo(int pastVisiblesItems, int visibleItemCount, int totalItemCount, int state) {
        this.pastVisiblesItems = pastVisiblesItems;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        this.state = state;
    }

    public int getVisibleItemCount() {
        return visibleItemCount;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public int getState() {
        return state;
    }

    public int getPastVisiblesItems() {

        return pastVisiblesItems;
    }

    public String toString() {
        return pastVisiblesItems + "-" + visibleItemCount + "-" + totalItemCount + "-" + state;
    }
}
