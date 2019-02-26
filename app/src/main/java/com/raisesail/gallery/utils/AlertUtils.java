package com.raisesail.gallery.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
/**
 * AlertDialog
 */

public class AlertUtils {
    public static AlertDialog.Builder showAlert(Context context, String title, String content, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setNegativeButton(context.getString(android.R.string.cancel), null);
        alert.setPositiveButton(context.getString(android.R.string.ok), onClickListener);
        alert.show();
        return alert;
    }
}