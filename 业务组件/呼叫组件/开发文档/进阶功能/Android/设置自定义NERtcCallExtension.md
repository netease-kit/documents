## 设置自定义 NERtcCallExtension 

若在使用组件过程中需要修改 NERtc sdk 相关配置或使用，可通过如下方式添加自定义 CallExtension 实现。

```java
CallKitUIOptions options = new CallKitUIOptions.Builder()
		......
		.rtcCallExtension(new SelfNERtcCallExtension())
		......
		.build();
CallKitUI.init(getApplicationContext(), options);
```

其中 `SelfNERtcCallExtension` 为继承 `NERtcCallExtension` 类实现的实例；

```java
public class SelfNERtcCallExtension extends NERtcCallExtension {

    /**
     * @param globalInit  true 为组件初始化时则 NERtc sdk 初始化，组件释放时 NERtc sdk 释放；false 为主叫发起呼叫或被叫收到呼叫邀请时初始化 NERtc sdk ，通话结束时 NERtc sdk 释放。
     */
    public SelfNERtcCallExtension(boolean globalInit) {
        super(globalInit);
    }

    /**
     * NERtc sdk 资源释放，注册监听反注册等操作
     */
    @Override
    protected void release() {
        super.release();
    }

    /**
     * 用于返回 NERtc sdk 初始化时添加回调信息对应 NERtcCallbackEx
     */
    @Override
    public NERtcCallbackExTemp provideNERtcCallback() {
        return super.provideNERtcCallback();
    }

    /**
     * 用于返回 NERtc sdk 注册 网络/音视频相关状态监听对应 NERtcStatsObserver
     */
    @Override
    public NERtcStatsObserver provideNERtcStatsObserver() {
        return super.provideNERtcStatsObserver();
    }

    /**
     * 组件初始化调用 callExtension 传入必要参数，默认根据 globalInit 参数如果为 true 会调用 initNERtc 方法
     *
     * @param context 上下文
     * @param appKey NERtc sdk appKey
     * @param option NERtc sdk 初始化 NERtcOption
     */
    @Override
    public void onInit(Context context, String appKey, NERtcOption option) {
        super.onInit(context, appKey, option);
    }

    /**
     * 发起呼叫时调用，此时未发起真正的呼叫（call/groupCall），默认根据 globalInit 参数如果为 false 会调用 initNERtc 方法
     */
    @Override
    public void onCallOut() {
        super.onCallOut();
    }

    /**
     * 被叫收到呼叫邀请时调用，此时未触发回调 onInvited 方法，默认根据 globalInit 参数如果为 false 会调用 initNERtc 方法
     */
    @Override
    public void onReceiveCall() {
        super.onReceiveCall();
    }

    /**
     * 每次通话结束或通话出现异常通话状态重制调用，默认根据 globalInit 参数如果为 false 会调用 release 方法
     */
    @Override
    public void onResetCallState() {
        super.onResetCallState();
    }

    /**
     * 配置呼叫回调接口，组件通过 manager 完成一些 NERtc 相关回调触发，用户一般不用关心，直接使用父类成员变量 manager 即可。
     * 若用户重写了 provideNERtcCallback 或 provideNERtcStatsObserver 方法需用户手动调用 manager 对应回调。
     */
    @Override
    public CallExtension configDelegateMgr(NERTCInternalDelegateManager manager) {
        return super.configDelegateMgr(manager);
    }

    /**
     * 配置呼叫过程中 rtcUid 和 AccId 转换的映射器，用户一般不用关心，直接使用父类成员变量 provider 即可。
     */
    @Override
    public CallExtension configBasicInfoProvider(BasicInfoProvider provider) {
        return super.configBasicInfoProvider(provider);
    }

    /**
     * NERtc sdk 加入房间动作，内部使用为调用 NERtcEx.getInstance().joinChannel，详细参考 NERtc sdk 说明
     */
    @Override
    protected int joinChannel(String token, String channelName, long rtcUid) {
        return super.joinChannel(token, channelName, rtcUid);
    }

    /**
     * NERtc sdk 离开房间动作，内部使用为调用 NERtcEx.getInstance().leaveChannel，详细参考 NERtc sdk 说明
     */
    @Override
    protected int leaveChannel() {
        return super.leaveChannel();
    }

    /**
     * NERtc sdk 允许本地视频采集发送开关，内部使用为调用 NERtcEx.getInstance().enableLocalVideo，详细参考 NERtc sdk 说明
     */
    @Override
    public void enableLocalVideo(boolean enable) {
        super.enableLocalVideo(enable);
    }

    /**
     * NERtc sdk 允许本地视频发送开关，内部使用为调用 NERtcEx.getInstance().muteLocalVideoStream，详细参考 NERtc sdk 说明
     */
    @Override
    public void muteLocalVideoStream(boolean mute) {
        super.muteLocalVideoStream(mute);
    }

    /**
     * NERtc sdk 允许本地音频发送开关，内部使用为调用 NERtcEx.getInstance().muteLocalAudioStream，详细参考 NERtc sdk 说明
     */
    @Override
    public void muteLocalAudioStream(boolean mute) {
        super.muteLocalAudioStream(mute);
    }

    /**
     * NERtc sdk 切换摄像头方向，内部使用为调用 NERtcEx.getInstance().switchCamera，详细参考 NERtc sdk 说明
     */
    @Override
    public void switchCamera() {
        super.switchCamera();
    }

    /**
     * NERtc sdk 是否订阅远端音频流，内部使用为调用 NERtcEx.getInstance().adjustUserPlaybackSignalVolume，详细参考 NERtc sdk 说明
     */
    @Override
    public void subscribeRemoteAudioStream(long rtcUid, boolean subscribe) {
        super.subscribeRemoteAudioStream(rtcUid, subscribe);
    }

    /**
     * NERtc sdk 设置本地视频画布，内部使用为调用 NERtcEx.getInstance().setupLocalVideoCanvas，详细参考 NERtc sdk 说明
     */
    @Override
    public void setupLocalVideoCanvas(NERtcVideoView videoView) {
        super.setupLocalVideoCanvas(videoView);
    }

    /**
     * NERtc sdk 设置远端视频画布，内部使用为调用 NERtcEx.getInstance().setupRemoteVideoCanvas，详细参考 NERtc sdk 说明
     */
    @Override
    public void setupRemoteVideoCanvas(NERtcVideoView videoView, long rtcUid) {
        super.setupRemoteVideoCanvas(videoView, rtcUid);
    }

    /**
     * NERtc sdk 初始化，内部调用 sdk init 方法，若重写此方法可参考如下已有实现，避免 NERtcCallbackProxyMgr 或 NERtcStatsObserverProxyMgr 失效
     *
     *  ALog.d(TAG, "sdk init.");
     *  //初始化之前 destroy
     *  release();
     *  try {
     *      if (option == null) {
     *          option = new NERtcOption();
     *          option.logLevel = NERtcConstants.LogLevel.WARNING;
     *      }
     *
     *      NERtcCallbackProxyMgr.getInstance().addCallback(provideNERtcCallback());
     *      NERtcEx.getInstance().init(context, appKey, NERtcCallbackProxyMgr.getInstance().getMainInnerCallback(), option);
     *  } catch (Exception e) {
     *      ALog.e(TAG, "sdk init failed", e);
     *      notify.notifySdkInitFailed();
     *      manager.onError(CallErrorCode.ERROR_INIT_RTC_SDK, "init rtc sdk failed", true);
     *      return;
     *  }
     *
     *  NERtcStatsObserverProxyMgr.getInstance().addCallback(provideNERtcStatsObserver());
     *  NERtcEx.getInstance().setStatsObserver(NERtcStatsObserverProxyMgr.getInstance().getMainInnerCallback());
     */
    @Override
    protected void initNERtc() {
        super.initNERtc();
    }

    /**
     * 封装便于组件调用，实际直接调用 joinChannel 方法，用户一般不需修改
     */
    @Override
    public int toJoinChannel(String token, String channelName, long rtcUid) {
        return super.toJoinChannel(token, channelName, rtcUid);
    }

    /**
     * 封装便于组件调用，实际直接调用 leaveChannel 方法，用户一般不需修改
     */
    @Override
    public int toLeaveChannel() {
        return super.toLeaveChannel();
    }

    /**
     * 对应组件销毁方法 destroySharedInstance 调用，内部调用 release 方法
     */
    @Override
    public void onUnit() {
        super.onUnit();
    }
}
```



