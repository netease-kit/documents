package com.netease.vce.demo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtil {

    private static AlertDialog alertDialog;

    public static void setAlertDialog(AlertDialog alertDialog) {
        DialogUtil.alertDialog = alertDialog;
    }

    public static AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public static androidx.appcompat.app.AlertDialog showMultiChoiceDialog(Context context, String title,
                                                                                   CharSequence[] items,
                                                                                   boolean[] checkedItems,
                                                                                   DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener,
                                                                                   DialogInterface.OnClickListener positiveOnclickListener) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        return builder.setTitle(title).setMultiChoiceItems(items, checkedItems, multiChoiceClickListener).setPositiveButton(
                "确定", positiveOnclickListener).show();
    }

    public static androidx.appcompat.app.AlertDialog showSingleChoiceDialog(Context context, String title,
                                                                                    CharSequence[] items,
                                                                                    int checkedItem,
                                                                                    DialogInterface.OnClickListener itemListener,
                                                                                    final DialogInterface.OnClickListener listener) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        return builder.setTitle(title).setSingleChoiceItems(items, checkedItem, itemListener).setPositiveButton("确定", listener)
               .show();
    }

    public static androidx.appcompat.app.AlertDialog showDialog(Context context, String title, String message,
                                                                        DialogInterface.OnClickListener positiveClickListener,
                                                                        DialogInterface.OnClickListener negativeClickListener) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        return builder.setTitle(title).setMessage(message).setPositiveButton("确定", positiveClickListener).setNegativeButton(
                "关闭", negativeClickListener).show();
    }
}
