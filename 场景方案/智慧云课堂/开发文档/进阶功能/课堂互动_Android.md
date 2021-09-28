# 课堂互动

教学双方加入教室后，开启课程。智慧云课堂提供了丰富的课堂功能，如：互动白板、屏幕共享、举手、视频、语音等。

## 开始上课

教师端开始上课。示例代码：

```
// 教师端开始上课
eduManager.getRoomService().startClass(roomUuid = eduRoom.roomUuid)
    .observe(this@BaseClassActivity, {
        ALog.i(tag, "startClazz")
    })
```

## 学生管理  
1. 教师端调用 remoteUserVideoEnable、remoteUserAudioEnable 方法控制学生端的摄像头和麦克风。如果需要和学生互动，可以开启对方的麦克风。示例代码：

```
// 控制学生打开摄像头

eduManager.roomConfig.memberStreamsPermission()?.apply {
    val self = entryMember
    video?.let { it ->
        // 首先检查自己是否有权限
        if (it.hasAllPermission(self.role)) {
            // 接着调用remoteUserVideoEnable打开指定userUuid学生的摄像头
            eduManager.getRtcService().remoteUserVideoEnable(userUuid, true)
                .observe(this@BaseClassActivity, {
                    // 最后处理结果回调
                    ALog.i(tag, "switchRemoteUserVideo")
                    ToastUtil.showShort(R.string.operation_successful)
                })
        }
    }
}



// 控制学生打开音频

eduManager.roomConfig.memberStreamsPermission()?.apply {
    val self = entryMember
    audio?.let { it ->
        // 首先检查自己是否有权限
        if (it.hasAllPermission(self.role)) {
            // 接着调用remoteUserAudioEnable打开指定userUuid学生的音频
            eduManager.getRtcService().remoteUserAudioEnable(member.userUuid, !member.hasAudio())
                .observe(this@BaseClassActivity, {
                    // 最后处理结果回调
                    ALog.i(tag, "switchRemoteUserAudio")
                    toastOperateSuccess()
                })
        }
    }
}
```

2. 教师端调用 grantPermission方法授权学生使用白板或屏幕共享。示例代码：

```
// 授权学生使用白板
eduManager.roomConfig.memberPropertiesPermission()?.apply {
    val self = entryMember
    whiteboard?.let { it ->
        // 首先检查自己是否有权限
        if (it.hasAllPermission(self.role)) {
            // 接着调用grantPermission授权学生使用白板
            eduManager.getBoardService().grantPermission(member.userUuid, !member.isGrantedWhiteboard())
                .observe(this@BaseClassActivity, {
                    // 最后处理结果回调
                    ALog.i(tag, "grantWhiteboardPermission")
                })
        }
    }
}
```

## 屏幕共享

教师端或学生端调用 startScreenCapture 发起屏幕共享，共享本端屏幕给其他人观看。示例代码：

```
// 发起屏幕共享
// 首先创建屏幕共享配置实例
val config = NERtcScreenConfig().apply {
    contentPrefer = NERtcScreenConfig.NERtcSubStreamContentPrefer.CONTENT_PREFER_DETAILS
    videoProfile = RTCVideoProfile.kVideoProfileHD1080p
}
// 接着发起本地屏幕共享
eduManager.getShareScreenService().startScreenCapture(config, data, object :
    MediaProjection.Callback() {
    override fun onStop() {
        // 最后处理结果回调
        runOnUiThread { stopLocalShareScreen() }
    }
})
```

## 在线聊天室

在 1 对 1 和互动大班课中，可以通过聊天室实现消息收发，学生和学生、学生和老师之间通过聊天室发送文字或图片消息，教师端可以禁言或解禁聊天室。

师生调用 enterChatRoom 加入聊天室，并通过 sendMessage发送文字和图片消息。示例代码：

```
// 发起加入聊天室
// 首先创建EnterChatRoomData实例
val data = EnterChatRoomData(activity.eduRoom?.chatRoomId())
// 接着使用EnterChatRoomData实例进入聊天室
imService.enterChatRoom(data).observe(this, { it ->
    // 最后处理结果回调
    if (it.success()) roomInfo = it.data!!.roomInfo
    it
}



// 发送文本消息
// 首先创建文本消息
val chatMessage = ChatRoomMessageBuilder.createChatRoomTextMessage(it.roomId, text)
// 接着发送消息
imService.sendMessage(chatMessage)





// 发送图片消息
// 首先创建图片消息
val chatMessage =
    ChatRoomMessageBuilder.createChatRoomImageMessage(it.roomId, file, file?.name)
// 接着发送消息
imService.sendMessage(chatMessage)
```