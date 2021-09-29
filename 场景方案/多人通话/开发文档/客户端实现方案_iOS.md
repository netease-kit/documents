# 客户端实现方案
## 功能实现流程
### 多人通话基础流程图
![云信多人通话流程图](../image/云信多人通话流程图.png)
### 音视频SDK接入
1、在项目Podfile中引入NERTC SDK
```
pod 'NERtcSDK', '4.0.3'
pod 'NIMSDK_LITE', '7.8.4'
```
2、在Info.plist文件中添加相机、麦克风访问权限：
```
Privacy - Camera Usage Description
Privacy - Microphone Usage Description
```

4、初始化SDK
```
- (void)initRTCSDK {
    NERtcEngine *coreEngine = [NERtcEngine sharedEngine];
    
    [coreEngine addEngineMediaStatsObserver:self];
    
    NERtcEngineContext *context = [[NERtcEngineContext alloc] init];
    context.engineDelegate = self;
    context.appKey = self.task.nrtcAppKey;
    int res = [coreEngine setupEngineWithContext:context];
    YXAlogInfo(@"初始化音视频引擎结果, res: %d", res);
    
}
```
5、加入rtc房间
```
/**
 加入频道

 @param token 频道 token
 @param channelName 频道名
 @param uId 自己的 Id
 @param completion 操作完成的 block 回调
 @return 操作返回值，被执行了则返回 0
 */
- (int)joinChannelWithToken:(NSString *)token
                channelName:(NSString *)channelName
                      myUid:(uint64_t)uId
                 completion:(NERtcJoinChannelCompletion)completion;
```
6、离开rtc房间
```
[[NERtcEngine sharedEngine] leaveChannel];
```

### 音视频通话API参考
[iOS SDK  API](https://dev.yunxin.163.com/docs/interface/NERTC_SDK/Latest/iOS/html/)