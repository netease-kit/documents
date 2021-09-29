### 1.3.3 => 1.4.0

可直接升级；

### 1.3.1 => 1.3.3

1. 将 `implementation 'com.netease.yunxin.kit:call:1.3.3'` 替换为 `implementation 'com.netease.yunxin.kit:call-adapter:1.3.3'`；
2. 将 `CallParams` 替换为 `SelfParams`；
3. 将 `NERTCVideoCall` 替换为 `NERTCVideoCallWrapper`；
4. 将 `VideoCallOptions` 替换为 `VideoCallOptionsWrapper`；
5. `VideoCallOptions` 删除 `uiService` 字段，通过 `VideoCallOptionsWrapper`设置此字段；
6. `VideoCallOptions`中新增 `enableOrder`字段 用于控制本地不带时长话单的发送，时长话单发送开关需到云信后台设置；
7. `VideoCallOptions`中新增 `logRootPath` 字段用于配置组件日志路径，若用户设置`IM Sdk`路径则最好将此路径同样设置给组件；
8. `NERTCVideoCall` 新增接口 `setCallExtension` 用于设置 rtc 相关配置以及调用；
9. `NERTCVideoCall` 新增接口 `setPushConfigProvider` 用于设置呼叫推送配置；

### 1.3.0 => 1.3.1

1. 可直接升级；

### 1.2.1 => 1.3.0

1. `NERTCVideoCall`添加视频采集控制接口：

   ```java
      /**
      	* 开启/关闭视频采集
      	* @param isMute    true:视频采集关闭 false:视频采集打开
      	*/
      public abstract void muteLocalVideo(boolean isMute);
   ```

   接口调用会触发 `NERTCCallingDelegate.onVideoMuted()` 回调；

2. `NERTCCallingDelegate`添加音频采集控制回调接口，通过 

   `NERtcVideoCall.muteLocalAudio` 方法调用触发：

      ```java
   /**
   	* 远端用户是否开启音频流采集
   	* @param userId    远端用户id
   	* @param isMuted   true:关闭，false:开启
   	*/
   void onAudioMuted(String userId, boolean isMuted);
      ```

3. `NERTCCallingDelegate` 添加 `onJoinChannel` 接口，此接口当用户加入音视频房间时触发此回调，用户可通过此回调获取用户 IM 的账号 Id在此次通话中的 uid 以及音视频房间的房间 Id 以及名称。

   ```java
     /**
     	* 当前用户加入音视频的回调
     	*
     	* @param accId         用户 id
     	* @param uid           用户用于加入 rtc 房间的 uid
     	* @param channelName   用户加入 rtc 房间的通道名称
     	* @param rtcChannelId  rtc 房间通道 id
     	*/
     void onJoinChannel(String accId, long uid, String channelName, long rtcChannelId);
   ```

4. 呼叫接口变更  `NERtcVideoCall.call`、 `NERtcVideoCall.groupCall`、 `NERtcVideoCall.grouInvite` 均增加 `extraInfo` 参数，此参数用户传递自定义参数，在被叫方收到邀请通知时可解析出。

   ```java
       private NERTCCallingDelegate callingDelegate = new NERTCCallingDelegate() {
              @Override
              public void onInvited(InvitedInfo invitedInfo) {
                  // 被叫用户通过 invitedInfo.attachment 获取对应自定义参数；
              }
         //......
       }
   ```

