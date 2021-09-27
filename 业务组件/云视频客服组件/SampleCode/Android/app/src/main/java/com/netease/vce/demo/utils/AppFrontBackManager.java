package com.netease.vce.demo.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 应用前后台状态监听帮助类，仅在Application中使用
 */

public class AppFrontBackManager {

    private static AppFrontBackManager instance;
    private boolean isBackground;

    private OnAppStatusListener mOnAppStatusListener;

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        //打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1){
                //从后台切到前台
                isBackground = false;
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onFront();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0){
                //从前台切到后台
                isBackground = true;
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    private AppFrontBackManager(){}
    public static AppFrontBackManager getInstance(){
        if(instance == null){
            synchronized (AppFrontBackManager.class){
                if(instance == null){
                    instance = new AppFrontBackManager();
                }
            }
        }
        return instance;
    }
    public void initialize(Application application){
        if(application == null){
            return;
        }
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
    /**
     * 注册状态监听
     * @param listener
     */
    public void setListener( OnAppStatusListener listener){
        mOnAppStatusListener = listener;
    }
    public boolean isInBackground(){
        return isBackground;
    }

    public interface OnAppStatusListener{
        void onFront();
        void onBack();
    }

}