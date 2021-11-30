## 概述

网易会议小程序组件 SDK提供了一套简单易用的接口，允许开发者通过调用NEMeeting SDK(以下简称SDK)提供的API，快速地集成音视频会议功能至现有小程序应用中。

## 快速接入

### 开发环境

  * 微信 App iOS 最低版本要求：7.0.9。
  * 微信 App Android 最低版本要求：7.0.8。
  * 小程序基础库最低版本要求：2.10.0。
  * 已安装最新版本的微信开发者工具。
  * 已安装微信的移动端设备以供调试和运行体验。
  * 由于微信开发者工具不支持原生组件（即 `<live-pusher>` 和 `<live-player>` 标签），需要在真机上进行运行体验。
  * 由于小程序测试号不具备 `<live-pusher>` 和 `<live-player>` 的使用权限，需要申请常规小程序账号进行开发。
  * 不支持 uniapp 开发环境，请使用原生小程序开发环境。

### SDK集成

1. 复制 NEMeeting 到 components 文件


2. 在使用会议组件的page引入组件

  > 由于手机屏幕限制，屏幕旋转配置需设置为横屏

```js
// xxx.json
{
	"pageOrientation": "landscape", // 设置为横屏
	"usingComponents": {
		"NEMeeting": "../../components/NEMeeting/NEMeeting" // 组件所在路径
	}
}
```
3. 使用组件

	**wxml文件**

```
<NEMeeting 
	id="meeting-component" 
	bindmeetingClosed="onMeetingClosed"
	binddisconnect="onDisconnect"
	bindkicked="onKicked"
	bindleave="leaveRoom" 
	bindonLoginStateChange="onLoginStateChange">
</NEMeeting>
```

#### API说明

**1. 初始化会议组件**

```js
// 通过小程序提供的 this.selectComponent() 方法获取组件实例
this.meetingComponent = this.selectComponent('#meeting-component')

/**
 * 初始化
	* @param param.appKey appKey
	* @param param.debug 是否开启调试模式
	* @param param.baseDomain [可选] 发起请求的domain
	* @param param.neRtcServerAddresses [可选] G2 sdk 私有化配置
	* @param param.imPrivateConf [可选] IM sdk 私有化配置
	*/
this.meetingComponent.initSDK({
	debug,
	appKey,
	baseDomain,
	neRtcServerAddresses,
	imPrivateConf
})

```

**2. 销毁会议组件**

```js
this.meetingComponent.destroy()
```

**3. 账号登录**

```js
const params = {
	accountId: '', //账号ID
	accountToken: '', //账号Token，特别提醒token后面有两个==占位符，请一并带上
}
this.meetingComponent.loginWithToken(params)
```

**4. 账号密码登录**

```js
const params = {
	username: '', //账号username
	password: '', //密码 无需加密，内部已封装
}
this.meetingComponent.loginWithAccount(params)
```

**5. 匿名登录**

```js
this.meetingComponent.anonymousJoinMeeting({
	roomId,
	nickName,
	openCamera,
	openMicrophone,
	tag
})
```

**6. 退出登录**

```js
this.meetingComponent.logout()

```

**7. 加入房间**

```js
const obj = {
	roomId: '', // 要加入会议ID
	nickName: '', //人员昵称
	openCamera: false, // true开启false关闭
	openMicrophone: false,  // true开启false关闭
	tag: '', // 成员自定义标签，可在成员列表昵称后显示
}
this.meetingComponent.joinRoom(obj)
```

**8. 结束、离开会议事件**

>  组件内部的leaveroom方法触发后，会抛出一个方法名为leave的事件，页面通过bindleave接收此方法可做后续操作。同样的，其他异常也是通过这样的方式处理。

```js

// wxml
<NEMeeting 
	id="meeting-component" 
	bindmeetingClosed="onMeetingClosed"
	binddisconnect="onDisconnect"
	bindkicked="onKicked"
	bindleave="leaveRoom" 
	bindonLoginStateChange="onLoginStateChange">
</NEMeeting>

	// js
  // 登录状态改变事件 idle：闲置，logining：登录中，logined：已登录，logouting：正在登出
}
	onLoginStateChange(status) {
    console.log(status.detail, 'login status')
		// 登录成功后才能加入房间（匿名登录除外）
    if (status.detail === 'logined') {
      const {roomId, nickName, openCamera, openMicrophone, tag} = this.data.config // 页面接受的参数，注意Boolean的判断
      this.meetingComponent.joinRoom({
        roomId, 
        nickName, 
        openCamera, 
        openMicrophone, 
        tag
      }).catch(err => {
        wx.showToast({
          title: err,
          icon: 'none'
        })
      })
    }
  },
  // 离开房间
	leaveRoom() {
		console.log('收到离开会议')
	},
	// 会议结束
  onMeetingClosed() {
    console.log('onMeetingClosed')
    this.destroy()
  },
	// 当前会议连接已断开
  onDisconnect() {
    console.log('onDisconnect')
    this.destroy()
  },
	// 因被主持人移出或切换至其他设备被踢出
  onKicked() {
    console.log('onKicked')
    this.destroy()
  },
	// 组件销毁（此方法已包含登出）
  destroy() {
    this.meetingComponent.destroy()
  }
```

**9. 当前会议信息，与会成员信息**

```js
	this.meetingComponent.getMeetingInfo() // 返回当前会议信息和成员信息
```

**10. 获取roomkit-sdk**

  > roomkit实例中包含会议中所有支持的方法
  
```js
	this.meetingComponent.getRoomKit()
```

## 注意事项

* 销毁意味着退出会议
* 初始化后用户需要执行登陆才可以进行加入
* 会议的全部功能在加入之后即可使用，无需其他额外配置
* 登陆的用户在其他页面登陆、创建或加入会议，会影响目前已经加入会议的页面，造成互踢
* API方法在执行失败后，如需进行错误排查，可以通过回调函数查看相关返回
