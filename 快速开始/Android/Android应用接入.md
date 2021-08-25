#Android应用接入

##引入


##初始化
* 引入依赖

``` implementation 'com.netease.yunxin.kit:call:1.3.0'```

```
allprojects {
    repositories {
        //...
        mavenCentral()
        //...
    }
}
// 若出现 More than one file was found with OS independent path 'lib/arm64-v8a/libc++_shared.so'.
// 可以在主 module 的 build.gradle 文件中 android 闭包内追加如下 packageOptions 配置
android{
  	//......
    packagingOptions {
      pickFirst 'lib/arm64-v8a/libc++_shared.so'
      pickFirst 'lib/armeabi-v7a/libc++_shared.so'
  	}
}

```
* 初始化

组件初始化不包含 IM sdk 的初始化，且组件初始化必须放在 IM sdk 初始化之后，否则会出现崩溃。 可以放在 Application/MainActivity 内完成组件初始化调用。

组件实现为单实例，通过接口 NERTCVideoCall.sharedInstance() 获取此实例，调用实例方法 setupAppKey 完成初始化。

	 /**
     * 初始化，需要且仅能调用一次。
     *
     * @param context context
     * @param appKey  网易云信应用的 AppKey，请在控制台中获取。
     * @param option  初始化选项。
     */
    public abstract void setupAppKey(Context context, String appKey, VideoCallOptions option);
···

##登录/登出