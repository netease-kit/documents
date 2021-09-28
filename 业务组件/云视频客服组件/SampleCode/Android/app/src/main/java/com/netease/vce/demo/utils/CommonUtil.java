package com.netease.vce.demo.utils;

import android.os.Looper;
import android.text.TextUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class CommonUtil {


    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static boolean isEmpty(Collection collection) {

        return collection == null || collection.isEmpty();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkInMainThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must call at main thread");
        }
    }

    public static boolean isContain(String[] strArray, String value){
        if(strArray == null || strArray.length <= 0 || TextUtils.isEmpty(value)){
            return false;
        }

        for(int i = 0; i < strArray.length; i++){
            if(value.equals(strArray[i])){
                return true;
            }
        }
        return false;
    }
}
