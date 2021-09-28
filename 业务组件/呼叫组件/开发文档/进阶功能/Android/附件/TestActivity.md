**TestActivity：**

```java
import android.os.Bundle;
import android.view.View;

import com.netease.lava.nertc.sdk.NERtcEx;
import com.netease.lava.nertc.sdk.video.NERtcVideoConfig;
import com.netease.lava.nertc.sdk.video.NERtcVideoView;
import com.netease.nim.demo.R;
import com.netease.yunxin.nertc.nertcvideocall.model.NERTCCallingDelegate;
import com.netease.yunxin.nertc.ui.base.CommonCallActivity;
import com.netease.yunxin.nertc.ui.p2p.NERtcCallDelegateForP2P;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestActivity extends CommonCallActivity {

    private boolean startPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      	// 当前页面启动是主叫还是被叫
        if (!getCallParam().isCalled()){
            // 启动呼叫
            doCall(null,null);

            findViewById(R.id.callerOperationGroup).setVisibility(View.VISIBLE);

            // 主叫取消
            View cancel = findViewById(R.id.ivCancel);
            cancel.setOnClickListener(v -> doCancel(null));

            // 设置摄像头为前置
            NERtcVideoConfig config = new NERtcVideoConfig();
            config.frontCamera = true;
            NERtcEx.getInstance().setLocalVideoConfig(config);

            // 呼叫方的本地预览
            NERtcVideoView videoViewLocalBig = findViewById(R.id.videoViewLocalBig);
            videoViewLocalBig.setVisibility(View.VISIBLE);
            getVideoCall().setupLocalView(videoViewLocalBig);
            NERtcEx.getInstance().startVideoPreview();
            startPreview = true;
        }else {
            findViewById(R.id.calledOperationGroup).setVisibility(View.VISIBLE);

            // 被叫拒绝
            View reject = findViewById(R.id.ivReject);
            reject.setOnClickListener(v -> doReject(null));

            // 被叫接受
            View accept = findViewById(R.id.ivAccept);
            accept.setOnClickListener(v -> doAccept(null));
        }

        View hangup = findViewById(R.id.ivHangUp);
        hangup.setOnClickListener(v -> {
            doHangup(null);
            finish();
        });
    }

    @Override// 资源释放
    protected void releaseAndFinish(boolean finishCall) {
        super.releaseAndFinish(finishCall);
        if (finishCall){
            doHangup(null);
        }
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_test;
    }

    @NotNull
    @Override
    protected NERTCCallingDelegate provideRtcDelegate() {
        return new NERtcCallDelegateForP2P(){

            @Override
            public void onUserEnter(@Nullable String userId) {
                findViewById(R.id.callerOperationGroup).setVisibility(View.GONE);
                findViewById(R.id.calledOperationGroup).setVisibility(View.GONE);

                findViewById(R.id.llOnTheCallOperation).setVisibility(View.VISIBLE);

                if (startPreview) {
                    NERtcEx.getInstance().stopVideoPreview();
                    startPreview = false;
                }
                // 展示远端画面
                findViewById(R.id.videoViewRemote).setVisibility(View.VISIBLE);
                getVideoCall().setupRemoteView(findViewById(R.id.videoViewRemote), userId);

                // 销毁本地预览大视频窗口
                NERtcVideoView videoViewLocalBig = findViewById(R.id.videoViewLocalBig);
                videoViewLocalBig.release();
                videoViewLocalBig.setVisibility(View.GONE);
                // 设置本地预览小视频窗口
                findViewById(R.id.videoViewLocalSmall).setVisibility(View.VISIBLE);
                getVideoCall().setupLocalView(findViewById(R.id.videoViewLocalSmall));

            }

            @Override // 主要用户取消
            public void onCancelByUserId(@Nullable String userId) {
                super.onCancelByUserId(userId);
                finish();
            }

            @Override// 被叫用户拒绝
            public void onRejectByUserId(@Nullable String userId) {
                super.onRejectByUserId(userId);
                finish();
            }

            @Override// 通过结束
            public void onCallEnd(@Nullable String userId) {
                super.onCallEnd(userId);
                finish();
            }

            @Override// 被叫用户占线
            public void onUserBusy(@Nullable String userId) {
                super.onUserBusy(userId);
                finish();
            }
        };
    }
}
```

