# 实现通话呼叫

针对已经集成 IM sdk 的用户若希望快速实现音视频通话功能，可通过集成呼叫组件完成实现。

## 注意事项
1. 若用户已经集成了 IM Sdk 或 NERTc Sdk 需参考**[产品动态](./产品动态-iOS.md)** 关于 sdk 版本的映射关系，确保使用的 sdk 版本和组件版本一致。
2. 若用户未接入 IM Sdk 可参考[官网](https://doc.yunxin.163.com/docs/TM5MzM5Njk/DQ5MTA5ODQ?platformId=60278)完成 IM Sdk 的接入。

**用户完成上述两点注意后继续参考下面内容。**


## 准备工作
1. 环境准备
   1. Xcode 10 及以上版本；
   2. iOS 9.0 及以上版本的 iOS 设备；
   3. CocoaPods；

2. 开通相关功能

    **注：如果已有相应的应用，可在原应用上申请开通【音视频通话2.0】及【信令】或 【IM 专业版】功能。**

   针对新应用可按照如下操作实现功能开通。

   网易云控制台，点击【应用】>【创建】创建自己的App，在【功能管理】中申请开通如下功能

   1. 若仅使用呼叫功能，则开通

      1. 【信令】
      2. 【音视频通话2.0】
      3. 【安全模式】- 组件支持使用安全模式以及非安全模式，开启安全模式请咨询相应SO。
   2. 若还需使用话单功能，则需要开通
      1. 【IM专业版】
      2. 【G2话单功能】-云信控制台-音视频通话2.0-功能配置-话单配置-开启话单类型消息抄送。

  3. 在控制台中【appkey管理】获取appkey。

## 集成

## 1. 集成说明

### 1.1 引入

> 建议使用CocoaPods进行管理，在`Podfile`中添加：

```ruby
pod 'NERtcCallKit'
```

> 组件依赖NERtcSDK，NIMSDK的特定版本，请***不要***在Podfile中指定NERtcSDK及NIMSDK的版本

### 1.2 初始化

组件实现为单实例，通过接口 `NERtcCallKit.sharedInstance` 获取此实例，调用实例方法 `setupAppKey` 完成初始化。

```objc
/// 初始化，所有功能需要先初始化
/// @param appKey 云信后台注册的appKey
/// @param options 其他配置项，如证书名
- (void)setupAppKey:(NSString *)appKey options:(nullable NERtcCallOptions *)options;
```

> 注：setupAppKey方法为使用组件前 ***必须*** 调用的方法，若不调用，会发生不可预知的问题!



### 1.3 登录/登出

**若已经在 app 内实现了 NIMSDK 登录/登出逻辑，则不必调用相应的登录/登出接口，直接跳过此章节。**

否则，可使用组件的`-[NERtcCallKit login:]` 进行登录，同样可使用`-[NERtcCallKit logout:]`进行登出，***登出或未进行登录则不能进行呼叫***。



### 1.4 设置通话回调

 **无论是一对一通话还是群组通话，在呼叫或收到呼叫邀请时需要设置相应的回调监听，用于接收对应通话的控制消息。**首先在需要收到监听的地方实现`NERtcCallKitDelegate`

```objc
@interface SomeViewController: UIViewController <NERtcCallKitDelegate>
@end
```

**注册回调**

```objc
// 执行设置回调监听
[NERtcCallKit.sharedInstance addDelegate:self];

// 通话结束后或页面销毁时需要移除对应的回调监听
[NERtcCallKit.sharedInstance removeDelegate:self];
```

**回调监听方法说明：**

```objc
@protocol NERtcCallKitDelegate <NSObject>

@optional

/// 收到邀请的回调
/// @param invitor 邀请方
/// @param userIDs 房间中的被邀请的所有人（不包含邀请者）
/// @param isFromGroup 是否是群组
/// @param groupID 群组ID
/// @param type 通话类型
- (void)onInvited:(NSString *)invitor
          userIDs:(NSArray<NSString *> *)userIDs
      isFromGroup:(BOOL)isFromGroup
          groupID:(nullable NSString *)groupID
             type:(NERtcCallType)type
       attachment:(nullable NSString *)attachment;

/// 接受邀请的回调
/// @param userID 接受者
- (void)onUserEnter:(NSString *)userID;

/// 拒绝邀请的回调
/// @param userID 拒绝者
- (void)onUserReject:(NSString *)userID;

/// 取消邀请的回调
/// @param userID 邀请方
- (void)onUserCancel:(NSString *)userID;

/// 用户离开的回调.
/// @param userID 用户userID
- (void)onUserLeave:(NSString *)userID;

/// 用户异常离开的回调
/// @param userID 用户userID
- (void)onUserDisconnect:(NSString *)userID;

/// 用户接受邀请的回调
/// @param userID 用户userID
- (void)onUserAccept:(NSString *)userID;

/// 忙线
/// @param userID 忙线的用户ID
- (void)onUserBusy:(NSString *)userID;

/// 通话类型切换的回调（仅1对1呼叫有效）
/// @param callType 切换后的类型
- (void)onCallTypeChange:(NERtcCallType)callType;

/// 通话结束
- (void)onCallEnd;

/// 呼叫超时
- (void)onCallingTimeOut;

/// 连接断开
/// @param reason 断开原因
- (void)onDisconnect:(NSError *)reason;

/// 发生错误
- (void)onError:(NSError *)error;

/// 启用/禁用相机
/// @param available 是否可用
/// @param userID 用户ID
- (void)onCameraAvailable:(BOOL)available userID:(NSString *)userID;

/// 启用/禁用麦克风
/// @param available 是否可用
/// @param userID 用户userID
- (void)onAudioAvailable:(BOOL)available userID:(NSString *)userID;

/// 视频采集变更回调
/// @param muted 是否关闭采集
/// @param userID 用户ID
- (void)onVideoMuted:(BOOL)muted userID:(NSString *)userID;

/// 音频采集变更回调
/// @param muted 是否关闭采集
/// @param userID 用户ID
- (void)onAudioMuted:(BOOL)muted userID:(NSString *)userID;

/// 自己加入成功的回调，通常用来上报、统计等
/// @param event 回调参数
- (void)onJoinChannel:(NERtcCallKitJoinChannelEvent *)event;

/// 首帧解码成功的回调
/// @param userID 用户id
/// @param width 宽度
/// @param height 高度
- (void)onFirstVideoFrameDecoded:(NSString *)userID width:(uint32_t)width height:(uint32_t)height;

/// 网络状态监测回调
/// @param stats key为用户ID, value为对应网络状态
- (void)onUserNetworkQuality:(NSDictionary<NSString *, NERtcNetworkQualityStats *> *)stats;

/// 呼叫请求已被其他端接收的回调
- (void)onOtherClientAccept;

/// 呼叫请求已被其他端拒绝的回调
- (void)onOtherClientReject;

@end
```


### 1.5 设置TokenHandler

若 NERtc sdk 采用安全模式则加入音视频房间时需要提供对应的token，详细参考[Token获取](https://doc.yunxin.163.com/docs/jcyOTA0ODM/DE0NjAwNDY?platformId=50192) 。

呼叫组件依赖 token，需要在用户在初始化时同时设置 token 服务，此 token 服务为用户服务端自己实现。若 NERtc sdk 采用非安全模式，则服务返回结果为 null，但是必须设置 Token Handler

```objc
    // 安全模式需要计算token，如果tokenHandler为nil表示非安全模式，需要联系经销商开通
    NERtcCallKit.sharedInstance.tokenHandler = ^(uint64_t uid, void (^complete)(NSString *token, NSError *error)) {
        // 在这里可以异步获取token，获取完成后调用complete(<tokenOrNil>, <errorOrNil>)
    };
```
