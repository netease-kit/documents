# 客户端实现方案
## 功能实现流程
### 多人通话基础流程图
![云信多人通话流程图](../image/云信多人通话流程图.png)
### 音视频SDK接入
1、在项目对应的feature module引入NERTC SDK
```
 implementation 'com.netease.yunxin:nertc:4.0.1'
 implementation 'com.netease.yunxin:report:2.0.3'
```
2、打开 app/src/main/AndroidManifest.xml 文件，添加必要的设备权限
```
 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
 <uses-permission android:name="android.permission.WAKE_LOCK"/>
 <uses-permission android:name="android.permission.CAMERA"/>
 <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
 <uses-permission android:name="android.permission.BLUETOOTH"/>
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.BROADCAST_STICKY"/>
 <uses-permission android:name="android.permission.READ_PHONE_STATE"/>

 <!-- Android Q(10.0) 及后续版本还需要额外添加以下权限 -->
 <uses-permission android:name="android.permission.READ_PRIVILEGED_PHONE_STATE" />

 <uses-feature android:name="android.hardware.camera"/>
 <uses-feature android:name="android.hardware.camera.autofocus"/>
```
3、在 proguard-rules.pro 文件中，为 NERTC SDK 添加 -keep 类的配置，可以防止混淆 NERTC SDK 公共类名称。
```
-keep class com.netease.lava.** {*;}
-keep class com.netease.yunxin.** {*;}
```
4、初始化SDK
```
        if (neRtcEx != null) {
            neRtcEx.release();
        } else {
            neRtcEx = NERtcEx.getInstance();
        }
        NERtcParameters parameters = new NERtcParameters();
        neRtcEx.setParameters(parameters); //先设置参数，后初始化
        NERtcOption option = new NERtcOption();
        if(BuildConfig.DEBUG){
            option.logLevel = NERtcConstants.LogLevel.INFO;
        }else {
            option.logLevel = NERtcConstants.LogLevel.WARNING;
        }
        try {
            neRtcEx.init(getApplicationContext(), appKey, neRtcCallback, option);
            initRtcParams();
        } catch (Exception e) {
            NERtcEx.getInstance().release();
            try {
                neRtcEx.init(getApplicationContext(), appKey, neRtcCallback, option);
                initRtcParams();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
```
5、加入rtc房间
```
  /**
     * @param token 安全认证签名（NERTC Token）。可设置为：null。非安全模式下可设置为 null。安全性不高，建议在产品正式上线前联系对应商务经理转为安全模式。已获取的NERTC Token。安全模式下必须设置为获取到的 Token 。若未传入正确的 Token 将无法进入房间。推荐使用安全模式。
     * @param channelName 房间名称，设置相同房间名称的用户会进入同一个通话房间。
     * @param uid 用户的唯一标识 id，房间内每个用户的 uid 必须是唯一的。不可设置为 0 或负数。
     */
NERtcEx.getInstance().joinChannel(token, channelName, uid);
```
6、离开rtc房间
```
NERtcEx.getInstance().leaveChannel();
```  

### 核心API参考
- 房间管理

API | 功能描述
---|---
[getInstance](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcEx.html#getInstance--) | 获取NERtc实例
[init](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#init-android.content.Context-java.lang.String-com.netease.lava.nertc.sdk.NERtcCallback-com.netease.lava.nertc.sdk.NERtcOption-) | 创建NERtc实例
[release](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#release--) | 销毁NERtc实例，释放资源
[joinChannel](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#joinChannel-java.lang.String-java.lang.String-long-) | 加入rtc房间
[leaveChannel](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#leaveChannel--) | 离开rtc房间
[setParameters](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#setParameters-com.netease.lava.nertc.sdk.NERtcParameters-) | 复杂参数设置接口。如果需要设置相关参数，请在调用 init 接口初始化之前调用此接口


- 房间事件

API | 事件描述
---|---
[onJoinChannel](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcCallback.html#onJoinChannel-int-long-long-) | 加入房间回调
[onLeaveChannel](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcCallback.html#onLeaveChannel-int-) | 退出房间回调
[onUserJoined](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcCallback.html#onUserJoined-long-) | 远端用户加入当前房间回调
[onUserLeave](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcCallback.html#onUserLeave-long-int-) | 远端用户离开当前房间回调
[onDisconnect](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcCallback.html#onDisconnect-int-) | 从房间断开的回调

- 音频管理

API | 功能描述
---|---
[setAudioProfile](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#setAudioProfile-int-int-) | 设置音频编码配置
[enableLocalAudio](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#enableLocalAudio-boolean-) | 开关本地音频采集
[subscribeRemoteAudioStream](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#subscribeRemoteAudioStream-long-boolean-) | 订阅／取消订阅指定音频流。

- 视频管理
  
API | 功能描述
---|---
[enableLocalVideo](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#enableLocalVideo-boolean-) | 开关本地视频
[setLocalVideoConfig](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#setLocalVideoConfig-com.netease.lava.nertc.sdk.video.NERtcVideoConfig-) | 设置视频发送配置
[setupLocalVideoCanvas](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#setupLocalVideoCanvas-com.netease.lava.api.IVideoRender-) | 设置本地用户视图
[setupRemoteVideoCanvas](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#setupRemoteVideoCanvas-com.netease.lava.api.IVideoRender-long-) | 设置远端用户视图
[subscribeRemoteVideoStream](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#subscribeRemoteVideoStream-long-com.netease.lava.nertc.sdk.video.NERtcRemoteVideoStreamType-boolean-) | 订阅 / 取消订阅指定远端用户的视频流。
[setScalingType](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/video/NERtcVideoView.html#setScalingType-int-) | 设置显示模式

- 数据统计事件

API | 事件描述
---|---
[onRtcStats](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onRtcStats-com.netease.lava.nertc.sdk.stats.NERtcStats-) | 当前通话统计回调，每2秒触发一次
[onNetworkQuality](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onNetworkQuality-com.netease.lava.nertc.sdk.stats.NERtcNetworkQualityInfo:A-) | 通话中每个用户的网络上下行质量报告回调
[onLocalAudioStats](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onLocalAudioStats-com.netease.lava.nertc.sdk.stats.NERtcAudioSendStats-) | 本地音频流统计信息回调
[onLocalVideoStats](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onLocalVideoStats-com.netease.lava.nertc.sdk.stats.NERtcVideoSendStats-) | 本地视频流统计信息回调
[onRemoteAudioStats](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onRemoteAudioStats-com.netease.lava.nertc.sdk.stats.NERtcAudioRecvStats:A-) | 通话中远端音频流的统计信息回调
[onRemoteVideoStats](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/stats/NERtcStatsObserver.html#onRemoteVideoStats-com.netease.lava.nertc.sdk.stats.NERtcVideoRecvStats:A-) | 通话中远端视频流的统计信息回调
[setStatsObserver](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtcEx.html#setStatsObserver-com.netease.lava.nertc.sdk.stats.NERtcStatsObserver-) | 设置统计信息回调