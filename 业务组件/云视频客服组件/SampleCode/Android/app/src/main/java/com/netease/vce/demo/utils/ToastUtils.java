package com.netease.vce.demo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void show(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int resId){
        Toast.makeText(context, context.getText(resId), Toast.LENGTH_SHORT).show();
    }
}
