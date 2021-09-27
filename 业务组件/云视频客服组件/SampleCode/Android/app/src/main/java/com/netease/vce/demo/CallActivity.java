package com.netease.vce.demo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.netease.vce.demo.utils.AVChatSoundPlayer;
import com.netease.vce.demo.utils.HandlerUtil;
import com.netease.yunxin.vce.NEVCECallback;
import com.netease.yunxin.vce.sdk.NEVCEError;
import com.netease.yunxin.vce.sdk.guest.NEGuest;
import com.netease.yunxin.vce.sdk.guest.NEGuestListener;
import com.netease.yunxin.vce.sdk.listener.IMLoginStatusListener;
import com.netease.yunxin.vce.sdk.manager.NetworkManager;
import com.netease.yunxin.vce.sdk.manager.PushManager;
import com.netease.yunxin.vce.sdk.net.model.GuestInfoModel;
import com.netease.yunxin.vce.sdk.net.model.GuestOnlineRequestModel;
import com.netease.yunxin.vce.sdk.net.model.QueueInfo;
import com.netease.yunxin.vce.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class CallActivity extends BaseActivity {

    private static final String TAG = "CallActivity";

    //计时器
    private Timer timer;

    private TimerTask timerTask;

    //查询队列
    private Timer queryQueueInfoTimer;

    private TimerTask queryQueueInfoTimerTask;

    private int wholeSecond;

    private TextView timerTV;

    //从呼叫开始及时，如果超过60s，重新置顶排队
    private static final int CALL_TIME_OUT = 60 * 1000;

    private CountDownTimer countDownTimer;

    private TextView queueInfoTV;

    private TextView callInfo;

    private com.netease.vce.demo.SelfInfo selfInfo;

    private String specialCode;

    //出队成功（配对成功后，不需要再查询队列信息）
    private boolean isOfflineSuccess;

    //入队成功（排队成功后，再查询排队信息）
    private boolean isOnlineSuccess;

    //监听im状态，im登录比较慢，要等im登录成功后才能排队，要不获取不到3000等透传
    private IMLoginStatusListener imIMLoginStatusListener;

    private final NetworkManager.NetChangeObserver netObserver = new NetworkManager.NetChangeObserver() {

        @Override
        public void onConnected(NetworkManager.NetType type) {
            startQueryQueueInfoTimer(true);
        }
        @Override
        public void onDisConnected() {
            queryQueueInfoTimer.cancel();
        }
    };

    private NEGuestListener guestListener = new NEGuestListener() {

        @Override
        public void onInviteGuestJoinRoom(String roomId) {
            super.onInviteGuestJoinRoom(roomId);
            CallActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 应用运行时，保持不锁屏、全屏化
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_call);
        initIntent();
        initViews();
        initData();
        if (PushManager.getInstance().isLogined()) {
            LogUtils.d(TAG, "im isLogined");
            startCall();
        } else {
            LogUtils.d(TAG, "im is not Logined observeOnlineStatus");
            imIMLoginStatusListener = new IMLoginStatusListener() {

                @Override
                public void statusChange(int status) {
                    startCall();
                }
            };
            PushManager.getInstance().addOnlineStatusListener(imIMLoginStatusListener);
        }
    }

    private void startCall() {
        startCall(specialCode);
        startTimer();
        startQueryQueueInfoTimer(false);
    }

    private void initData() {
        AVChatSoundPlayer.getInstance().playConnectingMusic();
        NetworkManager.getInstance().addListener(netObserver);
        NEGuest.getInstance().addListener(guestListener);
    }

    private void initIntent() {
        this.selfInfo = getIntent().getParcelableExtra(Constant.SELF_INFO);
        this.specialCode = getIntent().getStringExtra(Constant.SPECIAL_CODE);
    }

    private void initViews() {
        timerTV = findViewById(R.id.tv_timer);
        queueInfoTV = findViewById(R.id.tv_queue_info);
        callInfo = findViewById(R.id.tv_call_info);
    }

    private void startCall(String specialCode) {
        if (selfInfo == null) {
            LogUtils.d(TAG, "startCall but selfInfo == null");
            return;
        }
        GuestOnlineRequestModel onlineRequestModel = new GuestOnlineRequestModel();
        List<String> categoryList = new ArrayList<>();
        categoryList.add(selfInfo.getCode());
        onlineRequestModel.setCategoryList(categoryList);
        GuestInfoModel guestInfoModel = new GuestInfoModel();
        guestInfoModel.setName(selfInfo.getName());
        if (selfInfo.isVIP()) {
            guestInfoModel.setVip(1);
        }
        onlineRequestModel.setVisitorInfo(guestInfoModel);
        onlineRequestModel.setSpecialCode(specialCode);
        NEGuest.getInstance().callWithUI(CallActivity.this, selfInfo.getDesc(), selfInfo.getCode(), false);
    }

    public void cancelCall(View view) {
        NEGuest.getInstance().cancelCall();
    }

    private void startTimer() {
        //防止多次点击开启计时器
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        wholeSecond = 0;
        timerTask = new TimerTask() {

            @Override
            public void run() {
                wholeSecond++;
                HandlerUtil.post2MainThread(new Runnable() {

                    @Override
                    public void run() {
                        timerTV.setText(formatTime(wholeSecond));
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    private void startQueryQueueInfoTimer(boolean checkQueueState) {
        //防止多次点击开启计时器
        if (queryQueueInfoTimer != null) {
            queryQueueInfoTimer.cancel();
            queryQueueInfoTimer = null;
        }
        if (queryQueueInfoTimerTask != null) {
            queryQueueInfoTimerTask = null;
        }
        queryQueueInfoTimerTask = new TimerTask() {

            @Override
            public void run() {
                if (!isOfflineSuccess) {
                    startQueryQueueInfo(checkQueueState);
                }
            }
        };
        queryQueueInfoTimer = new Timer();
        queryQueueInfoTimer.schedule(queryQueueInfoTimerTask, 0, 1000);
    }

    private String formatTime(long seconds) {
        return String.format(" %02d:%02d", seconds / 60, seconds % 60);
    }

    private void startQueryQueueInfo(boolean checkQueueState) {
        NEGuest.getInstance().queryWaitingInfo(new NEVCECallback<QueueInfo>() {

            @Override
            public void onResult(int resultCode, String resultMsg, QueueInfo resultData) {
                if (resultCode == NEVCEError.ERROR_CODE_SUCCESS) {
                    LogUtils.d(TAG, "guestQueryQueueInfo onSuccess");
                    int position = resultData == null ? 0 : resultData.getPosition();
                    if (position < 1) {
                        position = 1;
                    }
                    String positionStr = String.valueOf(position);
                    queueInfoTV.setText(String.format(getString(R.string.your_queue_up_position), positionStr));
                    if (!resultData.isInQueue()) {
                        //不在队里，就返回到首页
                        if (checkQueueState) {
                            CallActivity.this.finish();
                        }
                    }
                } else {
                    LogUtils.d(TAG, "queryWaitingInfo onFailed code = " + resultCode + ", msg = " + resultMsg);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelCall(null);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }

        NEGuest.getInstance().removeListener(guestListener);
        if (queryQueueInfoTimer != null) {
            queryQueueInfoTimer.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        AVChatSoundPlayer.getInstance().stop();
        NetworkManager.getInstance().removeListener(netObserver);
        if (imIMLoginStatusListener != null) {
            PushManager.getInstance().removeOnlineStatusListener(imIMLoginStatusListener);
        }
        super.onDestroy();
    }
}
