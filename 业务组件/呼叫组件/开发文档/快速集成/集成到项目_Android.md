# 实现通话呼叫

针对已经集成 IM sdk 的用户若希望快速实现音视频通话功能，可通过集成呼叫组件完成实现。

## 注意事项
1. 若用户已经集成了 IM Sdk 或 NERTc Sdk 需参考**[产品动态](./产品动态-Android.md)** 关于 sdk 版本的映射关系，确保使用的 sdk 版本和组件版本一致。
2. 若用户未接入 IM Sdk 可参考[官网](https://doc.yunxin.163.com/docs/TM5MzM5Njk/zU4NzUxNjI?platformId=60002)完成 IM Sdk 的接入。

**用户完成上述两点注意后继续参考下面内容。**

## 准备工作

在开始运行示例项目之前，请确保您已完成以下操作：
联系云信商务获取开通以下权限，并联系技术支持配置产品服务和功能

1. 环境准备
   | 名称                 | 要求                          |
   | :------------------- | :---------------------------- |
   | JDK版本              | >1.8.0                        |
   | 最小Android API 版本 | API 21, Android 5.0           |
   | CPU架构支持          | ARM64、ARMV7                  |
   | IDE                  | Android Studio                |
   | 其他                 | 依赖androidx，不支持support库 |

2.  权限申请等

   在开始集成之前，请确保您已完成以下操作：
   联系云信商务获取开通以下权限，并联系技术支持配置产品服务和功能

   1. 通过文档[应用创建和服务开通](../../应用创建和服务开通.md)完成相关权限开通；
   2. 获取对应 AppKey；

## 集成

若需要在项目中引入呼叫组件按照以下步骤执行：

### 1. 前提 

已经完成云信 IM Sdk 接入，并实现了登录功能；

### 2. Gradle 引入

打开工程根部 build.gradle 文件添加如下代码引入 maven 仓库

```groovy
allprojects {
    repositories {
        //...
        mavenCentral()
        //...
    }
}
```

在主工程 build.gradle 文件中添加如下代码内容引入呼叫组件

```groovy
// 若出现 More than one file was found with OS independent path 'lib/arm64-v8a/libc++_shared.so'.
// 可以在主 module 的 build.gradle 文件中 android 闭包内追加如下 packageOptions 配置
android{
  	//......
    packagingOptions {
      pickFirst 'lib/arm64-v8a/libc++_shared.so'
      pickFirst 'lib/armeabi-v7a/libc++_shared.so'
  	}
}

dependencies {
    implementation 'com.netease.yunxin.kit:call-ui:1.4.0'
}
```

### 3. 初始化

**！！！在IM 登录成功后进行！！！**

**使用方在初始化完成后才可以收到其他人的的呼叫以及主动发起呼叫，否则接收不到对应的呼叫或产生 crash 提示需要进行初始化。若用户重复发调用初始化，则会销毁上次初始化内容，以当前初始化内容为准。**

**除了必要内容，其他选项按需配置！**

呼叫组件初始化内容可以放在工程的 `MainActivity` 中执行，尽量避免在 `MainActivity#onDestroy()`方法中做组件的释放。**可考虑用户登出时释放，登入时进行初始化。**

```java
CallKitUIOptions options = new CallKitUIOptions.Builder()
			// 必要：音视频通话 sdk appKey，用于通话中使用
			.rtcAppKey(appKey)
			// 必要：当前用户 AccId
			.currentUserAccId(“currentUserAccIdFromIM”)
			// 通话接听成功的超时时间单位 毫秒，默认30s
			.timeOutMillisecond(30 * 1000L)
			// 此处为 收到来电时展示的 notification 相关配置，如图标，提示语等。
			.notificationConfigFetcher(invitedInfo -> new CallKitNotificationConfig(R.drawable.ic_logo))
			// 收到被叫时若 app 在后台，在恢复到前台时是否自动唤起被叫页面，默认为 true
			.resumeBGInvitation(true)
			// 请求 rtc token 服务，若非安全模式则不需设置
			.rtcTokenService((uid, callback) -> requestRtcToken(appKey, uid, callback)) // 自己实现的 token 请求方法
			// 群组通话通话中邀请用户时，配置获取邀请的用户的列表
			.contactSelector((context, teamId, accounts, observer) -> {
				doFetchInviteAccountList(teamId, accounts, observer); // 自己实现的方法
				return null;
			})
			// 设置初始化 rtc sdk 相关配置，按照所需进行配置
			.rtcSdkOption(new NERtcOption())
			// 呼叫组件初始化 rtc 范围，true-全局初始化，false-每次通话进行初始化以及销毁
			// 全局初始化有助于更快进入首帧页面，当结合其他组件使用时存在rtc初始化冲突可设置false
			.rtcInitScope(true)
			.build();
// 若重复初始化会销毁之前的初始化实例，重新初始化
CallKitUI.init(getApplicationContext(), options);
```

### 4. 实现一对一通话

完成以上集成步骤后，若想实现 **用户A** 发起视频呼叫 **用户B** ，用户B 接收后进行视频通话的场景，可参考如下步骤：

1. 用户A 以及 用户B 均完成云信 IM Sdk 的登录，并成功初始化呼叫组件；

2. 用户A 获取到 自己 以及 用户B 登录云信 IM Sdk 的账号（AccId）；

3. 用户A 通过如下代码触发呼叫 用户B 的操作：

   ```java
   // @param type：呼叫类型 1-音频呼叫，2-视频呼叫
   // @param callerAccId： 呼叫方 IM 账号 Id
   // @param calledAccId： 被叫方 IM 账号 id
   CallParam param = CallParam.createSingleCallParam(2,"用户A AccId","用户B AccId");
   CallKitUI.startSingleCall(getActivity(), param);
   ```
   
4. 此时 用户B 会展示被叫页面，点击接听按钮进行视频通话；

5. 通话完成后点击挂断即可。

## 进阶功能

若希望进一步使用组件功能可参考下方列表内容完成：

[话单功能](../进阶功能/Android/话单功能.md)：用户想更详细的了解话单功能以及话单解析可参考此章节；

[自定义UI](../进阶功能/Android/自定义UI.md)：用户若希望自己实现UI可参考此章节内容完成；

[一对一呼叫通话流程](../进阶功能/Android/一对一呼叫通话流程.md)：便于了解底层实现呼叫流程；
