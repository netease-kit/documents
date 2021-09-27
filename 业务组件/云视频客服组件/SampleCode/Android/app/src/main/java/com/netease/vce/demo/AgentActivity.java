package com.netease.vce.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.netease.vce.demo.utils.DialogUtil;
import com.netease.vce.demo.utils.NavUtils;
import com.netease.vce.demo.utils.ToastUtils;
import com.netease.vce.demo.view.IncomingDialog;
import com.netease.yunxin.vce.sdk.NEAccountInfo;
import com.netease.yunxin.vce.sdk.NECallback;
import com.netease.yunxin.vce.sdk.NEMeetingInfo;
import com.netease.yunxin.vce.sdk.NEMeetingOnInjectedMenuItemClickListener;
import com.netease.yunxin.vce.sdk.NEVCEError;
import com.netease.yunxin.vce.sdk.agent.CallState;
import com.netease.yunxin.vce.sdk.agent.LoginState;
import com.netease.yunxin.vce.sdk.agent.NEAgent;
import com.netease.yunxin.vce.sdk.agent.NEAgentListener;
import com.netease.yunxin.vce.sdk.menu.NEMeetingMenuItem;
import com.netease.yunxin.vce.sdk.menu.NEMenuClickInfo;
import com.netease.yunxin.vce.sdk.menu.NEMenuItemInfo;
import com.netease.yunxin.vce.sdk.menu.NEMenuItems;
import com.netease.yunxin.vce.sdk.menu.NEMenuStateController;
import com.netease.yunxin.vce.sdk.menu.NEMenuVisibility;
import com.netease.yunxin.vce.sdk.menu.NESingleStateMenuItem;
import com.netease.yunxin.vce.sdk.net.model.BusinessTypeModel;
import com.netease.yunxin.vce.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class AgentActivity extends BaseActivity {

    private final static String TAG = "AgentActivity";

    private final static int ITEM_INVITE = 201;

    private final static int ITEM_TRANSFER = 202;

    private TextView agentNameTV;

    private TextView businessTypeTV;

    private SwitchCompat checkInSwitch;

    private boolean[] checkedItem;

    private IncomingDialog incomingDialog = null;

    /**
     * 业务类型列表
     */
    private List<BusinessTypeModel> businessTypeModelList = new ArrayList<>();

    private List<BusinessTypeModel> selectedBusinessTypeModelList = new ArrayList<>();

    private NEAgentListener agentListener = new NEAgentListener() {

        @Override
        public void onInviteAgentJoinRoom(String roomId) {
        }
        @Override
        public void onLoginStateChange(int agentLoginState) {
            if( agentLoginState == LoginState.IDLE){
                ToastUtils.show(AgentActivity.this, "当前账号已在其他端登录，您已退出登录");
                AgentActivity.this.finish();
            }

        }
        @Override
        public void onCheckInStateChange(boolean isCheck) {
            checkInSwitch.setChecked(isCheck);
        }
        @Override
        public void onCallStateChange(int callState) {
            if (callState == CallState.INCOMING) {
                incomingDialog = new IncomingDialog(AgentActivity.this);
                incomingDialog.setYesOnclickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        NEAgent.getInstance().accept(AgentActivity.this, new NECallback<Void>() {

                            @Override
                            public void onResult(int resultCode, String resultMsg, Void resultData) {
                                LogUtils.d(TAG,
                                           "NEAgent accept resultCode = " + resultCode + ", resultMsg = " + resultMsg);
                            }
                        });
                        incomingDialog.dismiss();
                    }
                });
                incomingDialog.setNoOnclickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        NEAgent.getInstance().reject(AgentActivity.this, new NECallback<Void>() {

                            @Override
                            public void onResult(int resultCode, String resultMsg, Void resultData) {
                                LogUtils.d(TAG,
                                           "NEAgent reject resultCode = " + resultCode + ", resultMsg = " + resultMsg);
                            }
                        });
                        incomingDialog.dismiss();
                    }
                });
                incomingDialog.show();
            } else if (callState == CallState.IDLE) {
                if (incomingDialog != null) {
                    incomingDialog.dismiss();
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        initViews();
        initData();
        getBusinessTypes();
    }

    private void getBusinessTypes() {
        NEAgent.getInstance().queryGroupList(new NECallback<List<BusinessTypeModel>>() {

            @Override
            public void onResult(int resultCode, String resultMsg, List<BusinessTypeModel> resultData) {
                LogUtils.d(TAG, "getBusinessTypes onSuccess resultData = " + resultData);
                if (resultData != null) {
                    businessTypeModelList.addAll(resultData);
                    LogUtils.d(TAG, "getBusinessTypes onSuccess businessTypeModelList = " + businessTypeModelList);
                } else {
                    LogUtils.d(TAG, "getBusinessTypes onSuccess but result == null");
                }

            }
        });
    }

    private void initViews() {
        agentNameTV = findViewById(R.id.tv_agent_name);
        businessTypeTV = findViewById(R.id.tv_business_type);
        checkInSwitch = findViewById(R.id.vip_switch);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void initData() {
        agentNameTV.setText(getIntent().getStringExtra(NavUtils.AGENT_NAME));
        checkInSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkInSwitch.isChecked()) {
                    List<String> categoryList = getMultiCheckedCodes();
                    if (categoryList.size() > 0) {
                        NEAgent.getInstance().checkIn(agentNameTV.getText().toString(), categoryList);
                    } else {
                        ToastUtils.show(AgentActivity.this, "请先选择办理业务类型");
                        checkInSwitch.setChecked(false);
                    }

                } else {
                    NEAgent.getInstance().checkout();
                }
            }
        });
        NEAgent.getInstance().addListener(agentListener);


        NEAgent.getInstance().getAccountInfo(new NECallback<NEAccountInfo>() {

            @Override
            public void onResult(int resultCode, String resultMsg, NEAccountInfo resultData) {
                LogUtils.d(TAG, "getAccountInfo resultCode = " + resultCode + ", resultMsg = " + resultMsg + ", resultData = " + resultData);
                if (resultCode == NEVCEError.ERROR_CODE_SUCCESS && resultData != null) {
                    agentNameTV.setText(resultData.accountName);
                }
            }
        });

        List<NEMeetingMenuItem> toolbarMenuItems = new ArrayList<>();
        toolbarMenuItems.add(NEMenuItems.micMenu());
        toolbarMenuItems.add(NEMenuItems.cameraMenu());
        toolbarMenuItems.add(NEMenuItems.screenShareMenu());
        toolbarMenuItems.add(NEMenuItems.whiteBoardShareMenu());
        NEAgent.getInstance().setInjectedToolMenuItems(toolbarMenuItems);
        List<NEMeetingMenuItem> moreMenuItems = new ArrayList<>();
        moreMenuItems.add(NEMenuItems.chatMenu());
        NESingleStateMenuItem singleStateMenuItem = new NESingleStateMenuItem(ITEM_TRANSFER,
                                                                              NEMenuVisibility.VISIBLE_ALWAYS,
                                                                              new NEMenuItemInfo("转接",
                                                                                                 R.drawable.transfer));
        moreMenuItems.add(singleStateMenuItem);
        singleStateMenuItem = new NESingleStateMenuItem(ITEM_INVITE, NEMenuVisibility.VISIBLE_ALWAYS,
                                                        new NEMenuItemInfo("邀请", R.drawable.invite));
        moreMenuItems.add(singleStateMenuItem);
        NEAgent.getInstance().setInjectedMoreMenuItems(moreMenuItems);
        NEAgent.getInstance().setOnInjectedMenuItemClickListener(new NEMeetingOnInjectedMenuItemClickListener() {

            @Override
            public void onInjectedMenuItemClick(Context context, NEMenuClickInfo clickInfo, NEMeetingInfo meetingInfo,
                                                NEMenuStateController stateController) {
                if (clickInfo.getItemId() == ITEM_INVITE) {
                    LogUtils.d(TAG, "onInviteClick");
                    final String[] checkedCode = {""};
                    DialogUtil.showSingleChoiceDialog(context, "邀请", getShowItems(), -1,
                                                      new DialogInterface.OnClickListener() {

                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              String checkedDesc = getShowItems()[which].toString();
                                                              checkedCode[0] = getSingleCheckedCodeByDesc(checkedDesc);
                                                          }
                                                      }, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(TextUtils.isEmpty(checkedCode[0])){
                                        ToastUtils.show(AgentActivity.this, "请选择业务类型");
                                        return;
                                    }

                                    List<String> checkedCodeList = new ArrayList<>();
                                    checkedCodeList.add(checkedCode[0]);
                                    NEAgent.getInstance().invite(checkedCodeList, new NECallback<Void>() {

                                        @Override
                                        public void onResult(int resultCode, String resultMsg, Void resultData) {
                                            if(resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                                                ToastUtils.show(AgentActivity.this, "邀请成功");
                                            }else{
                                                LogUtils.d(TAG, "邀请失败， code = " + resultCode + ", msg = " + resultMsg);
                                                ToastUtils.show(AgentActivity.this, "邀请已经发送过了");
                                            }
                                        }
                                    });
                                }
                            });
                } else if (clickInfo.getItemId() == ITEM_TRANSFER) {
                    LogUtils.d(TAG, "onTransferClick");
                    final String[] checkedCode = {""};
                    DialogUtil.showSingleChoiceDialog(context, "转接", getShowItems(), -1,
                                                      new DialogInterface.OnClickListener() {

                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              String checkedDesc = getShowItems()[which].toString();
                                                              checkedCode[0] = getSingleCheckedCodeByDesc(checkedDesc);
                                                          }
                                                      }, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(TextUtils.isEmpty(checkedCode[0])){
                                        ToastUtils.show(AgentActivity.this, "请选择业务类型");
                                        return;
                                    }
                                    List<String> checkedCodeList = new ArrayList<>();
                                    checkedCodeList.add(checkedCode[0]);
                                    NEAgent.getInstance().transfer(checkedCodeList, new NECallback<Void>() {

                                        @Override
                                        public void onResult(int resultCode, String resultMsg, Void resultData) {
                                            if(resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                                                ToastUtils.show(AgentActivity.this, "转接成功");
                                            }else{
                                                LogUtils.d(TAG, "转接失败， code = " + resultCode + ", msg = " + resultMsg);
                                                ToastUtils.show(AgentActivity.this, "转接已经发送过了");
                                            }
                                        }
                                    });
                                }
                            });
                }
            }
        });
    }

    public void selectBusinessType(View view) {
        if(checkInSwitch.isChecked()){
            ToastUtils.show(AgentActivity.this, "服务开启时，不允许选择办理业务类型");
            return;
        }

        LogUtils.d(TAG, "selectBusinessType businessTypeModelList = " + businessTypeModelList);
        DialogUtil.showMultiChoiceDialog(AgentActivity.this, getString(R.string.please_select), getShowItems(),
                                         checkedItem, (dialog, which, isChecked) -> {
                    BusinessTypeModel currentSelectBusinessModel = getBusinessTypeModel(which);
                    if (isChecked) {
                        selectedBusinessTypeModelList.add(currentSelectBusinessModel);
                    } else {
                        selectedBusinessTypeModelList.remove(currentSelectBusinessModel);
                    }

                }, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        businessTypeTV.setText(getCheckedDescs());
                    }
                });
    }

    private CharSequence[] getShowItems() {
        CharSequence[] items = new CharSequence[businessTypeModelList.size()];
        checkedItem = getCheckedItem();
        for (BusinessTypeModel businessTypeModel : businessTypeModelList) {
            items[businessTypeModelList.indexOf(businessTypeModel)] = businessTypeModel.getDesc();
        }
        return items;
    }

    private boolean[] getCheckedItem() {
        boolean[] checkedItem = new boolean[businessTypeModelList.size()];
        if (selectedBusinessTypeModelList != null) {
            for (BusinessTypeModel typeModel : selectedBusinessTypeModelList) {
                checkedItem[businessTypeModelList.indexOf(typeModel)] = true;
            }
        }
        return checkedItem;
    }

    private String getCheckedDescs() {
        String desc = "";
        if (selectedBusinessTypeModelList != null && selectedBusinessTypeModelList.size() > 0) {
            for (BusinessTypeModel typeModel : selectedBusinessTypeModelList) {
                desc += typeModel.getDesc() + "、";
            }
        } else {
            desc = "请选择";
        }
        return desc;
    }

    private String getSingleCheckedCodeByDesc(String desc) {
        String checkedCode = "";
        if (!TextUtils.isEmpty(desc) && businessTypeModelList != null) {
            for (BusinessTypeModel typeModel : businessTypeModelList) {
                if (desc.equals(typeModel.getDesc())) {
                    checkedCode = typeModel.getCode();
                }
            }
        }
        return checkedCode;
    }

    private List<String> getMultiCheckedCodes() {
        List<String> categoryList = new ArrayList<>();
        if (selectedBusinessTypeModelList != null) {
            for (BusinessTypeModel typeModel : selectedBusinessTypeModelList) {
                categoryList.add(typeModel.getCode());
            }
        }
        return categoryList;
    }

    private BusinessTypeModel getBusinessTypeModel(int index) {
        if (businessTypeModelList == null) {
            LogUtils.d(TAG, "getBusinessTypeModel but businessTypeModelList == null");
            return null;
        }
        return businessTypeModelList.get(index);
    }

    @Override
    protected void onDestroy() {
        NEAgent.getInstance().removeListener(agentListener);
        super.onDestroy();
    }
    public void logout(View view) {
        NEAgent.getInstance().logout(null);
        finish();
    }
}
