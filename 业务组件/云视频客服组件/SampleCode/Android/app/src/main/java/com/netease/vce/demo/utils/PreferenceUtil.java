package com.netease.vce.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    private final static String KEY_VIDEO_ENCODE_TYPE = "video_encode_type";
    private final static String KEY_SDK_DEVICE_ID = "key_sdk_device_id";
    private final static String SHARED_FILE_NAME = "netease_meeting_sdk_tv_shared";

    public static String getDeviceID(Context context) {
        return getString(context, KEY_SDK_DEVICE_ID);
    }

    public static void setDeviceID(Context context, String deviceID) {
        apply(context, KEY_SDK_DEVICE_ID, deviceID);
    }

    public static void saveVideoEncodeType(Context context, boolean isHard){
        apply(context, KEY_VIDEO_ENCODE_TYPE, isHard);
    }

    public static boolean isVideoEncodeHard(Context context){
        return getBool(context, KEY_VIDEO_ENCODE_TYPE);
    }

    private static String getString(Context context, String key) {
        return sharedPreferences(context).getString(key, null);
    }

    private static boolean getBool(Context context, String key) {
        return sharedPreferences(context).getBoolean(key, false);
    }

    private static void apply(Context context, String key, String value) {
        sharedPreferences(context).edit().putString(key, value).apply();
    }

    private static void apply(Context context, String key, boolean value) {
        sharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    private static SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE);
    }


}
