# Kit-Room-Web

网易云信房间 sdk，内部融合了 `G2音视频sdk`、`IM sdk`、`白板sdk`，帮助用户更快接入使用云信能力。

## 下载

```bash
$ npm install kit-room-web --save
```

## 引入

```ts
import RoomKit from 'kit-room-web'

const roomkit = new RoomKit({
    debug: true, // 开启日志
    deviceId: '', // 设备唯一id
})
// 初始化
roomkit.initialize({
  appKey, // 云信 appkey(注意K是大写)
  baseDomain, // 请求的domain地址，可不填
  neRtcServerAddresses: {} // RTC 私有化参数，可不填
  imPrivateConf: {} // IM 私有化参数，可不填
}
```

## 如何快速跑通音视频能力

以下对如何利用 roomkit 快速跑通云信能力做一些说明

### 登录

```ts
roomkit.getAuthService().loginWithAccount({
  username: '',
  password: '',
})
```

### 创建/加入 房间

```ts
// 创建房间
roomkit.getRoomService().startRoom(
  {
    roomId: '', // 房间id，可不填，服务器自动分配
    nickName: '', // 用户昵称
    tag: '', // 用户标签，可不填
  },
  {
    noAudio: false, // 不开启音频，默认不开启
    noVideo: false, // 不开启视频，默认不开启
    noCloudRecord: false, // 不开启屏幕录制，默认不开启
  }
)

// or 加入房间
roomkit.getRoomService().joinRoom(
  {
    roomId: '', // 房间id
    nickName: '', // 用户昵称
    tag: '', // 用户标签，可不填
  },
  {
    noAudio: false, // 不开启音频，默认不开启
    noVideo: false, // 不开启视频，默认不开启
    noCloudRecord: false, // 不开启屏幕录制，默认不开启
  }
)
```

### 播放本端音视频并推流

在创建或加入房间后，可播放本端并推流

```ts
// 获取本端视频渲染的dom节点
const localView = document.getElementById('localView')
// 获取房间内服务实例
const inRoomService = roomkit.getInRoomService()
// 获取本端userId
const userId = inRoomService.getMyUserId()
// 播放并推流
inRoomService
  .getInRoomVideoController()
  .attachRendererToUserVideoStream(localView, userId)
```

### 订阅并播放远端音视频

通过房间内服务监听远端用户加入和流信息变化事件，来订阅并播放远端音视频

```ts
// 获取房间内服务实例
const inRoomService = roomkit.getInRoomService()

// 监听用户加入
inRoomService.on('onRoomUserJoin', (userList: NERoomMemberInfo[]) => {
  // 获取远端视频容器节点
  const remoteContainer = document.getElementById('remoteContainer')
  // 插入每个用户dom节点，唯一标识建议使用avRoomUid
  userList.forEach((userInfo) => {
    const remoteView = document.createElement('div')
    remoteView.id = `remoteView-${userInfo.avRoomUid}`
    remoteContainer.appendChild(remoteView)
  })
})

// 监听远端推流事件
inRoomService.on('onRoomUserStreamAdded', async (event) => {
  // 订阅该流
  const avRoomUid = event.stream.getId()
  await inRoomService.getInRoomVideoController().subscribeRemoteVideo(
    event.userId || '', // userId
    true, // 是否订阅
    avRoomUid // avRoomUid
  )
})

// 监听远端流订阅成功事件
inRoomService.on('onRoomUserStreamSubscribed', (event) => {
  const avRoomUid = event.stream.getId()
  // 获取远端视频渲染的dom节点
  const remoteView = document.getElementById(`remoteView-${avRoomUid}`)
  // 播放远端音视频
  inRoomService
    .getInRoomVideoController()
    .attachRendererToUserVideoStream(remoteView, event.userId, avRoomUid)
})
```

详细接口文档可参考[roomkit web 接口文档](../API接口/Web.md)
