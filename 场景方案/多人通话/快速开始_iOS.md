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
* Xcode 11 及以上版本
* iOS 10.0 及以上版本的 iOS 设备


## 示例项目结构
|  目录   | 说明  |
|  ----  | ----  |
| NEGroupCall-iOS  | 应用主入口包含外部页面框架。 |
| NEGroupCall-iOS-Class | 项目主要功能逻辑，包含用户登录、注册、多人通话等 |
| NEGroupCall-iOS-Macro  | 宏定义|
| NEGroupCall-iOS-Class-Service  | 网络请求相关 |
| NEGroupCall-iOS-Base  | 基础类 |
| NEGroupCall-iOS-Support  | 分类和工具集 |
| NEGroupCall-iOS-Class-beauty  | 美颜lib，基于相芯 |

## 运行示例源码
1. GitHub下载源代码 [源码](https://github.com/netease-kit/NEGroupCall/tree/master/iOS)
2. 下载后，cd到NEGroupCall-master/iOS目录下，执行pod install。若不是最新版本，可通过命令pod repo update 更新本地版本。
3. 打开项目中AppKey.h文件，替换其中的kAppKey，kNertcAppkey，kApiHost。
* 此处所需替换的信息请联系技术支持同学，确认后台开通相关功能
4. 如果需要使用美颜功能请使用自己的相芯证书替换authpack.h证书文件
5. 运行在自己的iOS设备上