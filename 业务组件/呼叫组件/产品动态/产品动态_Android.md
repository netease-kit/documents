# 产品动态

## 版本迭代

| 版本号 | 适配 IM 版本 | 适配 RTC版本 | 更新内容                                                     | 发布日期   |
| :----- | :----------- | :----------- | :----------------------------------------------------------- | :--------- |
| 1.6.1  | 9.2.5        | 4.6.12       | 1. 支持通话类型切换确认；<br />2. TokenService#getToken 接口增加 channelName 字段； | 2022-08-22 |
| 1.5.7  | 9.2.5        | 4.6.12       | 1. 升级 IM sdk 至9.2.5；<br />2. 升级 Rtc sdk 至 4.6.12；<br />3. 支持自定义 rtcUid，channelName；<br />4. 支持呼叫或接听时直接设置 rtcToken；<br />5. 去除三方库 blankj 依赖； | 2022-07-19 |
| 1.5.5  | 8.5.5        | 4.2.142      | 1. 升级 nertc 至 4.2.142；<br />2. 1v1 呼叫时可添加全局服务端抄送参数；<br />3. 被叫挂断时可通过占线方式挂断； | 2022-06-07 |
| 1.5.4  | 8.5.5        | 4.2.140      | 1. 升级 alog 至 1.0.7；<br />2. 升级 nertc 至 4.2.140；<br />3. 适配 Android 系统高版本通知缺少 FLAG_IMMUTABLE 崩溃； | 2022-05-19 |
| 1.5.3  | 8.5.5        | 4.2.124      | 1. 调整拒接流程，在拒接后实现关闭信令房间；<br /><br />2. 升级 RTC sdk 版本至4.2.124；<br />3. 优化及bug 修复； | 2022-04-26 |
| 1.5.0  | 8.5.5        | 4.2.120      | 1. 升级 RTC sdk 版本至 4.2.120；<br />2. 优化及bug 修复；    | 2021-12-23 |
| 1.4.2  | 8.5.5        | 4.2.115      | 1. 升级 RTC sdk 版本至 4.2.115；<br />2. 优化及bug 修复；<br />3. `supportAutoJoinWhenCalled` 接口参数默认为 false； | 2021-11-25 |
| 1.4.0  | 8.5.5        | 4.2.105      | 1. 支持呼叫通话前支持切换通话类型；<br />2. 添加音频转视频功能；<br />3. 增加通话中根据 accId获取 rtcUid 或根据rtcUid 获取 accId；<br />4. 修改组件展示demo，从即时通讯demo 更换为 1对1demo； | 2021-09-28 |
| 1.3.3  | 8.5.5        | 4.0.9        | 1. 支持自定义呼叫推送配置；<br />2. 支持UI组件，用户可通过引入呼叫UI组件实现接入；<br />3. 支持设置失败话单发送开关;<br />4. 支持自定义 CallService 允许用户自己实现呼叫⻚面启动；<br />5. 支持 CallExtension 用于 rtc 相关扩展(如修改 rtc 初始化流程)；<br />6. RTC版本升级为4.0.9，IM 升级为 8.5.5。 | 2021-08-26 |
| 1.3.1  | 8.4.6        | 4.0.7        | 1. 修复外部设置视频参数无效bug；                             | 2021-07-16 |
| 1.3.0  | 8.4.6        | 4.0.7        | 1. 添加 channelName，channelId，uid，accid 映射关系回调 onJoinChannel；<br />2. 移动端 添加 muteLocalVideo 接口以及 onVideoMute 回调；<br />3. 移动端 添加 onAudioMute 回调；<br />4. 添加点对点通话类型埋点事件上报；<br />5. call 接口支持自定义扩展参数，被叫方在 onInvited 回调中解析对应参数。 | 2021-06-24 |
| 1.2.1  | 8.4.6        | 4.0.7        | 1. 修复服务端删除房间/踢出某个成员后，通话没有结束；<br />2. 频繁挂断通话场景下低概率导致上一通通话影响下一通通话；<br />3. 点对点通话时一方加入 rtc 失败时需通知对端通话结束；<br />4. RTC版本升级为4.0.7，IM 升级为 8.4.6。 | 2021-06-08 |
| 1.2.0  | 8.3.1        | 4.0.3        | 1. 1v1 呼叫添加本地预览；<br />2. 在收到首帧回调时才会刷新UI；<br />3. 修复若干 bug。 | 2021-05-14 |
| 1.1.0  | 8.3.1        | 4.0.3        | 1. RTC SDK升级4.0.3，IM升级8.3.1；<br />2. 修复呼叫状态下，主叫突然断网，被叫方接受，主叫方网络恢复后没有收到对方“接受”的问题；<br />3. 修复IM被踢后音视频房间未退出问题；<br />4. 首屏接通流程优化。 | 2021-04-25 |



---

## Changelog

## 1.6.1（2022-08-22）

### 功能更新

1. 支持通话类型切换确认；
2. TokenService#getToken 接口增加 channelName 字段；

### API 变更

1. 初始化配置添加如下接口：

   ```java
   // 初始化时传入自定义 uid 参数
   CallKitUIOptions options = new CallKitUIOptions.Builder()
     .video2AudioConfirm(true) // true，视频转音频需要确认；false 视频转音频不需要确认；
     .audio2VideoConfirm(true) // true，音频转视频需要确认；false 音频转视频不需要确认；
     ......
     .build();
   // 初始化
   CallKitUI.init(getApplicationContext(), options);
   ```

2. NERtcVideoCall#switchCallType 接口增加 state 参数

   ```java
   /**
   	* 视频通话中转为音频通话。(目前仅支持视频通话转音频通话)
   	* <p>
   	* 被操作方会收到 {@link NERTCCallingDelegate#onCallTypeChange(ChannelType,int)} 的回调。
   	*
   	* @param type     通话类型。可设置为 {@link ChannelType#AUDIO} 表示语音通话。
   	* @param state    切换确认状态{@link SwitchCallState}
   	* @param callback callback 回调。
   	*/
       public abstract void switchCallType(ChannelType type, int state, RequestCallback<Void> callback);
   ```

3. TokenService#getToken 接口增加 channelName 字段；

   ```java
   /**
     * 获取 Token 的服务。
     *
     * @param uid         用户 rtcID（用于加入 rtc 房间）。
     * @param channelName 加入 rtc 房间的名称
     * @param callback    callback 回调。
     */
   void getToken(long uid, String channelName, RequestCallback<String> callback);
   ```

   

## 1.5.7（2022-07-19）

### 功能更新

**注意，若自定义 rtcUid 或 channelName则不再支持通话时长话单发送，需要参考自定义话单实现。**

1. 升级 IM sdk 至 9.2.5；

2. 升级 NERtc sdk 至 4.6.12；

3.  支持自定义 rtcUid，channelName；

4.  支持呼叫或接听时直接设置 rtcToken；

5. 去除三方 blankj 依赖库；

### API变更

1. 支持自定义加入音视频房间的 uid；

   ```java
   // 初始化时传入自定义 uid 参数
   CallKitUIOptions options = new CallKitUIOptions.Builder()
   	.currentUserRtcUId(123L) // 自定义rtc uid 参数
     .currentUserAccId("accId") // 用户云信 IM sdk 登录的 accId
     ......
     .build();
   // 初始化
   CallKitUI.init(getApplicationContext(), options);
   ```

2. 支持呼叫时自定义音视频房间名称；

   ```java
   CallParam param = new CallParam.Builder()
     .rtcChannelName("channelName")// 自定义呼叫的音视频房间名称
   	......
     .build();
   CallKitUI.startSingleCall(context, param);// 发起呼叫
   ```

   ```java
   // 自定义 ui 用户
   SingleCallParam param = new SingleCallParam(
      p2pCalledAccId, // 被叫用户 accId
      ChannelType.retrieveType(channelType), // 呼叫类型
      extJsonStr, // 主叫透传给被叫的透传参数，可无
      globalExtraCopy,// 全局抄送只用于服务端解析，可无
      rtcChannelName,// 自定义的音视频房间名称
      rtcToken //呼叫时用户加入音视频房间的token
    )；
   NERTCVideoCall.sharedInstance().call(param,callback )// 发起呼叫
   ```

3. 支持提前设置用户加入音视频房间 token；

   ```java
   // 对于主叫参考上述 2.支持呼叫时自定义音视频房间名称
   // 对于被叫用户：accept 接口增加 token 字段
   NERTCVideoCall.sharedInstance().accept(param,  // 呼叫参数
                                          token,  // 自定义 token 可无
                                          callback) // 结果回调
   ```


## 1.5.5（2022-06-07）

### 功能更新

1. 升级 nertc 至 4.2.142；
2. 1v1 呼叫时可添加全局服务端抄送参数；
3. 被叫挂断时可通过占线方式挂断；

### API变更

#### 新增API

`NERTCVideoCall#call(String userId, String selfUserId, ChannelType type, String extraInfo, String globalExtraCopy, JoinChannelCallBack joinChannelCallBack);` 增加 globalExtraCopy 参数，用于呼叫时设置全局的抄送信息（服务端使用）；

`NERTCVideoCall#hangup(String channelId, int reason, RequestCallback<Void> callback);` 通过 reason 参数控制挂断原因，例如被叫用户可通过`NERTCVideoCall#hangup(null,TerminalCode#TERMINAL_CODE_BUSY，null)` 实现占线挂断；

## 1.5.4（2022-05-19）

### 功能更新

1. 升级 alog 至 1.0.7；
2. 升级 RTC sdk 至4.2.140；
3. 适配 Android 系统高版本通知缺少 FLAG_IMMUTABLE 崩溃；

## 1.5.3（2022-04-26）

### 功能更新

1. 调整拒接流程，在拒接后实现关闭信令房间，用户可在服务端收到对应关闭房间抄送；
2. 升级 RTC sdk 至4.2.124；
3. 优化及修复bug；

## 1.5.0（2021-12-23）

### 功能更新

1. 升级 RTC sdk 至4.2.120；
2. 优化及修复bug；

## 1.4.2（2021-11-25）

### 功能更新

1. 升级 RTC sdk 至4.2.115；
2. 优化及修复bug；
3. `supportAutoJoinWhenCalled` 接口参数默认为 false；

## 1.4.0（2021-09-28）

### 新增特性

1. 添加音频转视频功能；
2. 增加通话中根据 accId获取 rtcUid 或根据rtcUid 获取 accId；

### 功能更新

1. 支持呼叫通话前支持切换通话类型；
2. 修改组件展示demo，从即时通讯demo 更换为 1对1demo；

### API变更

#### 新增API

1. `NERTCVideoCall#getAccIdByRtcUid` 根据 RtcUid 获取对应的 AccId；
2. `NERTCVideoCall#getRtcUidByAccId` 根据 AccId 获取对应的 RtcUid；

#### 更新API

1. `NERTCVideoCall#switchCallType` 支持 `ChannelType#VIDEO` 参数 从音频切换为视频通话；
1. `CallKitNotificationConfig` 构造函数变更，新增 `channelId` 参数。

## 1.3.3（2021-08-26）

### 新增特性

1. 支持自定义呼叫推送配置；
2. 支持UI组件，用户可通过引入呼叫UI组件实现接入；
3. 支持设置失败话单发送开关；
4. 支持自定义 CallService 允许用户自己实现呼叫⻚面启动；
5. 支持 CallExtension 用于 rtc 相关扩展(如修改 rtc 初始化流程)；

### 功能更新

1. RTC版本升级为4.0.9，IM 升级为 8.5.5；

### API变更

#### 新增API

`NERTCVideoCall#setCallExtension` rtc 功能扩展

`VideoCallOptions#enableOrder` 本地话单开关


## 1.3.1（2021-07-16）

### 问题修复

1. 修复外部设置视频参数无效问题；

## 1.3.0（2021-06-24）

### 新增特性

1. 添加回调 onJoinChannel 接口方法，用于告知 channelName，channelId，uid，accid 关系映射；
2. 移动端添加 muteLocalVideo 接口以及 onVideoMute 回调；
3. 移动端 添加 onAudioMute 回调；

### 功能更新

1. call接口支持自定义扩展参数，被叫方在 onInvited 回调中解析对应参数；

### API变更

#### 新增API

`NERTCCallingDelegate#onJoinChannel` 用于本地用户映射 AccId、RtcUid、rtcChannelName、rtcChannelId

详见 [API 说明](https://dev.yunxin.163.com/docs/interface/NERTCCallkit/Latest/Android/html/)

## 1.2.1（2021-06-08）

### 功能更新

1. RTC版本升级为4.0.7，IM 升级为 8.4.6；

### 问题修复

1. 修复服务端删除房间/踢出某个成员后，通话没有结束；
2. 修复频繁挂断通话场景下低概率导致上一通通话影响下一通通话；
3. 修复一对一通话时一方加入 rtc 失败时另外一端可正常进入通话；

## 1.2.0（2021-05-14）

### 功能更新

1. 1v1 视频呼叫添加本地视频预览；
2. 在收到首帧回调时才会刷新UI；

### 问题修复

1. 修复 bug ，提供稳定性； 

## 1.1.0（2021-04-25）

### 新增特性

1. 首屏接通流程优化，提升接通速度；

### 功能更新

1. RTC SDK升级4.0.3，IM升级8.3.1；

### 问题修复

1. 修复呼叫状态下，主叫突然断网，被叫方接受，主叫方网络恢复后没有收到对方“接受”的问题；
2. 修复IM被踢后音视频房间未退出问题；



