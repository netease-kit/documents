## Android API接口
本页提供 智慧云课堂 for Android 的  [API 参考](https://dev.yunxin.163.com/docs/interface/NERTC_SDK/EDU/html/index.html)。
### API概览

**NEEduLogic组件的 API 接口列表如下：**

- **NEEduUiKit** **单例类，提供SDK配置，SDK初始化等基础能力，同时获取NEEduManager。**

| 接口                                                   | 备注              |
| ------------------------------------------------------ | ----------------- |
| config(context: Application, eduOptions: NEEduOptions) | 该接口用于SDK配置 |
| init()                                                 | 初始化组件        |
| enterClass(neEduClassOptions: NEEduClassOptions)       | 加入课堂          |

- **NEEduManager** **单例类，使用SDK提供的各种业务服务。**

| 接口                  | 备注             |
| --------------------- | ---------------- |
| getRoomService        | 获得课堂管理服务 |
| getMemberService      | 获得课堂成员服务 |
| getRtcService         | 获得音视频服务   |
| getIMService          | 获得消息聊天服务 |
| getShareScreenService | 获得屏幕共享服务 |
| getBoardService       | 获得白板服务     |
| getHandsUpService     | 获得举手上台服务 |
| destroy()             | 销毁对象         |

- **NEEduRoomService** **课堂管理类。**

| 接口                    | 备注                   |
| ----------------------- | ---------------------- |
| startClass              | 老师开始课堂           |
| finishClass:            | 老师结束课堂           |
|                         |                        |
| 回调方法                | 备注                   |
| onCurrentRoomInfo       | 当前课堂详情变化通知。 |
| onRoomStatesChange:     | 房间状态通知           |
| onNetworkQualityChange: | 网络变更通知           |

- **NEEduMemberService** ***课堂成员*** **管理类。**

| 接口                     | 备注                     |
| ------------------------ | ------------------------ |
| getMemberList            | 获取当前课堂成员详情列表 |
| getLocalUser             | 获取当前用户             |
|                          |                          |
| 回调方法                 | 备注                     |
| onMemberJoin             | 成员在线状态变化通知     |
| onMemberLeave            | 成员离开房间通知         |
| onMemberPropertiesChange | 成员属性变化通知         |

- **NEEduRtcService** **音视频管理类。**

| 接口                   | 备注                                   |
| ---------------------- | -------------------------------------- |
| muteAllAudio:          | 发送全体静音。                         |
| updateRtcAudio:        | 设置音频，会开关硬件                   |
| enableLocalVideo:      | 开关本地视频，会开关硬件               |
| updateRtcVideo:        | 设置成员的视频视图                     |
| updateRtcSubVideo:     | 设置辅流视频                           |
| localUserVideoEnable:  | 取消/发送本地视频，不开关硬件          |
| localUserAudioEnable:  | 取消/发送本地音频，不开关硬件          |
| remoteUserVideoEnable: | 老师开关远程视频                       |
| remoteUserAudioEnable: | 老师开关远程音频                       |
| destroy                | 离开音视频房间。                       |
|                        |                                        |
| 回调方法               | 备注                                   |
| onMuteAllAudio:        | 全体静音通知                           |
| onStreamChange:        | 流状态（音频、视频、辅流视频）变化通知 |

- **NEEduIMService** **聊天管理类。**

| 接口                        | 备注                        |
| --------------------------- | --------------------------- |
| sendMessage:                | 发送消息。                  |
| muteAllChat:                | 发送全体聊天禁言            |
| enterChatRoom:              | 加入聊天室                  |
| exitChatRoom:               | 退出聊天室                  |
|                             |                             |
| 回调方法                    | 备注                        |
| onReceiveMessage:           | 聊天消息通知。              |
| onMessageStatusChange:      | 图片消息状态变化通知。      |
| onAttachmentProgressChange: | 消息附件上传/下载进度通知。 |

- **NEEduShareScreenService** **屏幕共享管理类。**

| 接口                | 备注                             |
| ------------------- | -------------------------------- |
| grantPermission:    | 授权或取消授权成员的屏幕共享权限 |
| shareScreen         | 发送屏幕共享，不开关截屏         |
| finishShareScreen   | 取消屏幕共享，不开关截屏         |
| startScreenCapture  | 开始屏幕共享                     |
| stopScreenCapture   | 停止屏幕共享                     |
|                     |                                  |
| 回调方法            | 备注                             |
| onPermissionGranted | 屏幕共享权限发生变化             |
| onScreenShareChange | 屏幕共享状态变更                 |

- **NEEduBoardService** **白板管理类。**

| 接口                 | 备注                         |
| -------------------- | ---------------------------- |
| grantPermission:     | 授权或取消授权成员的白板权限 |
| initBoard            | 初始化白板                   |
| setEnableDraw        | 设置是否允许绘制             |
|                      |                              |
| 回调方法             | 备注                         |
| onPermissionGranted: | 白板权限变化通知             |

- **NEEduHandsUpService** **举手上台（适用于大班课场景）管理类。**

| 接口                  | 备注                     |
| --------------------- | ------------------------ |
| getHandsUpApplyList:  | 获取当前举手中的成员详情 |
| getOnStageMemberList  | 获取当前台上成员详情     |
| handsUpStateChange    | 改变举手状态             |
|                       |                          |
| 回调方法              | 备注                     |
| onHandsUpStateChange: | 学生台上状态发生变化     |
