## iOS API接口
本页提供 多人通话 for iOS 的  API 参考,更详细的NERTC SDK文档说明请移步到[这里](https://dev.yunxin.163.com/docs/product/互动直播2.0/客户端API/iOS)。
### 多人通话工程核心API概览
- 频道管理

API | 功能描述
---|---
[setupEngineWithContext](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/setupEngineWithContext:) | 初始化设置 NERtcEngine
[joinChannelWithToken](https://dev.yunxin.163.com/docs/interface/音视频2.0Android端/com/netease/lava/nertc/sdk/NERtc.html#joinChannel-java.lang.String-java.lang.String-long-) | 加入频道
[leaveChannel](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/leaveChannel) | 离开rtc房间
[setParameters](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/setParameters:) | 复杂参数设置接口


- 频道事件

API | 事件描述
---|---
[onNERtcEngineDidLeaveChannelWithResult](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineDelegate.html#//api/name/onNERtcEngineDidLeaveChannelWithResult:) | 离开频道回调
[onNERtcEngineUserDidJoinWithUserID](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineDelegate.html#//api/name/onNERtcEngineUserDidJoinWithUserID:userName:) | 远端用户加入当前频道回调
[onNERtcEngineUserDidLeaveWithUserID](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineDelegate.html#//api/name/onNERtcEngineUserDidLeaveWithUserID:reason:) | 远端用户离开当前房间回调
[onNERtcEngineDidDisconnectWithReason](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineDelegate.html#//api/name/onNERtcEngineDidDisconnectWithReason:) | 从频道断开的回调


- 音频管理

API | 功能描述
---|---
[setAudioProfile](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/setAudioProfile:scenario:) | 设置音频编码配置
[enableLocalAudio](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/enableLocalAudio:) | 开关本地音频采集


- 视频管理

API | 功能描述
---|---
[enableLocalVideo](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/enableLocalVideo:) | 开关本地视频
[setLocalVideoConfig](https://dev.yunxin.163.com/docs/interface/%E9%9F%B3%E8%A7%86%E9%A2%912.0iOS%E7%AB%AF/Protocols/INERtcEngine.html#//api/name/setLocalVideoConfig:) | 设置视频发送配置
[setupLocalVideoCanvas](https://dev.yunxin.163.com/docs/interface/%E9%9F%B3%E8%A7%86%E9%A2%912.0iOS%E7%AB%AF/Protocols/INERtcEngine.html#//api/name/setupLocalVideoCanvas:) | 设置本地用户视图
[setupRemoteVideoCanvas](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngine.html#//api/name/setupRemoteVideoCanvas:forUserID:) | 设置远端用户视图
[subscribeRemoteVideo](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngineEx.html#//api/name/subscribeRemoteVideo:forUserID:streamType:) | 订阅 / 取消订阅指定远端用户的视频流。

- 数据统计事件

API | 事件描述
---|---
[onRtcStats](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineMediaStatsObserver.html#//api/name/onRtcStats:) | 当前通话统计回调，每2秒触发一次
[onNetworkQuality](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/NERtcEngineMediaStatsObserver.html#//api/name/onNetworkQuality:) | 通话中每个用户的网络上下行质量报告回调
[addEngineMediaStatsObserver](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngineEx.html#//api/name/addEngineMediaStatsObserver:) | 添加 media 统计信息观测器
[removeEngineMediaStatsObserver](https://dev.yunxin.163.com/docs/interface/音视频2.0iOS端/Protocols/INERtcEngineEx.html#//api/name/removeEngineMediaStatsObserver:) | 删除 media 统计信息观测器
