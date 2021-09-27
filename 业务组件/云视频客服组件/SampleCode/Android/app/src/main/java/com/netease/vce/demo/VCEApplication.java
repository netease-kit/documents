/*
 * Copyright (c) 2014-2020 NetEase, Inc.
 * All right reserved.
 */

package com.netease.vce.demo;

import android.app.Application;
import com.netease.vce.demo.R;
import com.netease.vce.demo.utils.AppFrontBackManager;
import com.netease.yunxin.vce.sdk.NECallback;
import com.netease.yunxin.vce.sdk.NEVCESDKConfig;
import com.netease.yunxin.vce.sdk.SceneType;
import com.netease.yunxin.vce.sdk.guest.NEGuest;
import com.netease.yunxin.vce.utils.LogUtils;

public class VCEApplication extends Application {
    private static VCEApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppFrontBackManager.getInstance().initialize(this);
        NEVCESDKConfig sdkConfig = new NEVCESDKConfig();
        sdkConfig.appKey = AppConfig.APP_KEY_VCE;
        sdkConfig.appName = getString(R.string.app_name);
        sdkConfig.scene = SceneType.FINANCE;
        sdkConfig.serverUrl = AppConfig.SERVER_URL;
        NEGuest.getInstance().initialize(this, sdkConfig, new NECallback<Void>() {

            @Override
            public void onResult(int resultCode, String resultMsg, Void resultData) {
                LogUtils.i("FinanceApplication", "initialize NEGuest resultCode = " + resultCode + ", resultMsg = " + resultMsg);
            }
        });
    }

    public static VCEApplication getInstance() {
        return instance;
    }
}
