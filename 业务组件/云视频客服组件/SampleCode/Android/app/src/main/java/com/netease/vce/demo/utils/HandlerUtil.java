package com.netease.vce.demo.utils;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtil {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void post2MainThread(Runnable runnable){
        mHandler.post(runnable);
    }

    public static void post2MainThreadDelay(Runnable runnable, long delayMillis){
        mHandler.postDelayed(runnable, delayMillis);
    }

}
