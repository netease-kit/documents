
### 1.6.5 => 1.8.0

不可直接升级
1. 组件内部不再应用Rtc，需要用户外部依赖，需要用户在应用呼叫组件的同时自己引用Rtc，或者使用引用过Rtc的其他组件
```objc
#呼叫组件
pod 'NERtcCallKit','1.8.0'
#NERtcSDK
pod 'NERtcSDK', '4.6.22', :subspecs => ['RtcBasic']
```
2. 用户不需要实现 NERtcCallKitTokenHandler 回调获取Rtc Token，已经在组件内部处理相关获取流程

3. 移除 NERtcCallOptions 中配置属性 shouldInitializeRtc

4. 新增 NERtcCallOptions 中 globalInit 配置属性
```objc
/// 是否在初始化的时候初始化Rtc，默认为YES，设置为NO，主叫在呼叫时初始化，被叫在有呼叫到达的时候初始化
@property(nonatomic, assign) BOOL globalInit;
```

5. 新增 NERtcCallOptions joinRtcWhenCall 配置属性
```objc
/// 主叫是否在呼叫时加入rtc，默认为NO，不提前加入，在被叫接听时加入，如设置提前加入房间会带来通话费用的增加，同时提升首帧开画时间
@property(nonatomic, assign) BOOL joinRtcWhenCall;
```

### 1.6.1 => 1.6.5

可直接升级

1. 修复弱网下音视频切换控制失败未回调问题

### 1.5.7 => 1.6.1

不可直接升级，如果用到以下变更接口需要做出适当调整

1. 新增切换音视频需要切换配置接口，默认不开启确认
    ```objc
    /// 音视频切换是否需要确认配置
    /// @param video 切换到视频是否需要确认
    /// @param audio 切换到音频是否需要确认
    - (void)enableSwitchCallTypeConfirmVideo:(BOOL)video audio:(BOOL)audio;
    ```
2. 修改音视频切换接口参数，如果未开启音视频切换确认可以忽略第二个参数
    ```objc
    /// 在通话过程中切换通话类型。非通话过程中调用无效。仅支持1对1通话。
    /// @param type 通话类型: 音频/视频
    /// @param state 切换应答类型:  邀请/同意/拒绝
    /// @param completion 回调
    /// @discussion
    /// 切换完成后，组件内部会将己端和对端调用-enableLocalVideo:，此时外部不建议再调用-enableLocalVideo:，防止状态错乱.
    - (void)switchCallType:(NERtcCallType)type
                 withState:(NERtcSwitchState)state
                completion:(nullable void (^)(NSError *_Nullable error))completion;
    ```
3. 新增音视频切换回调，新增回调中有音视频切换指令类型，如果未开启音视频切换确认配置，可忽略第二个字段
    ```objc
    /// 通话类型切换的回调（仅1对1呼叫有效）
    /// @param callType 切换后的类型
    /// @param state 切换应答类型:  邀请/同意/拒绝
    - (void)onCallTypeChange:(NERtcCallType)callType withState:(NERtcSwitchState)state;
    ```
4. 获取token NERtcCallKitTokenHandler 回调增加channelName参数
    ```objc
    // 定义示例
    typedef void (^NERtcCallKitTokenHandler)(uint64_t uid, NSString *channelName,
                                         void (^complete)(NSString *token, NSError *error));
    // 使用示例
    callkit.tokenHandler = ^(uint64_t uid, NSString *channelName, void (^complete)(NSString *token, NSError *error)){
    };                                    
    ```
### 1.5.5 => 1.5.7

可直接升级

1. 支持自定义 rtcUid
    ```objc
    /// 初始化，所有功能需要先初始化
    /// @param appKey 云信后台注册的appKey
    /// @param rtcUid  用户自定义rtc uid，如果不需要自定义传入 <= 0 任意整数(或者使用-
    /// (void)setupAppKey:(NSString *)appKey options:(nullable NERtcCallOptions *)options
    /// 初始化接口)，内部自动生成，如果初始化传入会优先使用外部传入
    - (void)setupAppKey:(NSString *)appKey
             withRtcUid:(uint64_t)rtcUid
                options:(nullable NERtcCallOptions *)options;
    ```

2. 支持自定义 channelName 以及呼叫时传入rtc token
    ```objc
    /// 开始呼叫
    /// @param userID 呼叫的用户ID
    /// @param type 通话类型
    /// @param attachment 附件信息，透传到onInvited
    /// @param extra 全局抄送
    /// @param token  安全模式token，如果用户直接传入，不需要实现  tokenHandler  block回调
    /// @param channelName  自定义channelName，不传会默认生成
    /// @param completion 回调
    - (void)call:(NSString *)userID
            type:(NERtcCallType)type
      attachment:(nullable NSString *)attachment
     globalExtra:(nullable NSString *)extra
       withToken:(nullable NSString *)token
     channelName:(nullable NSString *)channelName
      completion:(nullable void (^)(NSError *_Nullable error))completion;            
    ```

### 1.5.0 => 1.5.5

可直接升级

1. 1 对 1 呼叫时，支持全局服务端抄送参数
    ```objc
    /// 开始呼叫
    /// @param userID 呼叫的用户ID
    /// @param type 通话类型
    /// @param attachment 附件信息，透传到onInvited
    /// @param extra 全局抄送
    /// @param token 安全模式token，如果用户直接传入，不需要实现  tokenHandler  block回调
    /// @param completion 回调
    - (void)call:(NSString *)userID
           type:(NERtcCallType)type
     attachment:(nullable NSString *)attachment
    globalExtra:(nullable NSString *)extra
     completion:(nullable void (^)(NSError *_Nullable error))completion;
    ```
2. 支持占线挂断
    ```objc
    /// 拒绝呼叫
    /// @param completion 回调
    /// @param reason  挂断原因
    - (void)rejectWithReason:(NSInteger)reason
       withCompletion:(nullable void (^)(NSError *_Nullable error))completion;
    ```
3. 升级 nertc 至 4.2.142

### 1.5.0 => 1.5.1

可直接升级

1. 扩展Rtc回调转发
2. 优化多人呼叫静音逻辑

### 1.4.2 => 1.5.0

可直接升级

1. 修复与 Swift 混编的某些问题

### 1.4.0 => 1.4.2

可直接升级

1. 支持 IM SDK 和 NERtc SDK 向上兼容

2. 支持私有化配置

   ```objc
   /// 初始化，所有功能需要先初始化，支持私有化
   /// @param appKey 云信后台注册的appKey
   /// @param options  IM 推送配置，Rtc是否初始化
   /// @param context  Rtc初始化配置，私有化参数配置
   - (void)setupAppKey:(NSString *)appKey
           options:(nullable NERtcCallOptions *)options
       withContext:(nonnull NERtcEngineContext *)context;

   ```

3. 支持呼叫接通前切换音视频，默认不支持通话接通前切换，如需支持设置被叫自动加入 channel 为 YES，会影响多端登录的 case

   ```objc
   @interface NERtcCallOptions : NSObject

   /// 被叫是否自动加入channel
   @property (nonatomic, assign) BOOL supportAutoJoinWhenCalled;

   @end
   ```

### 1.3.0 => 1.4.0

可直接升级

1. 添加被叫是否自动加入 channel 控制变量:

   ```objc
   /// 初始化，所有功能需要先初始化，支持私有化
   /// @param appKey 云信后台注册的appKey
   /// @param options  IM 推送配置，Rtc是否初始化
   /// @param context  Rtc初始化配置，私有化参数配置
   - (void)setupAppKey:(NSString *)appKey
               options:(nullable NERtcCallOptions *)options
           withContext:(nonnull NERtcEngineContext *)context;
   ```

2. 增加通话中根据 accId 获取 rtcUid 或根据 rtcUid 获取 accId:

   ```objc
   @interface NERtcCallKit : NSObject

   /// 根据uid 获取 accid
   /// @param uid 用户id
   /// @discussion 只有在通话中才能通过uid获取accid
   - (void)memberOfUid:(uint64_t)uid
         completion:(nullable void(^)(NIMSignalingMemberInfo * _Nullable info))completion;

   /// 根据uid 获取 accid
   /// @param accid  IM 用户id
   /// @discussion 只有在通话中才能通过accid获取获取uid
   - (void)memberOfAccid:(NSString *)accid
             completion:(nullable void(^)(NIMSignalingMemberInfo * _Nullable info))completion;

   @end
   ```

3. 支持自定义呼叫推送配置:

   ```objc
   @interface NERtcCallKit : NSObject

   /// 推送配置定制化，修改config.pushTitle，config.pushContent来完成。需要的上下文内容在context对象中提供。
   @property (nonatomic, copy, nullable) NERtcCallKitPushConfigHandler pushConfigHandler;

   @end
   ```

4. 支持用户通过呼叫组件的代理接收 NERtcSDK 回调:

   ```objc
   @interface NERtcCallKit : NSObject

   /**
     NERtcEngine 的回调接口，由用户提供
   */
   @property (nonatomic, weak) id<NERtcEngineDelegateEx> engineDelegate;

   @end
   ```

### 1.2.1 => 1.3.0

1. 添加视频采集控制接口：

   ```objc
   @interface NERtcCallKit : NSObject

   /// 开启或关闭视频采集
   /// @param muted YES：关闭，NO：开启
   /// @return 操作返回值，成功则返回 0
   - (int)muteLocalVideo:(BOOL)muted;

   @end
   ```

   接口调用会触发 `NERTCCallingDelegate.onVideoMuted()` 回调；

2. 添加音频采集控制回调接口

   ```objc
   @protocol NERtcCallKitDelegate <NSObject>

      /// 音频采集变更回调
      /// @param muted 是否关闭采集
      /// @param userID 用户ID
      - (void)onAudioMuted:(BOOL)muted userID:(NSString *)userID;

      @end
   ```

3. 添加 `onJoinChannel` 回调，此接口当用户加入音视频房间时触发此回调，用户可通过此回调获取用户 IM 的账号 Id 在此次通话中的 uid 以及音视频房间的房间 Id 以及名称。

   ```objc
   @protocol NERtcCallKitDelegate <NSObject>

   /// 自己加入成功的回调，通常用来上报、统计等
   /// @param event 回调参数
   - (void)onJoinChannel:(NERtcCallKitJoinChannelEvent *)event;

   @end

   @interface NERtcCallKitJoinChannelEvent : NSObject

   /// IM userID
   @property (nonatomic, copy) NSString *accid;

   /// 音视频用户id
   @property (nonatomic, assign) uint64_t uid;

   /// 音视频channelId
   @property (nonatomic, assign) uint64_t cid;

   /// 音视频channelName
   @property (nonatomic, copy) NSString *cname;

   @end

   ```

4. 呼叫接口变更 `-[NERtcCallKit call]` 增加 `attachment` 参数，此参数用户传递自定义参数，在被叫方收到邀请通知时可解析出。

   ```objc
   /// 收到邀请的回调
   /// @param invitor 邀请方
   /// @param userIDs 房间中的被邀请的所有人（不包含邀请者）
   /// @param isFromGroup 是否是群组 废弃
   /// @param groupID 群组ID 废弃
   /// @param type 通话类型
   - (void)onInvited:(NSString *)invitor
             userIDs:(NSArray<NSString *> *)userIDs
         isFromGroup:(BOOL)isFromGroup
             groupID:(nullable NSString *)groupID
                type:(NERtcCallType)type
          attachment:(nullable NSString *)attachment; // 增加的attachment，建议用JSON字符串
   ```
