package com.netease.vce.demo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.netease.vce.demo.utils.NavUtils;
import com.netease.yunxin.vce.sdk.NECallback;
import com.netease.yunxin.vce.sdk.NEVCEError;
import com.netease.yunxin.vce.sdk.agent.NEAgent;
import com.netease.yunxin.vce.utils.LogUtils;

import androidx.annotation.Nullable;

public class AgentLoginActivity extends BaseActivity {

    private final static String TAG = "AgentActivity";

    private String accountId = "19500000001";

    private String accountPwd = "123456789";

    private EditText agentNameET;
    private EditText passwordET;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_login);
        initViews();
        initData();
    }

    private void initViews() {
        agentNameET = findViewById(R.id.et_user_name);
        agentNameET.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                accountId = s.toString();
            }
        });
        passwordET = findViewById(R.id.et_password);
        passwordET.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                accountPwd = s.toString();
            }
        });
    }

    private void initData() {
        agentNameET.setHint("" + accountId);
        passwordET.setHint("" + accountPwd);
    }

    public void login(View view) {
        NEAgent.getInstance().loginWithPassword(accountId, accountPwd, new NECallback<Void>() {

            @Override
            public void onResult(int resultCode, String resultMsg, Void resultData) {
                if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                    LogUtils.d(TAG, "login vce sdk success");
                    //loginMeetingSDK会更新header里面的accountId，accountToken
                    NavUtils.startAgentActivity(AgentLoginActivity.this, accountId);
                } else {
                    LogUtils.d(TAG, "login vce sdk but login failed resultCode + " + resultCode + ", resultMsg = " + resultMsg);
                }
            }
        });
    }
}
