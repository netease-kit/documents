## iOS API接口
本页提供 智慧云课堂 for iOS 的  API 参考。
### API概览

**NEEduLogic组件的 API 接口列表如下：**

- `EduManager`单例类，设置SDK的配置信息，持有子功能对象。

| 接口                            | 备注       |
| ------------------------------- | ---------- |
| setupAppKey:options:            | 初始化组件 |
| login:success:failure:          | 登录账户   |
| enterClassroom:success:failure: | 加入课堂   |
| setCanvasView:forMember:        | 设置画布   |
| leaveClassroom                  | 离开课堂   |
| destoryClassroom                | 销毁对象   |

- `NEEduVideoService`：音视频管理类。

| 接口                       | 备注                                                         |
| -------------------------- | ------------------------------------------------------------ |
| setupAppkey:               | 设置音视频SDK的Appkey，EduManager的setupAppKey:options:方法中已调用。 |
| joinChannel: completion:   | 加入音视频房间，EduManager的enterClassroom:success:failure:中已调用 |
| setupLocalVideo:           | 设置本地视频画布，EduManager的setCanvasView:forMember:方法中已调用。 |
| setupRemoteVideo:          | 设置远端视频画布，EduManager的setCanvasView:forMember:方法中已调用。 |
| setupSubStreamVideo:       | 设置辅流视频画布                                             |
| enableLocalAudio:          | 开关音频                                                     |
| enableLocalVideo:          | 开关视频                                                     |
| muteLocalVideo:            | 取消/发送视频，不开关硬件                                    |
| muteLocalAudio:            | 取消/发送音频，不开关硬件                                    |
| subscribeVideo: forUserID: | 订阅用户的视频                                               |
| subscribeAudio: forUserID: | 订阅用户的音频                                               |
| leaveChannel               | 离开音视频房间，EduManager的leaveClassroom方法中已调用。     |
| destroy                    | 销毁对象，EduManager的destoryClassroom方法中已调用。         |

- `NEEduIMService`：聊天、信令管理类。

  | 接口                                     | 备注                                                         |
  | ---------------------------------------- | ------------------------------------------------------------ |
  | setupAppkey:                             | 设置IMSDK的AppKey，EduManager的setupAppKey:options:方法中已调用。 |
  | login: token: completion:                | 登录IMSDK，EduManager的login:success:failure:方法中已调用。  |
  | logoutWithCompletion:                    | 登出IMSDK。                                                  |
  | enterChatRoomWithParam: success: failed: | 加入聊天室，EduManager的enterClassroom:success:failure:方法中已调用。 |
  | exitChatroom: completion:                | 退出聊天室。                                                 |
  | sendChatroomTextMessage:                 | 发送聊天文字消息                                             |
  | sendChatroomImageMessage:                | 发送聊天图片消息                                             |
  | fetchChatroomInfo:                       | 获取聊天室信息                                               |
  | destroy                                  | 销毁对象                                                     |
  |                                          |                                                              |
  | 回调方法                                 | 备注                                                         |
  | didSendMessage: error:                   | 消息已发送                                                   |
  | didRecieveChatMessages:                  | 接受新消息                                                   |
  | didRecieveSignalMessage: fromUser:       | 接受信令通知                                                 |

- `NEEduMessageService`：信令通知分发类。

  | 接口                                    | 备注                        |
  | --------------------------------------- | --------------------------- |
  | updateProfile:                          | 初始化课堂快照数据          |
  |                                         |                             |
  | 回调方法                                | 备注                        |
  | onUserInWithUser: members:              | 用户进入课堂                |
  | onUserOutWithUser: members:             | 用户离开课堂                |
  | onVideoStreamEnable: user:              | 用户视频开启/关闭           |
  | onAudioStreamEnable: user:              | 用户音频频开启/关闭         |
  | onSubVideoStreamEnable:user:            | 用户辅流开启/关闭           |
  | onAudioAuthorizationEnable: user：      | 用户音频被授权开启/关闭     |
  | onVideoAuthorizationEnable: user：      | 用户视频被授权开启/关闭     |
  | onWhiteboardAuthorizationEnable: user:  | 用户被授予/取消白板编辑权限 |
  | onScreenShareAuthorizationEnable: user: | 用户被授予/取消屏幕共享权限 |
  | onHandsupStateChange: user:             | 用户举手状态变更            |
  | onLessonStateChange: roomUuid:          | 课堂状态变更                |
  | onLessonMuteAllAudio: roomUuid:         | 全体静音回调                |
  | onLessonMuteAllText: roomUuid:          | 全体禁言回调                |

  
