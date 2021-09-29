# 集成到项目
## 新建 Android 工程
1. 运行 Android Sudio，在顶部菜单依次选择 “File -> New -> New Project...” 新建工程。
2. 选择 'Phone and Tablet' -> 'Empty Activity' ，并单击Next。
3. 配置工程相关信息。
> 注意： Minimum API Level 为 API 19。

4. 单击 'Finish'，完成工程创建。


## 集成音视频SDK
1、在项目对应模块build.gradle文件中集成NERTC SDK,查看[SDK更新日志](https://doc.yunxin.163.com/docs/jcyOTA0ODM/zcyMTgwNTk?platformId=50002)
```
dependencies {
    // 音视频SDK    
    api 'com.netease.yunxin:nertc:4.2.102'    
}

```
2、添加权限
打开 app/src/main/AndroidManifest.xml 文件，添加必要的设备权限。

```
 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
 <uses-permission android:name="android.permission.WAKE_LOCK"/>
 <uses-permission android:name="android.permission.CAMERA"/>
 <uses-permission android:name="android.permission.RECORD_AUDIO"/>
 <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
 <uses-permission android:name="android.permission.BLUETOOTH"/>
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.BROADCAST_STICKY"/>
 <uses-permission android:name="android.permission.READ_PHONE_STATE"/>

 
 <uses-feature android:name="android.hardware.camera"/>
 <uses-feature android:name="android.hardware.camera.autofocus"/>


```
3、防止代码混淆
在 proguard-rules.pro 文件中，为 NERTC SDK 添加 -keep 类的配置，可以防止混淆 NERTC SDK 公共类名称。

```
-keep class com.netease.lava.** {*;}
-keep class com.netease.yunxin.** {*;}
```
## 实现音视频通话功能
1、初始化SDK

```
// 示例
private void initializeSDK() {
      try {
          NERtcEx.getInstance().init(getApplicationContext(),Config.APP_KEY,callback,null);
      } catch (Exception e) {
          showToast("SDK初始化失败");
          finish();
          ...
          return;
      }
      ...
}


```
2、加入房间

通过[joinChannel](https://dev.yunxin.163.com/docs/interface/NERTC_SDK/Latest/Android/html/classcom_1_1netease_1_1lava_1_1nertc_1_1sdk_1_1_n_e_rtc.html#a2a44081baf4bd457749d1c48cd2def36)接口加入房间

```
  
  NERtcEx.getInstance().joinChannel(token,channelName,uid);

```
参数说明：

参数 | 说明
---|---
token | 安全认证签名（NERTCToken）。可设置为：null。调试模式下可设置为null。安全性不高，建议在产品正式上线前在云信控制台中将指定应用的鉴权方式恢复为默认的安全模式。已获取的 [NERTC Token](https://doc.yunxin.163.com/docs/jcyOTA0ODM/TQ0MTI2ODQ?platformId=50002)。
channelName | 房间名称，设置相同房间名称的用户会进入同一个通话房间。**注意**：您也可以在加入通道前，通过[创建房间](https://doc.yunxin.163.com/docs/zUyNzE0ODI/zgzNjEyMTA?platformId=274)接口创建房间。加入房间时，若传入的 {channelName} 未事先创建，则云信服务器内部将为其自动创建一个名为 {channelName} 的通话房间。
uid | 用户的唯一标识 id，房间内每个用户的 uid 必须是唯一的。

SDK发起加入房间请求后，服务器会进行响应，开发者可以通过 NERtcCallback 的 onJoinChannel 回调监听加入房间的结果，同时该回调会抛出当前通话房间的 channelId 与加入房间总耗时（毫秒）。

注意：请在初始化方法中传入原型为 NERtcCallback / NERtcCallbackEx 的 callback。

3、退出房间

通过 [leaveChannel](https://dev.yunxin.163.com/docs/interface/NERTC_SDK/Latest/Android/html/classcom_1_1netease_1_1lava_1_1nertc_1_1sdk_1_1_n_e_rtc.html#af42a2d700d78e67022ea04345229fe94) 接口退出通话房间。

```
// 退出通话房间
NERtcEx.getInstance().leaveChannel();

```
NERtcCallback 提供 onLeaveChannel 来监听当前用户退出房间的结果。

4、销毁实例

当确定 App 短期内不再使用音视频通话实例时，可以通过 release() 接口释放对应的对象资源。

```
// 销毁实例
NERtcEx.getInstance().release();
```
