## 呼叫组件API文档

### 简介

为了方便开发者快速接入音视频通话 2.0，提升应用的研发效率，音视频通话 2.0 和信令产品携手为您打造应用层组件 NERTCCallkit，组件中集成了音视频通话 2.0 和信令的多项基础功能，全方位提升接入效率。

呼叫组件 NERTCCallkit 依赖网易云信 NERTC SDK 和 NIM SDK（含信令），支持 1v1 和多人视频通话。


### API列表

| 方法 | 参数 | 参数说明 | 功能 | 起始版本 |
| --- | ---- | ---- | ---- | ------- |
| login | account, token | account: 本端登录账户<br>token: 本端登录token | 登录IM接口，所有功能需要先进行登录后才能使用 | 1.0.0 |
| logout |  | <img width=500 /> |  登出，登出后无法再进行拨打操作 |  1.0.0 |
| setCallTimeout | time | time: 超时时间，单位ms | 设置呼叫超时时间，在呼叫前调用 | 1.0.0 |
| call | userId, type, attachment | userId: 被邀请方用户id<br> type: 1-音频，2-视频<br>attachment: 额外信息 | 邀请通话，被邀请方会收到的回调，组合行为（创建+邀请）| 1.0.0 |
| cancel | { channelId, account, requestId, offlineEnabled, attachExt }, sendMsg | channelId: 频道id<br>account: 对方account账号<br>requestId：邀请者邀请的请求id<br>offlineEnabled:true<br>attachExt:额外信息<br> sendMsg:true(超时场景为false) | 邀请者取消呼叫 | 1.0.0 |
| leave | channelId, offlineEnabled |channelId: 频道id<br>offlineEnabled:true| 离开 | 1.0.0 |
| hangup | channelId, offlineEnabled |channelId: 频道id<br>offlineEnabled:true | 挂断，同时挂断其他人 | 1.0.0 |
| accept | Object | 被邀请者接受 | 1.0.0 |
| reject | Object | 被邀请者拒绝 | 1.0.0 |


### event事件
| 方法 | 功能说明 | 起始版本 |
| --- | ---- | ------- |
| onInvited | 被呼叫用户收到邀请 | 1.0.0 |
| onUserBusy | 被呼叫用户超时 | 1.0.0 |
| onUserCancel | 被呼叫用户取消呼叫 | 1.0.0 |
| onUserAccept | 被呼叫用户接受 | 1.0.0 |
| onUserEnter | 被呼叫用户进入房间 | 1.0.0 |
| onUserReject | 被呼叫用户拒绝 | 1.0.0 |
| onUserLeave | 被呼叫用户离开房间 | 1.0.0 |
| onUserDisconnect | 被呼叫用户断开连接 | 1.0.0 |
| onUserNetworkQuality | 用户通话质量 | 1.0.0 |
| onDisconnect |  | 1.0.0 |
| onAudioAvailable | 被呼叫用户麦克风开启 | 1.0.0 |
| onCameraAvailable | 被呼叫用户摄像头开启 | 1.0.0 |
| onCallEnd | 通话结束 | 1.0.0 |
| onCallingTimeOut | 呼叫超时 | 1.0.0 |
| onCallTypeChange | 被呼叫用户通话方式改变 | 1.0.0 |
| onOtherClientAccept | 被呼叫用户在其他端接受 | 1.0.0 |
| onOtherClientReject | 被呼叫用户在其他端拒绝 | 1.0.0 |
| onVideoMuted| 被呼叫用户摄像头开启关闭监听 | 1.0.0 |
| onAudioMuted | 被呼叫用户麦克风开启关闭监听 | 1.0.0 |
| onMessageSent | 话单信息 | 1.0.0 |
| onError | 组件内部抛出错误 | 1.0.0 |


