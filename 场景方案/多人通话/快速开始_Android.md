# 跑通多人通话
多人通话示例代码提供多人音视频通话功能

## 前提条件
在开始运行示例项目之前，请确保您已完成以下操作：
* 在云信控制台创建应用，并获取对应的 AppKey。
* 为此应用开通以下相关服务与抄送：
* 产品服务：  
音视频通话 2.0、IM 专业版
* 产品功能：   
音视频通话 2.0 的云端录制和抄送功能。
* 如需使用美颜功能请联系相芯获取美颜证书 [相芯](https://www.faceunity.com/)

## 开发环境 
在开始运行示例项目之前，请确保开发环境满足以下要求：
* Android SDK API 等级 19 或以上。
* Android Studio 4.0 或以上版本。  
  如果低于此版本，需要调整 build.gradle 和 gradle\wrapper\gradle-wrapper.properties 中匹配的版本。
* Android 系统 4.3 或以上版本的移动设备。

## 示例项目结构
|  目录   | 说明  |
|  ----  | ----  |
| app  | 应用主入口包含外部页面框架。 |
| biz-user | 用户相关业务，包括登录、注册、用户信息功能 |
| lib-user  | 用户lib |
| lib-modularity  | app模块化 |
| lib-basic  | 基础lib |
| lib-utils  | 工具集 |
| lib-beauty-faceunity  | 美颜lib，基于相芯 |
| biz-video-group  | 多人音视频通话业务 |
| lib-video-group  | 多人音视频通话lib |

## 运行示例源码
1. GitHub下载源代码 [源码](https://github.com/netease-kit/NEGroupCall/tree/master/Android)
2. 导入Android Studio
3. 找到config文件夹下的test.properties文件和online.properties文件替换自己的APP_KEY
* 此处的APP_KEY请联系技术支持同学确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换lib-beauty-faceunity/src/main/java/com/beautyFaceunity/authpack.java 证书文件
5. 运行在自己的Android设备上