package com.netease.vce.demo;

import com.netease.yunxin.vce.sdk.net.HttpHelper;
import com.netease.yunxin.vce.sdk.net.model.AccountResponseModel;

import java.util.HashMap;
import java.util.Map;

public class AppNetApi {
    private static final String GET_ACCOUNT_API = "/v1/sdk/account/anonymous";
    public static void getAccountInfo(HttpHelper.HttpResultCallback<AccountResponseModel> callback) {
        String url = AppConfig.SERVER_URL + GET_ACCOUNT_API;
        Map<String, String> extraHeaders = new HashMap<>(1);
        extraHeaders.put("appKey", AppConfig.APP_KEY_VCE);
        HttpHelper.requestByGet(url, extraHeaders, callback);
    }

}
