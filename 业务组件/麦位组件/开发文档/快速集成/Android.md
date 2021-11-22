# 集成麦位组件

针对已经集成房间服务的客户，可以通过集成麦位组件实现麦位管理

## 注意事项
1. 若用户未接入 IM Sdk 可参考[官网](https://doc.yunxin.163.com/docs/TM5MzM5Njk/zU4NzUxNjI?platformId=60002)完成 IM Sdk 的接入。


## 准备工作
1. 环境准备
   1. AndroidStudio 4.0 版本及以上；
   2. Android sdk；


2. 开通相关功能

    **注：集成麦位组件的前提是必须已经跑通了房间服务房间服务**

## 集成

若需要在项目中引入麦位组件按照以下步骤执行：

### 0. 前提 

已经完成云信 IM Sdk 接入，并实现了登录功能；

客户自己的服务端已经接入了云信服务端的麦位组件相关接口，并且保持接口名一致

### 1. Gradle 引入

打工工程根部 build.gradle 文件添加如下代码引入 maven 仓库

```groovy
allprojects {
    repositories {
        //...
        mavenCentral()
        //...
    }
}
```

在主工程 build.gradle 文件中添加如下代码内容引入麦位组件

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
    implementation 'com.netease.yunxin.kit:seat:1.0.0'
}
```

### 2. 源码引入
如果想参考源码做定制话需求可以联系云信技术支持获取源码。

### 3. 初始化
初始化时传入自己的baseUrl，Appkey，token,userId,roomId,以及是否委托rtc操作的rtcOption.
```kotlin
val option = SeatOptions(
                baseURL, APP_KEY, accessToken,
                currentUserId, roomId, false
            )
            seatService.setupWithOptions(context, option)
```

### 3. 设置回调delegate来接受组件回调并处理
```kotlin
//定义delegate
val seatDelegate = object :SeatDelegate{
        /**
         * 收到上麦申请
         */
        override fun onSeatApplyRequest(event: SeatApplyEvent) {
            //audience need no impl
        }

        /**
         * 上麦申请被取消的回调
         */
        override fun onSeatApplyRequestCanceled(event: SeatApplyEvent) {
            //audience need no impl
        }

        /**
         * 收到报麦申请同意
         */
        override fun onSeatPickAccepted(event: SeatPickEvent) {
            //audience need no impl
        }

        /**
         * 收到报麦申请被拒绝
         */
        override fun onSeatPickRejected(event: SeatPickEvent) {
            //audience need no impl
        }

        /**
         * 麦位加入的回调
         */
        override fun onSeatEntered(event: SeatEnterEvent) {
            
        }

        /**
         * 麦位离开的回调
         */
        override fun onSeatLeft(event: SeatLeaveEvent) {
            
        }

        /**
         * 收到报麦申请
         */
        override fun onSeatPickRequest(event: SeatPickRequestEvent) {
                               
        }
//设置		
seatService.addDelegate(seatDelegate)
```
## 进阶功能

