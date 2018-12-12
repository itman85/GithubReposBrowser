package com.nvg.exam.phannguyen.ui.utils;

import android.databinding.BindingAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by phannguyen on 7/29/17.
 */

public class DraweeViewBindingAdapter {
    @BindingAdapter({"draweeImageUrl"})
    public static void loadImage(SimpleDraweeView view, String imageUrl) {
        view.setImageURI(imageUrl);
    }
}
