package com.netease.vce.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.vce.demo.utils.AppFrontBackManager;
import com.netease.vce.demo.utils.NavUtils;
import com.netease.vce.demo.utils.NotificationUtils;
import com.netease.vce.demo.utils.ToastUtils;
import com.netease.yunxin.vce.sdk.LoginState;
import com.netease.yunxin.vce.sdk.NECallback;
import com.netease.yunxin.vce.sdk.NEVCEError;
import com.netease.yunxin.vce.sdk.guest.GroupList;
import com.netease.yunxin.vce.sdk.guest.GuestCallState;
import com.netease.yunxin.vce.sdk.guest.NEGuest;
import com.netease.yunxin.vce.sdk.guest.NEGuestListener;
import com.netease.yunxin.vce.sdk.manager.NetworkManager;
import com.netease.yunxin.vce.sdk.menu.NEMeetingMenuItem;
import com.netease.yunxin.vce.sdk.menu.NEMenuItems;
import com.netease.yunxin.vce.sdk.net.HttpHelper;
import com.netease.yunxin.vce.sdk.net.model.AccountResponseModel;
import com.netease.yunxin.vce.sdk.net.model.BusinessTypeModel;
import com.netease.yunxin.vce.utils.FinanceUtils;
import com.netease.yunxin.vce.utils.LogUtils;
import com.netease.yunxin.vce.utils.NetWorkUtils;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;

public class GuestActivity extends BaseActivity {

    private final static String TAG = "GuestActivity";

    /**
     * 业务类型列表
     */
    private List<BusinessTypeModel> businessTypeModelList = new ArrayList<>();

    private EditText guestNameET;

    private TextView businessTypeTV;

    private SwitchCompat vipSwitch;

    private BusinessTypeModel selectBusinessModel;

    private int checkedItem = -1;

    private boolean fetchAuthInfoSuccess;

    private final NetworkManager.NetChangeObserver netObserver = new NetworkManager.NetChangeObserver() {

        @Override
        public void onConnected(NetworkManager.NetType type) {
            ToastUtils.show(GuestActivity.this, R.string.net_recovery);
            if (!fetchAuthInfoSuccess) {
                fetchAuthInfo();
            }

        }
        @Override
        public void onDisConnected() {
            ToastUtils.show(GuestActivity.this, R.string.net_error);
        }
    };

    public static boolean checkGuestName(String nickname) {
        if (nickname == null) {
            return false;
        }
        int nicknameLength = 0;
        char[] chars = nickname.toCharArray();
        for (char temp : chars) {
            if (temp <= 255) {
                nicknameLength += 1;
            } else {
                nicknameLength += 2;
            }
        }
        return nicknameLength <= 20 && Pattern.matches("[a-zA-Z0-9\\u0020\\u4E00-\\u9FA5]*", nickname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        //请求权限
        requestPermissions();
        //1、先获取账号信息， 2、登录im，3、登录会议sdk
        fetchAuthInfo();
        //        queueService = NEGuest.getInstance().getQueueService();
        //        queueService.initialize();
        initViews();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String toPage = intent.getStringExtra(Constant.TO_PAGE);
        LogUtils.d(TAG, "onNewIntent toPage = " + toPage);
//        if (Constant.PAGE_CALL.equals(toPage)) {
//            SelfInfo selfInfo = intent.getParcelableExtra(Constant.SELF_INFO);
//            NavUtils.startCall(GuestActivity.this, selfInfo);
//        } else if (Constant.PAGE_MEETING.equals(toPage)) {
//            String meetingId = intent.getStringExtra(Constant.MEETING_ID);
//            String name = intent.getStringExtra(Constant.NAME);
//            NavUtils.startMeetingActivity(GuestActivity.this, meetingId, name);
//        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        FinanceUtils.returnToFinance(this, false);
    }

    private void initViews() {
        guestNameET = findViewById(R.id.tv_agent_name);
        businessTypeTV = findViewById(R.id.tv_business_type);
        vipSwitch = findViewById(R.id.vip_switch);
        FinanceUtils.returnToFinance(this, true);
    }

    private NEGuestListener guestListener = new NEGuestListener() {

        @Override
        public void onInviteGuestJoinRoom(String roomId) {
//            NavUtils.startMeetingActivity(GuestActivity.this, roomId, guestNameET.getText().toString().trim());
        }
        @Override
        public void onTransfered() {
        }
        @Override
        public void onCallStateChange(GuestCallState callState) {
        }
        @Override
        public void onQueryGroupList(GroupList groupList) {
        }
        @Override
        public void onLoginStateChange(LoginState loginState) {
        }
    };

    private void initData() {
        NetworkManager.getInstance().addListener(netObserver);
        NEGuest.getInstance().addListener(guestListener);
        List<NEMeetingMenuItem> toolbarMenuItems = new ArrayList<>();
        toolbarMenuItems.add(NEMenuItems.micMenu());
        toolbarMenuItems.add(NEMenuItems.cameraMenu());
        toolbarMenuItems.add(NEMenuItems.screenShareMenu());
        toolbarMenuItems.add(NEMenuItems.whiteBoardShareMenu());
        NEGuest.getInstance().setInjectedToolMenuItems(toolbarMenuItems);

        List<NEMeetingMenuItem> moreMenuItems = new ArrayList<>();
        moreMenuItems.add(NEMenuItems.chatMenu());
        NEGuest.getInstance().setInjectedMoreMenuItems(moreMenuItems);
    }

    private void fetchAuthInfo() {
        AppNetApi.getAccountInfo(new HttpHelper.HttpResultCallback<AccountResponseModel>() {

            @Override
            public void onSuccess(AccountResponseModel result) {
                fetchAuthInfoSuccess = true;
                LogUtils.d(TAG, "getAccountInfo onSuccess result = " + result);
                if (result != null) {
                    NEGuest.getInstance().loginWithToken(result.accountId, result.accountToken,
                                                         new NECallback<Void>() {

                                                             @Override
                                                             public void onResult(int resultCode, String resultMsg,
                                                                                  Void resultData) {
                                                                 if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                                                                     LogUtils.d(TAG, "login vce sdk success");
                                                                     //loginMeetingSDK会更新header里面的accountId，accountToken
                                                                     getBusinessTypes();
                                                                 } else {
                                                                     LogUtils.d(TAG, "login vce sdk but login failed");
                                                                 }
                                                             }
                                                         });

                }
            }
            @Override
            public void onFailed(int code, String msg) {
                fetchAuthInfoSuccess = false;
                LogUtils.d(TAG, "getAccountInfo onFailed code = " + code + ", msg = " + msg);
            }
            @Override
            public void onException(Throwable throwable) {
                fetchAuthInfoSuccess = false;
                LogUtils.d(TAG, "getAccountInfo onException exception = " + throwable.getMessage());
            }
        });
    }

    private void getBusinessTypes() {
        LogUtils.d(TAG, "queryGroupList");
        NEGuest.getInstance().queryGroupList(new NECallback<List<BusinessTypeModel>>() {

            @Override
            public void onResult(int resultCode, String resultMsg, List<BusinessTypeModel> resultData) {
                if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                    LogUtils.d(TAG, "queryGroupList onSuccess");
                    businessTypeModelList = resultData;
                } else {
                    LogUtils.d(TAG, "queryGroupList failed code = " + resultCode + ", msg = " + resultMsg);
                }
            }
        });
    }
    private void requestPermissions() {
        PermissionX.init(this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                           Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,
                                           Manifest.permission.RECORD_AUDIO).onExplainRequestReason(
                (scope, deniedList) -> scope
                        .showRequestReasonDialog(deniedList, getString(R.string.permission_is_necessity),
                                                 getString(R.string.i_know))).onForwardToSettings(
                (scope, deniedList) -> scope
                        .showForwardToSettingsDialog(deniedList, getString(R.string.you_need_open_permissions),
                                                     getString(R.string.i_know))).request(
                (allGranted, grantedList, deniedList) -> {
                    if (!allGranted) {
                        Toast.makeText(GuestActivity.this,
                                       getText(R.string.app_not_have_permissions).toString() + deniedList,
                                       Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        NetworkManager.getInstance().removeListener(netObserver);
        NEGuest.getInstance().removeListener(guestListener);
        super.onDestroy();
    }

    public void call(View view) {
        if (!NetWorkUtils.isNetWorkAvailable(GuestActivity.this)) {
            ToastUtils.show(GuestActivity.this, R.string.net_error);
        }
//        else if (QueueManager.getInstance().getState() != QueueManager.STATE_IDLE) {
//            LogUtils.d(TAG, "call but state = " + QueueManager.getInstance().getState());
//            ToastUtils.show(GuestActivity.this, R.string.doing_business);
//        }
        else {
            callWithCode(null, false);
        }
    }

    /**
     * @param businessCode 转接的业务代码
     * @param isReQueue    是否是转接重新排队的
     */
    private void callWithCode(String businessCode, boolean isReQueue) {
        if (TextUtils.isEmpty(getGuestName())) {
            ToastUtils.show(this, R.string.input_username);
            return;
        }
        if (!checkGuestName(getGuestName())) {
            ToastUtils.show(this, R.string.username_incorrect);
            return;
        }
        if (selectBusinessModel == null || TextUtils.isEmpty(selectBusinessModel.getCode())) {
            ToastUtils.show(this, R.string.select_business_type);
            return;
        }
        //优先使用传进来的业务code
        String code = TextUtils.isEmpty(businessCode) ? selectBusinessModel.getCode() : businessCode;
        SelfInfo selfInfo = new SelfInfo(guestNameET.getText().toString().trim(), code, selectBusinessModel.getDesc(),
                                         vipSwitch.isChecked());
        if (AppFrontBackManager.getInstance().isInBackground()) {
            NotificationUtils.showCallNotification(GuestActivity.this, selfInfo);
        } else {
            NavUtils.startCall(GuestActivity.this, selfInfo);
        }
    }

    public void selectBusinessType(View view) {
        CharSequence[] items = new CharSequence[businessTypeModelList.size()];
        for (BusinessTypeModel businessTypeModel : businessTypeModelList) {
            items[businessTypeModelList.indexOf(businessTypeModel)] = businessTypeModel.getDesc();
        }
        new AlertDialog.Builder(this).setTitle(getString(R.string.please_select)).setSingleChoiceItems(items,
                                                                                                       checkedItem,
                                                                                                       (dialog, which) -> {
                                                                                                           checkedItem = which;
                                                                                                           selectBusinessModel = getBusinessTypeModel(
                                                                                                                   which);
                                                                                                           if (selectBusinessModel !=
                                                                                                               null) {
                                                                                                               businessTypeTV
                                                                                                                       .setText(
                                                                                                                               selectBusinessModel
                                                                                                                                       .getDesc());
                                                                                                           } else {
                                                                                                               LogUtils.d(
                                                                                                                       TAG,
                                                                                                                       "selectBusinessType but business type not get");
                                                                                                               ToastUtils
                                                                                                                       .show(GuestActivity.this,
                                                                                                                             R.string.business_type_not_get);
                                                                                                           }
                                                                                                           dialog.dismiss();
                                                                                                       }).create()
                                     .show();
    }

    private String getGuestName() {
        return guestNameET.getText().toString().trim();
    }

    private BusinessTypeModel getBusinessTypeModel(int index) {
        if (businessTypeModelList == null) {
            LogUtils.d(TAG, "getBusinessTypeModel but businessTypeModelList == null");
            return null;
        }
        return businessTypeModelList.get(index);
    }
}