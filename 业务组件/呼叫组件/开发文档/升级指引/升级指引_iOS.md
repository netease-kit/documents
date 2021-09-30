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

3. 添加 `onJoinChannel` 回调，此接口当用户加入音视频房间时触发此回调，用户可通过此回调获取用户 IM 的账号 Id在此次通话中的 uid 以及音视频房间的房间 Id 以及名称。

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

4. 呼叫接口变更  `-[NERtcCallKit call]`、` -[NERtcCallKit groupCall]`、` -[NERtcCallKit groupInvite]` 均增加 `attachment` 参数，此参数用户传递自定义参数，在被叫方收到邀请通知时可解析出。

   ```objc
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
          attachment:(nullable NSString *)attachment; // 增加的attachment，建议用JSON字符串
   ```
