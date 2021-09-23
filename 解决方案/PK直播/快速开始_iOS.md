# 快速开始_iOS

## 开发环境
Android:  
在开始运行示例项目之前，请确保开发环境满足以下要求：
* Android SDK API 等级 19 或以上。
* Android Studio 4.0 或以上版本。  
  如果低于此版本，需要调整 build.gradle 和 gradle\wrapper\gradle-wrapper.properties 中匹配的版本。
* Android 系统 4.3 或以上版本的移动设备。

## 示例项目结构
Android  
|  目录   | 说明  |
|  ----  | ----  |
| app  | 应用主入口包含外部页面框架。 |
| biz-user | 用户相关实现 |
| lib-user  | 用户lib |
| lib-modularity  | app模块化 |
| lib-basic  | 基础lib |
| lib-beauty-faceunity  | 美颜lib，基于相芯 |
| lib-network-kt | kotlin 使用的网络库 |
| biz-live | 直播业务实现，主要是UI方面 |
| lib-live-room-service | 直播房间服务 |
| lib-live-pk-service | Pk相关功能服务 |

## 运行示例源码
Android  
1. GitHub下载源代码 [源码](https://github.com/netease-kit/OnlinePK/tree/master/OnlinePK-Android)
2. 导入Android Studio
3. 找到config/test.properties 文件替换自己的appkey
* 此处的appkey请联系技术支持同学确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换lib-beauty-faceunity/src/main/java/com/beautyFaceunity/authpack.java 证书文件
5. 运行在自己的Android设备上