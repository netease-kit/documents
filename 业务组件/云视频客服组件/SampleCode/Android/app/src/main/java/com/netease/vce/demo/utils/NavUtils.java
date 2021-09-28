package com.netease.vce.demo.utils;

import android.content.Context;
import android.content.Intent;

import com.netease.vce.demo.AgentActivity;
import com.netease.vce.demo.AgentLoginActivity;
import com.netease.vce.demo.GuestActivity;
import com.netease.vce.demo.SelfInfo;
import com.netease.yunxin.vce.sdk.guest.NEGuest;
import com.netease.yunxin.vce.utils.LogUtils;

public class NavUtils {
    private static final String TAG = "NavUtils";
    public static final String AGENT_NAME = "agent_name";

    public static void startGuestActivity(Context context){
        LogUtils.d(TAG, "start guest activity");
        Intent intent = new Intent(context, GuestActivity.class);
        context.startActivity(intent);
    }

    public static void startAgentLoginActivity(Context context){
        LogUtils.d(TAG, "start agent activity");
        Intent intent = new Intent(context, AgentLoginActivity.class);
        context.startActivity(intent);
    }

    public static void startAgentActivity(Context context, String agentName){
        LogUtils.d(TAG, "start agent activity");
        Intent intent = new Intent(context, AgentActivity.class);
        intent.putExtra(AGENT_NAME, agentName);
        context.startActivity(intent);
    }

    public static void startCall(Context context, SelfInfo selfInfo){
        LogUtils.d(TAG, "startCallActivity");
        NEGuest.getInstance().callWithUI(context, selfInfo.getCode(), selfInfo.getName(), selfInfo.isVIP());
    }
}
