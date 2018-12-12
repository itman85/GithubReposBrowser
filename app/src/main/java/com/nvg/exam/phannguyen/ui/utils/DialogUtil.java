package com.nvg.exam.phannguyen.ui.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by phannguyen on 7/29/17.
 */

public class DialogUtil {
    public static void showDialog(Context context, String title, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
