package com.netease.vce.demo;

import android.os.Bundle;

import com.netease.vce.demo.utils.NavUtils;
import com.netease.yunxin.vce.sdk.guest.NEGuest;

import androidx.annotation.Nullable;

public class LauncherActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavUtils.startGuestActivity(this);
        NEGuest.getInstance().returnToVCERoom(LauncherActivity.this);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NEGuest.getInstance().returnToVCERoom(LauncherActivity.this);
    }
}
