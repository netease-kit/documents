# Web 端 Roomkit 接口文档

## 初始化、销毁

```ts
/**
 * 初始化
 * @param config
 * @returns {Promise<void>}
 */
initialize(params: {
  appKey: string // 云信 appkey
  baseDomain?: string // 请求的domain地址，可不填
  neRtcServerAddresses?: Object // RTC 私有化参数，可不填
  imPrivateConf?: Object // IM 私有化参数，可不填
}): void

// 销毁
release(): void
```

## 获取 Service

```ts
// 获取用于查询账号信息的账号服务
getAuthService(): AuthServiceImpl

// 获取用于创建或加入房间的房间服务
getRoomService(): RoomServiceImpl

// 获取房间前服务
getPreRoomService(): PreRoomServiceImpl

// 获取房间内服务
getInRoomService(): InRoomServiceImpl

// 获取房间设置服务
getSettingsService(): SettingServiceImpl

// 获取内部IM实例
getImImpl(): IMImpl

// 获取内部RTC实例
getRtcImpl(): RtcImpl
```

## Services API

### AuthService

用于查询账号信息的账号服务

```ts
 /**
  * token 登录
  * 在已登录状态下可以创建和加入房间，但在未登录状态下只能加入房间
  * @param params
  * @returns {Promise<apis.NEAccountInfo>}
  */
  async loginWithToken(params: {
    accountId: string
    token: string
  }): Promise<apis.NEAccountInfo>

 /**
  * 账密登录
  * 在已登录状态下可以创建和加入房间，但在未登录状态下只能加入房间
  * @param params
  * @returns {Promise<apis.NEAccountInfo>}
  */
  async loginWithAccount(params: {
    username: string
    password: string
  }): Promise<apis.NEAccountInfo>

 /**
  * SSO 登录
  * 在已登录状态下可以创建和加入房间，但在未登录状态下只能加入房间
  * @param params
  * @returns {Promise<apis.NEAccountInfo>}
  */
  async loginWithSSOToken(params: {
    ssoToken: string
  }): Promise<apis.NEAccountInfo>

 /**
  * 匿名登录
  */
  async anonymousLogin(params: {
    roomId: string
    nickName: string
    tag?: string
    noVideo: boolean
    noAudio: boolean
  }): Promise<apis.NERoomInfo & apis.NEAccountInfo>

/**
   * 注销当前已登录的账号
   * @returns {Promise<void>}
   */
  async logout(): Promise<void>

  // 被踢出
  on(eventName: 'onKickOut', listener: () => void): this
  // 账号信息过期通知，原因为用户修改了密码，应用层随后应该重新登录
  on(eventName: 'onAuthInfoExpired', listener: () => void): this
```

### RoomService
用于创建或加入房间的房间服务

```ts
/**
   * 创建一个新的房间，只有完成SDK的登录鉴权操作才允许创建房间。
   * @param params 房间参数对象，不能为空
   * @param options 房间选项对象，可不传；当未指定时，会使用默认的选项
   * @returns {Promise<NERoomInfo>}
   */
  async startRoom(
    params: NEStartRoomParams,
    {
      noAudio = true,
      noVideo = true,
      noCloudRecord = true,
    }: types.NERoomOptions = {
      noAudio: true,
      noVideo: true,
      noCloudRecord: true,
    }
  ): Promise<apis.NERoomInfo>

/**
   * 加入一个当前正在进行中的房间，已登录或未登录均可加入房间。
   * @param params 房间参数对象，不能为空
   * @param options 房间选项对象，可不传；当未指定时，会使用默认的选项
   * @returns {Promise<NERoomInfo>}
   */
  async joinRoom(
    params: NEJoinRoomParams,
    {
      noAudio = true,
      noVideo = true,
      noCloudRecord = true,
    }: types.NERoomOptions = {
      noAudio: true,
      noVideo: true,
      noCloudRecord: true,
    }
  ): Promise<apis.NERoomInfo>

/**
   * 离开当前房间
   * @param endIfPossible 如果是房间主持人，在离开房间时是否直接结束房间
   * @returns {Promise<void>}
   */
  async leaveCurrentRoom(endIfPossible: boolean): Promise<void>
```

### PreRoomService
房间前服务

```ts
/**
   * 预约房间
   * @param item
   * @returns {Promise<NERoomItem>}
   */
  async scheduleRoom(item: {
    meetingId?: apis.NERoomItem['meetingId']
    subject: apis.NERoomItem['subject']
    startTime?: apis.NERoomItem['startTime']
    endTime?: apis.NERoomItem['endTime']
    password?: apis.NERoomItem['password']
    settings?: apis.NERoomItem['settings']
  }): Promise<apis.NERoomItem>

/**
   * 编辑预约房间
   * @param item
   * @returns {Promise<NERoomItem>}
   */
  async editRoom(item: {
    meetingId: apis.NERoomItem['meetingId']
    subject?: apis.NERoomItem['subject']
    startTime?: apis.NERoomItem['startTime']
    endTime?: apis.NERoomItem['endTime']
    password?: apis.NERoomItem['password']
    settings?: apis.NERoomItem['settings']
  }): Promise<apis.NERoomItem>

/**
   * 取消预约房间，开始前可以取消
   * @param roomUniqueId
   * @returns {Promise<void>}
   */
  async cancelRoom(roomUniqueId: number): Promise<void>

/**
   * 根据唯一id获取房间信息
   * @param roomUniqueId
   * @returns {Promise<NERoomItem>}
   */
  async getRoomItemByIdUniqueId(
    roomUniqueId: number
  ): Promise<apis.NERoomItem>

/**
   * 根据房间状态查询房间信息列表， 不传默认返回NERoomItemStatus.init, NERoomItemStatus.started
   * @param status
   * @returns {Promise<NERoomItem[]>}
   */
  async getRoomList(
    status?: apis.NERoomItemStatus[]
  ): Promise<apis.NERoomItem[]>

  // 预定房间状态变更监听
  on(
    eventName: 'onScheduledRoomStatusChange',
    listener: (items: apis.NERoomItem[]) => void
  ): this
```

### inRoomService
房间内服务

```ts
/**
   * 返回房间内音频控制器
   * @returns {InRoomAudioController}
   */
  getInRoomAudioController(): InRoomAudioController

/**
   * 获取房间视频控制器
   * @returns {InRoomVideoController}
   */
  getInRoomVideoController(): InRoomVideoController

/**
   * 返回云端录制控制器
   * @returns {InRoomCloudRecordController}
   */
  getInRoomCloudRecordController(): InRoomCloudRecordController

/**
   * 在房间里获取聊天室控制器实例
   * @returns {InRoomChatController}
   */
  getInRoomChatController(): InRoomChatController

/**
   * 在房间里获取白板控制器实例
   * @returns {InRoomWhiteboardController}
   */
  getInRoomWhiteboardController(): InRoomWhiteboardController

/**
   * 获取房间屏幕共享控制器
   * @returns {InRoomScreenShareController}
   */
  getInRoomScreenShareController(): InRoomScreenShareController

/**
   * 获取房间直播控制器
   * @returns {InRoomLiveStreamController}
   */
  getInRoomLiveStreamController(): InRoomLiveStreamController

/**
   * 房间是否锁定
   * @returns {boolean}
   */
  isRoomLocked(): boolean

/**
   * 主持人锁定房间
   * @param isLock 房间是否锁定
   * @returns {Promise<void>}
   */
  async lockRoom(isLock: boolean): Promise<void>

/**
   * 获取当前房间用户数
   * @returns {nunmber}
   */
  getUserCount(): number

/**
   * 获取当前房间所有用户信息
   * @returns {apis.NERoomMemberInfo[]}
   */
  getAllUsers(): apis.NERoomMemberInfo[]

/**
   * 获取我的用户id
   * @returns {string}
   */
  getMyUserId(): string
  
/**
   * 获取我的用户信息
   * @returns {apis.NERoomMemberInfo}
   */
  getMyUserInfo(): apis.NERoomMemberInfo

/**
   * 根据userId获取用户信息
   * @returns {apis.NERoomMemberInfo}
   */
  getUserInfoById(userId: string): apis.NERoomMemberInfo

/**
   * 是否是用户自己
   * @param userId 用户id
   * @returns {boolean}
   */
  isMySelf(userId: string): boolean

/**
   * 是否为主持人
   * @param userId 用户
   */
  isHostUser(userId: string): boolean | undefined

/**
   * 自己是否为主持人
   * @returns {boolean}
   */
  isMySelfHost(): boolean

/**
   * 是否允许自己解除静音
   * @returns {boolean}
   */
  isParticipantsUnmuteSelfAllowed(): boolean

/**
   * 举手
   * @returns {Promise<void>}
   */
  async raiseMyHand(): Promise<void>

/**
   * 主持人将某个用户的手放下
   * @param userId 用户id
   * @returns {Promise<void>}
   */
  async lowerHand(userId: string): Promise<void>

/**
   * 主持人把所有的用户手放下
   * @returns {Promise<void>}
   */
  async lowerAllHands(): Promise<void>

/**
   * 音视频挂起
   * @returns {Promise<void>}
   */
  async hangUp(hangUp: boolean): Promise<void>

/**
   * 切换通话形态：音频转视频；视频转音频
   * @returns {Promise<void>}
   */
  async changeCallType(callType: 'video' | 'audio'): Promise<void>

/**
   * 获取当前房间Id
   * @returns {string}
   */
  getCurrentRoomId(): string

/**
   * 获取当前房间信息
   */
  getCurrentRoomInfo(): {
    roomInfo: apis.NERoomInfo
    members: apis.NERoomMemberInfo[]
  }

  // 当前通话关闭
  on(eventName: 'onRtcChannelClosed', listener: () => void): this
  // 当前通话断网
  on(eventName: 'onRtcChannelDisconnected', listener: () => void): this
  // RTC 被踢
  on(
    eventName: 'onRtcClientBanned',
    listener: (event: { userId: string }) => void
  ): this
  // 当前通话统计回调
  on(
    eventName: 'onTransportStats',
    listener: (stats: NETransportStats) => void
  ): this
  // 当前通话统计回调
  on(
    eventName: 'onSessionStats',
    listener: (stats: NERtcSessionStats) => void
  ): this
  // 本地音频流统计信息回调
  on(
    eventName: 'onLocalAudioStats',
    listener: (stats: NERtcAudioSendStats[]) => void
  ): this
  // 通话中远端音频流的统计信息回调
  on(
    eventName: 'onRemoteAudioStats',
    listener: (statsList: NERtcAudioRecvStats[]) => void
  ): this
  // 本地视频流统计信息回调
  on(
    eventName: 'onLocalVideoStats',
    listener: (stats: NERtcVideoSendStats[]) => void
  ): this
  // 通话中远端视频流的统计信息回调
  on(
    eventName: 'onRemoteVideoStats',
    listener: (statsList: NERtcVideoRecvStats[]) => void
  ): this
  // 通话中每个用户的网络上下行质量报告回调
  on(
    eventName: 'onNetworkQuality',
    listener: (statsList: NERtcNetworkQualityInfo[]) => void
  ): this
  // 房间错误回调
  on(eventName: 'onRoomError', listener: (event: NEResult) => void): this
  // 用户加入房间回调
  on(
    eventName: 'onRoomUserJoin',
    listener: (event: { userList: apis.NERoomMemberInfo[] }) => void
  ): this
  // 用户离开房间回调
  on(
    eventName: 'onRoomUserLeave',
    listener: (event: { userList: apis.NERoomMemberInfo[] }) => void
  ): this
  // 远端用户推流
  on(
    eventName: 'onRoomUserStreamAdded',
    listener: (event: { userId: string; [key: string]: any }) => void
  ): this
  // 远端用户停止推流
  on(
    eventName: 'onRoomUserStreamRemove',
    listener: (event: { userId: string; [key: string]: any }) => void
  ): this
  // 订阅远端流成功
  on(
    eventName: 'onRoomUserStreamSubscribed',
    listener: (event: { userId: string; [key: string]: any }) => void
  ): this
  // 用户信息变更回调
  on(
    eventName: 'onRoomUserUpdated',
    listener: (event: { userId: string; name: string }) => void
  ): this
  // 用户昵称变更回调
  on(
    eventName: 'onRoomUserNameChanged',
    listener: (event: { userId: string; name: string }) => void
  ): this
  // 用户挂起状态变更
  on(
    eventName: 'onRoomUserHangup',
    listener: (event: { userId: string; hangUp: boolean }) => void
  ): this
  // 白板状态变更
  on(
    eventName: 'onWhiteboardStatusChanged',
    listener: (event: {
      sharer: string // 白板共享人roomUid
      shared: string // 白板被共享人roomUid，62/63存在
      operator: string // 操作人roomUid
      sharerImAccid: string // 白板拥有者imAccid
      statue: NEWhiteboardStatus
    }) => void
  ): this
  // 房间主持人变更回调
  on(
    eventName: 'onRoomHostChanged',
    listener: (event: { userList: string[] }) => void
  ): this
  // 房间麦克风用户视频变更回调
  on(
    eventName: 'onRoomActiveSpeakerVideoUserChanged',
    listener: (event: { userId: string }) => void
  ): this
  // 远端音量变化回调
  on(
    eventName: 'onRemoteAudioVolumeIndication',
    listener: (volumeList: NERtcAudioVolumeInfo[], totalVolume: number) => void
  ): this
  // 焦点视频状态变化回调
  on(
    eventName: 'onRoomUserVideoPinStatusChanged',
    listener: (event: { userId: string; isPinned: boolean }) => void
  ): this
  // 房间成员视频状态变更回调
  on(
    eventName: 'onRoomUserVideoStatusChanged',
    listener: (event: { userId: string; status: NEVideoStatus }) => void
  ): this
  // 房间成员音频状态变更回调
  on(
    eventName: 'onRoomUserAudioStatusChanged',
    listener: (event: { userId: string; status: NEAudioStatus }) => void
  ): this
  on(
    eventName: 'onRoomUserScreenShareStatusChanged',
    listener: (event: { userId: string; status: boolean }) => void
  ): this
  // 主持人请求成员打开音频回调
  on(
    eventName: 'onRoomHostAskUnmuteAudioCallback',
    listener: (event: { userId: string }) => void
  ): this
  // 主持人请求成员打开视频回调
  on(
    eventName: 'onRoomHostAskUnmuteVideoCallback',
    listener: (event: { userId: string }) => void
  ): this
  // 接收到房间聊天室消息回调
  on(
    eventName: 'onRoomChatMessageReceived',
    listener: (event: { messages: types.ChatRoomMessage[] }) => void
  ): this
  // 房间锁定状态变更回调
  on(
    eventName: 'onRoomLockStatusChanged',
    listener: (event: { isLock: boolean }) => void
  ): this
  // 房间音视频状态变更回调
  on(
    eventName: 'onRoomCallTypeChanged',
    listener: (event: { callType: 'audio' | 'video' }) => void
  ): this
  // 远端用户音频状态改变
  on(
    eventName: 'onRoomUserMuteAudio',
    listener: (event: { userId: string; mute: boolean }) => void
  ): this
  // 远端用户视频状态改变
  on(
    eventName: 'onRoomUserMuteVideo',
    listener: (event: { userId: string; mute: boolean }) => void
  ): this
  // RTC 开启
  on(eventName: 'onRtcOpen', listener: () => void): this
  // RTC 准备重连
  on(eventName: 'onRtcWillReconnect', listener: () => void): this
  // RTC 重连成功
  on(eventName: 'onRtcReconnected', listener: () => void): this
  // RTC 发送指令超时
  on(eventName: 'onRtcSendCommandOverTime', listener: () => void): this
  // RTC 同步完成
  on(eventName: 'onRtcSyncDone', listener: (event: any) => void): this
  // RTC 错误
  on(eventName: 'onRtcError', listener: (event: any) => void): this
  // 不支持 RTC 的错误回调
  on(eventName: 'onRtcAbilityNotSupport', listener: (event: any) => void): this
```

### SettingService
房间设置服务

```ts
/**
   * 查询是否开启美颜功能
   * @returns {boolean}
   */
  isBeautySupported(): boolean

/**
   * 是否允许开启直播
   * @returns {boolean}
   */
  isLiveStreamSupported(): boolean

/**
   * 查询白板服务开关状态
   * @returns {boolean}
   */
  isWhiteboardSupported(): boolean

/**
   * 查询是否为当前用户启用了云端录制，房间内本地属性
   * @returns {boolean}
   */
  isCloudRecordSupported(): boolean

/**
   * 查询是否开启聊天室功能
   * @returns {boolean}
   */
  isChatSupported(): boolean
```

## Controllers API

### InRoomVideoController

```ts
/**
   * 获取当前焦点视频用户ID
   * @returns {string}
   */
  getPinnedUserId(): string | undefined

  /**
   * 主持人取消设置/设置对应用户为焦点视频
   * @param userId 用户id
   * @param on true 设置；false 取消设置
   * @returns {Promise<void>}
   */
  pinVideo(userId: string, on: boolean): Promise<void>

  /**
   * 查询对应用户是否被设置为焦点视频
   * @param userId 用户id
   * @returns {boolean}
   */
  isUserPinned(userId: string): boolean

  /**
   * 主持人请求房间内其他用户开启视频
   * @param userId 用户id
   * @returns {Promise<void>}
   */
  askParticipantStartVideo(userId: string): Promise<void>

  /**
   * 主持人关闭房间内其他用户的视频
   * @param userId 用户id
   * @returns {Promise<void>}
   */
  stopParticipantVideo(userId: string): Promise<void>

  /**
   * 查询本地视频是否关闭
   * @returns {boolean}
   */
  isMyVideoMuted(): boolean | undefined

  /**
   * 查询本地是否可以开启视频
   * @returns {boolean}
   */
  canUnmuteMyVideo(): boolean | undefined

  /**
   * 开启/关闭本地视频
   * @param mute true 关闭；false 开启
   * @returns {Promise<void>}
   */
  muteMyVideo(mute: boolean): Promise<void>

  /**
   * 获取当前所使用的摄像头设备id
   * @returns {string}
   */
  getSelectedCameraId(): string

  /**
   * 当前使用的是否是后置摄像头
   * @param deviceId 设备id
   * @returns {boolean}
   */
  isBackCamera(deviceId: string): boolean

  /**
   * 当前使用的是否是前置摄像头
   * @param deviceId 设备id
   * @returns {boolean}
   */
  isFrontCamera(deviceId: string): boolean

  /**
   * 切换至对应的摄像头
   */
  switchCamera(params?: any): any

  /**
   * 查询本地是否可以切换摄像头
   * @returns {boolean}
   */
  canSwitchCamera(): boolean

  /**
   * 订阅房间内某一视频流
   * @param userId 用户id
   * @param subscribe true 订阅；false 取消订阅
   * @returns {Promise<void>}
   */
  subscribeRemoteVideo(params: {
    userId: string
    subscribe: boolean
    avRoomUid?: number
    [key: string]: any
  }): Promise<any>

  /**
   * 订阅房间内某一视频辅流
   * @param userId 订阅或者取消订阅的id
   * @param subscribe true 订阅；false 取消订阅
   */
  subscribeRemoteVideoSubStream(parmas: {
    userId: string
    subscribe: boolean
    avRoomUid?: number
    [key: string]: any
  }): Promise<any>

  /**
   * 枚举视频设备
   * @returns {Promise<DeviceInfo[]>}
   */
  enumCameraDevices(): Promise<types.DeviceInfo[]>

  /**
   * 为用户视频添加画布
   * @param {any} renderDom
   * @param {string} userId
   * @return {Promise<void>}
   */
  attachRendererToUserVideoStream(params: {
    userId: string
    avRoomUid?: number
    [key: string]: any
  }): Promise<void>

  /**
   * 为辅流添加画布
   * @param {any} renderDom
   * @param {string} userId
   * @return {Promise<void>}
   */
  attachRendererToUserVideoSubStream(params: {
    userId: string
    avRoomUid?: number
    [key: string]: any
  }): Promise<void>

  /**
   * 屏幕截图
   * @param userId 用户id
   * @param config 配置
   * @returns {Promise<void>}
   */
  takeSnapshot(
    userId: string,
    config?: types.NESnapshotConfig
  ): Promise<void>

  /**
   * 设置通话属性
   * @param profile
   */
  setCallProfile(profile: Partial<types.Profile>): void
```

### InRoomAudioController
```ts
/**
   * 主持人将所有与会者静音
   * @param allowUnmuteSelf true 允许取消自己静音；false 不允许
   * @returns {Promise<void>}
   */
  muteAllParticipantsAudio(allowUnmuteSelf: boolean): Promise<void>

  /**
   * 取消主持人的全部静音
   * @returns {Promise<void>}
   */
  unmuteAllParticipantsAudio(): Promise<void>

  /**
   * 主持人将与会者的音频静音
   * @param userId 用户id
   * @param isMute true 关闭；false 开启
   * @returns {Promise<void>}
   */
  muteParticipantAudio(userId: string, isMute: boolean): Promise<void>

  /**
   * 设置自己音频静音或者开启
   * @param isMute true 关闭；false 开启
   * @returns {Promise<void>}
   */
  muteMyAudio(isMute: boolean): Promise<void>

  /**
   * 查询当前发言者的扬声器状态
   * @returns {Promise<boolean>}
   */
  isMyLoudSpeakerOn(): Promise<boolean>

  /**
   * 设置是否可以切换当前房间的音频输出源时是否打开扬声器
   * @param on true 打开；false 关闭
   * @returns {Promise<void>}
   */
  setMyLoudSpeakerOn(on: boolean): Promise<void>

  /**
   * 设置是否音频采集静音
   * @param isMute true 静音；false 不静音
   * @returns {Promise<void>}
   */
  setRecordDeviceMute(isMute: boolean): Promise<void>

  /**
   * 获取音频采集设备是否静音
   * @returns {Promise<boolean>}
   */
  isRecordDeviceMute(): Promise<boolean>

  /**
   * 设置是否音频播放静音
   * @param isMute true 静音；false 不静音
   * @returns {Promise<void>}
   */
  setPlayoutDeviceMute(isMute: boolean): Promise<void>

  /**
   * 获取当前音频播放设备是否静音
   * @returns {Promise<boolean>}
   */
  isPlayoutDeviceMute(): boolean

  /**
   * 开启或关闭耳返
   * @param enabled true 开启；false 关闭
   * @param volume 耳返音量 [0-100]，默认100
   * @returns {Promise<void>}
   */
  enableEarBack(enabled: boolean, volume: number): Promise<void>

  /**
   * 设置耳返音量
   * @param volume 耳返音量 [0-100]，默认100
   * @returns {Promise<void>}
   */
  setEarBackVolume(volume: number): Promise<void>

  /**
   * 订阅房间内某一音频流
   * @param userId 订阅或者取消订阅的id
   * @param subscribe true 订阅；false 取消订阅
   * @returns {Promise<void>}
   */
  subscribeRemoteAudio(
    userId: string,
    subscribe: boolean
  ): Promise<void>

  /**
   * 查询是否启用与会者加入房间时自动使其静音
   * @returns {boolean | undefined}
   */
  isMuteOnEntryOn(): boolean | undefined

  /**
   * 启用说话者音量提示。该方法允许 SDK 定期向 App 反馈当前谁在说话以及说话者的音量
   * @param enable 是否启用说话者音量提示 true 启用；false 不启用
   * @returns {Promise<void>}
   */
  enableAudioVolumeIndication(enable: boolean): Promise<void>

  /**
   * 调节录音音量
   * 加入频道前后都可以调用
   * @param volume 调节范围为：[0~400] 0 静音；100 原始音量（默认）；400 最大可为原始音量的 4 倍(自带溢出保护)
   * @returns {Promise<void>}
   */
  adjustRecordingSignalVolume(volume: number): Promise<void>

  /**
   * 调节播放音量
   * 加入频道前后都可以调用
   * @param volume 调节范围为：[0~400] 0 静音；100 原始音量（默认）；400 最大可为原始音量的 4 倍(自带溢出保护)
   * @returns {Promise<void>}
   */
  adjustPlaybackSignalVolume(volume: number): Promise<void>

  /**
   * 启动音频采集设备测试
   * @returns {Promise<void>}
   */
  startRecordDeviceTest(): Promise<void>

  /**
   * 停止音频采集设备测试
   * @returns {Promise<void>}
   */
  stopRecordDeviceTest(): Promise<void>

  /**
   * 开始音频播放设备测试
   * @returns {Promise<void>}
   */
  startPlayoutDeviceTest(): Promise<void>

  /**
   * 停止音频播放设备测试
   * @returns {Promise<void>}
   */
  stopPlayoutDeviceTest(): Promise<void>

  /**
   * 枚举音频播放设备
   * @returns {Promise<DeviceInfo[]>}
   */
  enumPlayoutDevices(): Promise<types.DeviceInfo[]>

  /**
   * 枚举音频采集设备
   * @returns {Promise<DeviceInfo[]>}
   */
  enumRecordDevices(): Promise<types.DeviceInfo[]>

  /**
   * 选择音频播放设备
   * @param deviceId 设备id
   * @returns {Promise<void>}
   */
  selectPlayoutDevice(deviceId: string): Promise<void>

  /**
   * 选择音频采集设备
   * @param deviceId 设备id
   * @returns {Promise<void>}
   */
  selectRecordDevice(deviceId: string): Promise<void>

  /**
   * 获取音频播放设备
   */
  getPlayoutDevice(): string

  /**
   * 获取音频采集设备
   */
  getRecordDevice(): string

  /**
   * 获取音频采集设备音量
   * @returns {number}
   */
  getRecordDeviceVolume(): number

  /**
   * 获取音频播放设备音量
   * @returns {number}
   */
  getPlayoutDeviceVolume(): number

  /**
   * 设置音频采集设备音量
   * @param volume 音频播放设备音量
   * @returns {void}
   */
  setRecordDeviceVolume(volume: number): void

  /**
   * 设置音频播放设备音量
   * @param volume 音频播放设备音量
   * @returns {void}
   */
  setPlayoutDeviceVolume(volume: number): void
```

### InRoomScreenShareController
```ts
/**
   * 其他人是否正在共享屏幕
   * @returns {boolean}
   */
  isOtherSharing(): boolean

/**
   * 本端是否正在共享屏幕
   * @returns {boolean}
   */
  isSharingScreen(): boolean | undefined

/**
   * 获取当前正在屏幕共享的用户ID
   * @returns {string}
   */
  getScreenSharingUserId(): string

/**
   * 开启本地屏幕共享
   * @returns {Promise<void>}
   */
  async startScreenShare(): Promise<void>

/**
   * 停止本地屏幕共享
   * @returns {Promise<void>}
   */
  async stopScreenShare(): Promise<void>

/**
   * 主持人操作当前共享的用户停止屏幕共享
   * @returns {Promise<void>}
   */
  async askSharingUserStopScreenShare(): Promise<void>

  // 用户开始屏幕共享
  on(
    eventName: 'onUserScreenShareStart',
    listener: (userId: string) => void
  ): this
  // 用户屏幕共享停止
  on(
    eventName: 'onUserScreenShareStop',
    listener: (reason: number) => void
  ): this
```

### InRoomCloudRecordController

```ts
/**
   * 查询当前房间是否正在云端录制
   * @returns {boolean}
   */
  isCloudRecordEnabled(): boolean
```

### InRoomWhiteboardController

```ts
/**
   * 初始化
   * @returns {void}
   */
  init({
    otherWindow,
    origin,
  }: {
    otherWindow: Window
    origin: string
  }): void

/**
   * 销毁
   * @returns {void}
   */
  destroy(): void

/**
   * 登录
   * @returns {void}
   */
  login(params: {
    whiteboardOwnerImAccid: string
    imPrivateConf?: any
  }): void

/**
   * 登出
   * @returns {void}
   */
  logout(): void

/**
   * 开启/关闭白板
   * @returns {Promise<void>}
   */
  async enableWhiteboard(enable: boolean): Promise<void>
  
/**
   * 设置是否有权限开启编辑
   * @param userId
   * @param enable
   * @returns {Promise<void>}
   */
  async setInteractPrivilege(
    userId: string,
    enable: boolean
  ): Promise<void>

/**
   * 查询自己是否有编辑特权
   * @returns {Promise<boolean>}
   */
  hasMyInteractPrivilege(): boolean

/**
   * 查询某用户是否有编辑能力
   * @returns {boolean}
   */
  hasInteractPrivilege(userId: string): boolean | undefined

/**
   * 设置显示白板工具栏
   * @returns {Promise<void>}
   */
  async showDrawTools(enable: boolean): Promise<void>

/**
   * 查询用户是否正在白板屏幕
   * @param userId 用户id
   * @returns {boolean}
   */
  isWhiteboardSharing(userId: string): boolean | un

/**
   * 查询当前用户是否正在共享
   * @returns {boolean}
   */
  isMyWhiteboardSharing(): boolean

/**
   * 查询其他用户是否正在共享
   * @returns {boolean}
   */
  isOtherSharing(): boolean

/**
   * 查询是否当前房间中开启
   * @returns {boolean}
   */
  isWhiteboardEnabled(): boolean

  // 加入白板成功
  on(eventName: 'onJoinWBSucceed', listener: () => void): this
  // 离开白板
  on(eventName: 'onLeave', listener: () => void): this
  // 白板错误
  on(eventName: 'onError', listener: (error: NEResult) => void): this
```

### InRoomChatController

```ts
/**
   * 加入聊天室
   * @param data 加入房间参数
   * @returns {Promise<void>}
   */
  enterChatRoom(params: {
    imAppKey: string
    imAccid: string
    imToken: string
    chatroomId: string
    chatroomNick: string
    tags?: string[]
    notifyTargetTags?: string
    chatroomCustom?: string
  }): Promise<void>

/**
   * 退出聊天室
   * @returns {Promise<void>}
   */
  exitChatRoom(): Promise<void>

/**
   * 发送文本消息
   * @param params
   * @returns {ChatRoomMessage}
   */
  sendTextMessage(
    params: types.NEChatRoomSendTextMessage
  ): types.ChatRoomMessage

/**
   * 重发文本消息
   * @param params
   * @returns {ChatRoomMessage}
   */
  resendTextMessage(
    params: types.NEChatRoomResendTextMessage
  ): types.ChatRoomMessage

/**
   * 发送文件类型的消息
   * @param params
   * @returns {ChatRoomMessage}
   */
  sendFileMessage(
    params: types.NEChatRoomSendFileMessage
  ): types.ChatRoomMessage

/**
   * 重发文件类型的消息
   * @param params
   * @returns {ChatRoomMessage}
   */
  resendFileMessage(
    params: types.NEChatRoomResendFileMessage
  ): types.ChatRoomMessage

/**
   * 获取历史消息
   * @param params
   */
  getHistoryMsgs(
    params?: types.NEChatRoomGetHistoryMsg
  ): Promise<types.NEChatRoomHistoryMsg>
```

### InRoomLiveStreamController

```ts
/**
   * 查询直播是否正在进行中
   * @returns {boolean}
   */
  isLiveStreamEnabled(): boolean

/**
   * 获取当前直播信息
   * @returns {NERoomItemLive}
   */
  getLiveStreamChannel(): apis.NERoomItemLive

/**
   * 主持人开启直播
   * @param params
   * @returns {Promise<NERoomItemLive>}
   */
  async startLiveStream(params: {
    liveAVRoomUids: number[]
    liveChatRoomEnable: boolean
    liveChatRoomIndependent: boolean
    liveLayout: 1 | 2 // 1 画廊；2 聚焦
    liveWebAccessControlLevel?: 0 | 1 | 2 // 0 不需要鉴权（默认）；1 需要鉴权；2 需要登录并且账号要与直播应用绑定
    meetingId: string
    password?: string
    title: string
  }): Promise<apis.NERoomItemLive>

/**
   * 主持人更新直播状态
   * @param params
   * @returns {Promise<NERoomItemLive>}
   */
  async updateLiveStream(params: {
    liveAVRoomUids?: number[]
    liveChatRoomEnable?: boolean
    liveLayout: 1 | 2 // 1 画廊；2 聚焦
    meetingId: string
  }): Promise<apis.NERoomItemLive>

/**
   * 主持人结束直播
   * @returns {Promise<void>}
   */
  async stopLiveStream(): Promise<void>
```
